package it.csi.sigas.sigasbl.validator.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import it.csi.sigas.sigasbl.validator.InputValidator;

public abstract class RegExValidator implements InputValidator {
	
	public abstract String getRegex();

	@Override
	public void validate(String fieldName, String fieldValue) throws Exception {		
					
		Pattern pattern;
		boolean matchFound = false;
		
		if(StringUtils.isBlank(fieldValue)) {
			return;
		}

		pattern = Pattern.compile(getRegex());

		Matcher matcher =  pattern.matcher(fieldValue);
		matchFound = matcher.matches();
		
		if (!matchFound) {
			throw new Exception("Input " + fieldName + " non valido");
		}	

	}

}
