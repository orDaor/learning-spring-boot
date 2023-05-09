package com.ltp.gradesubmission;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*here we are defining an annotation, that handles
* validation of score field of received data
*
* NOTE: here below is the way we define any annotation in Spring. Since this annotation
* will contain logic for validation, it is a constraint-validation annotation specofically
*
* @Target(ElementType.FIELD) --> this annotation applies to FIELDS
*
* @Retention(RetentionPolicy.RUNTIME) --> this annotation must be retained during the runtime
*
* @Constraint(validatedBy = ClassName.class) --> here we specify that this annotation is a constratin
* that is connected to the validation logic specified in ClassName
*
* --> When we define a @Constraint annotation we need to always define the "groups"
*     and the "payload" parameters
* */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ScoreValidator.class)
public @interface Score {
    //this is the default error message that is stored in the BindingResult object, but we can
    //overwrite this message with the one we specify in the @Score annotation like this for example:
    //@Score(message = "Score has not a valid value")
    String message() default "Invalid Score Data";

    //When we define a @Constraint annotation we need to always define the "groups"
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
