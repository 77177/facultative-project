--tutors
INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Mark', 'Rasane', '0tutor@gmail.com', '0', 'tutor');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Lewis', 'Meckln', '1tutor@gmail.com', '1', 'tutor');

--students
INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Laura', 'Hieme', '0student@gmail.com', '0', 'student');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Sam', 'Garrison', '1student@gmail.com', '1', 'student');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Donald', 'Trump', '2student@gmail.com', '2', 'student');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Britney', 'Speers', '3student@gmail.com', '3', 'student');

INSERT INTO users (first_name, last_name, email, password, position)
VALUES ('Ken', 'Ham', '4student@gmail.com', '4', 'student');

--courses
INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_1', 1, '2015-11-10', '2015-11-12', 'true');

INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_2', 2, '2015-11-18', '2015-11-19', 'false');


--srudent_course
INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (3, 1, 4, 'Good performance');

INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (4, 1, -1, 'Empty');

