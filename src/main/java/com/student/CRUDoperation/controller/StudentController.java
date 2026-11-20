package com.student.CRUDoperation.controller;

import com.student.CRUDoperation.dto.UpdateStudentRequestDto;
import com.student.CRUDoperation.dto.UpdateStudentResponseDto;
import com.student.CRUDoperation.dto.createStudentRequestDTO;
import com.student.CRUDoperation.dto.createStudentResponseDto;
import com.student.CRUDoperation.entity.Student;
import com.student.CRUDoperation.service.StudentService;
import jakarta.validation.Valid;
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
    @PostMapping
    public ResponseEntity<createStudentResponseDto> createStudent(@Valid @RequestBody createStudentRequestDTO createStudentRequestDTO){
     createStudentResponseDto createStudent=   studentService.createStudent(createStudentRequestDTO);
     return ResponseEntity
             .status(HttpStatus.CREATED)
             .body(createStudent);
    }
    @GetMapping("/get")
    public ResponseEntity<createStudentResponseDto> getStudent(@RequestParam Long id) {

        createStudentResponseDto studentResp = studentService.getStudent(id);
                return ResponseEntity.status(HttpStatus.OK).body(studentResp);
    }

    @GetMapping
    public ResponseEntity<List<createStudentResponseDto>> getallStudent(){
                 List<createStudentResponseDto> studentRecord= studentService.getallStudent();
                 if (studentRecord.isEmpty()){
                     return ResponseEntity
                             .status(HttpStatus.NOT_FOUND)
                             .body(null);
                 }
                   return ResponseEntity
                           .status(HttpStatus.OK)
                           .body(studentRecord);
    }
    @PutMapping
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(@RequestParam Long id, @RequestBody UpdateStudentRequestDto  studentReq){

               UpdateStudentResponseDto studentUpdate= studentService.updateStudent(id , studentReq);

               return ResponseEntity.ok(studentUpdate);

    }
    @DeleteMapping
    public ResponseEntity<String> deleteStudent(@RequestParam Long id){
                    studentService.deleteStudent(id);

                        return
                                ResponseEntity.noContent().build();

    }
    @PatchMapping("/delete-soft")
    public ResponseEntity<String> deleteSoftly(@RequestParam Long id) {
        studentService.deleteSoftly(id);

        return ResponseEntity.notFound().build();
    }



}
