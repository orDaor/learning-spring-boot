package com.ltp.gradesubmission.repository;

import com.ltp.gradesubmission.entity.Grade;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface GradeRepository extends CrudRepository<Grade, Long> {

    Optional<Grade> findByStudentId(Long studentId);

    Optional<Grade>  findFirstByStudentId(Long studentId);

    Optional<Grade> findFirstByCourseIdAndStudentId(Long courseId, Long studentId);

    Optional<List<Grade>> findAllByStudentId(Long studentId);

    Optional<List<Grade>> findAllByCourseId(Long courseId);
}