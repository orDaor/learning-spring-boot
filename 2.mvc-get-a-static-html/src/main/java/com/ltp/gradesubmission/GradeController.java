package com.ltp.gradesubmission;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//a class annotated as controller can have method handlers that are able
//to handle requests
@Controller
public class GradeController {
    //this annotation converts this method into a handler method that can
    //handle a GET request to the path "/hello"
    @GetMapping("/grades")
    public String getGrades() {
        return "grades";
    }

}
