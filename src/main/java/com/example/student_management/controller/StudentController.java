package com.example.student_management.controller;

import com.example.student_management.entity.Student;
import com.example.student_management.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*") // Permet les requêtes CORS depuis n'importe quelle origine
@Tag(name = "Student Management", description = "API pour la gestion des étudiants")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Enregistre un nouvel étudiant
     * POST /students/save
     */
    @Operation(summary = "Créer un nouvel étudiant", description = "Ajoute un nouvel étudiant dans la base de données")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Étudiant créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping("/save")
    public ResponseEntity<Student> save(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.save(student);
            return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Met à jour un étudiant existant
     * PUT /students/update/{id}
     */
    @Operation(summary = "Mettre à jour un étudiant", description = "Met à jour les informations d'un étudiant existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Étudiant mis à jour avec succès"),
        @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<Student> update(
        @Parameter(description = "ID de l'étudiant à mettre à jour") @PathVariable("id") int id, 
        @RequestBody Student student) {
        Student updatedStudent = studentService.update(id, student);
        if (updatedStudent != null) {
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Supprime un étudiant par son ID
     * DELETE /students/delete/{id}
     */
    @Operation(summary = "Supprimer un étudiant", description = "Supprime un étudiant de la base de données")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Étudiant supprimé avec succès"),
        @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "ID de l'étudiant à supprimer") @PathVariable("id") int id) {
        boolean deleted = studentService.delete(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Récupère un étudiant par son ID
     * GET /students/{id}
     */
    @Operation(summary = "Récupérer un étudiant par ID", description = "Retourne les informations d'un étudiant spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Étudiant trouvé"),
        @ApiResponse(responseCode = "404", description = "Étudiant non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Student> findById(
        @Parameter(description = "ID de l'étudiant à récupérer") @PathVariable("id") int id) {
        Student student = studentService.findById(id);
        if (student != null) {
            return new ResponseEntity<>(student, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Récupère tous les étudiants
     * GET /students/all
     */
    @Operation(summary = "Récupérer tous les étudiants", description = "Retourne la liste complète de tous les étudiants")
    @ApiResponse(responseCode = "200", description = "Liste des étudiants récupérée avec succès")
    @GetMapping("/all")
    public ResponseEntity<List<Student>> findAll() {
        List<Student> students = studentService.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    /**
     * Compte le nombre total d'étudiants
     * GET /students/count
     */
    @Operation(summary = "Compter les étudiants", description = "Retourne le nombre total d'étudiants dans la base de données")
    @ApiResponse(responseCode = "200", description = "Nombre d'étudiants retourné avec succès")
    @GetMapping("/count")
    public ResponseEntity<Long> countStudents() {
        long count = studentService.countStudents();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    /**
     * Récupère le nombre d'étudiants par année de naissance
     * GET /students/byYear
     */
    @Operation(summary = "Statistiques par année", description = "Retourne le nombre d'étudiants regroupés par année de naissance")
    @ApiResponse(responseCode = "200", description = "Statistiques récupérées avec succès")
    @GetMapping("/byYear")
    public ResponseEntity<Collection<Object[]>> findByYear() {
        Collection<Object[]> studentsByYear = studentService.findNbrStudentByYear();
        return new ResponseEntity<>(studentsByYear, HttpStatus.OK);
    }

    /**
     * Recherche des étudiants par nom
     * GET /students/search/nom/{nom}
     */
    @Operation(summary = "Rechercher par nom", description = "Recherche tous les étudiants ayant un nom spécifique")
    @ApiResponse(responseCode = "200", description = "Résultats de recherche retournés avec succès")
    @GetMapping("/search/nom/{nom}")
    public ResponseEntity<List<Student>> findByNom(
        @Parameter(description = "Nom de famille à rechercher") @PathVariable("nom") String nom) {
        List<Student> students = studentService.findByNom(nom);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    /**
     * Recherche des étudiants par prénom
     * GET /students/search/prenom/{prenom}
     */
    @Operation(summary = "Rechercher par prénom", description = "Recherche tous les étudiants ayant un prénom spécifique")
    @ApiResponse(responseCode = "200", description = "Résultats de recherche retournés avec succès")
    @GetMapping("/search/prenom/{prenom}")
    public ResponseEntity<List<Student>> findByPrenom(
        @Parameter(description = "Prénom à rechercher") @PathVariable("prenom") String prenom) {
        List<Student> students = studentService.findByPrenom(prenom);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    /**
     * Recherche des étudiants par nom et prénom
     * GET /students/search/{nom}/{prenom}
     */
    @Operation(summary = "Rechercher par nom et prénom", description = "Recherche les étudiants ayant un nom et prénom spécifiques")
    @ApiResponse(responseCode = "200", description = "Résultats de recherche retournés avec succès")
    @GetMapping("/search/{nom}/{prenom}")
    public ResponseEntity<List<Student>> findByNomAndPrenom(
        @Parameter(description = "Nom de famille à rechercher") @PathVariable("nom") String nom, 
        @Parameter(description = "Prénom à rechercher") @PathVariable("prenom") String prenom) {
        List<Student> students = studentService.findByNomAndPrenom(nom, prenom);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}
