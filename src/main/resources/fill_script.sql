--users - tutors
INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Mark', 'Rasane', '0tutor@gmail.com', '$2a$05$1MRWimua526pMo7b7c0ynuxzjBQGkW/nNGD2CfQ7m6UUsNda5Do/m', 'tutor');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Lewis', 'Meckln', '1tutor@gmail.com', '$2a$05$cX8f.TnQczI2tbvsYVNEkegQD.MuHbqWXnTzjzdoTwp/ezyY5wNpC', 'tutor');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Maksimus', 'III', '2tutor@gmail.com', '$2a$05$Aw7dHahBXbmP1RUYWIaBreN1zcAxh.ki5hzIlNfI9NN8VXHwIlkKO', 'tutor');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Andre', 'Koromyslov', '3tutor@gmail.com', '$2a$05$QRlaZ2zwxU3K7tFYO9STTuhMb6UGkbsr8H8BebO5sug3Cv2I3MxDG',
        'tutor');

--users - students
INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Laura', 'Hieme', '0student@gmail.com', '$2a$05$GyjRzb11IYENptTcv22BeehLWiufsyynVSJqvyY0wuJFiNZftRD3G',
        'student');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Sam', 'Garrison', '1student@gmail.com', '$2a$05$GYIJLW9IhhmzCqOtMlAxZOhDCHxWEA3YD/xwb5tTD1VAk2l1dAOSe',
        'student');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Donald', 'Trump', '2student@gmail.com', '$2a$05$Aw7dHahBXbmP1RUYWIaBreN1zcAxh.ki5hzIlNfI9NN8VXHwIlkKO',
        'student');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Britney', 'Speers', '3student@gmail.com', '$2a$05$QRlaZ2zwxU3K7tFYO9STTuhMb6UGkbsr8H8BebO5sug3Cv2I3MxDG',
        'student');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Ken', 'Ham', '4student@gmail.com', '$2a$05$t3ClColpM159lc2y2CsXYekuDKGyurk5XzidDHiEKSgZ6pKTs2Nca', 'student');

--courses
INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('Calculus 101', 1, '2019-02-01', '2019-04-01', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('History 433', 2, '2020-04-11', '2020-07-12', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('Java Core 101', 3, '2019-02-01', '2019-03-21', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('Chemistry 110', 4, '2021-01-11',   '2021-04-15', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('Literature 333', 1, '2019-03-01', '2019-05-19', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('Physics 101', 2, '2019-03-23', '2019-05-24', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('Solfeggio 2234', 3, '2019-02-24', '2019-05-27', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('Civil Engineering 7737', 4, '2019-03-01', '2019-06-02', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('Biology 1324', 1, '2019-04-11', '2019-06-04', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('Economics 2134', 2, '2019-04-21', '2019-06-11', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('Statistics 3987', 3, '2023-02-12', '2023-06-17', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('Electronics', 4, '2020-04-26', '2020-07-23', 'true');

--student_course

--course 1
INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (3, 1, -1, 'feed back bad');

INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (4, 1, 3, 'feedback good');

INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (5, 1, 2, 'excellent');

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
