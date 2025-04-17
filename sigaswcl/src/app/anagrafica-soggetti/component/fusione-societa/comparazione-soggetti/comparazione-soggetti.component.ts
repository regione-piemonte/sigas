import { Component, 
         EventEmitter, 
         Input, 
         OnInit, 
         Output, 
         Renderer2 } from "@angular/core";
import { Router } from "@angular/router";

import { LoggerService } from "../../../../core/services/logger.service";
import { AnagraficaSoggettiService } from "../../../service/anagrafica-soggetti.service";
import { LuoghiService } from "../../../../shared/service/luoghi-service"
import { UtilityService } from "../../../../core/services/utility/utility.service";
import { DestroySubscribers } from "../../../../core/commons/decorator/destroy-unsubscribers";

import { AnagraficaSoggettoVO } from "../../../../commons/vo/soggetti-vo";
import { ConsumiPrVO } from "../../../../commons/vo/consumi-pr-vo";
import { FusioneSoggettoVO } from "../../../../commons/vo/fusione-vo";
import { GenericResultVO } from "../../../../core/commons/vo/generic-result-vo";
import { formatDate } from "@angular/common";
import { RicercaSoggettoIncorporatoRequest } from "../../../../commons/request/ricerca-soggetto-incorporato-request";
import { ImportazioneUTFService } from "../../../../importazione-utf/service/importazione-utf.service";
import { AliquotaVO } from "../../../../commons/vo/aliquota-vo";

@Component({
    selector: 'app-comparazione-soggetti',
    templateUrl: './comparazione-soggetti.component.html',
    styleUrls: ['./comparazione-soggetti.component.scss']
})

@DestroySubscribers()
export class ComparazioneSoggettiComponent implements OnInit {

    @Input() soggettoIncorporante: AnagraficaSoggettoVO;
    @Input() soggettoIncorporato: AnagraficaSoggettoVO;

    @Output() closeComponentEvent = new EventEmitter<boolean>();    
    @Output() fusioneExecutionEvent = new EventEmitter<GenericResultVO>();

   /********************************************************************
    * Variabili di classe
    *********************************************************************/
    private showMessageInfo: boolean = false;
    private messageInfo: string = "";
    private showMessageError: boolean = false;
    private messageError: string = "";
    private showMessageSuccess: boolean = false;
    private messageSuccess: string = "";
    private loaderComparazioniSoggettoPage: boolean = false;
    private loaderDT: boolean = false;

    private dataFusione: any;
    private noteFusione: string;

    public subscribers: any = {};

    private selTotDichSoggettoIncorporato: number;
    private selAddLiqSoggettoIncorporato: number;
    private selTotVersSoggettoIncorporato: number;
    private totDichSoggettoIncorporato: number;
    private addLiqSoggettoIncorporato: number;
    private totVersSoggettoIncorporato: number;
    private loaderInfoDichiarazioniIncorporato: boolean = false;
    private elencoConsumiPerProVIncorporato: Array<ConsumiPrVO>;
    
    private selTotDichSoggettoIncorporante: number;
    private selAddLiqSoggettoIncorporante: number;
    private selTotVersSoggettoIncorporante: number;
    private totDichSoggettoIncorporante: number;
    private addLiqSoggettoIncorporante: number;
    private totVersSoggettoIncorporante: number;
    private loaderInfoDichiarazioniIncorporante: boolean = false;
    private elencoConsumiPerProVIncorporante: Array<ConsumiPrVO>;
    
    private consumiSoggettoIncorporato: Array<ConsumiPrVO>;
    private consumiSoggettoIncorporante: Array<ConsumiPrVO>;
    private listaAliquote: Array<AliquotaVO>;

    private consumiPrViewContainerIncorporante: ConsumiPrVO;
    private loaderConsumiItemsIncorporante: boolean;
    private consumiPrViewContainerIncorporato: ConsumiPrVO;
    private loaderConsumiItemsIncorporato: boolean;

    /********************************************************************
     * Costruttore
    *********************************************************************/
    constructor(
        private router: Router,
        private logger: LoggerService,
        private anagraficaSoggettiService: AnagraficaSoggettiService,
        private luoghiService:LuoghiService,
        private utilityService: UtilityService, 
        private renderer: Renderer2,
        private importazioneUTFService: ImportazioneUTFService
    ) { }      
    
    /********************************************************************
     * Funzioni associate ad Eventi
    *********************************************************************/

    ngOnInit(): void {
        console.log("this.soggettoIncorporante.idAnag", this.soggettoIncorporante.idAnag);
        console.log(" this.soggettoIncorporato.idAnag", this.soggettoIncorporato.idAnag);
        console.log("annualita", this.anagraficaSoggettiService.annoDichiarazione);

        this._caricaAliquote(+this.anagraficaSoggettiService.annoDichiarazione);

        this._caricaInformazioniSoggettoIncorporante(this.soggettoIncorporante.idAnag);
        this._caricaInformazioniSoggettoIncorporato(this.soggettoIncorporato.idAnag);  
        
        this.caricaConsumiSoggettoIncorporante();
        this.caricaConsumiSoggettoIncorporato();
    }

    // onCambiaProvinciaIncorporante(provincia: string) 
    // { 
    //     this.loaderInfoDichiarazioniIncorporante = true;
    //     if (provincia === "TOTALE"){
    //         //Ripristino valori iniziali
    //         this.selTotDichSoggettoIncorporante = this.totDichSoggettoIncorporante;
    //         this.selAddLiqSoggettoIncorporante = this.addLiqSoggettoIncorporante;
    //         this.selTotVersSoggettoIncorporante = this.totVersSoggettoIncorporante;            

    //         this.loaderInfoDichiarazioniIncorporante = false;                 
    //     } else {
  
    //         this.selTotDichSoggettoIncorporante = 0;
    //         this.selAddLiqSoggettoIncorporante = 0;
    //         this.selTotVersSoggettoIncorporante = 0;  

    //         this.elencoConsumiPerProVIncorporante.map(consumo => {
    //             if (consumo.provincia_erogazione === provincia){
    //                 this.selTotDichSoggettoIncorporante = consumo.totaleDichOrigine;
    //                 this.selAddLiqSoggettoIncorporante = consumo.addizionale_liquidata;
    //             }
    //         });  
            
    //         this.anagraficaSoggettiService.ricercaConsumiByIdAnagProvinceAndAnnualita(this.soggettoIncorporante.idAnag, 
    //                                                                                   +this.anagraficaSoggettiService.annoDichiarazione, 
    //                                                                                   provincia)
    //             .subscribe(consumiPrVOIncorporante =>
    //             {             
    //                 if(consumiPrVOIncorporante != null) {
    //                     this.selTotVersSoggettoIncorporante = consumiPrVOIncorporante.totaleVersato;
    //                 }
    //                 setTimeout(() => {
    //                     this.loaderInfoDichiarazioniIncorporante = false;                              
    //                 }, 1000)                        
    //             }, 
    //             err => {
    //                 this.logger.error("errore ");
    //                 this.loaderInfoDichiarazioniIncorporante = false;
    //             });          
    //     }  
    // }

    // onCambiaProvinciaIncorporato(provincia: string) 
    // { 
    //     this.loaderInfoDichiarazioniIncorporato = true;
    //     if (provincia === "TOTALE"){
    //         //Ripristino valori iniziali
    //         this.selTotDichSoggettoIncorporato = this.totDichSoggettoIncorporato;
    //         this.selAddLiqSoggettoIncorporato = this.addLiqSoggettoIncorporato;
    //         this.selTotVersSoggettoIncorporato = this.totVersSoggettoIncorporato;        
            
    //         this.loaderInfoDichiarazioniIncorporato = false;                   
    //     } else{
  
    //         this.selTotDichSoggettoIncorporato = 0;
    //         this.selAddLiqSoggettoIncorporato = 0;
    //         this.selTotVersSoggettoIncorporato = 0;  

    //         this.elencoConsumiPerProVIncorporato.map(consumo => {
    //             if (consumo.provincia_erogazione === provincia){
    //                 this.selTotDichSoggettoIncorporato = consumo.totaleDichOrigine;
    //                 this.selAddLiqSoggettoIncorporato = consumo.addizionale_liquidata;
    //             }
    //         });  
            
    //         this.anagraficaSoggettiService.ricercaConsumiByIdAnagProvinceAndAnnualita(this.soggettoIncorporato.idAnag, 
    //                                                                                   +this.anagraficaSoggettiService.annoDichiarazione, 
    //                                                                                   provincia)
    //             .subscribe(consumiPrVOIncorporato =>
    //             {             
    //                 if(consumiPrVOIncorporato != null) {
    //                     this.selTotVersSoggettoIncorporato = consumiPrVOIncorporato.totaleVersato;
    //                 }
    //                 setTimeout(() => {
    //                     this.loaderInfoDichiarazioniIncorporato = false;                              
    //                 }, 1000)                        
    //             }, 
    //             err => {
    //                 this.logger.error("errore ");
    //                 this.loaderInfoDichiarazioniIncorporante = false;
    //             });          
    //     }  
    // }

    onClose(){
        this.closeComponentEvent.emit(true);
    }

    onFusione() {
        let fusioneReq: FusioneSoggettoVO = new FusioneSoggettoVO(null,null,null,null);
        
        fusioneReq.idAnagIncorporante = this.soggettoIncorporante.idAnag;
        fusioneReq.idAnagIncorporato = this.soggettoIncorporato.idAnag;

        fusioneReq.dataFusione = new Date(this.dataFusione.year, this.dataFusione.month - 1, this.dataFusione.day);        
        /*
        fusioneReq.note = (this.soggettoIncorporante.note != null ? this.soggettoIncorporante.note  + "\n":"") + 
                          "Fusione con il soggetto " + this.soggettoIncorporato.denominazione + " in data " +
                          formatDate(fusioneReq.dataFusione, 'dd/MM/yyyy', 'it-IT');
        */
        
        this.showMessageError = false;                               
        this.showMessageSuccess = false;
        this.subscribers.confermaFusioneSoggetto = this.anagraficaSoggettiService.confermaFusioneSoggetto(fusioneReq)
            .subscribe(
                resp =>{              

                    this.fusioneExecutionEvent.emit(new GenericResultVO("OK","Fusione avvenuta con successo"));                    

                    //Refresh dati incorporante
                    this._caricaInformazioniSoggettoIncorporante(this.soggettoIncorporante.idAnag);
                    this.caricaConsumiSoggettoIncorporante();
                },err => {                

                    this.fusioneExecutionEvent.emit(new GenericResultVO("KO","Si e\' verifcato un\'errore imprevisto durante l\'operazione di fusione. Si prega di riprovare piu\' tardi."));

                    // Move on top
                    window.scrollTo(0, 0);
                }
            );
    }

    /********************************************************************
     * Funzioni private
     ********************************************************************/    

     private _caricaInformazioniSoggettoIncorporante(idAnagSoggettoIncorporante: number) {
        this.loaderInfoDichiarazioniIncorporante = true;        
        this.selTotDichSoggettoIncorporante = 0;
        this.selAddLiqSoggettoIncorporante = 0;
        this.selTotVersSoggettoIncorporante = 0;  
     
        this.subscribers.rivervaConsumiForProvince = this.anagraficaSoggettiService.ricercaConsumiForProvinceByIdAnag(idAnagSoggettoIncorporante)
            .subscribe(res => {
                this.elencoConsumiPerProVIncorporante = res;          
                res.map(elem => { 
                    this.selTotDichSoggettoIncorporante += elem.totaleDichOrigine;
                    this.selAddLiqSoggettoIncorporante +=  elem.addizionale_liquidata;
                    this.selTotVersSoggettoIncorporante += elem.totaleVersato
                });

                //Memorizzo i valori totali
                this.totDichSoggettoIncorporante = this.selTotDichSoggettoIncorporante;
                this.addLiqSoggettoIncorporante = this.selAddLiqSoggettoIncorporante
                this.totVersSoggettoIncorporante = this.selTotVersSoggettoIncorporante;

                this.loaderInfoDichiarazioniIncorporante = false;               

            }, err => {
            this.logger.error("errore ");
            this.loaderInfoDichiarazioniIncorporante = false;            
        });
     }

     private _caricaInformazioniSoggettoIncorporato(idAnagSoggettoIncorporato: number) {
        this.loaderInfoDichiarazioniIncorporato = true;
        this.selTotDichSoggettoIncorporato = 0;
        this.selAddLiqSoggettoIncorporato = 0;
        this.selTotVersSoggettoIncorporato = 0;  
     
        this.subscribers.rivervaConsumiForProvince = this.anagraficaSoggettiService.ricercaConsumiForProvinceByIdAnag(idAnagSoggettoIncorporato)
            .subscribe(res => {
                this.elencoConsumiPerProVIncorporato = res;          
                res.map(elem => { 
                    this.selTotDichSoggettoIncorporato += elem.totaleDichOrigine;
                    this.selAddLiqSoggettoIncorporato +=  elem.addizionale_liquidata;
                    this.selTotVersSoggettoIncorporato += elem.totaleVersato
                });

                //Memorizzo i valori totali
                this.totDichSoggettoIncorporato = this.selTotDichSoggettoIncorporato;
                this.addLiqSoggettoIncorporato = this.selAddLiqSoggettoIncorporato;
                this.totVersSoggettoIncorporato = this.selTotVersSoggettoIncorporato;

                this.loaderInfoDichiarazioniIncorporato = false;                

            }, err => {
            this.logger.error("errore ");
            this.loaderInfoDichiarazioniIncorporato = false;
        });
     }
     //############# GESTIONE CONSUMI SOGGETTO INCORPORATNE E SOGGETTO INCORPORATO

     //chiamata ws per recuperare i consumi del soggetto incorporante (viene riusato il ws per recupeare i consumi el soggetto incoporato)
     private caricaConsumiSoggettoIncorporante() {
        this.loaderInfoDichiarazioniIncorporante = true; 
        let ricercaSoggettoIncorporanteReq: RicercaSoggettoIncorporatoRequest = {
            idSoggettoIncorporato: this.soggettoIncorporante.idAnag,
            annualita: this.anagraficaSoggettiService.annoDichiarazione
        }

        this.consumiSoggettoIncorporante = [];
        this.anagraficaSoggettiService.ricercaSoggettoIncorporato(ricercaSoggettoIncorporanteReq).subscribe(resp => {
            console.log("risposta consumi incoporante", resp);
            this.consumiSoggettoIncorporante = resp;
            this.loaderInfoDichiarazioniIncorporante = false; 
            this.popolaConsumiIncorporante(this.consumiSoggettoIncorporante, "TOTALE");
        }, 
        error => {
            console.log("error", error);
            this.loaderInfoDichiarazioniIncorporante = false; 
        });
        
     }

     //chiamata ws per recuperare i consumi del possibile soggetto incorporato
     private caricaConsumiSoggettoIncorporato() {
        this.loaderInfoDichiarazioniIncorporato = true; 
        let ricercaSoggettoIncorporatoReq: RicercaSoggettoIncorporatoRequest = {
            idSoggettoIncorporato: this.soggettoIncorporato.idAnag,
            annualita: this.anagraficaSoggettiService.annoDichiarazione
        }

        this.anagraficaSoggettiService.ricercaSoggettoIncorporato(ricercaSoggettoIncorporatoReq).subscribe(resp => {
            console.log("risposta consumi incoporato", resp);
            this.consumiSoggettoIncorporato = resp;

            this.loaderInfoDichiarazioniIncorporato = false; 
            this.popolaConsumiIncorporato(this.consumiSoggettoIncorporato, "TOTALE");
        }, 
        error => {
            console.log("error", error);
            this.loaderInfoDichiarazioniIncorporato = false; 
        });
     }

     private _caricaAliquote(annualita: Number) 
     {
          this.importazioneUTFService.getAllAliquote(annualita).subscribe(resp => 
              {
                  this.listaAliquote = resp;
                  console.log("this.listaAliquote", this.listaAliquote);
              }, 
              err => {
                  this.logger.error(err);
              }
          );
     }
  
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
  
     private popolaConsumiIncorporato(importConsummiPrList: ConsumiPrVO[], provincia: String)
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
              this.consumiPrViewContainerIncorporato = Object.assign({}, consumoPr);           
  
          }
  
          console.log("this.consumiPrViewContainerIncorporato", this.consumiPrViewContainerIncorporato);
     }

     private popolaConsumiIncorporante(importConsummiPrList: ConsumiPrVO[], provincia: String)
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
              this.consumiPrViewContainerIncorporante = Object.assign({}, consumoPr);           
  
          }
  
          console.log("this.consumiPrViewContainerIncorporante", this.consumiPrViewContainerIncorporante);
     }
  
     onCambiaProvinciaIncorporato(siglaProvincia: String) {
  
      if(this.consumiPrViewContainerIncorporato!=null && this.consumiPrViewContainerIncorporato!= undefined) { 
          this.loaderInfoDichiarazioniIncorporato = true;
  
          if(siglaProvincia!="TOTALE")
          {
              let consummiPrFiltarti: ConsumiPrVO[] = this.consumiSoggettoIncorporato
                                                                       .filter((consumiPr) => consumiPr.provincia_erogazione == siglaProvincia);
              this.popolaConsumiIncorporato(consummiPrFiltarti, siglaProvincia);
          } else {
              this.popolaConsumiIncorporato(this.consumiSoggettoIncorporato, "TOTALE");
          }     
          
          setTimeout(() => {
              this.loaderInfoDichiarazioniIncorporato = false;
          }, 500);
          
        }
    }

    onCambiaProvinciaIncorporante(siglaProvincia: String) {
  
        if(this.consumiPrViewContainerIncorporante!=null && this.consumiPrViewContainerIncorporante!= undefined) { 
            this.loaderInfoDichiarazioniIncorporante = true;
    
            if(siglaProvincia!="TOTALE")
            {
                let consummiPrFiltarti: ConsumiPrVO[] = this.consumiSoggettoIncorporante
                                                                         .filter((consumiPr) => consumiPr.provincia_erogazione == siglaProvincia);
                this.popolaConsumiIncorporante(consummiPrFiltarti, siglaProvincia);
            } else {
                this.popolaConsumiIncorporante(this.consumiSoggettoIncorporante, "TOTALE");
            }     
            
            setTimeout(() => {
                this.loaderInfoDichiarazioniIncorporante = false;
            }, 500);
            
          }
      }
     
}