import { OnInit, OnDestroy, Component } from "@angular/core";
import { LoggerService } from '../../../../core/services/logger.service';
import { Router } from "@angular/router";
import { InserisciAccreditamentoService } from "../../service/inserisci-accreditamento.service";
import { ServizioVO } from "../../../../core/commons/vo/servizio-VO"; 
import { UserService } from '../../../../core/services/user.service';
import { ProvinceVO } from "../../../../commons/vo/luoghi-vo/province-vo";
import { ComuniVO } from "../../../../commons/vo/luoghi-vo/comuni-vo";
import { NazioniVO } from "../../../../commons/vo/luoghi-vo/nazioni-VO";
import { LuoghiService } from "../../../../shared/service/luoghi-service";
import { MessageRestError } from "../../../../core/commons/commons-core";
import { RoutingAccreditamentoFO } from "../../../../commons/routing";
import { OperatoreVO } from "../../../accreditamento-shared/commons/vo/operatore-vo";
import { ListaServiziService } from "../../../accreditamento-shared/service/lista-servizi.service";
import { MessageAccreditamentoSuccess } from "../../../accreditamento-shared/commons/commons-accreditamento";
import { DestroySubscribers } from "../../../../core/commons/decorator/destroy-subscribers";
import { ExceptionVO } from "../../../../core/commons/vo/exceptionVO";


declare var $: any;

@Component({
    selector: 'app-accreditamento-inserimento-operatore-servizi',
    templateUrl: './inserimento-operatore-servizi.component.html',
    styleUrls: ['./inserimento-operatore-servizi.component.scss']
})
@DestroySubscribers()
export class InserimentoOperatoreServizi implements OnInit {

    operatoreServiziModel: OperatoreVO;
    provinceModel: Array<ProvinceVO>;
    comuniModel: Array<ComuniVO>;
    statiEsteriModel: Array<NazioniVO>;
    serviziModel: Array<ServizioVO>;

    loaderLegaleRappresentante: boolean;
    loaderProvince: boolean;
    loaderComuni: boolean;
    loaderStatiEsteri: boolean;
    loaderServizio: boolean;
    loaderChapta: boolean;

    codiceFiscale: string;
    maxDate = new Date();


    public subscribers: any = {};

    ngOnInit(): void {
        this.loaderChapta = true;
        //TORNO ALL'INZIO
        let ricercaRequest = this.inserisciAccreditamentoService.RicercaDichiarante;
        let confermaDichiarante = this.inserisciAccreditamentoService.confermaDichiarante;
        let confermaLegaleRappresentante = this.inserisciAccreditamentoService.confermaLegaleRappresentante;
//        if (ricercaRequest == null || confermaDichiarante == null || confermaLegaleRappresentante == null)
//            this.router.navigateByUrl(RoutingAccreditamento.RICERCA_DICHIARANTE);

        //SE CONFERMA EFFETTUATA RICARICO DAL SERVICE
        this.operatoreServiziModel = new OperatoreVO("", "", "", null, "", null);

        this.subscribers.profilo = this.userService.profilatura$.subscribe(data => {
            this.codiceFiscale = data.codiceFiscaleUtente
            this.operatoreServiziModel.codiceFiscale = this.codiceFiscale;
            this.operatoreServiziModel.nome = data.nome;
            this.operatoreServiziModel.cognome = data.cognome;
        }, err => {
            this.logger.error(err);
        });

//        this.loadProvince();
//        this.loadStatiEsteri();
//        this.loadSevizio();
        this.loaderComuni = true;
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
    
    public resolved(captchaResponse: string) {
        console.log(`Resolved captcha with response: ${captchaResponse}`);
        this.loaderChapta = false;
      }

//    loadProvince() {
//        this.logger.info("carico le province")
//        this.loaderProvince = false;
//        this.subscribers.province = this.luoghiService.getAllProvince().subscribe(data => {
//            this.provinceModel = data;
//            this.provinceModel.push(new ProvinceVO(-1, "Nessuna Provincia Selezionata",null));
//            this.loaderProvince = true;
//        }, err => {
//            this.logger.error("Errore nel recupero delle province");
//        });
//    }

//    loadComuni(id: number) {
//        this.logger.info("carico i comuni  della provincia con id:" + id);
//        if (id != null && id != -1) {
//            this.loaderComuni = false;
//            this.operatoreServiziModel.cittaEstera = null;
//            this.operatoreServiziModel.idStatoEstero = null;
//            this.subscribers.comuniByProvincia = this.luoghiService.getComuneByIdProvincia(id).subscribe(data => {
//                this.comuniModel = data;
//                let com = this.comuniModel.find(x => x.id == this.operatoreServiziModel.idComune);
//                if (!com)
//                    this.operatoreServiziModel.idComune = null;
//
//                this.loaderComuni = true;
//            }, err => {
//                this.logger.error(err);
//            });
//
//        }
//        else {
//            this.comuniModel = new Array<ComuniVO>();
//            this.operatoreServiziModel.idComune = null;
//        }
//
//    }



//    loadStatiEsteri() {
//        this.logger.info("carico gli stati esteri");
//        this.loaderStatiEsteri = false;
//        this.loaderStatiEsteri = false;
//        this.subscribers.nazioni = this.luoghiService.getAllNazioni().subscribe(data => {
//            this.statiEsteriModel = data;
//            this.loaderStatiEsteri = true;
//        }, err => {
//            this.logger.error(err);
//        });
//    }

//    loadSevizio() {
//        this.logger.info("carico i servizi SIGAS")
//        this.loaderServizio = false;
//        this.subscribers.servizi = this.listaServiziService.listaAllServizi().subscribe(data => {
//            this.loaderServizio = true;
//            this.serviziModel = data;
//            for (let serv of this.serviziModel)
//                if (serv.id == 4)
//                    serv.checked = true;
//        }, err => {
//            this.logger.error(err);
//        });
//
//    }

    cancella() {
        this.operatoreServiziModel = new OperatoreVO(this.operatoreServiziModel.cognome, this.operatoreServiziModel.nome, this.codiceFiscale, "", "", null);
        for (let serv of this.serviziModel) {
            if (serv.id == 4)
                serv.checked = true;
            else
                serv.checked = false;
        }
    }

//    goBack() {
//        this.router.navigateByUrl(RoutingAccreditamentoFO.INSERMENTO_LEGALE_RAPPR);
//    }

    loaderPrincipale: boolean = false;
    showMessageError: boolean = false;
    messageError: string;
    onSubmit() {
//        let serviziSelezionati: Array<number> = new Array<number>();
//        for (let serv of this.serviziModel) {
//            if (serv.checked)
//                serviziSelezionati.push(serv.id);
//        }
//        this.operatoreServiziModel.servizi = serviziSelezionati;
//        this.loaderPrincipale = true;
//        this.subscribers.conferma = this.inserisciAccreditamentoService.confermaInserimentoAccreditamento(this.operatoreServiziModel).subscribe(
//            data => {
////                this.userService.getProfilatura();
//                this.inserisciAccreditamentoService.clean();
//                this.router.navigate([RoutingAccreditamentoFO.INSERIMENTO_DICHIARANTE], { queryParams: { message: MessageAccreditamentoSuccess.inserimento }, skipLocationChange: true });
//            },
//            err => {
//                if (err instanceof ExceptionVO && err.status == '200') {
//                    this.messageError = err.message;
//                }
//                else
//                    this.messageError = MessageRestError.INPUTERROR;
//
//                this.loaderPrincipale = false;
//                this.showMessageError = true;
//            });
        
        this.inserisciAccreditamentoService.confermaOperatoreVO = this.operatoreServiziModel;
        this.router.navigate([RoutingAccreditamentoFO.INSERIMENTO_DICHIARANTE]);
    }
    
    
    constructor(
        private logger: LoggerService,
        private router: Router,
        private luoghiService: LuoghiService,
        private listaServiziService: ListaServiziService,
        private inserisciAccreditamentoService: InserisciAccreditamentoService,
        private userService: UserService
    ) { }

}