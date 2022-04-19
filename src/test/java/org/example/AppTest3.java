package org.example;

import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;
import org.junit.*;
import repository.NotaRepository;
import repository.NotaXMLRepository;
import repository.StudentRepository;
import repository.TemaRepository;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class AppTest3
{
    private NotaValidator notaValidator;
    private NotaRepository notaRepository;
    private TemaValidator assignmentValidator;
    private TemaRepository assignmentRepository;
    private StudentValidator studentValidator;
    private StudentRepository studentRepository;

    @Before
    public void setup() {
        this.notaValidator = new NotaValidator();
        this.notaRepository = new NotaRepository(notaValidator);
        this.assignmentValidator = new TemaValidator();
        this.assignmentRepository = new TemaRepository(assignmentValidator);
        this.studentValidator = new StudentValidator();
        this.studentRepository = new StudentRepository(studentValidator);
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

    @Test
    public void testStudentAssignmentIntegration_assignmentSavedInRepository() {
        // No idea what should be done here... there is basically no relationship between saveStudent and saveAssignment
        // unless we also call the saveGrade method, which binds the former 2 together
        Student testStudent = new Student("1543", "test1", 937);
        studentRepository.save(testStudent);
        Tema assignment = new Tema("123", "ceva tema", 5, 3);
        assignmentRepository.save(assignment);

        int numberOfAssignments = StreamSupport
                .stream(assignmentRepository.findAll().spliterator(), false)
                .toArray().length;

        assertEquals(1, numberOfAssignments);
    }

    @Test
    public void testAddGrade_entitySavedInRepository() {
        Nota testNota = new Nota(new Pair<>("abc", "def"), 10, 5, "ok");
        notaRepository.save(testNota);

        Iterable<Nota> grades = notaRepository.findAll();
        assertTrue(grades instanceof Collection<?>);
        assertEquals(1, ((Collection<?>)grades).size());
    }


    @Test
    public void testStudentAssignmentGradeIntegration() {
        Student testStudent = new Student("1543", "test1", 937);
        studentRepository.save(testStudent);
        Tema assignment = new Tema("123", "ceva tema", 5, 3);
        assignmentRepository.save(assignment);
        Nota testNota = new Nota(new Pair<>("1543", "123"), 10, 5, "ok");
        notaRepository.save(testNota);

        Iterable<Nota> grades = notaRepository.findAll();
        assertTrue(grades instanceof Collection<?>);
        assertEquals(1, ((Collection<?>)grades).size());
    }
}

