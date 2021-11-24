/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the sigas_tipo_documento database table.
 * 
 */
@Entity
@Table(name="sigas_tipo_documento")
@NamedQuery(name="SigasTipoDocumento.findAll", query="SELECT s FROM SigasTipoDocumento s")
public class SigasTipoDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_tipo_documento")
	private Integer idTipoDocumento;

	@Column(name="codice_tipo_documento")
	private String codiceTipoDocumento;

	private String descrizione;


	@Column(name="id_tipo_documento_padre")
	private Integer idTipoDocumentoPadre;

	@Column(name="desc_vital_record_code_type")
	private String descVitalRecordCodeType;

	//bi-directional many-to-one association to SigasDocumenti
	@OneToMany(mappedBy="sigasTipoDocumento")
	private List<SigasDocumenti> sigasDocumentis;

	public SigasTipoDocumento() {
	}

	public Integer getIdTipoDocumento() {
		return this.idTipoDocumento;
	}

	public void setIdTipoDocumento(Integer idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getCodiceTipoDocumento() {
		return this.codiceTipoDocumento;
	}

	public void setCodiceTipoDocumento(String codiceTipoDocumento) {
		this.codiceTipoDocumento = codiceTipoDocumento;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}



	public Integer getIdTipoDocumentoPadre() {
		return this.idTipoDocumentoPadre;
	}

	public void setIdTipoDocumentoPadre(Integer idTipoDocumentoPadre) {
		this.idTipoDocumentoPadre = idTipoDocumentoPadre;
	}

	


	public String getDescVitalRecordCodeType() {
		return descVitalRecordCodeType;
	}

	public void setDescVitalRecordCodeType(String descVitalRecordCodeType) {
		this.descVitalRecordCodeType = descVitalRecordCodeType;
	}

	public List<SigasDocumenti> getSigasDocumentis() {
		return this.sigasDocumentis;
	}

	public void setSigasDocumentis(List<SigasDocumenti> sigasDocumentis) {
		this.sigasDocumentis = sigasDocumentis;
	}

	public SigasDocumenti addSigasDocumenti(SigasDocumenti sigasDocumenti) {
		getSigasDocumentis().add(sigasDocumenti);
		sigasDocumenti.setSigasTipoDocumento(this);

		return sigasDocumenti;
	}

	public SigasDocumenti removeSigasDocumenti(SigasDocumenti sigasDocumenti) {
		getSigasDocumentis().remove(sigasDocumenti);
		sigasDocumenti.setSigasTipoDocumento(null);

		return sigasDocumenti;
	}

}