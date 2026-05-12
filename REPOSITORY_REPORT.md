# Media_Matrix Repository Comprehensive Report

**Report Date:** 2026-05-12  
**Repository ID:** 1198259781  
**Repository Owner:** krish01info  
**Repository Name:** Media_Matrix  
**Repository URL:** https://github.com/krish01info/Media_Matrix

---

## Table of Contents
1. [Repository Overview](#repository-overview)
2. [Repository Metadata](#repository-metadata)
3. [Language Composition](#language-composition)
4. [Directory Structure](#directory-structure)
5. [Build Configuration Files](#build-configuration-files)
6. [Database Configuration](#database-configuration)
7. [Git Configuration](#git-configuration)

---

## Repository Overview

**Project Name:** Media_Matrix  
**Description:** A comprehensive media aggregation and analysis platform  
**Visibility:** Public  
**Fork Status:** Not a fork  
**Created:** ~40 days ago  
**Last Updated:** 14 hours ago  
**Last Push:** 2026-05-11T14:30:10Z  

---

## Repository Metadata

### Basic Information
- **Repository ID:** 1198259781
- **Owner Type:** User
- **Owner Username:** krish01info
- **Owner Avatar URL:** https://avatars.githubusercontent.com/u/185189330?v=4
- **Owner Profile URL:** https://github.com/krish01info
- **Full Name:** krish01info/Media_Matrix
- **Repository Size:** 227 KB
- **HTML URL:** https://github.com/krish01info/Media_Matrix

### Repository Settings

#### Merge & Commit Policies
- **Allow Auto Merge:** False
- **Allow Forking:** True
- **Allow Merge Commit:** True
- **Allow Rebase Merge:** True
- **Allow Squash Merge:** True
- **Allow Update Branch:** False
- **Delete Branch on Merge:** False
- **Pull Request Creation Policy:** All
- **Web Commit Signoff Required:** False

#### Merge Commit Configuration
- **Merge Commit Message:** PR_TITLE
- **Merge Commit Title:** MERGE_MESSAGE
- **Squash Merge Commit Message:** COMMIT_MESSAGES
- **Squash Merge Commit Title:** COMMIT_OR_PR_TITLE
- **Use Squash PR Title as Default:** False

### Repository Features
- **Default Branch:** main
- **Has Discussions:** False
- **Has Downloads:** True
- **Has Issues:** True
- **Has Pages:** False
- **Has Projects:** True
- **Has Pull Requests:** True
- **Has Wiki:** True
- **Is Template:** False
- **Archived:** False
- **Disabled:** False

### Repository Statistics
- **Stargazers Count:** 0
- **Watchers Count:** 0
- **Forks Count:** 0
- **Network Count:** 0
- **Open Issues Count:** 0
- **Subscribers Count:** 0

### Repository License & Topics
- **License:** None
- **Homepage:** None
- **Topics:** None
- **Primary Language:** Java

---

## Language Composition

### Language Distribution

| Language | Percentage | Usage |
|----------|-----------|-------|
| Java | 98.6% | Primary backend and Android application development |
| JavaScript | 1.4% | Database migration and utility scripts |

### Analysis
The repository is predominantly a **Java-based project** with a small amount of **JavaScript** for auxiliary tasks like database schema conversion and seeding.

---

## Directory Structure

```
Media_Matrix/
├── .github/
├── .idea/
│   └── .gitignore
├── app/
│   ├── .gitignore
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
├── gradle/
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
├── settings.gradle.kts
├── README.md
├── migrate_db.js
├── pg_schema.sql
└── pg_seed.sql
```

---

## Build Configuration Files

### 1. Root-Level build.gradle.kts
**Path:** `build.gradle.kts`  
**Size:** 167 bytes

```gradle kotlin dsl
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
}
```

**Purpose:** Root-level Gradle configuration file defining plugins applied to all subprojects.

---

### 2. App Module build.gradle.kts
**Path:** `app/build.gradle.kts`  
**Size:** 2,697 bytes

```gradle kotlin dsl
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

// Inject API keys from local.properties into BuildConfig
val localProps = Properties()
val localFile = rootProject.file("local.properties")
if (localFile.exists()) localProps.load(localFile.inputStream())

android {
    namespace = "com.example.media_matrix"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.media_matrix"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "NEWS_API_KEY", "\"${localProps["NEWS_API_KEY"] ?: ""}\"")
        buildConfigField("String", "GNEWS_API_KEY", "\"${localProps["GNEWS_API_KEY"] ?: ""}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    // AndroidX Core
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation(libs.cardview)
    implementation(libs.viewpager2)
    implementation(libs.swiperefreshlayout)
    implementation(libs.coordinatorlayout)

    // Lifecycle
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)

    // Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Retrofit + OkHttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.gson)

    // Glide
    implementation(libs.glide)

    // ExoPlayer (Media3)
    implementation(libs.media3.exoplayer)
    implementation(libs.media3.ui)
    implementation(libs.media3.session)

    // Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    // Google Sign-In (Credential Manager)
    implementation(libs.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
```

**Configuration Details:**

| Setting | Value |
|---------|-------|
| Namespace | com.example.media_matrix |
| Compile SDK | 36 |
| Min SDK | 30 |
| Target SDK | 36 |
| Application ID | com.example.media_matrix |
| Version Code | 1 |
| Version Name | 1.0 |
| Source/Target Compatibility | Java 11 |

**Key Features Enabled:**
- View Binding
- Build Config (for API key injection)

**API Keys Injected:**
- NEWS_API_KEY
- GNEWS_API_KEY

**Dependencies Categories:**
- AndroidX Core Libraries
- Lifecycle Management
- Navigation Framework
- Retrofit & OkHttp (REST APIs)
- Glide (Image Loading)
- ExoPlayer (Media3)
- Room Database
- Google Sign-In
- Testing Frameworks

---

### 3. settings.gradle.kts
**Path:** `settings.gradle.kts`  
**Size:** 537 bytes

```gradle kotlin dsl
pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Media_Matrix"
include(":app")
```

**Purpose:** Configures plugin and dependency repositories, includes the app module.

**Plugin Repositories:**
1. Google (Android, Google, and AndroidX libraries)
2. Maven Central
3. Gradle Plugin Portal

**Dependency Repositories:**
1. Google
2. Maven Central

---

### 4. gradle.properties
**Path:** `gradle.properties`  
**Size:** 1,255 bytes

```java properties
# Project-wide Gradle settings.
# IDE (e.g. Android Studio) users:
# Gradle settings configured through the IDE *will override*
# any settings specified in this file.
# For more details on how to configure your build environment visit
# http://www.gradle.org/docs/current/userguide/build_environment.html
# Specifies the JVM arguments used for the daemon process.
# The setting is particularly useful for tweaking memory settings.
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
# When configured, Gradle will run in incubating parallel mode.
# This option should only be used with decoupled projects. For more details, visit
# https://developer.android.com/r/tools/gradle-multi-project-decoupled-projects
# org.gradle.parallel=true
# AndroidX package structure to make it clearer which packages are bundled with the
# Android operating system, and which are packaged with your app's APK
# https://developer.android.com/topic/libraries/support-library/androidx-rn
android.useAndroidX=true
# Enables namespacing of each library's R class so that its R class includes only the
# resources declared in the library itself and none from the library's dependencies,
# thereby reducing the size of the R class for that library
android.nonTransitiveRClass=true
```

**Configuration Properties:**

| Property | Value | Purpose |
|----------|-------|---------|
| org.gradle.jvmargs | -Xmx2048m -Dfile.encoding=UTF-8 | JVM memory and encoding |
| android.useAndroidX | true | Enable AndroidX libraries |
| android.nonTransitiveRClass | true | Optimize R class namespacing |

---

## Database Configuration

### 1. Database Migration Script
**Path:** `migrate_db.js`  
**Size:** 2,948 bytes  
**Language:** JavaScript

```javascript
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
```

**Purpose:** Converts MySQL database schema to PostgreSQL format.

**Conversion Functions:**

1. **convertSchemaToPostgres()**
   - Removes MySQL database creation commands
   - Converts data types (INT AUTO_INCREMENT → SERIAL, TINYINT(1) → BOOLEAN, LONGTEXT → TEXT)
   - Removes MySQL-specific syntax (ENGINE=InnoDB, ON UPDATE CURRENT_TIMESTAMP)
   - Handles index and constraint conversions
   - Converts backticks to double quotes

2. **convertSeedToPostgres()**
   - Converts INTERVAL syntax (MySQL → PostgreSQL format)
   - Converts backticks to double quotes

---

### 2. PostgreSQL Schema
**Path:** `pg_schema.sql`  
**Size:** 12,431 bytes

#### Schema Structure Overview

**22 Tables Defined:**

1. **users** - User accounts and profiles
2. **categories** - News article categories
3. **sources** - News sources/outlets
4. **reporters** - Journalist profiles
5. **regions** - Geographic regions
6. **tags** - Article tags
7. **articles** - News articles
8. **article_tags** - Many-to-many articles-tags
9. **article_regions** - Many-to-many articles-regions
10. **newspapers** - Digital newspaper editions
11. **radio_streams** - Radio broadcast streams
12. **podcasts** - Podcast episodes
13. **trending_topics** - Trending news topics
14. **trending_topic_articles** - Many-to-many trending topics-articles
15. **regional_charts** - Regional news charts
16. **world_map_insights** - Geographic insights
17. **user_bookmarks** - User-saved articles
18. **user_reading_history** - User reading activity
19. **user_preferences** - User preferences
20. **refresh_tokens** - Authentication tokens
21. **compare_coverages** - Comparative coverage analysis
22. **compare_coverage_items** - Coverage comparison items

#### Complete Schema Code

```sql
-- ============================================================
-- Media Matrix — Complete MySQL Schema
-- ============================================================

-- ============================================================
-- 1. users
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  uuid CHAR(36) NOT NULL UNIQUE,
  username VARCHAR(50) NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  avatar_url VARCHAR(500) DEFAULT NULL,
  is_verified BOOLEAN DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP 
);

-- ============================================================
-- 2. categories
-- ============================================================
CREATE TABLE IF NOT EXISTS categories (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  slug VARCHAR(100) NOT NULL UNIQUE,
  description TEXT DEFAULT NULL,
  icon_url VARCHAR(500) DEFAULT NULL,
  gradient_start VARCHAR(7) DEFAULT NULL,
  gradient_end VARCHAR(7) DEFAULT NULL,
  display_order INT DEFAULT 0,
  is_active BOOLEAN DEFAULT 1,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- 3. sources
-- ============================================================
CREATE TABLE IF NOT EXISTS sources (
  id SERIAL PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  short_name VARCHAR(50) DEFAULT NULL,
  slug VARCHAR(100) NOT NULL UNIQUE,
  logo_url VARCHAR(500) DEFAULT NULL,
  website_url VARCHAR(500) DEFAULT NULL,
  description TEXT DEFAULT NULL,
  is_verified BOOLEAN DEFAULT 0,
  trust_score DECIMAL(5,2) DEFAULT 0.00,
  has_radio BOOLEAN DEFAULT 0,
  has_newspaper BOOLEAN DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- 4. reporters
-- ============================================================
CREATE TABLE IF NOT EXISTS reporters (
  id SERIAL PRIMARY KEY,
  name VARCHAR(200) NOT NULL,
  slug VARCHAR(100) NOT NULL UNIQUE,
  title VARCHAR(200) DEFAULT NULL,
  bio TEXT DEFAULT NULL,
  avatar_url VARCHAR(500) DEFAULT NULL,
  truth_score DECIMAL(5,2) DEFAULT 0.00,
  is_verified BOOLEAN DEFAULT 0,
  is_independent BOOLEAN DEFAULT 0,
  source_id INT DEFAULT NULL,
  total_articles INT DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (source_id) REFERENCES sources(id) ON DELETE SET NULL
);

-- ============================================================
-- 5. regions
-- ============================================================
CREATE TABLE IF NOT EXISTS regions (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  slug VARCHAR(100) NOT NULL UNIQUE,
  parent_id INT DEFAULT NULL,
  latitude DECIMAL(10,7) DEFAULT NULL,
  longitude DECIMAL(10,7) DEFAULT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (parent_id) REFERENCES regions(id) ON DELETE SET NULL
);

-- ============================================================
-- 6. tags
-- ============================================================
CREATE TABLE IF NOT EXISTS tags (
  id SERIAL PRIMARY KEY,
  name VARCHAR(100) NOT NULL UNIQUE,
  slug VARCHAR(100) NOT NULL UNIQUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- 7. articles
-- ============================================================
CREATE TABLE IF NOT EXISTS articles (
  id SERIAL PRIMARY KEY,
  uuid CHAR(36) NOT NULL UNIQUE,
  title VARCHAR(500) NOT NULL,
  subtitle VARCHAR(500) DEFAULT NULL,
  slug VARCHAR(500) NOT NULL,
  content TEXT NOT NULL,
  summary TEXT DEFAULT NULL,
  image_url VARCHAR(500) DEFAULT NULL,
  thumbnail_url VARCHAR(500) DEFAULT NULL,
  category_id INT NOT NULL,
  source_id INT NOT NULL,
  reporter_id INT DEFAULT NULL,
  truth_score DECIMAL(5,2) DEFAULT 0.00,
  is_verified BOOLEAN DEFAULT 0,
  is_featured BOOLEAN DEFAULT 0,
  is_breaking BOOLEAN DEFAULT 0,
  is_developing BOOLEAN DEFAULT 0,
  is_live BOOLEAN DEFAULT 0,
  is_morning_brief BOOLEAN DEFAULT 0,
  interaction_count BIGINT DEFAULT 0,
  view_count BIGINT DEFAULT 0,
  share_count BIGINT DEFAULT 0,
  published_at TIMESTAMP NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE RESTRICT,
  FOREIGN KEY (source_id) REFERENCES sources(id) ON DELETE RESTRICT,
  FOREIGN KEY (reporter_id) REFERENCES reporters(id) ON DELETE SET NULL
);

-- ============================================================
-- 8. article_tags (many-to-many)
-- ============================================================
CREATE TABLE IF NOT EXISTS article_tags (
  article_id INT NOT NULL,
  tag_id INT NOT NULL,
  PRIMARY KEY (article_id, tag_id),
  FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);

-- ============================================================
-- 9. article_regions (many-to-many)
-- ============================================================
CREATE TABLE IF NOT EXISTS article_regions (
  article_id INT NOT NULL,
  region_id INT NOT NULL,
  PRIMARY KEY (article_id, region_id),
  FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE,
  FOREIGN KEY (region_id) REFERENCES regions(id) ON DELETE CASCADE
);

-- ============================================================
-- 10. newspapers
-- ============================================================
CREATE TABLE IF NOT EXISTS newspapers (
  id SERIAL PRIMARY KEY,
  source_id INT NOT NULL,
  title VARCHAR(300) NOT NULL,
  cover_image_url VARCHAR(500) NOT NULL,
  pdf_url VARCHAR(500) DEFAULT NULL,
  edition_date DATE NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (source_id) REFERENCES sources(id) ON DELETE CASCADE
);

-- ============================================================
-- 11. radio_streams
-- ============================================================
CREATE TABLE IF NOT EXISTS radio_streams (
  id SERIAL PRIMARY KEY,
  source_id INT NOT NULL,
  title VARCHAR(300) NOT NULL,
  description TEXT DEFAULT NULL,
  stream_url VARCHAR(500) NOT NULL,
  thumbnail_url VARCHAR(500) DEFAULT NULL,
  is_live BOOLEAN DEFAULT 0,
  is_high_quality BOOLEAN DEFAULT 0,
  listener_count INT DEFAULT 0,
  display_order INT DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (source_id) REFERENCES sources(id) ON DELETE CASCADE
);

-- ============================================================
-- 12. podcasts
-- ============================================================
CREATE TABLE IF NOT EXISTS podcasts (
  id SERIAL PRIMARY KEY,
  source_id INT NOT NULL,
  title VARCHAR(300) NOT NULL,
  description TEXT DEFAULT NULL,
  audio_url VARCHAR(500) NOT NULL,
  thumbnail_url VARCHAR(500) DEFAULT NULL,
  duration_seconds INT DEFAULT NULL,
  episode_number INT DEFAULT NULL,
  published_at TIMESTAMP NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (source_id) REFERENCES sources(id) ON DELETE CASCADE
);

-- ============================================================
-- 13. trending_topics
-- ============================================================
CREATE TABLE IF NOT EXISTS trending_topics (
  id SERIAL PRIMARY KEY,
  title VARCHAR(300) NOT NULL,
  slug VARCHAR(300) NOT NULL,
  engagement_score BIGINT DEFAULT 0,
  engagement_label VARCHAR(100) DEFAULT NULL,
  region_id INT DEFAULT NULL,
  is_active BOOLEAN DEFAULT 1,
  trended_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (region_id) REFERENCES regions(id) ON DELETE SET NULL
);

-- ============================================================
-- 14. trending_topic_articles
-- ============================================================
CREATE TABLE IF NOT EXISTS trending_topic_articles (
  topic_id INT NOT NULL,
  article_id INT NOT NULL,
  PRIMARY KEY (topic_id, article_id),
  FOREIGN KEY (topic_id) REFERENCES trending_topics(id) ON DELETE CASCADE,
  FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE
);

-- ============================================================
-- 15. regional_charts
-- ============================================================
CREATE TABLE IF NOT EXISTS regional_charts (
  id SERIAL PRIMARY KEY,
  "rank" INT NOT NULL,
  title VARCHAR(300) NOT NULL,
  metric_label VARCHAR(200) DEFAULT NULL,
  region_id INT NOT NULL,
  article_id INT DEFAULT NULL,
  chart_date DATE NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (region_id) REFERENCES regions(id) ON DELETE CASCADE,
  FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE SET NULL
);

-- ============================================================
-- 16. world_map_insights
-- ============================================================
CREATE TABLE IF NOT EXISTS world_map_insights (
  id SERIAL PRIMARY KEY,
  title VARCHAR(300) NOT NULL,
  description TEXT DEFAULT NULL,
  latitude DECIMAL(10,7) NOT NULL,
  longitude DECIMAL(10,7) NOT NULL,
  icon_type VARCHAR(50) DEFAULT NULL,
  article_id INT DEFAULT NULL,
  is_active BOOLEAN DEFAULT 1,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE SET NULL
);

-- ============================================================
-- 17. user_bookmarks
-- ============================================================
CREATE TABLE IF NOT EXISTS user_bookmarks (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  article_id INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE (user_id, article_id),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE
);

-- ============================================================
-- 18. user_reading_history
-- ============================================================
CREATE TABLE IF NOT EXISTS user_reading_history (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  article_id INT NOT NULL,
  read_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  read_duration_sec INT DEFAULT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE CASCADE
);

-- ============================================================
-- 19. user_preferences
-- ============================================================
CREATE TABLE IF NOT EXISTS user_preferences (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL UNIQUE,
  preferred_categories JSON DEFAULT NULL,
  preferred_regions JSON DEFAULT NULL,
  preferred_sources JSON DEFAULT NULL,
  notification_enabled BOOLEAN DEFAULT 1,
  high_quality_audio BOOLEAN DEFAULT 0,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ============================================================
-- 20. refresh_tokens
-- ============================================================
CREATE TABLE IF NOT EXISTS refresh_tokens (
  id SERIAL PRIMARY KEY,
  user_id INT NOT NULL,
  token VARCHAR(500) NOT NULL UNIQUE,
  expires_at TIMESTAMP NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ============================================================
-- 21. compare_coverages
-- ============================================================
CREATE TABLE IF NOT EXISTS compare_coverages (
  id SERIAL PRIMARY KEY,
  topic_title VARCHAR(300) NOT NULL,
  is_active BOOLEAN DEFAULT 1,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================
-- 22. compare_coverage_items
-- ============================================================
CREATE TABLE IF NOT EXISTS compare_coverage_items (
  id SERIAL PRIMARY KEY,
  compare_id INT NOT NULL,
  source_id INT NOT NULL,
  headline VARCHAR(500) NOT NULL,
  stance_label VARCHAR(100) DEFAULT NULL,
  image_url VARCHAR(500) DEFAULT NULL,
  article_id INT DEFAULT NULL,
  FOREIGN KEY (compare_id) REFERENCES compare_coverages(id) ON DELETE CASCADE,
  FOREIGN KEY (source_id) REFERENCES sources(id) ON DELETE CASCADE,
  FOREIGN KEY (article_id) REFERENCES articles(id) ON DELETE SET NULL
);
```

---

### 3. PostgreSQL Seed Data
**Path:** `pg_seed.sql`  
**Size:** 37,994 bytes

#### Seed Data Overview

**Sample Data Inserted:**

| Category | Count | Details |
|----------|-------|---------|
| Categories | 8 | Global Affairs, Tech & Innovation, Politics & Policy, Science & Research, Economy, Space, Health, Environment |
| Sources | 8 | NYT, WSJ, BBC World, Reuters, Al Jazeera, Financial Times, AP, Bloomberg |
| Reporters | 6 | Elena Richardson, Marcus Thorne, Sarah Chen, James Okafor, Maria Santos, David Park |
| Regions | 13 | Americas, Europe, Asia, Middle East, Africa, Oceania, and regional subdivisions |
| Tags | 12 | Climate Accord 2.0, Semiconductor Rivalry, AI Regulation, Trade Wars, Space Exploration, etc. |
| Articles | 20 | Various articles with UUIDs, headlines, content, and metadata |
| Trending Topics | 4 | Climate Accord 2.0, Semiconductor Rivalry, AI Regulation Framework, Digital Currency Revolution |
| Newspapers | 3 | NYT, WSJ, FT daily editions |
| Radio Streams | 6 | BBC World, Reuters Market Update, Al Jazeera, FT, NYT radio |
| Podcasts | 3 | BBC World Service, Bloomberg Markets, The Daily (NYT) |
| Regional Charts | 6 | Electric Aviation, Remote Work Policy, Nuclear Fusion, Digital Currency, Green Energy, AI Healthcare |
| World Map Insights | 4 | East Europe, Central Asia, South Pacific, North Africa |
| Compare Coverages | 2 | G7 Climate Agreements, AI Regulation Approaches |

#### Complete Seed Data Code

```sql
-- ============================================================
-- Media Matrix — Seed Data for Testing
-- ============================================================

USE media_matrix;

-- ============================================================
-- Categories
-- ============================================================
INSERT INTO categories (name, slug, description, gradient_start, gradient_end, display_order) VALUES
('Global Affairs', 'global-affairs', 'Geopolitics, diplomacy, global conflicts, and international relations.', '#1a237e', '#0d47a1', 1),
('Tech & Innovation', 'tech-innovation', 'Latest in technology, AI, startups, and digital innovation.', '#004d40', '#00695c', 2),
('Politics & Policy', 'politics-policy', 'Government policy, elections, legislation, and political analysis.', '#b71c1c', '#c62828', 3),
('Science & Research', 'science-research', 'Scientific discoveries, research breakthroughs, and space exploration.', '#4a148c', '#6a1b9a', 4),
('Economy', 'economy', 'Markets, finance, trade, and economic analysis.', '#e65100', '#f57c00', 5),
('Space', 'space', 'Space exploration, NASA missions, and astronomical discoveries.', '#1b5e20', '#2e7d32', 6),
('Health', 'health', 'Medical breakthroughs, public health, and wellness.', '#880e4f', '#ad1457', 7),
('Environment', 'environment', 'Climate change, sustainability, and environmental policy.', '#1a237e', '#283593', 8);

-- ============================================================
-- Sources
-- ============================================================
INSERT INTO sources (name, short_name, slug, logo_url, website_url, is_verified, trust_score, has_radio, has_newspaper) VALUES
('The New York Times', 'NYT', 'nyt', 'https://res.cloudinary.com/demo/image/upload/nyt_logo.png', 'https://www.nytimes.com', 1, 92.50, 1, 1),
('Wall Street Journal', 'WSJ', 'wsj', 'https://res.cloudinary.com/demo/image/upload/wsj_logo.png', 'https://www.wsj.com', 1, 91.00, 0, 1),
('BBC World', 'BBC', 'bbc-world', 'https://res.cloudinary.com/demo/image/upload/bbc_logo.png', 'https://www.bbc.com', 1, 94.00, 1, 0),
('Reuters', 'Reuters', 'reuters', 'https://res.cloudinary.com/demo/image/upload/reuters_logo.png', 'https://www.reuters.com', 1, 95.50, 1, 0),
('Al Jazeera', 'AJ', 'al-jazeera', 'https://res.cloudinary.com/demo/image/upload/aj_logo.png', 'https://www.aljazeera.com', 1, 85.00, 1, 0),
('Financial Times', 'FT', 'financial-times', 'https://res.cloudinary.com/demo/image/upload/ft_logo.png', 'https://www.ft.com', 1, 93.00, 1, 1),
('Associated Press', 'AP', 'associated-press', 'https://res.cloudinary.com/demo/image/upload/ap_logo.png', 'https://apnews.com', 1, 96.00, 0, 0),
('Bloomberg', 'Bloomberg', 'bloomberg', 'https://res.cloudinary.com/demo/image/upload/bloomberg_logo.png', 'https://www.bloomberg.com', 1, 90.00, 0, 0);

-- ============================================================
-- Reporters
-- ============================================================
INSERT INTO reporters (name, slug, title, bio, truth_score, is_verified, is_independent, source_id) VALUES
('Elena Richardson', 'elena-richardson', 'Conflict Zone Specialist', 'Award-winning journalist covering global conflicts and humanitarian crises with over 15 years of field experience.', 91.00, 1, 1, NULL),
('Marcus Thorne', 'marcus-thorne', 'Deep Tech Analyst', 'Former Silicon Valley engineer turned technology journalist. Specializes in AI, quantum computing, and emerging tech.', 88.50, 1, 1, NULL),
('Sarah Chen', 'sarah-chen', 'Asia-Pacific Correspondent', 'Based in Singapore, covering economic and political developments across the Asia-Pacific region.', 89.00, 1, 0, 4),
('James Okafor', 'james-okafor', 'Africa Bureau Chief', 'Veteran journalist reporting on African politics, economics, and social issues for over two decades.', 87.50, 1, 1, NULL),
('Maria Santos', 'maria-santos', 'Latin America Editor', 'Pulitzer Prize nominee covering Latin American affairs, trade, and democratic movements.', 93.00, 1, 0, 7),
('David Park', 'david-park', 'Climate & Energy Reporter', 'Environmental journalist and author. Covers climate policy, renewable energy, and sustainability.', 90.00, 1, 1, NULL);

-- ============================================================
-- Regions
-- ============================================================
INSERT INTO regions (name, slug, parent_id, latitude, longitude) VALUES
('Americas', 'americas', NULL, 19.4326, -99.1332),
('Europe', 'europe', NULL, 50.8503, 4.3517),
('Asia', 'asia', NULL, 35.6762, 139.6503),
('Middle East', 'middle-east', NULL, 25.2048, 55.2708),
('Africa', 'africa', NULL, -1.2921, 36.8219),
('Oceania', 'oceania', NULL, -33.8688, 151.2093),
('North America', 'north-america', 1, 38.9072, -77.0369),
('South America', 'south-america', 1, -23.5505, -46.6333),
('Western Europe', 'western-europe', 2, 48.8566, 2.3522),
('Eastern Europe', 'eastern-europe', 2, 52.2297, 21.0122),
('East Asia', 'east-asia', 3, 35.6762, 139.6503),
('South Asia', 'south-asia', 3, 28.6139, 77.2090),
('Southeast Asia', 'southeast-asia', 3, 1.3521, 103.8198);

-- ============================================================
-- Tags
-- ============================================================
INSERT INTO tags (name, slug) VALUES
('Climate Accord 2.0', 'climate-accord-2'),
('Semiconductor Rivalry', 'semiconductor-rivalry'),
('AI Regulation', 'ai-regulation'),
('Trade Wars', 'trade-wars'),
('Space Exploration', 'space-exploration'),
('Digital Currency', 'digital-currency'),
('Vaccine Trials', 'vaccine-trials'),
('Arctic Policy', 'arctic-policy'),
('Nuclear Fusion', 'nuclear-fusion'),
('Carbon Neutrality', 'carbon-neutrality'),
('Election 2024', 'election-2024'),
('Market Volatility', 'market-volatility');

-- ============================================================
-- Articles (Sample Articles)
-- ============================================================
INSERT INTO articles (uuid, title, subtitle, slug, content, summary, image_url, thumbnail_url, category_id, source_id, reporter_id, truth_score, is_verified, is_featured, is_breaking, is_developing, is_live, is_morning_brief, published_at) VALUES
('a1b2c3d4-e5f6-7890-abcd-111111111111', 'Global Economy Shifts', 'Major economic indicators point to significant restructuring', 'global-economy-shifts', 'The global economy is undergoing fundamental shifts...', 'Summary of economic changes', 'https://example.com/img1.jpg', 'https://example.com/thumb1.jpg', 5, 8, 1, 89.00, 1, 1, 0, 0, 0, 1, NOW()),
('a1b2c3d4-e5f6-7890-abcd-222222222222', 'AI Revolution 2024', 'Artificial intelligence reaches new milestones in every industry', 'ai-revolution-2024', 'Artificial intelligence has reached unprecedented levels of capability...', 'AI breakthroughs this year', 'https://example.com/img2.jpg', 'https://example.com/thumb2.jpg', 2, 1, 2, 92.00, 1, 1, 0, 0, 0, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-333333333333', 'Central Bank announces unexpected interest rate changes', 'Markets react as Federal Reserve shifts monetary policy direction', 'central-bank-interest-rate', 'The Federal Reserve has announced a significant shift...', 'Fed policy announcement', 'https://example.com/img3.jpg', 'https://example.com/thumb3.jpg', 5, 8, 1, 95.00, 1, 0, 1, 0, 0, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-444444444444', 'New quantum computing breakthrough', 'Scientists achieve quantum supremacy in practical application', 'quantum-computing-breakthrough', 'A team of international scientists announced a major breakthrough...', 'Quantum computing milestone', 'https://example.com/img4.jpg', 'https://example.com/thumb4.jpg', 4, 2, 2, 90.50, 1, 0, 1, 0, 0, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-555555555555', 'Global Markets hit all-time high', 'Record-breaking rally across major stock exchanges', 'global-markets-all-time-high', 'Markets around the world have reached unprecedented heights...', 'Stock market reaches peak', 'https://example.com/img5.jpg', 'https://example.com/thumb5.jpg', 5, 2, 3, 88.00, 1, 1, 0, 0, 0, 1, NOW()),
('a1b2c3d4-e5f6-7890-abcd-666666666666', 'New Neural Engine architecture doubles processing efficiency', 'Breakthrough chip design achieves 2x performance per watt', 'neural-engine-architecture', 'Tech companies announce revolutionary chip improvements...', 'Computing hardware advancement', 'https://example.com/img6.jpg', 'https://example.com/thumb6.jpg', 2, 1, 2, 91.00, 1, 0, 0, 0, 0, 1, NOW()),
('a1b2c3d4-e5f6-7890-abcd-777777777777', 'Mars Rover discovers organic compounds in ancient lake bed', 'NASA confirms presence of complex organic molecules on Mars', 'mars-rover-organic-compounds', 'NASA scientists have announced a groundbreaking discovery on Mars...', 'Mars discovery news', 'https://example.com/img7.jpg', 'https://example.com/thumb7.jpg', 4, 7, 1, 96.00, 1, 1, 1, 0, 0, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-888888888888', 'Electoral Commission results pending', 'Counting continues in major districts with narrow margins reported', 'electoral-commission-results-pending', 'Election results continue to emerge with close races...', 'Election updates ongoing', 'https://example.com/img8.jpg', 'https://example.com/thumb8.jpg', 3, 3, 4, 93.50, 1, 0, 0, 1, 0, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-999999999999', 'G7 Summit Communiqué released', 'Leaders agree on new climate framework aiming for carbon neutrality', 'g7-summit-communique-released', 'G7 leaders have released a comprehensive climate agreement...', 'G7 climate framework', 'https://example.com/img9.jpg', 'https://example.com/thumb9.jpg', 8, 6, 5, 91.50, 1, 0, 0, 1, 0, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-aaaaaaaaaaaa', 'Breakthrough in universal flu vaccine trials', 'Phase III clinical trials show 95% efficacy across all flu strains', 'universal-flu-vaccine-breakthrough', 'Medical researchers have achieved a historic milestone...', 'Vaccine breakthrough', 'https://example.com/img10.jpg', 'https://example.com/thumb10.jpg', 7, 1, 3, 94.00, 1, 1, 1, 0, 0, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-bbbbbbbbbbbb', 'Trump says NATO refusal to help on Iran is very foolish mistake', 'US President criticizes alliance partners over Middle East stance', 'nato-refusal-iran', 'International diplomatic tensions have escalated...', 'Diplomatic tensions rise', 'https://example.com/img11.jpg', 'https://example.com/thumb11.jpg', 1, 1, 4, 87.00, 1, 0, 0, 0, 0, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-cccccccccccc', 'Latin American trade bloc proposes unified digital currency', 'Mercosur nations explore blockchain-based regional currency', 'latam-unified-digital-currency', 'South American nations are exploring a regional cryptocurrency...', 'Regional currency initiative', 'https://example.com/img12.jpg', 'https://example.com/thumb12.jpg', 5, 4, 5, 85.50, 0, 0, 0, 0, 0, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-dddddddddddd', 'Pentagon increases focus on Arctic surveillance capabilities', 'Department of Defense expands polar monitoring infrastructure', 'pentagon-arctic-surveillance', 'The U.S. military is expanding Arctic operations...', 'Arctic defense strategy', 'https://example.com/img13.jpg', 'https://example.com/thumb13.jpg', 1, 1, 4, 88.50, 1, 0, 0, 0, 0, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-eeeeeeeeeeee', 'Global Energy Summit', 'World leaders gather in Zurich for the acceleration of green energy', 'global-energy-summit', 'World leaders and industry executives convene for energy discussion...', 'Energy summit coverage', 'https://example.com/img14.jpg', 'https://example.com/thumb14.jpg', 8, 5, 6, 89.50, 1, 0, 0, 0, 1, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-ffffffffffff', 'Market Volatility Report', 'Analyzing the impact of recent policy changes on global tech markets', 'market-volatility-report', 'Global technology markets show signs of volatility...', 'Market analysis', 'https://example.com/img15.jpg', 'https://example.com/thumb15.jpg', 5, 2, 2, 86.00, 1, 0, 0, 0, 1, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-101010101010', 'Space Exploration Ethics', 'A discussion on regulations around private space travel and orbital debris', 'space-exploration-ethics', 'Experts debate the regulatory framework for commercial space ventures...', 'Space policy discussion', 'https://example.com/img16.jpg', 'https://example.com/thumb16.jpg', 6, 3, 1, 87.75, 1, 0, 0, 0, 1, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-121212121212', 'Global Economy Outlook', 'Comprehensive analysis of worldwide economic trends and forecasts', 'global-economy-outlook', 'The International Monetary Fund releases its latest economic assessment...', 'Economic forecast', 'https://example.com/img17.jpg', 'https://example.com/thumb17.jpg', 5, 8, 3, 90.00, 1, 1, 0, 0, 0, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-131313131313', 'New Policy Reform', 'Government announces sweeping reforms to education and healthcare', 'new-policy-reform', 'The government has unveiled a comprehensive reform package...', 'Policy reform announcement', 'https://example.com/img18.jpg', 'https://example.com/thumb18.jpg', 3, 4, 4, 85.00, 0, 0, 0, 0, 0, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-141414141414', 'Urban Transit Updates', 'Cities worldwide investing in sustainable public transportation', 'urban-transit-updates', 'Major cities worldwide are accelerating sustainable transit investments...', 'Urban transportation news', 'https://example.com/img19.jpg', 'https://example.com/thumb19.jpg', 8, 6, 5, 88.25, 1, 0, 0, 0, 0, 0, NOW()),
('a1b2c3d4-e5f6-7890-abcd-151515151515', 'Global Market Inflation Data', 'Central banks worldwide assess latest inflation metrics', 'global-market-inflation-data', 'Central banks across major economies release inflation statistics...', 'Inflation data release', 'https://example.com/img20.jpg', 'https://example.com/thumb20.jpg', 5, 1, 2, 91.25, 1, 1, 0, 0, 0, 1, NOW());

-- ============================================================
-- Article Tags
-- ============================================================
INSERT INTO article_tags (article_id, tag_id) VALUES
(1, 4), (1, 12),
(2, 3),
(3, 12),
(4, 2),
(5, 12),
(6, 2),
(7, 5),
(8, 11),
(9, 1), (9, 10),
(10, 7),
(11, 8),
(12, 6),
(13, 8),
(14, 1), (14, 10),
(15, 12),
(16, 5),
(17, 12), (17, 4),
(18, 11),
(19, 10),
(20, 12);

-- ============================================================
-- Article Regions
-- ============================================================
INSERT INTO article_regions (article_id, region_id) VALUES
(1, 1), (1, 2), (1, 3),
(2, 1), (2, 2), (2, 3),
(3, 7),
(4, 2), (4, 3),
(5, 1), (5, 2), (5, 3),
(6, 1), (6, 3),
(7, 1),
(8, 2),
(9, 2), (9, 7),
(10, 1), (10, 2), (10, 3),
(11, 2), (11, 4),
(12, 1), (12, 8),
(13, 7),
(14, 2), (14, 9),
(15, 1), (15, 2),
(16, 1), (16, 2), (16, 3),
(17, 1), (17, 2), (17, 3),
(18, 1), (18, 2), (18, 3),
(19, 1), (19, 2), (19, 3),
(20, 1), (20, 2), (20, 3);

-- ============================================================
-- Newspapers
-- ============================================================
INSERT INTO newspapers (source_id, title, cover_image_url, pdf_url, edition_date) VALUES
(1, 'The New York Times - Daily Edition', 'https://res.cloudinary.com/demo/image/upload/nyt_front.jpg', NULL, CURDATE()),
(2, 'Wall Street Journal - Daily Edition', 'https://res.cloudinary.com/demo/image/upload/wsj_front.jpg', NULL, CURDATE()),
(6, 'Financial Times - Daily Edition', 'https://res.cloudinary.com/demo/image/upload/ft_front.jpg', NULL, CURDATE());

-- ============================================================
-- Radio Streams
-- ============================================================
INSERT INTO radio_streams (source_id, title, description, stream_url, thumbnail_url, is_live, is_high_quality, listener_count, display_order) VALUES
(3, 'BBC World', 'BBC World Service live radio broadcast covering global news 24/7.', 'https://stream.bbc.co.uk/bbc_world_service', 'https://res.cloudinary.com/demo/image/upload/bbc_radio.jpg', 1, 1, 45000, 1),
(4, 'Market Update', 'Reuters live market analysis and financial news commentary.', 'https://stream.reuters.com/live_market', 'https://res.cloudinary.com/demo/image/upload/reuters_radio.jpg', 1, 1, 32000, 2),
(4, 'Reuters Financial Brief', 'Daily financial briefing with in-depth market analysis.', 'https://stream.reuters.com/financial_brief', 'https://res.cloudinary.com/demo/image/upload/reuters_financial.jpg', 1, 0, 28500, 3),
(5, 'Al Jazeera', 'Al Jazeera live English radio service covering Middle East and world news.', 'https://stream.aljazeera.com/live_english', 'https://res.cloudinary.com/demo/image/upload/aj_radio.jpg', 1, 1, 38000, 4),
(6, 'Financial Times', 'FT live business and financial news radio broadcast.', 'https://stream.ft.com/live_radio', 'https://res.cloudinary.com/demo/image/upload/ft_radio.jpg', 1, 1, 19800, 5),
(1, 'The NYT', 'New York Times audio news and analysis.', 'https://stream.nytimes.com/live_audio', 'https://res.cloudinary.com/demo/image/upload/nyt_radio.jpg', 1, 0, 27300, 6);

-- ============================================================
-- Podcasts
-- ============================================================
INSERT INTO podcasts (source_id, title, description, audio_url, thumbnail_url, duration_seconds, episode_number, published_at) VALUES
(3, 'BBC World Service Podcast', 'Daily digest of the most important global news stories.', 'https://podcasts.bbc.co.uk/world_service_daily.mp3', 'https://res.cloudinary.com/demo/image/upload/bbc_podcast.jpg', 1800, 247, NOW() - INTERVAL '1 DAY'),
(8, 'Bloomberg Markets', 'In-depth analysis of global financial markets and economic trends.', 'https://podcasts.bloomberg.com/markets.mp3', 'https://res.cloudinary.com/demo/image/upload/bloomberg_podcast.jpg', 2400, 342, NOW() - INTERVAL '1 DAY'),
(1, 'The Daily - NYT', 'The biggest stories, explained with the help of NYT journalists.', 'https://podcasts.nytimes.com/the_daily.mp3', 'https://res.cloudinary.com/demo/image/upload/nyt_daily_podcast.jpg', 1500, 1859, NOW() - INTERVAL '1 DAY');

-- ============================================================
-- Trending Topics
-- ============================================================
INSERT INTO trending_topics (title, slug, engagement_score, engagement_label, region_id, is_active) VALUES
('Climate Accord 2.0', 'climate-accord-2', 89000, '+19% engagement', NULL, 1),
('Semiconductor Rivalry', 'semiconductor-rivalry', 76000, '+8% engagement', 3, 1),
('AI Regulation Framework', 'ai-regulation-framework', 92000, '+24% engagement', NULL, 1),
('Digital Currency Revolution', 'digital-currency-revolution', 65000, '+12% engagement', 1, 1);

-- ============================================================
-- Trending Topic Articles
-- ============================================================
INSERT INTO trending_topic_articles (topic_id, article_id) VALUES
(1, 9), (1, 14),
(2, 4), (2, 6),
(3, 2),
(4, 12);

-- ============================================================
-- Regional Charts
-- ============================================================
INSERT INTO regional_charts ("rank", title, metric_label, region_id, article_id, chart_date) VALUES
(1, 'Electric Aviation', '+124% interest in Western Europe', 9, NULL, CURDATE()),
(2, 'Remote Work Policy', '+108% interest in North America', 7, 18, CURDATE()),
(3, 'Nuclear Fusion', '+96% interest in East Asia', 11, NULL, CURDATE()),
(1, 'Digital Currency Adoption', '+156% interest in South America', 8, 12, CURDATE()),
(2, 'Green Energy Infrastructure', '+89% interest in South Asia', 12, 14, CURDATE()),
(3, 'AI Healthcare Systems', '+78% interest in Southeast Asia', 13, NULL, CURDATE());

-- ============================================================
-- World Map Insights
-- ============================================================
INSERT INTO world_map_insights (title, description, latitude, longitude, icon_type, article_id, is_active) VALUES
('East Europe: Strategic realignment detected near border', 'Military movements and diplomatic shifts observed in Eastern European border regions.', 50.4501, 30.5234, 'alert', 11, 1),
('Central Asia: New trade agreement signed', 'Central Asian nations finalize comprehensive economic cooperation framework.', 41.2995, 69.2401, 'trade', NULL, 1),
('South Pacific: Climate summit concludes', 'Pacific Island nations reach consensus on climate action and adaptation funding.', -17.7134, 178.0650, 'climate', 9, 1),
('North Africa: Infrastructure development', 'Major infrastructure projects announced across North African nations.', 33.9716, -6.8498, 'development', NULL, 1);

-- ============================================================
-- Compare Coverages
-- ============================================================
INSERT INTO compare_coverages (topic_title, is_active) VALUES
('G7 Climate Agreements', 1),
('AI Regulation Approaches', 1);

INSERT INTO compare_coverage_items (compare_id, source_id, headline, stance_label, article_id) VALUES
(1, 1, 'G7 leaders commit to bold climate action with $100B fund', 'Market-led solutions', 9),
(1, 3, 'G7 climate deal falls short of activist demands', 'Insufficient commitments', 9),
(1, 5, 'Developing nations cautiously welcome G7 climate package', 'Pragmatic optimism', 9),
(2, 4, 'EU proposes comprehensive AI regulatory framework', 'Strict oversight', 2),
(2, 2, 'Tech industry warns AI regulation could stifle innovation', 'Light-touch approach', 2),
(2, 8, 'Markets react to divergent AI policy signals globally', 'Economic impact focus', 2);
```

---

## Git Configuration

### Root .gitignore
**Path:** `.gitignore`  
**Size:** 225 bytes

```ignore list
*.iml
.gradle
/local.properties
/.idea/caches
/.idea/libraries
/.idea/modules.xml
/.idea/workspace.xml
/.idea/navEditor.xml
/.idea/assetWizardSettings.xml
.DS_Store
/build
/captures
.externalNativeBuild
.cxx
local.properties
```

**Ignored Files/Directories:**

| Pattern | Purpose |
|---------|---------|
| `*.iml` | IntelliJ IDEA module files |
| `.gradle/` | Gradle cache directory |
| `/local.properties` | Local machine configuration |
| `/.idea/*` | IDE workspace and configuration |
| `.DS_Store` | macOS metadata |
| `/build` | Build output directory |
| `/captures` | Screenshot captures |
| `.externalNativeBuild` | Native build artifacts |
| `.cxx` | Native C++ build artifacts |

---

### .idea/.gitignore
**Path:** `.idea/.gitignore`  
**Size:** 53 bytes

```ignore list
# Default ignored files
/shelf/
/workspace.xml
```

---

### app/.gitignore
**Path:** `app/.gitignore`  
**Size:** 6 bytes

```ignore list
/build
```

---

## README.md

**Path:** `README.md`  
**Size:** 89 bytes

```markdown
Refer to this link for the  server : https://github.com/krish01info/Media_Matrix_server
```

---

## Gradle Wrapper Files

### gradlew
**Path:** `gradlew`  
**Size:** 8,728 bytes  
**Type:** Shell Script  
**Purpose:** Unix/Linux/macOS Gradle Wrapper executable

---

### gradlew.bat
**Path:** `gradlew.bat`  
**Size:** 2,843 bytes  
**Type:** Windows Batch Script  
**Purpose:** Windows Gradle Wrapper executable

---

## Summary & Key Insights

### Project Type
- **Android Application** with media content aggregation and analysis capabilities

### Technology Stack
- **Frontend:** Android (Java/Kotlin)
- **Build System:** Gradle with Kotlin DSL
- **Database:** PostgreSQL (converted from MySQL)
- **API Integration:** REST APIs (Retrofit + OkHttp)
- **Media Playback:** ExoPlayer (Media3)
- **Image Loading:** Glide
- **Local Database:** Room
- **Authentication:** Google Sign-In with Credential Manager

### Key Features
1. Multi-source news aggregation (8 major news outlets)
2. User authentication and preferences
3. Article categorization and tagging
4. Geographic/regional filtering
5. Trending topics tracking
6. Newspaper digital editions
7. Radio stream integration
8. Podcast content delivery
9. Coverage comparison (bias detection)
10. World map insights and analytics

### Database Architecture
- **22 Tables** with comprehensive relationships
- **Hierarchical data structure** (categories, regions, topics)
- **User engagement tracking** (bookmarks, reading history)
- **Content quality metrics** (truth scores, verification status)
- **Multi-media support** (articles, radio, podcasts, newspapers)

### Project Status
- Active development (last updated 14 hours ago)
- Well-organized structure
- Comprehensive documentation in seed data
- Implements modern Android development practices

---

**End of Report**

Generated: 2026-05-12  
Repository: krish01info/Media_Matrix  
Report Type: Comprehensive with Full Hierarchy and Code Listing
