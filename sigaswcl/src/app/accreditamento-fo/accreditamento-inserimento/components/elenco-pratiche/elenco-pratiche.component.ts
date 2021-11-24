import { Component, OnInit, Input, OnDestroy } from '@angular/core';
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
export class ElencoPraticheComponent implements OnInit {

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
  ) { }

  ngOnInit(): void {
    this.logger.info("init Ricerca Pratiche Component");
//    this.dtOptions = {
//            head: 20,
//            pagingType: 'full_numbers',
//            pageLength: 10,
//            processing: true,
//            language: DataTableIt.language,
//            responsive: true,
//            order: [],
//            columnDefs: [
//              { className: 'dt-center', 'targets': [0, 1, 2, 3] },
//              { width: '5%', targets: 0, orderable: false },
//              { width: '30%', targets: 1 },
//              { width: '10%', targets: 2 },
//              { width: '10%', targets: 3 }
//            ]
//          };
    
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

//  onSubmit() {
//    this.logger.info("submit init");
//    this.inserisciAccreditamentoService.clean();
//    this.inserisciAccreditamentoService.RicercaDichiarante = this.ricercaModel;
//    this.router.navigate([RoutingAccreditamentoFO.INSERIMENTO_DICHIARANTE]);
//
//  }

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



