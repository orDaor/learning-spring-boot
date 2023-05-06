package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Optional;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Grade getGrade(Long studentId, Long courseId) {
        Optional<Grade> optionalGrade = gradeRepository.findFirstByCourseIdAndStudentId(courseId, studentId);
        return optionalGrade.get();
    }

    @Override
    public Grade saveGrade(Grade grade, Long studentId, Long courseId) {
        //save a grade with the student id and course id it belongs to
        Student student = studentRepository.findById(studentId).get();
        Course course = courseRepository.findById(courseId).get();

        grade.setStudent(student);
        grade.setCourse(course);

        Grade savedGrade = gradeRepository.save(grade);

        return savedGrade;
    }

    @Override
    public Grade updateGrade(String score, Long studentId, Long courseId) {
        Optional<Grade> optionalGrade = gradeRepository.findFirstByCourseIdAndStudentId(courseId, studentId);

        Grade grade = optionalGrade.get();
        grade.setScore(score);

        Grade updatedGrade = gradeRepository.save(grade);
        return updatedGrade;
    }

    @Override
    public void deleteGrade(Long studentId, Long courseId) {
        Optional<Grade> optionalGrade = gradeRepository.findFirstByCourseIdAndStudentId(courseId, studentId);
        Grade grade = optionalGrade.get();
        gradeRepository.deleteById(grade.getId());
    }

    @Override
    public List<Grade> getStudentGrades(Long studentId) {
        Optional<List<Grade>> optionalGrades = gradeRepository.findAllByStudentId(studentId);
        List<Grade> grades = optionalGrades.get();
        return grades;
    }

    @Override
    public List<Grade> getCourseGrades(Long courseId) {
        Optional<List<Grade>> optionalGrades = gradeRepository.findAllByCourseId(courseId);
        List<Grade> grades = optionalGrades.get();
        return grades;
    }

    @Override
    public List<Grade> getAllGrades() {
        List<Grade> grades = (List<Grade>)gradeRepository.findAll();
        return grades;
    }

}
