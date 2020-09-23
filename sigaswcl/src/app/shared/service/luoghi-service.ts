import { Injectable } from "@angular/core";
import { ReplaySubject } from "rxjs";
import { Observable } from "rxjs/Observable";
import { HttpClient, HttpParams, HttpHeaders } from "@angular/common/http";
import { Router } from "@angular/router";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from "../../core/services/logger.service";
import { ProvinceVO } from "../../commons/vo/luoghi-vo/province-vo";
import { ComuniVO } from "../../commons/vo/luoghi-vo/comuni-vo";

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
}