DROP TABLE UserInfo CASCADE CONSTRAINTS;
DROP TABLE Community CASCADE CONSTRAINTS;
DROP SEQUENCE commId_seq;

CREATE TABLE UserInfo ( 
	userId      VARCHAR2(12)	PRIMARY KEY, 
	password	VARCHAR2(12)	NOT NULL,
	name		VARCHAR2(20)	NOT NULL,
	email		VARCHAR2(50),	
 	phone		VARCHAR2(20),
 	commId		NUMBER(4)
);

CREATE TABLE Community ( 
	cId     	NUMBER(4)		PRIMARY KEY, 
	cName		VARCHAR2(20)	NOT NULL,
	descr		VARCHAR2(50),
	startDate	Date,
	chairId		VARCHAR2(12)	
);

ALTER TABLE UserInfo ADD FOREIGN KEY (commId) REFERENCES Community (cId);
ALTER TABLE Community ADD FOREIGN KEY (chairId) REFERENCES UserInfo (userId);

CREATE SEQUENCE commId_seq
	START WITH 10
	INCREMENT BY 10; 
	
INSERT INTO Community VALUES (commId_seq.NEXTVAL, 'Cinema Paradiso', '영화를 사랑하는 사람들의 모임', SYSDATE, null);
INSERT INTO Community VALUES (commId_seq.NEXTVAL, 'A.R.M.Y', 'BTS 팬클럽', SYSDATE, null);
INSERT INTO Community VALUES (commId_seq.NEXTVAL, 'Aero Bike', '산악자전거 동호회', SYSDATE, null);
INSERT INTO Community VALUES (commId_seq.NEXTVAL, 'ILoveDBP', 'Database Programming Study Group', SYSDATE, null);

INSERT INTO UserInfo VALUES ('admin', 'admin', '시스템 관리자', 'admin@dongduk.ac.kr', '02-940-9999', null);
INSERT INTO UserInfo VALUES ('movieMan', 'movie', '이영화', 'young99@gmail.com', '010-1234-5678', 10);
INSERT INTO UserInfo VALUES ('mina', 'mina123', '김미나', 'mnkim@naver.com', '010-6677-2233', 40);
INSERT INTO UserInfo VALUES ('rizzi', 'rizzi123', 'James Rizzi', 'james@gmail.com', '520-342-5566', 30);
INSERT INTO UserInfo VALUES ('barnes', 'barnes123', 'Julian Barnes', 'barnes@hotmail.com', '778-443-1532', 10);

UPDATE Community SET chairId = 'movieMan' WHERE cid = 10;
COMMIT;
