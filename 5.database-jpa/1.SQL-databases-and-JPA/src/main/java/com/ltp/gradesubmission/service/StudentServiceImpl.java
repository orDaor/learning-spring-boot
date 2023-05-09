package com.ltp.gradesubmission.service;

import java.util.List;
import java.util.Optional;

import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.exception.StudentNotFoundException;
import com.ltp.gradesubmission.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public Student getStudent(Long id) {
        //NOTE: if we call get() on an optional that wraps a NULL value, then java will throw a "NoSuchElementException", therefore
        //below we want to prevent this case and throw our own exception
        Optional<Student> optionalStudent = studentRepository.findById(id);

        //optional contains null value (no student was found with that id)
        if (optionalStudent.isEmpty()) {
            throw new StudentNotFoundException(id);
        }

        //student was found
        return optionalStudent.get();
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudents() {
        return (List<Student>)studentRepository.findAll();
    }


}