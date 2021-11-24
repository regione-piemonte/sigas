import { Component, OnInit, OnDestroy } from "@angular/core";
import { InserimentoPagamentoService } from "../../service/inserisci-pagamento-service";
import { LoggerService } from "../../../core/services/logger.service";

@Component({
    selector: 'app-failure-pago-pa',
    templateUrl: './failure-pago-pa.html',
    styleUrls: ['./failure-pago-pa.scss']
})
export class FailurePagoPaComponent implements OnInit {


    constructor(private logger: LoggerService) {

    }

    ngOnInit(): void {

    }

}