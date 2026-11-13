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
    @PostMapping({"/create"})
    public ResponseEntity<createStudentResponseDto> createStudent(@Valid @RequestBody createStudentRequestDTO createStudentRequestDTO){
     createStudentResponseDto createStudent=   studentService.createStudent(createStudentRequestDTO);
     return ResponseEntity
             .status(HttpStatus.CREATED)
             .body(createStudent);
    }
    @GetMapping("/get")
    public ResponseEntity<createStudentResponseDto> getStudent(@RequestParam Long id) {

        createStudentResponseDto studentResp = studentService.getStudent(id);

        if (studentResp == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(studentResp);
    }

    @GetMapping({"/getAll"})
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
    @PutMapping({"/update"})
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(@RequestParam Long id, @RequestBody UpdateStudentRequestDto  studentReq){

               UpdateStudentResponseDto studentUpdate= studentService.updateStudent(id , studentReq);
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
