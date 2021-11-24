package it.csi.sigas.sigasbl.model.entity.custom;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class PaymentSubjectFOEntityCustomBase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	private Long idAnag;
	private String codiceAzienda;
	private String nomeAzienda;
	private String denominazione;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getIdAnag() {
		return idAnag;
	}
	public void setIdAnag(Long idAnag) {
		this.idAnag = idAnag;
	}
	public String getCodiceAzienda() {
		return codiceAzienda;
	}
	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}
	public String getNomeAzienda() {
		return nomeAzienda;
	}
	public void setNomeAzienda(String nomeAzienda) {
		this.nomeAzienda = nomeAzienda;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
}
