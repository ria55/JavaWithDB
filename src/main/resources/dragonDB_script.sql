DROP DATABASE IF EXISTS dragonDB;

CREATE DATABASE /* IF NOT EXISTS */ dragonDB;

USE dragonDB;

/* DROP TABLE IF EXISTS dragon */

CREATE TABLE /* IF NOT EXISTS */ dragon (
    id INT UNSIGNED AUTO_INCREMENT,
    unique_name VARCHAR(100) UNIQUE,
    dragon_text LONGTEXT,
    rarity ENUM('common','rare','very rare','epic','legendary','heroic'),
    design LONGBLOB,
    PRIMARY KEY (id)
);

/* DROP TABLE IF EXISTS element */

CREATE TABLE /* IF NOT EXISTS */ element (
    element_name VARCHAR(50),
    icon_img BLOB,
    PRIMARY KEY (element_name)
);

/* DROP TABLE IF EXISTS dragons_element */

CREATE TABLE /* IF NOT EXISTS */ dragons_element (
    id INT UNSIGNED AUTO_INCREMENT,
    dragon_id INT UNSIGNED,
    element_name VARCHAR(50),
    PRIMARY KEY (id),
    FOREIGN KEY (dragon_id) REFERENCES dragon(id),
    FOREIGN KEY (element_name) REFERENCES element(element_name)
);
