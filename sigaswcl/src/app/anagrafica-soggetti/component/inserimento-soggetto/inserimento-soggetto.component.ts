import { AfterViewInit, Component, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { Subject } from "rxjs";
import { DataTableDirective } from 'angular-datatables';
import { Routing } from '../../../commons/routing'
import { DataTableIt } from "../../../commons/class/commons-data-table";
import { LoggerService } from "../../../core/services/logger.service";

import { AnagraficaSoggettiService } from '../../service/anagrafica-soggetti.service';
import { AnagraficaSoggettoVO } from '../../../commons/vo/soggetti-vo';
import { RicercaConsumiRequest } from '../../../commons/request/ricerca-consumi-request';

@Component({
  selector: 'app-inserimento-soggetto',
  templateUrl: './inserimento-soggetto.component.html',
  styleUrls: ['./inserimento-soggetto.component.scss']
})

@DestroySubscribers()
export class InserimentoSoggettoComponent implements OnInit, AfterViewInit {
    
  private soggettoToSave: AnagraficaSoggettoVO;
  private ricercaConsumiRequest: RicercaConsumiRequest;
  private numberOfSoggetti: string;
  private nextVal: string = 'NEW_';
  private loaderInserimentoSoggettoPage: boolean;

  public subscribers: any = {};

  constructor(
    private router: Router,
    private logger: LoggerService,
    private anagraficaSoggettiService: AnagraficaSoggettiService,
    private renderer: Renderer2
  ) { }

  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);
  }
  
  ngOnInit() {
     console.log("INIZIO");
     this.soggettoToSave = new AnagraficaSoggettoVO(0,0,"","","","","","",0,"","","",0,false,0,"","",0,0,null);
     this.subscribers.number = this.anagraficaSoggettiService.numberOfSoggetti().subscribe(
     resp =>{
         this.numberOfSoggetti = this.nextVal+resp;
         this.soggettoToSave.codiceAzienda = this.numberOfSoggetti;
         console.log(this.numberOfSoggetti);
             },err => {
       this.logger.error("errore su soggetto");
     });
  }
  
  //Al termine dell’inserimento la lista delle anagrafiche verrà filtrata per “Nuovi Soggetti”.
  //Il filtro nuovi soggetti essendo privo di annualità filtrerà indipendentemente dall’annualità.
  
  onSubmitInserisci() {
      console.log("Inserisci");
      this.loaderInserimentoSoggettoPage = true;
      this.subscribers.confermaInserimentoSoggetto = this.anagraficaSoggettiService
                                                         .confermaInserimentoSoggetto(this.soggettoToSave, 
                                                                                      this.anagraficaSoggettiService.annoDichiarazione, 
                                                                                      "Operatore")
                                                         .subscribe
  (
        resp =>{
            console.log(resp);
            this.ricercaConsumiRequest =  new RicercaConsumiRequest('','NUOVI');
            
            this.anagraficaSoggettiService.ricercaConsumiReq = this.ricercaConsumiRequest;
            
            this.router.navigateByUrl(Routing.ELENCO_CONSUMI);
        },err => {
          this.logger.error("errore salvataggio soggetto");
      });
      console.log("FINE Inserisci");
      
  }
  
  goBack() {
      this.router.navigateByUrl(Routing.ELENCO_CONSUMI);
  }
}
