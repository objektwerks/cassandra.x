package homeschool

import java.time.format.DateTimeFormatter
import java.time.{LocalDateTime, ZoneOffset}
import java.util.UUID

import com.datastax.driver.core.AtomicMonotonicTimestampGenerator

object DateTime:
  private val timestamp = new AtomicMonotonicTimestampGenerator()
  private val formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss")
  def asString: String = LocalDateTime.ofEpochSecond(timestamp.next(), 0, ZoneOffset.UTC).format(formatter)
  def asDateTime(dateTime: String): LocalDateTime = LocalDateTime.parse(dateTime, formatter)

sealed trait Entity

final case class Teacher(id: UUID,
                         name: String,
                         email: String,
                         modified: String = DateTime.asString) extends Entity

final case class Student(id: UUID,
                         name: String,
                         email: String,
                         born: String,
                         modified: String = DateTime.asString) extends Entity

final case class Grade(id: UUID,
                       studentId: UUID,
                       grade: Int,
                       started: String = DateTime.asString,
                       completed: String = DateTime.asString,
                       modified: String = DateTime.asString) extends Entity

final case class School(id: UUID,
                        name: String,
                        website: String,
                        modified: String = DateTime.asString) extends Entity

final case class Category(name: String,
                          modified: String = DateTime.asString) extends Entity

final case class Course(id: UUID,
                        schoolId: UUID,
                        category: String,
                        name: String,
                        website: String,
                        modified: String = DateTime.asString) extends Entity

final case class Assignment(id: UUID,
                            studentId: UUID,
                            gradeId: UUID,
                            schoolId: UUID,
                            courseId: UUID,
                            description: String,
                            assigned: String = DateTime.asString,
                            completed: String = DateTime.asString,
                            score: Double = 0.0,
                            modified: String = DateTime.asString) extends Entity