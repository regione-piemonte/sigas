import { LuoghiService } from './../../shared/service/luoghi-service';
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ConfigService } from '../../core/services/config.service';
import { RicercaConsumiRequest } from '../../commons/request/ricerca-consumi-request';
import { ConfermaSoggettoRequest } from '../../commons/request/conferma-soggetto-request';
import { RicercaVersamentiRequest } from '../../commons/request/ricerca-versamenti-request';
import { LoggerService } from '../../core/services/logger.service';
import { DownloadReport } from '../../commons/request/download-Report-request';
import { DownloadVersamentiReport } from '../../commons/request/download-Versamenti-Report-request';
import { ItemVersamentiReport } from '../../commons/request/ItemVersamentiReport';
import { DownloadAccertamentiReport } from '../../commons/request/download-Accertamenti-Report-request';
import { DownloadSoggettiReport } from '../../commons/request/download-Soggetti-Report-request';
import { DownloadComunicazioneReport } from '../../commons/request/download-Comunicazione-Report-request';
import { AssociaSoggettoRequest } from '../../commons/request/associa-soggetto-request';
import { ConfermaConsumiRequest } from '../../commons/request/conferma-consumi-request';
import { ValidaSoggettoRequest } from '../../commons/request/valida-soggetto-request';
import { GestioneAllarmeRequest } from '../../commons/request/gestione-Allarme-request';
import { ConfermaVersamentoRequest } from '../../commons/request/conferma-versamento-request';
import { SalvaRimborsoRequest } from '../../commons/request/salva-rimborso-request';

import { ConsumiVO } from '../../commons/vo/consumi-vo';
import { ScartoVO } from '../../commons/vo/scarto-vo';
import { AnagraficaSoggettoVO } from '../../commons/vo/soggetti-vo';
import { ConsumiPrVO } from '../../commons/vo/consumi-pr-vo';
import { DichiaranteVO } from '../../commons/vo/dichiarante-vo';
import { FusioneSoggettoVO } from '../../commons/vo/fusione-vo';
import { AliquotaVO } from '../../commons/vo/aliquota-vo';
import { StoricoConsumiVO } from '../../commons/vo/storico-consumi-vo';
import { ProvinceVO } from '../../commons/vo/luoghi-vo/province-vo';
import { TipoVersamentiVO } from '../../commons/vo/tipo-versamenti-vo';
import { VersamentiPrVO } from '../../commons/vo/versamenti-pr-vo';
import { AllarmiSoggettoVO } from '../../commons/vo/allarmi-soggetto-vo';
import { Observable, Subject  } from 'rxjs';
import { AnaComunicazioniVO } from '../../commons/vo/ana-comunicazioni-vo';
import { TipoComunicazioniVO } from '../../commons/vo/tipo-comunicazioni-vo';
import { ConfermaAnaComunicazioniRequest } from '../../commons/request/conferma-anaComunicazioni-request';
import { RimborsoVO } from '../../commons/vo/rimborso-vo';
import { RicercaAnaComunicazioniRequest } from '../../commons/request/ricerca-anaComunicazioni-request';
import { AllarmeDocumentoRequest } from '../../commons/request/allarme-documento-request';
import { MessaggiVO } from '../../commons/vo/messaggi-vo';
import { DownloadDettaglioSoggettoReport } from '../../commons/request/DownloadDettaglioSoggettoReport-request';
import { UpdateAllarmeAccertamentoRequest } from '../../commons/request/update-allarme-accertamento-request';
import { CompensazioniPrVO } from '../../commons/vo/compensazioni-pr-vo';
import { SalvaCompensazioneRequest } from '../../commons/request/salva-compensazione-request';
import { AnnualitaVersamentoVO } from '../../commons/vo/annualita-versamenti-vo';
import { StoricoAnagraficaSoggettoVO } from '../../commons/vo/storico-anagrafica-soggetto-vo';
import { RateoVO } from '../../commons/vo/rateo-vo';
import { RicercaRateoRequest } from '../../commons/request/ricerca-rateo-request';
import { RicercaStoricoSoggettiRequest } from '../../commons/request/ricerca-storico-soggetti-riquest';
import { RicercaSoggettoIncorporatoRequest } from '../../commons/request/ricerca-soggetto-incorporato-request';


@Injectable({
  providedIn: 'root'
})
export class AnagraficaSoggettiService {

  private ricercaConsumiRequest: RicercaConsumiRequest;
  private ricercaAnaComunicazioniRequest: RicercaAnaComunicazioniRequest;
  private confermaAnaComunicazioniRequest: ConfermaAnaComunicazioniRequest;
  private ricercaVersamentiRequest: RicercaVersamentiRequest;
  private gestioneAllarmeRequest: GestioneAllarmeRequest;

  private consumo: ConsumiVO;
  private idConsumi: number;
  private idTipoComunicazione: number;
  private dichiarante: DichiaranteVO;
  private dichiaranteSelezionato: DichiaranteVO;
  private soggetto: AnagraficaSoggettoVO;
  private anno: string;
  private validazione: boolean;
  private codiceAzienda: string;
  private idDocumento: number;
  private idAnag: number;
  private requestAcc: Array<DownloadVersamentiReport>;
  listaAccertamentiRequest: Array<ConfermaVersamentoRequest>;
  private subscribers: any = {};
  private ricercaSoggettoIncorporatoRequest: RicercaSoggettoIncorporatoRequest

  /**
   * Evolutiva 202107-01 
  */  
  private totaleDichiarazioneSubject = new Subject<any>();
  public totaleDichiarazione = this.totaleDichiarazioneSubject.asObservable();
  emitTotaleDichiarazione(totaleDichiarazione: any) {
    this.totaleDichiarazioneSubject.next(totaleDichiarazione);
  }

  private totaleVersatoSubject = new Subject<any>();
  public totaleVersato = this.totaleVersatoSubject.asObservable();
  emitTotaleVersato(totaleVersato: any) {
    this.totaleVersatoSubject.next(totaleVersato);
  }

  private versamentiPrSubject = new Subject<any>();
  public versamentiPr = this.versamentiPrSubject.asObservable();
  emitVersamentiPr(versamentiPr: any){
    this.versamentiPrSubject.next(versamentiPr);
  }

  private versamentiPrAnnoPrecSubject = new Subject<any>();
  public versamentiPrAnnoPrec = this.versamentiPrAnnoPrecSubject.asObservable();
  emitVersamentiPrAnnoPrec(versamentiPrAnnoPrec: any){
    this.versamentiPrAnnoPrecSubject.next(versamentiPrAnnoPrec);
  }

  private consumiPrSubject = new Subject<any>();
  public consumiPr = this.consumiPrSubject.asObservable();
  emitConsumiPr(consumiPr: any){
    this.consumiPrSubject.next(consumiPr);
  };

  /**
   * Evolutiva  REQ-26 Editing Rateo
  */
  private rateoRefreshAnniProvincieDispinibiliSubject = new Subject<any>();
  public rateoRefreshAnniProvincieDispinibiliObservable = this.rateoRefreshAnniProvincieDispinibiliSubject.asObservable();
  emitRateoRefreshAnniProvincieDispinibili(rateoRefreshAnniProvincieDispinibili: any) {
    this.rateoRefreshAnniProvincieDispinibiliSubject.next(rateoRefreshAnniProvincieDispinibili);
  }
  //************************************/


  /**
   * refresh rateo per versamenti
  */
  private versamentiRefreshRateoSubject = new Subject<any>();
  public versamentiRefreshRateoObservable = this.versamentiRefreshRateoSubject.asObservable();
  emitVersamentiRefreshRateo(versamentiRefreshRateo: any) {
    this.versamentiRefreshRateoSubject.next(versamentiRefreshRateo);
  }

  public ricercaConsumiForProvinceByAnno(anno: number) {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaConsumiPerProvince';
    let params = new HttpParams().set('id', this.dichiarante.idAnag.toString())
    .set('anno', anno.toString());
    return this.http.get<Array<ConsumiPrVO>>(url, { params: params });
  }

  public ricercaVersamentiForProvinceByAnno(anno: number) {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaVersamentiPerProvince';    
    let params = new HttpParams().set('id', this.dichiarante.idAnag.toString())     
    .set('anno',anno.toString());
    return this.http.get<Array<VersamentiPrVO>>(url, { params: params });
  }
  //-----------------------------------
  
  constructor(
    private http: HttpClient,
    private config: ConfigService,
    private logger: LoggerService,
    private luoghiService: LuoghiService
  ) {
    logger.info('ConsultaSoggettiService constructor');
  }

  public set ricercaConsumiReq(ricercaConsumiRequest: RicercaConsumiRequest) {
    this.ricercaConsumiRequest = ricercaConsumiRequest;
  }

  public get ricercaConsumiReq(): RicercaConsumiRequest {
    return this.ricercaConsumiRequest;
  }

  public set setCodiceAzienda(codAzienda: string) {
    this.codiceAzienda = codAzienda;
  }

  public get getCodiceAzienda(): string {
    return this.codiceAzienda;
  }

  public set setIdDoc(idDocumento: number) {
    this.idDocumento = idDocumento;
  }

  public get getIdDoc(): number {
    return this.idDocumento;
  }

  public set setIdAnag(idAnag: number) {
    this.idAnag = idAnag;
  }

  public get getIdAnag(): number {
    return this.idAnag;
  }

  public set confermaAnaComunicazioniReq(confermaAnaComunicazioniRequest: ConfermaAnaComunicazioniRequest) {
    this.confermaAnaComunicazioniRequest = confermaAnaComunicazioniRequest;
  }

  public get confermaAnaComunicazioniReq(): ConfermaAnaComunicazioniRequest {
    return this.confermaAnaComunicazioniRequest;
  }

  public set ricercaAnaComunicazioniReq(ricercaAnaComunicazioniRequest: RicercaAnaComunicazioniRequest) {
    this.ricercaAnaComunicazioniRequest = ricercaAnaComunicazioniRequest;
  }

  public get ricercaAnaComunicazioniReq(): RicercaAnaComunicazioniRequest {
    return this.ricercaAnaComunicazioniRequest;
  }

  public get valida(): boolean {
    return this.validazione;
  }
  public set valida(validazione: boolean) {
    this.validazione = validazione;
  }

  public get dettaglioConsumo(): ConsumiVO {
    return this.consumo;
  }
  public set dettaglioConsumo(consumo: ConsumiVO) {
    this.consumo = consumo;
  }

  public get idConsumiScarti(): number {
    return this.idConsumi;
  }
  public set idConsumiScarti(id: number) {
    this.idConsumi = id;
  }

  public get idTipoComunicazion(): number {
    return this.idTipoComunicazione;
  }
  public set idTipoComunicazion(id: number) {
    this.idTipoComunicazione = id;
  }

  public get annoDichiarazione(): string {
    return this.anno;
  }

  public set annoDichiarazione(anno: string) {
    this.anno = anno;
  }

  public set confermaSoggetto(soggetto: AnagraficaSoggettoVO) {
      this.soggetto = soggetto;
  }
  public get confermaSoggetto(): AnagraficaSoggettoVO {
      return this.soggetto;
  }

  public get headerDichiarante(): DichiaranteVO {
    return this.dichiarante;
  }

  public set headerDichiarante(dichiarante: DichiaranteVO) {
    this.dichiarante = dichiarante;
  }

  public get headerDichiaranteSelezionato(): DichiaranteVO {
    return this.dichiaranteSelezionato;
  }

  public set headerDichiaranteSelezionato(dichiaranteSelezionato: DichiaranteVO) {
    this.dichiaranteSelezionato = dichiaranteSelezionato;
  }

  //CR REQ17 possibilità di aeffetuare n-compensazioni
  public salvaCompensazione(compensazionePrVO : CompensazioniPrVO) {
    var url: string = this.config.getBEServer() + '/rest/home/salvaCompensazione'; 
    
    let salvaCompensazioneRequest = <SalvaCompensazioneRequest>{};
    salvaCompensazioneRequest.compensazionePrVO = compensazionePrVO;
    return this.http.post<SalvaCompensazioneRequest>(url, salvaCompensazioneRequest);
  }
  //---------------------------------------------------

  public ricercaConsumi() {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaConsumi';
    return this.http.post<Array<ConsumiVO>>(url, this.ricercaConsumiRequest);
  }
  public ricercaConsumiMock() {
    let validato = this.ricercaConsumiRequest.validato;

    if (validato === "VALIDATO") {
      var url: string = 'assets/mock/consumi-validati.json';
      return this.http.get<Array<ConsumiVO>>(url);
    } else if (validato === "NON_VALIDATO") {
      var url: string = 'assets/mock/consumi-nonvalidati.json';
      return this.http.get<Array<ConsumiVO>>(url);
    } else {
      var url: string = 'assets/mock/consumi.json';
      return this.http.get<Array<ConsumiVO>>(url);
    }
  }

  public ricercaPerAnno() {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaAnnualita';
    return this.http.get<Array<string>>(url);
  }

  public ricercaPerAnnoMock() {
    var url: string = 'assets/mock/report-anno.json';
    return this.http.get<Array<string>>(url);
  }

  public ricercaSoggetto() {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaSoggetto';
    return this.http.post<AnagraficaSoggettoVO>(url, this.dichiarante.idAnag);
  }

  public ricercaSoggettobyIdAnagrafica(idAnag: number) {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaSoggettoByID';
    let params = new HttpParams().set('id', idAnag.toString());
    return this.http.get<AnagraficaSoggettoVO>(url, { params: params });
  }

  public ricercaSoggettoMock() {
    var url: string = 'assets/mock/soggetto.json';
    return this.http.get<AnagraficaSoggettoVO>(url);
  }
  
  public numberOfSoggetti() {
     var url: string = this.config.getBEServer() + '/rest/home/numberOfSoggetti';
     return this.http.get<string>(url);
   }

  public ricercaListaSoggetti() {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaListaSoggetti';
    return this.http.post<Array<AnagraficaSoggettoVO>>(url, this.ricercaConsumiRequest);
  }  
  
  public ricercaSoggettoByID() {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaSoggettoByID';
    let params = new HttpParams().set('id', this.dichiarante.idAnag.toString());
    return this.http.get<AnagraficaSoggettoVO>(url, { params: params });
  }

  public ricercaSoggettoByCode(codice: string) {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaSoggettoByCode';
    let params = new HttpParams().set('codice', codice);
    return this.http.get<AnagraficaSoggettoVO>(url, { params: params });
  }

  public ricercaSoggettoByIdAnag(idAnag: number) {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaSoggettoByID';
    let params = new HttpParams().set('id', idAnag.toString());
    return this.http.get<AnagraficaSoggettoVO>(url, { params: params });
  }

  public ricercaSoggettoSelezionatoByID() {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaSoggettoByID';
    let params = new HttpParams().set('id', this.dichiaranteSelezionato.idAnag.toString());
    return this.http.get<AnagraficaSoggettoVO>(url, { params: params });
  }

  public ricercaSoggetti() {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaSoggetti';
    return this.http.get<Array<AnagraficaSoggettoVO>>(url);
  }

  public ricercaConsumiForProvince() {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaConsumiPerProvince';
    let params = new HttpParams().set('id', this.dichiarante.idAnag.toString())
    .set('anno', this.anno);
    return this.http.get<Array<ConsumiPrVO>>(url, { params: params });
  }

  public ricercaConsumiForProvinceByIdAnag(idAnag: number) {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaConsumiPerProvince';
    let params = new HttpParams().set('id', idAnag.toString())
                                 .set('anno', this.anno);
    return this.http.get<Array<ConsumiPrVO>>(url, { params: params });
  }
  
  public ricercaConsumiForProvinceAnnoPrec() {
      var url: string = this.config.getBEServer() + '/rest/home/ricercaConsumiPerProvince';
      let params = new HttpParams().set('id', this.dichiarante.idAnag.toString())
      .set('anno', (+this.anno-1).toLocaleString().replace(".", ""));
      return this.http.get<Array<ConsumiPrVO>>(url, { params: params });
    }
  
  public ricercaConsumiForProvinceMock() {
   var url: string = 'assets/mock/consumi-pr.json';
    return this.http.get<Array<ConsumiPrVO>>(url);
  }

  public ricercaVersamentiForProvince() {
      var url: string = this.config.getBEServer() + '/rest/home/ricercaVersamentiPerProvince';
      let params = new HttpParams().set('id', this.dichiarante.idAnag.toString())
      .set('anno', this.anno);
      return this.http.get<Array<VersamentiPrVO>>(url, { params: params });
  }
  
  
  public ricercaVersamentiForProvinceAnnoPrec() {
      var url: string = this.config.getBEServer() + '/rest/home/ricercaVersamentiPerProvince';    
      let params = new HttpParams().set('id', this.dichiarante.idAnag.toString())
      //.set('anno',(+this.anno -1).toLocaleString());     
      .set('anno',(+this.anno -1).toLocaleString().replace(".", ""));
      return this.http.get<Array<VersamentiPrVO>>(url, { params: params });
  }
  
  public scaricaExcel() {
    var url: string = this.config.getBEServer() + '/rest/home/salvaConsumiPerProvince';
    let request = new DownloadReport(this.dichiarante.idAnag, this.annoDichiarazione);
    console.log(request);
    const body = request;
    return this.http.post(url, body, { responseType: 'blob' }).map(
      (res) => {
        return new Blob([res], { type: 'application/xls' })
      });
    }

  public scaricaExcelSoggetto(request: DownloadDettaglioSoggettoReport) {
    var url: string = this.config.getBEServer() + '/rest/home/salvaSoggetto';    
    console.log(request);
    const body = request;
    return this.http.post(url, body, { responseType: 'blob' }).map(
      (res) => {
        return new Blob([res], { type: 'application/xls' })
      });
  }

  public scaricaExcelElencoSoggetto(soggetti: Array<ConsumiVO>) {
    var url: string = this.config.getBEServer() + '/rest/home/salvaElencoSoggetti';
    let request = new DownloadSoggettiReport(soggetti, this.annoDichiarazione);
    console.log(request);
    const body = request;
    return this.http.post(url, body, { responseType: 'blob' }).map(
      (res) => {
        return new Blob([res], { type: 'application/xls' })
      });
  }

  public scaricaExcelElencoVersamenti(id_provincia: number, id_tipo_versamento: number, mese: string, itemVersamentiReportList: Array<ItemVersamentiReport>) {
    var url: string = this.config.getBEServer() + '/rest/versamenti/salvaElencoVersamenti';
    
    let request = new DownloadVersamentiReport(this.dichiarante.idAnag, this.dichiarante.denominazione, 
                                               this.annoDichiarazione, id_provincia, id_tipo_versamento, 
                                               mese, itemVersamentiReportList);
    console.log(request);
    const body = request;
    return this.http.post(url, body, { responseType: 'blob' }).map(
      (res) => {
        return new Blob([res], { type: 'application/xls' })
      });
  }

  public scaricaExcelElencoAccertamenti(accertamenti : Array<VersamentiPrVO>) {
    var url: string = this.config.getBEServer() + '/rest/home/salvaElencoAccertamenti';
    let request = new DownloadAccertamentiReport(accertamenti, this.dichiarante.idAnag);
    console.log(request);
    const body = request;
    return this.http.post(url, body, { responseType: 'blob' }).map(
      (res) => {
        return new Blob([res], { type: 'application/xls' })
      });
  }

  public scaricaExcelElencoDocumenti(id_tipo_versamento: number) {
    var url: string = this.config.getBEServer() + '/rest/home/salvaElencoDocumenti';
    let request = new DownloadComunicazioneReport(this.dichiarante.idAnag, this.annoDichiarazione, id_tipo_versamento);
    console.log(request);
    const body = request;
    return this.http.post(url, body, { responseType: 'blob' }).map(
      (res) => {
        return new Blob([res], { type: 'application/xls' })
      });
  }

  public confermaModificaSoggetto(soggettoUpdate: AnagraficaSoggettoVO, annualita: String, ownerOperazione: String) {
      var url: string = this.config.getBEServer() + '/rest/home/updateSoggetto';

      const body = new ConfermaSoggettoRequest(soggettoUpdate, annualita, ownerOperazione);
      return this.http.post<AnagraficaSoggettoVO>(url, body);
  }

  public confermaInserimentoSoggetto(soggettoInsert: AnagraficaSoggettoVO, annualita: String, ownerOperazione: String) {
      var url: string = this.config.getBEServer() + '/rest/home/insertSoggetto';

      const body = new ConfermaSoggettoRequest(soggettoInsert, annualita, ownerOperazione);
      return this.http.post<AnagraficaSoggettoVO>(url, body);
  }

  public confermaAssociaSoggetto(associateSoggettoNew: AnagraficaSoggettoVO, associateSoggettoSelezionato: AnagraficaSoggettoVO) {
    var url: string = this.config.getBEServer() + '/rest/home/associateSoggetto';

    const body = new AssociaSoggettoRequest(associateSoggettoNew, associateSoggettoSelezionato);
    return this.http.post<AnagraficaSoggettoVO>(url, body);
  }

  public confermaFusioneSoggetto(fusioneReq: FusioneSoggettoVO) {
    var url: string = this.config.getBEServer() + '/rest/home/fusioneSoggetto';

    return this.http.post<AnagraficaSoggettoVO>(url, fusioneReq);
  }

  public cancellaFusioneSoggetto(idAnagIncorporante: number){
    var url: string = this.config.getBEServer() + '/rest/home/cancella-fusione-soggetto/' + idAnagIncorporante;
    return this.http.post<String>(url, null);
  }

  public ricercaScartiByIdConsumi() {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaScartiByIdConsumi';
    let params = new HttpParams().set('id', this.idConsumi.toString());
    return this.http.get<Array<ScartoVO>>(url, { params: params });
  }

  public getAllAliquote() {
    var url: string = this.config.getBEServer() + '/rest/home/getAllAliquote';
  console.log(this.anno);
  let params = new HttpParams().set('annoDichiarazione', this.anno=='' ? '9999':this.anno.toString());
    return this.http.get<Array<AliquotaVO>>(url, { params: params});
  }

  public confermaModificaConsumi(consumiUpdate: ConsumiPrVO, scartiUpdate: Array<ScartoVO>) {
    var url: string = this.config.getBEServer() + '/rest/home/updateConsumi';

    const body = new ConfermaConsumiRequest(consumiUpdate, scartiUpdate);
    return this.http.post<ConsumiPrVO>(url, body);
  }
  
  public updateTotaleDichConsumi(consumiUpdate: ConsumiPrVO) {
      var url: string = this.config.getBEServer() + '/rest/home/updateTotaleDichConsumi';

      const body = new ConfermaConsumiRequest(consumiUpdate, null);
      return this.http.post<ConsumiPrVO>(url, body);
    }
  
  
  public updateCompensazioneConsumi(consumiUpdate: ConsumiPrVO) {
    var url: string = this.config.getBEServer() + '/rest/home/updateCompensazioneConsumi'; 

    const body = new ConfermaConsumiRequest(consumiUpdate, null);
    return this.http.post<ConsumiPrVO>(url, body);
  }

  public storicoConsumi(idConsumo: number) {
    var url: string = this.config.getBEServer() + '/rest/home/storicoConsumi';

    let params = new HttpParams().set('id', idConsumo.toString());
    return this.http.get<Array<StoricoConsumiVO>>(url, { params: params });
  }

  public ripristinaModificaConsumi(idConsumoStorico: number, idConsumo: number) {
    var url: string = this.config.getBEServer() + '/rest/home/ripristinaModificaConsumi';

    let params = new HttpParams()
      .set('id', idConsumo.toString())
      .set('idStorico', idConsumoStorico.toString());
    return this.http.get<ConsumiPrVO>(url, { params: params });
  }

  public validaSoggetto(validato: boolean) {
    var url: string = this.config.getBEServer() + '/rest/home/validaSoggetto';

    const body = new ValidaSoggettoRequest(this.dichiarante.idAnag,  this.anno, validato);
    return this.http.post<string>(url, body);
  }
  
  public ricercaAnniVersamenti() {
     var url: string = this.config.getBEServer() + '/rest/versamenti/ricercaAnnualitaVersamenti';
     if   (this.dichiarante !=null && this.dichiarante.idAnag != null){
         let params = new HttpParams().set('id', this.dichiarante.idAnag.toString());
         //return this.http.get<Array<string>>(url, { params: params });
         return this.http.get<AnnualitaVersamentoVO>(url, { params: params });
     }
  }

  public ricercaAnniVersamentiPerRicerca() {
    var url: string = this.config.getBEServer() + '/rest/versamenti/annualitaVersamentiPerRicerca';
    if   (this.dichiarante !=null && this.dichiarante.idAnag != null){
        let params = new HttpParams().set('id', this.dichiarante.idAnag.toString());
        return this.http.get<Array<string>>(url, { params: params });        
    }
 }
  
  public ricercaProvinceVersamenti() {
    var url: string = this.config.getBEServer() + '/rest/versamenti/ricercaProvinceVersamenti';
    if   (this.dichiarante.idAnag != null){
        let params = new HttpParams().set('id', this.dichiarante.idAnag.toString());
        return this.http.get<Array<ProvinceVO>>(url, { params: params });
    }
  }

  public ricercaListaRimborsi() {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaListaRimborsi';

    let params = new HttpParams().set('idAnag',this.dichiarante.idAnag.toString());
    return this.http.get<Array<RimborsoVO>>(url, { params: params });
  }

  public salvaRimborso(rimborso: RimborsoVO) {
    var url: string = this.config.getBEServer() + '/rest/home/salvaRimborso';

    const body = new SalvaRimborsoRequest(rimborso);
    return this.http.post<RimborsoVO>(url, body);
  }

  public salvaDetermina(rimborso: RimborsoVO) {
    var url: string = this.config.getBEServer() + '/rest/home/salvaDetermina';
    let request = new DownloadReport(this.dichiarante.idAnag, rimborso.annualita);
    request.idRimborso = rimborso.idRimborso;
    request.idComunicazioni = rimborso.anaComunicazioniVO.idComunicazione;
    console.log(request);
    const body = request;
    return this.http.post(url, body, { responseType: 'blob' }).map(
      (res) => {
        return new Blob([res], { type: 'application/xls' })
      });
  }

  public ricercaTipoVersamenti() {
      var url: string = this.config.getBEServer() + '/rest/versamenti/ricercaTipoVersamenti';
      return this.http.get<Array<TipoVersamentiVO>>(url);
  }
    
  public ricercaMeseVersamenti(annualita: string) {
    var url: string = this.config.getBEServer() + '/rest/versamenti/ricercaMeseVersamenti';
      if   (this.dichiarante.idAnag != null){
          let params = new HttpParams().set('id', this.dichiarante.idAnag.toString())
          .set('annualita', annualita);
          return this.http.get<Array<string>>(url, { params: params });
      }
  }
  
  public ricercaAllarmi(idConsumo:string) {
    var url: string = this.config.getBEServer() + '/rest/versamenti/ricercaAllarmiVersamento';
      
    let params = new HttpParams().set('id', idConsumo);
    return this.http.get<AllarmiSoggettoVO>(url, { params: params });
  }

  public set ricercaVersamentiReq(ricercaVersamentiRequest: RicercaVersamentiRequest) {
     this.ricercaVersamentiRequest = ricercaVersamentiRequest;
  }

  public ricercaDocumentiByIdAnag() {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaDocumentiByIdAnag';
    let params = new HttpParams().set('id', this.dichiarante.idAnag.toString());
    return this.http.get<Array<AnaComunicazioniVO>>(url, { params: params });
  }

  public ricercaAllarmiByIdDocumentoAndCodiceAzienda() {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaAllarmiByIdDocumentoAndCodiceAzienda';
    let params = new HttpParams().set('idDocumento', this.idDocumento.toString())
    .set('codiceAzienda', this.codiceAzienda.toString());
  return this.http.get<Array<AllarmiSoggettoVO>>(url, { params: params });
  }


  public ricercaDocumentiByAnnoAndTipologia() {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaDocumentiByAnnoAndTipologia';
    this.ricercaAnaComunicazioniRequest.idAnag = this.dichiarante.idAnag;
    return this.http.post<Array<AnaComunicazioniVO>>(url, this.ricercaAnaComunicazioniRequest);
  }

  public allarmeDocumento(allarmeOn: boolean, idComunicazione: number) {
    var url: string = this.config.getBEServer() + '/rest/home/allarmeDocumento';

    if ( this.dichiarante.idAnag != null ) {
      const body = new AllarmeDocumentoRequest( this.dichiarante.idAnag, idComunicazione,
        'COMUNICAZIONI', allarmeOn, 'Allarme relativo al documento ' + idComunicazione + ', del soggetto ' + this.dichiarante.idAnag);
        return this.http.post<AllarmiSoggettoVO>(url, body);
    }
  }

  public getAllTipoComunicazioni() {
    var url: string = this.config.getBEServer() + '/rest/home/getAllTipoComunicazioni';
    return this.http.get<Array<TipoComunicazioniVO>>(url);
  }

  public ricercaTipoComunicazioniByIdTipoComunicazione() {
    var url: string = this.config.getBEServer() + '/rest/home/ricercaTipoComunicazioniByIdTipoComunicazione';
    let params = new HttpParams().set('id', this.idTipoComunicazione.toString());
    return this.http.get<TipoComunicazioniVO>(url, { params: params });
  }

  public getUrlScaricaDocumento(): String {
    return this.config.getBEServer() + '/rest/home/stampaDocumento/';
  }
  
  public getUrlScaricaDocumentoAllegato(descComunicazione: string, fileName: string): String {
      return this.config.getBEServer() + '/rest/home/stampaDocumentoAllegato/'+descComunicazione+'/'+fileName;
  }  
  
  public getUrlScaricaPacchetto(descComunicazione: string) {
      console.log('call ricercaAccertamenti');
      var url: string = this.config.getBEServer() + '/rest/home/scaricaPacchetto';
      let params = new HttpParams().set('descComunicazione', descComunicazione);
      
      return this.http.get(url,{params : params})
  }
  
  public getUrlScaricaDocumentoMaster(): String {
      return this.config.getBEServer() + '/rest/home/stampaDocumentoMaster/';
  }  

  fileChange(file: File): Observable<AnaComunicazioniVO> {
    var url: string = this.config.getBEServer() + '/rest/home/salvaAllegatoVerbale';
    let formData: FormData = new FormData();
    if (null != file) {
      formData.append('data', file, file.name);
      formData.append('fileName', file.name);
    }
    formData.append('id', this.dichiarante.idAnag.toString());
    formData.append('annualita', this.confermaAnaComunicazioniRequest.anaComunicazioni.annualita.toString());
    formData.append('note', this.confermaAnaComunicazioniRequest.anaComunicazioni.note.toString());
    formData.append('datiRiassuntivi', this.confermaAnaComunicazioniRequest.anaComunicazioni.datiRiassuntivi.toString());
    formData.append('nprotocollo', this.confermaAnaComunicazioniRequest.anaComunicazioni.nprotocollo.toString());
    formData.append('rifArchivio', this.confermaAnaComunicazioniRequest.anaComunicazioni.rifArchivio.toString());
    formData.append('dataDocumento', this.formatDate(this.confermaAnaComunicazioniRequest.anaComunicazioni.dataDocumento.toString()) );
    var idTipoComFromRequest = this.confermaAnaComunicazioniRequest.anaComunicazioni.tipoComunicazioneVO.idTipoComunicazione;
    formData.append('idTipoComunicazione', idTipoComFromRequest.toString());
    formData.append('tipologiaComunicazione', this.confermaAnaComunicazioniRequest.anaComunicazioni.tipoComunicazioneVO.denominazione);
    formData.append('codiceAzienda', this.codiceAzienda);

    return this.http.post<AnaComunicazioniVO>(url, formData);
  }

  updateFileTestataChange(documentoToSave: AnaComunicazioniVO): Observable<AnaComunicazioniVO> {
    console.log('anaCom = ', documentoToSave)
    var url: string = this.config.getBEServer() + '/rest/home/aggiornaTestaAllegato';
    return this.http.post<AnaComunicazioniVO>(url , documentoToSave);
  }

  updateFileChange(file: File): Observable<AnaComunicazioniVO> {
    var url: string = this.config.getBEServer() + '/rest/home/aggiornaAllegatoVerbale';
    console.log(url)
    let formData: FormData = new FormData();
    if (null != file) {
      formData.append('data', file, file.name);
      console.log('Data',file.name)
      formData.append('fileName', file.name);
    }
    console.log('Descr',this.confermaAnaComunicazioniReq.anaComunicazioni.descrizione)
    console.log('confermaAnaComunicazioneRequest = ',this.confermaAnaComunicazioniRequest);
    formData.append('id', this.dichiarante.idAnag.toString());
    formData.append('idComunicazione', this.confermaAnaComunicazioniRequest.anaComunicazioni.idComunicazione.toString());
    formData.append('annualita', this.confermaAnaComunicazioniRequest.anaComunicazioni.annualita.toString());
    formData.append('note', this.confermaAnaComunicazioniRequest.anaComunicazioni.note.toString());
    formData.append('datiRiassuntivi', this.confermaAnaComunicazioniRequest.anaComunicazioni.datiRiassuntivi.toString());
    formData.append('nprotocollo', this.confermaAnaComunicazioniRequest.anaComunicazioni.nprotocollo.toString());
    formData.append('rifArchivio', this.confermaAnaComunicazioniRequest.anaComunicazioni.rifArchivio.toString());
    var idTipoComFromRequest = this.confermaAnaComunicazioniRequest.anaComunicazioni.tipoComunicazioneVO.idTipoComunicazione;
    formData.append('idTipoComunicazione', idTipoComFromRequest.toString());
    formData.append('tipologiaComunicazione', this.confermaAnaComunicazioniRequest.anaComunicazioni.tipoComunicazioneVO.denominazione);
    formData.append('codiceAzienda', this.codiceAzienda);
    formData.append('dataDocumento', this.formatDate(this.confermaAnaComunicazioniRequest.anaComunicazioni.dataDocumento.toString()) );
    console.log('fom date = ', formData)
    return this.http.post<AnaComunicazioniVO>(url, formData);
  }
  
  formatDate(date) {
      var d = new Date(date),
          month = '' + (d.getMonth() + 1),
          day = '' + d.getDate(),
          year = d.getFullYear();

      if (month.length < 2) 
          month = '0' + month;
      if (day.length < 2) 
          day = '0' + day;

      return [year, month, day].join('-');
  }
  
  public ricercaConsumiForProvinceAndAnnualita(annualita: number, provincia:string ) {
      var url: string = this.config.getBEServer() + '/rest/home/ricercaConsumiPerProvinceAndAnnualita';
      let params = new HttpParams().set('id', this.dichiarante.idAnag.toString())
                                  .set('anno', annualita.toString())
                                  .set('provincia', provincia);
     return this.http.get<ConsumiPrVO>(url, { params: params });
  }

  public ricercaConsumiByIdAnagProvinceAndAnnualita(idAnag: number, 
                                                    annualita: number, 
                                                    provincia:string ) 
  {
      var url: string = this.config.getBEServer() + '/rest/home/ricercaConsumiPerProvinceAndAnnualita';
      let params = new HttpParams().set('id', idAnag.toString())
                                  .set('anno', annualita.toString())
                                  .set('provincia', provincia);
                                  
      return this.http.get<ConsumiPrVO>(url, { params: params });
  }

  public get ricercaVersamentiReq(): RicercaVersamentiRequest {
      return this.ricercaVersamentiRequest;
  }
  
  public ricercaVersamenti() {
    var url: string = this.config.getBEServer() + '/rest/versamenti/ricercaVersamenti';
    return this.http.post<Array<VersamentiPrVO>>(url, this.ricercaVersamentiRequest);
  }
  
  public ricercaVersamentiCalcoli(ricercaVersamentiRequest: RicercaVersamentiRequest) {
      var url: string = this.config.getBEServer() + '/rest/versamenti/ricercaVersamenti';
      return this.http.post<Array<VersamentiPrVO>>(url, ricercaVersamentiRequest);
    }

  public allarmeSoggetto( allarme: boolean, id_consumi: number ) {
      var url: string = this.config.getBEServer() + '/rest/versamenti/allarmeSoggetto';
      if ( this.dichiarante.idAnag != null ) {
          const body = new GestioneAllarmeRequest( this.dichiarante.idAnag, id_consumi, allarme);
          return this.http.post<string>( url, body );
      }
  }
  
  public confermaInserimentoVersamento(versamento: VersamentiPrVO) {
      var url: string = this.config.getBEServer() + '/rest/versamenti/insertVersamento';

      const body = new ConfermaVersamentoRequest(versamento);
      return this.http.post<VersamentiPrVO>(url, body);
    }
  
  public confermaModificaVersamento(versamento: VersamentiPrVO) {
      var url: string = this.config.getBEServer() + '/rest/versamenti/updateVersamento';
      console.log('Update url', url);
      const  body = new ConfermaVersamentoRequest(versamento);
      console.log('Body',body);
      return this.http.post<VersamentiPrVO>(url, body);
    }

    
    public ricercaAccertamenti(anno: string, provincia: string) {
      console.log('call ricercaAccertamenti');
      var url: string = this.config.getBEServer() + '/rest/home/elencoAccertamentiByIdAnag';
      let params = new HttpParams().set('idAnag', this.dichiarante.idAnag.toString())
                                    .set('anno', anno)
                                    .set('provincia', provincia);
      
      return this.http.get<Array<VersamentiPrVO>>(url,{params : params})
    }
    
    public confermaModificaAccertamento(versamenti: Array<VersamentiPrVO>) {
      this.listaAccertamentiRequest = [];
      var url: string = this.config.getBEServer() + '/rest/home/updateAccertamenti';
      for(let versamento of versamenti){
        let ver = new VersamentiPrVO(
          versamento.idVersamento,
          versamento.idConsumi,
          versamento.idAnag,
          versamento.annualita,
          versamento.mese,
          versamento.provincia,
          versamento.tipo,
          versamento.consumo,
          versamento.dataVersamento,
          versamento.importo,
          versamento.note,
          versamento.dataAccertamento,
          versamento.importoComplessivo,
          versamento.hasPagamentiVersamenti, 
          versamento.pagamentiVersamenti);
          ver.allarme = versamento.allarme ; 
          console.log('Ver e  ugale', ver);
          let rq = new ConfermaVersamentoRequest(ver);
          console.log('Rq e uguale a ', rq);
          this.listaAccertamentiRequest.push(rq);
      }

      console.log('Update url', url);
      console.log('Body',this.listaAccertamentiRequest);
      return this.http.post<VersamentiPrVO>(url, this.listaAccertamentiRequest);
    }

    public aggiornaAllarmeAccertamento(updateAllarmeAccertamentoRequest: UpdateAllarmeAccertamentoRequest) {      
      var url: string = this.config.getBEServer() + '/rest/home/updateAllarmeAccertamento';
      
      console.log('Update url', url);
      console.log('Body',updateAllarmeAccertamentoRequest);
      return this.http.post<UpdateAllarmeAccertamentoRequest>(url, updateAllarmeAccertamentoRequest);
    }
    
    /**
     * Consente di eliminare un documento.
     *
     * @param idDocumento
     */
    public eliminaDocumento(idDocumento: number) {
        const url = this.config.getBEServer() + '/rest/home/delete/' + idDocumento;
        return this.http.delete(url, {});
    }
    
    /**
     * Ricerca la lista degli storici di un soggetto anagrafica.
     *
     * @param idAnagRif
     */
    public ricercaStoricoAnagraficaSoggetto(idAnagRif: number) {
      var url: string = this.config.getBEServer() + '/rest/home/ricerca-lista-storico-soggetto';    
      let params = new HttpParams().set('id', idAnagRif.toString())      
      return this.http.get<Array<StoricoAnagraficaSoggettoVO>>(url, { params: params });    
    }

    /**
     * Consente di eliminare un versamento.
     *
     * @param idVersamento
     */    
    public eliminaVersamento(idVersamento: number, idAnag: number) {
      var url: string = this.config.getBEServer() + '/rest/versamenti/delete/' + idVersamento;      
      let params = new HttpParams().set('idAnag', idAnag.toString())      
      return this.http.delete(url, { params: params });
    }
    
    public inserisciElencoVersamenti(versamentoList: Array<VersamentiPrVO>) {
      var url: string = this.config.getBEServer() + '/rest/versamenti/insertElencoVersamenti';

      const body = versamentoList.map(versamento => new ConfermaVersamentoRequest(versamento));
      return this.http.post<Array<VersamentiPrVO>>(url, body);
    }

    /**
     * Servizio eliminazione rateo
     */     
    public eliminaRateo(idRateo: number) {
      var url: string = this.config.getBEServer() + '/rest/rateo/cancella/' + idRateo;      
      return this.http.delete(url, {});
    }

    /**
     * Servizio ricerca rateo a partire dall'idRateo
     */     
    public ricercaRateo(idRateo: number) {
      var url: string = this.config.getBEServer() + '/rest/rateo/' + idRateo;      
      return this.http.get<RateoVO>(url, {});
    }

    /**
     * Servizio ricerca lista ratei
     */     
    public ricercaRateoList(ricercaRateoRequest: RicercaRateoRequest) {
      var url: string = this.config.getBEServer() + '/rest/rateo/ricerca-rateo';      
      const body = ricercaRateoRequest;
      return this.http.post<Array<RateoVO>>(url, body);
    }

    /**
     * Servizio inserisci nuovo rateo
     */     
    public inserisciRateo(rateo: RateoVO) {
      var url: string = this.config.getBEServer() + '/rest/rateo/inserisci';      
      const body = rateo;
      return this.http.put<String>(url, body);
    }

    /**
     * Servizio modifica rateo
     */     
    public modificaRateo(rateo: RateoVO) {
      var url: string = this.config.getBEServer() + '/rest/rateo/modifica';      
      const body = rateo;
      return this.http.post<String>(url, body);
    }

    /**
     * Servizio ricerca storico anagrafica POST con oggetto request
    */  
    public ricercaStoricoAnagraficaSoggetti(ricercaStoricoSoggettiRequest: RicercaStoricoSoggettiRequest) {
      var url: string = this.config.getBEServer() + '/rest/home/ricerca-lista-storico-soggetti';
      const body = ricercaStoricoSoggettiRequest;
      return this.http.post<Array<StoricoAnagraficaSoggettoVO>>(url, body);    
    }

    /**
     * Creazione oggetto request per servizio ricerca soggetto incorporato
     */
    public set ricercaSoggettoIncorporatoReq(ricercaSoggettoIncorporatoRequest: RicercaSoggettoIncorporatoRequest) {
      this.ricercaSoggettoIncorporatoRequest = ricercaSoggettoIncorporatoRequest;
    }

    public get ricercaSoggettoIncorporatoReq(): RicercaSoggettoIncorporatoRequest {
      return this.ricercaSoggettoIncorporatoRequest;
    }

    /**
     * Serivizio ricerca soggetto incorporato (fusione) POST on oggetto request
     */
    public ricercaSoggettoIncorporato(ricercaSoggettoIncorporatoRequest: RicercaSoggettoIncorporatoRequest) {
      var url: string = this.config.getBEServer() + '/rest/home/ricerca-soggetto-incorporato';
      const body = ricercaSoggettoIncorporatoRequest;
      console.log("Request ws", ricercaSoggettoIncorporatoRequest);
      return this.http.post<Array<ConsumiPrVO>>(url, body);    
    }

}
