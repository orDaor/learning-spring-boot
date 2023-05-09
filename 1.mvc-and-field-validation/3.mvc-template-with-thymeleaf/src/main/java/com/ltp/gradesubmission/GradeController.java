package com.ltp.gradesubmission;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//a class annotated as controller can have method handlers that are able
//to handle requests
@Controller
public class GradeController {
    //this annotation converts this method into a handler method that can
    //handle a GET request to the path "/hello"
    @GetMapping("/grades")
    public String getGrades(Model model ) {
        //create a POJO
        Grade grade = new Grade("Orgher", "Matematica", "9");

        //store POJO data in the MODEL (model will have a property grade equal to grade object)
        model.addAttribute("grade", grade);
        return "grades";
    }

}
