package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasPagamenti;

@Repository
public interface SigasPagamentiCrudRepository extends CrudRepository<SigasPagamenti, Integer> {

	@Query("select distinct(u.annoOrdInc) from SigasPagamenti u ")
	public List<String> findAnnualitaVersamenti();
	
}
