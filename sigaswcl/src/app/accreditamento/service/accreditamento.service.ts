import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ConfigService } from '../../core/services/config.service';
import { LoggerService } from '../../core/services/logger.service';
import { AliquotaVO } from '../../commons/vo/aliquota-vo';
import { TipoAliquotaVO } from '../../commons/vo/tipo-aliquota-vo';
import { ConfermaAliquotaRequest } from '../../commons/request/conferma-aliquota-request';
import { UtenteProvvisorioVO } from '../../commons/vo/utente-provvisorio-vo';
import { RicercaAccreditamentoRequest } from '../../commons/request/ricerca-accreditamento-request';
import { ConfermaPraticaAccreditamentoRequest } from '../../commons/request/conferma-pratica-accreditamento-request';
import { AccreditamentoVO } from '../../commons/vo/accreditamento-vo';
import { RicercaPraticheAccreditamentoRequest } from '../../commons/request/ricerca-pratiche-accreditamento-request';


@Injectable({
  providedIn: 'root'
})
export class AccreditamentoService {

  constructor(
    private http: HttpClient,
    private config: ConfigService,
    private logger: LoggerService,
  ) {
    logger.info('AccreditamentoService constructor');
  }
  
///RICERCA ACCREDITAMENTO
  private ricercaAccreditamentoRequest: RicercaAccreditamentoRequest;

  public set ricercaAccreditamento(ricercaAccreditamentoRequest: RicercaAccreditamentoRequest) {
      this.ricercaAccreditamentoRequest = ricercaAccreditamentoRequest;
  }
  
  public get ricercaAccreditamento(): RicercaAccreditamentoRequest {
      return this.ricercaAccreditamentoRequest;
  }
  
  
///RICERCA ACCREDITAMENTO
  private ricercaPraticheRequest: RicercaPraticheAccreditamentoRequest;

  public set ricercaPraticheAcc(ricercaPraticheRequest: RicercaPraticheAccreditamentoRequest) {
      this.ricercaPraticheRequest = ricercaPraticheRequest;
  }
  
  public get ricercaPraticheAcc(): RicercaPraticheAccreditamentoRequest {
      return this.ricercaPraticheRequest;
  }
  
  
  private confermaPraticaAccreditamentoRequest: ConfermaPraticaAccreditamentoRequest;

  public set confermaAccreditamento(confermaPraticaAccreditamentoRequest: ConfermaPraticaAccreditamentoRequest) {
      this.confermaPraticaAccreditamentoRequest = confermaPraticaAccreditamentoRequest;
  }
  
  public get confermaAccreditamento(): ConfermaPraticaAccreditamentoRequest {
      return this.confermaPraticaAccreditamentoRequest;
  }
  
  
  
  ricercaPratiche() {
      var url: string = this.config.getBEServer() + '/rest/accreditamento/ricercaPratiche';
      //const header = new HttpHeaders({ 'Content-Type': 'application/json; charset=utf-8' });
      return this.http.post<Array<UtenteProvvisorioVO>>(url, this.ricercaPraticheRequest);
  }
  
  ricercaPraticaAccreditamento() {
      var url: string = this.config.getBEServer() + '/rest/accreditamento/ricercaPraticaAccreditamento';
      return this.http.post<AccreditamentoVO>(url, this.ricercaAccreditamentoRequest);
  }
  
  confermaPraticaAccreditamento() {
      var url: string = this.config.getBEServer() + '/rest/accreditamento/confermaPraticaAccreditamento';
      return this.http.post<string>(url, this.confermaPraticaAccreditamentoRequest);
  }
  
  
  

}
