INSERT INTO tutors (tutor_first_name, tutor_last_name, username, password)
VALUES ('Mark', 'Rasane', '0', '0');
INSERT INTO tutors (tutor_first_name, tutor_last_name, username, password)
VALUES ('Lewis', 'Meckln', '0', '0');
INSERT INTO students (student_first_name, student_last_name, username, password)
VALUES ('Laura', 'Hieme', '0', '0');
INSERT INTO students (student_first_name, student_last_name, username, password)
VALUES ('Sam', 'Garrison', '0', '0');
INSERT INTO students (student_first_name, student_last_name, username, password)
VALUES ('Donald', 'Trump', '0', '0');
INSERT INTO students (student_first_name, student_last_name, username, password)
VALUES ('Britney', 'Speers', '0', '0');
INSERT INTO students (student_first_name, student_last_name, username, password)
VALUES ('Ken', 'Ham', '0', '0');
INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_1', 0, '2015-11-10', '2015-11-12', 'true');
INSERT INTO courses (course_name, tutor_id, starting_date, finishing_date, active)
VALUES ('COURSE_2', 1, '2015-11-18', '2015-11-19', 'false');
INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (0, 0, -1, 'Empty');
INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (3, 1, 4, 'Good performance');
