package it.csi.sigas.sigasbl.dispatcher;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import it.csi.sigas.sigasbl.common.AuthorizationRoles;
import it.csi.sigas.sigasbl.model.vo.depositocausionale.DepositoCausionaleVO;
import it.csi.sigas.sigasbl.request.depositocauzionale.RigeneraBollettinoPagamentoRequest;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

public interface IDepositoCausionaleDispatcher {
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public byte[] generaBollettinoPagamento(Long idDocumento, Integer annoAccertamento, String numeroAccertamento, Integer tipoCarreloDepositoCauzionale);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public byte[] rigeneraBollettinoPagamento(RigeneraBollettinoPagamentoRequest rigeneraBollettinoPagamentoRequest);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public void protocollaDepositoCausionale(MultipartFormDataInput input);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public List<DepositoCausionaleVO> ricercaElencoDepositoCausionaleByIdDocumento(Long idDocumento);
		
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public void inviaMailRifiutoDepositoCauzionale(Long idDocumento, String noteRifiuto);
	
	@PreAuthorize(value = AuthorizationRoles.HOME)
	public void aggiornaDepositoCauzionaleDatiAggiuntivi(Long idDocumento, String annoAccertamento, String numeroAccertamento, String numeroDetermina, BigDecimal importo);

}
