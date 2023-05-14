package com.ltp.gradesubmission.entity;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.List;

/* @Entity --> this tells spring JPA to create a table that can store
*               student entities (records)
*
*          --> as soon as the application starts up, spring will connect to the SQL database
*               that has the URL specified in the "application.properties" file, and tells it to
*               create a "student" table (the query to do so, is handled by spring JPA, so we
*               do not need to write any SQL code)
*
* @Columns --> maps a field to column*/

@Entity
@Table(name = "student") //specifies the table name
@Data

//@NoArgsConstructor // --> can not be used because class contains @NonNull fields

/*a parameters constructor that can update only a certain number of fields (not all of them), only
* the ones that ar marked with @NonNull annotation*/
@RequiredArgsConstructor

@ToString(callSuper = true)
public class Student {

    @Id //this is going to be the "primary key" column of the table
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "name", nullable = false) //we can not store NULL student names
    @NotBlank(message = "Name can not be blank")
    private String name;

    @NonNull
    @Column(name = "birth_date", nullable = false) //we can not store NULL student birthdays
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    /* One student can be associated to many (a list of) grades
    *
    * cascade = CascadeType.ALL --> effect example: if we query a student to be deleted, all the grades linked to it are deleted too*/
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Grade> grades;

    public Student() { }
}
