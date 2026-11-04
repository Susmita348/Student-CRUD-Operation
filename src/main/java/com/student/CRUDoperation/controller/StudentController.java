package com.student.CRUDoperation.controller;

import com.student.CRUDoperation.entity.Student;
import com.student.CRUDoperation.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping({"/api/students"})
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }
    @PostMapping({"/create"})
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        student.setDeleted(false);
     Student createStudent=   studentService.createStudent(student);
     return ResponseEntity
             .status(HttpStatus.CREATED)
             .body(createStudent);
    }
    @GetMapping({"/get"})
    public ResponseEntity< Optional<Student>> getStudent(@RequestParam Long id){

                        Optional<Student> studentResp = studentService.getStudent(id);
                            if (studentResp.isEmpty()){
                            return ResponseEntity.notFound().build();
                        }
                        return ResponseEntity.status(HttpStatus.OK)
                                .body(studentResp);
    }
    @GetMapping({"/getAll"})
    public ResponseEntity<List<Student>> getallStudent(){
                 List<Student> studentRecord= studentService.getallStudent();
                 if (studentRecord.isEmpty()){
                     return ResponseEntity
                             .status(HttpStatus.NOT_FOUND)
                             .body(null);
                 }
                   return ResponseEntity
                           .status(HttpStatus.OK)
                           .body(studentRecord);
    }
    @PutMapping({"/update"})
    public ResponseEntity<Student> updateStudent(@RequestParam Long id, @RequestBody Student studentReq){
        studentReq.setDeleted(false);
               Student studentUpdate= studentService.updateStudent(id ,studentReq);
                    if(studentUpdate==null){
                        return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(null);
                    }
               return ResponseEntity.ok(studentUpdate);

    }
    @DeleteMapping({"/Delete"})
    public ResponseEntity<String> deleteStudent(@RequestParam Long id){
                    Boolean isDeleted=studentService.deleteStudent(id);
                    if(!isDeleted){
                        return
                                ResponseEntity
                                        .status(HttpStatus.NOT_FOUND)
                                        .body(null);
                    }
                    return ResponseEntity.ok("Record Deleted");
    }
    @PatchMapping("/delete-soft/{id}")
    public ResponseEntity<String> deleteSoftly(@PathVariable Long id) {
        Boolean isDeleted = studentService.deleteSoftly(id);

        if (!isDeleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Record deleted");
    }

}
