/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import it.csi.sigas.sigasbl.model.vo.home.OrdinativiIncassoVO;
import it.csi.sigas.sigasbl.model.vo.home.VersamentiPrVO;

public class ConfermaVersamentoContabiliaRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "versamento non deve essere vuoto")
	private VersamentiPrVO versamento;
	
	@NotNull(message = "ordinativo incasso non deve essere vuoto")
	private OrdinativiIncassoVO pagamento;
	
	private boolean conciliato;
	
	private boolean confermaInserimento;
	

	public OrdinativiIncassoVO getPagamento() {
		return pagamento;
	}

	public void setPagamento(OrdinativiIncassoVO pagamento) {
		this.pagamento = pagamento;
	}

	public VersamentiPrVO getVersamento() {
		return versamento;
	}

	public void setVersamento(VersamentiPrVO versamento) {
		this.versamento = versamento;
	}

	public boolean isConciliato() {
		return conciliato;
	}

	public void setConciliato(boolean conciliato) {
		this.conciliato = conciliato;
	}

	public boolean isConfermaInserimento() {
		return confermaInserimento;
	}

	public void setConfermaInserimento(boolean confermaInserimento) {
		this.confermaInserimento = confermaInserimento;
	}
	
	


}