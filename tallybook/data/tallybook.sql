DROP EVENT IF EXISTS deleteExpireUser;
DROP TRIGGER IF EXISTS activateUser;
DROP TABLE IF EXISTS userinfo;
DROP TABLE IF EXISTS tallybook_bill;
DROP TABLE IF EXISTS nonactivatedUser;
DROP TABLE IF EXISTS userlist;

CREATE TABLE userlist (
	userid INT UNSIGNED AUTO_INCREMENT,
	username VARCHAR(20),
	email VARCHAR(50),
	password VARCHAR(256) NOT NULL,
	appid VARCHAR(256),
	PRIMARY KEY (userid, username, email)
);

CREATE TABLE userinfo (
	userid INT UNSIGNED NOT NULL,
	registerTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (userid),
	FOREIGN KEY (userid) REFERENCES userlist(userid) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE tallybook_bill (
	userid INT UNSIGNED NOT NULL,
	id BIGINT UNSIGNED AUTO_INCREMENT,
	money FLOAT NOT NULL,
	type ENUM("income","outlay") NOT NULL,
	date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
	FOREIGN KEY (userid) REFERENCES userlist(userid) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE nonactivatedUser (
	username VARCHAR(20) NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(256) NOT NULL,
	hash CHAR(40) NOT NULL,
	expire TIMESTAMP NOT NULL,
	confirm Enum('true', 'false') DEFAULT 'false',
	PRIMARY KEY (username)
);

CREATE EVENT deleteExpireUser 
	ON SCHEDULE 
		EVERY 5 MINUTE 
	DO
		DELETE FROM nonactivatedUser WHERE expire < CURRENT_TIMESTAMP OR confirm = 'true';

delimiter |

CREATE TRIGGER activateUser AFTER UPDATE ON nonactivatedUser FOR EACH ROW
	BEGIN
		INSERT INTO userlist (username, email, password, appid) VALUES (OLD.username, OLD.email, OLD.password, SHA(CONCAT(OLD.password, NOW())));			
		INSERT INTO userinfo (userid, registerTime) VALUES (LAST_INSERT_ID(), NOW());			
	END;

|
delimiter ;