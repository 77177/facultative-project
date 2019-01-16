DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS courses;
DROP TABLE IF EXISTS student_course;

CREATE TABLE users
(
  id         BIGINT AUTO_INCREMENT,
  first_name VARCHAR(40),
  last_name  VARCHAR(40),
  email      VARCHAR(40) UNIQUE,
  password   VARCHAR(100),
  position   VARCHAR(40),
  primary key (id)
);

CREATE TABLE courses
(
  course_id      BIGINT AUTO_INCREMENT,
  course_name    VARCHAR(40) UNIQUE,
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
  feedback   VARCHAR(200),
  PRIMARY KEY (course_id,student_id),
  FOREIGN KEY (student_id) references users(id),
  FOREIGN KEY (course_id) references courses(course_id)
);