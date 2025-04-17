package it.csi.sigas.sigasbl.integration.epay.mapper;

import java.util.List;

import it.csi.sigas.sigasbl.integration.epay.rest.ppay.RequestObjects.CreateDebtPositionRequest;
import it.csi.sigas.sigasbl.model.entity.SigasPaymentCart;

public interface PPayRestCreateDebtPositionRequestMapper {
	
	CreateDebtPositionRequest mapCreateDebtRequest(List<SigasPaymentCart> cart, String cf, String descrizioneCausaleVersamento, String cfEnte, 
												   Integer annoAccertamento, String numeroAccertamento);

}
