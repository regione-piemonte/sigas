import { Component, OnInit, OnDestroy } from "@angular/core";
import { InserimentoPagamentoService } from "../../service/inserisci-pagamento-service";
import { LoggerService } from "../../../core/services/logger.service";
import { Router, ActivatedRoute } from "@angular/router";

import { PaymentFoService } from '../../service/pagamento-fo.service';

@Component({
    selector: 'app-success-pago-pa',
    templateUrl: './success-pago-pa.html',
    styleUrls: ['./success-pago-pa.scss']
})
export class SuccessPagoPaComponent implements OnInit {

    paymentSuccess: boolean;
    showErrorMessage: string = null;

    url_chiamante_esterno: string;
    idPagamento: string;
    digest: string;
    codEsito: string;

    mail: string = '';

    constructor(
                private router: Router,
                private route: ActivatedRoute,
                private inserimentoPagamentoService: InserimentoPagamentoService,
                private foPayService: PaymentFoService,
                private logger: LoggerService) {

        if(this.route.queryParams) {
            //url_chiamante_esterno?idPagamento={identificativoPagamento}&digest={digest}&codEsito=[000|100]
            this.route.queryParams.subscribe(params => {
                this.url_chiamante_esterno = params['url_chiamante_esterno'];
                this.idPagamento = params['idPagamento'];
                this.digest = params['digest'];
                this.codEsito = params['codEsito'];
            });
        }
    }

    showSpinner: boolean = false;
    ngOnInit(): void {
        this.showSpinner = !(this.paymentSuccess = this.codEsito == '000');

        if(!this.paymentSuccess) {
            this.storeCodEsito();
        }
    }

    storeCodEsito() {
        this.foPayService.cartReq.status = "ERROR";
        this.foPayService.cartReq.paymentCode = this.idPagamento;

        this.foPayService.savePaymentCart(() => {
            this.showSpinner = false;
        }, 
        (error) => {
            if(error.errorCode == 'BUSSINESS EXCEPTION')
                this.showErrorMessage = error.message;
            else
                this.showErrorMessage = 'Errore generico durante la registrazione dello stato del carrello.';
        });
    }
        
}