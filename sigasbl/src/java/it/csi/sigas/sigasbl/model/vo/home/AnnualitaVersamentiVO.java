package it.csi.sigas.sigasbl.model.vo.home;

import java.util.List;

import it.csi.sigas.sigasbl.common.rest.VO;

public class AnnualitaVersamentiVO implements VO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<String> lista_annualita;
	
	String anno_ultimo_versamento;

	public List<String> getLista_annualita() {
		return lista_annualita;
	}

	public void setLista_annualita(List<String> lista_annualita) {
		this.lista_annualita = lista_annualita;
	}

	public String getAnno_ultimo_versamento() {
		return anno_ultimo_versamento;
	}

	public void setAnno_ultimo_versamento(String anno_ultimo_versamento) {
		this.anno_ultimo_versamento = anno_ultimo_versamento;
	}	

}
