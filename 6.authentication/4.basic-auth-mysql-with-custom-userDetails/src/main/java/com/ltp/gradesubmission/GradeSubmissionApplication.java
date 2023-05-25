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

		//save the "admin" user with role = ADMIN
		tablesInit.saveAdminUser();

		//pupulate other tables with some initial data
		tablesInit.saveStudents();

		tablesInit.saveCourses();

		tablesInit.saveGrades();

	}

}
