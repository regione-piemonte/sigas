import { Component, OnInit, ViewChild } from '@angular/core';
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



@Component({
  selector: 'app-anagrafica-soggetto',
  templateUrl: './anagrafica-soggetto.component.html',
  styleUrls: ['./anagrafica-soggetto.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})

@DestroySubscribers()
export class AnagraficaSoggettoComponent implements OnInit {

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
  private dichiaranteVO: DichiaranteVO;
  private totDich: number;
  private addLiq: number;
  private totVers: number;
  private selTotDich: number;
  private selAddLiq: number;
  private selTotVers: number;
  private copFid: string;
  private impFid: number; 
  private loaderDT: boolean;
  private dtTrigger: Subject<any> = new Subject();
  private updateSoggetto:boolean;
  private associateSoggetto: boolean;
  private provinceModel: Array<ProvinceVO>;
  private comuniModel: Array<ComuniVO>;
  private loaderProvince: boolean;
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


  constructor(
    private router: Router,
    private logger: LoggerService,
    private anagraficaSoggettiService: AnagraficaSoggettiService,
    private luoghiService: LuoghiService
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

    this.soggetto = new AnagraficaSoggettoVO(0,0,"","","","","","",0,"","","",0,false,0,"","",0,0);

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
      this.subscribers.rivervaConsumiForProvince = this.anagraficaSoggettiService.ricercaConsumiForProvince()
        .subscribe(res => {
          this.consumiPr = res;
          res.map(elem => { 
            this.totDich += elem.totaleDich;
            this.addLiq +=  elem.addizionale_liquidata;
          });
         
          this.selTotDich = this.totDich;
          this.selAddLiq = this.addLiq;
          this.loaderPage = false;
        }, err => {
          this.logger.error("errore ");
          this.loaderPage = false;
      });

      this.loaderPage = true;
      this.totVers = 0;
      this.subscribers.ricercaVersamentiForPeovince = this.anagraficaSoggettiService.ricercaVersamentiForProvince()
        .subscribe(res => {
          this.versamentiPr = res;
          res.map(elem => { 
            if (elem.tipo.denominazione != 'Accertamento')
            this.totVers += elem.importo;
          });
          this.selTotVers = this.totVers;
          this.loaderPage = false;
        }, err => {
          this.logger.error("errore ");
          this.loaderPage = false;
      });
    }

    this.fusioneReq = new FusioneSoggettoVO(0, 0, null, '');

  }

  cambiaProvincia(provincia: string) {
    if (provincia === "TOTALE"){
      this.selTotDich = this.totDich;
      this.selAddLiq = this.addLiq;
      this.selTotVers = this.totVers;
    } else{

      this.selTotDich = 0;
      this.selAddLiq = 0;
      this.consumiPr.map(consumo => {
        if (consumo.provincia_erogazione === provincia){
          this.selTotDich = consumo.totaleDich;
          this.selAddLiq = consumo.addizionale_liquidata;
        }
      });

      this.selTotVers = 0;
      this.versamentiPr.map(versamento => {
        if (versamento.provincia === provincia){
          this.selTotVers += versamento.importo;
        }
      });
    }

  }

  reInitSoggetto(){
    this.subscribers.ricercaSoggettoByID = this.anagraficaSoggettiService.ricercaSoggettoByID()
    .subscribe(res => {
         this.soggetto = res;
         this.copFid = res.fideussione ? 'OK' : 'KO';
         this.impFid = res.importoFideussione != null ? res.importoFideussione : 0;
         this.loaderPage = false;
         this.updateSoggetto = false;
         this.associateSoggetto = false;
       }, err => {
         this.logger.error("errore ");
         this.loaderPage = false;
    });
    this.loaderPage = true;
      this.totDich = 0;
      this.addLiq = 0;
      this.subscribers.ricercaConsumiForProvince = this.anagraficaSoggettiService.ricercaConsumiForProvince()
        .subscribe(res => {
          this.consumiPr = res;
          res.map(elem => { 
            this.totDich += elem.totaleDich;
            this.addLiq +=  elem.addizionale_liquidata;
          });
         
          this.selTotDich = this.totDich;
          this.selAddLiq = this.addLiq;
          this.loaderPage = false;
        }, err => {
          this.logger.error("errore ricerca consumi per provincia");
          this.loaderPage = false;
      });

      this.loaderPage = true;
      this.totVers = 0;
      this.subscribers.ricercaVersamentiForProvince = this.anagraficaSoggettiService.ricercaVersamentiForProvince()
        .subscribe(res => {
          this.versamentiPr = res;
          res.map(elem => { 
            this.totVers += elem.importo;
          });
          this.selTotVers = this.totVers;
          this.loaderPage = false;
        }, err => {
          this.logger.error("errore ricerca versmaneti per provincia");
          this.loaderPage = false;
      });
   }
  
  modificaSoggetto() {
      this.loaderModPage = true;
//    this.router.navigateByUrl(Routing.MODIFICA_SOGGETTO); esco dal tab dei soggetti
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
        this.subscribers.confermaModificaSoggetto = this.anagraficaSoggettiService.confermaModificaSoggetto(this.soggettoToSave).subscribe(
          resp =>{
              console.log(resp);
              this.updateSoggetto = false;
              this.loaderPage = true;
              this.reInitSoggetto();
          },err => {
            this.logger.error("errore salvataggio soggetto");
        });
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
          // this.anagraficaSoggettiService.headerDichiarante.idAnag = this.soggettoSelezionato.idAnag;
          // this.anagraficaSoggettiService.headerDichiarante.denominazione = this.soggettoSelezionato.denominazione;
          // this.reInitSoggetto();
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
      this.subscribers.scarica = this.anagraficaSoggettiService.scaricaExcelSoggetto().subscribe(
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
  
}
