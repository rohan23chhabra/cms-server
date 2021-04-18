package org.cms.server.student;

import java.util.List;
import org.cms.core.course.Course;
import org.cms.core.student.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

	private static final String POST_STUDENT_SUCCESS = "Student added successfully with id = %s";
	private static final String UPDATE_STUDENT_FAILED = "Student to be updated with id %s doesn't exist in our database records";
	private static final String UPDATE_STUDENT_SUCCESS = "Student with id %s is successfully updated";
	private static final String DELETE_STUDENT_FAILED = "Student to be deleted with id %s doesn't exist in our database records";
	private static final String DELETE_STUDENT_SUCCESS = "Student with id %s is successfully deleted";
	private static final String SUBSCRIBE_FAILED = "Student with id %s couldn't be subscribed to course with id %s";
	private static final String SUBSCRIBE_SUCCESS = "Student with id %s is successfully subscribed to course with id %s";

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@RequestMapping("/api/students")
	public List<Student> getAllStudents() {
		return studentService.getAllStudents();
	}

	@RequestMapping("/api/students/{id}")
	public ResponseEntity<Student> getStudent(@PathVariable String id) {
		Student fetchedStudent = studentService.getStudent(id);
		if (fetchedStudent != null) {
			return ResponseEntity.ok(fetchedStudent);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@RequestMapping(method = RequestMethod.POST, value = "/api/students")
	public ResponseEntity<String> addStudent(@RequestBody Student student) {
		String idSaved = studentService.addStudent(student);
		return ResponseEntity.ok(String.format(POST_STUDENT_SUCCESS, idSaved));
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/api/students/{id}")
	public ResponseEntity<String> addStudent(@RequestBody Student student, @PathVariable String id) {
		boolean isSuccessful = studentService.updateStudent(id, student);
		if (!isSuccessful) {
			return new ResponseEntity<>(String.format(UPDATE_STUDENT_FAILED, id), HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(String.format(UPDATE_STUDENT_SUCCESS, id));
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/api/students/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable String id) {
		boolean isSuccessful = studentService.deleteStudent(id);
		if (!isSuccessful) {
			return new ResponseEntity<>(String.format(DELETE_STUDENT_FAILED, id), HttpStatus.NOT_FOUND);
		}

		return ResponseEntity.ok(String.format(DELETE_STUDENT_SUCCESS, id));
	}

	@RequestMapping(method = RequestMethod.GET, value = "/api/students/{id}/courses")
	public List<Course> getCoursesForStudent(@PathVariable String id) {
		return studentService.getCoursesForStudent(id);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/api/students/{student_id}/courses/{course_id}")
	public ResponseEntity<String> subscribe(@PathVariable String student_id, @PathVariable String course_id) {
		boolean isSuccessful = studentService.subscribe(student_id, course_id);
		if (!isSuccessful) {
			return new ResponseEntity<>(String.format(SUBSCRIBE_FAILED, student_id, course_id), HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(String.format(SUBSCRIBE_SUCCESS, student_id, course_id));
	}
}
