package homeschool

import com.datastax.driver.core.utils.UUIDs

import java.util.concurrent.TimeUnit

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers

class StoreTest extends AnyFunSuite with BeforeAndAfterAll with Matchers:
  val store = new Store("127.0.0.1")
  store.load("/event.model.ddl.cql")
  store.load("/query.model.ddl.cql")
  val eventRepository = new EventRepository(store.asyncSession)
  val queryRepository = new QueryRepository(store.session)

  val teacher = Teacher(id = UUIDs.random(), name = "Barney Rebel", email = "barney.rebel@hb.com")
  val student = Student(id = UUIDs.random(), name = "Fred Flintstone", email = "fred.flintstone@hb.com", born = DateTime.asString)
  val grade = Grade(id = UUIDs.random(), student.id, grade = 1, started = DateTime.asString, completed = DateTime.asString)
  val school = School(id = UUIDs.random(), name = "Rocky Grade School", website = "rockey.gs.com")
  val category = Category(name = "grammer")
  val course = Course(id = UUIDs.random(), school.id, category.name, name = "Basic Grammer", website = "rockey.gs.com/basic_grammer")
  val assignment = Assignment(id = UUIDs.random(), student.id, grade.id, school.id, course.id, description = "sentences", assigned = DateTime.asString, completed = DateTime.asString, score = 100.0)

  override protected def afterAll(): Unit = {
    store.close()
  }

  test("event repository") {
    eventRepository.onTeacher(teacher).get(1, TimeUnit.SECONDS).wasApplied shouldBe true
    eventRepository.onStudent(student).get(1, TimeUnit.SECONDS).wasApplied shouldBe true
    eventRepository.onGrade(grade).get(1, TimeUnit.SECONDS).wasApplied shouldBe true
    eventRepository.onSchool(school).get(1, TimeUnit.SECONDS).wasApplied shouldBe true
    eventRepository.onCategory(category).get(1, TimeUnit.SECONDS).wasApplied shouldBe true
    eventRepository.onCourse(course).get(1, TimeUnit.SECONDS).wasApplied shouldBe true
    eventRepository.onAssignment(assignment).get(1, TimeUnit.SECONDS).wasApplied shouldBe true
  }

  test("query repository") {
    queryRepository.listTeachers().length shouldBe 1
    queryRepository.listStudents().length shouldBe 1
    queryRepository.listGrades(student.id).length shouldBe 1
    queryRepository.listSchools().length shouldBe 1
    queryRepository.listCategories().length shouldBe 1
    queryRepository.listCourses(school.id).length shouldBe 1
    queryRepository.listAssignments(student.id, grade.id, school.id, course.id).length shouldBe 1
    queryRepository.calculateScore(student.id, grade.id, school.id, course.id) shouldBe 100.0
  }