import { Component, OnInit, ViewChild, ElementRef, AfterViewInit, Renderer2 } from '@angular/core';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { AccreditamentoService } from '../../service/accreditamento.service';
import { LoggerService } from '../../../core/services/logger.service';
import { DataTableIt } from '../../../commons/class/commons-data-table';
import { DataTableDirective } from 'angular-datatables';
import { AliquotaVO } from '../../../commons/vo/aliquota-vo';
import { TipoAliquotaVO } from '../../../commons/vo/tipo-aliquota-vo';
import { Subject } from 'rxjs';
import { ConfermaAliquotaRequest } from '../../../commons/request/conferma-aliquota-request';
import { SigasDialogComponent } from '../../../shared/component/sigas-dialog/sigas-dialog.component';
import { TipoConsumoVO } from '../../../commons/vo/tipo-consumo-vo';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';
import * as moment from 'moment';
import { Router } from '@angular/router';
import { AccreditamentoVO } from '../../../commons/vo/accreditamento-vo';
import { OperatoreVO } from '../../../commons/vo/operatore-vo';
import { LegaleRappresentanteVO } from '../../../commons/vo/legale-rappresentante-vo';
import { DichiaranteVO } from '../../../commons/vo/dichiarante-vo';
import { UtenteProvvisorioVO } from '../../../commons/vo/utente-provvisorio-vo';
import { RoutingAccreditamento } from '../../../commons/routing';
import { ConfermaPraticaAccreditamentoRequest } from '../../../commons/request/conferma-pratica-accreditamento-request'

declare var jquery: any;
declare var $: any;

@Component({
  selector: 'app-accreditamento',
  templateUrl: './accreditamento.component.html',
  styleUrls: ['./accreditamento.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})

@DestroySubscribers()
export class AccreditamentoComponent implements OnInit, AfterViewInit {


    accreditamento: AccreditamentoVO;
    public subscribers: any = {};
    
    private loaderDT: boolean;
    private dtTrigger: Subject<any> = new Subject();
    
    private dtOptions: any;

    private showMessageError: boolean;
    private showSuccess: boolean;
    private messageSuccess: string;
    private messageError: string;
    statoSelezionato: string;

    confermaPraticaAccreditamentoRequest: ConfermaPraticaAccreditamentoRequest;

  constructor(
        private logger: LoggerService,
        private accreditamentoService: AccreditamentoService,
        private router: Router,
        private renderer: Renderer2
    ) { }

  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);
  }

  @ViewChild(SigasDialogComponent) sigasDialog: SigasDialogComponent;
  @ViewChild(DataTableDirective) dtElement: DataTableDirective;

  ngOnInit(): void { 
      this.logger.info("init Accreditamento Component");
    
      this.accreditamento = new AccreditamentoVO(new DichiaranteVO(null,""),new LegaleRappresentanteVO("","","","","","",0,0,0,""),new OperatoreVO("","","","","","",0,0,0,"",null),new UtenteProvvisorioVO(0,"",0,0,0,"",0,"",null,"",null,"",null,null,null));
      this.clearMsg();
      this.loaderDT = true;
      this.subscribers.accreditamento = this.accreditamentoService.ricercaPraticaAccreditamento().subscribe(data => {
          this.accreditamento = data;
//          console.log(this.accreditamento);
          this.statoSelezionato = this.accreditamento.utenteProvvisorio.stato;
          setTimeout(() => {
              this.loaderDT = false;
            },1000);
      }, err => {
          this.logger.error(err);        
      });
      
    }

    salvaPratica() {
        
        this.logger.info("init Accreditamento Component");
        
        this.loaderDT = true;
        this.confermaPraticaAccreditamentoRequest = new ConfermaPraticaAccreditamentoRequest(this.accreditamento.dichiarante, this.accreditamento.operatore, this.accreditamento.legaleRappresentante, this.accreditamento.utenteProvvisorio.idUtenteProvv, this.statoSelezionato, this.accreditamento.utenteProvvisorio.note);
        this.accreditamentoService.confermaAccreditamento= this.confermaPraticaAccreditamentoRequest;
        this.subscribers.confermaPraticaAccreditamento = this.accreditamentoService.confermaPraticaAccreditamento().subscribe(data => {
//            this.messageSuccess = "Pratica Salvata correttamente";
//            this.showSuccess = true;
            this.loaderDT = false;
//            this.router.navigate([RoutingAccreditamento.ACCREDITAMENTO_INSERIMENTO_SUCCESS], { queryParams: { message: "Inserimento accreditamento avvenuto con successo" }, skipLocationChange: true });
            this.accreditamento.utenteProvvisorio.stato = this.statoSelezionato;
            this.messageSuccess = "Modifica pratica accreditamento avvenuta con successo";
            this.showSuccess = true;
        }, err => {
            this.messageError = "Si è verificato un errore durante il salvataggio della pratica";
            this.showMessageError = true;
            this.logger.error(err);        
        });
        

    }

    clearMsg(){
        this.messageSuccess = "";
        this.showSuccess = false;
        this.showMessageError = false;
        this.messageError = "";
    }
    
    goBack(){
        this.router.navigate([RoutingAccreditamento.ACCREDITAMENTO_ELENCO]);
    }
 
}
