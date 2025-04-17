package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;
import it.csi.sigas.sigasbl.model.entity.custom.SigasStoricoAnagraficaSoggettiCustom;

public interface SigasStoricoAnagraficaSoggettiCustomRepository {
	
	List<SigasStoricoAnagraficaSoggettiCustom> findStoricoAnagraficaSoggettiCustom(String denominazioneSoggetto,
																					String annualita,
																					String indirizzo,
																					String codiceAzienda,
																					String cfPiva);

}
