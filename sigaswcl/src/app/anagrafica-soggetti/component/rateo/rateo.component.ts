import { AfterViewInit, 
         Component, 
         OnInit, 
         Renderer2, 
         ViewChild } from "@angular/core";         
import { Router } from "@angular/router";
import { Subject } from "rxjs";

import {DatatableComponent} from '@swimlane/ngx-datatable';
import { DataTableIt } from "../../../commons/class/commons-data-table";
import { DataTableDirective } from 'angular-datatables';

import { RicercaRateoRequest } from "../../../commons/request/ricerca-rateo-request";
import { DichiaranteVO } from "../../../commons/vo/dichiarante-vo";
import { RateoVO } from "../../../commons/vo/rateo-vo";
import { ProvinceVO } from "../../../commons/vo/luoghi-vo/province-vo";
import { RateoRowItemTable } from "../../../commons/vo/rateo-row-item-table";

import { LoggerService } from "../../../core/services/logger.service";
import { AnagraficaSoggettiService } from "../../service/anagrafica-soggetti.service";
import { LuoghiService } from "../../../shared/service/luoghi-service"
import { UtilityService } from "../../../core/services/utility/utility.service";
import { DestroySubscribers } from "../../../core/commons/decorator/destroy-unsubscribers";

declare var jquery: any;
declare var $: any;

@Component({
    selector: 'app-rateo',
    templateUrl: './rateo.component.html',
    styleUrls: ['./rateo.component.scss']
})

@DestroySubscribers()
export class RateoComponent implements OnInit, AfterViewInit {

    @ViewChild(DataTableDirective) dtElement: DataTableDirective;

    /********************************************************************
     * Variabili di classe
    *********************************************************************/
    private dtOptions: any;
    private dtTrigger: Subject<any> = new Subject();

    public subscribers: any = {};

    private elencoRatei: Array<RateoVO> = new Array<RateoVO>();
    private rateoRowItemTableList: Array<RateoRowItemTable> = new Array<RateoRowItemTable>();

    private filterDisabled: boolean;
    private loaderDT: boolean;
    private loaderYear: boolean;
    private loaderMonth: boolean;
    private anniVersamentiPerRicerca: Array<string>;
    private mesiVersamenti: Array<string>;
    private loaderProvince: boolean;
    private provinciaSelected: ProvinceVO;
    private provinciaList: Array<ProvinceVO>;    
    private mesiAnnuali: Array<any> = [
        {
            'valore': '1',
            'descrizione': 'Gennaio'
        },
        {
            'valore': '2',
            'descrizione': 'Febbraio'
        },
        {
            'valore': '3',
            'descrizione': 'Marzo'
        },
        {
            'valore': '4',
            'descrizione': 'Aprile'
        },
        {
            'valore': '5',
            'descrizione': 'Maggio'
        },
        {
            'valore': '6',
            'descrizione': 'Giugno'
        },
        {
            'valore': '7',
            'descrizione': 'Luglio'
        },
        {
            'valore': '8',
            'descrizione': 'Agosto'
        },
        {
            'valore': '9',
            'descrizione': 'Settembre'
        },
        {
            'valore': '10',
            'descrizione': 'Ottobre'
        },
        {
            'valore': '11',
            'descrizione': 'Novembre'
        },
        {
            'valore': '12',
            'descrizione': 'Dicembre'
        }
    ];


    private annoSelected: string;
    private meseSelected: string;
    private dichiaranteVO: DichiaranteVO;
    private idRateoDaCancellare: number;

    private showMessageErrorModRateo: boolean = false;
    private messageErrorModRateo: String = "Si è verificato un errore durante l'esecuzione dell'operazione richiesta. Si prega di riprovare più tardi.";
    private showSuccessModRateo: boolean = false;
    private messageSuccessModRateo: String = "Operazione completata con successo.";
    private showMessageInfoAggiornaRateo: boolean = false;
    private messageInfoAggiornaRateo: String = "Attenzione eventuali modifiche non confermate verrano perse alla chiusura.";   

    /********************************************************************
     * Costruttore
    *********************************************************************/
    constructor(
        private router: Router,
        private logger: LoggerService,
        private anagraficaSoggettiService: AnagraficaSoggettiService,
        private luoghiService:LuoghiService,
        private utilityService: UtilityService, 
        private renderer: Renderer2
    ) { 
        this.anagraficaSoggettiService.rateoRefreshAnniProvincieDispinibiliObservable.subscribe((rateoRefreshAnniProvincieDispinibili) => {
            if(rateoRefreshAnniProvincieDispinibili) {
                this.loadAnniVersamentoPerRicerca();
                this.loadProvinceVersamenti();
            }            
        });
    }      
    
    /********************************************************************
     * Funzioni associate ad Eventi
    *********************************************************************/  
    ngOnInit(): void {
        // Move on top
        window.scrollTo(0, 0);

        //Inzializzazione tabella
        //--------------------------
        this.dtOptions = {
            deferRender: true,
            destroy: true,
            processing: true,            
            responsive: {
                details: false
            },            
            head: 20,
            pageLength: 10,
            pagingType: 'first_last_numbers',
            language: DataTableIt.language,
            columnDefs: [                
                {targets: 0, orderable: true},
                {targets: 1, orderable: true},
                {targets: 2, orderData: true},                
                {targets: 3, orderable: true}                 
            ],
            bInfo: false
        };        

        //Inizializzazione variabili
        this.rateoRowItemTableList=[];
        
        //Lettura oggetto dichiarante
        if (this.anagraficaSoggettiService.headerDichiarante) {
            this.dichiaranteVO = this.anagraficaSoggettiService.headerDichiarante;            
        }

        //Caricamento combo anni. Per uniformità si utilizza lo stesso servzio presente nel
        //componente versamenti
        this.loadAnniVersamentoPerRicerca();
        this.loadProvinceVersamenti();
    }

    ngAfterViewInit(): void {
        //throw new Error("Method not implemented.");
    }

    //Ricerca versamenti
    changeFilter() {        
        this.showMessageInfoAggiornaRateo = false; 
        this.showMessageErrorModRateo = false;
        this.showSuccessModRateo = false;               
        if ( this.annoSelected && this.provinciaSelected ) {            
            this.filterDisabled = true;
            this.loaderDT = true;
            this.showMessageInfoAggiornaRateo = true;                 
            //this.anagraficaSoggettiService.getIdAnag
            var ricercaRateoRequest: RicercaRateoRequest = new RicercaRateoRequest(this.dichiaranteVO.idAnag, 
                                                                                   this.provinciaSelected.sigla,
                                                                                   this.annoSelected,
                                                                                   null);
            this.subscribers.ricercaRateo = this.anagraficaSoggettiService.ricercaRateoList(ricercaRateoRequest)
                                                .subscribe(resp => {
                                                                this.elencoRatei = resp;
                                                                this.rateoRowItemTableList = new Array<RateoRowItemTable>();
                                                                if(this.elencoRatei != null && this.elencoRatei != undefined) {
                                                                    this.rateoRowItemTableList = 
                                                                            this.elencoRatei.map(rateo => {return new RateoRowItemTable(rateo.idRateo,
                                                                                                                                        rateo.idAnag,
                                                                                                                                        rateo.importo,
                                                                                                                                        rateo.mese,
                                                                                                                                        this.mesiAnnuali.filter(mese => mese.descrizione == rateo.mese)[0].valore,                                                                                                                                        
                                                                                                                                        this.provinciaSelected.denominazione,
                                                                                                                                        rateo.annualita)});
                                                                }
                                                                
                                                                this.rateoRowItemTableList = this.simulaInserimentoRateoVuoto(this.rateoRowItemTableList);                                                                    
                                                                this.filterDisabled = false;
                                                                this.loaderDT = false;                                                                
                                                        }, 
                                                        err => {//this.logger.error( err );
                                                                this.loaderDT = false;
                                                                this.showMessageInfoAggiornaRateo = false;
                                                                this.showMessageErrorModRateo = true; 
                                                        });
        }
        
    }

    /********************************************************************
     * Funzioni private
    *********************************************************************/
    loadAnniVersamentoPerRicerca(){
        //this.logger.info("carico gli anni versamento")
        this.loaderYear = true;
        if (this.anagraficaSoggettiService.ricercaAnniVersamentiPerRicerca() != null)  
        {
            this.subscribers.anniVersamentiPerRicerca = this.anagraficaSoggettiService.ricercaAnniVersamentiPerRicerca()
                .subscribe(
                    res => { 
                        this.anniVersamentiPerRicerca = res;
                        this.loaderYear = false;                        
                    }, 
                    err => {                        
                        this.loaderYear = false;}
                    );
        }
    }
  
    loadProvinceVersamenti() {
        //this.logger.info("carico le province")
        this.loaderProvince = true;
        this.subscribers.province = this.anagraficaSoggettiService.ricercaProvinceVersamenti().subscribe(data => {
            this.loaderProvince = false;
            this.provinciaList = data;
        }, err => {
            //this.logger.error("Errore nel recupero delle province");
            this.loaderProvince = false;
        });
    }

    loadMesiVersamento() {        
        this.loaderMonth = true;
        this.subscribers.meseVersamenti = this.anagraficaSoggettiService.ricercaMeseVersamenti(this.annoSelected).subscribe(data => {
            this.mesiVersamenti = data;
            this.loaderMonth = false;
        }, err => {
            //this.logger.error("Errore nel recupero dei mesi versamenti ");
            this.loaderMonth = false;
        });
    }

    rerender(): void {
        try {
          this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            // Destroy the table first
            dtInstance.destroy();
            // Call the dtTrigger to rerender again
            this.dtTrigger.next();
          });
        } catch (err) {
          console.log(err);
        }
    }

    simulaInserimentoRateoVuoto(rateoRealiArray: RateoRowItemTable[])
    {
        let rateiItemTableFittizi: RateoRowItemTable[]=[];
        if(rateoRealiArray != null && rateoRealiArray != undefined )
        {            
            for (var i = 0 ; i <= 11; i++) {            
                if(rateoRealiArray.filter(rateo => rateo.mese == this.mesiAnnuali[i].descrizione).length == 0)
                {
                    rateiItemTableFittizi.push(this.creaRateoVuotoPerMese(this.dichiaranteVO.idAnag, 
                                                                          this.annoSelected, 
                                                                          this.provinciaSelected.denominazione, 
                                                                          this.mesiAnnuali[i].descrizione,
                                                                          this.mesiAnnuali[i].valore, 
                                                                          this.anagraficaSoggettiService.getCodiceAzienda));
                }           
            }
            if(rateiItemTableFittizi.length > 0){
                rateoRealiArray = [...rateoRealiArray, ...rateiItemTableFittizi];
            }                       
        } else {
            for (var i = 0 ; i <= 11; i++) {                            
                rateiItemTableFittizi.push(this.creaRateoVuotoPerMese(this.dichiaranteVO.idAnag, 
                                                                      this.annoSelected, 
                                                                      this.provinciaSelected.denominazione, 
                                                                      this.mesiAnnuali[i].descrizione,
                                                                      this.mesiAnnuali[i].valore, 
                                                                      this.anagraficaSoggettiService.getCodiceAzienda));
                           
            }
            rateoRealiArray = [...rateiItemTableFittizi];          
        }
        rateoRealiArray.sort((a,b) => {                                                                   
                return a.codMese - b.codMese;
                    
        });        
        return rateoRealiArray
    }

    creaRateoVuotoPerMese(idAnagrafica: number, annualita: string, provincia: String, 
                          mese: string, codiceMese: number, codiceAzienda: string)
    {    
        let rateoFittizio: RateoRowItemTable = new RateoRowItemTable(null, idAnagrafica, null, 
                                                                    mese, codiceMese, provincia, annualita);
        return rateoFittizio;
    }

    checkRateoValue(rateo: RateoVO){
        if(rateo.importo===undefined || 
           rateo.importo===null ||
           rateo.importo===0)
        {           
          return false;
        }        
        return true;  
    }

    inserisciNuovoRateo(rateoVO: RateoVO){        
        this.subscribers.inserisciNuovoRateo = this.anagraficaSoggettiService.inserisciRateo(rateoVO).subscribe(
            resp => {
                this.changeFilter();                
                this.loaderDT = false;
                this.showSuccessModRateo = true;
                this.showMessageInfoAggiornaRateo = false;
                this.showMessageErrorModRateo = false;
                this.anagraficaSoggettiService.emitVersamentiRefreshRateo(true);                                                                               
            }, 
            err => {
                this.loaderDT = false;                    
                this.showSuccessModRateo = false;
                this.showMessageInfoAggiornaRateo = false;
                this.showMessageErrorModRateo = true;
            } 
        );
    }

    modificaRateo(rateoVO: RateoVO){       
        this.subscribers.inserisciNuovoRateo = this.anagraficaSoggettiService.modificaRateo(rateoVO).subscribe(
            resp => {
                this.changeFilter();                
                this.loaderDT = false;
                this.showSuccessModRateo = true;
                this.showMessageInfoAggiornaRateo = false;
                this.showMessageErrorModRateo = false;
                this.anagraficaSoggettiService.emitVersamentiRefreshRateo(true);                                                              
            }, 
            err => {
                this.loaderDT = false;                    
                this.showSuccessModRateo = false;
                this.showMessageInfoAggiornaRateo = false;
                this.showMessageErrorModRateo = true; 
            } 
        );
    }

    cancellaRateo(){
        this.showSuccessModRateo = false;
        this.showMessageInfoAggiornaRateo = false;
        this.showMessageErrorModRateo = false; 
        this.subscribers.inserisciNuovoRateo = this.anagraficaSoggettiService.eliminaRateo(this.idRateoDaCancellare).subscribe(
            resp => {
                this.changeFilter();                
                this.loaderDT = false;
                this.showSuccessModRateo = true;
                this.showMessageInfoAggiornaRateo = false;
                this.showMessageErrorModRateo = false;  
                this.anagraficaSoggettiService.emitVersamentiRefreshRateo(true);                                                               
            }, 
            err => {
                this.loaderDT = false;                    
                this.showSuccessModRateo = false;
                this.showMessageInfoAggiornaRateo = false;
                this.showMessageErrorModRateo = true;  
            } 
        );
    }

    salvaRateo(rateoRowItemTable: RateoRowItemTable){
        let rateoVO: RateoVO = new RateoVO(rateoRowItemTable.idRateo, rateoRowItemTable.idAnag, rateoRowItemTable.importo, 
                                           rateoRowItemTable.mese, this.provinciaSelected.id, rateoRowItemTable.annualita);
        
        this.loaderDT = true;
        if(rateoRowItemTable.idRateo == null && 
           rateoRowItemTable.idRateo == undefined && 
           rateoRowItemTable.idRateo != 0)
        {
            //INSERIMENTO NUOVO RATEO
            this.inserisciNuovoRateo(rateoVO);
        } else {
            //MODIFICA RATEO
            this.modificaRateo(rateoVO);
        }
    }

    annullaCancellaRateo(){
        this.idRateoDaCancellare = null;
    }

    visualizzaDialogBoxConfernmaCancellazione(rateoRowItemTable: RateoRowItemTable){
        this.idRateoDaCancellare = rateoRowItemTable.idRateo;
        $('#dialogBoxRateo').modal('show');
    }

}