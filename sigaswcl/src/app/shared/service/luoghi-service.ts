import { Injectable } from "@angular/core";
import { ReplaySubject } from "rxjs";
import { Observable } from "rxjs/Observable";
import { HttpClient, HttpParams, HttpHeaders } from "@angular/common/http";
import { Router } from "@angular/router";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger.service";
import { ProvinceVO } from "../../commons/vo/luoghi-vo/province-vo";
import { ComuniVO } from "../../commons/vo/luoghi-vo/comuni-vo";
import { NazioniVO } from "../../commons/vo/luoghi-vo/nazioni-VO";
import { RegioniVO } from "../../commons/vo/luoghi-vo/regioni-VO";

@Injectable()
export class LuoghiService {


    getAllProvince() {
        var url: string = this.config.getBEServer() + '/rest/luoghi/province';
        return this.http.get<Array<ProvinceVO>>(url);
    }

    getComuneByIdProvincia(id: number) {
        var url: string = this.config.getBEServer() + '/rest/luoghi/comuniById';
        let params = new HttpParams().set('idProvincia', id.toString());
        return this.http.get<Array<ComuniVO>>(url, { params: params });
    }

    public provinciaBySigla(siglaProvincia:string ) {
        var url: string = this.config.getBEServer() + '/rest/luoghi/provinciaBySigla';
        let params = new HttpParams().set('siglaProvincia', siglaProvincia);
        return this.http.get<ProvinceVO>(url, { params: params });
    }
    
    constructor(private http: HttpClient, private config: ConfigService, private router: Router, private logger: LoggerService) {
        console.log("utility-service constructor");
    }
    
    getAllNazioni() {
        var url: string = this.config.getBEServer() + '/rest/luoghi/nazioni';
        return this.http.get<Array<NazioniVO>>(url);
    }
    
    getAllRegioni(): Observable<Array<RegioniVO>> {
        var url: string = this.config.getBEServer() + '/rest/luoghi/regioni';
        //const header = new HttpHeaders({ 'Content-Type': 'application/json; charset=utf-8' });
        return this.http.get<Array<RegioniVO>>(url, /*{ headers: header }*/);
    }
    
    ricercaIndirizzo(indirizzo: string, id: number): Observable<any> {
        var url: string = this.config.getBEServer() + '/rest/luoghi/ricercaIndirizzo';
        let params = new HttpParams().set("indirizzo", indirizzo).set("id", id.toString());
        return this.http.get(url, { params: params });
    }
    
    
    getProvinceByIdRegione(id: number) {
        var url: string = this.config.getBEServer() + '/rest/luoghi/provinceById';
        let params = new HttpParams().set('idRegione', id.toString());
        return this.http.get<Array<ProvinceVO>>(url, { params: params });
    }
}