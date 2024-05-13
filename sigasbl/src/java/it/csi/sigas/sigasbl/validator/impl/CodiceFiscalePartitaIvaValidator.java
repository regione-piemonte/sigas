package it.csi.sigas.sigasbl.validator.impl;

import org.springframework.stereotype.Component;

@Component("codiceFiscalePartitaIvaValidator")
public class CodiceFiscalePartitaIvaValidator extends RegExValidator {
	
	protected static final String REGEX = CodiceFiscaleValidator.REGEX  + "|" + PartitaIvaValidator.REGEX;

	@Override
	public String getRegex() {		
		return REGEX;
	}
}
