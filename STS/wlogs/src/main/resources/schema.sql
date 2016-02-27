CREATE TABLE IF NOT EXISTS wlogs_users (
  username VARCHAR(20) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled TINYINT NOT NULL DEFAULT 1,
  email VARCHAR(255) NOT NULL,
  PRIMARY KEY (username)
);
CREATE TABLE IF NOT EXISTS wlogs_user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(20) NOT NULL,
  role varchar(50) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY unique_username_role (role, username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES wlogs_users (username)
);