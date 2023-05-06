package com.ltp.gradesubmission.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "grade")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "score", nullable = false)
    private String score;

    /* this tells JPA that many  Grades can be associated to one student
    *
    *   --> therefore the "grade" table is called "CHILD TABLE", and the "student" table is called the "PARENT TABLE
    *
    *   --> the child table "grade" contains the "foreign key" column, because each grade can point to a student that owns that grade. That means
    *       more grades can point with their foreign key to a single student
    *
    * JoinColumn --> defines a foreign key column that joins the student and grade table
    * optional = false --> a grade can not be saved with not valid student_id value
    *
    * @ManyToOne --> many Grades can belong to One Student*/
    @NotNull
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @NotNull
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", referencedColumnName = "id")
    private Course course;

    public Grade() { }
}
