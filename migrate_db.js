const fs = require('fs');
const path = require('path');

const srcDir = 'C:\\Users\\rs224\\OneDrive\\Desktop\\project\\Media_matrix_server\\database';
const schemaPath = path.join(srcDir, 'schema.sql');
const seedPath = path.join(srcDir, 'seed.sql');

function convertSchemaToPostgres(sql) {
    let result = sql;
    
    // Drop MySQL specific commands
    result = result.replace(/CREATE DATABASE IF NOT EXISTS media_matrix[\s\S]*?COLLATE utf8mb4_unicode_ci;/g, '');
    result = result.replace(/USE media_matrix;/g, '');
    
    // Replace datatypes and defaults
    result = result.replace(/INT AUTO_INCREMENT PRIMARY KEY/g, 'SERIAL PRIMARY KEY');
    result = result.replace(/TINYINT\(1\) DEFAULT 0/g, 'BOOLEAN DEFAULT false');
    result = result.replace(/TINYINT\(1\) DEFAULT 1/g, 'BOOLEAN DEFAULT true');
    result = result.replace(/TINYINT\(1\)/g, 'BOOLEAN');
    result = result.replace(/LONGTEXT/g, 'TEXT');
    
    // Replace ON UPDATE CURRENT_TIMESTAMP (not supported inline in Postgres)
    result = result.replace(/ON UPDATE CURRENT_TIMESTAMP/g, '');
    
    // Strip trailing commas before ENGINE=InnoDB
    result = result.replace(/,\s*\)\s*ENGINE=InnoDB;/g, '\n);');
    
    // Drop ENGINE=InnoDB
    result = result.replace(/\)\s*ENGINE=InnoDB;/g, ');');
    
    // Handle inline indices: 
    // Replace "INDEX idx_name (col)" and "UNIQUE KEY uq_name (col)" inside CREATE TABLE
    const indexLines = [];
    result = result.split('\n').map(line => {
        if (line.trim().startsWith('INDEX ') || line.trim().startsWith('FULLTEXT INDEX ')) {
            // Keep track but remove from CREATE TABLE
            return '';
        }
        if (line.trim().startsWith('UNIQUE KEY ')) {
            return line.replace(/UNIQUE KEY \w+ \((.*?)\)/, 'UNIQUE ($1)');
        }
        return line;
    }).join('\n');
    
    // Fix dangling commas caused by removing INDEX lines
    result = result.replace(/,\s*\n\s*\)/g, '\n)');
    
    // Convert backticks
    result = result.replace(/`rank`/g, '"rank"');
    
    return result;
}

function convertSeedToPostgres(sql) {
    let result = sql;
    
    // Convert date intervals: NOW() - INTERVAL 1 DAY -> NOW() - INTERVAL '1 DAY'
    result = result.replace(/INTERVAL (\d+) DAY/gi, "INTERVAL '$1 DAY'");
    result = result.replace(/INTERVAL (\d+) HOUR/gi, "INTERVAL '$1 HOUR'");
    result = result.replace(/INTERVAL (\d+) MINUTE/gi, "INTERVAL '$1 MINUTE'");
    
    // Convert backticks
    result = result.replace(/`rank`/g, '"rank"');
    
    return result;
}

// Ensure database "media_matrix" exists command goes separately, we'll run it directly.

const pgSchema = convertSchemaToPostgres(fs.readFileSync(schemaPath, 'utf8'));
const pgSeed = convertSeedToPostgres(fs.readFileSync(seedPath, 'utf8'));

fs.writeFileSync('pg_schema.sql', pgSchema);
fs.writeFileSync('pg_seed.sql', pgSeed);

console.log("Converted SQL files generated: pg_schema.sql, pg_seed.sql");
