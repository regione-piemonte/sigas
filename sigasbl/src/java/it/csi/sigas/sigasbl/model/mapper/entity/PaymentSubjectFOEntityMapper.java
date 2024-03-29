/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper.entity;

import java.util.List;

public interface PaymentSubjectFOEntityMapper<EntityType, VOType> {

	VOType mapEntityToVO(EntityType dto);
	EntityType mapVOtoEntity(VOType vo);
	
	List<VOType> mapListEntityToListVO(List<? extends EntityType> en);

}
