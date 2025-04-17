import { AfterViewInit, Component, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { Subject } from "rxjs";
import { DataTableDirective } from 'angular-datatables';
import { Routing } from '../../../commons/routing';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { DataTableIt } from "../../../commons/class/commons-data-table";
import { LoggerService } from "../../../core/services/logger.service";
import { AnagraficaSoggettiService } from '../../service/anagrafica-soggetti.service';
import { RicercaStoricoSoggettiRequest } from '../../../commons/request/ricerca-storico-soggetti-riquest';
import { StoricoAnagraficaSoggettoVO } from 'src/app/commons/vo/storico-anagrafica-soggetto-vo';
import { ImportazioneUTFService } from '../../../importazione-utf/service/importazione-utf.service';
import { ConsumiPrVO } from '../../../commons/vo/consumi-pr-vo';
import { ProvinceVO } from "../../../commons/vo/luoghi-vo/province-vo";

@Component({
  selector: 'app-storico-anagrafica',
  templateUrl: './storico-anagrafica.component.html',
  styleUrls: ['./storico-anagrafica.component.scss']
})

@DestroySubscribers()
export class StoricoAnagraficaComponent implements OnInit, AfterViewInit {

  @ViewChild(DataTableDirective) dtElement: DataTableDirective;
  private dtOptionsStoricoAnagrafica: any;
  private loaderDT: boolean = false;
  private loaderYear: boolean;
  private reportAnno: Array<string>;
  private validati: Array<any>;
  private dtTriggerStoricoAnagrafica: Subject<any> = new Subject();
  private anno: string;
  private ricercaStoricoSoggettiRequest: RicercaStoricoSoggettiRequest = {};
  private storicoAnagraficaList: StoricoAnagraficaSoggettoVO[] = [];

  message: string = 'Consulta lo storico anagrafica';
  type: string = 'INFO'; 

  showMessageError: boolean = false;
  messageError:string;
  
  // dati import
  private importConsumiPrList: Array<ConsumiPrVO>;
  private importConsumiPrViewContainer: ConsumiPrVO;
  private importElencoProvince: Array<ProvinceVO> = [];    
  private loaderImportItems: boolean = false;
  private loaderImportElencoProvince: boolean = false



  public subscribers: any = {};

  constructor(
    private router: Router,
    private logger: LoggerService,
    private anagraficaSoggettiService: AnagraficaSoggettiService,
    private renderer: Renderer2,
    private importUtfService: ImportazioneUTFService
  ) { }

  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);
  }

  ngOnInit() {
    // Move on top
    window.scrollTo(0, 0);

    this.loaderYear = true;
    this.subscribers.ricercaPerAnno = this.anagraficaSoggettiService.ricercaPerAnno()
      .subscribe(res => {
        this.reportAnno = res;
        this.loaderYear = false;
      }, err => {
        this.logger.error("errore ");
        this.loaderYear = false;
    });

    this.initTable();
    this.renderTable();
  }

  renderTable(): void {  

   if (this.dtElement != null && this.dtElement.dtInstance != null) {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
          dtInstance.destroy();
          this.dtTriggerStoricoAnagrafica.next();
        });
    } else {
        setTimeout(() => {
          this.dtTriggerStoricoAnagrafica.next();
        });
    }
  }
  
  ricercaStoricoAnagraficaSoggetti() {

    this.loaderDT = true;
    this.showMessageError = false;
    this.messageError = null;

    this.anagraficaSoggettiService.ricercaStoricoAnagraficaSoggetti(this.ricercaStoricoSoggettiRequest).subscribe(list => {

      if(list != null) {
        this.storicoAnagraficaList = list;       
        console.log("storicoAnagraficaList", this.storicoAnagraficaList);   
      } else {
        this.storicoAnagraficaList = []; 
      }
      this.loaderDT = false;
      this.renderTable();
    },
    err => {
      this.logger.error("errore ", err.message);

      this.messageError =  err.message;
      this.showMessageError = true;
      this.storicoAnagraficaList = []; 
      this.loaderDT = false;
      this.renderTable();
    });
  }

  initTable() {
    this.dtOptionsStoricoAnagrafica = {
      destroy: true,
      processing: true,
      head: 20,
      pageLength: 10,
      pagingType: 'full_numbers',
      language: DataTableIt.language,
      responsive: {
        details: false
    },
    aaSorting: [],      
      columnDefs: [          
        { width: '26%', orderable: true, targets: 0 },
        { width: '18%', orderable: false, targets: 1 },
        { width: '10%', orderable: false, targets: 2 },
        { width: '15%', orderable: false, targets: 3 },
        { width: '15%', orderable: false, targets: 4 },        
        { width: '4%', orderable: false, targets: 5 },        
        { width: '2%', orderable: false, targets: 6 }
    ],
      bInfo: false
    };
  }

  showImportUTF(el: StoricoAnagraficaSoggettoVO) {
    console.log("StoricoAnagraficaSoggettoVO", el);
    this.loaderImportItems = true;
    
    let idImportUTF: number = el.idImport;
    let annualita: number = +el.annualita;

    console.log("idImportUTF", idImportUTF);
    console.log("annualita", annualita);

    this.importUtfService.ricercaReportUTF(idImportUTF, annualita).subscribe(resp => {
      console.log("resp", resp);
      console.log(">>>>>>>>Ricerca report UTF per idImport: " + idImportUTF);
      this.importConsumiPrList = resp;
      this._inizializzaElencoProvincieImport(this.importConsumiPrList);
      this._popolaContainerVisualizzazioneImport(this.importConsumiPrList);
      this.loaderImportItems = false;
    },
    err => {

      console.log("Errore nella ricerca dei report UTF", err);
      this.loaderImportItems = false;

    });
  }

  pulisciCampi() {
    this.ricercaStoricoSoggettiRequest = {};
    this.storicoAnagraficaList = [];
    this.showMessageError = false;
    this.messageError = null;
    this.renderTable();
  }

  private _inizializzaElencoProvincieImport(importConsumiPrList: Array<ConsumiPrVO>) {
    this.loaderImportElencoProvince = true;
    this.importElencoProvince = importConsumiPrList.map((consumiPr) => new ProvinceVO(0,'',consumiPr.provincia_erogazione));
    this.loaderImportElencoProvince = false;
}

onCambiaProvinciaImport(siglaProvincia: String) {

  if(this.importConsumiPrList!=null && this.importConsumiPrList!= undefined)
  { 
      this.loaderImportItems = true;

      if(siglaProvincia!="TOTALE")
      {
          let importSelezionatoConsummiPrFiltarti: ConsumiPrVO[] = this.importConsumiPrList
                                                                   .filter((consumiPr) => consumiPr.provincia_erogazione == siglaProvincia);
          this._popolaContainerVisualizzazioneImport(importSelezionatoConsummiPrFiltarti);
      } else {
          this._popolaContainerVisualizzazioneImport(this.importConsumiPrList);
      }     

      setTimeout(() => {
          this.loaderImportItems = false;
      }, 500);
      
  }
}

  private _popolaContainerVisualizzazioneImport(importConsummiPrList: ConsumiPrVO[]) {
        if(importConsummiPrList!=null && importConsummiPrList!=undefined)
        {
            let usi_industriali_1200 = importConsummiPrList
                                       .reduce((acc, val) => acc + val.usi_industriali_1200, 0);

            let usi_industriali_up = importConsummiPrList
                                    .reduce((acc, val) => acc + val.usi_industriali_up, 0); 

            let tot_industriali = importConsummiPrList
                                .reduce((acc, val) => acc + val.tot_industriali, 0);

            let usi_civili_120 = importConsummiPrList
                                .reduce((acc, val) => acc + val.usi_civili_120, 0);

            let usi_civili_480 = importConsummiPrList
                                .reduce((acc, val) => acc + val.usi_civili_480, 0);

            let usi_civili_1560 = importConsummiPrList
                                  .reduce((acc, val) => acc + val.usi_civili_1560, 0);

            let usi_civili_up = importConsummiPrList
                                .reduce((acc, val) => acc + val.usi_civili_up, 0);

            let tot_civili = importConsummiPrList
                             .reduce((acc, val) => acc + val.tot_civili, 0);

            let tot_nuovi_allacciamenti = importConsummiPrList
                                          .reduce((acc, val) => acc + val.tot_nuovi_allacciamenti, 0);

            let conguaglio_dich = importConsummiPrList
                                  .reduce((acc, val) => acc + val.conguaglio_dich, 0); 

            let totaleDich = importConsummiPrList
                            .reduce((acc, val) => acc + val.totaleDich, 0);

            let rateo_dich = importConsummiPrList
                            .reduce((acc, val) => acc + val.rateo_dich, 0);
            
            this.importConsumiPrViewContainer = new ConsumiPrVO(0,0,'','','','',
                                                                           usi_industriali_1200,
                                                                           usi_industriali_up,
                                                                           usi_civili_120,
                                                                           usi_civili_480,
                                                                           usi_civili_1560,
                                                                           usi_civili_up,
                                                                           tot_nuovi_allacciamenti,
                                                                           tot_industriali,
                                                                           tot_civili,
                                                                           0,0,
                                                                           rateo_dich,
                                                                           conguaglio_dich,
                                                                           0,0,0,
                                                                           totaleDich,
                                                                           0,'',[],0,0);

        }       

    }

}
