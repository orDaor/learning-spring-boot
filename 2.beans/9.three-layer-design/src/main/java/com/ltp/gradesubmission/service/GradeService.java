package com.ltp.gradesubmission.service;

import com.ltp.gradesubmission.Constants;
import com.ltp.gradesubmission.Grade;
import com.ltp.gradesubmission.repository.GradeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public class GradeService {

    GradeRepository gradeRepository = new GradeRepository();

    public Grade getGradeByIndex(int index) {
        return index == Constants.NOT_FOUND ? new Grade() : getGrade(index);
    }

    public int getGradeIndex(String id) {
        for (int i = 0; i < gradeRepository.getAllGrades().size(); i++) {
            if (gradeRepository.getAllGrades().get(i).getId().equals(id)) return i;
        }
        return Constants.NOT_FOUND;
    }

    public void addGrade(Grade grade) {
        gradeRepository.getAllGrades().add(grade);
    }

    public void updateGrade(Grade grade, int index) {
        gradeRepository.getAllGrades().set(index, grade);
    }

    public Grade getGrade(int index) {
        return gradeRepository.getAllGrades().get(index);
    }


    public List<Grade> getAllGrades() {
        return gradeRepository.getAllGrades();
    }

    public void submitGrade(Grade grade) {
        int index = getGradeIndex(grade.getId());
        if (index == Constants.NOT_FOUND) {
            addGrade(grade);
        } else {
            updateGrade(grade, index);
        }
    }
}
