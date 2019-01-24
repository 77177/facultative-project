--users - tutors
INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Mark', 'Rasane', '0tutor@gmail.com', '$2a$05$1MRWimua526pMo7b7c0ynuxzjBQGkW/nNGD2CfQ7m6UUsNda5Do/m', 'tutor');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Lewis', 'Meckln', '1tutor@gmail.com', '$2a$05$cX8f.TnQczI2tbvsYVNEkegQD.MuHbqWXnTzjzdoTwp/ezyY5wNpC', 'tutor');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Maksimus', 'III', '2tutor@gmail.com', '$2a$05$Aw7dHahBXbmP1RUYWIaBreN1zcAxh.ki5hzIlNfI9NN8VXHwIlkKO', 'tutor');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Andre', 'Koromyslov', '3tutor@gmail.com', '$2a$05$QRlaZ2zwxU3K7tFYO9STTuhMb6UGkbsr8H8BebO5sug3Cv2I3MxDG', 'tutor');

--users - students
INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Laura', 'Hieme', '0student@gmail.com', '$2a$05$GyjRzb11IYENptTcv22BeehLWiufsyynVSJqvyY0wuJFiNZftRD3G', 'student');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Sam', 'Garrison', '1student@gmail.com', '$2a$05$GYIJLW9IhhmzCqOtMlAxZOhDCHxWEA3YD/xwb5tTD1VAk2l1dAOSe', 'student');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Donald', 'Trump', '2student@gmail.com', '$2a$05$Aw7dHahBXbmP1RUYWIaBreN1zcAxh.ki5hzIlNfI9NN8VXHwIlkKO', 'student');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Britney', 'Speers', '3student@gmail.com', '$2a$05$QRlaZ2zwxU3K7tFYO9STTuhMb6UGkbsr8H8BebO5sug3Cv2I3MxDG', 'student');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Ken', 'Ham', '4student@gmail.com', '$2a$05$t3ClColpM159lc2y2CsXYekuDKGyurk5XzidDHiEKSgZ6pKTs2Nca', 'student');

--courses
INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_1', 1, '2019-02-01', '2019-03-01', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_2', 2, '2019-02-01', '2019-03-01', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_3', 3, '2019-02-01', '2019-03-01', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_4', 4, '2019-02-01', '2019-03-01', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_5', 1, '2019-03-01', '2019-05-01', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_6', 2, '2019-03-01', '2019-05-01', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_7', 3, '2019-03-01', '2019-05-01', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_8', 4, '2019-03-01', '2019-06-01', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_9', 1, '2019-04-01', '2019-06-01', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_10', 2, '2019-04-01', '2019-06-01', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_11', 3, '2019-04-01', '2019-06-01', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_12', 4, '2019-04-01', '2019-06-01', 'true');

--student_course

--course 1
INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (3, 1, -1, 'feed back bad');

INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (4, 1, 3, 'feedback good');

INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (5, 1, 2, 'excelent');

INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (6, 1, 0, 'not bad');

INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (7, 1, 7, 'could be better');

--course 2
INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (3, 2, 4, 'Good performance');

INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (7, 2, 5, 'Awful performance');

INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (5, 2, 42, 'Alien');
