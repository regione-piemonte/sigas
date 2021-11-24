package it.csi.sigas.sigasbl.integration.epay.mapper;

import java.util.List;

import it.csi.sigas.sigasbl.integration.epay.to.InserisciListaDiCaricoRequest;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCart;

/**
 * @author 
 * @date 
 */
public interface EPayWsInputMapper {

	InserisciListaDiCaricoRequest mapPagamentoWsMapper(List<SigasPaymentCart> rate, String cf, String causale, String cfEnte);
}
