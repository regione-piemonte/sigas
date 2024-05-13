import { Component, OnInit, ViewChild, ElementRef, AfterViewInit, Renderer2 } from '@angular/core';
import { DestroySubscribers } from '../../../../core/commons/decorator/destroy-unsubscribers';
import { ModificaAccreditamentoService } from "../../service/modifica-accreditamento.service";
import { LoggerService } from '../../../../core/services/logger.service';
import { DataTableIt } from '../../../../commons/class/commons-data-table';
import { DataTableDirective } from 'angular-datatables';
import { AliquotaVO } from '../../../../commons/vo/aliquota-vo';
import { TipoAliquotaVO } from '../../../../commons/vo/tipo-aliquota-vo';
import { Subject } from 'rxjs';
import { ConfermaAliquotaRequest } from '../../../../commons/request/conferma-aliquota-request';
import { SigasDialogComponent } from '../../../../shared/component/sigas-dialog/sigas-dialog.component';
import { TipoConsumoVO } from '../../../../commons/vo/tipo-consumo-vo';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../../commons/class/dateformat';
import * as moment from 'moment';
import { Router } from '@angular/router';
import { UtenteProvvisorioVO } from '../../../../commons/vo/utente-provvisorio-vo';
import { RoutingAccreditamentoFO } from '../../../../commons/routing';
import { RicercaAccreditamentoRequest } from '../../../../commons/request/ricerca-accreditamento-request';

declare var jquery: any;
declare var $: any;

@Component({
  selector: 'app-elenco-pratiche-accreditamento',
  templateUrl: './elenco-pratiche-accreditamento.component.html',
  styleUrls: ['./elenco-pratiche-accreditamento.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})

@DestroySubscribers()
export class ElencoPraticheAccreditamentoComponent implements OnInit, AfterViewInit {


    elencoPratiche: Array<UtenteProvvisorioVO>
    public subscribers: any = {};
    
    private loaderDT: boolean;
    private dtTrigger: Subject<any> = new Subject();
    
    private dtOptions: any;
    
    ricercaModel: RicercaAccreditamentoRequest;

  constructor(
    private logger: LoggerService,
    private modificaAccreditamentoService: ModificaAccreditamentoService,
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
                { className: 'dt-center', 'targets': [0, 1, 2, 3] },
                { width: '10%', targets: 0 },
                { width: '30%', targets: 1 },
                { width: '30%', targets: 2 },
                { width: '30%', targets: 3 },
              ]
            };
      
      this.loaderDT = true;
      this.subscribers.ricercaDichiarante =  this.modificaAccreditamentoService.ricercaPratiche().subscribe(data => {
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

    onSubmit() {
        console.log(this.ricercaModel);
      this.modificaAccreditamentoService.ricercaAccreditamento = this.ricercaModel;
      this.router.navigate([RoutingAccreditamentoFO.MODIFICA_ACCREDITAMENTO]);

    }

   
    
    goBack(){
    }
    
    checkRow(idUtenteProvv: number){
        console.log(idUtenteProvv);
        this.ricercaModel = new RicercaAccreditamentoRequest(idUtenteProvv);
    }




  
}
