INSERT INTO tutors (tutor_id, tutor_first_name, tutor_last_name, username, password)
VALUES (0, 'Mark', 'Rasane', '0', '0');
INSERT INTO tutors (tutor_id, tutor_first_name, tutor_last_name, username, password)
VALUES (1, 'Lewis', 'Meckln', '0', '0');
INSERT INTO students (student_id, student_first_name, student_last_name, username, password)
VALUES (0, 'Laura', 'Hieme', '0', '0');
INSERT INTO students (student_id, student_first_name, student_last_name, username, password)
VALUES (1, 'Sam', 'Garrison', '0', '0');
INSERT INTO students (student_id, student_first_name, student_last_name, username, password)
VALUES (2, 'Donald', 'Trump', '0', '0');
INSERT INTO students (student_id, student_first_name, student_last_name, username, password)
VALUES (3, 'Britney', 'Speers', '0', '0');
INSERT INTO students (student_id, student_first_name, student_last_name, username, password)
VALUES (4, 'Ken', 'Ham', '0', '0');
INSERT INTO courses (course_id, course_name, tutor_id, starting_date, finishing_date, active)
VALUES (0, 'COURSE_1', 0, '2015-11-10', '2015-11-12', 'true');
INSERT INTO courses (course_id, course_name, tutor_id, starting_date, finishing_date, active)
VALUES (1, 'COURSE_2', 1, '2015-11-18', '2015-11-19', 'false');
INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (0, 0, -1, 'Empty');
INSERT INTO student_course(student_id, course_id, mark, feedback)
VALUES (3, 1, 4, 'Good performance');
