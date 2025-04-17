import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ConfigService } from '../../core/services/config.service';
import { LoggerService } from '../../core/services/logger.service';
import { ConfermaAliquotaRequest } from '../../commons/request/conferma-aliquota-request';
import { RicercaDocumentazioneRequest } from '../../commons/request/ricerca-documentazione-request';
import { AnagraficaSoggettoVO } from '../../commons/vo/soggetti-vo';
import { TipoDocumentoVO } from '../../commons/vo/tipo-documento-vo';
import { DocumentiVO } from '../../commons/vo/documenti-vo';
import { ConfermaDocumentazioneRequest } from '../../commons/request/conferma-documentazione-request';
import { AllegatoMultipleFieldVO } from "../../commons/vo/allegato-multiple-field-vo";


@Injectable({
  providedIn: 'root'
})
export class DocumentazioneService {

  constructor(
    private http: HttpClient,
    private config: ConfigService,
    private logger: LoggerService,
  ) {
    logger.info('DocumentazioneService constructor');
  }
  
///RICERCA DOCUMENTAZIONE
  private ricercaDocumentazioneRequest: RicercaDocumentazioneRequest;

  public set ricercaDocumentazione(ricercaDocumentazioneRequest: RicercaDocumentazioneRequest) {
      this.ricercaDocumentazioneRequest = ricercaDocumentazioneRequest;
  }
  
  public get ricercaDocumentazione(): RicercaDocumentazioneRequest {
      return this.ricercaDocumentazioneRequest;
  }
  
  
  
  private confermaDocumentazioneRequest: ConfermaDocumentazioneRequest;

  public set confermaDocumentazione(confermaDocumentazioneRequest: ConfermaDocumentazioneRequest) {
      this.confermaDocumentazioneRequest = confermaDocumentazioneRequest;
  }
  
  public get confermaDocumentazione(): ConfermaDocumentazioneRequest {
      return this.confermaDocumentazioneRequest;
  }
  
  public ricercaAziendeAccreditato() {
      var url: string = this.config.getBEServer() + '/rest/documentazione/ricercaAziendeAccreditato';
      return this.http.get<Array<AnagraficaSoggettoVO>>(url);
    }
  
  public listaTipoDocumenti() {
      var url: string = this.config.getBEServer() + '/rest/documentazione/listaTipoDocumenti';
      return this.http.get<Array<TipoDocumentoVO>>(url);
    }
  
  ricercaDocumenti() {
      var url: string = this.config.getBEServer() + '/rest/documentazione/ricercaDocumenti';
      //const header = new HttpHeaders({ 'Content-Type': 'application/json; charset=utf-8' });
      return this.http.post<Array<DocumentiVO>>(url, this.ricercaDocumentazioneRequest);
  }
  
  salvaDocumentazione(fileMaster: File, fileAllegati: Array<AllegatoMultipleFieldVO>) {
      var url: string = this.config.getBEServer() + '/rest/documentazione/salvaDocumentazione';      
      
      let formData: FormData = new FormData();
      if (null != fileMaster) {
        formData.append('data', fileMaster, fileMaster.name);
        console.log('Data',fileMaster.name)
        formData.append('fileName', fileMaster.name);
      }
      
      if(fileAllegati!=null){
          formData.append('numeroAllegati', fileAllegati.length.toString());  
          
          fileAllegati.forEach((item, index) => {
              formData.append('allegatoDescrizione'+index, item.descrizione);
              formData.append('allegatoData'+index, item.file);
              formData.append('allegatoNome'+index, item.filename);
              
          });
      }      
      
      formData.append('idAnag', this.confermaDocumentazioneRequest.documentiVO.anagraficaSoggettoVO.idAnag.toString());
      if(this.confermaDocumentazioneRequest.documentiVO.annualita!=null){
          formData.append('annualita', this.confermaDocumentazioneRequest.documentiVO.annualita.toString());
      }     
      
      formData.append('cfPiva', this.confermaDocumentazioneRequest.documentiVO.cfPiva);

      if(this.confermaDocumentazioneRequest.documentiVO.tipoDocumentoVO.codiceTipoDocumento == "DEPO" || this.confermaDocumentazioneRequest.documentiVO.tipoDocumentoVO.codiceTipoDocumento == "DEPO_INT"){
        formData.append('nomeFile', this.confermaDocumentazioneRequest.documentiVO.anagraficaSoggettoVO.denominazione + "_richiesta_deposito_cauzionale.pdf");
      } else {
        formData.append('nomeFile', fileMaster.name);
      }      

      formData.append('note', this.confermaDocumentazioneRequest.documentiVO.note);
      formData.append('rifArchivio', this.confermaDocumentazioneRequest.documentiVO.rifArchivio);
      formData.append('idTipoDocumento', this.confermaDocumentazioneRequest.documentiVO.tipoDocumentoVO.idTipoDocumento.toString());
      
      if(this.confermaDocumentazioneRequest.documentiVO.tipoComunicazioneVO!=null){
          formData.append('idTipoComunicazione', this.confermaDocumentazioneRequest.documentiVO.tipoComunicazioneVO.idTipoDocumento.toString());
      }      
      
      if(this.confermaDocumentazioneRequest.documentiVO.tipoRimborsoVO!=null){
          formData.append('idTipoRimborso', this.confermaDocumentazioneRequest.documentiVO.tipoRimborsoVO.idTipoDocumento.toString());
      }      
      
      if(this.confermaDocumentazioneRequest.documentiVO.nProtocolloAccertamento!=null){
          formData.append('nProtocolloAccertamento', this.confermaDocumentazioneRequest.documentiVO.nProtocolloAccertamento.toString());          
      }
      
      if(this.confermaDocumentazioneRequest.documentiVO.annoProtocolloAccertamento!=null){
          formData.append('annoProtocolloAccertamento', this.confermaDocumentazioneRequest.documentiVO.annoProtocolloAccertamento.toString());          
      }

      if(this.confermaDocumentazioneRequest.codiceProvincia!=null){
        formData.append('codiceProvincia', this.confermaDocumentazioneRequest.codiceProvincia.toString());          
      }

      if(this.confermaDocumentazioneRequest.importo!=null){
        formData.append('importo', this.confermaDocumentazioneRequest.importo.toString());          
      }

      if(this.confermaDocumentazioneRequest.depCausionaleIndirizzo!=null && 
         this.confermaDocumentazioneRequest.depCausionaleIndirizzo!="")
      {
        formData.append('depCausionaleIndirizzo', this.confermaDocumentazioneRequest.depCausionaleIndirizzo.toString());          
      }
  
      return this.http.post<Array<DocumentiVO>>(url, formData);
  }
  
  
  public getUrlScaricaDocumentoAllegato(idDocumento: string, fileName: string): String {
      return this.config.getBEServer() + '/rest/documentazione/getAllegato/'+idDocumento+'/'+fileName;
    }
  
  public getUrlScaricaPacchetto(): String {
      return this.config.getBEServer() + '/rest/documentazione/getPacchettoDocumenti/';
    }
  
  

  
  public getUrlScaricaDocumentoMaster(): String {
      return this.config.getBEServer() + '/rest/documentazione/getDocumentoMaster/';
    }
  

}
