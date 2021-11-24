/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.mapper;

import java.util.ArrayList;
import java.util.List;

public interface EntityDMapper<EntityType, VOType> {

	VOType mapEntityToVO(EntityType en);

	default List<VOType> mapListEntityToListVO(List<EntityType> en) {
		if (null == en)
			return null;
		List<VOType> v = new ArrayList<VOType>();
		for (EntityType e : en) {
			v.add(mapEntityToVO(e));
		}
		return v;
	}

}
