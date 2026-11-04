package com.student.CRUDoperation.service;


import com.student.CRUDoperation.entity.Student;
import com.student.CRUDoperation.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService
{
    private StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository   ){
        this.studentRepository=studentRepository;
    }
    public Student createStudent(Student studentReq){
          studentReq.setDeleted(false);
            Student studentResp= studentRepository.save(studentReq);
                    return studentResp;

    }
    public Optional<Student> getStudent(Long id){
                  Optional<Student> studentResp= studentRepository.findByIdAndDeletedFalse(id);
                  if(studentResp.isPresent()) {
                      return Optional.of(studentResp.get());
                  }
                  return Optional.empty();
    }
    public List<Student> getallStudent(){
                  List<Student> studentResp= studentRepository.findByDeletedFalse();
                  return (studentResp);
    }
    public Student updateStudent(Long id, Student studentReq) {

        Optional<Student> existingStudent = studentRepository.findByIdAndDeletedFalse(id);
        if (existingStudent.isEmpty()) {
            return null;
        }
        Student studentSave = existingStudent.get();
        studentSave.setName(studentReq.getName());
        studentSave.setEmail(studentReq.getEmail());
        studentSave.setRollNo(studentReq.getRollNo());
        studentSave.setSubject(studentReq.getSubject());
        studentSave.setAge(studentReq.getAge());
                    studentSave.setDeleted(false);
              Student savedStudent=  studentRepository.save(studentSave);
              return savedStudent;
    }

    public  Boolean deleteStudent(Long id){
        Boolean isStudent= studentRepository.existsById(id);
        if(!isStudent){
            return false;
        }
        studentRepository.deleteById(id);
      return true;
    }
    public Boolean deleteSoftly(Long id){
           Optional<Student> existingStudent= studentRepository.findByIdAndDeletedFalse(id);
           if(existingStudent.isEmpty()){
               return false;

           }
           Student savedStudent=  existingStudent.get();
            savedStudent.setDeleted(true);
            studentRepository.save(savedStudent);
            return true;
    }

}
