package com.github.wesleyegberto.doitapp.business.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author wesley
 */
@Documented
@Constraint(validatedBy = CrossCheckValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CrossCheck {

    String message() default "Cross check failed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
