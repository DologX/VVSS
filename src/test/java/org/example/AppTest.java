package org.example;

import domain.Student;
import org.junit.*;
import repository.StudentRepository;
import repository.StudentXMLRepository;
import service.Service;
import validation.StudentValidator;

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
}
