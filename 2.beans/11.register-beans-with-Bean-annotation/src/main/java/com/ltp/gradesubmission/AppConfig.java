package com.ltp.gradesubmission;

import com.ltp.gradesubmission.repository.GradeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*This class is responsible for registering beans --> As soon as the application runs, the
* methods of this class are executed, and they return objects that are registered as beans...*/
@Configuration
public class AppConfig {
    /*
    this method returns a dependency (object) that will be registered as
    * a "bean" in the spring container. Such a method is called "bean definition"
    * */
    @Bean
    public GradeRepository gradeRepository() {
        return new GradeRepository();
    }
}
