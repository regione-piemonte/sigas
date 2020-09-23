import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ConfigService } from '../../core/services/config.service';
import { LoggerService } from '../../core/services/logger.service';
import { Observable } from "rxjs/Observable";
import { TassiVO } from '../../commons/vo/tassi-vo';
import { TipoTassiVO } from '../../commons/vo/tipo-tassi-vo';
import { ConfermaTassoRequest } from "../../commons/request/conferma-tasso-request";

@Injectable({
  providedIn: 'root'
})
export class TassiService {

  private tasso: TassiVO;

  constructor(
    private http: HttpClient,
    private config: ConfigService,
    private logger: LoggerService,
  ) { 
    logger.info("TassiService constructor");
  }
  
  public getAllMock() {
      var url: string = 'assets/mock/tassi.json';
      return  this.http.get<Array<TassiVO>>(url);
  }

  getAllTipoTassi() {
      var url: string = this.config.getBEServer() + '/rest/impostazioni/tipoTassi';
      return this.http.get<Array<TipoTassiVO>>(url);
  }
  
  public getAll() {
      var url: string = this.config.getBEServer() + '/rest/impostazioni/tassi';
      return this.http.get<Array<TassiVO>>(url);
    }
  
  public getTassiRimborso( ) {
    var url: string = this.config.getBEServer() + '/rest/impostazioni/tassiByRimborso';
    return this.http.get<Array<TassiVO>>(url);
  }
  
  public eliminaTasso(idTasso: number){
      var url: string = this.config.getBEServer() + '/rest/impostazioni/eliminaTasso/' + idTasso.toString();
      return this.http.post(url, null);
  }
    
  public updateTasso(idTasso: number, tassoVO: TassiVO) {
      let url: string = this.config.getBEServer() + '/rest/impostazioni/aggiornaTasso';
      const body = new ConfermaTassoRequest(tassoVO);
      return this.http.post<TassiVO>(url, body);
  }
  
  public addTasso(tassoVO: TassiVO) {
      var url: string = this.config.getBEServer() + '/rest/impostazioni/aggiungiTasso';
      const body = new ConfermaTassoRequest(tassoVO);
      return this.http.post<TassiVO>(url, body);
  }

  public tassiByAccertamenti() {
    var url: string = this.config.getBEServer() + '/rest/impostazioni/tassiByAccertamenti';
    return this.http.get<Array<TassiVO>>(url);
  }

}
