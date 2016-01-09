DROP TABLE IF EXISTS message;
CREATE TABLE message (
	id INT UNSIGNED AUTO_INCREMENT,
	subject VARCHAR(50) NOT NULL,
	content	TEXT NOT NULL,
	`from` VARCHAR(30) NOT NULL,
	`to` VARCHAR(30) NOT NULL,
	`folder` VARCHAR(10) NOT NULL,
	flag ENUM('answered', 'deleted', 'recent', 'seen'),
	datetime TIMESTAMP NULL,
	PRIMARY KEY (id)
);


