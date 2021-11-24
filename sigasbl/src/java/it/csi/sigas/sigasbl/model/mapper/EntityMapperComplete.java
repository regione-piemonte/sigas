/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper;

import java.util.List;

public interface EntityMapperComplete<EntityType, VOType> {

	VOType mapEntityToVO(EntityType dto);
	EntityType mapVOtoEntity(VOType vo);
	
	List<VOType> mapListEntityToListVO(List<EntityType> en);
	List<EntityType> mapListVoToListEntity(List<VOType> en);
}
