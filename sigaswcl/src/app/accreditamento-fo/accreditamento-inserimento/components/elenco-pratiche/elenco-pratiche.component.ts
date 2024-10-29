import { Component, OnInit, Input, OnDestroy, Renderer2, AfterViewInit } from '@angular/core';
import { LoggerService } from '../../../../core/services/logger.service';
import { Router } from '@angular/router';
import { InserisciAccreditamentoService } from '../../service/inserisci-accreditamento.service';
import { RicercaDichiaranteRequest } from '../../../../commons/request/ricerca-dichiarante-request';
import { RoutingAccreditamento } from '../../../../commons/routing';
import { RoutingAccreditamentoFO } from '../../../../commons/routing';
import { DestroySubscribers } from '../../../../core/commons/decorator/destroy-subscribers';
import { UtenteProvvisorioVO } from '../../../../commons/vo/utente-provvisorio-vo';
import { DataTableIt } from '../../../../commons/class/commons-data-table';
import { Subject } from 'rxjs';
import { RicercaAccreditamentoRequest } from '../../../../commons/request/ricerca-accreditamento-request';



@Component({
  selector: 'app-accreditamento-elenco-pratiche',
  templateUrl: './elenco-pratiche.component.html',
  styleUrls: ['./elenco-pratiche.component.scss']
})
export class ElencoPraticheComponent implements OnInit, AfterViewInit {

    ricercaModel: RicercaDichiaranteRequest;
    ricercaModelAcc: RicercaAccreditamentoRequest;
    elencoPratiche: Array<UtenteProvvisorioVO>
    public subscribers: any = {};

    private loaderDT: boolean;
    private dtTrigger: Subject<any> = new Subject();
    
    private dtOptions: any;

  constructor(
    private logger: LoggerService,
    private inserisciAccreditamentoService: InserisciAccreditamentoService,
    private router: Router,
    private renderer: Renderer2
  ) { }

  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);
  }

  ngOnInit(): void {
    this.logger.info("init Ricerca Pratiche Component");

    this.dtOptions = {
            searching: true,
            pagingType: 'full_numbers',
            info: false,
            language: DataTableIt.language,
            ordering: true,
            columnDefs: [
              { className: 'dt-center' },
              
            ]
          };
    
    this.loaderDT = true;
    this.subscribers.ricercaDichiarante = this.inserisciAccreditamentoService.ricercaPratiche().subscribe(data => {
        this.elencoPratiche = data;
        this.loaderDT = false;
        setTimeout(() => {
            this.dtTrigger.next();
          });
    }, err => {
        this.logger.error(err);        
    });
    this.initRicercaModel();
  }

  cancella() {
    this.initRicercaModel();
  }
  
  goBack(){
      this.router.navigate([RoutingAccreditamentoFO.RICERCA_PRATICHE]);  
  }


  checkRow(idUtenteProvv: number){
      console.log(idUtenteProvv);
      this.ricercaModelAcc = new RicercaAccreditamentoRequest(idUtenteProvv);
  }
  
  visualizzaPratica() {
      console.log(this.ricercaModel);
    this.inserisciAccreditamentoService.ricercaAccreditamento = this.ricercaModelAcc;
    this.router.navigate([RoutingAccreditamentoFO.VISUALIZZA_ACCREDITAMENTO]);

  }

  initRicercaModel() {
    this.ricercaModel = new RicercaDichiaranteRequest("", "");
  }
}



