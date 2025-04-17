package it.csi.sigas.sigasbl.model.repositories;

import java.util.List;

import it.csi.sigas.sigasbl.model.entity.custom.UTFStandaloneEntityCustom;
import it.csi.sigas.sigasbl.model.entity.custom.UTFStandaloneEntitySoggettiMacroReport;

public interface SigasUTFStandaloneRepository {
	
	public List<UTFStandaloneEntityCustom> getUTFReportByIdImport(Long idImport, Integer annualita);
	
	public List<UTFStandaloneEntityCustom> getUTFReportDettaglioSoggettiByIdImport(Long idImport);
	
	public List<UTFStandaloneEntitySoggettiMacroReport> getUTFSoggettiMacroReportByIdReport(Long idImportA, Long idImportB, Integer annualita);
	
	public List<UTFStandaloneEntityCustom> getUTFReportDettaglioSoggettiByIdImportIdAnagrafica(Long idImport, Long idAnag);

}
