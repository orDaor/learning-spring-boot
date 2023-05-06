package com.ltp.gradesubmission.repository;

import com.ltp.gradesubmission.entity.Student;
import org.springframework.data.repository.CrudRepository;

/* StudentRepository --> this interface is a CRUD repository with which we can
* perform CRUD operations against the database, to handle students inside the "student" table
*
* CrudRepository<Student, Long>
    Student --> type of the data record (entities) we want to handle
    Long --> type of the id of each Student entity*/

public interface StudentRepository extends CrudRepository<Student, Long> { }