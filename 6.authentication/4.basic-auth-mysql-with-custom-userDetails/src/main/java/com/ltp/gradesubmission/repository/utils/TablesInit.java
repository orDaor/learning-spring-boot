package com.ltp.gradesubmission.repository.utils;

import com.ltp.gradesubmission.entity.*;
import com.ltp.gradesubmission.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TablesInit {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private UserAuthorityRepository userAuthorityRepository;

    private Student[] students;

    private Grade[] grades;

    private Course[] courses;

    public void saveStudents() {
        //store students in the DB
        Student studentHarry = new Student("Harry Potter", LocalDate.parse(("1980-07-31")));
        Student studentRon = new Student("Ron Weasley", LocalDate.parse(("1980-03-01")));
        Student studentHermione = new Student("Hermione Granger", LocalDate.parse(("1979-09-19")));
        Student studentNeville = new Student("Neville Longbottom", LocalDate.parse(("1980-07-30")));

        students = new Student[] {
                studentHarry,
                studentRon,
                studentHermione,
                studentNeville
        };

        for (Student student : students) {
            studentRepository.save(student);
        }
    }

    public void saveGrades() {
        //store grades in the DB
        Grade grade_A = new Grade("A", students[0], courses[4]); //Harry, Physics
        Grade grade_B = new Grade("B", students[2], courses[3]); //Hermione, Deutsch
        Grade grade_C = new Grade("C", students[2], courses[4]); //Hermione, Physics
        Grade grade_D = new Grade("D", students[3], courses[0]); //Neville, Math
        Grade grade_E = new Grade("E", students[0], courses[0]); //Harry, Math
        Grade grade_F = new Grade("F", students[3], courses[4]); //Neville, Physics

        grades = new Grade[] {
                grade_A,
                grade_B,
                grade_C,
                grade_D,
                grade_E,
                grade_F
        };

        for (Grade grade : grades) {
            gradeRepository.save(grade);
//            System.out.println("Saved grade with score: " + grade.getScore());
        }
    }

    public void saveCourses() {
        Course course_Math = new Course("Math", "abc", "Very difficult");
        Course course_Geography = new Course("Geography", "def", "Very easy");
        Course course_History = new Course("History", "ghi", "Very interesting");
        Course course_Deutsch = new Course("Deutsch", "lmn", "Very shitty");
        Course course_Physics = new Course("Physics ", "opq", "Very aggressive");
        Course course_Geometry = new Course("Geometry", "rst", "Very strange");

        courses = new Course[] {
                course_Math,
                course_Geography,
                course_History,
                course_Deutsch,
                course_Physics,
                course_Geometry
        };

        for (Course course : courses) {
            courseRepository.save(course);
        }

    }

    public void saveAdminUser() {

        //save "admin" user
        UserData user_ADMIN = new UserData(
                "admin",
                passwordEncoder.encode("admin-psw"),
                "Manuel",
                "The Super",
                "manuel@thesuper.com",
                30
        );

        userDataRepository.save(user_ADMIN);

        //save authority "ADMIN" for the "admin" user
        UserAuthority userAuthority = new UserAuthority(user_ADMIN.getUsername(), "ROLE_ADMIN");

        userAuthorityRepository.save(userAuthority);

    }

}
