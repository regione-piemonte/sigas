import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ConfigService } from '../../core/services/config.service';
import { LoggerService } from '../../core/services/logger.service';
import { ImportazioneUTFRequest } from '../../commons/request/importazione-utf-request';
import { ImportUTFVO } from '../../commons/vo/import-utf-vo';
import { AnnualitaUTFVO } from 'src/app/commons/vo/annualita-utf-vo';

@Injectable({
  providedIn: 'root'
})
export class ImportazioneUTFService {

  private importazioneUTFRequest: ImportazioneUTFRequest;

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
    this.importazioneUTFRequest;

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
}
