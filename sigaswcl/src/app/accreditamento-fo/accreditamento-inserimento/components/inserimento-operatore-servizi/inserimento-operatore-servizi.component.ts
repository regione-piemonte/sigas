import { OnInit, OnDestroy, Component, AfterViewInit, Renderer2 } from "@angular/core";
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
export class InserimentoOperatoreServizi implements OnInit, AfterViewInit {

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



    cancella() {
        this.operatoreServiziModel = new OperatoreVO(this.operatoreServiziModel.cognome, this.operatoreServiziModel.nome, this.codiceFiscale, "", "", null);
        for (let serv of this.serviziModel) {
            if (serv.id == 4)
                serv.checked = true;
            else
                serv.checked = false;
        }
    }


    loaderPrincipale: boolean = false;
    showMessageError: boolean = false;
    messageError: string;
    onSubmit() {        
        this.inserisciAccreditamentoService.confermaOperatoreVO = this.operatoreServiziModel;
        this.router.navigate([RoutingAccreditamentoFO.INSERIMENTO_DICHIARANTE]);
    }    
    
    constructor(
        private logger: LoggerService,
        private router: Router,
        private luoghiService: LuoghiService,
        private listaServiziService: ListaServiziService,
        private inserisciAccreditamentoService: InserisciAccreditamentoService,
        private userService: UserService,
        private renderer: Renderer2
    ) { }

    ngAfterViewInit(): void {
        const element = this.renderer.selectRootElement('#idDivToContPrin');
        setTimeout(() => element.focus(), 0);
    }

}