package it.csi.sigas.sigasbl.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import it.csi.sigas.sigasbl.model.vo.depositocausionale.DepositoCausionaleVO;
import it.csi.sigas.sigasbl.model.vo.home.ReportResponse;

public interface IDepositoCausionaleService {
	
	public List<DepositoCausionaleVO> salvaDepositoCausionale(DepositoCausionaleVO depositoCausionaleVO);
	
	public DepositoCausionaleVO ricercaDepositoCausionaleById(Long idDepositoCausionale);	
	
	public byte[] generaReportRichiestaDepositoCausionale(DepositoCausionaleVO depositoCausionaleVO);
	
	public byte[] generaReportApprovazioneDepositoCausionale(DepositoCausionaleVO depositoCausionaleVO, Date dataInvioMail);
	
	public byte[] generaCarrelloBollettinoPagamentoDepositoCausionaleByIdDicumentoRichiesta(Long idDocumento, Integer annoAccertamento, 
																							String numeroAccertamento, Integer tipoCarreloDepositoCauzionale);
	
	public List<DepositoCausionaleVO> ricercaElencoDepositoCausionaleByIdDocumento(Long idDocumento);
	
	public List<DepositoCausionaleVO> ricercaElencoDepositoCausionaleByCodicePagamento(String codicePagamento);
	
	public boolean aggiornaImportoRichiestaDepositoCauzionale(List<DepositoCausionaleVO> depositoCausionaleVOs, BigDecimal importo,
															  String annoAccertamento, String numeroAccertamento, String numeroDetermina);
	
	
	public boolean aggiornaRichiestaDepositoCauzionaleDatiAggiuntivi(List<DepositoCausionaleVO> depositoCausionaleVOs, 
															  		 String annoAccertamento, String numeroAccertamento, 
															  		 String numeroDetermina);

}
