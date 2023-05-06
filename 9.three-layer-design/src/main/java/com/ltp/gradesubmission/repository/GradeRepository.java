package com.ltp.gradesubmission.repository;

import com.ltp.gradesubmission.Constants;
import com.ltp.gradesubmission.Grade;

import java.util.ArrayList;
import java.util.List;

public class GradeRepository {
    private List<Grade> studentGrades = new ArrayList<>();


    public void addGrade(Grade grade) {
        studentGrades.add(grade);
    }

    public void updateGrade(Grade grade, int index) {
        studentGrades.set(index, grade);
    }

    public Grade getGrade(int index) {
        return studentGrades.get(index);
    }

    public List<Grade> getAllGrades() {
        return studentGrades;
    }

}
