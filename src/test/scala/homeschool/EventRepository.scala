package homeschool

import com.datastax.driver.core.{BatchStatement, BoundStatement, ResultSetFuture, Session}

import scala.collection.JavaConverters.*

class EventRepository(asyncSession: Session):
  val preparedOnTeacherEvent = asyncSession.prepare("insert into event.teacher (id, name, email, modified) values (?, ?, ?, ?)")
  val preparedOnTeacherQuery = asyncSession.prepare("insert into query.teacher (id, name, email, modified) values (?, ?, ?, ?)")

  val preparedOnStudentEvent = asyncSession.prepare("insert into event.student (id, name, email, born, modified) values (?, ?, ?, ?, ?)")
  val preparedOnStudentQuery = asyncSession.prepare("insert into query.student (id, name, email, born, modified) values (?, ?, ?, ?, ?)")

  val preparedOnGradeEvent = asyncSession.prepare("insert into event.grade (id, student_id, grade, started, completed, modified) values (?, ?, ?, ?, ?, ?)")
  val preparedOnGradeQuery = asyncSession.prepare("insert into query.grade (id, student_id, grade, started, completed, modified) values (?, ?, ?, ?, ?, ?)")

  val preparedOnSchoolEvent = asyncSession.prepare("insert into event.school (id, name, website, modified) values (?, ?, ?, ?)")
  val preparedOnSchoolQuery = asyncSession.prepare("insert into query.school (id, name, website, modified) values (?, ?, ?, ?)")

  val preparedOnCategoryEvent = asyncSession.prepare("insert into event.category (name, modified) values (?, ?)")
  val preparedOnCategoryQuery = asyncSession.prepare("insert into query.category (name, modified) values (?, ?)")

  val preparedOnCourseEvent = asyncSession.prepare("insert into event.course (id, school_id, name, category, website, modified) values (?, ?, ?, ?, ?, ?)")
  val preparedOnCourseQuery = asyncSession.prepare("insert into query.course (id, school_id, name, category, website, modified) values (?, ?, ?, ?, ?, ?)")

  val preparedOnAssignmentEvent = asyncSession.prepare("insert into event.assignment (id, student_id, grade_id, school_id, course_id, description, assigned, completed, score, modified) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
  val preparedOnAssignmentQuery = asyncSession.prepare("insert into query.assignment (id, student_id, grade_id, school_id, course_id, description, assigned, completed, score, modified) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")

  def batch(boundStatements: BoundStatement*): ResultSetFuture =
    val batch = new BatchStatement()
    batch.addAll(boundStatements.asJava)
    asyncSession.executeAsync(batch)

  def onTeacher(teacher: Teacher): ResultSetFuture =
    batch(preparedOnTeacherEvent.bind(teacher.id, teacher.name, teacher.email, teacher.modified),
          preparedOnTeacherQuery.bind(teacher.id, teacher.name, teacher.email, teacher.modified))

  def onStudent(student: Student): ResultSetFuture =
    batch(preparedOnStudentEvent.bind(student.id, student.name, student.email, student.born, student.modified),
          preparedOnStudentQuery.bind(student.id, student.name, student.email, student.born, student.modified))

  def onGrade(grade: Grade): ResultSetFuture =
    val gradeAsJavaInteger = grade.grade.asInstanceOf[java.lang.Integer]
    batch(preparedOnGradeEvent.bind(grade.id, grade.studentId, gradeAsJavaInteger, grade.started, grade.completed, grade.modified),
          preparedOnGradeQuery.bind(grade.id, grade.studentId, gradeAsJavaInteger, grade.started, grade.completed, grade.modified))

  def onSchool(school: School): ResultSetFuture =
    batch(preparedOnSchoolEvent.bind(school.id, school.name, school.website, school.modified),
          preparedOnSchoolQuery.bind(school.id, school.name, school.website, school.modified))

  def onCategory(category: Category): ResultSetFuture =
    batch(preparedOnCategoryEvent.bind(category.name, category.modified),
          preparedOnCategoryQuery.bind(category.name, category.modified))

  def onCourse(course: Course): ResultSetFuture =
    batch(preparedOnCourseEvent.bind(course.id, course.schoolId, course.category, course.name, course.website, course.modified),
          preparedOnCourseQuery.bind(course.id, course.schoolId, course.category, course.name, course.website, course.modified))

  def onAssignment(assignment: Assignment): ResultSetFuture =
    val a = assignment
    val scoreAsJavaDouble = a.score.asInstanceOf[java.lang.Double]
    batch(preparedOnAssignmentEvent.bind(a.id, a.studentId, a.gradeId, a.schoolId, a.courseId, a.description, a.assigned, a.completed, scoreAsJavaDouble, a.modified),
          preparedOnAssignmentQuery.bind(a.id, a.studentId, a.gradeId, a.schoolId, a.courseId, a.description, a.assigned, a.completed, scoreAsJavaDouble, a.modified))