package org.guy.rpg.dwg.validators.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Constraint for HitDie validation annotation.
 * 
 * @author Guy
 *
 */
public class HitDieConstraint implements ConstraintValidator<HitDie, String> {

	private String regex;
	
	@Override
	public void initialize(HitDie hitDieAnnotation) {
		regex = hitDieAnnotation.regexp();
	}

	@Override
	public boolean isValid(String hitDie, ConstraintValidatorContext ctx) {
		if (hitDie == null || hitDie.equals("")) {
			return false;
		}
		
		// Validate hit dies of format "#d##" as in 1d8 or 1d20:
		if (hitDie.matches(regex)) {
			return true;
		} else {
			return false;
		}
	}
}