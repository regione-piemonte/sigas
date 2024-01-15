package it.csi.sigas.sigasbl.request.home;

import java.io.Serializable;

public class UpdateAllarmeAccertamentoRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idAllarme;
	
	private int statoAllarme;

	public Integer getIdAllarme() {
		return idAllarme;
	}

	public void setIdAllarme(Integer idAllarme) {
		this.idAllarme = idAllarme;
	}

	public int getStatoAllarme() {
		return statoAllarme;
	}

	public void setStatpAllarme(int statoAllarme) {
		this.statoAllarme = statoAllarme;
	}	

}
