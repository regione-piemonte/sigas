import { AfterViewInit, Component, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { Subject } from "rxjs";
import { DataTableDirective } from 'angular-datatables';
import { Routing } from '../../../commons/routing';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { DataTableIt } from "../../../commons/class/commons-data-table";
import { LoggerService } from "../../../core/services/logger.service";
import { AnagraficaSoggettiService } from '../../service/anagrafica-soggetti.service';
import { ConsumiVO } from '../../../commons/vo/consumi-vo';
import { AnagraficaSoggettoVO } from '../../../commons/vo/soggetti-vo';
import { RicercaConsumiRequest } from '../../../commons/request/ricerca-consumi-request';
import { saveAs } from "file-saver";
import { AllarmiSoggettoVO } from '../../../commons/vo/allarmi-soggetto-vo';
import { OrdinativiService } from '../../service/ordinativi.service';

@Component({
  selector: 'app-associa-ordinc-soggetto',
  templateUrl: './associa-ordinc-soggetto.component.html',
  styleUrls: ['./associa-ordinc-soggetto.component.scss']
})

@DestroySubscribers()
export class AssociaOrdincSoggettoComponent implements OnInit, AfterViewInit {


    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    private dtOptions: any;
    private loaderDT: boolean;
    private filterDisabled: boolean;
    private loaderYear: boolean;
    private elencoConsumi: Array<ConsumiVO>;
    private elencoSoggetti: Array<AnagraficaSoggettoVO>;
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
    private soggettoSelezionato: ConsumiVO;
    

    public subscribers: any = {};
    
    private showMessageError: boolean;
    private showSuccess: boolean;
    private messageSuccess: string;
    private messageError: string;

    constructor(
      private router: Router,
      private logger: LoggerService,
      private anagraficaSoggettiService: AnagraficaSoggettiService,
      private ordinativiService: OrdinativiService,
      private renderer: Renderer2
    ) { }

    ngAfterViewInit(): void {
      const element = this.renderer.selectRootElement('#idDivToContPrin');
      setTimeout(() => element.focus(), 0);
    }

    ngOnInit() {
        this.clearMsg();
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
          { className: 'dt-center', "targets": [1,2] },
          { width: '50%', targets: 1 },
          { width: '50%', targets: 2 } 
        ]
      };
      
      setTimeout(() => {
        if(this.ordinativiService.getShowSuccess){
            this.showSuccess = this.ordinativiService.getShowSuccess;
            this.messageSuccess = this.ordinativiService.getMessageSuccess;
        }
        
        if(this.ordinativiService.getShowMessageError){
            this.showMessageError = this.ordinativiService.getShowMessageError;
            this.messageError = this.ordinativiService.getMessageError;
        }
      });

      
      this.loaderDT = true;

      this.subscribers.ricercaConsumi = this.anagraficaSoggettiService.ricercaSoggetti()
      .subscribe(resp => {
        this.elencoSoggetti = resp;
        this.loaderDT = false;
        setTimeout(() => {
          this.dtTrigger.next(); 
        });
      }, err => {
        this.logger.error(err);
        this.loaderDT = false;
    });

      this.loaderExcel = false;
      this.ordinativiService.soggettoAssociato = null;
    }

    rerender(): void {
      this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
         dtInstance.destroy();
         this.dtTrigger.next();
     });
    }

    clearMsg(){
        this.messageSuccess = "";
        this.showSuccess = false;
        this.showMessageError = false;
        this.messageError = "";
    }
    goAssociaVersamento(){
//        this.router.navigateByUrl(Routing.ASSOCIA_VERSAMENTO);
        this.router.navigateByUrl(Routing.ORDINATIVI_INCASSO);
    }
    
    goBack(){
        this.router.navigateByUrl(Routing.ORDINATIVI_INCASSO);  
    }
    
//    associaSogg(sogg: ConsumiVO){
//        this.ordinativiService.soggettoAssociato = sogg;
//    }
    
    associaSogg(sogg: AnagraficaSoggettoVO){
        this.ordinativiService.soggettoAssociato = sogg;
        this.clearMsg();
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

    calculateArialLabel(sogg: AnagraficaSoggettoVO): String{
      return "Seleziona soggetto " +  sogg.denominazione + " avente codice azienda " + sogg.codiceAzienda;
    }
}
