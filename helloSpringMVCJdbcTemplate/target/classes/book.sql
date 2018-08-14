create table BOOK
(
	ID INTEGER NOT NULL PRIMARY KEY,
	NAME varchar2(100) NOT NULL,
	PUBLISH_HOUSE varchar2(50) NOT NULL,
	PUBLISH_DATE varchar2(20) NOT NULL,
	ORIG_AUTHOR  varchar2(50),
	ORIG_BOOK_NAME varchar2(100),
	LANGUAGE varchar2(100),
	ISBN varchar2(50),
	BUY_DATE varchar2(20),
	BUY_STORE varchar2(50),
	RECORD_DATE date default SYSDATE
);



CREATE SEQUENCE BOOK_ID_SEQ
  START WITH 1
  INCREMENT BY 1
  CACHE 100;
  

  
CREATE or REPLACE trigger BOOK_TRIGGER
	before insert on BOOK
	for each row
	begin
  		select BOOK_ID_SEQ.nextval into :new.ID from dual;
	end;
	
insert into book(name,
                 publish_house,
                 publish_date,
                 orig_author,
                 orig_book_name,
                 language,
                 isbn,
                 buy_date,
                 buy_store) values
                 ('Spring 實戰 (第4版)',
                 '人民郵電出版社','2016-04-01',
                 'Craig Walls',
                 'Spring In Action FOURTH EDITION',
                 '簡體中文',
                 '9787115417305',
                 '2017-09-01',
                 '天瓏書局')

CREATE table CATEGORY
(
	ID INTEGER NOT NULL PRIMARY KEY,
	NAME varchar2(50) NOT NULL,
	CATG_TYPE varchar2(50) NOT NULL,
	RECORD_DATE date default SYSDATE
)
CREATE SEQUENCE CATEGORY_ID_SEQ
  START WITH 1
  INCREMENT BY 1
  CACHE 100;
CREATE or REPLACE trigger CATEGORY_TRIGGER
	before insert on CATEGORY
	for each row
	begin
  		select CATEGORY_ID_SEQ.nextval into :new.ID from dual;
	end;

CREATE TABLE BOOK_CATEGORY
(
	BOOK_ID INTEGER NOT NULL,
	CATEGORY_ID INTEGER NOT NULL,
	PRIMARY KEY (BOOK_ID,CATEGORY_ID)
)

alter table BOOK_CATEGORY ADD constraint BOOK_ID_FK foreign key(BOOK_ID) references BOOK(ID);
alter table BOOK_CATEGORY ADD constraint CATG_ID_FK foreign key(CATEGORY_ID) references CATEGORY(ID);
insert into BOOK_CATEGORY values(2,1)