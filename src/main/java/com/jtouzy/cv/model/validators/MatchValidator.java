package com.jtouzy.cv.model.validators;

import com.jtouzy.cv.model.classes.Match;
import com.jtouzy.dao.errors.validation.DataValidationException;
import com.jtouzy.dao.validation.AbstractDataValidator;

public class MatchValidator extends AbstractDataValidator<Match> {
	@Override
	protected void validateCommon(Match object) 
	throws DataValidationException {
		super.validateCommon(object);
	}
}
