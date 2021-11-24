package it.csi.sigas.sigasbl.scheduled.impl;

import it.csi.sigas.sigasbl.integration.doqui.DoquiConstants;
import it.csi.sigas.sigasbl.integration.doqui.bean.KeyDocumentoActa;
import it.csi.sigas.sigasbl.integration.doqui.exception.FruitoreException;
import it.csi.sigas.sigasbl.integration.doqui.exception.IntegrationException;
import it.csi.sigas.sigasbl.model.entity.SigasAllegato;
import it.csi.sigas.sigasbl.model.entity.SigasDocumenti;
import it.csi.sigas.sigasbl.model.mapper.entity.DocumentiEntityMapper;
import it.csi.sigas.sigasbl.model.mapper.entity.StatoArchiviazioneEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.SigasDocumentiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasStatoArchiviazioneRepository;
import it.csi.sigas.sigasbl.model.vo.documenti.AllegatoDocumentazioneVO;
import it.csi.sigas.sigasbl.model.vo.documenti.DocumentiVO;
import it.csi.sigas.sigasbl.scheduled.IMoveDocumentService;
import it.csi.sigas.sigasbl.service.IDocumentazioneService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class MoveDocumentServiceImpl implements IMoveDocumentService {

    protected static Logger logger = Logger.getLogger(MoveDocumentServiceImpl.class);
    private SigasDocumentiRepository sigasDocumentiRepository;
    private SigasStatoArchiviazioneRepository sigasStatoArchiviazioneRepository;
    private IDocumentazioneService iDocumentazioneService;
    private DocumentiEntityMapper documentiEntityMapper;
    private StatoArchiviazioneEntityMapper statoArchiviazioneEntityMapper;

    public MoveDocumentServiceImpl() {
    }

    @Autowired
    public MoveDocumentServiceImpl(SigasDocumentiRepository sigasDocumentiRepository,
                                   SigasStatoArchiviazioneRepository sigasStatoArchiviazioneRepository,
                                   IDocumentazioneService iDocumentazioneService, DocumentiEntityMapper documentiEntityMapper,
                                   StatoArchiviazioneEntityMapper statoArchiviazioneEntityMapper) {
        this.sigasDocumentiRepository = sigasDocumentiRepository;
        this.sigasStatoArchiviazioneRepository = sigasStatoArchiviazioneRepository;
        this.iDocumentazioneService = iDocumentazioneService;
        this.documentiEntityMapper = documentiEntityMapper;
        this.statoArchiviazioneEntityMapper = statoArchiviazioneEntityMapper;
    }

    @Override
    public String call() throws Exception {
        logger.info("Sposto i documenti da INDEX -> ACTA");
        spastaDocumentiIndexToActa();
        return "OK";
    }

    private void spastaDocumentiIndexToActa() {
        logger.info("INIZIO: spastaDocumentiIndexToActa");
        List<SigasDocumenti> listDocumentiDaCaricare = sigasDocumentiRepository.findBySigasStatoArchiviazioneIdStatoArchiviazione(sigasStatoArchiviazioneRepository.findByCodiceStato(DoquiConstants.CODICE_STATO_ARCHIVIAZIONE_DA_CARICARE).getIdStatoArchiviazione());
        for (SigasDocumenti sigasDocumenti : listDocumentiDaCaricare) {
            try {
                byte[] fileMaster = iDocumentazioneService.getDocumentoMaster(sigasDocumenti.getIdDocumento());
                DocumentiVO documentoVO = documentiEntityMapper.mapEntityToVO(sigasDocumenti);
                documentoVO.setFileMaster(fileMaster);
                for (AllegatoDocumentazioneVO allegatoVO : documentoVO.getSigasAllegatos()) {
                    byte[] allegatoFile = iDocumentazioneService.getAllegato(sigasDocumenti.getIdDocumento(), allegatoVO.getNomeFile());
                    allegatoVO.setFile(allegatoFile);
                }
                KeyDocumentoActa keyDocumentoActa = iDocumentazioneService.protocollaDocumentoFisico(true, documentoVO);
                documentoVO.setNProtocollo(keyDocumentoActa.getNumeroProtocollo());
                documentoVO.setDataProtocollazione(new Timestamp(new Date().getTime()));
                documentoVO.setSigasStatoArchiviazioneVO(statoArchiviazioneEntityMapper.mapEntityToVO(sigasStatoArchiviazioneRepository.findByCodiceStato(DoquiConstants.CODICE_STATO_ARCHIVIAZIONE_CARICATO)));
                for (AllegatoDocumentazioneVO allegatoVO : documentoVO.getSigasAllegatos()) {
                    allegatoVO.setNumeroProtocollo(keyDocumentoActa.getNumeroProtocollo());
                    allegatoVO.setDataOraProtocollo(new Timestamp(new Date().getTime()));
                    allegatoVO.setStatoArchiviazioneVO(statoArchiviazioneEntityMapper.mapEntityToVO(sigasStatoArchiviazioneRepository.findByCodiceStato(DoquiConstants.CODICE_STATO_ARCHIVIAZIONE_CARICATO)));
                }
                SigasDocumenti sigasDocumento = sigasDocumentiRepository.save(documentiEntityMapper.mapVOtoEntity(documentoVO));
                for (SigasAllegato allegatoTmp : sigasDocumento.getSigasAllegatos()) {
                    allegatoTmp.setSigasDocumenti(sigasDocumento);
                }
                sigasDocumentiRepository.save(sigasDocumento);
                // DELETE DOCUMENTS FROM INDEX
                iDocumentazioneService.eliminaDocumentazioneIndex(sigasDocumento.getIdIndex());
                sigasDocumento.setIdIndex(null);
                for (SigasAllegato sigasAllegato : sigasDocumento.getSigasAllegatos()) {
                    iDocumentazioneService.eliminaDocumentazioneIndex(sigasAllegato.getIdIndex());
                    sigasAllegato.setIdIndex(null);
                }
                sigasDocumentiRepository.save(sigasDocumento);
            } catch (IntegrationException | FruitoreException | IOException e) {
                logger.error(e);
            }
        }
        logger.info("FINE: spastaDocumentiIndexToActa");
    }
}
