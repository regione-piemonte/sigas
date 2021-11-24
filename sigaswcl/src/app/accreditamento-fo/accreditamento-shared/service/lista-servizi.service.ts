import { Injectable } from "@angular/core";
import { ReplaySubject } from "rxjs";
import { Observable } from "rxjs/Observable";
import { HttpClient } from "@angular/common/http";
import { ConfigService } from "../../../core/services/config.service";
import { LoggerService } from '../../../core/services/logger.service';
import { ServizioVO } from "../../../core/commons/vo/servizio-VO";

@Injectable()
export class ListaServiziService {

    listaAllServizi() {
        var url: string = this.config.getBEServer() + '/rest/accreditamento/listaAllServizi';
        return this.http.get<Array<ServizioVO>>(url);
    }

    listaServiziAssociatiAdAccreditamento() {
        var url: string = this.config.getBEServer() + '/rest/accreditamento/recuperaServiziAccreditamento';
        return this.http.get<Array<ServizioVO>>(url);
    }

    constructor(private http: HttpClient, private config: ConfigService, private logger: LoggerService) {
        logger.info("InserisciAccreditamentoService constructor");
    }
}