package com.ltp.gradesubmission.repository.utils;

import com.ltp.gradesubmission.entity.Course;
import com.ltp.gradesubmission.entity.Grade;
import com.ltp.gradesubmission.entity.Student;
import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.repository.CourseRepository;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.repository.StudentRepository;
import com.ltp.gradesubmission.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TablesInit {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Student[] students;

    private Grade[] grades;

    private Course[] courses;

    private User[] users;


    public void addStudents() {
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

    public void addGrades() {
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

    public void addCourses() {
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

    public void addUsers() {

        User user_Admin = new User("admin", passwordEncoder.encode("admin-psw"));

        User user_User = new User("user", passwordEncoder.encode("user-psw"));

        users = new User[]  {
                user_Admin,
                user_User
        };

        for (User user : users) {
            userRepository.save(user);
        }

    }

}
