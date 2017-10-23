CREATE TABLE DataStoreDb.customer (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `emailId` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE DataStoreDb.customerFiles (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `createdTime` Timestamp NOT NULL,
  `updatedTime` Timestamp NOT NULL,
  `description` varchar(100) default NULL,
  `fileSize` varchar(45) NOT NULL,
  `path` varchar(100) NOT NULL,
  `customerId` int(11),
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  FOREIGN KEY (customerId) REFERENCES customer(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;