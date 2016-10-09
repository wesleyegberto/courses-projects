package com.github.wesleyegberto.doitapp.business;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author wesley
 */
public class CrossCheckValidator implements ConstraintValidator<CrossCheck, ValidEntity> {
    
    @Override
    public void initialize(CrossCheck constraintAnnotation) {
        
    }
    
    @Override
    public boolean isValid(ValidEntity entity, ConstraintValidatorContext context) {
        return entity.isValid();
    }
}
