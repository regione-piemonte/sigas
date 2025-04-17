package it.csi.sigas.sigasbl.model.vo.depositocausionale;

import java.math.BigDecimal;

import it.csi.sigas.sigasbl.common.rest.VO;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.documenti.DocumentiVO;
import it.csi.sigas.sigasbl.model.vo.luoghi.ProvinciaVO;

public class DepositoCausionaleVO implements VO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long idDepositoCausionale;
	
	private BigDecimal importo;	
	
	private DocumentiVO documentoVO;
	
	private ProvinciaVO provinciaVO;
	
	private AnagraficaSoggettoVO anagraficaSoggettoVO;	
	
	private String annoAcccertamento;	
	
	private String numeroAccertamento;	
	
	private String numeroDetermina;

	public long getIdDepositoCausionale() {
		return idDepositoCausionale;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public DocumentiVO getDocumentoVO() {
		return documentoVO;
	}

	public ProvinciaVO getProvinciaVO() {
		return provinciaVO;
	}

	public AnagraficaSoggettoVO getAnagraficaSoggettoVO() {
		return anagraficaSoggettoVO;
	}

	public void setIdDepositoCausionale(long idDepositoCausionale) {
		this.idDepositoCausionale = idDepositoCausionale;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public void setDocumentoVO(DocumentiVO documentoVO) {
		this.documentoVO = documentoVO;
	}

	public void setProvinciaVO(ProvinciaVO provinciaVO) {
		this.provinciaVO = provinciaVO;
	}

	public void setAnagraficaSoggettoVO(AnagraficaSoggettoVO anagraficaSoggettoVO) {
		this.anagraficaSoggettoVO = anagraficaSoggettoVO;
	}

	public String getAnnoAcccertamento() {
		return annoAcccertamento;
	}

	public String getNumeroAccertamento() {
		return numeroAccertamento;
	}

	public String getNumeroDetermina() {
		return numeroDetermina;
	}

	public void setAnnoAcccertamento(String annoAcccertamento) {
		this.annoAcccertamento = annoAcccertamento;
	}

	public void setNumeroAccertamento(String numeroAccertamento) {
		this.numeroAccertamento = numeroAccertamento;
	}

	public void setNumeroDetermina(String numeroDetermina) {
		this.numeroDetermina = numeroDetermina;
	}	

}
