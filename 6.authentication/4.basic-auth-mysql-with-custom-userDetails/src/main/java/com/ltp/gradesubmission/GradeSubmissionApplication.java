package com.ltp.gradesubmission;

import com.ltp.gradesubmission.repository.utils.TablesInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GradeSubmissionApplication implements CommandLineRunner {

	@Autowired
	private TablesInit tablesInit;

	public static void main(String[] args) {
		SpringApplication.run(GradeSubmissionApplication.class, args);
	}

	//this is called right before the main() is executed
	@Override
	public void run(String... args) throws Exception {

		//pupulate DB with at least an admin user
		tablesInit.addUsers();

		//pupulate DBs with other data
		tablesInit.addStudents();

		tablesInit.addCourses();

		tablesInit.addGrades();

	}

}
