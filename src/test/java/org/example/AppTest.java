package org.example;

import domain.Student;
import org.junit.*;
import repository.StudentRepository;
import repository.StudentXMLRepository;
import service.Service;
import validation.StudentValidator;
import validation.ValidationException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    private StudentValidator studentValidator;
    private StudentRepository studentRepository;

    @Before
    public void setup() {
        this.studentValidator = new StudentValidator();
        this.studentRepository = new StudentRepository(studentValidator);
    }

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue( true);
    }

    @Test
    public void shouldAnswerWithFalse() {
        assertFalse(false);
    }

    @Test
    public void testSaveStudent_entitySavedInTheRepositoryContainer() {
        Student testStudent = new Student("1543", "test1", 937);
        studentRepository.save(testStudent);

        Iterable<Student> students = studentRepository.findAll();
        Stream<Student> filteredStudents = StreamSupport.stream(students.spliterator(), false)
                .filter(student -> student.getID().equals("1543"));
        assertEquals(1, filteredStudents.toArray().length);
    }

    @Test(expected = ValidationException.class)
    public void testSaveStudent_entityRejectedForAnInvalidGroupNumber() {
        Student testStudent = new Student("111", "test2", 940);
        studentRepository.save(testStudent);
    }

    @Test(expected = ValidationException.class)
    public void testSaveStudent_entityRejectedForAnInvalidName() {
        Student testStudent = new Student("222", "", 940);
        studentRepository.save(testStudent);
    }

    @Test
    public void testSaveStudent_checkIfDuplicateIdIsIgnored() {
        String studentId = "1234";
        Student testStudent = new Student(studentId, "test4", 937);
        studentRepository.save(testStudent);
        studentRepository.save(testStudent);

        Iterable<Student> students = studentRepository.findAll();
        Stream<Student> filteredStudents = StreamSupport.stream(students.spliterator(), false)
                .filter(student -> student.getID().equals(studentId));
        assertEquals(1, filteredStudents.toArray().length);
    }
}
