CREATE TABLE boards (
  id          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  address     VARCHAR(20)      NOT NULL,
  description VARCHAR(1000)    NOT NULL,
  name        VARCHAR(100)     NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY address (address)
);

CREATE TABLE posts (
  id        INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  thread_id INT(11) UNSIGNED NOT NULL,
  post_time VARCHAR(100)     NOT NULL,
  author    VARCHAR(100)     NOT NULL,
  message   VARCHAR(1000)    NOT NULL,
  PRIMARY KEY (id),
  KEY post_to_thread (thread_id),
  CONSTRAINT post_to_thread FOREIGN KEY (thread_id) REFERENCES threads (id)
);

CREATE TABLE threads (
  id       INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  board_id INT(11) UNSIGNED NOT NULL,
  PRIMARY KEY (id),
  KEY threads_to_boards (board_id),
  CONSTRAINT threads_to_boards FOREIGN KEY (board_id) REFERENCES boards (id)
);