--users
INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Mark', 'Rasane', '0tutor@gmail.com', '$2a$05$1MRWimua526pMo7b7c0ynuxzjBQGkW/nNGD2CfQ7m6UUsNda5Do/m', 'tutor');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Lewis', 'Meckln', '1tutor@gmail.com', '$2a$05$cX8f.TnQczI2tbvsYVNEkegQD.MuHbqWXnTzjzdoTwp/ezyY5wNpC', 'tutor');

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
VALUES ('COURSE_1', 1, '2015-11-10', '2015-11-12', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_2', 2, '2015-11-18', '2015-11-19', 'false');

--student_course
INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (1, 1, -1, 'Empty');

INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (3, 1, -1, 'Empty');

INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (4, 2, 4, 'Good performance');
