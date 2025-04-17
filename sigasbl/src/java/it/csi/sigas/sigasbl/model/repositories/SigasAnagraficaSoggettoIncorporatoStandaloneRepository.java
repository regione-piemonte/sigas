package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import it.csi.sigas.sigasbl.model.entity.custom.ConsumiSoggettoIncorporatoEntityCustom;

public interface SigasAnagraficaSoggettoIncorporatoStandaloneRepository {

	List<ConsumiSoggettoIncorporatoEntityCustom> getDettaglioConsumiSoggettoIncorporatoByIdFusioneAndAnnualita(
			Long idFusione, String annualita);
}
