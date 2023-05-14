package com.ltp.gradesubmission.service;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.CourseNotFoundException;
import com.ltp.gradesubmission.exception.GradeNotFoundException;
import com.ltp.gradesubmission.exception.StudentNotFoundException;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        //no grade was found
        if (optionalGrade.isEmpty()) {
            throw new GradeNotFoundException(studentId, courseId);
        }

        //grade was found
        return optionalGrade.get();
    }

    @Override
    public Grade saveGrade(Grade grade, Long studentId, Long courseId) {
        //save a grade with the student id and course id it belongs to

        //check if the student exists
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException(studentId);
        }

        //check if the course exists
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isEmpty()) {
            throw new CourseNotFoundException(courseId);
        }

        grade.setStudent(optionalStudent.get());
        grade.setCourse(optionalCourse.get());

        Grade savedGrade = gradeRepository.save(grade);

        return savedGrade;
    }

    @Override
    public Grade updateGrade(String score, Long studentId, Long courseId) {
        Optional<Grade> optionalGrade = gradeRepository.findFirstByCourseIdAndStudentId(courseId, studentId);

        //the grade to be updated does not exist
        if (optionalGrade.isEmpty()) {
            throw new GradeNotFoundException(studentId, courseId);
        }


        Grade grade = optionalGrade.get();
        grade.setScore(score);

        Grade updatedGrade = gradeRepository.save(grade);
        return updatedGrade;
    }

    @Override
    public void deleteGrade(Long studentId, Long courseId) {
        Optional<Grade> optionalGrade = gradeRepository.findFirstByCourseIdAndStudentId(courseId, studentId);

        //the grade to be deleted does not exist
        if (optionalGrade.isEmpty()) {
            throw new GradeNotFoundException(studentId, courseId);
        }

        //the grade exists
        Grade grade = optionalGrade.get();
        gradeRepository.deleteById(grade.getId());
    }

    @Override
    public List<Grade> getStudentGrades(Long studentId) {
        Optional<List<Grade>> optionalGrades = gradeRepository.findAllByStudentId(studentId);

        List<Grade> grades = optionalGrades.get();

        //not grade found with that sudentId
        if (optionalGrades.isEmpty() || grades.size() == 0) {
            throw new GradeNotFoundException(studentId, null);
        }

        //at least one grade was found
        return grades;
    }

    @Override
    public List<Grade> getCourseGrades(Long courseId) {
        Optional<List<Grade>> optionalGrades = gradeRepository.findAllByCourseId(courseId);

        List<Grade> grades = optionalGrades.get();

        //no grade was found
        if (optionalGrades.isEmpty() || grades.size() == 0) {
            throw new GradeNotFoundException(null, courseId);
        }

        //at least one grade was found
        return grades;
    }

    @Override
    public List<Grade> getAllGrades() {
        List<Grade> grades = (List<Grade>)gradeRepository.findAll();
        return grades;
    }

}
