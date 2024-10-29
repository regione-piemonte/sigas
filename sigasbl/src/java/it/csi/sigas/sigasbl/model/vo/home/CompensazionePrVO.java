package it.csi.sigas.sigasbl.model.vo.home;

import java.util.Date;

import it.csi.sigas.sigasbl.common.rest.VO;

public class CompensazionePrVO implements VO {
	 
	private static final long serialVersionUID = -1;
	private long id_consumi;
	private long id_compensazione;
	private double conguaglio_di_riferimento;
	private double conguaglio_compensato;
	private double compensazione;
	private Date data_compensazione;
	private double conguaglio_di_riferimento_t0;
	
	public long getId_consumi() {
		return id_consumi;
	}
	public void setId_consumi(long id_consumi) {
		this.id_consumi = id_consumi;
	}
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
	public Date getData_compensazione() {
		return data_compensazione;
	}
	public void setData_compensazione(Date data_compensazione) {
		this.data_compensazione = data_compensazione;
	}
	public double getConguaglio_di_riferimento_t0() {
		return conguaglio_di_riferimento_t0;
	}
	public void setConguaglio_di_riferimento_t0(double conguaglio_di_riferimento_t0) {
		this.conguaglio_di_riferimento_t0 = conguaglio_di_riferimento_t0;
	}	
}
