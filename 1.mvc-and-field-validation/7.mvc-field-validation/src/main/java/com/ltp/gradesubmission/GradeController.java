package com.ltp.gradesubmission;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//a class annotated as controller can have method handlers that are able
//to handle requests
@Controller
public class GradeController {

    //this class contains a list of Grade objects by default
    List<Grade> studentGrades = new ArrayList<>();

    //@RequestParam --> take as an input the value of the query param "name"
    //that is coming with the request.
    //required = false --> no error occures if query param "name" does not come with the request, that
    //means the parameter "name" is optional
    @GetMapping("/")
    public String getForm(Model model, @RequestParam(required = false) String id) {
        System.out.println(id);

        Grade grade = null;

        //check if in the grades list there is a grade with the requested name
        Integer index = this.getGradeIndex(id);
        if (index < 0) {
            //no-match found
            grade = new Grade();
        } else {
            //one grade with the requested name was found
            grade = this.studentGrades.get(index);
        }

        model.addAttribute("grade", grade);
        return "form";
    }

    @GetMapping("/grades")
    public String getGrades(Model model) {

        model.addAttribute("grades", this.studentGrades);

        return "grades";
    }

    /*Hanlde the POST request carrying the form data.
    * Spring creates an empty Grade object with the empty constructor, then it uses the Grade setters to
    * update the properties of the empty object with the values of the received form data*/

    /*@Valid --> checks if the fields are valid
    * BindingResult --> carries the result of the validation
    *
    * NOTE: regardless of validation, grade object is always populated with received data, even
    * if those data are empty*/
    @PostMapping("/add-grade")
    public String postGrade(@Valid Grade grade, BindingResult result) {
        //check if validation failed
        System.out.println("Has error? --> " + result.hasErrors());

        //force the user to stay in the form if input data are wrong
        if (result.hasErrors()) {
            String firstErrorMessage = result.getAllErrors().get(0).getDefaultMessage();
            String secondErrorMessage = result.getAllErrors().get(1).getDefaultMessage();
            String thirdErrorMessage = result.getAllErrors().get(2).getDefaultMessage();
            System.out.println(firstErrorMessage);
            System.out.println(secondErrorMessage);
            System.out.println(thirdErrorMessage);
            return "redirect:/grades";
        }

        Integer index = getGradeIndex(grade.getId());
        //if this grade does not exist yet, add a new one in the list
        if (index < 0) {
            this.studentGrades.add(grade);
        } else {
            this.studentGrades.set(index, grade);
        }
        return "redirect:/grades";
    }

   public Integer getGradeIndex(String id) {
       for (int i = 0; i < studentGrades.size(); i++) {
           Grade grade = studentGrades.get(i);
           if (grade.getId().equals(id)) {
               return i;
           }
       }
       return  Constants.NOT_FOUND;
   }

}
