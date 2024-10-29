import {AfterViewInit, Component, OnDestroy, OnInit, Renderer2} from '@angular/core';
import {LoggerService} from '../../../../core/services/logger.service';
import {ModificaAccreditamentoService} from '../../service/modifica-accreditamento.service';
import {Router} from '@angular/router';
import {DestroySubscribers} from '../../../../core/commons/decorator/destroy-subscribers';
import {UserService} from '../../../../core/services/user.service';
import {DichiaranteVO} from '../../../../commons/vo/dichiarante-accreditamento-vo';
import {LuoghiService} from '../../../../shared/service/luoghi-service';
import {RoutingAccreditamentoFO} from '../../../../commons/routing';
import {AccreditamentoVO} from '../../../../commons/vo/accreditamento-vo';
import {OperatoreVO} from '../../../../commons/vo/operatore-vo';
import {LegaleRappresentanteVO} from '../../../../commons/vo/legale-rappresentante-vo';
import {UtenteProvvisorioVO} from '../../../../commons/vo/utente-provvisorio-vo';
import {AnnullaAccreditamentoRequest} from '../../../../commons/request/annulla-accreditamento-request';

@Component({
    selector: 'app-accreditamento-modifica-pratica',
    templateUrl: './modifica-pratica-accreditamento.component.html',
    styleUrls: ['./modifica-pratica-accreditamento.component.scss']
})
@DestroySubscribers()
export class ModificaPraticaAccreditamentoComponent implements OnInit, OnDestroy, AfterViewInit {

    modificaDichiaranteModel: DichiaranteVO;
    accreditamento: AccreditamentoVO;
    messageError: string;
    showMessageError: boolean;
    messageSuccess: string;
    showSuccess: boolean;
    public subscribers: any = {};
    private loaderDT: boolean;

    constructor(
        private logger: LoggerService,
        private modificaAccreditamentoService: ModificaAccreditamentoService,
        private luoghiService: LuoghiService,
        private userService: UserService,
        private router: Router,
        private renderer: Renderer2
    ) {
    }
    ngAfterViewInit(): void {
        const element = this.renderer.selectRootElement('#idDivToContPrin');
        setTimeout(() => element.focus(), 0);
    }

    ngOnInit(): void {
        this.logger.info('init Accreditamento Component');
        this.accreditamento = new AccreditamentoVO(new DichiaranteVO(null, ''), new LegaleRappresentanteVO('', '', '', '', '', '', 0, 0, 0, ''), new OperatoreVO('', '', '', '', '', '', 0, 0, 0, '', null), new UtenteProvvisorioVO(0, '', 0, 0, 0, '', 0, '', null, '', null, '', null, null, null));
        this.loaderDT = true;
        this.subscribers.accreditamento = this.modificaAccreditamentoService.ricercaPraticaAccreditamento().subscribe(data => {
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

    salvaPratica() {
        this.logger.info('init Accreditamento Component');
        this.loaderDT = true;
        this.modificaAccreditamentoService.confermaDichiarante = this.accreditamento.dichiarante;
        this.modificaAccreditamentoService.confermaLegaleRappresentante = this.accreditamento.legaleRappresentante;
        this.modificaAccreditamentoService.confermaOperatore = this.accreditamento.operatore;
        this.subscribers.confermaPraticaAccreditamento = this.modificaAccreditamentoService.confermaModificaAccreditamento(this.accreditamento.utenteProvvisorio.idUtenteProvv)
            .subscribe(data => {
                this.loaderDT = false;
                this.messageSuccess = 'Modifica pratica accreditamento avvenuta con successo';
                this.showSuccess = true;
            }, err => {
                this.messageError = 'Si è verificato un errore durante il salvataggio della pratica';
                this.showMessageError = true;
                this.logger.error(err);
            });
    }

    goBack() {
        this.router.navigate([RoutingAccreditamentoFO.MODIFICA_ACCREDITAMENTO_ELENCO]);
    }

    annullaPratica() {
        this.loaderDT = true;
        this.modificaAccreditamentoService.confermaAnnulamento = new AnnullaAccreditamentoRequest(this.accreditamento.utenteProvvisorio.idUtenteProvv, 'ANNULLATA', '');
        this.modificaAccreditamentoService.cambiaStatoNoteAccreditamento()
            .subscribe(data => {
                this.loaderDT = false;
                this.accreditamento.utenteProvvisorio.stato = 'ANNULLATA';
                this.messageSuccess = 'Pratica annullata con successo';
                this.showSuccess = true;
            }, err => {
                this.messageError = 'Si è verificato un errore durante il salvataggio della pratica';
                this.showMessageError = true;
                this.logger.error(err);
            });
    }

    ngOnDestroy(): void {
    }
}
