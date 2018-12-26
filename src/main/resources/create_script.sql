CREATE TABLE students (
	student_id	integer NOT NULL UNIQUE,
	student_first_name	varchar ( 40 ),
	student_last_name	varchar ( 40 ),
	login varchar ( 40 ),
	password varchar ( 40 ),
	PRIMARY KEY(student_id)
);

CREATE TABLE tutors (
	tutor_id	integer NOT NULL UNIQUE,
	tutor_first_name	varchar ( 40 ),
	tutor_last_name	varchar ( 40 ),
	login varchar ( 40 ),
	password varchar ( 40 ),
	PRIMARY KEY(tutor_id)
);

CREATE TABLE courses (
	course_id	integer NOT NULL UNIQUE,
	course_name	varchar ( 40 ),
	tutor_id	integer,
	starting_date	date,
	finishing_date	date,
	active	boolean,
	FOREIGN KEY(tutor_id) REFERENCES tutors,
	PRIMARY KEY(course_id)
);

CREATE TABLE student_course (
	student_id	integer,
	course_id	integer,
	mark	integer ( 1 ),
	feedback	varchar ( 200 ),
	FOREIGN KEY(student_id) REFERENCES students,
	FOREIGN KEY(course_id) REFERENCES courses
);