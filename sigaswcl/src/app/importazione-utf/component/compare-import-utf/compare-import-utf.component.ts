import { AfterViewInit, Component, Input, OnDestroy, OnInit, Renderer2, SimpleChanges } from "@angular/core";
import { DestroySubscribers } from "../../../core/commons/decorator/destroy-subscribers";
import { Router } from "@angular/router";
import { LoggerService } from "../../../core/services/logger.service";
import { AnagraficaSoggettiService } from "../../../anagrafica-soggetti/service/anagrafica-soggetti.service";
import { LuoghiService } from "../../../shared/service/luoghi-service";
import { UtilityService } from "../../../core/services/utility/utility.service";
import { ConsumiPrVO } from "../../../commons/vo/consumi-pr-vo";
import { ImportazioneUTFService } from "../../service/importazione-utf.service";
import { ProvinceVO } from "../../../commons/vo/luoghi-vo/province-vo";
import { Observable, Subject, Subscription } from "rxjs";
import { ImportUTFVO } from "../../../commons/vo/import-utf-vo";

@Component({
    selector: 'app-compare-import-utf',
    templateUrl: './compare-import-utf.component.html',
    styleUrls: ['./compare-import-utf.component.scss']
})  
@DestroySubscribers()
export class CompareImportUTFComponent implements OnInit, AfterViewInit, OnDestroy 
{

    /*
    @Input() idImportAttuale: number;    
    @Input() idImportSelezionato: number; 
    */   

    /*
    @Input() importAttualeSelected: boolean
    @Input() importSelezionatoSelected: boolean
    */

    private importAttualeSelected: boolean
    private importSelezionatoSelected: boolean
    private idImportAttuale: number;    
    private idImportSelezionato: number;

    public subscribers: any = {};

    private eventidImportSelezionatoSubscription: Subscription;
    private showMessageInfo: boolean = false;
    private messageInfo: string = "";
    private showMessageError: boolean = false;
    private messageError: string = "";
    private showMessageSuccess: boolean = false;
    private messageSuccess: string = "";
    private loaderComparazioniSoggettoPage: boolean = false;
    private loaderDT: boolean = false;

    private importAttualeConsumiPrList: Array<ConsumiPrVO>;
    private importAttualeConsumiPrViewContainer: ConsumiPrVO;
    private importAttualeElencoProvince: Array<ProvinceVO> = [];
    private loaderImportAttualeElencoProvince: boolean = false;
    private loaderImportAttualeItems: boolean = false;

    private importSelezionatoConsumiPrList: Array<ConsumiPrVO>;
    private importSelezionatoConsumiPrViewContainer: ConsumiPrVO;
    private importSelezionatoElencoProvince: Array<ProvinceVO> = [];    
    private loaderImportSelezionatoItems: boolean = false;
    private loaderImportSelezionatoElencoProvince: boolean = false

    private showImportSelezionato: boolean = false;

    private annualitaSelezionata: Number;
    private listaDichiarazioniPresenti: Array<ImportUTFVO>;
    private loaderDichiarazioniPresenti: boolean = false;
    private ultimoReportCaricatoPerAnno: ImportUTFVO;
    private dichirazionePresenteSelezionata: ImportUTFVO;
    private visualizzaSezioneReport: Boolean = false;

    public checkVisualizzaSelectElencoImport: boolean = false;
    

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

        this.importazioneUTFService.importUTFAnnualitaSelezionataSubjectObservable
                                   .subscribe((annualita: Number) => {
                                        this.visualizzaSezioneReport = false;
                                        this.annualitaSelezionata = annualita;
                                        this._caricaElencoDichirazioniPresentiAnno(annualita);
                                   });
        
        this.importazioneUTFService.importUTFUnicoImportPerAnnualitaSubjectObservable
                                   .subscribe((idImport: number) => {
                                       this.visualizzaSezioneReport = true;
                                       this.idImportAttuale = idImport;                                       
                                       this.loaderImportAttualeElencoProvince = true;
                                       this._inzializzaImportAttualePrViewContainer(this.idImportAttuale, this.annualitaSelezionata);   
                                       
                                   });
    }       

   /********************************************************************
    * Funzioni associate ad Eventi
    *********************************************************************/

    ngOnInit(): void {
        /*            
        if(this.idImportAttuale != null) {
            this.loaderImportAttualeElencoProvince = true;
            this._inzializzaImportAttualePrViewContainer(this.idImportAttuale);   
        }
        */          
    }

    ngAfterViewInit(): void {
        // TO DO
    }

    ngOnChanges(changes: SimpleChanges) {        
        this.showImportSelezionato = true;

        if(this.importAttualeConsumiPrList == null || this.importAttualeConsumiPrList == undefined ) {
            this.loaderImportAttualeElencoProvince = true;
            this._inzializzaImportAttualePrViewContainer(this.idImportAttuale, this.annualitaSelezionata);   
        }

        this.loaderImportSelezionatoElencoProvince = true;                    
        this._inizializzaImportSelezionatoPrViewContainer(this.idImportSelezionato, this.annualitaSelezionata);
    }

    ngOnDestroy(): void {
        //this.eventidImportSelezionatoSubscription.unsubscribe();
    }   

    selectDichiarazione(dichiarazione: ImportUTFVO){
        this.idImportSelezionato = this.dichirazionePresenteSelezionata.importId;
        this.visualizzaSezioneReport = true;
        this.showImportSelezionato = true;

        if(this.importAttualeConsumiPrList == null || this.importAttualeConsumiPrList == undefined ) {
            this.loaderImportAttualeElencoProvince = true;
            this._inzializzaImportAttualePrViewContainer(this.idImportAttuale, this.annualitaSelezionata);   
        }        

        this.loaderImportSelezionatoElencoProvince = true;                    
        this._inizializzaImportSelezionatoPrViewContainer(this.idImportSelezionato, this.annualitaSelezionata);
    }

    onCambiaProvinciaImportAttuale(siglaProvincia: String) {

        if(this.importAttualeConsumiPrList!=null && this.importAttualeConsumiPrList!= undefined)
        { 
            this.loaderImportAttualeItems = true;

            if(siglaProvincia!="TOTALE")
            {
                let importAttualeConsummiPrFiltarti: ConsumiPrVO[] = this.importAttualeConsumiPrList
                                                                         .filter((consumiPr) => consumiPr.provincia_erogazione == siglaProvincia);
                this._popolaContainerVisualizzazioneImportAttuale(importAttualeConsummiPrFiltarti);
            } else {
                this._popolaContainerVisualizzazioneImportAttuale(this.importAttualeConsumiPrList);
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
                let importSelezionatoConsummiPrFiltarti: ConsumiPrVO[] = this.importSelezionatoConsumiPrList
                                                                         .filter((consumiPr) => consumiPr.provincia_erogazione == siglaProvincia);
                this._popolaContainerVisualizzazioneImportSelezionato(importSelezionatoConsummiPrFiltarti);
            } else {
                this._popolaContainerVisualizzazioneImportSelezionato(this.importSelezionatoConsumiPrList);
            }     

            setTimeout(() => {
                this.loaderImportSelezionatoItems = false;
            }, 500);
            
        }
    }

    /********************************************************************
     * Funzioni private
     ********************************************************************/

    private _popolaContainerVisualizzazioneImportAttuale(importConsummiPrList: ConsumiPrVO[])
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
            
            this.importAttualeConsumiPrViewContainer = new ConsumiPrVO(0,0,'','','','',
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

    private _popolaContainerVisualizzazioneImportSelezionato(importConsummiPrList: ConsumiPrVO[])
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
            
            this.importSelezionatoConsumiPrViewContainer = new ConsumiPrVO(0,0,'','','','',
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

    private _inizializzaElencoProvincieImportAttuale(importAttualeConsumiPrList: Array<ConsumiPrVO>) {
        
        this.importAttualeElencoProvince = importAttualeConsumiPrList.map((consumiPr) => new ProvinceVO(0,'',consumiPr.provincia_erogazione));
        this.importAttualeElencoProvince = this.importAttualeElencoProvince.reduce((r, a) => r.some(x => x.sigla == a.sigla) ? r : [...r, a], []);
        this.importAttualeElencoProvince = this.importAttualeElencoProvince.sort((a,b) => (a.sigla > b.sigla) ? 1 : ((b.sigla > a.sigla) ? -1 : 0));
        this.loaderImportAttualeElencoProvince = false;
    }

    private _inizializzaElencoProvincieImportSelezionato(importSelezionatoConsumiPrList: Array<ConsumiPrVO>) {
        
        this.importSelezionatoElencoProvince = importSelezionatoConsumiPrList.map((consumiPr) => new ProvinceVO(0,'',consumiPr.provincia_erogazione));
        this.importSelezionatoElencoProvince = this.importSelezionatoElencoProvince.reduce((r, a) => r.some(x => x.sigla == a.sigla) ? r : [...r, a], []);
        this.importSelezionatoElencoProvince = this.importSelezionatoElencoProvince.sort((a,b) => (a.sigla > b.sigla) ? 1 : ((b.sigla > a.sigla) ? -1 : 0));
        this.loaderImportSelezionatoElencoProvince = false;
    }
    
    private _inizializzaImportSelezionatoPrViewContainer(idImport: number, annualita: Number) {
        this.loaderImportSelezionatoItems = true;         
        this.importSelezionatoElencoProvince = null;   
        this.importazioneUTFService.ricercaReportUTF(idImport, annualita).subscribe(
            resp => {                  
                this.importSelezionatoConsumiPrList = resp;
                this._inizializzaElencoProvincieImportSelezionato(this.importSelezionatoConsumiPrList);
                this._popolaContainerVisualizzazioneImportSelezionato(this.importSelezionatoConsumiPrList);
                this.loaderImportSelezionatoItems = false;
            },
            error => {
                this.logger.error("Errore nella ricerca dei report UTF", error);
                this.loaderImportSelezionatoItems = false;
            }
        );
    }

    private _inzializzaImportAttualePrViewContainer(idImport: number, annualita: Number) {
        this.loaderImportAttualeItems = true;
        this.importazioneUTFService.ricercaReportUTF(idImport, annualita).subscribe(
            resp => {
                this.importAttualeConsumiPrList = resp;
                this._inizializzaElencoProvincieImportAttuale(this.importAttualeConsumiPrList);
                this._popolaContainerVisualizzazioneImportAttuale(this.importAttualeConsumiPrList);               
                this.loaderImportAttualeItems = false;
            },
            error => {
                this.logger.error("Errore nella ricerca dei report UTF", error);
            }
        );
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
                this.idImportAttuale = this.listaDichiarazioniPresenti[0].importId;
                this.ultimoReportCaricatoPerAnno = this.listaDichiarazioniPresenti[0];
                /*
                if(this.listaDichiarazioniPresenti.length == 1){
                    this.importazioneUTFService.emitImportUTFUnicoImportPerAnnualita(this.idImportAttuale);
                } else {
                    this.listaDichiarazioniPresenti = this.listaDichiarazioniPresenti.filter((importUtf) => importUtf.importId != this.idImportAttuale);
                }
                    */
                if(this.listaDichiarazioniPresenti.length == 1){
                    this.importazioneUTFService.emitImportUTFUnicoImportPerAnnualita(this.idImportAttuale);
                    this.checkVisualizzaSelectElencoImport = false;
                } else {
                    this.listaDichiarazioniPresenti = this.listaDichiarazioniPresenti.filter(item => item.importId != this.idImportAttuale);
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

}