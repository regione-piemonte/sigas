import { Component, OnInit, OnDestroy, ViewChild, Renderer2, AfterViewInit } from "@angular/core";
import { LoggerService } from '../../../../core/services/logger.service';
import { Router } from "@angular/router";
import { ExceptionVO } from "../../../../core/commons/vo/exceptionVO";
import { DestroySubscribers } from "../../../../core/commons/decorator/destroy-subscribers";
import { UserService } from '../../../../core/services/user.service';
import { DichiaranteVO } from "../../../../commons/vo/dichiarante-accreditamento-vo";
import { SigasDialogComponent } from "../../../../shared/component/sigas-dialog/sigas-dialog.component";
import { RegioniVO } from "../../../../commons/vo/luoghi-vo/regioni-VO";
import { ProvinceVO } from "../../../../commons/vo/luoghi-vo/province-vo";
import { ComuniVO } from "../../../../commons/vo/luoghi-vo/comuni-vo";
import { NazioniVO } from "../../../../commons/vo/luoghi-vo/nazioni-VO";
import { LuoghiService } from "../../../../shared/service/luoghi-service";
import { MessageRestError, GinevraPiemonte } from "../../../../core/commons/commons-core";
import { RoutingAccreditamento } from "../../../../commons/routing";
import { MessageAccreditamento } from "../../../accreditamento-shared/commons/commons-accreditamento";
//import { RevocaAccreditamentoService } from "../../../../accreditamento-revoca/service/revoca-accreditamento.service";
import { AccreditamentoVO } from '../../../../commons/vo/accreditamento-vo';
import { OperatoreVO } from '../../../../commons/vo/operatore-vo';
import { LegaleRappresentanteVO } from '../../../../commons/vo/legale-rappresentante-vo';
import { UtenteProvvisorioVO } from '../../../../commons/vo/utente-provvisorio-vo';
import { RoutingAccreditamentoFO } from '../../../../commons/routing';
import { AnnullaAccreditamentoRequest } from '../../../../commons/request/annulla-accreditamento-request';
import { InserisciAccreditamentoService } from "../../service/inserisci-accreditamento.service";

@Component({
    selector: 'app-accreditamento-visualizza-pratica',
    templateUrl: './visualizza-pratica-accreditamento.component.html',
    styleUrls: ['./visualizza-pratica-accreditamento.component.scss']
})
@DestroySubscribers()
export class VisualizzaPraticaAccreditamentoComponent implements OnInit, OnDestroy, AfterViewInit {

    modificaDichiaranteModel: DichiaranteVO;
    accreditamento: AccreditamentoVO;
    private loaderDT: boolean;
    messageError: string;
    showMessageError: boolean;
    messageSuccess: string;
    showSuccess: boolean;
    public subscribers: any = {};

    ngOnInit(): void {
        this.logger.info("init Accreditamento Component");
         
        this.accreditamento = new AccreditamentoVO(new DichiaranteVO(null,""),new LegaleRappresentanteVO("","","","","","",0,0,0,""),new OperatoreVO("","","","","","",0,0,0,"",null),new UtenteProvvisorioVO(0,"",0,0,0,"",0,"",null,"",null,"",null,null,null))
        this.loaderDT = true;
        this.subscribers.accreditamento = this.inserisciAccreditamentoService.ricercaPraticaAccreditamento().subscribe(data => {
            this.accreditamento = data;            
            setTimeout(() => {
                this.loaderDT = false;
              });
        }, err => {
            this.logger.error(err);        
        });
    }   

    cancella() {
        
    }

    onSubmit() {

    }

    goBack(){
        this.router.navigate([RoutingAccreditamentoFO.ELENCO_PRATICHE]);
    }    

    ngOnDestroy(): void {

    }

    constructor(
        private logger: LoggerService,
        private inserisciAccreditamentoService: InserisciAccreditamentoService,
        private luoghiService: LuoghiService,
        private userService: UserService,
        private router: Router,
        private renderer: Renderer2
    ) { }

    ngAfterViewInit(): void {
        const element = this.renderer.selectRootElement('#idDivToContPrin');
        setTimeout(() => element.focus(), 0);
    }
}