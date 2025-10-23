package com.example.student_management.repository;

import com.example.student_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    // Recherche d'un étudiant par son identifiant
    Student findById(int id);

    // Recherche d'étudiants par nom
    List<Student> findByNom(String nom);

    // Recherche d'étudiants par prénom
    List<Student> findByPrenom(String prenom);

    // Recherche d'étudiants par nom et prénom
    List<Student> findByNomAndPrenom(String nom, String prenom);

    // Requête personnalisée pour compter les étudiants par année de naissance
    @Query("SELECT YEAR(s.dateNaissance), COUNT(s) FROM Student s GROUP BY YEAR(s.dateNaissance)")
    Collection<Object[]> findNbrStudentByYear();

    // Requête personnalisée pour trouver les étudiants nés après une certaine date
    @Query("SELECT s FROM Student s WHERE s.dateNaissance > :date")
    List<Student> findStudentsBornAfter(java.util.Date date);

    // Requête personnalisée pour compter le nombre total d'étudiants
    @Query("SELECT COUNT(s) FROM Student s")
    long countAllStudents();
}
