Homeschool
----------
>Homeschool domain model using Cassandra and CQRS/ES.

Object Model
------------
* Teacher (id, name, email, modified)
* Student (id, name, email, born, modified)
* Grade (id, studentId, grade, started, completed, modified)
* School (id, name, website, modified)
* Category (name, modified)
* Course (id, schoolId, name, category, website, modified)
* Assignment (id, studentId, gradeId, schoolId, courseId, description, assigned, completed, score, modified)

Relational Model
----------------
* Teacher
* Student 1 ---> * Grade
* School 1 ---> * Course 1 ---> 1 Category
* Assignement 1 ---> 1 Student | Grade | School | Course

Event Model
-----------
* Teachers (key = [id], columns = name, email, date_time)
* Students (key = [id], columns = name, born, email, date_time)
* Grades (key = [id], columns = student_id, grade, started, completed, date_time)
* Schools (key = [id], columns = name, website, date_time)
* Categories (key = [id], columns = name, date_time)
* Course (key = [id], columns = school_id, name, category, website, date_time)
* Assignment (key = [id], columns = student_id, grade_id, school_id, course_id, description, assigned, completed, score, date_time)

Query Model
-----------
* Teachers (key = [id], columns = name, email, modified)
* Students (key = [id], columns = name, born, email, modified)
* Grades (key = [id, student_id], columns = grade, started, completed, modified)
* Schools (key = [id], columns = name, website, modified)
* Categories (key = [name], columns = modified)
* Course (key = [id, school_id], columns = name, category, website, modified)
* Assignment (key = [id, student_id, grade_id, school_id, course_id], columns = description, assigned, completed, score, modified)

Commands
--------
* Change[T]

Events
------
* Changed[T]

Queries
-------
* List[T]
* Calculate Score By Student, Grade, School and Course across Assignments

Scenarios
---------
1. Source * --- Command ---> 1 CommandHandler 1 --- Event ---> 1 EventHandler
2. Source * --- Query ---> 1 QueryHandler 1 --- Result ---> 1 ResultHandler 1 --- Result ---> * Source
3. Source * --- *Handler * --- Failure ---> * Source

Service
-------
* brew services **start** cassandra | brew services **stop** cassandra

Test
----
1. sbt clean test