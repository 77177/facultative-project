CREATE TABLE students (
	student_id	INT NOT NULL UNIQUE,
	student_first_name	VARCHAR ( 40 ),
	student_last_name	VARCHAR ( 40 ),
	login VARCHAR ( 40 ),
	password VARCHAR ( 40 ),
	PRIMARY KEY(student_id)
);

CREATE TABLE tutors (
	tutor_id	INT NOT NULL UNIQUE,
	tutor_first_name	VARCHAR (40),
	tutor_last_name	VARCHAR (40),
	login VARCHAR (40),
	password VARCHAR (40),
	PRIMARY KEY(tutor_id)
);

CREATE TABLE courses (
	course_id	INT NOT NULL UNIQUE,
	course_name	VARCHAR (40),
	tutor_id	INT,
	starting_date	DATE,
	finishing_date	DATE,
	active	boolean,
	FOREIGN KEY(tutor_id) REFERENCES tutors,
	PRIMARY KEY(course_id)
);

CREATE TABLE student_course (
	student_id	INT,
	course_id	INT,
	mark	INT,
	feedback	VARCHAR (200),
	FOREIGN KEY(student_id) REFERENCES students,
	FOREIGN KEY(course_id) REFERENCES courses
);