package com.ltp.gradesubmission;

import com.ltp.gradesubmission.repository.utils.TablesInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

		//pupulate DBs
		tablesInit.addStudents();

		tablesInit.addCourses();

		tablesInit.addGrades();

	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
