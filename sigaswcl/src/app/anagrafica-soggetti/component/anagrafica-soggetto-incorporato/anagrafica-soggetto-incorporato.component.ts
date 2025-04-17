import { Component, OnInit, ViewChild, Inject, AfterViewInit, AfterViewChecked, Renderer2 } from '@angular/core';
import { Router } from "@angular/router";
// import { formatDate } from "@angular/common";
import { Routing } from "../../../commons/routing";
import { LoggerService } from '../../../core/services/logger.service';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { AnagraficaSoggettiService } from '../../service/anagrafica-soggetti.service';
import { AnagraficaSoggettoVO } from '../../../commons/vo/soggetti-vo';
import { ConsumiPrVO } from '../../../commons/vo/consumi-pr-vo';
import { ComuniVO } from '../../../commons/vo/luoghi-vo/comuni-vo';
import { ProvinceVO } from '../../../commons/vo/luoghi-vo/province-vo';
import { LuoghiService } from "../../../shared/service/luoghi-service";

import { SigasDialogComponent } from "../../../shared/component/sigas-dialog/sigas-dialog.component";

import * as moment from 'moment';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';

import {UtilityService} from '../../../core/services/utility/utility.service';

import { DOCUMENT } from '@angular/common';

import { ImportazioneUTFService } from '../../../importazione-utf/service/importazione-utf.service';
import { AliquotaVO } from '../../../commons/vo/aliquota-vo';

@Component({
  selector: 'app-anagrafica-soggetto-incorporato',
  templateUrl: './anagrafica-soggetto-incorporato.component.html',
  styleUrls: ['./anagrafica-soggetto-incorporato.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})

@DestroySubscribers()
export class AnagraficaSoggettoIncorporatoComponent implements OnInit, AfterViewInit {

  private loaderModPage: boolean;
  private loaderPage: boolean;
  private soggettoIncorporato: AnagraficaSoggettoVO;
  private totDich: number;
  private addLiq: number;
  private totVers: number;
  private selTotDich: number;
  private selAddLiq: number;
  private selTotVers: number;
  private copFid: string;
  private impFid: number; 
  private provinceModel: Array<ProvinceVO>;
  private comuniModel: Array<ComuniVO>;
  private loaderProvince: boolean;
  private loaderProvinceDettAna: boolean;
  private loaderComuni: boolean;
  public subscribers: any = {};
    
  private consumiSoggettoIncorporatoPr: Array<ConsumiPrVO>;
  private listaAliquote: Array<AliquotaVO>;
  private consumiPrViewContainer: ConsumiPrVO;

  loaderConsumiItems: boolean;

  constructor(
    private router: Router,
    private logger: LoggerService,
    private anagraficaSoggettiService: AnagraficaSoggettiService,
    private luoghiService: LuoghiService,
    private utilityService: UtilityService,
    @Inject(DOCUMENT) private document: Document,
    private renderer: Renderer2,
    private importazioneUTFService: ImportazioneUTFService,
  ) { }

  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);
  }

  ngOnInit() {
    // Move on top
    window.scrollTo(0, 0);    
    this.soggettoIncorporato = new AnagraficaSoggettoVO(0,0,"","","","","","",0,"","","",0,false,0,"","",0,0,null);

    if (this.anagraficaSoggettiService.ricercaSoggettoIncorporatoReq.idSoggettoIncorporato > 0) {
      this.loaderPage = true;
      this.loaderConsumiItems = true;
      this.loaderModPage = false; 
      this.loaderProvinceDettAna = true;
      console.log("this.anagraficaSoggettiService.ricercaSoggettoIncorporatoReq", this.anagraficaSoggettiService.ricercaSoggettoIncorporatoReq)
      this.anagraficaSoggettiService.ricercaSoggettoIncorporato(this.anagraficaSoggettiService.ricercaSoggettoIncorporatoReq).subscribe(resp => {
        console.log("ricercaSoggettoIncorporato", resp);

        this.consumiSoggettoIncorporatoPr = resp;
        this.soggettoIncorporato = this.consumiSoggettoIncorporatoPr[0].anagraficaSoggettoVO;

        //get provincia e comuni 
        this.loadProvince();
        this.loadComuni(this.soggettoIncorporato.idProvincia);
        this._caricaAliquote(+this.anagraficaSoggettiService.ricercaSoggettoIncorporatoReq.annualita);

        this.popolaConsumi(this.consumiSoggettoIncorporatoPr, 'TOTALE');
        this.loaderPage = false;

        this.loaderConsumiItems = false;
        this.loaderProvinceDettAna = false;
      },
      error => {
        console.log("errore", error);
      });
      
      this.totDich = 0;
      this.addLiq = 0;
      this.totVers = 0;
      

      this.selTotDich = 0;          
      this.selAddLiq = 0;
      this.selTotVers = 0;  
     
      // this.subscribers.rivervaConsumiForProvince = this.anagraficaSoggettiService.ricercaConsumiForProvince()
      //   .subscribe(res => {
      //     this.consumiPr = res;          
      //     if(this.consumiPr==null || this.consumiPr.length==0){
      //         this.viewAssociaSoggetto = true;
      //     }
      //     res.map(elem => { 
      //       this.totDich += elem.totaleDichOrigine;
      //       this.addLiq +=  elem.addizionale_liquidata;
      //       this.totVers += elem.totaleVersato
      //     });          
      //     this.selTotDich = this.totDich;          
      //     this.selAddLiq = this.addLiq;
      //     this.selTotVers = this.totVers;          

      //     this.loaderProvinceDettAna = false;
      //     this.loaderPage = false;

      //     setTimeout(() => {
      //       let out: HTMLElement = document.getElementById('totale');
      //       if(out!=null||out!=undefined){
      //         console.log(">>>>>>>>>>>>>>>>>>>>>>>trovato")
      //         out.focus();
      //       }
      //     }, 1000)

      //   }, err => {
      //     this.logger.error("errore ");
      //     this.loaderPage = false;
      // });      
    }

  }

  reInit() {
    this.ngOnInit();
  }
     
    @ViewChild(SigasDialogComponent) sigasDialog: SigasDialogComponent;

    goBack() {
      this.sigasDialog.open();
      
      this.subscribers.save = this.sigasDialog.salvaAction.subscribe(data => {    
        // this.router.navigateByUrl(Routing.ELENCO_CONSUMI);
        }, err => {
            this.logger.error(err);
        });

      this.annullaPopUp();
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
    
  //##################################
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

   private popolaConsumi(importConsummiPrList: ConsumiPrVO[], provincia: String)
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
            this.consumiPrViewContainer = Object.assign({}, consumoPr);           

        }

        console.log("this.consumiPrViewContainer", this.consumiPrViewContainer);
   }

   cambiaProvincia(siglaProvincia: String) {

    if(this.consumiSoggettoIncorporatoPr!=null && this.consumiSoggettoIncorporatoPr!= undefined)
    { 
        this.loaderConsumiItems = true;

        if(siglaProvincia!="TOTALE")
        {
            let consummiPrFiltarti: ConsumiPrVO[] = this.consumiSoggettoIncorporatoPr
                                                                     .filter((consumiPr) => consumiPr.provincia_erogazione == siglaProvincia);
            this.popolaConsumi(consummiPrFiltarti, siglaProvincia);
        } else {
            this.popolaConsumi(this.consumiSoggettoIncorporatoPr, "TOTALE");
        }     
        this.loaderProvinceDettAna = false;
        setTimeout(() => {
            this.loaderConsumiItems = false;
        }, 500);
        
    }
}
}
