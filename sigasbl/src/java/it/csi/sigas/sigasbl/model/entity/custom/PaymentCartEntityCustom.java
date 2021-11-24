/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity.custom;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class PaymentCartEntityCustom implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private String codiceAzienda;
	private String nomeAzienda;
	private String denominazione;
	private String siglaProvinciaAzienda;
    private String mesi;
    private String totaleImporto;
	
}
