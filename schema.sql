CREATE TABLE IF NOT EXISTS `Room` (
    `id` VARCHAR(255) NOT NULL ,
    `name` VARCHAR(255) NOT NULL ,
    `type_id` int NOT NULL ,
    `floor` int NOT NULL ,
    `desc` TEXT NOT NULL ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS `RoomType` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL ,
    `std_price` DOUBLE NOT NULL ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS `Guest` (
    `id_num` VARCHAR(255) NOT NULL ,
    `name` VARCHAR(255) NOT NULL ,
    `phone` VARCHAR(255) ,
    `sex` ENUM('男', '女', '保密') ,
    `age` INTEGER ,
    PRIMARY KEY (`id_num`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS `Order` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `room_id` VARCHAR(255) NOT NULL ,
    `guest_id` VARCHAR(255) NOT NULL ,
    `check_in_time` DATETIME ,
    `check_out_time` DATETIME ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS `Admin` (
    `id` VARCHAR(255) NOT NULL ,
    `name` VARCHAR(255) NOT NULL,
    `pwd` VARCHAR(255) NOT NULL ,
    `role_id` INTEGER NOT NULL ,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;


CREATE TABLE IF NOT EXISTS `AdminRole` (
    `id` INTEGER NOT NULL ,
    `name` VARCHAR(255) NOT NULL ,
    `grade` INTEGER NOT NULL
)
