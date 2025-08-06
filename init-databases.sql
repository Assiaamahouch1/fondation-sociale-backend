-- Initialize databases for all microservices
CREATE DATABASE IF NOT EXISTS fondation_sociale_auth;
CREATE DATABASE IF NOT EXISTS fondation_sociale_user;
CREATE DATABASE IF NOT EXISTS fondation_sociale_content;
CREATE DATABASE IF NOT EXISTS fondation_sociale_social;
CREATE DATABASE IF NOT EXISTS fondation_sociale_notification;
CREATE DATABASE IF NOT EXISTS fondation_sociale_admin;

-- Grant permissions
GRANT ALL PRIVILEGES ON fondation_sociale_auth.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON fondation_sociale_user.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON fondation_sociale_content.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON fondation_sociale_social.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON fondation_sociale_notification.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON fondation_sociale_admin.* TO 'root'@'%';

FLUSH PRIVILEGES;