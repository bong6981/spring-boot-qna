INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES ('1', 'javajigi', 'test', '자바지기', 'javajigi@slipp.net')
INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES ('2', 'sanjigi', 'test', '산지기', 'sanjigi@slipp.net')
INSERT INTO USER (ID, USER_ID, PASSWORD, NAME, EMAIL) VALUES ('3', 'hi', 'hi', 'hi', 'hi@slipp.net')

INSERT INTO QUESTION (ID, WRITER_ID, TITLE, CONTENTS, CREATED_DATE_TIME) VALUES ('1', '3', 'hello', 'bye', '2021-03-22 17:03:01.106461')
INSERT INTO QUESTION (ID, WRITER_ID, TITLE, CONTENTS, CREATED_DATE_TIME) VALUES ('2', '1', 'hoi', 'dui', '2021-03-20 16:03:03.106461')

INSERT INTO ANSWER (ID, CONTENTS, CREATED_DATE_TIME, QUESTION_ID, WRITER_ID) VALUES  ('1', 'This is an answer', '2021-03-20 16:03:03.106461', '2', '1')
