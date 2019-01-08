CREATE TABLE students
(
  student_id         BIGINT AUTO_INCREMENT,
  student_first_name VARCHAR(40),
  student_last_name  VARCHAR(40),
  username           VARCHAR(40),
  password           VARCHAR(40),
  PRIMARY KEY (student_id)
);
CREATE TABLE tutors
(
  tutor_id         BIGINT AUTO_INCREMENT,
  tutor_first_name VARCHAR(40),
  tutor_last_name  VARCHAR(40),
  username         VARCHAR(40),
  password         VARCHAR(40),
  PRIMARY KEY (tutor_id)
);
CREATE TABLE courses
(
  course_id      BIGINT AUTO_INCREMENT,
  course_name    VARCHAR(40),
  tutor_id       INT,
  starting_date  DATE,
  finishing_date DATE,
  active         BOOLEAN,
  PRIMARY KEY (course_id)
);
CREATE TABLE student_course
(
  student_id INT,
  course_id  INT,
  mark       INT,
  feedback   VARCHAR(200)
);