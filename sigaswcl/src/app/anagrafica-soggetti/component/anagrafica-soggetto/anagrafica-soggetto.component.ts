import { Component, OnInit, ViewChild, Inject, AfterViewInit, AfterViewChecked, Renderer2 } from '@angular/core';
import { Router } from "@angular/router";
import { formatDate } from "@angular/common";
import { Routing } from "../../../commons/routing";
import { saveAs } from "file-saver";
import { LoggerService } from '../../../core/services/logger.service';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { AnagraficaSoggettiService } from '../../service/anagrafica-soggetti.service';
import { AnagraficaSoggettoVO } from '../../../commons/vo/soggetti-vo';
import { ConsumiPrVO } from '../../../commons/vo/consumi-pr-vo';
import { VersamentiPrVO } from '../../../commons/vo/versamenti-pr-vo';
import { ComuniVO } from '../../../commons/vo/luoghi-vo/comuni-vo';
import { ProvinceVO } from '../../../commons/vo/luoghi-vo/province-vo';
import { LuoghiService } from "../../../shared/service/luoghi-service";

import { Subject } from 'rxjs';
import { DataTableIt } from '../../../commons/class/commons-data-table';
import { DichiaranteVO } from '../../../commons/vo/dichiarante-vo';
import { FusioneSoggettoVO } from '../../../commons/vo/fusione-vo';
import { SigasDialogComponent } from "../../../shared/component/sigas-dialog/sigas-dialog.component";

import * as moment from 'moment';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';

import {UtilityService} from '../../../core/services/utility/utility.service';
import {MessageEnum} from '../../../core/services/utility/enum/messageEnum';
import { DownloadDettaglioSoggettoReport } from '../../../commons/request/DownloadDettaglioSoggettoReport-request';
import { ItemSoggettoReport } from '../../../commons/request/ItemSoggettoReport';

import { DOCUMENT } from '@angular/common';


@Component({
  selector: 'app-anagrafica-soggetto',
  templateUrl: './anagrafica-soggetto.component.html',
  styleUrls: ['./anagrafica-soggetto.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})

@DestroySubscribers()
export class AnagraficaSoggettoComponent implements OnInit, AfterViewInit {

  private loaderModPage: boolean;
  private loaderPage: boolean;
  private loaderFusionePage: boolean;
  private soggetto: AnagraficaSoggettoVO;
  private soggettiFusione: Array<AnagraficaSoggettoVO>;
  private soggettoToSave: AnagraficaSoggettoVO;
  private soggettoSelezionato: AnagraficaSoggettoVO;
  private elencoSoggetti: Array<AnagraficaSoggettoVO>;
  private consumiPr: Array<ConsumiPrVO>;
  private versamentiPr: Array<VersamentiPrVO>;
  private versamentiPrAnnoPrec: Array<VersamentiPrVO>;
  private dichiaranteVO: DichiaranteVO;
  private totDich: number;
  private addLiq: number;
  private totVers: number;
  private totVersAnnoPrec: number;
  private selTotDich: number;
  private selAddLiq: number;
  private selTotVers: number;
  private congualioCalcAnnoPrec: number;
  private copFid: string;
  private impFid: number; 
  private loaderDT: boolean;
  private dtTrigger: Subject<any> = new Subject();
  private updateSoggetto:boolean;
  private associateSoggetto: boolean;
  private provinceModel: Array<ProvinceVO>;
  private comuniModel: Array<ComuniVO>;
  private loaderProvince: boolean;
  private loaderProvinceDettAna: boolean;
  private loaderComuni: boolean;
  private fusioneSoggetto: boolean;
  private fusioneReq: FusioneSoggettoVO;
  private dtOptions: any;
  private dtOptionsFusione: any;
  private showMessageError: boolean;
  private loaderExcel:boolean;
  private soggettoDerivante : AnagraficaSoggettoVO;

  public subscribers: any = {};

  private dataFusione: any;
  viewAssociaSoggetto: boolean;

  private messageError: string;


  constructor(
    private router: Router,
    private logger: LoggerService,
    private anagraficaSoggettiService: AnagraficaSoggettiService,
    private luoghiService: LuoghiService,
    private utilityService: UtilityService,
    @Inject(DOCUMENT) private document: Document,
    private renderer: Renderer2
  ) { }

  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);
  }

  inviaTotaleDichiarato(totaleDichiarato: any){
    this.anagraficaSoggettiService.emitTotaleDichiarazione(totaleDichiarato);
  }

  inviaTotaleVersato(totaleVersato: any){
    this.anagraficaSoggettiService.emitTotaleVersato(totaleVersato);
  }

  inviaVersamentiPr(versamentiPrToSend: any){
    this.anagraficaSoggettiService.emitVersamentiPr(versamentiPrToSend);
  }

  inviaVersamentiPrAnnoPrec(versamentiPrAnnoPrecToSend: any){
    this.anagraficaSoggettiService.emitVersamentiPrAnnoPrec(versamentiPrAnnoPrecToSend);
  }

  inviaConsumiPr(consumiPrToSend: any){
    this.anagraficaSoggettiService.emitConsumiPr(consumiPrToSend);
  }   

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
        { className: 'dt-center', 'targets': [0, 1, 2, 3] },
        { width: '5%', targets: 0, orderable: false },
        { width: '30%', targets: 1 },
        { width: '10%', targets: 2 },
        { width: '10%', targets: 3 }
      ]
    };

    this.dtOptionsFusione = {
      head: 20,
      pagingType: 'full_numbers',
      pageLength: 10,
      processing: true,
      language: DataTableIt.language,
      responsive: true,
      order: [],
      columnDefs: [
      ]
    };

    this.soggetto = new AnagraficaSoggettoVO(0,0,"","","","","","",0,"","","",0,false,0,"","",0,0,null);
    this.viewAssociaSoggetto = false;

    if (this.anagraficaSoggettiService.headerDichiarante) {
      this.loaderPage = true;
      this.loaderModPage = false;
      this.loaderFusionePage = false;
      
      this.copFid = '';
      this.impFid = 0;
      this.subscribers.ricercaSoggettiByID = this.anagraficaSoggettiService.ricercaSoggettoByID()
        .subscribe(res => {
          this.soggetto = res;
          this.loadProvince();
          this.loadComuni(res.idProvincia);
          this.copFid = res.fideussione ? 'OK' : 'KO';
          this.impFid = res.importoFideussione != null ? res.importoFideussione : 0;
          this.loaderPage = false;
        }, err => {
          this.logger.error("errore in caricamento sogetti per ID");
          this.loaderPage = false;
      });

      this.loaderPage = true;
      this.totDich = 0;
      this.addLiq = 0;
      this.totVers = 0;
      this.loaderProvinceDettAna = true;

      this.selTotDich = 0;          
      this.selAddLiq = 0;
      this.selTotVers = 0;  
     
      this.subscribers.rivervaConsumiForProvince = this.anagraficaSoggettiService.ricercaConsumiForProvince()
        .subscribe(res => {
          this.consumiPr = res;          
          if(this.consumiPr==null || this.consumiPr.length==0){
              this.viewAssociaSoggetto = true;
          }
          res.map(elem => { 
            this.totDich += elem.totaleDichOrigine;
            this.addLiq +=  elem.addizionale_liquidata;
            this.totVers += elem.totaleVersato
          });          
          this.selTotDich = this.totDich;          
          this.selAddLiq = this.addLiq;
          this.selTotVers = this.totVers;          

          this.loaderProvinceDettAna = false;
          this.loaderPage = false;

          setTimeout(() => {
            let out: HTMLElement = document.getElementById('totale');
            if(out!=null||out!=undefined){
              console.log(">>>>>>>>>>>>>>>>>>>>>>>trovato")
              out.focus();
            }
          }, 1000)

        }, err => {
          this.logger.error("errore ");
          this.loaderPage = false;
      });      
    }
    this.fusioneReq = new FusioneSoggettoVO(0, 0, null, '');

  }
  
  modButtonFideussione() {
      this.soggettoToSave.fideussione = !this.soggettoToSave.fideussione;
      
    }
  
  cambiaProvincia(provincia: string) {       

      this.loaderProvinceDettAna = true;
      if (provincia === "TOTALE"){
        this.selTotDich = this.totDich;        
        this.selAddLiq = this.addLiq;
        this.selTotVers = this.totVers;        

        console.log(this.totVers);
        console.log(this.congualioCalcAnnoPrec);
        this.loaderProvinceDettAna = false;

        let out: HTMLElement = document.getElementById('totale');        
        if(out!=null||out!=undefined){
          console.log(">>>>>>>>>>>>>>>>>>>>>>>trovato")
          out.focus();
        }        
      } else{

        this.selTotDich = 0;
        this.selAddLiq = 0;
        this.consumiPr.map(consumo => {
          if (consumo.provincia_erogazione === provincia){
            this.selTotDich = consumo.totaleDichOrigine;
            this.selAddLiq = consumo.addizionale_liquidata;
          }
        });

        this.selTotVers = 0;        
        this.anagraficaSoggettiService.ricercaConsumiForProvinceAndAnnualita(+this.anagraficaSoggettiService.annoDichiarazione, provincia).subscribe(consumiPrVO=>{             
            if(consumiPrVO!=null){
              this.selTotVers = consumiPrVO.totaleVersato;
            }
            setTimeout(() => {
              this.loaderProvinceDettAna = false;

              let out: HTMLElement = document.getElementById('btn-'+provincia);
              if(out!=null||out!=undefined){
                console.log(">>>>>>>>>>>>>>>>>>>>>>>trovato")
                out.focus();
              }

            }, 1000)                        
        }, err => {
            this.logger.error("errore ");
            this.loaderProvinceDettAna = false;
        });
        
      }

    }

  reInitSoggetto(){
    this.subscribers.ricercaSoggettoByID = this.anagraficaSoggettiService.ricercaSoggettoByID()
    .subscribe(res => {
         this.soggetto = res;
         this.copFid = res.fideussione ? 'OK' : 'KO';
         this.impFid = res.importoFideussione != null ? res.importoFideussione : 0;

         //update card header dettaglio-soggetto-component         
         this.anagraficaSoggettiService.headerDichiarante.denominazione = this.soggetto.denominazione;

         this.loaderPage = false;
         this.updateSoggetto = false;
         this.associateSoggetto = false;
       }, err => {
         this.logger.error("errore ");
         this.loaderPage = false;
    });

    this.loaderPage = true;
    this.loaderProvinceDettAna = true;

    this.totDich = 0;
    this.addLiq = 0;
    this.totVers = 0;
    this.selTotDich = 0;          
    this.selAddLiq = 0;
    this.selTotVers = 0;
    
    this.subscribers.ricercaConsumiForProvince = this.anagraficaSoggettiService.ricercaConsumiForProvince()
      .subscribe(res => {
        this.consumiPr = res;          
        res.map(elem => { 
          this.totDich += elem.totaleDichOrigine;
          this.addLiq +=  elem.addizionale_liquidata;
          this.totVers += elem.totaleVersato
        });
        
        this.selTotDich = this.totDich;          
        this.selAddLiq = this.addLiq;
        this.selTotVers = this.totVers;

        this.loaderPage = false;
        this.loaderProvinceDettAna = false;
      }, err => {
        this.logger.error("errore ricerca consumi per provincia");
        this.loaderPage = false;
    });      
   }
  
  modificaSoggetto() {
      this.loaderModPage = true;
      this.updateSoggetto = true;
      this.loaderModPage = false;
      this.soggettoToSave = Object.assign({}, this.soggetto);
  }
  
  associaSoggetto() {

      this.loaderComuni = true;
      this.loaderProvince = true;
      this.associateSoggetto = true;

      this.loaderDT = true;
      this.subscribers.ricercaListaSoggetti = this.anagraficaSoggettiService.ricercaListaSoggetti()
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
    }

    @ViewChild(SigasDialogComponent) sigasDialog: SigasDialogComponent;
    buttonConfirmText: string;
    buttonAnnullaText: string;
    messageDialog: string;

    goBack() {
      
      this.buttonConfirmText = "Ok";
      this.buttonAnnullaText = "Annulla";
      this.messageDialog= "Tutte le modifiche apportate verranno perse. Continuare?";
      this.sigasDialog.open();
      
      this.subscribers.save = this.sigasDialog.salvaAction.subscribe(data => {
        this.updateSoggetto = false;       
        this.associateSoggetto = false;
        // this.router.navigateByUrl(Routing.ELENCO_CONSUMI);
        this.updateSoggetto = false;
        this.fusioneSoggetto = false;
        this.reInitSoggetto();
        }, err => {
            this.logger.error(err);
        });

      this.annullaPopUp();
    }

    goBackFusione() {
      this.updateSoggetto = false;       
      this.associateSoggetto = false;
      this.updateSoggetto = false;
      this.fusioneSoggetto = false;
      this.reInitSoggetto();
    }

    annullaPopUp() {
      this.subscribers.close = this.sigasDialog.closeAction.subscribe(data => {
          this.subscribers.close.unsubscribe();
          this.subscribers.save.unsubscribe();
          this.subscribers.XAction.unsubscribe();
      }, err => {
          this.logger.error(err);
      });

      this.subscribers.XAction = this.sigasDialog.XAction.subscribe(data => {
          this.subscribers.close.unsubscribe();
          this.subscribers.save.unsubscribe();
          this.subscribers.XAction.unsubscribe();
      }, err => {
          this.logger.error(err);
      });
    }

    goBackSoggetti() {
      this.router.navigateByUrl(Routing.ELENCO_CONSUMI);
    }
    
    loadProvince() {
        this.logger.info("carico le province")
        this.loaderProvince = false;
        this.subscribers.province = this.luoghiService.getAllProvince().subscribe(data => {
            this.loaderProvince = true;
            this.provinceModel = data;
            this.provinceModel.push(new ProvinceVO(-1, "Nessuna Provincia Selezionata",""));
        }, err => {
            this.logger.error("Errore nel recupero delle province");
        });
    }
    
    loadComuni(id: number) {
        this.logger.info("carico i comuni della provincia con id:" + id);
        if (id != null && id != -1) {
            this.loaderComuni = false;
            this.subscribers.comuniByIdProvincia = this.luoghiService.getComuneByIdProvincia(id).subscribe(data => {
                this.comuniModel = data;
                this.loaderComuni = true;
            }, err => {
                this.logger.error("Errore nel recupero dei comuni");
            });
        }
        else {
            this.comuniModel = new Array<ComuniVO>();
            this.loaderComuni = true;
        }
    }  
    
    onSubmitModifica() {
      this.soggettoToSave.version = this.soggetto.version;

      if((this.soggettoToSave.note!=null && this.soggettoToSave.note!=undefined) && 
         this.soggettoToSave.note.length > 255)
      {
        this.utilityService.getMessageByKey(MessageEnum.SIZENOTE).subscribe(msg => {
				   this.showMessageError = true;
           this.messageError = msg.message || '';
        }, error => {this.logger.error(error);});
      } else if (this.soggettoToSave.denominazione !=null && 
                 this.soggettoToSave.denominazione && 
                 this.soggettoToSave.denominazione.length > 150 ) 
      {
        this.utilityService.getMessageByKey(MessageEnum.SIZEDENOMINAZIONEANA).subscribe(msg => {
          this.showMessageError = true;
          this.messageError = msg.message || '';
        }, error => {this.logger.error(error);});
      }  else if (this.soggettoToSave.indirizzo !=null && 
                  this.soggettoToSave.indirizzo && 
                  this.soggettoToSave.indirizzo.length > 255)
      {
        this.utilityService.getMessageByKey(MessageEnum.SIZEINDIRIZZOANA).subscribe(msg => {
          this.showMessageError = true;
          this.messageError = msg.message || '';
        }, error => {this.logger.error(error);});
      } else{
        if(this.soggettoToSave.cfPiva != null && this.soggettoToSave.cfPiva != undefined)
        {
          this.soggettoToSave.cfPiva = this.soggettoToSave.cfPiva.toUpperCase();
        }        
        this.subscribers.confermaModificaSoggetto = this.anagraficaSoggettiService.confermaModificaSoggetto(this.soggettoToSave).subscribe(
          resp =>{
              this.showMessageError = false;
              console.log(resp);
              this.updateSoggetto = false;
              this.loaderPage = true;
              this.reInitSoggetto();
          },err => {
            this.showMessageError = true;
            this.messageError = err.message || '';
            this.logger.error("errore salvataggio soggetto");
        });
      }      
    }
    
     associaSoggSubmit() {
        console.log("INIZIO");
        console.log(this.soggetto);
        console.log(this.soggettoSelezionato);
        console.log("FINE");
        this.loaderPage = true;
        this.subscribers.confermaAssociaSoggetto = this.anagraficaSoggettiService.confermaAssociaSoggetto(this.soggetto, this.soggettoSelezionato).subscribe(
          resp => {
          console.log(resp);
          this.updateSoggetto = false;          
          this.router.navigateByUrl(Routing.ELENCO_CONSUMI);
          }, err => {
            this.logger.error("errore associazione soggetto");
          });
    }
    
    checkRow(id: number, denom: string) {
        this.dichiaranteVO = new DichiaranteVO(id, denom);
        this.anagraficaSoggettiService.headerDichiaranteSelezionato = this.dichiaranteVO;
        this.subscribers.ricercaSoggettoSelezionatoByID = this.anagraficaSoggettiService.ricercaSoggettoSelezionatoByID().subscribe(
          resp => {
            this.soggettoSelezionato = resp;
            console.log(resp);
          }, err => {
            this.logger.error("errore ricerca soggetto selezionato by id");
          });
      }


    onSubmitFusione() {
     this.fusioneReq.idAnagConfluente = this.soggetto.idAnag;
     console.log( this.fusioneReq);
     this.loaderFusionePage = true;
     this.showMessageError = false;
     this.fusioneReq.dataFusione = new Date(this.dataFusione.year, this.dataFusione.month - 1, this.dataFusione.day);
     
     
     for (var index = 0; index < this.soggettiFusione.length; index++) {
         if(this.soggettiFusione[index].idAnag === this.fusioneReq.idAnagDerivante){
             this.soggettoDerivante = this.soggettiFusione[index];
         }
         
     }
     
     this.fusioneReq.note = (this.fusioneReq.note != null ? this.fusioneReq.note  + "\n":"") + 
                            "Fusione con il soggetto " + this.soggettoDerivante.denominazione + " in data " +
                            formatDate(this.fusioneReq.dataFusione, 'dd/MM/yyyy', 'it-IT');
     
     this.subscribers.confermaFusioneSoggetto = this.anagraficaSoggettiService.confermaFusioneSoggetto(this.fusioneReq).subscribe(
        resp =>{
            console.log(resp);
            this.fusioneSoggetto = false;
            this.loaderFusionePage = false;
            this.router.navigateByUrl(Routing.ELENCO_CONSUMI);
        },err => {
          this.logger.error("errore fusione soggetto");
          this.loaderFusionePage = false;
          this.showMessageError = true;
          // Move on top
          window.scrollTo(0, 0);
      });
    }

    fusioneSocietaria() {
      this.fusioneSoggetto = true;
      this.loaderFusionePage = true;
      this.loaderDT = true;
      this.fusioneReq = new FusioneSoggettoVO(0,0,null,null);
      this.subscribers.ricercaListaSoggetti = this.anagraficaSoggettiService.ricercaListaSoggetti().subscribe(
        resp =>{
            console.log(resp);
            this.soggettiFusione = new Array<AnagraficaSoggettoVO>();
            resp.map(el => {
              if (el.idAnag != this.soggetto.idAnag)
                this.soggettiFusione.push(el);
            })
            this.fusioneSoggetto = true;
            this.loaderFusionePage = false;
            this.loaderDT = false;
        },err => {
          this.logger.error("errore caricamento soggetti");
          this.loaderDT = false;
      });
    }

    goExcelSoggetto() {
      this.loaderExcel = true;
      var itemSoggettoReportList: Array<ItemSoggettoReport> = new Array<ItemSoggettoReport>();
      this.consumiPr.forEach(itemSoggetto => {
        itemSoggettoReportList.push(new ItemSoggettoReport(itemSoggetto.provincia_erogazione,
                                                           itemSoggetto.totaleVersato,
                                                           itemSoggetto.totaleDich,
                                                           itemSoggetto.addizionale_liquidata));
      });      
      var prov = this.provinceModel.filter(item =>item.id === this.soggetto.idProvincia)[0];
      var com = this.comuniModel.filter(item => item.id === this.soggetto.idComune)[0];
      let request = new DownloadDettaglioSoggettoReport(this.soggetto.denominazione, 
                                                        this.soggetto.codiceAzienda,
                                                        this.soggetto.indirizzo,
                                                        this.soggetto.iban,
                                                        this.soggetto.telefono,
                                                        this.soggetto.pec,
                                                        this.soggetto.email,
                                                        this.soggetto.note,
                                                        this.copFid,
                                                        this.impFid,
                                                        com.denominazione,
                                                        prov.sigla,
                                                        itemSoggettoReportList);
      this.subscribers.scarica = this.anagraficaSoggettiService.scaricaExcelSoggetto(request).subscribe(
          res => {
            this.loaderExcel = false;
            saveAs(res, "export_soggetto_" + this.anagraficaSoggettiService.headerDichiarante.denominazione + 
              "_" + this.anagraficaSoggettiService.annoDichiarazione + '.xls');
            
          },
          err => {
              this.logger.error("errore in download excel");
          }
      );
    }

    calculateArialLabel(el: AnagraficaSoggettoVO){
      let frase: String;
      frase = "Seleziona soggetto " + el.denominazione + 
              " avente il codice " + el.codiceAzienda;
      return frase;
    }

    calculateArialLabelSoggettoFusione(el: AnagraficaSoggettoVO){
      let frase: String;
      frase = "Seleziona soggetto " + el.denominazione + 
              " avente il codice " + el.codiceAzienda + 
              " che converge in un Soggetto unico a seguito di una Fusione Societaria";
      return frase;
    }
  
}
