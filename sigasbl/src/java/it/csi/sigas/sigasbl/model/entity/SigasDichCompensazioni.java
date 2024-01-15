package it.csi.sigas.sigasbl.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "sigas_dich_compensazioni")
@NamedQuery(name = "SigasDichCompensazioni.findAll", query = "SELECT i FROM SigasDichCompensazioni i")
public class SigasDichCompensazioni {
	
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SIGAS_DICH_COMPENSAZIONI_IDCOMPENSAZIONI_GENERATOR", 
					   sequenceName = "SEQ_DICH_COMPENSAZIONI", 
					   allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SIGAS_DICH_COMPENSAZIONI_IDCOMPENSAZIONI_GENERATOR")
	@Column(name = "id_compensazione", unique = true, nullable = false)
	private long id_compensazione;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_compensazione")
	private Date data_compensazione;
	
	@Column(name = "conguaglio_di_riferimento", nullable = true)
	private double conguaglio_di_riferimento;
	
	@Column(name = "conguaglio_compensato", nullable = true)
	private double conguaglio_compensato;
	
	@Column(name = "compensazione", nullable = true)
	private double compensazione;
	
	@Column(name = "id_consumi", unique = true, nullable = false)
	private long id_consumi;

	public long getId_compensazione() {
		return id_compensazione;
	}

	public void setId_compensazione(long id_compensazione) {
		this.id_compensazione = id_compensazione;
	}	

	public double getConguaglio_di_riferimento() {
		return conguaglio_di_riferimento;
	}

	public void setConguaglio_di_riferimento(double conguaglio_di_riferimento) {
		this.conguaglio_di_riferimento = conguaglio_di_riferimento;
	}

	public double getConguaglio_compensato() {
		return conguaglio_compensato;
	}

	public void setConguaglio_compensato(double conguaglio_compensato) {
		this.conguaglio_compensato = conguaglio_compensato;
	}

	public double getCompensazione() {
		return compensazione;
	}

	public void setCompensazione(double compensazione) {
		this.compensazione = compensazione;
	}

	public long getId_consumi() {
		return id_consumi;
	}

	public void setId_consumi(long id_consumi) {
		this.id_consumi = id_consumi;
	}

	public Date getData_compensazione() {
		return data_compensazione;
	}

	public void setData_compensazione(Date data_compensazione) {
		this.data_compensazione = data_compensazione;
	}	
		
}
