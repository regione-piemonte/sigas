/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.sigas.sigasbl.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.xml.serialize.XMLSerializer;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.lowagie.text.xml.XmlParser;

import it.csi.sigas.sigasbl.common.Constants;
import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.EsitoImport;
import it.csi.sigas.sigasbl.common.Quadro;
import it.csi.sigas.sigasbl.common.StatoValidazione;
import it.csi.sigas.sigasbl.common.StatusAllarme;
import it.csi.sigas.sigasbl.common.TipoAllarme;
import it.csi.sigas.sigasbl.common.TipoDocumento;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
import it.csi.sigas.sigasbl.integration.doqui.utils.XmlSerializer;
import it.csi.sigas.sigasbl.model.entity.SigasAliquote;
import it.csi.sigas.sigasbl.model.entity.SigasAllarmi;
import it.csi.sigas.sigasbl.model.entity.SigasAnaComunicazioni;
import it.csi.sigas.sigasbl.model.entity.SigasAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasComune;
import it.csi.sigas.sigasbl.model.entity.SigasDichConsumi;
import it.csi.sigas.sigasbl.model.entity.SigasDichScarti;
import it.csi.sigas.sigasbl.model.entity.SigasFrontespizio;
import it.csi.sigas.sigasbl.model.entity.SigasImportUTF;
import it.csi.sigas.sigasbl.model.entity.SigasProvincia;
import it.csi.sigas.sigasbl.model.entity.SigasQuadroF;
import it.csi.sigas.sigasbl.model.entity.SigasQuadroG;
import it.csi.sigas.sigasbl.model.entity.SigasQuadroH;
import it.csi.sigas.sigasbl.model.entity.SigasQuadroI;
import it.csi.sigas.sigasbl.model.entity.SigasQuadroM;
import it.csi.sigas.sigasbl.model.entity.SigasQuadroN;
import it.csi.sigas.sigasbl.model.entity.SigasStoricoAnagraficaSoggetti;
import it.csi.sigas.sigasbl.model.entity.SigasTipoAllarmi;
import it.csi.sigas.sigasbl.model.entity.SigasValidazione;
//import it.csi.sigas.sigasbl.model.entity.SigasVariazioneAnagraficaImportUTF;
import it.csi.sigas.sigasbl.model.entity.custom.UTFStandaloneEntityCustom;
import it.csi.sigas.sigasbl.model.entity.custom.UTFStandaloneEntitySoggettiMacroReport;
import it.csi.sigas.sigasbl.model.mapper.entity.AnnualitaImportEntityMapper;
import it.csi.sigas.sigasbl.model.repositories.SigasAliquoteRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAllarmiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAnaComunicazioniRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasAnagraficaSoggettiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasComuneRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichConsumiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichScartiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasDichVersamentiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasFrontespizioRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasImportRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasProvinciaRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasQuadroFRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasQuadroGRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasQuadroHRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasQuadroIRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasQuadroMRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasQuadroNRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasStoricoAnagraficaSoggettiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasTipoAllarmiRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasUTFStandaloneRepository;
import it.csi.sigas.sigasbl.model.repositories.SigasValidazioneRepository;
//import it.csi.sigas.sigasbl.model.repositories.SigasVariazioneAnagraficaImportUTFRepository;
import it.csi.sigas.sigasbl.model.vo.AnagraficaSoggettoVO;
import it.csi.sigas.sigasbl.model.vo.home.ConsumiPrVO;
import it.csi.sigas.sigasbl.model.vo.home.ScartoVO;
import it.csi.sigas.sigasbl.model.vo.importUTF.AnnualitaImportVO;
import it.csi.sigas.sigasbl.model.vo.importUTF.ImportUTFVO;
import it.csi.sigas.sigasbl.model.vo.importUTF.UTFStandaloneEntitySoggettiMacroReportVO;
import it.csi.sigas.sigasbl.request.utf.UTFConfermaDichiarazioneRequest;
import it.csi.sigas.sigasbl.request.utf.UTFConfermaSoggettoDichiarazioneRequest;
import it.csi.sigas.sigasbl.request.utf.UTFSoggettiMacroReportByIdReportRequest;
import it.csi.sigas.sigasbl.service.IHomeService;
import it.csi.sigas.sigasbl.service.IUTFService;

@Service
public class UTFServiceImpl implements IUTFService {

	@Autowired
	private SigasImportRepository sigasImportRepository;
	
	@Autowired
	private SigasFrontespizioRepository sigasFrontespizioRepository;
	
	@Autowired
	private SigasQuadroFRepository sigasQuadroFRepository;
	
	@Autowired
	private SigasQuadroGRepository sigasQuadroGRepository;
	
	@Autowired
	private SigasQuadroHRepository sigasQuadroHRepository;
	
	@Autowired
	private SigasQuadroIRepository sigasQuadroIRepository;
	
	@Autowired
	private SigasQuadroNRepository sigasQuadroNRepository;
	
	@Autowired
	private SigasQuadroMRepository sigasQuadroMRepository;
	
	@Autowired
	private SigasAnagraficaSoggettiRepository sigasAnagraficaSoggettiRepository;
	
	@Autowired
	private SigasAliquoteRepository sigasAliquoteRepository;
	
	@Autowired
	private SigasDichConsumiRepository sigasDichConsumiRepository;
	
	@Autowired 
	private SigasAnaComunicazioniRepository sigasAnaComunicazioniRepository;
	
	@Autowired
	private SigasDichVersamentiRepository sigasDichVersamentiRepository;
	
	@Autowired
	private SigasDichScartiRepository sigasDichScartiRepository;
	
	@Autowired
	private SigasTipoAllarmiRepository sigasTipoAllarmeRepository;
	
	@Autowired
	private SigasAllarmiRepository sigasAllarmiRepository;
	
	@Autowired
	private AnnualitaImportEntityMapper annualitaEntityMapper;
	
	@Autowired
	private SigasProvinciaRepository sigasProvinciaRepository;
	
	@Autowired
	private SigasComuneRepository sigasComuneRepository;
	
	@Autowired
	private SigasUTFStandaloneRepository sigasUTFStandaloneRepository;
	
	@Autowired
	private SigasStoricoAnagraficaSoggettiRepository sigasStoricoAnagraficaSoggettiRepository;
	
	@Autowired
	private SigasValidazioneRepository sigasValidazioneRepository;
	
	@Autowired
	private IHomeService homeService;
	
	//@Autowired
	//private SigasVariazioneAnagraficaImportUTFRepository sigasVariazioneAnagraficaImportUTFRepository;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final static int progRigoInizioTotali_2017 = 6;
	private final static int progRigoInizioTotali_2018 = 7;
	
	private static final String OWNER_UPDATE_SOGGETTO_ANAG = "IMPORT";
	private static final String CONFERMA_NUOVO_SOGGETTO_DICHIARAZIONI= "INS";
	private static final String CONFERMA_UPDATE_SOGGETTO_DICHIARAZIONI = "UPD";
	private static final String CONFERMA_ALL_DICHIARAZIONI = "ALL";
	private static final String CONFERMA_NOP_SOGGETTO_DICHIARAZIONI = "NOP";
	private static final Boolean FLG_SELECTED_IMPORT = true;
	private static final Boolean FLG_NOT_SELECTED_IMPORT = false;
	
	
	@Value("${year.to.show}")
	private int yearToShow;
	
	@Transactional
	@Override
	public SigasImportUTF checkImportUTF(MultipartFormDataInput input, String username) {

		try {
			String anno = input.getFormDataPart("annualita", String.class, null);
			
			// Controlla se l'importazione e' gia'stata eseguita
			/*
			if (sigasImportRepository.findDowloadedByAnno(anno) != null) {
				throw new BusinessException("Il file delle dichiariazioni UTF e' stato gia' importato per l'anno '" + anno + "'", ErrorCodes.BUSSINESS_EXCEPTION);
			}
			*/
			
			// Inizializza impprt in DB
	    	SigasImportUTF sigasImport = new SigasImportUTF();
		    sigasImport.setImportDate(new Date());
		    sigasImport.setUserID(username);
		    sigasImport.setEsito(EsitoImport.DOWNLOAD_INPROGRESS.getEsitoImport());
		    sigasImport.setAnnualita(anno);
		    
		    return sigasImportRepository.save(sigasImport);
			
		}
		catch (IOException e) {
			throw new BusinessException("Errore durante l'importazione delle dichiariazioni UTF: " + e.getMessage(), ErrorCodes.IO_EXCEPTION);
		} 
	}
	
	@Transactional
	@Override
	public boolean checkStateUTF(long importId, int esito) {

		SigasImportUTF sigasImport = null;
		
	
		sigasImport = sigasImportRepository.findOne(importId);
		
		if ( sigasImport == null ) {
			throw new BusinessException("Il file delle dichiariazioni UTF non e' stato importato", ErrorCodes.BUSSINESS_EXCEPTION);
		} 
		
		if (sigasImport.getEsito() != esito) {
			return false;
		}
		
		return true;
	}
	
	@Transactional
	@Override
	public ImportUTFVO importUTF(MultipartFormDataInput input, String username, long importId) {
		ImportUTFVO importUtfVO = new ImportUTFVO();
    	String fileName = "";
    	String line = "";
        String cvsSplitBy = ";";
        String filenameSplitBy = "_";
        String extSplitBy = Pattern.quote(".");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SigasFrontespizio sigasFrontespizio = null;
    	int numFiles = 0;
    	String annualita = null;
    	BufferedReader br = null;
    	InputPart inputPart = input.getParts().iterator().next();
    	SigasImportUTF sigasImport = sigasImportRepository.findOne(importId);
    	boolean isFrontespizioImported = false;
		boolean isQuadroFImported = false;
		boolean isQuadroGImported = false;
		boolean isQuadroHImported = false; 
		boolean isQuadroIImported = false;
		boolean isQuadroMImported = false;
		boolean isQuadroNImported = false;    	
		
    	MultivaluedMap<String, String> header = inputPart.getHeaders();
    	fileName = getFileName(header);
    	String ext[] = fileName.split(Pattern.quote("."));
    	
   		try {
    	
   			if (ext.length <2 || !ext[1].equals("zip")) {
   	    		throw new BusinessException("Il file '" + fileName + "' non e' un file zip", ErrorCodes.FILE_NOT_VALID);
   	    	}
   			
			InputStream inputStream = inputPart.getBody(InputStream.class, null);
			annualita = input.getFormDataPart("annualita", String.class, null);

			// Verifica dichiarazione annuale
//			String annoDichiarazione = fileName.split("_")[2];
//			if (!annoDichiarazione.equals(annualita)) {
//				throw new BusinessException("Il file delle dichiariazioni UTF '"+ fileName +"' non corrisponde con l'annualita' '" + annualita + "'", ErrorCodes.IO_EXCEPTION);
//			}
			
			// Estrazione dei file utf dallo zip
			ZipInputStream zis = new ZipInputStream(inputStream);
	        
	        ZipEntry zipEntry = zis.getNextEntry();
	        
	        while (zipEntry != null) {
	            numFiles++;
            
	            logger.info("File unzip : "+ zipEntry.getName());         

	            	
	            	String nomeQuadro = null;
	            	String fileNamePart[] = zipEntry.getName().split((filenameSplitBy));
	            	
	            	if(fileNamePart.length > 5) {
	            		nomeQuadro = fileNamePart[4] + filenameSplitBy + fileNamePart[5].split(extSplitBy)[0];
	            	} else {
	            		nomeQuadro = fileNamePart[4].split(extSplitBy)[0];
	            	}
	            	
	            	
	            	boolean isUnzip = false;
		        	
		        	for (Quadro q : Quadro.values()) {
		                if (q.name().equals(nomeQuadro)) {
		                   isUnzip = true;
		                   break;
		                }
		            }
	            	
		        	if (isUnzip) {
		        		
		        		// legge file
			            ByteArrayOutputStream baos = new ByteArrayOutputStream();
						int b = zis.read();
						while (b >= 0) {
							baos.write(b);
							b = zis.read();
						}
						byte[] buffer = baos.toByteArray();
			            
		        		
		            	br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buffer)));
		        		
		        		int contatore = 0;
	            		
	            		// Salta il rigo intestazione
	            		line = br.readLine();
	            		// legge le righe dei dati
						while ((line = br.readLine()) != null) {
							String[] col = line.split(cvsSplitBy);
							String className = "it.csi.sigas.sigasbl.model.entity.Sigas"+ nomeQuadro.charAt(0);
							
							if (fileNamePart.length > 5) {
								String typeQuadro[] = nomeQuadro.substring(1).split(filenameSplitBy); 
								className = className + typeQuadro[0].toLowerCase() + typeQuadro[1];
							} else {
								className = className + nomeQuadro.substring(1).toLowerCase();
							}
							
							Class<?> sigasQuadro = Class.forName(className);
							
							Constructor<?> costruttori[] = sigasQuadro.getConstructors();
	
							for (int i=0; i < costruttori.length; i++)
							{
								Class<?>  tipiParam[] = costruttori[i].getParameterTypes();
								
								if (tipiParam.length != 0)
								{
									Object params[] = new Object[tipiParam.length]; 
									params[0] = new SigasImportUTF();
									
									for (int j=0; j < tipiParam.length; j++) {
										String tipo[] = tipiParam[j].getName().split(Pattern.quote("."));
										switch(tipo[tipo.length - 1]) {	
											case "Date":
												params[j] = formatter.parse(col[j-1]);
												break;
											case "String":
												params[j] = col[j-1];
												break;
											case "int":
												if (StringUtils.isNotEmpty(col[j-1]))
													params[j] = Integer.parseInt(col[j-1]);
												else
													params[j] = 0;
												break;
											case "float":
												if (StringUtils.isNotEmpty(col[j-1])) {
													params[j] = Float.parseFloat(col[j-1].replace(",","."));
												} else 
													params[j] = Float.parseFloat("0.0");
												break;
											case "double":
												if (StringUtils.isNotEmpty(col[j-1])) {
													params[j] = Double.parseDouble(col[j-1].replace(",","."));
												} else 
													params[j] = Double.parseDouble("0.0");
												break;
												
										}
									}
									
									// Creazione oggetto
									Object quadroObj = costruttori[i].newInstance(params);
									
									// incova il metodo per assegnare l'oggetto SigasImportUTF
									sigasQuadro.getMethod("setSigasImport", SigasImportUTF.class).invoke(quadroObj, sigasImport);
									
								
									contatore++;
									
									// Salvataggio in DB
									if (nomeQuadro.equals(Quadro.FRONTESPIZIO.getName())) {
										sigasFrontespizio = (SigasFrontespizio)quadroObj;
										if (!sigasFrontespizio.getAnno().equals(annualita)) {
											throw new BusinessException("L'annualita' "+ annualita + " dell'importazione non corrisponde con l'anno "+ sigasFrontespizio.getAnno() + " presente nei dati del file Frontespizio", ErrorCodes.BUSSINESS_EXCEPTION);
										}
										sigasFrontespizioRepository.save(sigasFrontespizio);
										isFrontespizioImported = true;
									}
									else if (nomeQuadro.equals(Quadro.QUADRO_F.getName())) {
										if (!((SigasQuadroF)quadroObj).getAnno().equals(annualita)) {
											throw new BusinessException("L'annualita' "+ annualita + " dell'importazione non corrisponde con l'anno "+ ((SigasQuadroF)quadroObj).getAnno() + " presente nei dati del file Quadro F", ErrorCodes.BUSSINESS_EXCEPTION);
										}
										sigasQuadroFRepository.save((SigasQuadroF)quadroObj);
										isQuadroFImported = true;
									} else if (nomeQuadro.equals(Quadro.QUADRO_G.getName())) {
										if (!((SigasQuadroG)quadroObj).getAnno().equals(annualita)) {
											throw new BusinessException("L'annualita' "+ annualita + " dell'importazione non corrisponde con l'anno "+ ((SigasQuadroG)quadroObj).getAnno() + " presente nei dati del file Quadro G", ErrorCodes.BUSSINESS_EXCEPTION);
										}
										sigasQuadroGRepository.save((SigasQuadroG)quadroObj);
										isQuadroGImported = true;
									} else if (nomeQuadro.equals(Quadro.QUADRO_H.getName())) {
										if (!((SigasQuadroH)quadroObj).getAnno().equals(annualita)) {
											throw new BusinessException("L'annualita' "+ annualita + " dell'importazione non corrisponde con l'anno "+ ((SigasQuadroH)quadroObj).getAnno() + " presente nei dati del file Quadro H", ErrorCodes.BUSSINESS_EXCEPTION);
										}
										sigasQuadroHRepository.save((SigasQuadroH)quadroObj);
										isQuadroHImported = true;
									} else if (nomeQuadro.equals(Quadro.QUADRO_I.getName())) {
										if (!((SigasQuadroI)quadroObj).getAnno().equals(annualita)) {
											throw new BusinessException("L'annualita' "+ annualita + " dell'importazione non corrisponde con l'anno "+ ((SigasQuadroI)quadroObj).getAnno() + " presente nei dati del file Quadro I", ErrorCodes.BUSSINESS_EXCEPTION);
										}
										sigasQuadroIRepository.save((SigasQuadroI)quadroObj);
										isQuadroIImported = true;
									}else if (nomeQuadro.equals(Quadro.QUADRO_M.getName())) {
										if (!((SigasQuadroM)quadroObj).getAnno().equals(annualita)) {
											throw new BusinessException("L'annualita' "+ annualita + " dell'importazione non corrisponde con l'anno "+ ((SigasQuadroM)quadroObj).getAnno() + " presente nei dati del file Quadro M", ErrorCodes.BUSSINESS_EXCEPTION);
										}
										sigasQuadroMRepository.save((SigasQuadroM)quadroObj);
										isQuadroMImported = true;
									} else if (nomeQuadro.equals(Quadro.QUADRO_N.getName())) {
										if (!((SigasQuadroN)quadroObj).getAnno().equals(annualita)) {
											throw new BusinessException("L'annualita' "+ annualita + " dell'importazione non corrisponde con l'anno "+ ((SigasQuadroN)quadroObj).getAnno() + " presente nei dati del file Quadro N", ErrorCodes.BUSSINESS_EXCEPTION);
										}
										sigasQuadroNRepository.save((SigasQuadroN)quadroObj);
										isQuadroNImported = true;
									}
								}
							}          		
						}
						logger.info("il file '" + zipEntry.getName() + "' e'stato correttamente caricato: numero record = " + contatore);
		        	}

	            zipEntry = zis.getNextEntry();
	            
	        }
	        zis.closeEntry();
	        zis.close();
	        
	        if (!isFrontespizioImported)
			{
	        	throw new BusinessException("Il file delle dichiarazioni non contiene il Frontespizio ", ErrorCodes.BUSSINESS_EXCEPTION);
			}
	        
	        if (!isQuadroFImported)
			{
	        	throw new BusinessException("Il file delle dichiarazioni non contiene il Quadro F ", ErrorCodes.BUSSINESS_EXCEPTION);
			}
	        
	        if (!isQuadroGImported)
			{
	        	throw new BusinessException("Il file delle dichiarazioni non contiene il Quadro G ", ErrorCodes.BUSSINESS_EXCEPTION);
			}
	        
	        if (!isQuadroHImported)
			{
	        	throw new BusinessException("Il file delle dichiarazioni non contiene il Quadro H ", ErrorCodes.BUSSINESS_EXCEPTION);
			}
	        if (!isQuadroIImported)
			{
	        	throw new BusinessException("Il file delle dichiarazioni non contiene il Quadro I ", ErrorCodes.BUSSINESS_EXCEPTION);
			}
	        if (!isQuadroMImported)
			{
	        	throw new BusinessException("Il file delle dichiarazioni non contiene il Quadro M ", ErrorCodes.BUSSINESS_EXCEPTION);
			}
	        if (!isQuadroNImported)
			{
	        	throw new BusinessException("Il file delle dichiarazioni non contiene il Quadro N ", ErrorCodes.BUSSINESS_EXCEPTION);
			}
	        
	        sigasImport.setEsito(EsitoImport.DOWNLOAD_COMPLETE.getEsitoImport());
			
		} catch (BusinessException e) {
			sigasImport.setEsito(EsitoImport.ERROR_DOWNLOAD.getEsitoImport());
			sigasImport.setErrore(e.getMessage());
			throw new BusinessException(e.getMessage(), ErrorCodes.BUSSINESS_EXCEPTION);
		} catch (Exception e) {
			sigasImport.setEsito(EsitoImport.ERROR_DOWNLOAD.getEsitoImport());
			sigasImport.setErrore( "Si e' verificato un errore durante il download del file '" + fileName + "'");
			throw new BusinessException(sigasImport.getErrore(), ErrorCodes.IO_EXCEPTION);
		} finally {
			// salva stato importazione
	       
			if (sigasImport == null) {
				sigasImport = new SigasImportUTF();
			}
	        sigasImport.setAnnualita(annualita);
	    	sigasImport.setImportDate(new Date());
	    	sigasImport.setUserID(username);	    	
	    	
	    	//NON NECESSARIO
	    	//sigasImport.setSelectedImport(false);
	    	
	    	sigasImport = sigasImportRepository.save(sigasImport);
		}
    	
    	logger.info("Il file '"+ fileName + "' e' stato caricaricato");
    	
    	importUtfVO.setImportId(sigasImport.getIdImport());
    	importUtfVO.setFilename(fileName);
    	importUtfVO.setAnno(annualita);
    	importUtfVO.setNumeroFile(numFiles);
    	
		return importUtfVO;
	}
	
		
	private Long checkAnagraficaSoggettoPresenteInDB(SigasFrontespizio sigasFrontespizio) {
		
		Long idAnagraficaSoggetto = null;
		SigasAnagraficaSoggetti sigasAnagraficaSoggetti = null;		
		
		//Verifica presenza in SIGAS_ANAGRAFICA_SOGGETTI
		logger.info("checkAnagraficaSoggettoPresenteInDB - sigasAnagraficaSoggettiRepository:  " + sigasFrontespizio.getCodiceDitta());
		sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByCodiceAziendaForImport(sigasFrontespizio.getCodiceDitta());
		if(sigasAnagraficaSoggetti==null) {
			
			//Verifica presenza in SIGAS_ANAGRAFICA_SOGGETTI per DENOMINAZIONE e INDIRIZZO
			SigasComune sigasComune = sigasComuneRepository.findByDenomComune(sigasFrontespizio.getComuneSede());
			BigDecimal idComune = new BigDecimal(sigasComune.getIdComune());
			SigasProvincia sigasProvincia = sigasProvinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(sigasFrontespizio.getProvinciaSede());
			BigDecimal idProvincia = new BigDecimal(sigasProvincia.getIdProvincia()); 
			sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByDenominazioneAndIndirizzoForImport(sigasFrontespizio.getDenominazione(), 
																												 sigasFrontespizio.getIndirizzoSede(),
																												 idProvincia,
																												 idComune);
			if(sigasAnagraficaSoggetti == null) {
				List<SigasStoricoAnagraficaSoggetti>  sigasStoricoAnagraficaSoggetti = null;
				
				//Verifica presenza in SIGAS_STORICO_ANAGRAFICA_SOGGETTI - CODICE_AZIENDA
				sigasStoricoAnagraficaSoggetti = sigasStoricoAnagraficaSoggettiRepository.findByCodiceAziendaForImport(sigasFrontespizio.getCodiceDitta());
				logger.info("checkAnagraficaSoggettoPresenteInDB - sigasStoricoAnagraficaSoggettiRepository:  " + sigasFrontespizio.getCodiceDitta());
				if(sigasStoricoAnagraficaSoggetti == null || sigasStoricoAnagraficaSoggetti.isEmpty()) {
					
					//Verifica presenza in SIGAS_STORICO_ANAGRAFICA_SOGGETTI - DENOMINAZIONE
					sigasStoricoAnagraficaSoggetti = sigasStoricoAnagraficaSoggettiRepository.findByDenominazioneForImport(sigasFrontespizio.getDenominazione());
					logger.info("checkAnagraficaSoggettoPresenteInDB - sigasStoricoAnagraficaSoggettiRepository - findByDenominazione:  " + sigasFrontespizio.getDenominazione());
					if(sigasStoricoAnagraficaSoggetti != null && !sigasStoricoAnagraficaSoggetti.isEmpty() ) {
						idAnagraficaSoggetto = sigasStoricoAnagraficaSoggetti.get(0).getIdAnag();
					} else {
						
						//Verifica presenza in SIGAS_STORICO_ANAGRAFICA_SOGGETTI - INDIRIZZO
						sigasStoricoAnagraficaSoggetti = sigasStoricoAnagraficaSoggettiRepository.findByIndirizzoForImport(sigasFrontespizio.getIndirizzoSede(),
																														   idProvincia,
																														   idComune);
						logger.info("checkAnagraficaSoggettoPresenteInDB - sigasStoricoAnagraficaSoggettiRepository - findByIndirizzoForImport:  " + sigasFrontespizio.getIndirizzoSede());
						if(sigasStoricoAnagraficaSoggetti != null && !sigasStoricoAnagraficaSoggetti.isEmpty() ) {
							idAnagraficaSoggetto = sigasStoricoAnagraficaSoggetti.get(0).getIdAnag();
						}					
					}
					
				} else {
					logger.info("checkAnagraficaSoggettoPresenteInDB - IdAnag(0):  " + sigasStoricoAnagraficaSoggetti.get(0).getIdAnag());
					idAnagraficaSoggetto = sigasStoricoAnagraficaSoggetti.get(0).getIdAnag();				
				}
				
			} else {				
				logger.info("checkAnagraficaSoggettoPresenteInDB - IdAnag:  " + sigasAnagraficaSoggetti.getIdAnag());
				idAnagraficaSoggetto = sigasAnagraficaSoggetti.getIdAnag();								
			}			
						
		} else {
			logger.info("checkAnagraficaSoggettoPresenteInDB - IdAnag:  " + sigasAnagraficaSoggetti.getIdAnag());
			idAnagraficaSoggetto = sigasAnagraficaSoggetti.getIdAnag();
		}
		logger.info("checkAnagraficaSoggettoPresenteInDB - idAnagraficaSoggetto:  " + idAnagraficaSoggetto);
		return idAnagraficaSoggetto;		
	}
	
	private boolean esistonoDifferenze(SigasFrontespizio sigasFrontespizio, SigasAnagraficaSoggetti sigasAnagraficaSoggetti, long idImport) {
		
		String regularExpCleanChar = "[\\s,.]";
		
		String codiceAziendaFrontespizio = sigasFrontespizio.getCodiceDitta() == null ? "" : sigasFrontespizio.getCodiceDitta().replaceAll(regularExpCleanChar, "");
		String codiceAziendaAnagrafica = sigasAnagraficaSoggetti.getCodiceAzienda() == null ? "" : sigasAnagraficaSoggetti.getCodiceAzienda().replaceAll(regularExpCleanChar, "");
		
		String denominazioneFrontespizio = sigasFrontespizio.getDenominazione() == null ? "" : sigasFrontespizio.getDenominazione().replaceAll(regularExpCleanChar, "");
		String denominazioneAnagrafica = sigasAnagraficaSoggetti.getDenominazione() == null ? "" : sigasAnagraficaSoggetti.getDenominazione().replaceAll(regularExpCleanChar, "");
		
		String indirizzoSedeFrontespizio = sigasFrontespizio.getIndirizzoSede() == null ? "" : sigasFrontespizio.getIndirizzoSede().replaceAll(regularExpCleanChar, "");
		String indirizzoSedeAnagrafica = sigasAnagraficaSoggetti.getIndirizzo() == null ? "" : sigasAnagraficaSoggetti.getIndirizzo().replaceAll(regularExpCleanChar, "");
						
		if(!codiceAziendaFrontespizio.equalsIgnoreCase(codiceAziendaAnagrafica)) {
			return true;
		}
		
		if(!denominazioneFrontespizio.equalsIgnoreCase(denominazioneAnagrafica)) {
			return true;
		}
		
		if(!indirizzoSedeFrontespizio.equals(indirizzoSedeAnagrafica)) {
			return true;
		}		
		
		if(sigasAnagraficaSoggetti.getSigasImport() != null && 
		   sigasAnagraficaSoggetti.getSigasImport().getIdImport() != idImport &&
		   (sigasAnagraficaSoggetti.getSelectedImport()==null || sigasAnagraficaSoggetti.getSelectedImport()==false)) 
		{
			return true;
		}
		
		return false;
	}
	
	/*
	private Long checkVariazioneAnagraficaSoggettoPresenteInDB(String codiceDitta, String denominazione) {
				
		SigasVariazioneAnagraficaImportUTF sigasVariazioneAnagraficaImportUTF = null;				
		
		//Verifica presenza in Variaziona Anagrafica
		sigasVariazioneAnagraficaImportUTF = sigasVariazioneAnagraficaImportUTFRepository.findByDenominazione(denominazione);
		if(sigasVariazioneAnagraficaImportUTF==null) {
			
			sigasVariazioneAnagraficaImportUTF = sigasVariazioneAnagraficaImportUTFRepository.findByCodiceAzienda(codiceDitta);
			if(sigasVariazioneAnagraficaImportUTF!=null) {
				return sigasVariazioneAnagraficaImportUTF.getIdVariazioneAnag();
			}
			else {
				return null;
			}
						
		} else {
			return sigasVariazioneAnagraficaImportUTF.getIdVariazioneAnag();
		}	
				
	}
	*/
    
    // Caricamento dati in DB
    @Transactional
    @Override
    public void populateConsumi(String anno, long importId) {
    
    	SigasImportUTF sigasImport = sigasImportRepository.findOne(importId);
    	
    	if (sigasImport == null) {
    		throw new BusinessException("Non e' stata importata nessuna dichiarazione UTF per l'annualita' '" + anno + "'", ErrorCodes.BUSSINESS_EXCEPTION);
    	}
    	
    	if (sigasImport.getEsito() == EsitoImport.CALCULATE_INPROGRESS.getEsitoImport()) {
    	
	    	try {
		    	
	    		//Lista anagrafica frontespizio inseriti nello storico durante l'iterazione del frontespizio dell'import che si sta tentando di fare
	    		List<SigasFrontespizio> sigasFrontespizioInseritiInStoricoList = new ArrayList<SigasFrontespizio>();
	    		
		    	// Legge anagrafica dalla tabella Frontespizio
		    	List<SigasFrontespizio> sigasFrontespizioList = sigasFrontespizioRepository.findByImportOrderByCodiceDitta(sigasImport);
		    	
		    	String currentCodiceDitta = null;
		    	SigasAnagraficaSoggetti sigasAnagraficaSoggetti = null;
		
		    	for (SigasFrontespizio sigasFrontespizio : sigasFrontespizioList) {
		    		
		    		if (!sigasFrontespizio.getCodiceDitta().equals(currentCodiceDitta)) {
		    			
		    			currentCodiceDitta = sigasFrontespizio.getCodiceDitta();
		    		
		    			logger.info("Soggetto: '" + sigasFrontespizio.getCodiceDitta() + "' - '" + sigasFrontespizio.getDenominazione() + "'" );
		    			
		    			//----------------------------------------------
		    			// Aggiornamento anagrafica
		    			//----------------------------------------------
		    			//OLD CODE
		    			//sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByCodiceAzienda(sigasFrontespizio.getCodiceDitta());
		    			
		    			//---------------------------------------------
		    			//NEW Version
		    			//---------------------------------------------
		    			Long idAnagraficaSoggetto = checkAnagraficaSoggettoPresenteInDB(sigasFrontespizio);
		    			if(idAnagraficaSoggetto != null) {
		    				sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnagPerImportFrontespizio(idAnagraficaSoggetto);
		    			} else {
		    				
		    				//Save in sigas_anagrafica. Si tratta di un NUOVO item.
		    				sigasAnagraficaSoggetti = new SigasAnagraficaSoggetti();
		    				sigasAnagraficaSoggetti = this.salvaAnagraficaByImportUTF(sigasAnagraficaSoggetti, sigasFrontespizio, sigasImport);
		    						    				
		    				//Save in storico.			    			
			    			this.salvaStoricoAnagrafica(sigasAnagraficaSoggetti, sigasFrontespizio, OWNER_UPDATE_SOGGETTO_ANAG, importId, anno);
			    			
		    				
		    				//inserimento nella lista che tiene traccia dei frontespizi storicizzati
		    				sigasFrontespizioInseritiInStoricoList.add(sigasFrontespizio);
		    			}
		    			
		    			if(sigasAnagraficaSoggetti!=null){
		    				
		    				//se true l'oggetto sigasFrontespizio del ciclo for ha una combo codice_ditta, denominazione e indirizzo sede
		    				//non ancora esaminata durante questo import
		    				boolean corrispondenzaInserita = false;
		    				
		    				Iterator<SigasFrontespizio> iterator = sigasFrontespizioInseritiInStoricoList.iterator();
		    				while(iterator.hasNext()) {
		    					SigasFrontespizio sigasFrontespizioNext = iterator.next();
		    					if(sigasFrontespizioNext.getCodiceDitta().equals(sigasFrontespizio.getCodiceDitta()) &&
		    							sigasFrontespizioNext.getDenominazione().equals(sigasFrontespizio.getDenominazione()) &&
		    							sigasFrontespizioNext.getIndirizzoSede().equals(sigasFrontespizio.getIndirizzoSede()) &&
		    							sigasFrontespizioNext.getSigasImport().getIdImport() == importId) {
		    						corrispondenzaInserita = true;
		    					}
		    				}
		    				
			    			if(this.esistonoDifferenze(sigasFrontespizio, sigasAnagraficaSoggetti, importId) && !corrispondenzaInserita) 
			    			{
			    				this.salvaStoricoAnagraficaBeforUpdate(sigasAnagraficaSoggetti, OWNER_UPDATE_SOGGETTO_ANAG, importId, anno);
			    				
			    				//Save in storico.			    			
					    		this.salvaStoricoAnagrafica(sigasAnagraficaSoggetti, sigasFrontespizio, OWNER_UPDATE_SOGGETTO_ANAG, importId, anno);
					    		//inserimento nella lista che tiene traccia dei frontespizi storicizzati
			    				sigasFrontespizioInseritiInStoricoList.add(sigasFrontespizio);
			    			}
		    			}
		    			
		    			//----------------------------------------------
		    			//
		    			//----------------------------------------------
		    			
		    			// Controllo dichiarazione UTF
		    			if(sigasAnagraficaSoggetti!=null){
		    				checkDichiarazione(sigasAnagraficaSoggetti, sigasFrontespizio.getAnno(), sigasImport.getUserID());
		    			}		    			
		    			
		    		} else {
		    			//-------------------------------
		    			// MARTS REQ-16
		    			//-------------------------------
		    			/* 
		    			 * ESTRATTO FRONTESPIZIO - 2022
		    			 * 
		    			21/08/2023	TOO00313M	AGN ENERGIA SPA	TO	VOLPIANO	VIA AMALFI 6	S	2022	AL
		    			21/08/2023	TOO00313M	AGN ENERGIA SPA	TO	VOLPIANO	VIA AMALFI 6	S	2022	AT
		    			21/08/2023	TOO00313M	AGN ENERGIA SPA	TO	VOLPIANO	VIA AMALFI 6	S	2022	BI
		    			21/08/2023	TOO00313M	AGN ENERGIA SPA	TO	VOLPIANO	VIA AMALFI 6	S	2022	CN
		    			21/08/2023	TOO00313M	AGN ENERGIA SPA	TO	VOLPIANO	VIA AMALFI 6	S	2022	NO
		    			21/08/2023	TOO00313M	AGN ENERGIA SPA	TO	VOLPIANO	VIA AMALFI 6	S	2022	TO
		    			21/08/2023	TOO00313M	AGN ENERGIA SPA	TO	VOLPIANO	VIA AMALFI 6	S	2022	VB
		    			21/08/2023	TOO00313M	AGN ENERGIA SPA	TO	VOLPIANO	VIA AMALFI 6	S	2022	VC
		    			*/
		    			//-------------------------------------------------
		    			//Avendo lo stesso codice si tiene traccia in sigas_storico _anagrafica e si effettua il save in sigas_anagrafica di eventuali 
		    			//variazioni dati indicati nel FRONTESPIZIO. Save solo in storico. Il passaggio in anagrafica soggetto dovrebbe avvenire alla conferma 
		    			//dell'import da parte dell'utente
		    			//-------------------------------------------------
		    					    			
		    			Long idAnagraficaSoggetto = checkAnagraficaSoggettoPresenteInDB(sigasFrontespizio);
		    			if(idAnagraficaSoggetto != null) {
		    				sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnagPerImportFrontespizio(idAnagraficaSoggetto);
		    			} else {
		    				
		    				//Save in sigas_anagrafica. Si tratta di un NUOVO item.
		    				sigasAnagraficaSoggetti = new SigasAnagraficaSoggetti();
		    				sigasAnagraficaSoggetti = this.salvaAnagraficaByImportUTF(sigasAnagraficaSoggetti, sigasFrontespizio, sigasImport);
		    				
		    				//Save in storico.			    			
			    			this.salvaStoricoAnagrafica(sigasAnagraficaSoggetti, sigasFrontespizio, OWNER_UPDATE_SOGGETTO_ANAG, importId, anno);
			    			
			    			//inserimento nella lista che tiene traccia dei frontespizi storicizzati
		    				sigasFrontespizioInseritiInStoricoList.add(sigasFrontespizio);
		    			}
		    			
		    			if(sigasAnagraficaSoggetti!=null) {
		    				
		    				//se true l'oggetto sigasFrontespizio del ciclo for ha una combo codice_ditta, denominazione e indirizzo sede
		    				//non ancora esaminata durante questo import
		    				boolean corrispondenzaInserita = false;
		    				
		    				Iterator<SigasFrontespizio> iterator = sigasFrontespizioInseritiInStoricoList.iterator();
		    				while(iterator.hasNext()) {
		    					SigasFrontespizio sigasFrontespizioNext = iterator.next();
		    					if(sigasFrontespizioNext.getCodiceDitta().equals(sigasFrontespizio.getCodiceDitta()) &&
		    							sigasFrontespizioNext.getDenominazione().equals(sigasFrontespizio.getDenominazione()) &&
		    							sigasFrontespizioNext.getIndirizzoSede().equals(sigasFrontespizio.getIndirizzoSede()) &&
		    							sigasFrontespizioNext.getSigasImport().getIdImport() == importId) {
		    						corrispondenzaInserita = true;
		    					}
		    				}
		    				
			    			if(this.esistonoDifferenze(sigasFrontespizio, sigasAnagraficaSoggetti, importId)  && !corrispondenzaInserita) 
			    			{
			    				this.salvaStoricoAnagraficaBeforUpdate(sigasAnagraficaSoggetti, OWNER_UPDATE_SOGGETTO_ANAG, importId, anno);
			    				
			    				//Save in storico.			    				
			    				this.salvaStoricoAnagrafica(sigasAnagraficaSoggetti, sigasFrontespizio, OWNER_UPDATE_SOGGETTO_ANAG, importId, anno);
			    				//inserimento nella lista che tiene traccia dei frontespizi storicizzati
			    				sigasFrontespizioInseritiInStoricoList.add(sigasFrontespizio);
			    			}
		    			}
		    			
		    		}
		    		
		    		// Popolamento Consumi
		    		SigasDichConsumi sigasDichConsumi = new SigasDichConsumi();
		    		
		    		// Recupera dati QUADRO M per codice azienda, provincia 
		    		List<SigasQuadroM> sigasQuadroMList = sigasQuadroMRepository.findByCodiceDittaAndProvinciaAndImportUTF(sigasFrontespizio.getCodiceDitta(), 
		    																											   sigasFrontespizio.getProvincia(), 
		    																											   sigasFrontespizio.getSigasImport(), 
		    																											   sigasFrontespizio.getAnno());
		    		
		    		// Calcola i consumi
		    		int usiIndustrialiUp = 0;
		    		int usiIndustriali1200 = 0;
		    		int usiCivili120 = 0;
		    		int usiCivili480 = 0;
		    		int usiCivili1560 = 0;
		    		int usiCiviliUp = 0;
		    		double totNuoviAllacciamenti = 0.0;
		    		double totCivili120 = 0, totCivili480 = 0, totCivili1560 = 0, totCiviliUp = 0;
		    		double totIIndustriali = 0, totIIndustrialiUp = 0, totIIndustriali1200 = 0;
		    		double  totaleDich = 0, arrotondamenti = 0, rettifiche = 0;
		    		List<SigasDichScarti> sigasDichScartiList = new ArrayList<SigasDichScarti>();

		    		for (SigasQuadroM sigasQuadroM : sigasQuadroMList) {
		    			
		    			if (sigasQuadroM.getAliquota() != 0) {
		    				// Cerca tipo consumo per aliquota e prog_rigo
			    			SigasAliquote sigasAliquote = sigasAliquoteRepository.findByAliquotaAndProgRigo(sigasQuadroM.getAliquota(),
			    																							sigasQuadroM.getProgRigo(), 
			    																							Integer.parseInt(sigasFrontespizio.getAnno()));
			    			
			    			GregorianCalendar aliquotaStartDate = null;
			    			GregorianCalendar aliquotaEndDate = null;
			    			if (sigasAliquote != null) {
			    				aliquotaStartDate = new GregorianCalendar();
				    			aliquotaStartDate.setTime(sigasAliquote.getValiditaStart());
				    			aliquotaEndDate = new GregorianCalendar();
				    			aliquotaEndDate.setTime(sigasAliquote.getValiditaEnd());
			    			}
			    			
			    			if (sigasAliquote != null && 
			    					Integer.parseInt(sigasFrontespizio.getAnno()) >= aliquotaStartDate.get(Calendar.YEAR) &&
			    					Integer.parseInt(sigasFrontespizio.getAnno()) <= aliquotaEndDate.get(Calendar.YEAR) ) {
			    			
			    				switch(sigasAliquote.getSigasTipoAliquote().getSigasTipoConsumo().getCampoDichConsumo()) {
			    					case "usi_industriali_up":
			    						usiIndustrialiUp += sigasQuadroM.getConsumi();			    						
			    						totIIndustrialiUp += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
			    						break;
			    					case "usi_industriali_1200":
			    						usiIndustriali1200 += sigasQuadroM.getConsumi();			    						
			    						totIIndustriali1200 += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
			    						break;
			    					case "usi_civili_120":
			    						usiCivili120 += sigasQuadroM.getConsumi();			    						
			    						totCivili120 += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
			    						break;
			    					case "usi_civili_480":
			    						usiCivili480 += sigasQuadroM.getConsumi();			    						
			    						totCivili480 += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
			    						break;
			    					case "usi_civili_1560":
			    						usiCivili1560 += sigasQuadroM.getConsumi();			    						
			    						totCivili1560 += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
			    						break;
			    					case "usi_civili_up":
			    						usiCiviliUp += sigasQuadroM.getConsumi();
			    						totCiviliUp += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
			    						break;
			    					case "tot_nuovi_allacciamenti":
			    						totNuoviAllacciamenti += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
			    						break;			    						
			    				}
			    			} else { 
			    				// Salva lo scarto in DB
			    				SigasDichScarti sigasDichScarti = new SigasDichScarti();
			    				
			    				sigasDichScarti.setAliquota(sigasQuadroM.getAliquota());
			    				sigasDichScarti.setConsumi(sigasQuadroM.getConsumi());			    				
			    				sigasDichScarti.setImposta(sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota());
			    				sigasDichScarti.setProvincia(sigasQuadroM.getProvincia());
			    				sigasDichScarti.setSigasDichConsumi(sigasDichConsumi);			    				
			    				
		    					List<SigasAliquote> listAliquote = sigasAliquoteRepository.findByProgRigoAndAnno(sigasQuadroM.getProgRigo(), Integer.parseInt(sigasFrontespizio.getAnno()) );
		    					
		    					List<String> descUso = new ArrayList<>();
		    					for (SigasAliquote aliquota : listAliquote) {
		    						if(!descUso.contains(aliquota.getSigasTipoAliquote().getSigasTipoConsumo().getDescrizione()))
		    							descUso.add(aliquota.getSigasTipoAliquote().getSigasTipoConsumo().getDescrizione());
								}
		    					sigasDichScarti.setDescUsoScarto(StringUtils.join(descUso," - "));			    				
		
			    				sigasDichScartiList.add(sigasDichScarti);			    				
			    			}
		    			}
		    			if(2018<=Integer.parseInt(sigasFrontespizio.getAnno())) {
		    				if (sigasQuadroM.getProgRigo().equals(String.valueOf(progRigoInizioTotali_2018+2)) )
			    				totaleDich = sigasQuadroM.getImposta();
			    			else if (sigasQuadroM.getProgRigo().equals(String.valueOf(progRigoInizioTotali_2018+1)) )
			    				arrotondamenti = sigasQuadroM.getImposta();
			    			else if (sigasQuadroM.getProgRigo().equals(String.valueOf(progRigoInizioTotali_2018)) )
			    				rettifiche += sigasQuadroM.getImposta();
		    			}else {
		    				if (sigasQuadroM.getProgRigo().equals(String.valueOf(progRigoInizioTotali_2017+2)) )
			    				totaleDich = sigasQuadroM.getImposta();
			    			else if (sigasQuadroM.getProgRigo().equals(String.valueOf(progRigoInizioTotali_2017+1)) )
			    				arrotondamenti = sigasQuadroM.getImposta();
			    			else if (sigasQuadroM.getProgRigo().equals(String.valueOf(progRigoInizioTotali_2017)) )
			    				rettifiche += sigasQuadroM.getImposta();
		    			}
		    			
		    		}
		    		
		    		// Recupera dati QUADRO M per codice azienda, provincia
		    		List<SigasQuadroN> sigasQuadroNList = sigasQuadroNRepository.findByImportOrderByCodiceDitta(sigasFrontespizio.getCodiceDitta(), 
		    																									sigasFrontespizio.getProvincia(), 
		    																									sigasFrontespizio.getSigasImport());
		    		
		    		boolean anomaliaImpostaProgRig3eProgRig4 = verificaAnomaliaQuadroNProgRigo3ProgRigo4(sigasQuadroNList);
		    		
		    		double conguaglioDich = 0;
		    		double adLiquidata = 0;
		    		double rateo = 0;
		    		if (sigasQuadroNList != null) {
		    			for(SigasQuadroN sigasQuadroN : sigasQuadroNList) {
			    			if ( sigasQuadroN.getProgRigo().equals("1"))
			    				adLiquidata = sigasQuadroN.getImposta();
			    			else if ( sigasQuadroN.getProgRigo().equals("3")) {
			    				//conguaglioDich = +sigasQuadroN.getImposta();
			    				if(anomaliaImpostaProgRig3eProgRig4) {
			    					conguaglioDich = 0;
			    				} else if(sigasQuadroN.getImposta()!=0) {
			    					conguaglioDich = +sigasQuadroN.getImposta();
			    				}			    				
			    			} else if ( sigasQuadroN.getProgRigo().equals("4")) {
			    				//conguaglioDich = -sigasQuadroN.getImposta();
			    				if(anomaliaImpostaProgRig3eProgRig4) {
			    					conguaglioDich = 0;
			    				} else if(sigasQuadroN.getImposta()!=0) {
			    					conguaglioDich = -sigasQuadroN.getImposta();
			    				}
			    				
			    			} else if (sigasQuadroN.getProgRigo().equals("912"))
			    				rateo = sigasQuadroN.getImposta();
		    			}
		    		}
		    		
		    		// Recupera Versamenti per conguaglio calolato
		    		double versamenti = 0;
		    		try {
		    			if(sigasAnagraficaSoggetti!=null) {
		    				versamenti = sigasDichVersamentiRepository.sumByAnnoAndProvinciaAndSoggetto(sigasFrontespizio.getAnno(), 
																										sigasFrontespizio.getProvincia(), 
																										sigasAnagraficaSoggetti);
		    			}
		    			
		    		} catch(Exception e) {
		    			logger.debug("Nessun Versamento trovato per il Soggetto '" + sigasAnagraficaSoggetti.getCodiceAzienda() + 
		    					 	 " nell'anno " + sigasFrontespizio.getAnno() + 
		    					 	 " e per la provincia '" + sigasFrontespizio.getProvincia());
		    		}		    		
		    		
		    		// SIGAS 57 - totale industriale come somma delle imposte
		    		totIIndustriali = totIIndustrialiUp + totIIndustriali1200;
		    		
		    		if (sigasQuadroMList.size() != 0 && sigasAnagraficaSoggetti != null) {
			    		sigasDichConsumi.setSigasAnagraficaSoggetti(sigasAnagraficaSoggetti);
			    		sigasDichConsumi.setSigasImport(sigasFrontespizio.getSigasImport());
			    		sigasDichConsumi.setAnnualita(sigasFrontespizio.getAnno());
			    		sigasDichConsumi.setProvinciaErogazione(sigasFrontespizio.getProvincia());
			    		sigasDichConsumi.setDataPresentazione(sigasFrontespizio.getDataPresentazione());
			    		sigasDichConsumi.setUsiIndustrialiUp(usiIndustrialiUp);
			    		sigasDichConsumi.setUsiIndustriali1200(usiIndustriali1200);
			    		sigasDichConsumi.setUsiCivili120(usiCivili120);
			    		sigasDichConsumi.setUsiCivili480(usiCivili480);
			    		sigasDichConsumi.setUsiCivili1560(usiCivili1560);
			    		sigasDichConsumi.setUsiCiviliUp(usiCiviliUp);
			    		sigasDichConsumi.setTotNuoviAllacciamenti(totNuoviAllacciamenti);
			    		sigasDichConsumi.setTotIndustriali(totIIndustriali);
			    		sigasDichConsumi.setTotCivili(totCivili120 + totCivili480 + totCivili1560 + totCiviliUp);
			    		sigasDichConsumi.setConguaglioDich(conguaglioDich);
			    		sigasDichConsumi.setRateoDich(rateo);
			    		sigasDichConsumi.setAddizionaleLiquidata(adLiquidata);
//			    		SIGAS-72
//			    		sigasDichConsumi.setConguaglioCalcolato(totaleDich - versamenti);
			    		sigasDichConsumi.setConguaglioCalcolato(totIIndustriali+totCivili120 + totNuoviAllacciamenti +totCivili480 + totCivili1560 + totCiviliUp + rettifiche + arrotondamenti - versamenti);
			    		sigasDichConsumi.setTotaleDich(totaleDich);
			    		sigasDichConsumi.setTotaleDichOrigine(totaleDich);
			    		sigasDichConsumi.setTotaleCalcolato(totIIndustriali+totCivili120 + totNuoviAllacciamenti +totCivili480 + totCivili1560 + totCiviliUp + rettifiche + arrotondamenti);
			    		sigasDichConsumi.setRettifiche(rettifiche);
			    		sigasDichConsumi.setArrotondamenti(arrotondamenti);
			    		
			    		sigasDichConsumi.setSelectedImport(false);
			    		
			    		sigasDichConsumi = sigasDichConsumiRepository.save(sigasDichConsumi);		    		

		    		
			    		// Salva in DB gli eventuali scarti
			    		for (SigasDichScarti sigasDichScarti : sigasDichScartiList) {
			    			sigasDichScarti.setSigasDichConsumi(sigasDichConsumi);
			    			sigasDichScartiRepository.save(sigasDichScarti);
			    		}
			    		
			    		if (sigasDichScartiList.size() > 0) {
			    			
			    			addAlarm(sigasAnagraficaSoggetti.getCodiceAzienda(),sigasDichConsumi,null,TipoAllarme.SCARTI.getName(),sigasImport.getUserID() ,
			    					StatusAllarme.ATTIVO.getName(), "Rilevati scarti per il soggetto " + sigasAnagraficaSoggetti.getCodiceAzienda(),
			    					anno);
			    		}						
						// Controllo coerenza
						checkCoerenza(sigasDichConsumi);
		    		} else {
		    			
		    			if(sigasAnagraficaSoggetti!=null) {
		    				sigasDichConsumi.setSigasAnagraficaSoggetti(sigasAnagraficaSoggetti);
				    		sigasDichConsumi.setSigasImport(sigasFrontespizio.getSigasImport());
				    		sigasDichConsumi.setAnnualita(sigasFrontespizio.getAnno());
				    		sigasDichConsumi.setProvinciaErogazione(sigasFrontespizio.getProvincia());
				    		sigasDichConsumi.setDataPresentazione(sigasFrontespizio.getDataPresentazione());
				    		sigasDichConsumi.setConguaglioDich(conguaglioDich);
				    		sigasDichConsumi.setRateoDich(rateo);
				    		sigasDichConsumi.setAddizionaleLiquidata(adLiquidata);
				    		
				    		sigasDichConsumi.setSelectedImport(false);
				    		
				    		sigasDichConsumi = sigasDichConsumiRepository.save(sigasDichConsumi);
		    			}		    			
			    	}
		    	} 
		    	// Aggiorna stato import
		    	sigasImport.setEsito(EsitoImport.IMPORT_COMPLETE.getEsitoImport());
		
	    	} catch (Exception e) {
				sigasImport.setEsito(EsitoImport.ERROR_CALCULATE.getEsitoImport());
				sigasImport.setErrore(e.getMessage());
				throw new BusinessException(e.getMessage(), ErrorCodes.BUSSINESS_EXCEPTION);
			} finally {
				// Salva stato import
				sigasImportRepository.save(sigasImport);
			}
    	} else {
        	logger.warn("Non e' possibile calcolare i consumi perche' l'import e' in fase '" + sigasImport.getEsito() + "'");
        }
    }
    
    private SigasAnagraficaSoggetti _formattaAnagarficaObjectFromFrontespizio(SigasFrontespizio sigasFrontespizio) {
    	SigasAnagraficaSoggetti sigasAnagraficaSoggetti = new SigasAnagraficaSoggetti();
    	sigasAnagraficaSoggetti.setCodiceAzienda(sigasFrontespizio.getCodiceDitta());
		sigasAnagraficaSoggetti.setDenominazione(sigasFrontespizio.getDenominazione());
		sigasAnagraficaSoggetti.setIndirizzo(sigasFrontespizio.getIndirizzoSede());
		sigasAnagraficaSoggetti.setTipo(sigasFrontespizio.getTipoSoggetto());		
		
		// Fk Provincia	
		SigasProvincia sigasProvincia = sigasProvinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(sigasFrontespizio.getProvinciaSede());
		if (sigasProvincia == null) {
			sigasProvincia = sigasProvinciaRepository.findOne((long) 0);
		}
		sigasAnagraficaSoggetti.setSigasProvincia(sigasProvincia);

		// Fk Comune
		SigasComune sigasComune = sigasComuneRepository.findByDenomComune(sigasFrontespizio.getComuneSede());
		if (sigasComune == null) {
			sigasComune = sigasComuneRepository.findOne((long) 0);
		}
		sigasAnagraficaSoggetti.setSigasComune(sigasComune);
		
		return sigasAnagraficaSoggetti;
    }
    
    private SigasAnagraficaSoggetti salvaAnagraficaByImportUTF(SigasAnagraficaSoggetti sigasAnagraficaSoggetti, SigasFrontespizio sigasFrontespizio, 
    														   SigasImportUTF sigasImport) 
    {
    	sigasAnagraficaSoggetti.setCodiceAzienda(sigasFrontespizio.getCodiceDitta());
		sigasAnagraficaSoggetti.setDenominazione(sigasFrontespizio.getDenominazione());
		sigasAnagraficaSoggetti.setIndirizzo(sigasFrontespizio.getIndirizzoSede());
		sigasAnagraficaSoggetti.setTipo(sigasFrontespizio.getTipoSoggetto());
		sigasAnagraficaSoggetti.setSigasImport(sigasImport);
		
		
		// Fk Provincia	
		SigasProvincia sigasProvincia = sigasProvinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(sigasFrontespizio.getProvinciaSede());
		if (sigasProvincia == null) {
			sigasProvincia = sigasProvinciaRepository.findOne((long) 0);
		}
		sigasAnagraficaSoggetti.setSigasProvincia(sigasProvincia);

		// Fk Comune
		SigasComune sigasComune = sigasComuneRepository.findByDenomComune(sigasFrontespizio.getComuneSede());
		if (sigasComune == null) {
			sigasComune = sigasComuneRepository.findOne((long) 0);
		}
		sigasAnagraficaSoggetti.setSigasComune(sigasComune);
		
		//Nuova anagrafica d'abilitare mediante funzionalità conferma presente nella GUI.
		sigasAnagraficaSoggetti.setSelectedImport(FLG_NOT_SELECTED_IMPORT);
		
		// Salva in DB
		logger.info("salvaAnagraficaByImportUTF: \n " + XmlSerializer.objectToXml(sigasAnagraficaSoggetti));
		sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.save(sigasAnagraficaSoggetti);
		
		return sigasAnagraficaSoggetti;
    }
    
    private void salvaStoricoAnagrafica(SigasAnagraficaSoggetti sigasAnagraficaSoggettiBeforeUpdate, SigasFrontespizio sigasFrontespizio, 
    									String ownerOperazione, Long idImport, String annualita) 
    {
		if(sigasAnagraficaSoggettiBeforeUpdate == null || sigasFrontespizio == null) {
			return;
		}		
		
		SigasStoricoAnagraficaSoggetti sigasStoricoAnagraficaSoggetti = new SigasStoricoAnagraficaSoggetti();		
		sigasStoricoAnagraficaSoggetti.setDataRiferimento(Calendar.getInstance().getTime());		
		sigasStoricoAnagraficaSoggetti.setEmail(sigasAnagraficaSoggettiBeforeUpdate.getEmail());
		sigasStoricoAnagraficaSoggetti.setIban(sigasAnagraficaSoggettiBeforeUpdate.getIban());
		sigasStoricoAnagraficaSoggetti.setIdAnag(sigasAnagraficaSoggettiBeforeUpdate.getIdAnag());
		//sigasStoricoAnagraficaSoggetti.setIndirizzo(sigasAnagraficaSoggettiBeforeUpdate.getIndirizzo());
		sigasStoricoAnagraficaSoggetti.setPec(sigasAnagraficaSoggettiBeforeUpdate.getPec());
		sigasStoricoAnagraficaSoggetti.setCfPiva(sigasAnagraficaSoggettiBeforeUpdate.getCfPiva());
		sigasStoricoAnagraficaSoggetti.setOwnerOperazione(ownerOperazione);
		sigasStoricoAnagraficaSoggetti.setIdImport(idImport);
		sigasStoricoAnagraficaSoggetti.setAnnualita(annualita);
				
		//Da frontespizio quindi da NUOVO IMPORT
		sigasStoricoAnagraficaSoggetti.setCodiceAzienda(sigasFrontespizio.getCodiceDitta());
		sigasStoricoAnagraficaSoggetti.setDenominazione(sigasFrontespizio.getDenominazione());
		sigasStoricoAnagraficaSoggetti.setIndirizzo(sigasFrontespizio.getIndirizzoSede());
		
		// Fk Provincia	
		SigasProvincia sigasProvincia = sigasProvinciaRepository.findBySiglaProvinciaAndFineValiditaIsNull(sigasFrontespizio.getProvinciaSede());
		if (sigasProvincia == null) {
			sigasProvincia = sigasProvinciaRepository.findOne((long) 0);
		}
		sigasStoricoAnagraficaSoggetti.setSigasProvincia(sigasProvincia);

		// Fk Comune
		SigasComune sigasComune = sigasComuneRepository.findByDenomComune(sigasFrontespizio.getComuneSede());
		if (sigasComune == null) {
			sigasComune = sigasComuneRepository.findOne((long) 0);
		}
		sigasStoricoAnagraficaSoggetti.setSigasComune(sigasComune);
		
		
		logger.info("salvaStoricoAnagrafica: \n " + XmlSerializer.objectToXml(sigasStoricoAnagraficaSoggetti));
		this.sigasStoricoAnagraficaSoggettiRepository.save(sigasStoricoAnagraficaSoggetti);		
	}
    
    private void salvaStoricoAnagraficaBeforUpdate(SigasAnagraficaSoggetti sigasAnagraficaSoggettiBeforeUpdate,String ownerOperazione, Long idImport, String annualita) 
	{
		if(sigasAnagraficaSoggettiBeforeUpdate == null) {
			return;
		}		
		
		SigasStoricoAnagraficaSoggetti sigasStoricoAnagraficaSoggetti = new SigasStoricoAnagraficaSoggetti();		
		sigasStoricoAnagraficaSoggetti.setDataRiferimento(Calendar.getInstance().getTime());		
		sigasStoricoAnagraficaSoggetti.setEmail(sigasAnagraficaSoggettiBeforeUpdate.getEmail());
		sigasStoricoAnagraficaSoggetti.setIban(sigasAnagraficaSoggettiBeforeUpdate.getIban());
		sigasStoricoAnagraficaSoggetti.setIdAnag(sigasAnagraficaSoggettiBeforeUpdate.getIdAnag());	
		sigasStoricoAnagraficaSoggetti.setPec(sigasAnagraficaSoggettiBeforeUpdate.getPec());
		sigasStoricoAnagraficaSoggetti.setCfPiva(sigasAnagraficaSoggettiBeforeUpdate.getCfPiva());
		sigasStoricoAnagraficaSoggetti.setOwnerOperazione(ownerOperazione);
		sigasStoricoAnagraficaSoggetti.setIdImport(idImport);
		sigasStoricoAnagraficaSoggetti.setAnnualita(annualita);
				
		sigasStoricoAnagraficaSoggetti.setCodiceAzienda(sigasAnagraficaSoggettiBeforeUpdate.getCodiceAzienda());
		sigasStoricoAnagraficaSoggetti.setDenominazione(sigasAnagraficaSoggettiBeforeUpdate.getDenominazione());
		sigasStoricoAnagraficaSoggetti.setIndirizzo(sigasAnagraficaSoggettiBeforeUpdate.getIndirizzo());
		sigasStoricoAnagraficaSoggetti.setSigasComune(sigasAnagraficaSoggettiBeforeUpdate.getSigasComune());
		sigasStoricoAnagraficaSoggetti.setSigasProvincia(sigasAnagraficaSoggettiBeforeUpdate.getSigasProvincia());
	
		logger.info("salvaStoricoAnagrafica: \n " + XmlSerializer.objectToXml(sigasStoricoAnagraficaSoggetti));
		this.sigasStoricoAnagraficaSoggettiRepository.save(sigasStoricoAnagraficaSoggetti);		
	}	
    
    private Double quadroNCercaImpostaByRigo(List<SigasQuadroN> sigasQuadroNList, String valoreRigo) {
    	Double output = null;
    	
    	if (sigasQuadroNList == null || sigasQuadroNList.isEmpty() || valoreRigo == null ) {
    		return output;    		
    	}    	
    	Iterator<SigasQuadroN> iter = sigasQuadroNList.iterator();
    	while(iter.hasNext()){
    		SigasQuadroN item = iter.next();
    		if(item.getProgRigo()!=null && item.getProgRigo().equalsIgnoreCase(valoreRigo)) {
    			output = item.getImposta();
    		}
    	}    	
    	return output;
    }
    
    private boolean verificaAnomaliaQuadroNProgRigo3ProgRigo4(List<SigasQuadroN> sigasQuadroNList) {
    	boolean rigo3DiversoDaZero = false;
    	boolean rigo4DiversoDaZero = false;
    	
    	Double impostaRigo3 = quadroNCercaImpostaByRigo(sigasQuadroNList, "3");
    	if(impostaRigo3 != null && impostaRigo3 != 0) {
    		rigo3DiversoDaZero = true;
    	}
    	
    	Double impostaRigo4 = quadroNCercaImpostaByRigo(sigasQuadroNList, "4");
    	if(impostaRigo4 != null && impostaRigo4 != 0) {
    		rigo4DiversoDaZero = true;
    	}
    	
    	return rigo3DiversoDaZero && rigo4DiversoDaZero;    	
    }
    
    @Transactional
    @Override
    public List<AnnualitaImportVO> ricercaAnnualita () {
    	
    	int currentYear = new GregorianCalendar().get(Calendar.YEAR);
    	List<AnnualitaImportVO> annualitaImportVOList = new ArrayList<AnnualitaImportVO>();
			
		List<SigasImportUTF> sigasImportUTFList;
		SigasImportUTF sigasImportUTF = null;
		
		for(int year = currentYear - yearToShow; year <= currentYear; year++) {
			String success = "";
			sigasImportUTFList = sigasImportRepository.findByAnno(String.valueOf(year));
			if ( sigasImportUTFList == null || sigasImportUTFList.size() == 0) {
				sigasImportUTF = new SigasImportUTF();
				sigasImportUTF.setAnnualita(String.valueOf(year));
			} else {
				sigasImportUTF = sigasImportUTFList.get(0);
				/*
				int numeroSoggetti = sigasFrontespizioRepository.findByDistinctCodiceDittaAndImportUtf(sigasImportUTF).size();
				int numeroScarti = sigasDichScartiRepository.findBySigasDichConsumiAnnualita(String.valueOf(year)).size();
				int numeroIncoerenza = sigasAllarmiRepository.findBySigasDichConsumiAnnualitaAndSigasTipoAllarmeIdTipoAllarme(String.valueOf(year), 2).size();
				int numeroDocumentiPervenuti = sigasAnaComunicazioniRepository.findByAnnualitaOrderByDataDocumentoAsc(String.valueOf(year)).size();
				success = "Dichiarazioni importate: " + numeroSoggetti + " - " +
						"Numero Scarti: " + numeroScarti + " - " +
						"Numero Incongruenze: " + numeroIncoerenza + " - " +
						"Dichiarazioni con documenti pervenuti: " + numeroDocumentiPervenuti;
				*/
				int numeroSoggetti = sigasFrontespizioRepository.findByDistinctCodiceDittaAndImportUtf(sigasImportUTF).size();
				success = "Dichiarazioni importate: " + numeroSoggetti;
			}
			
			AnnualitaImportVO annualitaVo = annualitaEntityMapper.mapEntityToVO(sigasImportUTF);
			annualitaVo.setSuccess(success);
			
			annualitaImportVOList.add(annualitaVo);
			
			
		}
    	
		return annualitaImportVOList;
    	
    }
    
    @Transactional
    @Override
	public List<ImportUTFVO> getImportUTFListByAnno(Long anno) {
    	if(anno==null) {
    		throw new BusinessException("Parametro anno import mancante", ErrorCodes.BUSSINESS_EXCEPTION);
    	}
    	List<ImportUTFVO> importUTFVOList = null;
    	
    	try 
    	{
    		List<SigasImportUTF> sigasImportUTFList = this.sigasImportRepository.findByAnno(anno.toString());
    		if(sigasImportUTFList!=null) 
    		{    			
    			importUTFVOList = new ArrayList<>();
    			Iterator<SigasImportUTF> iterator = sigasImportUTFList.iterator();
    			while(iterator.hasNext()) {
    				SigasImportUTF sigasImportUTF = iterator.next();
    				ImportUTFVO importUTFVO = new ImportUTFVO();
    				
    				importUTFVO.setAnno(sigasImportUTF.getAnnualita());
    				importUTFVO.setDataRiferimento(sigasImportUTF.getImportDate());
    				importUTFVO.setImportId(sigasImportUTF.getIdImport());
    				importUTFVO.setSelectedImport(sigasImportUTF.getSelectedImport());
    				importUTFVO.setEsito(sigasImportUTF.getEsito());
    				
    				importUTFVOList.add(importUTFVO);
    			}
    		}
    		
    	} catch (Exception e) {			
			throw new BusinessException(e.getMessage(), ErrorCodes.BUSSINESS_EXCEPTION);
		}
    	
		return importUTFVOList;
	}
    
    @Transactional
    @Override
    public List<ConsumiPrVO> getUTFReportByIdImport(Long idImport, Integer annualita){
    	
    	if(idImport==null) {
    		throw new BusinessException("Parametro identificativo import mancante", ErrorCodes.BUSSINESS_EXCEPTION);
    	}
    	
    	List<ConsumiPrVO> utfReportByIdImportList = null;
    	try {
    		List<UTFStandaloneEntityCustom> utfStandaloneEntityCustomList = sigasUTFStandaloneRepository.getUTFReportByIdImport(idImport, annualita);
    		if(utfStandaloneEntityCustomList != null) 
    		{
    			utfReportByIdImportList = new ArrayList<>();
    			Iterator<UTFStandaloneEntityCustom> iterator = utfStandaloneEntityCustomList.iterator();
    			while(iterator.hasNext()) {
    				UTFStandaloneEntityCustom utfStandaloneEntityCustomItem = iterator.next();
    				
    				ConsumiPrVO consumiPrVO = new ConsumiPrVO();
    				
    				//AnagraficaSoggettoVO anagraficaSoggettoVO = new AnagraficaSoggettoVO();
    				//anagraficaSoggettoVO.setIdAnag(utfStandaloneEntityCustomItem.getIdAnag().longValue());
    				//anagraficaSoggettoVO.setDenominazione(utfStandaloneEntityCustomItem.getDenominazione());    				
    				//consumiPrVO.setAnagraficaSoggettoVO(anagraficaSoggettoVO);
    				
    				if(utfStandaloneEntityCustomItem.getUsi_industriali_1200()!=null) {
    					consumiPrVO.setUsi_industriali_1200(utfStandaloneEntityCustomItem.getUsi_industriali_1200().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getUsi_industriali_up()!=null) {
    					consumiPrVO.setUsi_industriali_up(utfStandaloneEntityCustomItem.getUsi_industriali_up().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getUsi_civili_120()!=null) {
    					consumiPrVO.setUsi_civili_120(utfStandaloneEntityCustomItem.getUsi_civili_120().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getUsi_civili_480()!=null) {    					
    					consumiPrVO.setUsi_civili_480(utfStandaloneEntityCustomItem.getUsi_civili_480().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getUsi_civili_1560()!=null) {    					
    					consumiPrVO.setUsi_civili_1560(utfStandaloneEntityCustomItem.getUsi_civili_1560().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getUsi_industriali_up()!=null) {    					
    					consumiPrVO.setUsi_civili_up(utfStandaloneEntityCustomItem.getUsi_industriali_up().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getTot_nuovi_allacciamenti()!=null) {    					
    					consumiPrVO.setTot_nuovi_allacciamenti(utfStandaloneEntityCustomItem.getTot_nuovi_allacciamenti().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getTot_industriali()!=null) {    					
    					consumiPrVO.setTot_industriali(utfStandaloneEntityCustomItem.getTot_industriali().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getTot_civili()!=null) {    					
    					consumiPrVO.setTot_civili(utfStandaloneEntityCustomItem.getTot_civili().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getConguaglio_dich()!=null) {    					
    					consumiPrVO.setConguaglio_dich(utfStandaloneEntityCustomItem.getConguaglio_dich().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getTotaleDich()!=null) {    					
    					consumiPrVO.setTotaleDich(utfStandaloneEntityCustomItem.getTotaleDich().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getRateo_dich()!=null) {    					
    					consumiPrVO.setRateo_dich(utfStandaloneEntityCustomItem.getRateo_dich().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getProvErogazione()!=null){
    					consumiPrVO.setProvincia_erogazione(utfStandaloneEntityCustomItem.getProvErogazione());
    				}
    				utfReportByIdImportList.add(consumiPrVO);
    			}
    		}
    		
    	} catch (Exception e) {			
			throw new BusinessException(e.getMessage(), ErrorCodes.BUSSINESS_EXCEPTION);
		}
    	
    	return utfReportByIdImportList;
    	
    }
    
    @Transactional
    @Override
    public List<ConsumiPrVO> getUTFReportDettaglioSoggettiByIdImport(Long idImport){
    	
    	if(idImport==null) {
    		throw new BusinessException("Parametro identificativo import mancante", ErrorCodes.BUSSINESS_EXCEPTION);
    	}
    	
    	List<ConsumiPrVO> utfReportByIdImportList = null;
    	try {
    		List<UTFStandaloneEntityCustom> utfStandaloneEntityCustomList = sigasUTFStandaloneRepository.getUTFReportDettaglioSoggettiByIdImport(idImport);
    		if(utfStandaloneEntityCustomList != null) 
    		{
    			utfReportByIdImportList = new ArrayList<>();
    			Iterator<UTFStandaloneEntityCustom> iterator = utfStandaloneEntityCustomList.iterator();
    			while(iterator.hasNext()) {
    				UTFStandaloneEntityCustom utfStandaloneEntityCustomItem = iterator.next();
    				
    				ConsumiPrVO consumiPrVO = new ConsumiPrVO();
    				
    				AnagraficaSoggettoVO anagraficaSoggettoVO = new AnagraficaSoggettoVO();
    				anagraficaSoggettoVO.setIdAnag(utfStandaloneEntityCustomItem.getIdAnag().longValue());
    				anagraficaSoggettoVO.setDenominazione(utfStandaloneEntityCustomItem.getDenominazione());    				
    				consumiPrVO.setAnagraficaSoggettoVO(anagraficaSoggettoVO);
    				
    				if(utfStandaloneEntityCustomItem.getUsi_industriali_1200()!=null) {
    					consumiPrVO.setUsi_industriali_1200(utfStandaloneEntityCustomItem.getUsi_industriali_1200().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getUsi_industriali_up()!=null) {
    					consumiPrVO.setUsi_industriali_up(utfStandaloneEntityCustomItem.getUsi_industriali_up().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getUsi_civili_120()!=null) {
    					consumiPrVO.setUsi_civili_120(utfStandaloneEntityCustomItem.getUsi_civili_120().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getUsi_civili_480()!=null) {    					
    					consumiPrVO.setUsi_civili_480(utfStandaloneEntityCustomItem.getUsi_civili_480().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getUsi_civili_1560()!=null) {    					
    					consumiPrVO.setUsi_civili_1560(utfStandaloneEntityCustomItem.getUsi_civili_1560().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getUsi_industriali_up()!=null) {    					
    					consumiPrVO.setUsi_civili_up(utfStandaloneEntityCustomItem.getUsi_industriali_up().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getTot_nuovi_allacciamenti()!=null) {    					
    					consumiPrVO.setTot_nuovi_allacciamenti(utfStandaloneEntityCustomItem.getTot_nuovi_allacciamenti().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getTot_industriali()!=null) {    					
    					consumiPrVO.setTot_industriali(utfStandaloneEntityCustomItem.getTot_industriali().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getTot_civili()!=null) {    					
    					consumiPrVO.setTot_civili(utfStandaloneEntityCustomItem.getTot_civili().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getConguaglio_dich()!=null) {    					
    					consumiPrVO.setConguaglio_dich(utfStandaloneEntityCustomItem.getConguaglio_dich().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getTotaleDich()!=null) {    					
    					consumiPrVO.setTotaleDich(utfStandaloneEntityCustomItem.getTotaleDich().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getRateo_dich()!=null) {    					
    					consumiPrVO.setRateo_dich(utfStandaloneEntityCustomItem.getRateo_dich().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getProvErogazione()!=null){
    					consumiPrVO.setProvincia_erogazione(utfStandaloneEntityCustomItem.getProvErogazione());
    				}
    				utfReportByIdImportList.add(consumiPrVO);
    			}
    		}
    		
    	} catch (Exception e) {			
			throw new BusinessException(e.getMessage(), ErrorCodes.BUSSINESS_EXCEPTION);
		}
    	
    	return utfReportByIdImportList;
    	
    }
    
    public void checkDichiarazione(SigasAnagraficaSoggetti sigasAnagraficaSoggetti, String anno, String userId) {
    	List<SigasAnaComunicazioni> sigasAnaComunicazioniList = sigasAnaComunicazioniRepository.findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndSigasTipoComunicazioniDenominazioneOrderByDataDocumentoAsc
    			(sigasAnagraficaSoggetti.getIdAnag(), anno, TipoDocumento.DICHIARAZIONE_UTF.getName());
		
    	logger.info("checkDichiarazione: \n " + XmlSerializer.objectToXml(sigasAnaComunicazioniList));
    	if (sigasAnaComunicazioniList.size() == 0) {
	    		addAlarm(sigasAnagraficaSoggetti.getCodiceAzienda(), null, null,
					TipoAllarme.COMUNICAZIONI.getName(), userId, StatusAllarme.ATTIVO.getName(),
					"Mancanza Documento per il soggetto " + sigasAnagraficaSoggetti.getCodiceAzienda(),anno);
    	}
    }
    
    public void checkCoerenza(SigasDichConsumi sigasDichConsumi) {
    	
    	String note = "Errore di Coereza: ";
    	boolean isAlarm = false;
    	String provincia = sigasDichConsumi.getProvinciaErogazione();
    	String codiceDitta = sigasDichConsumi.getSigasAnagraficaSoggetti().getCodiceAzienda();
    	SigasImportUTF sigasImportUTF = sigasDichConsumi.getSigasImport();
    	String annualita = sigasDichConsumi.getAnnualita();
    	long utenza_mc_f = 0;
    	long utenza_mc_f_1 = 0, utenza_mc_f_2 = 0, utenza_mc_f_3 = 0, utenza_mc_f_4 = 0, utenza_mc_f_5 = 0, utenza_mc_f_6 = 0;
    	long utenza_mc_g_1 = 0, utenza_mc_g_2 = 0, utenza_mc_g_3 = 0, utenza_mc_g_4 = 0, utenza_mc_g_5 = 0, utenza_mc_g_6 = 0;
    	long consumi_m_1 = 0, consumi_m_2 = 0, consumi_m_3 = 0, consumi_m_4 = 0, consumi_m_5 = 0, consumi_m_6 = 0;
    	long consumi_i_5 = 0, consumi_i_6 = 0, consumi_i_7 = 0, consumi_i_8 = 0, consumi_i_9 = 0, consumi_i_10 = 0;

    	boolean is2018OrMajor = 2018<=Integer.parseInt(annualita);
    	int limiteControllo;
    	
    	if(is2018OrMajor) {
			limiteControllo = 6;
		}else {
			limiteControllo = 5;
		}
    	
    	// Carica valori quadri G, F e M
     	List<SigasQuadroG> sigasQuadroGList = sigasQuadroGRepository.findUtenzaMc(provincia, codiceDitta, sigasImportUTF);
    	
    	for (SigasQuadroG sigasQuadroG : sigasQuadroGList) {    		
    		
    		
    		if (Integer.parseInt(sigasQuadroG.getProgRigo()) <= limiteControllo) {
				try {
					utenza_mc_f = sigasQuadroFRepository.sumUtenzaMc(sigasQuadroG.getProgRigo(), provincia, codiceDitta, sigasImportUTF);
				} catch (Exception e) {
		    		logger.debug("Nessun Utenza in MC nel quadro F con prog '"+ sigasQuadroG.getProgRigo() +"' per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
		    		continue;
		    	}
    		}
    		
    		try {
	    		switch(sigasQuadroG.getProgRigo()) {
	    			case "1":
	    				utenza_mc_f_1 = utenza_mc_f;
	    				utenza_mc_g_1 = sigasQuadroG.getUtenzeMc();
	    				consumi_m_1 = sigasQuadroMRepository.sumConsumi(sigasQuadroG.getProgRigo(), provincia, codiceDitta, sigasImportUTF);
	    				consumi_i_5 = sigasQuadroIRepository.sumConsumi("5", provincia, codiceDitta, sigasImportUTF);
	    				break;
	    			case "2":
	    				utenza_mc_f_2 = utenza_mc_f;
	    				utenza_mc_g_2 = sigasQuadroG.getUtenzeMc();
	    				consumi_m_2 = sigasQuadroMRepository.sumConsumi(sigasQuadroG.getProgRigo(), provincia, codiceDitta, sigasImportUTF);
	    				consumi_i_6 = sigasQuadroIRepository.sumConsumi("6", provincia, codiceDitta, sigasImportUTF);
	    				break;
	    			case "3":
	    				utenza_mc_f_3 = utenza_mc_f;
	    				utenza_mc_g_3 = sigasQuadroG.getUtenzeMc();
	    				consumi_m_3 += sigasQuadroMRepository.sumConsumi(sigasQuadroG.getProgRigo(), provincia, codiceDitta, sigasImportUTF);
	    				consumi_i_7 = sigasQuadroIRepository.sumConsumi("7", provincia, codiceDitta, sigasImportUTF);
	    				break;
	    			case "4":
	    				utenza_mc_f_4 = utenza_mc_f;
	    				utenza_mc_g_4 = sigasQuadroG.getUtenzeMc();
	    				consumi_m_4 += sigasQuadroMRepository.sumConsumi(sigasQuadroG.getProgRigo(), provincia, codiceDitta, sigasImportUTF);
	    				consumi_i_8 = sigasQuadroIRepository.sumConsumi("8", provincia, codiceDitta, sigasImportUTF);
	    				break;
	    			case "5":
	    				utenza_mc_f_5 = utenza_mc_f;
	    				utenza_mc_g_5 = sigasQuadroG.getUtenzeMc();
	    				consumi_m_5 = sigasQuadroMRepository.sumConsumi(sigasQuadroG.getProgRigo(), provincia, codiceDitta, sigasImportUTF);
	    				consumi_i_9 = sigasQuadroIRepository.sumConsumi("9", provincia, codiceDitta, sigasImportUTF);
	    				break;
	    			case "6":
	    				utenza_mc_f_6 = utenza_mc_f;
	    				utenza_mc_g_6 = sigasQuadroG.getUtenzeMc();
	    				consumi_m_6 = sigasQuadroMRepository.sumConsumi(sigasQuadroG.getProgRigo(), provincia, codiceDitta, sigasImportUTF);
	    				consumi_i_10 = sigasQuadroIRepository.sumConsumi("10", provincia, codiceDitta, sigasImportUTF);
	    				break;
	    		}
        		
    		} catch (Exception e) { 
        		logger.error("ERRORE DI COERENZA: non c'e' corrispondenza tra il quadro M con prog '"+ sigasQuadroG.getProgRigo() 
        			+"' e il quadro I con prog '" + (Integer.parseInt(sigasQuadroG.getProgRigo()) + 4) 
        			+ "' per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"
        			+ sigasImportUTF.getIdImport()+ "'");
        		addAlarm(codiceDitta, sigasDichConsumi,null,TipoAllarme.COERENZA.getName(), sigasImportUTF.getUserID(),StatusAllarme.ATTIVO.getName(), 
        				"non c'e' corrispondenza tra il quadro M con prog '"+ sigasQuadroG.getProgRigo() 
             			+"' e il quadro I con prog '" + (Integer.parseInt(sigasQuadroG.getProgRigo()) + 4) 
             			+ "' per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "'",annualita );
        	}
    		
    		// Controllo tra il quadro F e il quadro G
    		if (Integer.parseInt(sigasQuadroG.getProgRigo()) <= 5 && utenza_mc_f != sigasQuadroG.getUtenzeMc()) {
    			logger.warn("ERRORE DI COERENZA Rigo " + sigasQuadroG.getProgRigo() + " tra il quadro F e G per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport() + "'");
    			note += "Quadro F e G Rigo " +  sigasQuadroG.getProgRigo() + "; ";
    			isAlarm = true;
    		}
    	}
    	
    	// Controllo tra il quadro F e il quadro M
		if (utenza_mc_f_1 != consumi_m_1) {
			logger.warn("ERRORE DI COERENZA Rigo 1 tra i quadri F e M per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Quadro F e M Rigo 1; ";
			isAlarm = true;
		} else if (utenza_mc_f_2 != consumi_m_2) {
			logger.warn("ERRORE DI COERENZA Rigo 2 tra i quadri F e M per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Quadro F e M Rigo 2; ";
			isAlarm = true;
		} else if (utenza_mc_f_3 != consumi_m_3) {
			logger.warn("ERRORE DI COERENZA Rigo 3 tra i quadri F e M per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Quadro F e M Rigo 3; ";
			isAlarm = true;
		} else if (utenza_mc_f_4 != consumi_m_4) {
			logger.warn("ERRORE DI COERENZA Rigo 4 tra i quadri F e M per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Quadro F e M Rigo 4; ";
			isAlarm = true;
		} else if (utenza_mc_f_5 != consumi_m_5) {
			logger.warn("ERRORE DI COERENZA Rigo 5 tra i quadri F e M per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Quadro F e M Rigo 5; ";
			isAlarm = true;
		} else if (utenza_mc_f_6 != consumi_m_6 && is2018OrMajor) {
			logger.warn("ERRORE DI COERENZA Rigo 6 tra i quadri F e M per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Quadro F e M Rigo 6; ";
			isAlarm = true;
		} 
		
    	
    	// Controllo tra il quadro G e il quadro M
		if (utenza_mc_g_1 != consumi_m_1) {
			logger.warn("ERRORE DI COERENZA Rigo 1 tra i quadri G e M per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Quadro G e M Rigo 1; ";
			isAlarm = true;
		} else if (utenza_mc_g_2 != consumi_m_2) {
			logger.warn("ERRORE DI COERENZA Rigo 2 tra i quadri G e M per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Quadro G e M Rigo 2; ";
			isAlarm = true;
		} else if (utenza_mc_g_3 != consumi_m_3) {
			logger.warn("ERRORE DI COERENZA Rigo 3 tra i quadri G e M per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Quadro G e M Rigo 3; ";
			isAlarm = true;
		} else if (utenza_mc_g_4 != consumi_m_4) {
			logger.warn("ERRORE DI COERENZA Rigo 4 tra i quadri G e M per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Quadro G e M Rigo 4; ";
			isAlarm = true;
		} else if (utenza_mc_g_5 != consumi_m_5) {
			logger.warn("ERRORE DI COERENZA Rigo 5 tra i quadri G e M per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Quadro G e M Rigo 5; ";
			isAlarm = true;
		} else if (utenza_mc_g_6 != consumi_m_6 && is2018OrMajor) {
			logger.warn("ERRORE DI COERENZA Rigo 6 tra i quadri G e M per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Quadro G e M Rigo 6; ";
			isAlarm = true;
		}

		// Controllo tra il quadro I e il quadro M
		if (consumi_i_5 != consumi_m_1) {
			logger.warn("ERRORE DI COERENZA Rigo 1 quadro M e Rigo 5 quadro I per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Rigo 1 Quadro M e Rigo 5 Quadro I; ";
			isAlarm = true;
		} else if (consumi_i_6 != consumi_m_2) {
			logger.warn("ERRORE DI COERENZA Rigo 2 quadro M e Rigo 6 quadro I per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Rigo 2 Quadro M e Rigo 6 Quadro I; ";
			isAlarm = true;
		} else if (consumi_i_7 != consumi_m_3) {
			logger.warn("ERRORE DI COERENZA Rigo 3 quadro M e Rigo 7 quadro I per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Rigo 3 Quadro M e Rigo 7 Quadro I; ";
			isAlarm = true;
		} else if (consumi_i_8 != consumi_m_4) {
			logger.warn("ERRORE DI COERENZA Rigo 4 quadro M e Rigo 8 quadro I per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Rigo 4 Quadro M e Rigo 5 Quadro I; ";
			isAlarm = true;
		} else if (consumi_i_9 != consumi_m_5) {
			logger.warn("ERRORE DI COERENZA Rigo 5 quadro M e Rigo 9 quadro I per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Rigo 5 Quadro M e Rigo 9 Quadro I; ";
			isAlarm = true;
		} else if (consumi_i_10 != consumi_m_6 && is2018OrMajor) {
			logger.warn("ERRORE DI COERENZA Rigo 6 quadro M e Rigo 10 quadro I per il soggetto '" + codiceDitta + "' nella provincia '" + provincia + "' relativa all'importazione '"+ sigasImportUTF.getIdImport());
			note += "Rigo 6 Quadro M e Rigo 10 Quadro I; ";
			isAlarm = true;
		}
		
		if (isAlarm) {
			// Genero un Alarm associato alla dichjiarazione consumi
			addAlarm(codiceDitta, sigasDichConsumi, null, TipoAllarme.COERENZA.getName(), sigasImportUTF.getUserID(),
					StatusAllarme.ATTIVO.getName(), note, annualita);
    	}
    }    
    
    private void addAlarm(String codiceAzienda, SigasDichConsumi sigasDichConsumi,
    		SigasAnaComunicazioni sigasAnaComunicazioni, 
    		String tipoAllarme, String userId, int status, String nota,
    		String annualita) 
    {
    	
    	SigasTipoAllarmi sigasTipoAllarme = sigasTipoAllarmeRepository.findByDenominazioneIgnoreCase(tipoAllarme);
		
		SigasAllarmi sigasAllarmi = new SigasAllarmi();
		
		sigasAllarmi.setAttivazione(new Date());
		sigasAllarmi.setCodiceAzienda(codiceAzienda);
		sigasAllarmi.setSigasTipoAllarme(sigasTipoAllarme);
		sigasAllarmi.setSigasDichConsumi(sigasDichConsumi);
		sigasAllarmi.setSigasAnaComunicazioni(sigasAnaComunicazioni);
		sigasAllarmi.setUtente(userId);
		sigasAllarmi.setStatus(status);
		sigasAllarmi.setNota(nota);
		sigasAllarmi.setAnnualita(annualita);
		
		sigasAllarmiRepository.save(sigasAllarmi);
    }
    
    private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		
		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");
				
				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}
    
    
    @Override 
    @Transactional
    public SigasImportUTF updateImportState(long importId, int stato, String errorMessage) {
    	
    	SigasImportUTF sigasImport = sigasImportRepository.findOne(importId);
    	
    	if (sigasImport == null) {
    		sigasImport = new SigasImportUTF();
	    	sigasImport.setImportDate(new Date());
	    	sigasImport.setUserID("");
    	}
    	
    	sigasImport.setEsito(stato);
    	sigasImport.setErrore(errorMessage);
    	
    	//NON NECESSARIO
    	//sigasImport.setSelectedImport(false);
    	
    	return sigasImportRepository.save(sigasImport);
    }    
    
    
    //@Transactional
    @Override 
    public void confermaSoggettoDichiarazioniUTF(UTFConfermaSoggettoDichiarazioneRequest utfConfermaSoggettoDichiarazioneRequest) {
    	
    	//Verifica precdenete conferma
    	List<SigasDichConsumi> sigasDichConsumiList = this.sigasDichConsumiRepository
    													  .findConsumiByIdAnagAnnualitaIdImportSelected(utfConfermaSoggettoDichiarazioneRequest.getIdAnag(), 
																										utfConfermaSoggettoDichiarazioneRequest.getAnnualita(),
																										utfConfermaSoggettoDichiarazioneRequest.getIdImport());
    	if(sigasDichConsumiList!=null && !sigasDichConsumiList.isEmpty()) 
    	{    		
    		throw new BusinessException("Soggetto - dichiarazioni gia' confermate", ErrorCodes.BUSSINESS_EXCEPTION);    		
    	}
    	//----------------------------------------
    	
    	//RESET delle dichiarazioni associate all'anagrafica
    	this.sigasDichConsumiRepository.aggiornaSelectFlagByIdAnagAnnualita(false, 
																			utfConfermaSoggettoDichiarazioneRequest.getIdAnag(), 
																			utfConfermaSoggettoDichiarazioneRequest.getAnnualita());
    	
    	//ABILITAZIONE dichiarazione associate all'anagrafica e all'import indicati nella reuuest
    	this.sigasDichConsumiRepository.aggiornaSelectFlagByIdAnagAnnualitaIdImport(true, 
																					utfConfermaSoggettoDichiarazioneRequest.getIdAnag(), 
																					utfConfermaSoggettoDichiarazioneRequest.getAnnualita(),
																					utfConfermaSoggettoDichiarazioneRequest.getIdImport());
    	//ABILITAZIONE ANAGRAFICA
    	
    	SigasStoricoAnagraficaSoggetti sigasStoricoAnagraficaSoggetti = this.sigasStoricoAnagraficaSoggettiRepository
    																		.ricercaUltimaAnagraficaByIdImportIdAnag(utfConfermaSoggettoDichiarazioneRequest.getIdImport(),
    																												 utfConfermaSoggettoDichiarazioneRequest.getIdAnag());
    	if(sigasStoricoAnagraficaSoggetti == null) {
    		sigasStoricoAnagraficaSoggetti = this.sigasStoricoAnagraficaSoggettiRepository
												  .ricercaUltimaAnagraficaByIdAnag(utfConfermaSoggettoDichiarazioneRequest.getIdAnag());
    		
    	}
    	
    	if(sigasStoricoAnagraficaSoggetti!=null) 
    	{
    		
    		BigDecimal idImportRiferimento = (utfConfermaSoggettoDichiarazioneRequest.getIdImport()!=null) ? 
    										 new BigDecimal(utfConfermaSoggettoDichiarazioneRequest.getIdImport()) : 
    										 BigDecimal.ZERO;
    		
    		BigDecimal idProvincia = (sigasStoricoAnagraficaSoggetti.getSigasProvincia()!=null) ? 
					 				 new BigDecimal(sigasStoricoAnagraficaSoggetti.getSigasProvincia().getIdProvincia()) : 
					 				 BigDecimal.ZERO;
    		
    		BigDecimal idComune = (sigasStoricoAnagraficaSoggetti.getSigasComune()!=null) ? 
					 			  new BigDecimal(sigasStoricoAnagraficaSoggetti.getSigasComune().getIdComune()) : 
					 			  BigDecimal.ZERO;  		
    		
    		
    		this.sigasAnagraficaSoggettiRepository.aggiornaAnagraficaByFieldsConfermaImport(FLG_SELECTED_IMPORT, 
				    														  sigasStoricoAnagraficaSoggetti.getDenominazione(), 
				    														  sigasStoricoAnagraficaSoggetti.getCodiceAzienda(), 
				    														  sigasStoricoAnagraficaSoggetti.getIdAnag(),
				    														  sigasStoricoAnagraficaSoggetti.getIndirizzo(),				    														  
				    														  idImportRiferimento,
				    														  idProvincia,
				    														  idComune);
    		
    		//Si effettua la cancellazione di un eventuale record di validazione già presente per l'azienda e per l'anno.
    		//La cancelazione consente di rispettare il flusso / workflow definito per la gestione della fase di validazione.
    		this.gestioneValidazionePerConfermaImport(sigasStoricoAnagraficaSoggetti.getCodiceAzienda(), utfConfermaSoggettoDichiarazioneRequest.getAnnualita());
    		
    		//valutare se dopo, aver confermato un import che apporta delle modifiche all'anagrafica esistente, aggiornare anche id import 
    		//this.sigasStoricoAnagraficaSoggettiRepository.aggiornaFlagByIdStoricoAnag(FLG_SELECTED_IMPORT, sigasStoricoAnagraficaSoggetti.getIdStoricoAnag());
    		this.sigasStoricoAnagraficaSoggettiRepository.aggiornaFlagByIdAnagAndIdImport(FLG_SELECTED_IMPORT, sigasStoricoAnagraficaSoggetti.getIdAnag(), idImportRiferimento);
    	}
    	
    	    	    	    	
    	
    }
    
    public void gestioneValidazionePerConfermaImport(String codiceAzienda, String anno) {
    	
    	SigasValidazione  sigasValidazione = sigasValidazioneRepository.findByAnnoAndCodiceAzienda(anno, codiceAzienda);
    	if(sigasValidazione != null) {    		
    		sigasValidazioneRepository.cancellaValidazione(sigasValidazione.getIdValid());
    	}	
    	
    }
    
    private void storicizzaModificaAnagrafica(SigasAnagraficaSoggetti sigasAnagraficaSoggettiBeforeUpdate, String ownerOperazione, Long idImport, String annualita) {
		if(sigasAnagraficaSoggettiBeforeUpdate == null) {
			return;
		}	
		
		SigasStoricoAnagraficaSoggetti sigasStoricoAnagraficaSoggetti = new SigasStoricoAnagraficaSoggetti();
		sigasStoricoAnagraficaSoggetti.setCodiceAzienda(sigasAnagraficaSoggettiBeforeUpdate.getCodiceAzienda());
		sigasStoricoAnagraficaSoggetti.setDataRiferimento(Calendar.getInstance().getTime());
		sigasStoricoAnagraficaSoggetti.setDenominazione(sigasAnagraficaSoggettiBeforeUpdate.getDenominazione());
		sigasStoricoAnagraficaSoggetti.setEmail(sigasAnagraficaSoggettiBeforeUpdate.getEmail());
		sigasStoricoAnagraficaSoggetti.setIban(sigasAnagraficaSoggettiBeforeUpdate.getIban());
		sigasStoricoAnagraficaSoggetti.setIdAnag(sigasAnagraficaSoggettiBeforeUpdate.getIdAnag());
		sigasStoricoAnagraficaSoggetti.setIndirizzo(sigasAnagraficaSoggettiBeforeUpdate.getIndirizzo());
		sigasStoricoAnagraficaSoggetti.setPec(sigasAnagraficaSoggettiBeforeUpdate.getPec());
		sigasStoricoAnagraficaSoggetti.setCfPiva(sigasAnagraficaSoggettiBeforeUpdate.getCfPiva());
		sigasStoricoAnagraficaSoggetti.setOwnerOperazione(ownerOperazione);
		if(idImport != null) {
			sigasStoricoAnagraficaSoggetti.setIdImport(idImport);
		}		
		sigasStoricoAnagraficaSoggetti.setAnnualita(annualita);
		
		this.sigasStoricoAnagraficaSoggettiRepository.save(sigasStoricoAnagraficaSoggetti);		
	}
    
    @Transactional
    @Override
    public void confermaDichiarazioniUTF(UTFConfermaDichiarazioneRequest utfConfermaDichiarazioneRequest) {
    	
    	if(utfConfermaDichiarazioneRequest==null) {
    		throw new BusinessException("Parametro UTFConfermaDichiarazioneRequest mancante", ErrorCodes.BUSSINESS_EXCEPTION);
    	}
		
		if(utfConfermaDichiarazioneRequest.getIdImportA()==null) {
    		throw new BusinessException("Parametro ID import A mancante", ErrorCodes.BUSSINESS_EXCEPTION);
    	}
		
		if(utfConfermaDichiarazioneRequest.getAzioneRichiesta()==null) {
    		throw new BusinessException("Parametro Azione Richiesta mancante", ErrorCodes.BUSSINESS_EXCEPTION);
    	}
		
		if(utfConfermaDichiarazioneRequest.getAnnualita()==null) {
    		throw new BusinessException("Parametro ricerca Annualita' mancante", ErrorCodes.BUSSINESS_EXCEPTION);
    	}

		List<UTFStandaloneEntitySoggettiMacroReport> macroReportList =  this.sigasUTFStandaloneRepository
																		    .getUTFSoggettiMacroReportByIdReport(utfConfermaDichiarazioneRequest.getIdImportA(), 
																		    									 utfConfermaDichiarazioneRequest.getIdImportB(),
																		    									 Integer.valueOf(utfConfermaDichiarazioneRequest.getAnnualita()));
		
		if(macroReportList != null && !macroReportList.isEmpty()) {
			
			List<SigasDichConsumi> sigasDichConsumiList = null;
			
			Iterator<UTFStandaloneEntitySoggettiMacroReport> iterator = macroReportList.iterator();
			while(iterator.hasNext()) {
				
				UTFStandaloneEntitySoggettiMacroReport item = iterator.next();
				if(item.getAzione().equalsIgnoreCase(utfConfermaDichiarazioneRequest.getAzioneRichiesta()) || 
				   (utfConfermaDichiarazioneRequest.getAzioneRichiesta().equalsIgnoreCase(CONFERMA_ALL_DICHIARAZIONI) 
					//&& !item.getAzione().equalsIgnoreCase(CONFERMA_NOP_SOGGETTO_DICHIARAZIONI)
					)
				   ) 
				{	
					
					//Verifica presenza consumi da confermare
			    	sigasDichConsumiList = this.sigasDichConsumiRepository
			    	 						   .findConsumiByIdAnagAnnualitaToConfirm(Long.valueOf(item.getId_anag()), 
																					  utfConfermaDichiarazioneRequest.getAnnualita(),
													    							  utfConfermaDichiarazioneRequest.getIdImportA());
			    	if(sigasDichConsumiList!=null && !sigasDichConsumiList.isEmpty()) 
			    	{    		
			    		//RESET delle dichiarazioni associate all'anagrafica
				    	this.sigasDichConsumiRepository.aggiornaSelectFlagByIdAnagAnnualita(false, 
				    																		Long.valueOf(item.getId_anag()), 
				    																		utfConfermaDichiarazioneRequest.getAnnualita());
				    	
				    	//ABILITAZIONE dichiarazione associate all'anagrafica e all'import ricavati
				    	this.sigasDichConsumiRepository.aggiornaSelectFlagByIdAnagAnnualitaIdImport(true, 
				    																				Long.valueOf(item.getId_anag()), 
																					    			utfConfermaDichiarazioneRequest.getAnnualita(),
																					    			utfConfermaDichiarazioneRequest.getIdImportA());    		
			    	}
			    	//----------------------------------------
			    	
			    	
			    	//ABILITAZIONE anagrafica
			    	//---------------------------------------------------------
			    	SigasStoricoAnagraficaSoggetti sigasStoricoAnagraficaSoggetti = this.sigasStoricoAnagraficaSoggettiRepository
																						.ricercaUltimaAnagraficaByIdImportIdAnag(utfConfermaDichiarazioneRequest.getIdImportA(),
																																 Long.valueOf(item.getId_anag()));
			    	
			    	if(sigasStoricoAnagraficaSoggetti == null) {
			    		sigasStoricoAnagraficaSoggetti = this.sigasStoricoAnagraficaSoggettiRepository
															  .ricercaUltimaAnagraficaByIdAnag(Long.valueOf(item.getId_anag()));
			    		
			    	}
			    	
			    	if(sigasStoricoAnagraficaSoggetti!=null) 
			    	{			    		
			    		
						BigDecimal idImportRiferimento = (utfConfermaDichiarazioneRequest.getIdImportA()!=null) ? 
														 new BigDecimal(utfConfermaDichiarazioneRequest.getIdImportA()) : 
														 BigDecimal.ZERO;
						
						BigDecimal idProvincia = (sigasStoricoAnagraficaSoggetti.getSigasProvincia()!=null) ? 
				 				 new BigDecimal(sigasStoricoAnagraficaSoggetti.getSigasProvincia().getIdProvincia()) : 
				 				 BigDecimal.ZERO;
		
						BigDecimal idComune = (sigasStoricoAnagraficaSoggetti.getSigasComune()!=null) ? 
								 			  new BigDecimal(sigasStoricoAnagraficaSoggetti.getSigasComune().getIdComune()) : 
								 			  BigDecimal.ZERO;						
			    		
			    		this.sigasAnagraficaSoggettiRepository.aggiornaAnagraficaByFieldsConfermaImport(FLG_SELECTED_IMPORT, 
																										sigasStoricoAnagraficaSoggetti.getDenominazione(), 
																										sigasStoricoAnagraficaSoggetti.getCodiceAzienda(), 
																										sigasStoricoAnagraficaSoggetti.getIdAnag(),
																										sigasStoricoAnagraficaSoggetti.getIndirizzo(),																										
																										idImportRiferimento,
																										idProvincia,
																										idComune);	
			    		
			    		//Si effettua la cancellazione di un eventuale record di validazioen già presente per l'azienda e per l'anno.
			    		//La cancelazione consente di rispettare il flusso / workflow definito per la gestione della fase di validazione.
			    		this.gestioneValidazionePerConfermaImport(sigasStoricoAnagraficaSoggetti.getCodiceAzienda(), utfConfermaDichiarazioneRequest.getAnnualita());
			    		
			    		//this.sigasStoricoAnagraficaSoggettiRepository.aggiornaFlagByIdStoricoAnag(FLG_SELECTED_IMPORT, sigasStoricoAnagraficaSoggetti.getIdStoricoAnag());
			    		this.sigasStoricoAnagraficaSoggettiRepository.aggiornaFlagByIdAnagAndIdImport(FLG_SELECTED_IMPORT, sigasStoricoAnagraficaSoggetti.getIdAnag(), idImportRiferimento);
			    	}
			    	//---------------------------------------------------------
					
				} 				
			}			
		}	
    	
    	
    	//Riporto in sigas_anagrafica LE ULTIME modifiche storicizzate in sigas_storico_anagrafica
    	//this.sigasStoricoAnagraficaSoggettiRepository.aggiornaAnagraficheByIdImportUTF(confermaUTFImportRequest.getIdImport());    	
    	
    }

	@Override
	public List<UTFStandaloneEntitySoggettiMacroReportVO> 
	getUTFSoggettiMacroReportByIdReport(UTFSoggettiMacroReportByIdReportRequest request) 
	{
		if(request==null) {
    		throw new BusinessException("Parametro ricerca mancante", ErrorCodes.BUSSINESS_EXCEPTION);
    	}
		
		if(request.getIdImportA()==null) {
    		throw new BusinessException("Parametro ricerca ID import A mancante", ErrorCodes.BUSSINESS_EXCEPTION);
    	}
		
		if(request.getAnnualita()==null) {
    		throw new BusinessException("Parametro ricerca Annualita' mancante", ErrorCodes.BUSSINESS_EXCEPTION);
    	}
		
		//---------------------------------------------------------------
		//Il servizio può essere invocato anche per un solo import
		//---------------------------------------------------------------
		/*
		if(request.getIdImportB()==null) {
    		throw new BusinessException("Parametro ricerca ID import B mancante", ErrorCodes.BUSSINESS_EXCEPTION);
    	}
    	*/
		
		List<UTFStandaloneEntitySoggettiMacroReportVO> ouput = null;
		
		try {			
			
			List<UTFStandaloneEntitySoggettiMacroReport> macroReportList =  this.sigasUTFStandaloneRepository
																			    .getUTFSoggettiMacroReportByIdReport(request.getIdImportA(), 
																			    									 request.getIdImportB(),
																			    									 request.getAnnualita());
			if(macroReportList!=null) {
				ouput = new ArrayList<>();
				Iterator<UTFStandaloneEntitySoggettiMacroReport> iterator = macroReportList.iterator();
				
				while(iterator.hasNext()) {
					UTFStandaloneEntitySoggettiMacroReport itemDTO = iterator.next();
					
					UTFStandaloneEntitySoggettiMacroReportVO itemVO = new UTFStandaloneEntitySoggettiMacroReportVO();
					
					itemVO.setId_anag(itemDTO.getId_anag());
					itemVO.setDenominazione(itemDTO.getDenominazione());
					itemVO.setTot_uso_civile(itemDTO.getTot_uso_civile());
					itemVO.setTot_uso_industriale(itemDTO.getTot_uso_industriale());
					itemVO.setTot_nuovi_allacciamenti(itemDTO.getTot_nuovi_allacciamenti());
					itemVO.setTot_dichiarazione(itemDTO.getTot_dichiarazione());
					itemVO.setAzione(itemDTO.getAzione());
					itemVO.setSelectedImport(itemDTO.getSelectedImport());
					
					ouput.add(itemVO);
				}
			}
			
		} catch (Exception e) {			
			throw new BusinessException(e.getMessage(), ErrorCodes.BUSSINESS_EXCEPTION);
		}		
		
		return ouput;
	}	
	

	@Override
	public List<ConsumiPrVO> getUTFReportDettaglioSoggettiByIdImportIdAnag(Long idImport, Long idAnag) 
	{
		if(idImport==null) {
    		throw new BusinessException("Parametro identificativo import mancante", ErrorCodes.BUSSINESS_EXCEPTION);
    	}
		
		if(idAnag==null) {
    		throw new BusinessException("Parametro identificativo anagrafica mancante", ErrorCodes.BUSSINESS_EXCEPTION);
    	}
    	
    	List<ConsumiPrVO> utfReportByIdImportList = null;
    	try {
    		List<UTFStandaloneEntityCustom> utfStandaloneEntityCustomList = sigasUTFStandaloneRepository.getUTFReportDettaglioSoggettiByIdImportIdAnagrafica(idImport, idAnag);
    		if(utfStandaloneEntityCustomList != null) 
    		{
    			utfReportByIdImportList = new ArrayList<>();
    			Iterator<UTFStandaloneEntityCustom> iterator = utfStandaloneEntityCustomList.iterator();
    			while(iterator.hasNext()) {    				
    				
    				UTFStandaloneEntityCustom utfStandaloneEntityCustomItem = iterator.next();
    				
    				ConsumiPrVO consumiPrVO = new ConsumiPrVO();    				
    				
    				SigasStoricoAnagraficaSoggetti sigasStoricoAnagraficaSoggetti = this.sigasStoricoAnagraficaSoggettiRepository.ricercaUltimaAnagraficaByIdImportIdAnag(idImport, idAnag);
    				AnagraficaSoggettoVO anagraficaSoggettoVO = new AnagraficaSoggettoVO();
    				
    				if(sigasStoricoAnagraficaSoggetti != null ) {
    					anagraficaSoggettoVO.setIdAnag(utfStandaloneEntityCustomItem.getIdAnag().longValue());
    					anagraficaSoggettoVO.setDenominazione(sigasStoricoAnagraficaSoggetti.getDenominazione()); 
    					anagraficaSoggettoVO.setCodiceAzienda(sigasStoricoAnagraficaSoggetti.getCodiceAzienda());
    					anagraficaSoggettoVO.setIndirizzo(sigasStoricoAnagraficaSoggetti.getIndirizzo());
    					anagraficaSoggettoVO.setIdComune(sigasStoricoAnagraficaSoggetti.getSigasComune().getIdComune());
    					anagraficaSoggettoVO.setIdProvincia(sigasStoricoAnagraficaSoggetti.getSigasProvincia().getIdProvincia());
    				} else {
    					SigasAnagraficaSoggetti sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByIdAnag(utfStandaloneEntityCustomItem.getIdAnag().longValue());
    					anagraficaSoggettoVO.setIdAnag(sigasAnagraficaSoggetti.getIdAnag());
    					anagraficaSoggettoVO.setDenominazione(sigasAnagraficaSoggetti.getDenominazione()); 
    					anagraficaSoggettoVO.setCodiceAzienda(sigasAnagraficaSoggetti.getCodiceAzienda());
    					anagraficaSoggettoVO.setIndirizzo(sigasAnagraficaSoggetti.getIndirizzo());
    					anagraficaSoggettoVO.setIdComune(sigasAnagraficaSoggetti.getSigasComune().getIdComune());
    					anagraficaSoggettoVO.setIdProvincia(sigasAnagraficaSoggetti.getSigasProvincia().getIdProvincia());
    				}
    				   				
    				consumiPrVO.setAnagraficaSoggettoVO(anagraficaSoggettoVO);
    				
    				consumiPrVO.setProvincia_erogazione(utfStandaloneEntityCustomItem.getProvErogazione());
    				
    				if(utfStandaloneEntityCustomItem.getUsi_industriali_1200()!=null) {
    					consumiPrVO.setUsi_industriali_1200(utfStandaloneEntityCustomItem.getUsi_industriali_1200().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getUsi_industriali_up()!=null) {
    					consumiPrVO.setUsi_industriali_up(utfStandaloneEntityCustomItem.getUsi_industriali_up().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getUsi_civili_120()!=null) {
    					consumiPrVO.setUsi_civili_120(utfStandaloneEntityCustomItem.getUsi_civili_120().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getUsi_civili_480()!=null) {    					
    					consumiPrVO.setUsi_civili_480(utfStandaloneEntityCustomItem.getUsi_civili_480().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getUsi_civili_1560()!=null) {    					
    					consumiPrVO.setUsi_civili_1560(utfStandaloneEntityCustomItem.getUsi_civili_1560().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getUsi_industriali_up()!=null) {    					
    					consumiPrVO.setUsi_civili_up(utfStandaloneEntityCustomItem.getUsi_industriali_up().longValue());
    				}
    				if(utfStandaloneEntityCustomItem.getTot_nuovi_allacciamenti()!=null) {    					
    					consumiPrVO.setTot_nuovi_allacciamenti(utfStandaloneEntityCustomItem.getTot_nuovi_allacciamenti().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getTot_industriali()!=null) {    					
    					consumiPrVO.setTot_industriali(utfStandaloneEntityCustomItem.getTot_industriali().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getTot_civili()!=null) {    					
    					consumiPrVO.setTot_civili(utfStandaloneEntityCustomItem.getTot_civili().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getConguaglio_dich()!=null) {    					
    					consumiPrVO.setConguaglio_dich(utfStandaloneEntityCustomItem.getConguaglio_dich().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getTotaleDich()!=null) {    					
    					consumiPrVO.setTotaleDich(utfStandaloneEntityCustomItem.getTotaleDich().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getRateo_dich()!=null) {    					
    					consumiPrVO.setRateo_dich(utfStandaloneEntityCustomItem.getRateo_dich().doubleValue());
    				}
    				if(utfStandaloneEntityCustomItem.getProvErogazione()!=null){
    					consumiPrVO.setProvincia_erogazione(utfStandaloneEntityCustomItem.getProvErogazione());
    				}
    				List<ScartoVO> scartiList =	homeService.ricercaScartiByIdConsumi(utfStandaloneEntityCustomItem.getIdConsumi());
    				if(scartiList!=null) {
    					consumiPrVO.setListaScarti(scartiList);
    				}
    				if(utfStandaloneEntityCustomItem.getRettifiche()!=null){
    					consumiPrVO.setRettifiche(utfStandaloneEntityCustomItem.getRettifiche());
    				}
    				if(utfStandaloneEntityCustomItem.getArrotondamenti()!=null) {
    					consumiPrVO.setArrotondamenti(utfStandaloneEntityCustomItem.getArrotondamenti());
    				}
    				
    				
    				utfReportByIdImportList.add(consumiPrVO);
    			}
    		}
    		
    	} catch (Exception e) {			
			throw new BusinessException(e.getMessage(), ErrorCodes.BUSSINESS_EXCEPTION);
		}
    	
    	return utfReportByIdImportList;
	}    
     
}
