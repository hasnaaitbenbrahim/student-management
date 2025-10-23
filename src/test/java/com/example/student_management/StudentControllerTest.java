package com.example.student_management;

import com.example.student_management.controller.StudentController;
import com.example.student_management.entity.Student;
import com.example.student_management.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void testSaveStudent() {
        // Arrange - Préparer les données de test
        Student student = new Student();
        student.setNom("LACHGAR");
        student.setPrenom("Mohamed");
        student.setDateNaissance(new Date());

        Student savedStudent = new Student();
        savedStudent.setId(1);
        savedStudent.setNom("LACHGAR");
        savedStudent.setPrenom("Mohamed");
        savedStudent.setDateNaissance(student.getDateNaissance());

        // Act - Simuler le comportement du service
        when(studentService.save(any(Student.class))).thenReturn(savedStudent);

        // Assert - Exécuter le test et vérifier les résultats
        ResponseEntity<Student> response = studentController.save(student);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("LACHGAR", response.getBody().getNom());
        assertEquals("Mohamed", response.getBody().getPrenom());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void testDeleteStudent() {
        // Arrange - Simuler une suppression réussie
        when(studentService.delete(anyInt())).thenReturn(true);

        // Act - Exécuter la suppression
        ResponseEntity<Void> response = studentController.delete(1);

        // Assert - Vérifier le résultat
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteStudentNotFound() {
        // Arrange - Simuler une suppression échouée (étudiant non trouvé)
        when(studentService.delete(anyInt())).thenReturn(false);

        // Act - Exécuter la suppression
        ResponseEntity<Void> response = studentController.delete(999);

        // Assert - Vérifier le résultat
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testFindAllStudents() {
        // Arrange - Créer des étudiants de test
        Student student1 = new Student();
        student1.setId(1);
        student1.setNom("LACHGAR");
        student1.setPrenom("Mohamed");

        Student student2 = new Student();
        student2.setId(2);
        student2.setNom("MARTIN");
        student2.setPrenom("Sophie");

        List<Student> students = Arrays.asList(student1, student2);

        // Act - Simuler le service
        when(studentService.findAll()).thenReturn(students);

        // Assert - Exécuter le test
        ResponseEntity<List<Student>> response = studentController.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("LACHGAR", response.getBody().get(0).getNom());
        assertEquals("MARTIN", response.getBody().get(1).getNom());
    }

    @Test
    void testCountStudents() {
        // Arrange - Simuler le comptage
        when(studentService.countStudents()).thenReturn(15L);

        // Act - Exécuter le test
        ResponseEntity<Long> response = studentController.countStudents();

        // Assert - Vérifier le résultat
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(15L, response.getBody());
    }

    @Test
    void testFindByYear() {
        // Arrange - Créer des données de test pour les statistiques par année
        Object[] year1985 = {1985, 2};
        Object[] year1990 = {1990, 1};
        Collection<Object[]> studentsByYear = Arrays.asList(year1985, year1990);

        // Act - Simuler le service
        when(studentService.findNbrStudentByYear()).thenReturn(studentsByYear);

        // Assert - Exécuter le test
        ResponseEntity<Collection<Object[]>> response = studentController.findByYear();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testFindById() {
        // Arrange - Créer un étudiant de test
        Student student = new Student();
        student.setId(1);
        student.setNom("LACHGAR");
        student.setPrenom("Mohamed");

        // Act - Simuler le service
        when(studentService.findById(1)).thenReturn(student);

        // Assert - Exécuter le test
        ResponseEntity<Student> response = studentController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("LACHGAR", response.getBody().getNom());
        assertEquals("Mohamed", response.getBody().getPrenom());
    }

    @Test
    void testFindByIdNotFound() {
        // Arrange - Simuler un étudiant non trouvé
        when(studentService.findById(999)).thenReturn(null);

        // Act - Exécuter le test
        ResponseEntity<Student> response = studentController.findById(999);

        // Assert - Vérifier le résultat
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateStudent() {
        // Arrange - Créer un étudiant existant et les nouvelles données
        Student existingStudent = new Student();
        existingStudent.setId(1);
        existingStudent.setNom("LACHGAR");
        existingStudent.setPrenom("Mohamed Ali");

        Student updatedStudent = new Student();
        updatedStudent.setId(1);
        updatedStudent.setNom("LACHGAR");
        updatedStudent.setPrenom("Mohamed Ali");

        // Act - Simuler le service
        when(studentService.update(1, updatedStudent)).thenReturn(existingStudent);

        // Assert - Exécuter le test
        ResponseEntity<Student> response = studentController.update(1, updatedStudent);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Mohamed Ali", response.getBody().getPrenom());
    }

    @Test
    void testUpdateStudentNotFound() {
        // Arrange - Simuler un étudiant non trouvé pour la mise à jour
        Student student = new Student();
        student.setNom("INEXISTANT");
        student.setPrenom("Test");

        when(studentService.update(999, student)).thenReturn(null);

        // Act - Exécuter le test
        ResponseEntity<Student> response = studentController.update(999, student);

        // Assert - Vérifier le résultat
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testFindByNom() {
        // Arrange - Créer des étudiants de test
        Student student1 = new Student();
        student1.setId(1);
        student1.setNom("LACHGAR");
        student1.setPrenom("Mohamed");

        Student student2 = new Student();
        student2.setId(2);
        student2.setNom("LACHGAR");
        student2.setPrenom("Ali");

        List<Student> students = Arrays.asList(student1, student2);

        // Act - Simuler le service
        when(studentService.findByNom("LACHGAR")).thenReturn(students);

        // Assert - Exécuter le test
        ResponseEntity<List<Student>> response = studentController.findByNom("LACHGAR");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("LACHGAR", response.getBody().get(0).getNom());
        assertEquals("LACHGAR", response.getBody().get(1).getNom());
    }
}
