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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.sigas.sigasbl.common.ErrorCodes;
import it.csi.sigas.sigasbl.common.EsitoImport;
import it.csi.sigas.sigasbl.common.Quadro;
import it.csi.sigas.sigasbl.common.StatusAllarme;
import it.csi.sigas.sigasbl.common.TipoAllarme;
import it.csi.sigas.sigasbl.common.TipoDocumento;
import it.csi.sigas.sigasbl.common.exception.BusinessException;
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
import it.csi.sigas.sigasbl.model.entity.SigasTipoAllarmi;
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
import it.csi.sigas.sigasbl.model.repositories.SigasTipoAllarmiRepository;
import it.csi.sigas.sigasbl.model.vo.importUTF.AnnualitaImportVO;
import it.csi.sigas.sigasbl.model.vo.importUTF.ImportUTFVO;
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
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final static int progRigoInizioTotali_2017 = 6;
	private final static int progRigoInizioTotali_2018 = 7;
	
	@Value("${year.to.show}")
	private int yearToShow;
	
	@Transactional
	@Override
	public SigasImportUTF checkImportUTF(MultipartFormDataInput input, String username) {

		try {
			String anno = input.getFormDataPart("annualita", String.class, null);
			
			// Controlla se l'importazione e' gia'stata eseguita
			if (sigasImportRepository.findDowloadedByAnno(anno) != null) {
				throw new BusinessException("Il file delle dichiariazioni UTF e' stato gia' importato per l'anno '" + anno + "'", ErrorCodes.BUSSINESS_EXCEPTION);
			}
			
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
    	
   			if (ext.length <2 || !ext[1].equals("zip")) {;
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
			sigasImport.setErrore( "Si e' verifiacato un errore durante il download del file '" + fileName + "'");
			throw new BusinessException(sigasImport.getErrore(), ErrorCodes.IO_EXCEPTION);
		} finally {
			// salva stato importazione
	       
			if (sigasImport == null) {
				sigasImport = new SigasImportUTF();
			}
	        sigasImport.setAnnualita(annualita);
	    	sigasImport.setImportDate(new Date());
	    	sigasImport.setUserID(username);
	    	
	    	sigasImport = sigasImportRepository.save(sigasImport);
		}
    	
    	logger.info("Il file '"+ fileName + "' e' stato caricaricato");
    	
    	importUtfVO.setImportId(sigasImport.getIdImport());
    	importUtfVO.setFilename(fileName);
    	importUtfVO.setAnno(annualita);
    	importUtfVO.setNumeroFile(numFiles);
    	
		return importUtfVO;
	}
    
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
		    	
		    	// Legge anagrafica dalla tabella Frontespizio
		    	List<SigasFrontespizio> sigasFrontespizioList = sigasFrontespizioRepository.findByImportOrderByCodiceDitta(sigasImport);
		    	
		    	String currentCodiceDitta = null;
		    	SigasAnagraficaSoggetti sigasAnagraficaSoggetti = null;
		
		    	for (SigasFrontespizio sigasFrontespizio : sigasFrontespizioList) {
		    		if (!sigasFrontespizio.getCodiceDitta().equals(currentCodiceDitta)) {
		    			currentCodiceDitta = sigasFrontespizio.getCodiceDitta();
		    		
		    			logger.debug("Soggetto: '" + sigasFrontespizio.getCodiceDitta() + "' - '" + sigasFrontespizio.getDenominazione() + "'" );
		    			
		    			// Aggiornamento anagrafica   			
		    			sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.findByCodiceAzienda(sigasFrontespizio.getCodiceDitta());
		    			if (sigasAnagraficaSoggetti == null) {
			    			sigasAnagraficaSoggetti = new SigasAnagraficaSoggetti();
			    			
			    			sigasAnagraficaSoggetti.setCodiceAzienda(sigasFrontespizio.getCodiceDitta());
			    			sigasAnagraficaSoggetti.setDenominazione(sigasFrontespizio.getDenominazione());
			    			sigasAnagraficaSoggetti.setIndirizzo(sigasFrontespizio.getIndirizzoSede());
			    			sigasAnagraficaSoggetti.setTipo(sigasFrontespizio.getTipoSoggetto());
			    			
			    			//sigasAnagraficaSoggetti.setProvincia(sigasFrontespizio.getProvinciaSede());
			    			//sigasAnagraficaSoggetti.setComune(sigasFrontespizio.getComuneSede());
			    			
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
			    			
			    			// Salva in DB
			    			sigasAnagraficaSoggetti = sigasAnagraficaSoggettiRepository.save(sigasAnagraficaSoggetti);
		    			}
		    			
		    			// Controllo dichiarazione UTF
		    			checkDichiarazione(sigasAnagraficaSoggetti, sigasFrontespizio.getAnno(), sigasImport.getUserID());
		    			
		    		}
		    		
		    		// Popolamento Consumi
		    		SigasDichConsumi sigasDichConsumi = new SigasDichConsumi();
		    		
		    		// Recupera dati QUADRO M per codice azienda, provincia 
		    		List<SigasQuadroM> sigasQuadroMList = sigasQuadroMRepository.findByCodiceDittaAndProvinciaAndImportUTF(sigasFrontespizio.getCodiceDitta(), 
		    				sigasFrontespizio.getProvincia(), sigasFrontespizio.getSigasImport(), sigasFrontespizio.getAnno());
		    		
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
			    			SigasAliquote sigasAliquote = sigasAliquoteRepository.findByAliquotaAndProgRigo(sigasQuadroM.getAliquota(),sigasQuadroM.getProgRigo(), Integer.parseInt(sigasFrontespizio.getAnno()) );
			    			
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
			    						//totIIndustrialiUp += sigasQuadroM.getImposta();
			    						totIIndustrialiUp += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
			    						break;
			    					case "usi_industriali_1200":
			    						usiIndustriali1200 += sigasQuadroM.getConsumi();
			    						//totIIndustriali1200 += sigasQuadroM.getImposta();
			    						totIIndustriali1200 += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
			    						break;
			    					case "usi_civili_120":
			    						usiCivili120 += sigasQuadroM.getConsumi();
			    						//totCivili120 += sigasQuadroM.getImposta();
			    						totCivili120 += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
			    						break;
			    					case "usi_civili_480":
			    						usiCivili480 += sigasQuadroM.getConsumi();
			    						//totCivili480 += sigasQuadroM.getImposta();
			    						totCivili480 += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
			    						break;
			    					case "usi_civili_1560":
			    						usiCivili1560 += sigasQuadroM.getConsumi();
			    						//totCivili1560 += sigasQuadroM.getImposta();
			    						totCivili1560 += sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota();
			    						break;
			    					case "usi_civili_up":
			    						usiCiviliUp += sigasQuadroM.getConsumi();
			    						//totCiviliUp += sigasQuadroM.getImposta();
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
			    				//sigasDichScarti.setImposta(sigasQuadroM.getImposta());
			    				sigasDichScarti.setImposta(sigasQuadroM.getConsumi() * sigasQuadroM.getAliquota());
			    				sigasDichScarti.setProvincia(sigasQuadroM.getProvincia());
			    				sigasDichScarti.setSigasDichConsumi(sigasDichConsumi);
		
			    				sigasDichScartiList.add(sigasDichScarti);
			    				
			    			}
		    			}
		    			if("2018".equalsIgnoreCase(sigasFrontespizio.getAnno())) {
		    				if (sigasQuadroM.getProgRigo().equals(String.valueOf(progRigoInizioTotali_2018+2)) )
			    				totaleDich = sigasQuadroM.getImposta();
			    			else if (sigasQuadroM.getProgRigo().equals(String.valueOf(progRigoInizioTotali_2018+1)) )
			    				arrotondamenti = sigasQuadroM.getImposta();
			    			else if (sigasQuadroM.getProgRigo().equals(String.valueOf(progRigoInizioTotali_2018)) )
			    				rettifiche = sigasQuadroM.getImposta();
		    			}else {
		    				if (sigasQuadroM.getProgRigo().equals(String.valueOf(progRigoInizioTotali_2017+2)) )
			    				totaleDich = sigasQuadroM.getImposta();
			    			else if (sigasQuadroM.getProgRigo().equals(String.valueOf(progRigoInizioTotali_2017+1)) )
			    				arrotondamenti = sigasQuadroM.getImposta();
			    			else if (sigasQuadroM.getProgRigo().equals(String.valueOf(progRigoInizioTotali_2017)) )
			    				rettifiche = sigasQuadroM.getImposta();
		    			}
		    			
		    		}
		    		
		    		// Recupera dati QUADRO M per codice azienda, provincia
		    		List<SigasQuadroN> sigasQuadroNList = sigasQuadroNRepository.findByImportOrderByCodiceDitta(sigasFrontespizio.getCodiceDitta(), 
		    				sigasFrontespizio.getProvincia(), sigasFrontespizio.getSigasImport());
		    		
		    		double conguaglioDich = 0;
		    		double adLiquidata = 0;
		    		double rateo = 0;
		    		if (sigasQuadroNList != null) {
		    			for(SigasQuadroN sigasQuadroN : sigasQuadroNList) {
			    			if ( sigasQuadroN.getProgRigo().equals("1"))
			    				adLiquidata = sigasQuadroN.getImposta();
			    			else if ( sigasQuadroN.getProgRigo().equals("3"))
			    				conguaglioDich = +sigasQuadroN.getImposta();
			    			else if ( sigasQuadroN.getProgRigo().equals("4"))
			    				conguaglioDich = -sigasQuadroN.getImposta();
			    			else if (sigasQuadroN.getProgRigo().equals("912"))
			    				rateo = sigasQuadroN.getImposta();
		    			}
		    		}
		    		
		    		// Recupera Versamenti per conguaglio calolato
		    		double versamenti = 0;
		    		try {
		    			versamenti = sigasDichVersamentiRepository.sumByAnnoAndProvinciaAndSoggetto(sigasFrontespizio.getAnno(), 
		    					sigasFrontespizio.getProvincia(), sigasAnagraficaSoggetti);
		    		} catch(Exception e) {
		    			logger.debug("Nessun Versamento trovato per il Soggetto '" + sigasAnagraficaSoggetti.getCodiceAzienda() + 
		    					" nell'anno " + sigasFrontespizio.getAnno() + " e per la provincia '" + sigasFrontespizio.getProvincia());
		    		}
		    		
		    		
		    		// Come totale industriale viene preso l'imposta piu' alta
//		    		if (totIIndustrialiUp > totIIndustriali1200) 
//		    			totIIndustriali = totIIndustrialiUp;
//		    		else
//		    			totIIndustriali =totIIndustriali1200;
		    		
		    		// SIGAS 57 - totale industriale come somma delle imposte
		    		totIIndustriali = totIIndustrialiUp + totIIndustriali1200;
		    		
		    		if (sigasQuadroMList.size() != 0) {
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
			    		sigasDichConsumi.setTotaleCalcolato(totIIndustriali+totCivili120 + totNuoviAllacciamenti +totCivili480 + totCivili1560 + totCiviliUp + rettifiche + arrotondamenti);
			    		sigasDichConsumi.setRettifiche(rettifiche);
			    		sigasDichConsumi.setArrotondamenti(arrotondamenti);
			    		
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
			    		sigasDichConsumi.setSigasAnagraficaSoggetti(sigasAnagraficaSoggetti);
			    		sigasDichConsumi.setSigasImport(sigasFrontespizio.getSigasImport());
			    		sigasDichConsumi.setAnnualita(sigasFrontespizio.getAnno());
			    		sigasDichConsumi.setProvinciaErogazione(null);
			    		
			    		sigasDichConsumi = sigasDichConsumiRepository.save(sigasDichConsumi);
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
				int numeroSoggetti = sigasFrontespizioRepository.findByDistinctCodiceDittaAndImportUtf(sigasImportUTF).size();
				int numeroScarti = sigasDichScartiRepository.findBySigasDichConsumiAnnualita(String.valueOf(year)).size();
				int numeroIncoerenza = sigasAllarmiRepository.findBySigasDichConsumiAnnualitaAndSigasTipoAllarmeIdTipoAllarme(String.valueOf(year), 2).size();
				int numeroDocumentiPervenuti = sigasAnaComunicazioniRepository.findByAnnualitaOrderByDataDocumentoAsc(String.valueOf(year)).size();
				success = "Dichiarazioni importate: " + numeroSoggetti + " - " +
						"Numero Scarti: " + numeroScarti + " - " +
						"Numero Incongruenze: " + numeroIncoerenza + " - " +
						"Dichiarazioni con documenti pervenuti: " + numeroDocumentiPervenuti;
			}
			
			AnnualitaImportVO annualitaVo = annualitaEntityMapper.mapEntityToVO(sigasImportUTF);
			annualitaVo.setSuccess(success);
			
			annualitaImportVOList.add(annualitaVo);
			
			
		}
    	
		return annualitaImportVOList;
    	
    }
    
    public void checkDichiarazione(SigasAnagraficaSoggetti sigasAnagraficaSoggetti, String anno, String userId) {
    	List<SigasAnaComunicazioni> sigasAnaComunicazioniList = sigasAnaComunicazioniRepository.findBySigasAnagraficaSoggettiIdAnagAndAnnualitaAndSigasTipoComunicazioniDenominazioneOrderByDataDocumentoAsc
    			(sigasAnagraficaSoggetti.getIdAnag(), anno, TipoDocumento.DICHIARAZIONE_UTF.getName());
		
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

    	boolean is2018 = "2018".equalsIgnoreCase(annualita);
    	int limiteControllo;
    	
    	if(is2018) {
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
		} else if (utenza_mc_f_6 != consumi_m_6 && is2018) {
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
		} else if (utenza_mc_g_6 != consumi_m_6 && is2018) {
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
		} else if (consumi_i_10 != consumi_m_6 && is2018) {
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
    	return sigasImportRepository.save(sigasImport);
    }
    
}
