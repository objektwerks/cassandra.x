DROP KEYSPACE IF EXISTS query;
CREATE KEYSPACE query WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1 };
CREATE TABLE query.teacher (
    id uuid,
    name text,
    email text,
    modified text,
    PRIMARY KEY (id)
);
CREATE INDEX teacher_name_idx ON query.teacher (name);
CREATE TABLE query.student (
    id uuid,
    name text,
    email text,
    born text,
    modified text,
    PRIMARY KEY (id)
);
CREATE INDEX student_name_idx ON query.student (name);
CREATE TABLE query.grade (
    id uuid,
    student_id uuid,
    grade int,
    started text,
    completed text,
    modified text,
    PRIMARY KEY (id)
);
CREATE INDEX student_id_grade_idx ON query.grade (student_id);
CREATE TABLE query.school (
    id uuid,
    name text,
    website text,
    modified text,
    PRIMARY KEY (id)
);
CREATE INDEX school_name_idx ON query.school (name);
CREATE TABLE query.category (
    name text,
    modified text,
    PRIMARY KEY (name)
);
CREATE TABLE query.course (
    id uuid,
    school_id uuid,
    name text,
    category text,
    website text,
    modified text,
    PRIMARY KEY (id)
);
CREATE INDEX school_id_course_idx ON query.course (school_id);
CREATE INDEX course_name_idx ON query.course (name);
CREATE TABLE query.assignment (
    id uuid,
    student_id uuid,
    grade_id uuid,
    school_id uuid,
    course_id uuid,
    description text,
    assigned text,
    completed text,
    score double,
    modified text,
    PRIMARY KEY (student_id, grade_id, school_id, course_id)
);