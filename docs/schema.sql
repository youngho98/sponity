DROP DATABASE IF EXISTS sponity;
CREATE DATABASE sponity;
USE sponity;


CREATE TABLE board
(
  board_id INT           NOT NULL AUTO_INCREMENT,
  club_id  INT           NOT NULL,
  user_id  VARCHAR(40)   NOT NULL,
  title    VARCHAR(40)   NOT NULL,
  content  VARCHAR(4000) NULL    ,
  view_cnt INT           NOT NULL DEFAULT 0,
  reg_date TIMESTAMP     NOT NULL DEFAULT now(),
  img_1    VARCHAR(400)  NULL    ,
  img_2    VARCHAR(400)  NULL    ,
  img_3    VARCHAR(400)  NULL    ,
  PRIMARY KEY (board_id)
);

ALTER TABLE board
  ADD CONSTRAINT UQ_board_id UNIQUE (board_id);

CREATE TABLE club
(
  club_id      INT           NOT NULL AUTO_INCREMENT,
  club_name    VARCHAR(40)   NOT NULL,
  category     VARCHAR(40)   NOT NULL,
  wide_area    VARCHAR(40)   NOT NULL,
  detail_area  VARCHAR(40)   NOT NULL,
  introduction VARCHAR(1000) NULL    ,
  club_img     VARCHAR(400)  NULL    ,
  PRIMARY KEY (club_id)
);

ALTER TABLE club
  ADD CONSTRAINT UQ_club_id UNIQUE (club_id);

CREATE TABLE club_like
(
  like_id INT         NOT NULL AUTO_INCREMENT,
  user_id VARCHAR(40) NOT NULL,
  club_id INT         NOT NULL,
  PRIMARY KEY (like_id)
);

ALTER TABLE club_like
  ADD CONSTRAINT UQ_like_id UNIQUE (like_id);

CREATE TABLE member
(
  member_id INT         NOT NULL AUTO_INCREMENT,
  user_id   VARCHAR(40) NOT NULL,
  club_id   INT         NOT NULL,
  leader    VARCHAR(1)  NOT NULL DEFAULT 'N' COMMENT '그룹장이면 Y, 아니면 N',
  PRIMARY KEY (member_id)
);

ALTER TABLE member
  ADD CONSTRAINT UQ_member_id UNIQUE (member_id);

CREATE TABLE notice
(
  notice_id INT           NOT NULL AUTO_INCREMENT,
  club_id   INT           NOT NULL,
  user_id   VARCHAR(40)   NOT NULL,
  title     VARCHAR(40)   NOT NULL,
  content   VARCHAR(4000) NULL    ,
  view_cnt  INT           NOT NULL DEFAULT 0,
  reg_date  TIMESTAMP     NOT NULL DEFAULT now(),
  img_1     VARCHAR(400)  NULL    ,
  img_2     VARCHAR(400)  NULL    ,
  img_3     VARCHAR(400)  NULL    ,
  PRIMARY KEY (notice_id)
);

ALTER TABLE notice
  ADD CONSTRAINT UQ_notice_id UNIQUE (notice_id);

CREATE TABLE review
(
  review_id INT          NOT NULL AUTO_INCREMENT,
  board_id  INT          NOT NULL,
  user_id   VARCHAR(40)  NOT NULL,
  content   VARCHAR(400) NOT NULL,
  reg_date  TIMESTAMP    NOT NULL DEFAULT now(),
  PRIMARY KEY (review_id)
);

ALTER TABLE review
  ADD CONSTRAINT UQ_review_id UNIQUE (review_id);

CREATE TABLE user
(
  user_id     VARCHAR(40)  NOT NULL,
  password    VARCHAR(300) NOT NULL,
  user_name   VARCHAR(40)  NOT NULL,
  nickname    VARCHAR(40)  NOT NULL,
  email       VARCHAR(40)  NOT NULL,
  wide_area   VARCHAR(40)  NOT NULL,
  detail_area VARCHAR(40)  NOT NULL,
  profile_img VARCHAR(400) NULL    ,
  PRIMARY KEY (user_id)
);

ALTER TABLE user
  ADD CONSTRAINT UQ_user_id UNIQUE (user_id);

ALTER TABLE user
  ADD CONSTRAINT UQ_nickname UNIQUE (nickname);

ALTER TABLE user
  ADD CONSTRAINT UQ_email UNIQUE (email);

ALTER TABLE member
  ADD CONSTRAINT FK_user_TO_member
    FOREIGN KEY (user_id)
    REFERENCES user (user_id) ON DELETE CASCADE;

ALTER TABLE member
  ADD CONSTRAINT FK_club_TO_member
    FOREIGN KEY (club_id)
    REFERENCES club (club_id) ON DELETE CASCADE;

ALTER TABLE board
  ADD CONSTRAINT FK_club_TO_board
    FOREIGN KEY (club_id)
    REFERENCES club (club_id) ON DELETE CASCADE;

ALTER TABLE board
  ADD CONSTRAINT FK_user_TO_board
    FOREIGN KEY (user_id)
    REFERENCES user (user_id) ON DELETE CASCADE;

ALTER TABLE review
  ADD CONSTRAINT FK_user_TO_review
    FOREIGN KEY (user_id)
    REFERENCES user (user_id) ON DELETE CASCADE;

ALTER TABLE review
  ADD CONSTRAINT FK_board_TO_review
    FOREIGN KEY (board_id)
    REFERENCES board (board_id) ON DELETE CASCADE;

ALTER TABLE notice
  ADD CONSTRAINT FK_club_TO_notice
    FOREIGN KEY (club_id)
    REFERENCES club (club_id) ON DELETE CASCADE;

ALTER TABLE notice
  ADD CONSTRAINT FK_user_TO_notice
    FOREIGN KEY (user_id)
    REFERENCES user (user_id) ON DELETE CASCADE;

ALTER TABLE club_like
  ADD CONSTRAINT FK_club_TO_club_like
    FOREIGN KEY (club_id)
    REFERENCES club (club_id) ON DELETE CASCADE;

ALTER TABLE club_like
  ADD CONSTRAINT FK_user_TO_club_like
    FOREIGN KEY (user_id)
    REFERENCES user (user_id) ON DELETE CASCADE;