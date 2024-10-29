import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ConfigService } from '../../core/services/config.service';
import { LoggerService } from '../../core/services/logger.service';
import { AliquotaVO } from '../../commons/vo/aliquota-vo';
import { TipoAliquotaVO } from '../../commons/vo/tipo-aliquota-vo';
import { ConfermaAliquotaRequest } from '../../commons/request/conferma-aliquota-request';

@Injectable({
  providedIn: 'root'
})
export class AliquoteService {

  private tipoAl: string;
  private idAl: number;
  private descrizioneAl: string;

  constructor(
    private http: HttpClient,
    private config: ConfigService,
    private logger: LoggerService,
  ) {
    logger.info('AliquoteService constructor');
  }

  public get tipoAliquota(): string {
    return this.tipoAl;
  }

  public set tipoAliquota(anno: string) {
    this.tipoAl = anno;
  }

  public get idAliquota(): number {
    return this.idAl;
  }

  public set idAliquota(id: number) {
    this.idAl = id;
  }

  public get descrizioneAliquota(): string {
    return this.descrizioneAl;
  }

  public set descrizioneAliquota(descrizione: string) {
    this.descrizioneAl = descrizione;
  }

  public ricercaAliquote() {
    var url: string = this.config.getBEServer() + '/rest/impostazioni/aliquote';
    return this.http.get<Array<AliquotaVO>>(url);
  }
  
  public ricercaPerAnno() {
      var url: string = this.config.getBEServer() + '/rest/home/ricercaAnnualita';
      return this.http.get<Array<string>>(url);
    }

  public ricercaTipiAliquoteByTipo() {
    var url: string = this.config.getBEServer() + '/rest/impostazioni/tipiAliquoteByTipo';
    let params = new HttpParams().set('tipo', this.tipoAl);
    return this.http.get<Array<TipoAliquotaVO>>(url, { params: params });
  }

  public ricercaTipiAliquoteByDescrizione() {
    var url: string = this.config.getBEServer() + '/rest/impostazioni/tipiAliquoteByDescrizione';
    let params = new HttpParams().set('id', this.idAl.toString())
    .set('descrizione', this.descrizioneAl);
    return this.http.get<TipoAliquotaVO>(url, { params: params });
  }

  public confermaInserimentoAliquota(aliquotaInsert: AliquotaVO) {
    var url: string = this.config.getBEServer() + '/rest/impostazioni/insertAliquota';

    const body = new ConfermaAliquotaRequest(aliquotaInsert);
    return this.http.post<AliquotaVO>(url, body);
  }

  public confermaModificaAliquota(aliquotaUpdate: AliquotaVO) {
    var url: string = this.config.getBEServer() + '/rest/impostazioni/updateAliquota';

    const body = new ConfermaAliquotaRequest(aliquotaUpdate);
    return this.http.post<AliquotaVO>(url, body);
  }

  public eliminaAliquota(id: number) {
    var url: string = this.config.getBEServer() + '/rest/impostazioni/eliminaAliquota/' + id.toString();
    return this.http.post(url, null);
}

}
