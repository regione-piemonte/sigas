import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { Subject } from "rxjs";
import { DataTableDirective } from 'angular-datatables';
import { Routing } from '../../../commons/routing';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { DataTableIt } from "../../../commons/class/commons-data-table";
import { LoggerService } from "../../../core/services/logger.service";
import { AnagraficaSoggettiService } from '../../service/anagrafica-soggetti.service';
import { ConsumiVO } from '../../../commons/vo/consumi-vo';
import { RicercaConsumiRequest } from '../../../commons/request/ricerca-consumi-request';
import { saveAs } from "file-saver";
import { AllarmiSoggettoVO } from '../../../commons/vo/allarmi-soggetto-vo';
import { AllarmiVO } from 'src/app/commons/vo/allarmi-vo';

@Component({
  selector: 'app-soggetti',
  templateUrl: './soggetti.component.html',
  styleUrls: ['./soggetti.component.scss']
})

@DestroySubscribers()
export class SoggettiComponent implements OnInit {


  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;

  private dtOptions: any;
  private loaderDT: boolean;
  private filterDisabled: boolean;
  private loaderYear: boolean;
  private elencoConsumi: Array<ConsumiVO>;
  private reportAnno: Array<string>;
  private validati: Array<any>;
  private dtTrigger: Subject<any> = new Subject();
  private ricercaConsumiRequest: RicercaConsumiRequest;
  private anno: string;
  private validato: string;
  private loaderExcel: boolean;
  private allarmeSogg: AllarmiSoggettoVO;
  private allarmeIncoerenza: boolean;
  private filtro: string;

  public subscribers: any = {};

  constructor(
    private router: Router,
    private logger: LoggerService,
    private anagraficaSoggettiService: AnagraficaSoggettiService
  ) { }

  ngOnInit() {
    // Move on top
    window.scrollTo(0, 0);

    this.dtOptions = {
      head: 20,
      pagingType: 'full_numbers',
      pageLength: 10,
      processing: true,
      language: DataTableIt.language,
      responsive: true,
      order: [],
      columnDefs: [
        { className: 'dt-center', "targets": [1,2,3,4] },
        { width: '15%', targets: 2, orderData: 6 },
        { width: '15%', targets: 3, orderData: 7 }, 
        { width: '10%', targets: 4 },
        { width: '20%', targets: 5 },
        {targets: 6, visible: false },
        {targets: 7, visible: false }
      ]
    };

    this.ricercaConsumiRequest =  new RicercaConsumiRequest(null,'');
    this.loaderYear = true;
    this.subscribers.ricercaPerAnno = this.anagraficaSoggettiService.ricercaPerAnno()
      .subscribe(res => {
        this.reportAnno = res;
        this.loaderYear = false;
      }, err => {
        this.logger.error("errore ");
        this.loaderYear = false;
    });

    this.validati = [
      {
        "valore": "",
        "descrizione":"Tutti"
      },
      {
        "valore": "VALIDATO",
        "descrizione":"Validato"
      },
      {
        "valore": "NON_VALIDATO",
        "descrizione":"Non Validato"
      },
      {
        "valore": "NUOVI",
        "descrizione":"Nuovi Soggetti"
      },
      {
        "valore": "INCOERENZE",
        "descrizione":"Incoerenze"
      },
      {
        "valore": "RAVVEDIMENTI",
        "descrizione":"Ravvedimenti"
      },
      {
        "valore": "SCARTI",
        "descrizione":"Scarti"
      },
      {
        "valore": "NOTE",
        "descrizione":"Note"
      },
      {
        "valore": "ACCERTAMENTI",
        "descrizione":"Accertamenti"
      },
      {
        "valore": "RIMBORSI",
        "descrizione":"Rimborsi"
      },      
      {
        "valore": "DOCUMENTI",
        "descrizione":"Documenti"
      },
      {
        "valore": "VERSAMENTO",
        "descrizione":"Versamento"
      }
    ];

    this.validato = this.validati[0].valore;

    this.loaderDT = true;
    //this.anagraficaSoggettiService.ricercaConsumiReq = this.ricercaConsumiRequest;

    if (this.anagraficaSoggettiService.ricercaConsumiReq != undefined) {
      this.anno = this.anagraficaSoggettiService.ricercaConsumiReq.anno;
      this.validato = this.anagraficaSoggettiService.ricercaConsumiReq.validato;
    }
      
    this.subscribers.ricercaConsumi = this.anagraficaSoggettiService.ricercaConsumi()
      .subscribe(resp => {
        this.elencoConsumi = resp;
        if ((this.anno === null || this.anno === '') && this.validato === 'NUOVI') {
          this.elencoConsumi.forEach(item => {                
              item.allarmi.nuovo = true
          });
        }
        this.loaderDT = false;
        setTimeout(() => {
          this.dtTrigger.next(); 
        });
      }, err => {
        this.logger.error(err);
        this.loaderDT = false;
    });

    this.loaderExcel = false;
  }

  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
       dtInstance.destroy();
       this.dtTrigger.next();
   });
  }

  changeFilter() {
    if (this.anno != null || this.validato != null) {
      this.loaderDT = true;
      this.filterDisabled = true;
      if (this.validato === 'NUOVI') {
        this.anno = '';
      }
      if (this.validato === 'INCOERENZE' || 
          this.validato === 'RAVVEDIMENTI' || 
          this.validato === 'SCARTI' ||
          this.validato === 'NOTE' || 
          this.validato === 'ACCERTAMENTI' ||
          this.validato === 'RIMBORSI' || 
          this.validato === 'DOCUMENTI'  ||
          this.validato === 'VERSAMENTO') 
      {
        this.filtro = this.validato;
        this.validato = '';
      } else {
        this.filtro = '';
      }
     
      this.ricercaConsumiRequest.anno = this.anno;
      this.ricercaConsumiRequest.validato = this.validato;
      this.anagraficaSoggettiService.ricercaConsumiReq = this.ricercaConsumiRequest;
      // If necessario poichè resetta il filtro se seleziono 'nuovi soggetti' e poi 'incoerenze'
      if (this.validato === '') {
        this.validato = this.filtro;
      }
	  if (((this.anno === null || this.anno === '') && this.validato === 'NUOVI') || 
        (this.anno !== null && this.anno !== '' && this.validato !== 'NUOVI') ) {
        this.subscribers.ricercaCosumi = this.anagraficaSoggettiService.ricercaConsumi()
          .subscribe(resp => {
            this.elencoConsumi = resp;
            if (this.filtro === 'INCOERENZE') {
              this.elencoConsumi = this.elencoConsumi.filter(
                consumi => consumi.allarmi.coerenza === true);
            } else if (this.filtro === 'RAVVEDIMENTI') {
              this.elencoConsumi = this.elencoConsumi.filter(
                consumi => consumi.allarmi.ravv === true);
            } else if (this.filtro === 'SCARTI') {
              this.elencoConsumi = this.elencoConsumi.filter(
                consumi => consumi.allarmi.scarti === true);
            } else if (this.filtro === 'NOTE') {
              this.elencoConsumi = this.elencoConsumi.filter(
                consumi => consumi.allarmi.note === true);
            } else if (this.filtro === 'ACCERTAMENTI') {
              this.elencoConsumi = this.elencoConsumi.filter(
                consumi => consumi.allarmi.acc === true);
            } else if (this.filtro === 'RIMBORSI') {
              this.elencoConsumi = this.elencoConsumi.filter(
                consumi => consumi.allarmi.rimb === true);
            } else if (this.filtro === 'DOCUMENTI') {
              this.elencoConsumi = this.elencoConsumi.filter(
                consumi => consumi.allarmi.doc === true);
            } else if (this.filtro === 'VERSAMENTO') {
              this.elencoConsumi = this.elencoConsumi.filter(
                consumi => consumi.allarmi.vers === true);
            } else if (this.filtro === 'RIMBORSI_SCADUTI') {
              this.elencoConsumi = this.elencoConsumi.filter(
                consumi => consumi.allarmi.rimbscad === true);
            } else if ((this.anno === null || this.anno === '') && this.validato === 'NUOVI') {
              this.elencoConsumi.forEach(item => {                
                item.allarmi.nuovo = true
              });
            }

            this.loaderDT = false;
            this.filterDisabled = false;
            this.rerender();
          }, err => {
            this.logger.error(err);
            this.loaderDT = false;
            this.rerender();
          });
      } else {
        this.filterDisabled = false;
        this.loaderDT = false;
      }
    }
  }

  goInserimentoSoggetto(){
      this.router.navigateByUrl(Routing.NUOVO_SOGGETTO)
  }
  
  
  dettaglioSoggetto(consumo: ConsumiVO) {
    this.anagraficaSoggettiService.dettaglioConsumo = consumo;
    this.anagraficaSoggettiService.valida = consumo.validato === 'VALIDATO'?true:false;
    if (this.anno != undefined)
      this.anagraficaSoggettiService.annoDichiarazione = this.anno;
    this.anagraficaSoggettiService.headerDichiarante = { 
          idAnag: consumo.idAnag,
          denominazione: consumo.denominazione
    }
    this.anagraficaSoggettiService.setCodiceAzienda = consumo.codiceAzienda;

    this.router.navigateByUrl(Routing.DETTAGLIO_SOGGETTO)
  }

  goExcel() {
    this.loaderExcel = true;
    this.anagraficaSoggettiService.annoDichiarazione = this.anno;
    this.subscribers.scaricaExcelElencoSoggetti = this.anagraficaSoggettiService.scaricaExcelElencoSoggetto(this.elencoConsumi).subscribe(
        res => {
          this.loaderExcel = false;
          saveAs(res, "export_elenco_anagrafica_soggetti_" + this.anno + '.xls');
          
        },
        err => {
            this.logger.error("errore in download excel");
        }
    );
  }
}
