CREATE TABLE users
(
  id         BIGINT AUTO_INCREMENT,
  first_name VARCHAR(40),
  last_name  VARCHAR(40),
  email      VARCHAR(40) UNIQUE,
  password   VARCHAR(40),
  position   VARCHAR(40),
  primary key (id)
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