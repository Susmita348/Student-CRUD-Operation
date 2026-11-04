
package com.student.CRUDoperation.repository;

import com.student.CRUDoperation.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByDeletedFalse();

    Optional<Student> findByIdAndDeletedFalse(Long id);
}
