import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { ConfigService } from "./config.service";
import { ReplaySubject } from "rxjs";
import { Observable } from "rxjs/Observable";
import { ProfilaturaVO } from '../commons/vo/profilatura-vo';
import { LoggerService } from "./logger.service";
import { Roles } from "../commons/commons-core";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private profilatura = new ReplaySubject<ProfilaturaVO>();
  profilatura$: Observable<ProfilaturaVO> = this.profilatura.asObservable();

  constructor(
    private http: HttpClient,
    private config: ConfigService, 
    private logger: LoggerService
  ) { 
    console.log("user-service constructor");
  }


  getProfilatura() {
    var url: string = this.config.getBEServer() + '/rest/user/getProfilatura';
    return this.http.get<ProfilaturaVO>(url).subscribe(data => {
        this.profilatura.next(data);
    }, (err) => {
        this.logger.error("ERRORE GET PROFILATURA");
    });
  }

  logOut() {
    var url: string = this.config.getBEServer() + '/rest/user/localLogout';
    return this.http.get(url);
  }

  //MOCK PER FE
  getProfilaturaMock() {
      let useCase: Array<String> = new Array<String>();


      //REGIONE PIEMONTE
      useCase.push(
          Roles.HOME
      );

      let flagPrimoAccesso: boolean = false;
      let impostazioneUTF: boolean = true;
      let aliquote: boolean = true;
      let tassiInteresse: boolean = true;

      let codiceFiscaleUtente: string = "BVORCR93E08A182G";
      let profilatura: ProfilaturaVO = new ProfilaturaVO(
          useCase,
          codiceFiscaleUtente,
          'Mario',
          'Rossi',
          flagPrimoAccesso,
          impostazioneUTF,
          aliquote,
          tassiInteresse);
      this.profilatura.next(profilatura);
  }
}
