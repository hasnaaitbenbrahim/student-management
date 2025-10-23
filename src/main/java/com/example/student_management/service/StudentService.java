package com.example.student_management.service;

import com.example.student_management.entity.Student;
import com.example.student_management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    /**
     * Enregistre ou met à jour un étudiant
     * @param student l'étudiant à sauvegarder
     * @return l'étudiant sauvegardé avec son ID
     */
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    /**
     * Supprime un étudiant par son ID
     * @param id l'identifiant de l'étudiant à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean delete(int id) {
        Optional<Student> studentOptional = Optional.ofNullable(studentRepository.findById(id));
        if (studentOptional.isPresent()) {
            studentRepository.delete(studentOptional.get());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Récupère un étudiant par son ID
     * @param id l'identifiant de l'étudiant
     * @return l'étudiant trouvé ou null
     */
    public Student findById(int id) {
        return studentRepository.findById(id);
    }

    /**
     * Récupère tous les étudiants
     * @return la liste de tous les étudiants
     */
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    /**
     * Compte le nombre total d'étudiants
     * @return le nombre d'étudiants
     */
    public long countStudents() {
        return studentRepository.count();
    }

    /**
     * Récupère le nombre d'étudiants par année de naissance
     * @return une collection d'objets contenant l'année et le nombre d'étudiants
     */
    public Collection<Object[]> findNbrStudentByYear() {
        return studentRepository.findNbrStudentByYear();
    }

    /**
     * Recherche des étudiants par nom
     * @param nom le nom à rechercher
     * @return la liste des étudiants trouvés
     */
    public List<Student> findByNom(String nom) {
        return studentRepository.findByNom(nom);
    }

    /**
     * Recherche des étudiants par prénom
     * @param prenom le prénom à rechercher
     * @return la liste des étudiants trouvés
     */
    public List<Student> findByPrenom(String prenom) {
        return studentRepository.findByPrenom(prenom);
    }

    /**
     * Recherche des étudiants par nom et prénom
     * @param nom le nom à rechercher
     * @param prenom le prénom à rechercher
     * @return la liste des étudiants trouvés
     */
    public List<Student> findByNomAndPrenom(String nom, String prenom) {
        return studentRepository.findByNomAndPrenom(nom, prenom);
    }

    /**
     * Met à jour un étudiant existant
     * @param id l'identifiant de l'étudiant à mettre à jour
     * @param student les nouvelles données de l'étudiant
     * @return l'étudiant mis à jour ou null si non trouvé
     */
    public Student update(int id, Student student) {
        Student existingStudent = studentRepository.findById(id);
        if (existingStudent != null) {
            existingStudent.setNom(student.getNom());
            existingStudent.setPrenom(student.getPrenom());
            existingStudent.setDateNaissance(student.getDateNaissance());
            return studentRepository.save(existingStudent);
        }
        return null;
    }
}
