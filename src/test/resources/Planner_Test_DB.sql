
use planner_test;

SET foreign_key_checks = 0; -- 외래키 참조 확인 해제 --

DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS `planner`;
DROP TABLE IF EXISTS `plannerlike`;
DROP TABLE IF EXISTS `review`;
DROP TABLE IF EXISTS `spotlike`;
DROP TABLE IF EXISTS `review_comment`;
DROP TABLE IF EXISTS `reviewlike`;

SET foreign_key_checks = 1; -- 외래키 참조 확인 다시 설정 --

create table account (
account_id	int primary key	not null auto_increment,
email varchar(40) not null unique,
password varchar(255) not null,
name varchar(10) not null,
nickname varchar(25) not null,
phone varchar(11) not null,
image varchar(255) not null,
create_date datetime not null,
update_date datetime not null);

create table planner(
planner_id int primary key not null auto_increment,
account_id int not null,
title varchar(255) not null,
plan_date_start datetime not null,
plan_date_end datetime not null,
member_count int not null default 1,
member text null,
plan text not null,
like_count int default 0,
create_date datetime not null,
update_date datetime not null,
foreign key(account_id) references account(account_id) ON DELETE CASCADE);

create table plannerlike(
like_id int primary key not null auto_increment,
account_id int not null,
planner_id int not null,
like_date datetime not null,
foreign key(account_id) references `account`(account_id) ON DELETE CASCADE,
foreign key(planner_id) references `planner`(planner_id) ON DELETE CASCADE);

create table review(
review_id int primary key not null auto_increment,
planner_id int not null,
title varchar(250) not null,
content text not null,
writer varchar(25) not null,
writer_id int not null,
like_count int not null default 0,
create_date datetime not null,
update_date datetime not null,
foreign key(planner_id) references planner(planner_id) ON DELETE CASCADE,
foreign key(writer_id) references account(account_id) ON DELETE CASCADE);

create table review_comment(
comment_id int primary key not null auto_increment,
review_id int not null,
writer int not null,
content text not null,
create_date datetime not null,
update_date datetime not null,
foreign key(review_id) references review(review_id) ON DELETE CASCADE,
foreign key(writer) references account(account_id) ON DELETE CASCADE);

create table reviewlike(
like_id int primary key not null auto_increment,
account_id int not null,
review_id int not null,
foreign key(account_id) references account(account_id) ON DELETE CASCADE,
foreign key(review_id) references review(review_id) ON DELETE CASCADE);

-- create table spot(
-- spot_id int primary key not null auto_increment,
-- spot_name varchar(50) not null,
-- spot_image text,
-- country_name varchar(50) not null,
-- city_name varchar(50) not null default "",
-- detail text,
-- like_count int);

create table spotlike(
like_id int primary key not null auto_increment,
account_id int not null,
content_id int not null,
like_date datetime not null,
foreign key(account_id) references account(account_id) ON DELETE CASCADE);

-- create table spotlike(
-- like_id int primary key not null auto_increment,
-- account_id int not null,
-- spot_id int not null,
-- like_date datetime not null,
-- foreign key(account_id) references account(account_id),
-- foreign key(spot_id) references spot(spot_id));


INSERT INTO `account` (`account_id`, `email`, `password`, `name`, `nickname`, `phone`, `image`, `create_date`, `update_date`) VALUES (1,'test@naver.com','$2a$10$olP0VFVfEGHpg2AlhnS/Ze5kcXTnvGYmnsbBpBC2Asx7g/Z58p/TO','test','test','01012345678','Account\\user.png','2022-10-29 20:42:02','2022-10-29 20:42:02');

INSERT INTO `planner` (`planner_id`, `account_id`, `title`, `plan_date_start`, `plan_date_end`, `member_count`, `member`, `plan`, `like_count`, `create_date`, `update_date`) VALUES (1,1,'초보여행','2022-08-10 00:00:00','2022-08-12 00:00:00',1,'{}','{}',0,'2022-11-04 01:55:56','2022-11-04 01:55:56');

INSERT INTO `plannerlike` (`like_id`, `account_id`, `planner_id`, `like_date`) VALUES (1,1,1,'2022-11-09 21:11:49');

INSERT INTO `spotlike` (`like_id`, `account_id`, `content_id`, `like_date`) VALUES ('1', '1', '2733967', now());

INSERT INTO `review` (`review_id`, `planner_id`, `title`, `content`, `writer`, `writer_id`, `like_count`, `create_date`, `update_date`) VALUES (1,1,'여행후기','재미있었다링','test', 1, 0,'2022-11-09 21:14:58','2022-11-09 21:14:58');
