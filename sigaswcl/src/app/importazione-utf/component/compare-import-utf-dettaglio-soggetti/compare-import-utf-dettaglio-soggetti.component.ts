import { AfterViewInit, 
         Component, 
         Input, 
         OnDestroy, 
         OnInit, 
         QueryList, 
         Renderer2, 
         ViewChild,
         ViewChildren} from "@angular/core";
import { Router } from "@angular/router";
import { Subject } from "rxjs";

import { DestroySubscribers } from "../../../core/commons/decorator/destroy-subscribers";
import { ImportazioneUTFService } from "../../service/importazione-utf.service";
import { LoggerService } from "../../../core/services/logger.service";
import { AnagraficaSoggettiService } from "../../../anagrafica-soggetti/service/anagrafica-soggetti.service";
import { LuoghiService } from "../../../shared/service/luoghi-service";
import { UtilityService } from "../../../core/services/utility/utility.service";
import { ConsumiPrVO } from "../../../commons/vo/consumi-pr-vo";
import { DataTableIt } from '../../../commons/class/commons-data-table';
import { DataTableDirective } from "angular-datatables/src/angular-datatables.directive";
import { ReportUTFSoggettiMacroVO } from '../../../commons/vo/report-utf-soggetti-macro-vo';
import { ProvinceVO } from "../../../commons/vo/luoghi-vo/province-vo";
import { ImportUTFVO } from "../../../commons/vo/import-utf-vo";
import { ScartoVO } from "../../../commons/vo/scarto-vo";
import { AliquotaVO } from "../../../commons/vo/aliquota-vo";
import { ComuniVO } from "src/app/commons/vo/luoghi-vo/comuni-vo";
import { resolveDep } from "@angular/core/src/view/provider";


@Component({
    selector: 'app-compare-import-utf-dettaglio-soggetti',
    templateUrl: './compare-import-utf-dettaglio-soggetti.component.html',
    styleUrls: ['./compare-import-utf-dettaglio-soggetti.component.scss']
})  
@DestroySubscribers()
export class CompareImportUTFDettaglioSoggettiComponent implements OnInit, AfterViewInit, OnDestroy 
{
    @ViewChildren(DataTableDirective)
    dtElements: QueryList<DataTableDirective>;    

    private idImportUltimoCaricato: number;    
    private idImportSelezionato: number;

    private showMessageInfo: boolean = false;
    private messageInfo: string = "";
    private showMessageError: boolean = false;
    private messageError: string = "";
    private showMessageSuccess: boolean = false;
    private messageSuccess: string = "";
    private loaderComparazioniSoggettoPage: boolean = false;
    private loaderDT: boolean = false;    

    private reportUTFSoggettiMacroByIdReportList: Array<ReportUTFSoggettiMacroVO>;   
    private dtOptions: any;  
    private dtTrigger: Subject<any> = new Subject();

    public subscribers: any = {};
    private showImportSelezionato: boolean = false;

    private importAttualeConsumiPrList: Array<ConsumiPrVO>;
    private importAttualeConsumiPrViewContainer: ConsumiPrVO;
    private importAttualeElencoProvince: Array<ProvinceVO> = [];
    private loaderImportAttualeElencoProvince: boolean = false;
    private loaderImportAttualeItems: boolean = false;
    private isProvinciaAttualeTOTALE: boolean = true;

    private importSelezionatoConsumiPrList: Array<ConsumiPrVO>;
    private importSelezionatoConsumiPrViewContainer: ConsumiPrVO;
    private importSelezionatoElencoProvince: Array<ProvinceVO> = [];    
    private loaderImportSelezionatoItems: boolean = false;
    private loaderImportSelezionatoElencoProvince: boolean = false
    private isProvinciaSelezionataTOTALE: boolean = true;

    private listaDichiarazioniPresenti: Array<ImportUTFVO>;
    private loaderDichiarazioniPresenti: boolean = false;    
    private dichirazionePresenteSelezionata: ImportUTFVO;
    private visualizzaSezioneReport: Boolean = false;
    private visualizzaPulsantiera: Boolean = false;
    private ultimoReportCaricatoPerAnno: ImportUTFVO;
    private annualitaSelezionata: Number;

    private listaScarti: Array<ScartoVO>;
    private sommaScarti: number;   
    private scarto: ScartoVO;
    private listaAliquote: Array<AliquotaVO>;
    
    private disabilitaPulsanteSaveINS: boolean = true;
    private disabilitaPulsanteSaveUPD: boolean = true;
    private disabilitaPulsanteSaveALL: boolean = true;

    private visualizzaMainSpinnerModaleDettaglioSoggetto: boolean = false;
    private visualizzaMainSpinnerModaleConfrontaSoggetto: boolean = false;

    private idAnagraficaSoggettoDaModificare: number;
    private idAnagraficaSoggettoDaInserire: number;

    private denominazioneSoggettoInAnagrafica: string;
    private codiceAziendaSoggettoInAnagrafica: string;
    private indirizzoSoggettoInAnagrafica: string;
    private comuneSoggettoInAnagrafica: ComuniVO;
    private provinciaSoggettoInAnagrafica: ProvinceVO;
    private loaderDenominazioneSoggettoInAnag: boolean;

    public checkVisualizzaSelectElencoImport: boolean = false;
    
    private elencoPronvince: Array<ProvinceVO>;
    private comuneImportAttuale: ComuniVO;
    private provinciaImportAttuale: ProvinceVO;
    
   /********************************************************************
    * Costruttore
    *********************************************************************/
   constructor(
        private router: Router,
        private logger: LoggerService,
        private anagraficaSoggettiService: AnagraficaSoggettiService,
        private importazioneUTFService: ImportazioneUTFService,
        private luoghiService:LuoghiService,
        private utilityService: UtilityService, 
        private renderer: Renderer2
    ) {       

        this.importazioneUTFService
            .importUTFAnnualitaSelezionataSubjectObservable
            .subscribe((annualita: Number) => {
                this.annualitaSelezionata = annualita;
                this.showMessageError = false;
                this.showMessageSuccess = false;
                this.visualizzaSezioneReport = false;
                this.visualizzaPulsantiera = false;
                this.idImportUltimoCaricato = null;
                this.idImportSelezionato = null;
                this._caricaElencoDichirazioniPresentiAnno(annualita);
                this._caricaAliquote(this.annualitaSelezionata);
                this._caricaElencoProvince();
            });

        this.importazioneUTFService.importUTFUnicoImportPerAnnualitaSubjectObservable
            .subscribe((idImport: number) => {                
                this.visualizzaSezioneReport = true;
                this._caricaTabellaElencoSoggettiDettagli();
            });

    }
    
   /********************************************************************
    * Funzioni associate ad Eventi
    *********************************************************************/  

    ngOnInit(): void {      
        
        this._inizializzaForm();
        this._setupTabellaElencoSoggettiDettagli(); 
        //this._caricaTabellaElencoSoggettiDettagli();             
    }

    ngOnDestroy(): void {
        //TO DO
    }

    ngAfterViewInit(): void {
        //TO DO
    }

    showCompareSoggetti(idAnag: number, tipoElaborazione: String) {
        this.showImportSelezionato = true;
        this.visualizzaMainSpinnerModaleDettaglioSoggetto = true;
        this.visualizzaMainSpinnerModaleConfrontaSoggetto = true;        
        this.importAttualeConsumiPrList = null;
        this.importAttualeConsumiPrViewContainer = null;
        this.importSelezionatoConsumiPrList = null;
        this.importSelezionatoConsumiPrViewContainer = null;
        this.showMessageError = false;

        //confronot anagrafica
        this.denominazioneSoggettoInAnagrafica = null;
        this.codiceAziendaSoggettoInAnagrafica = null;
        this.indirizzoSoggettoInAnagrafica = null;
        this.comuneSoggettoInAnagrafica = null;
        this.provinciaSoggettoInAnagrafica = null;

        this.loaderImportAttualeItems = true;
        this.loaderImportAttualeElencoProvince = true;        
        this.importazioneUTFService.ricercaReportUTFDettaglioSoggettiByIdImportIdAnag(this.idImportUltimoCaricato, idAnag).subscribe(
            resp => {
                console.log("ricercaReportUTFDettaglioSoggettiByIdImportIdAnag response", resp);
                
                this.importAttualeConsumiPrList = resp;
                this._inizializzaElencoProvincieImportAttuale(this.importAttualeConsumiPrList);
                this.isProvinciaAttualeTOTALE = true;
                this._popolaContainerVisualizzazioneImportAttuale(this.importAttualeConsumiPrList,"TOTALE");                
                this._ricercaSoggettoInAnagrafica(idAnag);
                this._determinaProvinciaEComuneDelSoggetto(this.importAttualeConsumiPrList[0].anagraficaSoggettoVO.idProvincia,
                                                           this.importAttualeConsumiPrList[0].anagraficaSoggettoVO.idComune);                
                this.loaderImportAttualeItems = false;
                this.loaderImportAttualeElencoProvince = false;
                this.visualizzaMainSpinnerModaleDettaglioSoggetto = false;
                this.visualizzaMainSpinnerModaleConfrontaSoggetto = false;
            },
            err => {
                this.messageError="Si è verificato un errore in fase di caricamento informazioni relative all'import selezionato.";
                this.showMessageError = true;
                this.loaderImportAttualeItems = false;
                this.loaderImportAttualeElencoProvince = false;
                this.visualizzaMainSpinnerModaleDettaglioSoggetto = false;
                this.visualizzaMainSpinnerModaleConfrontaSoggetto = false;
            }
        );
        if(this.idImportSelezionato!=null && this.idImportSelezionato!=undefined){
            this.loaderImportSelezionatoItems = true;
            this.loaderImportSelezionatoElencoProvince = true;
            this.importazioneUTFService.ricercaReportUTFDettaglioSoggettiByIdImportIdAnag(this.idImportSelezionato, idAnag).subscribe(
                resp => {                
                    this.importSelezionatoConsumiPrList = resp;
                    this._inizializzaElencoProvincieImportSelezionato(this.importSelezionatoConsumiPrList);
                    this.isProvinciaSelezionataTOTALE = true;
                    this._popolaContainerVisualizzazioneImportSelezionato(this.importSelezionatoConsumiPrList,"TOTALE");               
                    this.loaderImportSelezionatoItems = false;
                    this.loaderImportSelezionatoElencoProvince = false;
                },
                err => {
                    this.messageError="Si è verificato un errore in fase di caricamento informazioni relative all'import selezionato.";
                    this.showMessageError = true;
                    this.loaderImportSelezionatoItems = false;
                    this.loaderImportSelezionatoElencoProvince = false;
                }
            );
        }        
    }

    selectDichiarazione(dichiarazione: ImportUTFVO){        
        this.showMessageError = false;
        this.showMessageSuccess = false;
        this.idImportSelezionato = this.dichirazionePresenteSelezionata.importId;        

        this.visualizzaSezioneReport = true;
        this._caricaTabellaElencoSoggettiDettagli();
    }

    onCambiaProvinciaImportAttuale(siglaProvincia: String) {

        if(this.importAttualeConsumiPrList!=null && this.importAttualeConsumiPrList!= undefined)
        { 
            this.loaderImportAttualeItems = true;

            if(siglaProvincia!="TOTALE")
            {
                this.isProvinciaAttualeTOTALE = false;
                let importAttualeConsummiPrFiltarti: ConsumiPrVO[] = this.importAttualeConsumiPrList
                                                                         .filter((consumiPr) => consumiPr.provincia_erogazione == siglaProvincia);
                this._popolaContainerVisualizzazioneImportAttuale(importAttualeConsummiPrFiltarti, siglaProvincia);
            } else {
                this.isProvinciaAttualeTOTALE = true;
                this._popolaContainerVisualizzazioneImportAttuale(this.importAttualeConsumiPrList, "TOTALE");
            }     

            setTimeout(() => {
                this.loaderImportAttualeItems = false;
            }, 500);
            
        }
    }

    onCambiaProvinciaImportSelezionato(siglaProvincia: String) {

        if(this.importSelezionatoConsumiPrList!=null && this.importSelezionatoConsumiPrList!= undefined)
        { 
            this.loaderImportSelezionatoItems = true;

            if(siglaProvincia!="TOTALE")
            {
                this.isProvinciaSelezionataTOTALE = false;
                let importSelezionatoConsummiPrFiltarti: ConsumiPrVO[] = this.importSelezionatoConsumiPrList
                                                                         .filter((consumiPr) => consumiPr.provincia_erogazione == siglaProvincia);
                this._popolaContainerVisualizzazioneImportSelezionato(importSelezionatoConsummiPrFiltarti, siglaProvincia);
            } else {
                this.isProvinciaSelezionataTOTALE = true;
                this._popolaContainerVisualizzazioneImportSelezionato(this.importSelezionatoConsumiPrList, "TOTALE");
            }     

            setTimeout(() => {
                this.loaderImportSelezionatoItems = false;
            }, 500);
            
        }
    }
    calculateClassesAnagrafica(value: string, value2: string): string{
        if(value == null || value2 == null) {
            return "";
        }
        if(value != value2)
        {
            return "bg-color-valore-diverso"
        } else {
            return "";
        }
    }

    calculateClasses(value: number, value2: number): String{
        
        if(this._round(value,2)!=this._round(value2,2))
        {
            return "bg-color-valore-diverso"
        } else {
            return "";
        }
    }

    annullaSaveSoloINS(){
        //NESSUNA AZIONE PREVISTA
    }

    confermaSaveSoloINS(){
        this.subscribers.confermaDichiarazioniINS = this.importazioneUTFService.confermaDichiarazioniUTF(this.annualitaSelezionata.toString(),
                                                                                                         this.idImportUltimoCaricato, 
                                                                                                         this.idImportSelezionato, 
                                                                                                         "INS")
        .subscribe(
            resp =>{                
                this.showMessageError = false;
                this.showMessageSuccess = true;
                this.messageSuccess = "Operazione conclusa con successo.";
                this._caricaTabellaElencoSoggettiDettagli();           
            },
            err => {                                
                this.messageError="Si è verificato un errore in fase di conferma delle informazioni relative all'import selezionato.";
                this.showMessageError = true;
                this.showMessageSuccess = false;
            }

        );
    }

    annullaSaveAll(){
        //NESSUNA AZIONE PREVISTS
    }

    confermaSaveAll(){
        this.subscribers.confermaSaveAll = this.importazioneUTFService.confermaDichiarazioniUTF(this.annualitaSelezionata.toString(),
                                                                                                this.idImportUltimoCaricato, 
                                                                                                this.idImportSelezionato, 
                                                                                                "ALL")
        .subscribe(
            resp =>{                
                this.showMessageError = false;
                this.showMessageSuccess = true;
                this.messageSuccess = "Operazione conclusa con successo.";
                this._caricaTabellaElencoSoggettiDettagli();           
            },
            err => {                                
                this.messageError="Si è verificato un errore in fase di conferma delle informazioni relative all'import selezionato.";
                this.showMessageError = true;
                this.showMessageSuccess = false;
            }

        );
    }

    annullaSaveSoloUPD(){
        //NESSUNA AZIONE RICHIESTA
    }

    confermaSaveSoloUPD(){
        this.subscribers.confermaSaveSoloUPD = this.importazioneUTFService.confermaDichiarazioniUTF(this.annualitaSelezionata.toString(),
                                                                                                    this.idImportUltimoCaricato, 
                                                                                                    this.idImportSelezionato, 
                                                                                                    "UPD")
        .subscribe(
            resp =>{                
                this.showMessageError = false;
                this.showMessageSuccess = true;
                this.messageSuccess = "Operazione conclusa con successo.";
                this._caricaTabellaElencoSoggettiDettagli();           
            },
            err => {                                
                this.messageError="Si è verificato un errore in fase di conferma delle informazioni relative all'import selezionato.";
                this.showMessageError = true;
                this.showMessageSuccess = false;
            }

        );
    }

    annullaSaveUnicoINS(){
        //TO DO
    }

    confermaSaveUnicoINS(){
        this.subscribers.confermaSaveUnicoINS = this.importazioneUTFService.confermaSoggettoDichiarazioniUTF(this.annualitaSelezionata.toString(),
                                                                                                             this.idImportUltimoCaricato, 
                                                                                                             this.idAnagraficaSoggettoDaInserire, 
                                                                                                             "INS")
        .subscribe(
            resp =>{                
                this.showMessageError = false;
                this.showMessageSuccess = true;
                this.messageSuccess = "Operazione conclusa con successo.";
                this._caricaTabellaElencoSoggettiDettagli();           
            },
            err => {                                
                this.messageError="Si è verificato un errore in fase di conferma delle informazioni relative all'import selezionato.";
                this.showMessageError = true;
                this.showMessageSuccess = false;
            }

        );
    }

    annullaSaveUnicoUPD(){
        //TO DO
    }

    confermaSaveUnicoUPD(){
        this.subscribers.confermaSaveUnicoUPD = this.importazioneUTFService.confermaSoggettoDichiarazioniUTF(this.annualitaSelezionata.toString(),
                                                                                                                    this.idImportUltimoCaricato, 
                                                                                                                    this.idAnagraficaSoggettoDaModificare, 
                                                                                                                    "UPD")
        .subscribe(
            resp =>{                
                this.showMessageError = false;
                this.showMessageSuccess = true;
                this.messageSuccess = "Operazione conclusa con successo.";
                this._caricaTabellaElencoSoggettiDettagli();           
            },
            err => {                                
                this.messageError="Si è verificato un errore in fase di conferma delle informazioni relative all'import selezionato.";
                this.showMessageError = true;
                this.showMessageSuccess = false;
            }

        );
    }

    settaDatiSoggettoDaConfermare(idAnag: number, tipoElaborazione: String){
        if(tipoElaborazione=="UPD"){
            this.idAnagraficaSoggettoDaModificare = idAnag;
        } else {
            this.idAnagraficaSoggettoDaInserire = idAnag;
        }
    }

   /********************************************************************
    * Funzioni private
    *********************************************************************/

   private _determinaProvinciaEComuneDelSoggetto(idProvincia: number, idComune :number)
   {
        this.showMessageError = false;
        this.showMessageSuccess = false;

        this.provinciaImportAttuale = this.elencoPronvince.filter(item =>item.id === idProvincia)[0];
        this.luoghiService.getComuneByIdProvincia(idProvincia).subscribe(resp => {
            let comuni: ComuniVO[] = resp;
            this.comuneImportAttuale = comuni.filter(item => item.id === idComune)[0];
            let marts = comuni.filter(item => item.id === idComune)[0];                       
        },
        err => {
            this.messageError="Si è verificato un errore in fase di comume del soggetto selezionato.";
            this.showMessageError = true;
            this.showMessageSuccess = false;
        })
   }

   private _ricercaSoggettoInAnagrafica(idAnag : number) 
   {        
        this.loaderDenominazioneSoggettoInAnag = true;            
        this.anagraficaSoggettiService.ricercaSoggettoByIdAnag(idAnag).subscribe(
            resp => {
                console.log("resp sogg anag", resp);
                if(resp.idAnag == null || resp.idAnag == undefined){
                    this.loaderDenominazioneSoggettoInAnag = false;
                } else {
                    this.luoghiService.getAllProvince().subscribe(provs => {
                        let province: ProvinceVO[] = provs;
                        this.provinciaSoggettoInAnagrafica = province.filter(item =>item.id === resp.idProvincia)[0];
                        this.luoghiService.getComuneByIdProvincia(resp.idProvincia).subscribe(coms => {
                            let comuni: ComuniVO[] = coms;
                            this.comuneSoggettoInAnagrafica = comuni.filter(item => item.id === resp.idComune)[0];
                            this.denominazioneSoggettoInAnagrafica = resp.denominazione;
                            this.codiceAziendaSoggettoInAnagrafica = resp.codiceAzienda;
                            this.indirizzoSoggettoInAnagrafica = resp.indirizzo;
                            this.loaderDenominazioneSoggettoInAnag = false;
                        },
                        err => {
                            this.loaderDenominazioneSoggettoInAnag = false;
                        })
                    },
                    err => {
                        this.loaderDenominazioneSoggettoInAnag = false;
                    });

                }                
            },
            err => {
                this.loaderDenominazioneSoggettoInAnag = false;
            }
        );                
   }

   private _round(num: number, fractionDigits: number): number {
    //return Number(num.toFixed(fractionDigits));
    return +Number(Math.round((num + Number.EPSILON) * 100) / 100).toFixed(fractionDigits);
   }

   private _caricaElencoProvince(){
    this.luoghiService.getAllProvince().subscribe(
        resp => {
            this.elencoPronvince = resp;
        },
        err => {
            this.messageError="Si è verificato un errore in fase di caricamento elenco province.";
            this.showMessageError = true;
        }
    );
   }

   private _inizializzaForm(){
        this.importAttualeConsumiPrList = null;
        this.importAttualeConsumiPrViewContainer = null;
        this.importSelezionatoConsumiPrList = null;
        this.importSelezionatoConsumiPrViewContainer = null;
        
        this.luoghiService.getAllProvince().subscribe(
            resp => {
                this.elencoPronvince = resp;
            },
            err => {
                this.messageError="Si è verificato un errore in fase di caricamento elenco province.";
                this.showMessageError = true;
            }
        );
   }

   private _setupTabellaElencoSoggettiDettagli(){
        this.dtOptions = {            
            destroy: true,
            head: 20,
            pagingType: 'full_numbers',
            pageLength: 10,
            processing: true,
            language: DataTableIt.language,
            responsive: true,
            searching: true,
            paging: true,
            //ordering: true,
            order: [[1, 'asc']],
            columnDefs: [
                { width: '3%', targets: 0, orderable: false },
                { width: '10%', targets: 5 },
                { width: '5%', targets: 6, orderable: false },          
            ]        
        };
   }

   private _caricaDatiImportSelezionato()
   {
        this.subscribers.ricercaReportSelezionato = this.importazioneUTFService
                                                        .ricercaReportUTFDettaglioSoggetti(this.idImportSelezionato)
                                                        .subscribe(
            resp =>{            
                this.importSelezionatoConsumiPrList = resp;
                this.showMessageError = false;           
            },
            err => {
            this.messageError="Si è verificato un errore in fase di caricamento informazioni relative all'import selezionato.";
            this.showMessageError = true;
            }
        );  
   }

   private _caricaTabellaElencoSoggettiDettagli()
   {
        this.loaderDT = true;
        this.visualizzaPulsantiera = false;
        this.disabilitaPulsanteSaveINS = true;
        this.disabilitaPulsanteSaveUPD = true;
        this.disabilitaPulsanteSaveALL = true;
        this.reportUTFSoggettiMacroByIdReportList = [];
        this._rerenderTable();      
        this.subscribers.ricercaElencoSoggettiDettagli = this.importazioneUTFService
                                                            .ricercaReportUTFSoggettiMacroByIdReport(this.idImportUltimoCaricato, 
                                                                                                     this.idImportSelezionato,
                                                                                                     this.annualitaSelezionata)
                                                            .subscribe(
            resp =>{            
                this.reportUTFSoggettiMacroByIdReportList = resp;
                /*
                if(this.reportUTFSoggettiMacroByIdReportList != null && 
                   this.reportUTFSoggettiMacroByIdReportList != undefined && 
                   this.reportUTFSoggettiMacroByIdReportList.length > 0)
                {
                    this.disabilitaPulsanteSaveALL = false;
                }
                */    
                this.showMessageError = false;                                
                this._rerenderTable();               
                
                setTimeout(() => {                    
                    this.loaderDT = false;
                    this.visualizzaPulsantiera = true;
                    this.disabilitaPulsanteSaveINS = this._disabilitaPulsanteSave("INS", this.reportUTFSoggettiMacroByIdReportList);
                    this.disabilitaPulsanteSaveUPD = this._disabilitaPulsanteSave("UPD", this.reportUTFSoggettiMacroByIdReportList);                    
                    this.disabilitaPulsanteSaveALL =this._disabilitaPulsanteSaveAll(this.reportUTFSoggettiMacroByIdReportList);
                },1000);           
            },
            err => {                
                this.loaderDT = true;
                this.messageError="Si è verificato un errore in fase di caricamento informazioni relative all'import selezionato.";
                this.showMessageError = true;
            }
        );  
   }
   
   private _rerenderTable(): void 
   {        
        this.dtElements.forEach((dtElement: DataTableDirective) => {
            if (dtElement != null && dtElement.dtInstance != null) {
                dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
                  dtInstance.destroy();
                  this.dtTrigger.next();
                });
            } else {                
                this.dtTrigger.next();                
            }
        });
   }

   private _verificaPresenzaItemPerTipoElaborazione(tipoElaborazione: String, listaItem : ReportUTFSoggettiMacroVO[]): boolean 
   {
        if(listaItem==null || listaItem==undefined)
        {
            return false;
        }

        let item = listaItem.filter((item) => (item.azione == tipoElaborazione && item.selectedImport == false));
        if(item != null && item != undefined && item.length > 0)
        {
            return true;
        }

        return false;
   }

   private _popolaContainerVisualizzazioneImportAttuale(importConsummiPrList: ConsumiPrVO[], provincia: String)
    {
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

            let rettifiche = importConsummiPrList
                            .reduce((acc, val) => acc + val.rettifiche, 0);

            let arrotondamenti = importConsummiPrList
                                .reduce((acc, val) => acc + val.arrotondamenti, 0);
            
            
            let listaScart = [];
            if(provincia!="TOTALE")
            {
                listaScart = importConsummiPrList[0].listaScarti;
            }           

            let consumoPr: ConsumiPrVO = {  id_consumi: 0,
                                            annualita: 0,
                                            user_import: '',
                                            provincia_erogazione: 'Totali',
                                            data_presentazione: '',
                                            stato_dich: '',
                                            usi_industriali_1200: usi_industriali_1200,
                                            usi_industriali_up: usi_industriali_up,
                                            usi_civili_120: usi_civili_120,
                                            usi_civili_480: usi_civili_480,
                                            usi_civili_1560: usi_civili_1560,
                                            usi_civili_up: usi_civili_up,
                                            tot_nuovi_allacciamenti: tot_nuovi_allacciamenti,
                                            tot_industriali: tot_industriali,
                                            tot_civili: tot_civili,
                                            rettifiche: rettifiche,
                                            arrotondamenti: arrotondamenti,
                                            rateo_dich: rateo_dich,
                                            conguaglio_dich: conguaglio_dich, 
                                            conguaglio_calc: 0,
                                            addizionale_liquidata: 0,
                                            totaleCalcolato: 0,
                                            totaleDich: totaleDich,
                                            totaleDichOrigine: 0,
                                            nuoviAllacciamenti: null,
                                            concilia: false,
                                            scarti: false,
                                            note: '',
                                            compensazione: 0,
                                            conguaglio_dich_prec: 0,
                                            rateo_dich_prec: 0,
                                            totaleVersato: 0,
                                            compensazionePrVO: null,
                                            anagraficaSoggettoVO: importConsummiPrList[0].anagraficaSoggettoVO,
                                            listaScarti: listaScart,
                                        };
            this.importAttualeConsumiPrViewContainer = Object.assign({}, consumoPr);            

        }       

   }

   private _popolaContainerVisualizzazioneImportSelezionato(importConsummiPrList: ConsumiPrVO[], provincia: String)
    {
        if(importConsummiPrList!=null && importConsummiPrList!=undefined  && importConsummiPrList.length > 0)
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

            let rettifiche = importConsummiPrList
                            .reduce((acc, val) => acc + val.rettifiche, 0);

            let arrotondamenti = importConsummiPrList
                                .reduce((acc, val) => acc + val.arrotondamenti, 0);

            let listaScart = [];
            if(provincia!="TOTALE")
            {
                listaScart = importConsummiPrList[0].listaScarti;
            }

            let consumoPr: ConsumiPrVO = {  id_consumi: 0,
                                            annualita: 0,
                                            user_import: '',
                                            provincia_erogazione: 'Totali',
                                            data_presentazione: '',
                                            stato_dich: '',
                                            usi_industriali_1200: usi_industriali_1200,
                                            usi_industriali_up: usi_industriali_up,
                                            usi_civili_120: usi_civili_120,
                                            usi_civili_480: usi_civili_480,
                                            usi_civili_1560: usi_civili_1560,
                                            usi_civili_up: usi_civili_up,
                                            tot_nuovi_allacciamenti: tot_nuovi_allacciamenti,
                                            tot_industriali: tot_industriali,
                                            tot_civili: tot_civili,
                                            rettifiche: rettifiche,
                                            arrotondamenti: arrotondamenti,
                                            rateo_dich: rateo_dich,
                                            conguaglio_dich: conguaglio_dich, 
                                            conguaglio_calc: 0,
                                            addizionale_liquidata: 0,
                                            totaleCalcolato: 0,
                                            totaleDich: totaleDich,
                                            totaleDichOrigine: 0,
                                            nuoviAllacciamenti: null,
                                            concilia: false,
                                            scarti: false,
                                            note: '',
                                            compensazione: 0,
                                            conguaglio_dich_prec: 0,
                                            rateo_dich_prec: 0,
                                            totaleVersato: 0,
                                            compensazionePrVO: null,
                                            anagraficaSoggettoVO: importConsummiPrList[0].anagraficaSoggettoVO,
                                            listaScarti: listaScart,
                                        };
            this.importSelezionatoConsumiPrViewContainer = Object.assign({}, consumoPr);           

        }       

   }

   private _inizializzaElencoProvincieImportAttuale(importAttualeConsumiPrList: Array<ConsumiPrVO>) 
    {       
        let elencoProvince = importAttualeConsumiPrList.map((consumiPr) => new ProvinceVO(0,'',consumiPr.provincia_erogazione));
        this.importAttualeElencoProvince =  elencoProvince.reduce((r, a) => r.some(x => x.sigla == a.sigla) ? r : [...r, a], []);
        this.importAttualeElencoProvince = this.importAttualeElencoProvince.sort((a,b) => (a.sigla > b.sigla) ? 1 : ((b.sigla > a.sigla) ? -1 : 0));
        this.loaderImportAttualeElencoProvince = false;
   }

   private _inizializzaElencoProvincieImportSelezionato(importSelezionatoConsumiPrList: Array<ConsumiPrVO>) 
    {        
        let elencoProvince = importSelezionatoConsumiPrList.map((consumiPr) => new ProvinceVO(0,'',consumiPr.provincia_erogazione));       
        this.importSelezionatoElencoProvince = elencoProvince.reduce((r, a) => r.some(x => x.sigla == a.sigla) ? r : [...r, a], []);
        this.importSelezionatoElencoProvince = this.importSelezionatoElencoProvince.sort((a,b) => (a.sigla > b.sigla) ? 1 : ((b.sigla > a.sigla) ? -1 : 0));
        this.loaderImportSelezionatoElencoProvince = false;
   }

   private _caricaElencoDichirazioniPresentiAnno(anno:Number) {
        this.loaderDichiarazioniPresenti = true;
        this.subscribers.elencoDichiarazioni = this.importazioneUTFService.elencoReportUTFByAnno(anno.toString()).subscribe(
          data => {            
            this.listaDichiarazioniPresenti = [];
            let tempDichiarazioniPresenti: ImportUTFVO[] = data.filter(importUtf => importUtf.esito == 4 );   
            this.listaDichiarazioniPresenti = tempDichiarazioniPresenti;
            if(this.listaDichiarazioniPresenti!=null && this.listaDichiarazioniPresenti!=undefined){
                this.listaDichiarazioniPresenti = this.listaDichiarazioniPresenti.sort((a,b) => (a.importId > b.importId) ? 1 : ((b.importId < a.importId) ? -1 : 0));
                this.idImportUltimoCaricato = this.listaDichiarazioniPresenti[0].importId;                
                this.ultimoReportCaricatoPerAnno = this.listaDichiarazioniPresenti[0];

                if(this.listaDichiarazioniPresenti.length == 1){
                    this.importazioneUTFService.emitImportUTFUnicoImportPerAnnualita(this.idImportUltimoCaricato);
                    this.checkVisualizzaSelectElencoImport = false;
                } else {
                    this.listaDichiarazioniPresenti = this.listaDichiarazioniPresenti.filter(item => item.importId != this.idImportUltimoCaricato);
                    this.checkVisualizzaSelectElencoImport = true;
                }
            }            

            this.loaderDichiarazioniPresenti = false;
          }, err => {
              this.logger.error(err);
              this.messageError = err.message;
              this.loaderDichiarazioniPresenti = false;
          }
        );
   }

   private _caricaAliquote(annualita: Number) 
   {
        this.importazioneUTFService.getAllAliquote(annualita).subscribe(resp => 
            {
                this.listaAliquote = resp;
            }, 
            err => {
                this.logger.error(err);
            }
        );
   }

   private _disabilitaPulsanteSave(tipoElaborazione: String, listaItem : ReportUTFSoggettiMacroVO[]): boolean
   {
        return !this._verificaPresenzaItemPerTipoElaborazione(tipoElaborazione, this.reportUTFSoggettiMacroByIdReportList);
   }
   
   private _disabilitaPulsanteSaveAll(listaItem : ReportUTFSoggettiMacroVO[]): boolean {

        if(listaItem==null || listaItem==undefined)
        {
            return true;
        }

        let item = listaItem.filter((item) => item.selectedImport == false);
        if(item != null && item != undefined && item.length > 0)
        {
            return false;
        }

        return true;

   }

  /********************************************************************
   * Funzioni pubbliche
   *********************************************************************/
   public getAliquota(id: number): number 
   {
        let aliquota: number;
        if(this.listaAliquote!=null && this.listaAliquote!=undefined)
        {
            this.listaAliquote.forEach(el => {
                if (el.tipoAliquote.idTipoAliquota === id) {
                    aliquota = el.aliquota;
                }
            });
        }   
        return aliquota;
   }

}