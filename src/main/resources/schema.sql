DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS units;
DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    colonist_id VARCHAR(8)   NOT NULL PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL,
    password    VARCHAR(200) NOT NULL
);

CREATE TABLE units
(
    unit_id             VARCHAR(8) PRIMARY KEY,
    colonist_id         VARCHAR(8) NOT NULL,
    image_url           VARCHAR,
    title               VARCHAR(30),
    region              VARCHAR(15),
    description         CLOB,
    cancellation_policy VARCHAR(255),
    price               int,
    score               decimal default 0.0,
    total_reviews       int     default 0,
    foreign key (colonist_id) references users (colonist_id)
);

CREATE TABLE reviews
(
    review_id    UUID PRIMARY KEY,
    unit_id      VARCHAR(8),
    review_stars int not NULL,
    comment      CLOB default '',
    foreign key (unit_id) references units (unit_id)
);
