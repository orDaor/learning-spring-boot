package com.ltp.gradesubmission;

import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.repository.StudentRepository;
import com.ltp.gradesubmission.repository.utils.TablesInit;
import com.ltp.gradesubmission.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class GradeSubmissionApplication implements CommandLineRunner {

	@Autowired
	TablesInit tablesInit;

	public static void main(String[] args) {
		SpringApplication.run(GradeSubmissionApplication.class, args);
	}

	//this is called right before the main() is executed
	@Override
	public void run(String... args) throws Exception {

		//pupulate DBs
		tablesInit.addStudents();

		tablesInit.addCourses();

		tablesInit.addGrades();

	}
}
