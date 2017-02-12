package org.guy.rpg.dwg.validators.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Custom annotation for HitDie form inputs.
 * Constraints for this validator are defined in HitDieConstraint.
 * 
 * @author Guy
 *
 */

@Documented
@Constraint(validatedBy = HitDieConstraint.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HitDie {

	String message() default "Hit Die value must match pattern <num>d<num> as in 1d8.";
	
	String regexp() default "(?i)\\dd\\d{1,2}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}