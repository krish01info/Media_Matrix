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
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,












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
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
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
