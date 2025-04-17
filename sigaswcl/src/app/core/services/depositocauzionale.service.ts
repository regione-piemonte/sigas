
import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { ConfigService } from "./config.service";
import { LoggerService } from "./logger.service";
import { RigeneraBollettinoPagamentoRequestVO } from "../commons/vo/RigeneraBollettinoPagamentoRequestVO";

@Injectable({
    providedIn: 'root'
})
export class DepositoCauzionaleService {

    constructor(
        private http: HttpClient,
        private config: ConfigService, 
        private logger: LoggerService
    ){

    }

    rigeneraBollettinoPagamento(idPagamento : String) {
        var url: string = this.config.getBEServer() + '/rest/deposito-causionale/bollettino-pagamento/rigenera';
        
        const rigeneraBollettinoPagamentoRequestVO = new RigeneraBollettinoPagamentoRequestVO(0,0,idPagamento);
        return this.http.post<String>(url, rigeneraBollettinoPagamentoRequestVO);
    }

}