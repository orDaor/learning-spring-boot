package com.ltp.gradesubmission;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//a class annotated as controller can have method handlers that are able
//to handle requests
@Controller
public class GradeController {

    //this class contains a list of Grade objects by default
    List<Grade> studentGrades = new ArrayList<>();

    @GetMapping("/")
    public String getGrades(Model model) {
        model.addAttribute("grade", new Grade("your name", "your subject", "your score"));
        return "form";
    }

    @GetMapping("/grades")
    public String getForm(Model model) {

        model.addAttribute("grades", this.studentGrades);

        return "grades";
    }

    /*Hanlde the POST request carrying the form data.
    * Spring creates an empty Grade object with the empty constructor, then it uses the Grade setters to
    * update the properties of the empty object with the values of the received form data*/
    @PostMapping("/add-grade")
    public  String postGrade(Grade grade) {
        System.out.println(grade);
        this.studentGrades.add(grade);
        return "redirect:/grades";
    }


}
