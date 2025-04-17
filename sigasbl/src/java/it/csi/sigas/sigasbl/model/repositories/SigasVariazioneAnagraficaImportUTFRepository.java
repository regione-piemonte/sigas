package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasVariazioneAnagraficaImportUTF;

@Repository
public interface SigasVariazioneAnagraficaImportUTFRepository extends CrudRepository<SigasVariazioneAnagraficaImportUTF, Long>, 
																	  JpaSpecificationExecutor<SigasVariazioneAnagraficaImportUTF> 
{
	List<SigasVariazioneAnagraficaImportUTF> findAll();
	
	SigasVariazioneAnagraficaImportUTF findByIdAnag(Long id);
	
	SigasVariazioneAnagraficaImportUTF findByidVariazioneAnag(Long id);
	
	SigasVariazioneAnagraficaImportUTF findByCodiceAzienda(String codiceAzienda);
	
	SigasVariazioneAnagraficaImportUTF findByDenominazione(String denominazione);
	
	@Query(value=
			"SELECT va.* " + 
			"FROM sigas_variazione_anagrafica_importUTF va" +			 
			"WHERE " + 
			"(:denominazione='' OR va.denominazione = :denominazione) " + 
			"AND (:codiceAzienda='' OR va.codice_azienda = :codiceAzienda) " +
			"AND (:indirizzo='' OR va.indirizzo = :indirizzo) ",nativeQuery = true)
	List<SigasVariazioneAnagraficaImportUTF> findVariazioneAnagrafica(@Param("denominazione") String denominazione, 
																	  @Param("codiceAzienda") String codiceAzienda,
																	  @Param("indirizzo") String indirizzo);

}
