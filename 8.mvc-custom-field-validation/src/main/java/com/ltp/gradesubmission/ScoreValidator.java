package com.ltp.gradesubmission;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ltp.gradesubmission.Score;

import java.util.Arrays;
import java.util.List;

//<Score, String>
// --> Score: annotation to which this logic is appplied
// --> String: type of the value evaluated by isValid()
public class ScoreValidator implements ConstraintValidator<Score, String> {

    List<String> scores = Arrays.asList(
            "A", "B", "C", "D", "F",
            "A+", "B+", "C+", "D+",
            "A-", "B-", "C-", "D-"
    );

    @Override
    public boolean isValid(String score, ConstraintValidatorContext context) {
        for (String scoreItem : this.scores) {
            if (scoreItem.equals(score)) {
                //score is among permitted values in the list, therefore it is valid
                return true;
            }
        }

        //score was not found among permitted scores list, therefore it is not a valid value
        return false;
    }
}
