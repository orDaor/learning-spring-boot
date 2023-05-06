package com.ltp.gradesubmission.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "course")
@Data
@RequiredArgsConstructor
@ToString(callSuper = true)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "subject", nullable = false)
    private String subject;

    @NonNull
    @Column(name = "code", nullable = false)
    private String code;

    @NonNull
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Grade> grades;


    public Course() { }
}
