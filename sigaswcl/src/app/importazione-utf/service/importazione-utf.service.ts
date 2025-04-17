import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Subject } from 'rxjs';
import { ConfigService } from '../../core/services/config.service';
import { LoggerService } from '../../core/services/logger.service';
import { ImportazioneUTFRequest } from '../../commons/request/importazione-utf-request';
import { ImportUTFVO } from '../../commons/vo/import-utf-vo';
import { AnnualitaUTFVO } from '../../commons/vo/annualita-utf-vo';
import { ConsumiPrVO } from '../../commons/vo/consumi-pr-vo';
import { UTFConfermaSoggettoDichiarazioneRequest } from '../../commons/request/utf-conferma-soggetto-dichiarazione-request';
import { ReportUTFSoggettiMacroVO } from '../../commons/vo/report-utf-soggetti-macro-vo';
import { AliquotaVO } from '../../commons/vo/aliquota-vo';
import { UTFConfermaDichiarazioniRequest } from '../../commons/request/utf-conferma-dichiarazioni-request';



@Injectable({
  providedIn: 'root'
})
export class ImportazioneUTFService {

  private importazioneUTFRequest: ImportazioneUTFRequest;

  private compareImportUTFVariazioneIdImportSelezionatoSubject = new Subject<Boolean>();
  public compareImportUTFVariazioneIdImportSelezionatoObservable = this.compareImportUTFVariazioneIdImportSelezionatoSubject
                                                                       .asObservable();
  emitCompareImportUTFVariazioneIdImportSelezionato(refresh: Boolean) {
    this.compareImportUTFVariazioneIdImportSelezionatoSubject.next(refresh);
  }

  private importUTFAnnualitaSelezionataSubject = new Subject<Number>();
  public importUTFAnnualitaSelezionataSubjectObservable = this.importUTFAnnualitaSelezionataSubject.asObservable();
  emitImportUTFAnnualitaSelezionata(annualita: Number) {
    this.importUTFAnnualitaSelezionataSubject.next(annualita);
  }

  private importUTFAnnualitaSelezionataChangedSubject = new Subject<boolean>();
  public importUTFAnnualitaSelezionataChangedSubjectObservable = this.importUTFAnnualitaSelezionataChangedSubject.asObservable();
  emitImportUTFAnnualitaSelezionataChanged(changed: boolean) {
    this.importUTFAnnualitaSelezionataChangedSubject.next(changed);
  }

  private importUTFUnicoImportPerAnnualitaSubject = new Subject<number>();
  public importUTFUnicoImportPerAnnualitaSubjectObservable = this.importUTFUnicoImportPerAnnualitaSubject.asObservable();
  emitImportUTFUnicoImportPerAnnualita(idImport: number) {
    this.importUTFUnicoImportPerAnnualitaSubject.next(idImport);
  }

  constructor(
    private http: HttpClient,
    private config: ConfigService,
    private logger: LoggerService,
  ) { }

  public get importazioneUTFReq(): ImportazioneUTFRequest {
    return this.importazioneUTFRequest;
  }
  public set importazioneUTFReq( importazioneUTFRequest: ImportazioneUTFRequest) {
      this.importazioneUTFRequest  = importazioneUTFRequest;
  }

  // SERVIZI
  public ricercaAnnualita() {
    var url: string = this.config.getBEServer() + '/rest/UTF/ricercaAnnualita';
    return this.http.get<Array<AnnualitaUTFVO>>(url);
  }

  public importUTF() {
    var url: string = this.config.getBEServer() + '/rest/UTF/importazioneUTF';
    
    const fd = new FormData();
    fd.append('utfFile', this.importazioneUTFRequest.utfFile);
    fd.append('annualita', this.importazioneUTFRequest.annualita);

    return this.http.post<ImportUTFVO>(url, fd);
  }

  public popolamentoConsumi() {
    var url: string = this.config.getBEServer() + '/rest/UTF/popolamentoConsumi';

    let popolamentoUTFRequest = {
      "annualita" : this.importazioneUTFRequest.annualita,
      "utfFile" : File
    };
    return this.http.post<ImportUTFVO>(url, popolamentoUTFRequest);
  }

  public ricercaReportUTF(idImport: number, annualita: Number) {
    var url: string = this.config.getBEServer() + '/rest/UTF/utf-report/' + idImport + '/' + annualita;
    return this.http.get<Array<ConsumiPrVO>>(url);
  }

  public ricercaReportUTFDettaglioSoggetti(idImport: number) {
    var url: string = this.config.getBEServer() + '/rest/UTF/utf-report/' + idImport + '/dettaglio-soggetti';
    return this.http.get<Array<ConsumiPrVO>>(url);
  }

  public ricercaReportUTFDettaglioSoggettiByIdImportIdAnag(idImport: Number, idAnag: Number) {
    var url: string = this.config.getBEServer() + '/rest/UTF/utf-report/' + idImport + '/dettaglio-soggetti/' + idAnag;
    return this.http.get<Array<ConsumiPrVO>>(url);
  }

  public ricercaReportUTFSoggettiMacroByIdReport(idImportA: number, idImportB: number, annualita: Number) {
    var url: string = this.config.getBEServer() + '/rest/UTF/utf-report/dettaglio-soggetti-macro';
    let utfSoggettiMacroReportByIdReportRequest = {
      "idImportA" : idImportA,
      "idImportB" : idImportB,
      "annualita" : annualita
    };
    return this.http.post<ReportUTFSoggettiMacroVO[]>(url, utfSoggettiMacroReportByIdReportRequest);
  }

  public elencoReportUTFByAnno(anno: String) {
    var url: string = this.config.getBEServer() + '/rest/UTF/elenco/' + anno;
    return this.http.get<Array<ImportUTFVO>>(url);
  } 

  public confermaSoggettoDichiarazioniUTF(annualita: string, idImport: number, idAnag: number, azioneRichiesta: string) {
    let utfConfermaSoggettoDichiarazioneRequest : UTFConfermaSoggettoDichiarazioneRequest;
    utfConfermaSoggettoDichiarazioneRequest = new UTFConfermaSoggettoDichiarazioneRequest();
    utfConfermaSoggettoDichiarazioneRequest.annualita = annualita;
    utfConfermaSoggettoDichiarazioneRequest.idImport = idImport;
    utfConfermaSoggettoDichiarazioneRequest.idAnag = idAnag;
    utfConfermaSoggettoDichiarazioneRequest.azioneRichiesta = azioneRichiesta;
    var url: string = this.config.getBEServer() + '/rest/UTF/conferma-soggetto-dichiarazioni';
    return this.http.post<void>(url, utfConfermaSoggettoDichiarazioneRequest);
  }
  
  public confermaDichiarazioniUTF(annualita: string, idImportA: number, idImportB: number, azioneRichiesta: string) {
    let utfConfermaDichiarazioniRequest : UTFConfermaDichiarazioniRequest;
    utfConfermaDichiarazioniRequest = new UTFConfermaDichiarazioniRequest();
    utfConfermaDichiarazioniRequest.annualita = annualita;
    utfConfermaDichiarazioniRequest.idImportA = idImportA;
    utfConfermaDichiarazioniRequest.idImportB = idImportB;    
    utfConfermaDichiarazioniRequest.azioneRichiesta = azioneRichiesta;
    var url: string = this.config.getBEServer() + '/rest/UTF/conferma-dichiarazioni';
    return this.http.post<void>(url, utfConfermaDichiarazioniRequest);
  }  

  public getAllAliquote(annualita: Number) {
    let url: string = this.config.getBEServer() + '/rest/home/getAllAliquote';    
    let params = new HttpParams().set('annoDichiarazione', annualita.toString() == '' ? '9999' : annualita.toString());
      return this.http.get<Array<AliquotaVO>>(url, { params: params});
  }

}
