package it.csi.sigas.sigasbl.validator.impl;

import org.springframework.stereotype.Component;

@Component("partitaIvaValidator")
public class PartitaIvaValidator extends RegExValidator {
	
	protected static final String REGEX = "^[0-9]{11}$";

	@Override
	public String getRegex() {		
		return REGEX;
	}

}
