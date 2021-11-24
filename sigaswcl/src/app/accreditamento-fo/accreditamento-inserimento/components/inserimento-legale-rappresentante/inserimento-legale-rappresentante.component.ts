import { OnInit, OnDestroy, Component } from "@angular/core";
import { LoggerService } from '../../../../core/services/logger.service';
import { Router } from "@angular/router";
import { DatePipe } from "@angular/common";
import { InserisciAccreditamentoService } from "../../service/inserisci-accreditamento.service";
import { DichiaranteVO } from "../../../../commons/vo//dichiarante-accreditamento-vo";
import { ProvinceVO } from "../../../../commons/vo/luoghi-vo/province-vo";
import { ComuniVO } from "../../../../commons/vo/luoghi-vo/comuni-vo";
import { NazioniVO } from "../../../../commons/vo/luoghi-vo/nazioni-VO";
import { LuoghiService } from "../../../../shared/service/luoghi-service";
import { ExceptionVO } from "../../../../core/commons/vo/exceptionVO";
import { RoutingAccreditamento } from "../../../../commons/routing";
import { RoutingAccreditamentoFO } from "../../../../commons/routing";
import { LegaleRappresentanteVO } from "../../../accreditamento-shared/commons/vo/legale-rappresentante-vo";
import { DestroySubscribers } from "../../../../core/commons/decorator/destroy-subscribers";

import { MessageRestError } from "../../../../core/commons/commons-core";
import { MessageAccreditamentoSuccess } from "../../../accreditamento-shared/commons/commons-accreditamento";

declare var $: any;

@Component({
    selector: 'app-accreditamento-inserimento-legale-rappresentante',
    templateUrl: './inserimento-legale-rappresentante.component.html',
    styleUrls: ['./inserimento-legale-rappresentante.component.scss']
})
@DestroySubscribers()
export class InserimentoLegaleRappresentante implements OnInit {


    rappresentanteModel: LegaleRappresentanteVO;
    provinceModel: Array<ProvinceVO>;
    comuniModel: Array<ComuniVO>;
    statiEsteriModel: Array<NazioniVO>;

    loaderLegaleRappresentante: boolean;
    loaderProvince: boolean;
    loaderComuni: boolean;
    loaderStatiEsteri: boolean;
    maxDate = new Date();

    showMessageError: boolean;
    messageError: string;
    
    showMessage: boolean;
    message: string;
    tipoMessaggio: string;

    public subscribers: any = {};


    ngOnInit(): void {
        this.loaderLegaleRappresentante = true;
        //TORNO ALL'INZIO
        let ricercaRequest = this.inserisciAccreditamentoService.RicercaDichiarante;
        let confermaDichiarante = this.inserisciAccreditamentoService.confermaDichiarante;
//        if (ricercaRequest == null || confermaDichiarante == null)
//            this.router.navigateByUrl(RoutingAccreditamento.RICERCA_DICHIARANTE);
//        else {
            //SE CONFERMA EFFETTUATA RICARICO DAL SERVICE
//            let confermaLegaleRappresentante = this.inserisciAccreditamentoService.confermaLegaleRappresentante;
//            if (confermaLegaleRappresentante == null) {
//                if (confermaDichiarante.flagCensito != null && confermaDichiarante.flagCensito) {
//                    this.recuperaDaTableIrba(confermaDichiarante);
//                }
//                else {
                    this.rappresentanteModel = new LegaleRappresentanteVO("", "", "", "", "", "", -1, null, null, "");
                    this.loaderLegaleRappresentante = true;
//                }
//            }
//            else {
//                this.rappresentanteModel = confermaLegaleRappresentante;
//                this.loaderLegaleRappresentante = true;
//                if (this.rappresentanteModel.idProvincia != null && this.rappresentanteModel.idProvincia != -1)
//                    this.loadComuni(this.rappresentanteModel.idProvincia);
//            }

            this.loadProvince();
//            this.loadStatiEsteri();
            this.loaderComuni = true;
//        }

    }

    ngAfterViewChecked() {
        if ($('#datetimepicker1').length) {
            $('#datetimepicker1').datetimepicker({
                format: 'DD/MM/YYYY',
                maxDate: this.maxDate,
                disabledDates: [this.maxDate]
            });
        }

    }




//    recuperaDaTableIrba(dichiarante: DichiaranteVO): any {
//        this.subscribers.legaleRappr = this.inserisciAccreditamentoService.recuperaLegaleRappresentanteByCfdichiaranteAndPi(dichiarante.codiceFiscale, dichiarante.partitaIva).subscribe(data => {
//            if (data == null)
//                data = new LegaleRappresentanteVO("", "", "", "", "", "", -1, null, null, "");
//            this.rappresentanteModel = data;
//            this.loaderLegaleRappresentante = true;
//            if (data.idProvincia != -1 && data.idProvincia != null)
//                this.loadComuni(data.idProvincia);
//            else {
//                this.loadProvince();
//                this.loadStatiEsteri();
//            }
//        }, (err) => {
//            //NEL CASO DI ERRORE MOSTRO IL FORM
//            this.rappresentanteModel = new LegaleRappresentanteVO("", "", "", "", "", "", -1, null, null, "");
//            this.loaderLegaleRappresentante = true;
//            this.loadProvince();
//            this.loadStatiEsteri();
//            this.loaderComuni = true;
//
//        });
//    }

    loadProvince() {
        this.logger.info("carico le province")
        this.loaderProvince = false;
        this.subscribers.province = this.luoghiService.getAllProvince().subscribe(data => {
            this.loaderProvince = true;
            this.provinceModel = data;
            this.provinceModel.push(new ProvinceVO(-1, "Nessuna Provincia Selezionata",null));
        }, err => {
            this.logger.error("Errore nel recupero delle province");
        });
    }

    loadComuni(id: number) {
        this.logger.info("carico i comuni  della provincia con id:" + id);
        if (id != null && id != -1) {
            this.loaderComuni = false;
            this.rappresentanteModel.idStatoEstero = null;
            this.subscribers.comuniByProvince = this.luoghiService.getComuneByIdProvincia(id).subscribe(data => {
                this.comuniModel = data;
                let com = this.comuniModel.find(x => x.id == this.rappresentanteModel.idComune);
                if (!com)
                    this.rappresentanteModel.idComune = null;

                this.loaderComuni = true;
            }, err => {
                this.logger.error(err);
            });

        }
        else {
            this.comuniModel = new Array<ComuniVO>();
            this.rappresentanteModel.idComune = null;
        }

    }

    loadStatiEsteri() {
        this.logger.info("carico gli stati esteri")
        this.loaderStatiEsteri = false;
        this.loaderStatiEsteri = false;
        this.subscribers.nazioni = this.luoghiService.getAllNazioni().subscribe(data => {
            this.statiEsteriModel = data;
            this.loaderStatiEsteri = true;
        }, err => {
            this.logger.error(err);
        });
    }

    cancella() {
        this.rappresentanteModel = new LegaleRappresentanteVO("", "", "", "", "", null, -1, null, null, "")
    }

    goBack() {
        this.router.navigateByUrl(RoutingAccreditamentoFO.INSERIMENTO_DICHIARANTE);
    }

    onSubmit() {
        this.loaderLegaleRappresentante = false;
        this.subscribers.verifica = this.inserisciAccreditamentoService.verificaEsistenzaLegaleRappresentante(this.rappresentanteModel.codiceFiscale).subscribe(
            data => {
//                this.loaderLegaleRappresentante = true;
                this.inserisciAccreditamentoService.confermaLegaleRappresentante = this.rappresentanteModel;
//                this.router.navigateByUrl(RoutingAccreditamentoFO.INSERIMENTO_OPERATRE_SERVIZI);
                this.subscribers.conferma = this.inserisciAccreditamentoService.confermaInserimentoAccreditamento().subscribe(
                        data => {
//                            this.userService.getProfilatura();
                            this.loaderLegaleRappresentante = true;
                            this.inserisciAccreditamentoService.clean();
                            this.router.navigate([RoutingAccreditamentoFO.ACCREDITAMENTO_INSERIMENTO_SUCCESS], { queryParams: { message: MessageAccreditamentoSuccess.inserimento }, skipLocationChange: true });
                        },
                        err => {
                            if (err instanceof ExceptionVO && err.status == '200') {
                                    this.message = err.message;
                                    this.showMessage = true;
                                    this.tipoMessaggio = err.errorCode;
                                    
                               
                            }
                            else
                                this.messageError = MessageRestError.INPUTERROR;

                            this.loaderLegaleRappresentante = true;
                            this.showMessageError = true;
                        });
            },
            err => {
                if (err instanceof ExceptionVO && err.status == '200') {
                    this.loaderLegaleRappresentante = true;
                    this.timerShowMessage();
                    this.messageError = err.message;
                    this.showMessageError = true;
                }
            }
        );
    }
 
    timerShowMessage() {
        let intervalId: number = 0;
        let seconds: number = 8;
        intervalId = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {
                this.showMessageError = false;
                clearInterval(intervalId);
            }
        }, 1000);
    }

    constructor(
        private logger: LoggerService,
        private inserisciAccreditamentoService: InserisciAccreditamentoService,
        private luoghiService: LuoghiService,
        private router: Router
    ) { }
}
