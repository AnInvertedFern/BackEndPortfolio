CREATE TABLE `authorities` (
  `username` varchar(255) NOT NULL,
  `authority` varchar(255) NOT NULL,
  UNIQUE KEY `authorities_idx_1` (`username`, `authority`),
  CONSTRAINT `authorities_ibfk_1`
  FOREIGN KEY (`username`)
  REFERENCES `users` (`username`)
) 

CREATE TABLE `users` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `enabled` boolean NOT NULL,
  PRIMARY KEY (`username`)
)