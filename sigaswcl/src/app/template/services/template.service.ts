import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { ConfigService } from "../../core/services/config.service";
import { LoggerService } from '../../core/services/logger.service';
import { Observable } from "rxjs";
import { DatiTemplateVO } from "../../commons/vo/template/dati-template-vo";
import { DatiTemplateRequest } from "../../commons/request/template/dati-template-request";

@Injectable()
export class TemplateService {

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        this.logger.info(TemplateService.name);
    }

    getDatiTemplate(body: DatiTemplateRequest): Observable<DatiTemplateVO> {
        var url: string = this.config.getBEServer() + '/rest/template/getDatiTemplate';
        return this.http.post<DatiTemplateVO>(url, body);
    }

    stampaTemplate(body: DatiTemplateRequest): Observable<Blob> {
        var url: string = this.config.getBEServer() + '/rest/template/stampaTemplate';
        return this.http.post(url, body, { responseType: 'blob' }).map(res => new Blob([res], { type: 'application/pdf' }));
    }

    downloadTemplate(body: DatiTemplateRequest): Observable<Blob> {
        var url: string = this.config.getBEServer() + '/rest/template/downloadTemplate';
        return this.http.post(url, body, { responseType: 'blob' }).map(res => new Blob([res], { type: 'application/pdf' }));
    }

    nomiTemplate(body: DatiTemplateRequest): Observable<DatiTemplateVO> {
        var url: string = this.config.getBEServer() + '/rest/template/nomiTemplate';
        return  this.http.post<DatiTemplateVO>(url, body);
    }
}