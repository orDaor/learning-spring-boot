package com.ltp.gradesubmission;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

/*Spring boot starter validation dependency checks is fields with validation
* constraints, for ex: @NoteBland, ...*/
public class Grade {
    //@NotBlank --> no white spaces, no empty string
    //NOTE: @NotEmpty --> allows for white spaces
    @NotBlank(message = "Name can not be blank")
    private String name;
    @NotBlank(message = "Subject can not be blank")
    private String subject;
    //apply the custom defined validation-annotation to this field
    @Score(message = "Score has not a valid value")
    private  String score;
    private String id;

    public Grade() {
        this.id = UUID.randomUUID().toString();
    }

    public Grade(String name, String subject, String score, String id) {
        this.name = name;
        this.subject = subject;
        this.score = score;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "name='" + name + '\'' +
                ", subject='" + subject + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
