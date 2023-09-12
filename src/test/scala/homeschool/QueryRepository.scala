package homeschool

import java.util.UUID

import com.datastax.driver.core.Session

import scala.collection.JavaConverters._
import scala.collection.mutable.Buffer

class QueryRepository(session: Session) {
  val preparedListGrades = session.prepare("select id, grade, started, completed, modified from query.grade where student_id = ?")
  val preparedListCourses = session.prepare("select id, name, category, website, modified from query.course where school_id = ?")
  val preparedListAssignments = session.prepare("select id, description, assigned, completed, score, modified from query.assignment where student_id= ? and grade_id = ? and school_id = ? and course_id = ?")
  val preparedCalculateScore= session.prepare("select sum(score) from query.assignment where student_id = ? and grade_id = ? and school_id = ? and course_id = ?")

  def listTeachers(): Buffer[Teacher] = {
    session.execute("select id, name, email, modified from query.teacher").all.asScala.map { row =>
      Teacher(id = row.getUUID(0), name = row.getString(1), email = row.getString(2), modified = row.getString(3))
    }
  }

  def listStudents(): Buffer[Student] = {
    session.execute("select id, name, email, born, modified from query.student").all.asScala.map { row =>
      Student(id = row.getUUID(0), name = row.getString(1), email = row.getString(2), born = row.getString(3), modified = row.getString(4))
    }
  }

  def listGrades(studentId: UUID): Buffer[Grade] = {
    session.execute(preparedListGrades.bind(studentId)).all.asScala.map { row =>
      Grade(id = row.getUUID(0), studentId, grade = row.getInt(1), started = row.getString(2), completed = row.getString(3), modified = row.getString(4))
    }
  }

  def listSchools(): Buffer[School] = {
    session.execute("select id, name, website, modified from query.school").all.asScala.map { row =>
      School(id = row.getUUID(0), name = row.getString(1), website = row.getString(2), modified = row.getString(3))
    }
  }

  def listCategories(): Buffer[Category] = {
    session.execute("select name, modified from query.category").all.asScala.map { row =>
      Category(name = row.getString(0), modified = row.getString(1))
    }
  }

  def listCourses(schoolId: UUID): Buffer[Course] = {
    session.execute(preparedListCourses.bind(schoolId)).all.asScala.map { row =>
      Course(id = row.getUUID(0), schoolId, category = row.getString(1), name = row.getString(2), website = row.getString(3), modified = row.getString(4))
    }
  }

  def listAssignments(studentId: UUID, gradeId: UUID, schoolId: UUID, courseId: UUID): Buffer[Assignment] = {
    session.execute(preparedListAssignments.bind(studentId, gradeId, schoolId, courseId)).all.asScala.map { row =>
      Assignment(id = row.getUUID(0), studentId, gradeId, schoolId, courseId, description = row.getString(1),
        assigned = row.getString(2), completed = row.getString(3), score = row.getDouble(4), modified = row.getString(5))
    }
  }

  def calculateScore(studentId: UUID, gradeId: UUID, schoolId: UUID, courseId: UUID): Double = {
    session.execute(preparedCalculateScore.bind(studentId, gradeId, schoolId, courseId)).one.getDouble(0)
  }
}