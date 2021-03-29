DROP KEYSPACE IF EXISTS event;
CREATE KEYSPACE event WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1 };
CREATE TABLE event.teacher (
    id uuid,
    name text,
    email text,
    modified text,
    PRIMARY KEY (id)
);
CREATE TABLE event.student (
    id uuid,
    name text,
    email text,
    born text,
    modified text,
    PRIMARY KEY (id)
);
CREATE TABLE event.grade (
    id uuid,
    student_id uuid,
    grade int,
    started text,
    completed text,
    modified text,
    PRIMARY KEY (id)
);
CREATE TABLE event.school (
    id uuid,
    name text,
    website text,
    modified text,
    PRIMARY KEY (id)
);
CREATE TABLE event.category (
    name text,
    modified text,
    PRIMARY KEY (name)
);
CREATE TABLE event.course (
    id uuid,
    school_id uuid,
    name text,
    category text,
    website text,
    modified text,
    PRIMARY KEY (id)
);
CREATE TABLE event.assignment (
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
    PRIMARY KEY (id)
);