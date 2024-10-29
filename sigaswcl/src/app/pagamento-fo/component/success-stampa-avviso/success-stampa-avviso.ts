import { Component, OnInit, OnDestroy } from "@angular/core";
import { InserimentoPagamentoService } from "../../service/inserisci-pagamento-service";
import { LoggerService } from "../../../core/services/logger.service";
import { saveAs } from "file-saver";
import { ActivatedRoute, Router } from "@angular/router";

@Component({
    selector: 'app-success-stampa-avviso',
    templateUrl: './success-stampa-avviso.html',
    styleUrls: ['./success-stampa-avviso.scss']
})
export class SuccessStampaAvvisoComponent implements OnInit {

    loaderSuccess: boolean;

    id: string;

    constructor(
        private inserimentoPagamentoService: InserimentoPagamentoService,
        private logger: LoggerService,
        private activatedRoute: ActivatedRoute) {

    }

    ngOnInit(): void {
        this.loaderSuccess = true;
        this.activatedRoute.params.subscribe(params => {
            this.id = params['id'];
        });
    }
    
    stampaAvviso(){
        this.loaderSuccess = false;
        /*
        this.inserimentoPagamentoService.scaricaPdf(this.id).subscribe(data => {
            this.loaderSuccess = true;
            saveAs(data, "export_avviso_"+this.id+".pdf");
        }, err => {
            this.logger.error(err);
        });
        */
    }

}