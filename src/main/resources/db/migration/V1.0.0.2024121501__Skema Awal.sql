CREATE TABLE event_publication (
  completion_date DATETIME(6),
  publication_date DATETIME(6),
  id binary(16) NOT NULL,
  event_type VARCHAR(255),
  listener_id VARCHAR(255),
  serialized_event VARCHAR(255),
  PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE event_publication_archive (
  completion_date DATETIME(6),
  publication_date DATETIME(6),
  id binary(16) NOT NULL,
  event_type VARCHAR(255),
  listener_id VARCHAR(255),
  serialized_event VARCHAR(255),
  PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE server (
  status TINYINT CHECK (status BETWEEN 0 AND 1),
  id BIGINT NOT NULL,
  image_url VARCHAR(255),
  ip_address VARCHAR(255) NOT NULL,
  memory VARCHAR(255),
  name VARCHAR(255),
  type VARCHAR(255),
  PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE server_seq (next_val BIGINT) engine = InnoDB;

INSERT INTO
  server_seq
VALUES
  (1);

ALTER TABLE server
ADD CONSTRAINT UK96tx503up4941ibvsnhh8itdi UNIQUE (ip_address);