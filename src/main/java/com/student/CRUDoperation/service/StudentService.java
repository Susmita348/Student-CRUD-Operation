package com.student.CRUDoperation.service;


import com.student.CRUDoperation.dto.UpdateStudentRequestDto;
import com.student.CRUDoperation.dto.UpdateStudentResponseDto;
import com.student.CRUDoperation.dto.createStudentRequestDTO;
import com.student.CRUDoperation.dto.createStudentResponseDto;
import com.student.CRUDoperation.entity.Student;
import com.student.CRUDoperation.exception.DuplicateResourceException;
import com.student.CRUDoperation.exception.ResourceNotFoundExceptionHandler;
import com.student.CRUDoperation.repository.StudentRepository;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService
{
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository   ){
        this.studentRepository=studentRepository;
    }
    public createStudentResponseDto createStudent(createStudentRequestDTO createStudentRequestDTO){
        Student  student= mapToEntity(createStudentRequestDTO);
                if(emailExists(student)){

                    throw new DuplicateResourceException("Student with Email " +student.getEmail() +" already exists");
                }


      Student studentResp=studentRepository.save(student);
      return mapToDto(studentResp);


    }
    public createStudentResponseDto getStudent(Long id){
                  Student studentResp= studentRepository
                          .findByIdAndDeletedFalse(id)
                          .orElseThrow(()->
                                  new ResourceNotFoundExceptionHandler("Student with id " +id +" not found"));

//                  if(studentResp.isPresent()) {
//                      return mapToDto(studentResp.get());
//                  }
//                  return null;

        return mapToDto(studentResp);
    }
    public List<createStudentResponseDto> getallStudent(){
                  List<Student> studentResp= studentRepository.findByDeletedFalse();

                  return studentResp.stream()
                          .map(this::mapToDto)
                          .toList();
    }
    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentReq) {

        Student existingStudent = studentRepository
                .findByIdAndDeletedFalse(id)
            .orElseThrow(()->
                new ResourceNotFoundExceptionHandler("Student with id " +id +" not found"));
        Student studentSave = existingStudent;
        studentSave.setName(studentReq.getName());

        studentSave.setRollNo(studentReq.getRollNo());
        studentSave.setSubject(studentReq.getSubject());
        studentSave.setAge(studentReq.getAge());
                    studentSave.setDeleted(false);
                    studentSave.setUpdatedAt(LocalDateTime.now());
              Student savedStudent=  studentRepository.save(studentSave);
             return  mapToUpdateDto(savedStudent);
    }

    public  void deleteStudent(Long id){
       Student student=  studentRepository.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundExceptionHandler("Student with id " +id +" not found"));
        studentRepository.delete(student);

    }
    public void deleteSoftly(Long id){
         Student studentsoftlydeleted= studentRepository.
                     findByIdAndDeletedFalse(id)
                       .orElseThrow(()->
                          new ResourceNotFoundExceptionHandler("Student with id " +id +" not found"));


        studentsoftlydeleted.setDeleted(true);
            studentRepository.save(studentsoftlydeleted);

    }

    private   Student mapToEntity(createStudentRequestDTO createStudentRequestDTO){
        Student student=new Student();
        student.setName(createStudentRequestDTO.getName());
        student.setAge(createStudentRequestDTO.getAge());
        student.setEmail(createStudentRequestDTO.getEmail());
        student.setSubject(createStudentRequestDTO.getSubject());
        student.setRollNo(createStudentRequestDTO.getRollNo());
        student.setDeleted(false);
        student.setCreatedAt(LocalDateTime.now());
        student.setUpdatedAt(LocalDateTime.now());
        return student;

    }
    private createStudentResponseDto mapToDto(Student student){
        createStudentResponseDto responseDto = new createStudentResponseDto();
        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setSubject(student.getSubject());
        responseDto.setCreatedAt(student.getCreatedAt());
        responseDto.setUpdatedAt(student.getUpdatedAt());
        responseDto.setMessage("student saved sucessfully");
        return responseDto;

    }
    private UpdateStudentResponseDto mapToUpdateDto(Student student){
        UpdateStudentResponseDto responseDto = new UpdateStudentResponseDto();
        responseDto.setId(student.getId());
        responseDto.setName(student.getName());
        responseDto.setAge(student.getAge());
        responseDto.setEmail(student.getEmail());
        responseDto.setRollNo(student.getRollNo());
        responseDto.setSubject(student.getSubject());

        responseDto.setUpdatedAt(student.getUpdatedAt());
        responseDto.setMessage("student saved sucessfully");
        return responseDto;
    }
    private Boolean emailExists(Student student){
      return  studentRepository.existsByEmail(student.getEmail());
    }
}
