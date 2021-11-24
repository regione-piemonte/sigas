/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.csi.sigas.sigasbl.model.entity.SigasDocumenti;


@Repository
public interface SigasDocumentiRepository extends CrudRepository<SigasDocumenti, Integer> {

	@Query(value=
			"select" + 
			"	sd.*" + 
			" from sigas_documenti sd" + 
			" where" + 
			"	(:idAnag=0 OR sd.id_anag = :idAnag) and " +
			"	(:nProtocollo='' OR sd.rif_archivio = :nProtocollo OR sd.n_protocollo = :nProtocollo) and " +
			"	(:annualita='' OR sd.annualita = :annualita) and " +
			"	(:idTipoDocumento=0 OR sd.id_tipo_documento in (select id_tipo_documento from sigas_tipo_documento where id_tipo_documento_padre = :idTipoDocumento) OR sd.id_tipo_documento = :idTipoDocumento) and"
			+ " (:insUser='' OR sd.ins_user=:insUser) " ,nativeQuery = true)
	List<SigasDocumenti> ricercaDocumenti(@Param("idAnag") Long idAnag, @Param("nProtocollo") String nProtocollo, 
											@Param("annualita") String  annualita, @Param("idTipoDocumento") Integer  idTipoDocumento,
											@Param("insUser") String  insUser);
	
	
	@Query(value=
			"select" + 
			"	sd.*" + 
			" from sigas_documenti sd" + 
			" where" + 
			"	(:idAnag=0 OR sd.id_anag = :idAnag) and " +
			"	(:idStatoDocumento=0 OR sd.id_stato = :idStatoDocumento) and " +
			"	(:dataProtocolloDal='' OR (TO_CHAR(sd.data_protocollazione , 'yyyyMMdd') >= :dataProtocolloDal)) and " +
			"   (:dataProtocolloAl='' OR (TO_CHAR(sd.data_protocollazione , 'yyyyMMdd')  <= :dataProtocolloAl)) and " +
			"   sd.id_stato_archiviazione!=1 ORDER BY sd.data_protocollazione DESC " ,nativeQuery = true)
	List<SigasDocumenti> ricercaDocumentiBo(@Param("idAnag") Long idAnag, @Param("idStatoDocumento") Integer idStatoDocumento, 
											@Param("dataProtocolloDal") String  dataProtocolloDal, @Param("dataProtocolloAl") String  dataProtocolloAl);
	
	List<SigasDocumenti> findBySigasStatoArchiviazioneIdStatoArchiviazione(Integer idStatoArchiviazione);
	
	List<SigasDocumenti> findByRifArchivio(String rifArchivio);
}
