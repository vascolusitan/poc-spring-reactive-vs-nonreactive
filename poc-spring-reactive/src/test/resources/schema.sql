CREATE SEQUENCE person_seq
    START WITH 1
    INCREMENT BY 1
    NOMAXVALUE;
-- CREATE TABLE person (
--                         id NUMBER DEFAULT person_seq.NEXTVAL PRIMARY KEY,
--                         name VARCHAR2(255),
--                         age NUMBER
-- );
--select * from person;
--drop table person;
CREATE TABLE person (
                        id INTEGER PRIMARY KEY,
                        name VARCHAR(255),
                        age INTEGER
);
ALTER TABLE person ALTER COLUMN id SET DEFAULT NEXT VALUE FOR person_seq;

