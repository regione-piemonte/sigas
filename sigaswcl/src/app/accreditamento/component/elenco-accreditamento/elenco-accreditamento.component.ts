import { Component, OnInit, ViewChild, ElementRef, Renderer2, AfterViewInit } from '@angular/core';
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
import { UtenteProvvisorioVO } from '../../../commons/vo/utente-provvisorio-vo';
import { RoutingAccreditamento } from '../../../commons/routing';
import { RicercaAccreditamentoRequest } from '../../../commons/request/ricerca-accreditamento-request';
import { RicercaPraticheAccreditamentoRequest } from '../../../commons/request/ricerca-pratiche-accreditamento-request';


declare var jquery: any;
declare var $: any;

@Component({
  selector: 'app-elenco-accreditamento',
  templateUrl: './elenco-accreditamento.component.html',
  styleUrls: ['./elenco-accreditamento.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})

@DestroySubscribers()
export class ElencoAccreditamentoComponent implements OnInit, AfterViewInit {


    elencoPratiche: Array<UtenteProvvisorioVO>
    public subscribers: any = {};
    
    private loaderDT: boolean;
    private dtTrigger: Subject<any> = new Subject();
    
    private dtOptions: any;
    
    ricercaModel: RicercaAccreditamentoRequest;
    
    statoRicerca:string;

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
      this.logger.info("init Ricerca Pratiche Component");
      
      this.dtOptions = {
              searching: true,
              pagingType: 'full_numbers',
              info: false,
              language: DataTableIt.language,
              ordering: true,
              columnDefs: [
                { className: 'dt-center'},
                { orderable: false, targets: 0 }
                
              ]
      
            };
      this.statoRicerca = "IN_LAVORAZIONE";
      this.accreditamentoService.ricercaPraticheAcc = new RicercaPraticheAccreditamentoRequest(this.statoRicerca);
      this.loaderDT = true;
      this.subscribers.ricercaDichiarante = this.accreditamentoService.ricercaPratiche().subscribe(data => {
          this.elencoPratiche = data;
          this.loaderDT = false;
          setTimeout(() => {
              this.dtTrigger.next();
            });
      }, err => {
          this.logger.error(err);        
      });
//      this.s();
    }
  
  onChangeStato(){
      this.accreditamentoService.ricercaPraticheAcc = new RicercaPraticheAccreditamentoRequest(this.statoRicerca);
      this.loaderDT = true;
      this.subscribers.ricercaDichiarante = this.accreditamentoService.ricercaPratiche().subscribe(data => {
          this.elencoPratiche = data;
          this.loaderDT = false;
          this.rerender();
      }, err => {
          this.logger.error(err);        
      });
  }
  
  rerender(): void {
      this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
         dtInstance.destroy();
         this.dtTrigger.next();
     });
    }

    onSubmit() {
        console.log(this.ricercaModel);
      this.accreditamentoService.ricercaAccreditamento = this.ricercaModel;
      this.router.navigate([RoutingAccreditamento.ACCREDITAMENTO_GESTIONE]);

    }

   
    
    goBack(){
    }
    
    checkRow(idUtenteProvv: number){
        console.log(idUtenteProvv);
        this.ricercaModel = new RicercaAccreditamentoRequest(idUtenteProvv);
    }




  
}
