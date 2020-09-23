import { Component, OnInit } from '@angular/core';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { DataTableIt } from "../../../commons/class/commons-data-table";
import { Subject } from "rxjs";
import { saveAs } from "file-saver";
import { LoggerService } from '../../../core/services/logger.service';
import { AnagraficaSoggettiService } from '../../service/anagrafica-soggetti.service';
import { TassiService } from '../../../tassi/service/tassi.service';
import { RimborsoVO } from '../../../commons/vo/rimborso-vo';
import { TassiVO } from '../../../commons/vo/tassi-vo';

import * as moment from 'moment';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';

@Component({
  selector: 'app-rimborsi',
  templateUrl: './rimborsi.component.html',
  styleUrls: ['./rimborsi.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})

@DestroySubscribers()
export class RimborsiComponent implements OnInit {

  private dtOptions: any;
  private loaderDT: boolean;
  private loaderTassi: boolean;
  private elencoRimborso: Array<RimborsoVO>;
  private elencoTassi: Array<TassiVO>;
  private dtTrigger: Subject<any> = new Subject();
  private calcolaRimborsoView: boolean;
  private rimborso : RimborsoVO;
  private totaleInteressi: number;
  private totaleRimborso: number;
  private disableCalcolo: boolean;
  private disableDetermina: boolean;
  private loaderDetermina: boolean;
  private errorMsg: boolean;
  private eMsg: string;

  public subscribers: any = {};

  private dataIstanza: any;
  private dataRimborso: any;
  private dataVersamento: any;

  constructor(
    private router: Router,
    private logger: LoggerService,
    private anagraficaSoggettiService: AnagraficaSoggettiService,
    private tassiService: TassiService
  ) { }

  ngOnInit() {

    // Move on top
    window.scrollTo(0, 0);

    this.dtOptions = {
      searching: false,
      paging: false,
      info: false,
      language: DataTableIt.language,
      ordering: false,
      columnDefs: [
        { targets: '_all', className: 'dt-center' }
      ]
    };
   
    this.errorMsg = false;
    this.eMsg = "";
    this.reInit();

  }

  goBack() {
    this.router.navigateByUrl(Routing.ELENCO_CONSUMI);
  }

  goCalcola(rimborso: RimborsoVO) {
    this.calcolaRimborsoView = true;
    this.loaderTassi = true;
    this.rimborso = rimborso;
    const _dataIstanza = moment(this.rimborso.dataIstanza);
    this.dataIstanza = {
            year: _dataIstanza.year(),
            month: Number(_dataIstanza.format('MM')),
            day: Number(_dataIstanza.format('DD'))
        };
    const _dataRimborso = moment(this.rimborso.dataRimborso);
    this.dataRimborso = {
            year: _dataRimborso.year(),
            month: Number(_dataRimborso.format('MM')),
            day: Number(_dataRimborso.format('DD'))
        };
    const _dataVersamento = moment(this.rimborso.dataVersamento);
    this.dataVersamento = {
            year: _dataVersamento.year(),
            month: Number(_dataVersamento.format('MM')),
            day: Number(_dataVersamento.format('DD'))
        };
    this.subscribers.getTassiRimborso = this.tassiService.getTassiRimborso()
      .subscribe(res => {
      this.elencoTassi = res;
      this.loaderTassi = false;
      this.disableCalcolo = false;
      this.disableDetermina = true;
      if (rimborso.statoPratica === 'DETERMINA') {
        this.disableCalcolo = true;
        this.disableDetermina = false;
        this.loadRimborso();
      }
      }, err => {
        this.logger.error("errore ");
    });
    
  }

  loadRimborso() {
    this.errorMsg = false;
    if (this.elencoTassi != null && this.rimborso.dataRimborso != null) {
      let dataIstanza =  new Date(this.rimborso.dataIstanza);
      let dataRimborso =  new Date(this.rimborso.dataRimborso);
//      const _dataIstanza = moment(this.rimborso.dataIstanza);
//      this.dataIstanza = {
//              year: _dataIstanza.year(),
//              month: Number(_dataIstanza.format('MM')),
//              day: Number(_dataIstanza.format('DD'))
//          };
//      const _dataRimborso = moment(this.rimborso.dataRimborso);
//      this.dataRimborso = {
//              year: _dataRimborso.year(),
//              month: Number(_dataRimborso.format('MM')),
//              day: Number(_dataRimborso.format('DD'))
//          };
//      const _dataVersamento = moment(this.rimborso.dataVersamento);
//      this.dataVersamento = {
//              year: _dataVersamento.year(),
//              month: Number(_dataVersamento.format('MM')),
//              day: Number(_dataVersamento.format('DD'))
//          };
      if ( dataIstanza.getTime() > dataRimborso.getTime()) {
        this.disableDetermina = true;
        this.errorMsg = true;
        this.eMsg = "La \'Data Istanza Rimborso\' non può essere superiore alla \'Data Pagamento Rimborso\'";
        return;
      } else {
        this.disableDetermina = false;
        this.errorMsg = false;
      }
      this.totaleInteressi = 0;
      this.totaleRimborso = 0;
      this.elencoTassi.map(tasso => {
        tasso.numGiorni = 0;
        let tassoStartDate = new Date(tasso.dataStart);
        let tassoEndDate = new Date(tasso.dataEnd);
        if ( tassoStartDate.getTime() <= dataIstanza.getTime() && tassoEndDate.getTime() >= dataIstanza.getTime()
              && tassoEndDate.getTime() <= dataRimborso.getTime()) {
          tasso.numGiorni = Math.floor(Date.UTC(tassoEndDate.getFullYear(), tassoEndDate.getMonth(), tassoEndDate.getDate()) -
                                Date.UTC(dataIstanza.getFullYear(), dataIstanza.getMonth(), dataIstanza.getDate())) /(1000 * 60 * 60 * 24);

        }
        if ( tassoStartDate.getTime() >= dataIstanza.getTime() && tassoEndDate.getTime() <= dataRimborso.getTime()) {
          tasso.numGiorni = Math.floor(Date.UTC(tassoEndDate.getFullYear(), tassoEndDate.getMonth(), tassoEndDate.getDate()) -
                                Date.UTC(tassoStartDate.getFullYear(), tassoStartDate.getMonth(), tassoStartDate.getDate())) /(1000 * 60 * 60 * 24);

        }
        if ( tassoStartDate.getTime() >= dataIstanza.getTime() &&
             tassoStartDate.getTime() <= dataRimborso.getTime() && tassoEndDate.getTime() >= dataRimborso.getTime()) {
          tasso.numGiorni = Math.floor(Date.UTC(dataRimborso.getFullYear(), dataRimborso.getMonth(), dataRimborso.getDate()) -
                                Date.UTC(tassoStartDate.getFullYear(), tassoStartDate.getMonth(), tassoStartDate.getDate())) /(1000 * 60 * 60 * 24);

        }

        if ( tassoStartDate.getTime() <= dataIstanza.getTime() && tassoEndDate.getTime() >= dataIstanza.getTime() &&
             tassoStartDate.getTime() <= dataRimborso.getTime() && tassoEndDate.getTime() >= dataRimborso.getTime()) {
          tasso.numGiorni = Math.floor(Date.UTC(dataRimborso.getFullYear(), dataRimborso.getMonth(), dataRimborso.getDate()) -
                                Date.UTC(dataIstanza.getFullYear(), dataIstanza.getMonth(), dataIstanza.getDate())) /(1000 * 60 * 60 * 24);

        }

        tasso.totInteresse = ((this.rimborso.importo * tasso.valore)/365/100) * tasso.numGiorni;
      
        this.totaleInteressi += tasso.totInteresse;
      });
    

      this.totaleRimborso = this.totaleInteressi + this.rimborso.importo;
    }
  }
  
  diffDate(date1, date2){
      var dt1 = new Date(date1);
      var dt2 = new Date(date2);
      return Math.floor((Date.UTC(dt2.getFullYear(), dt2.getMonth(), dt2.getDate()) - Date.UTC(dt1.getFullYear(), dt1.getMonth(), dt1.getDate()) ) /(1000 * 60 * 60 * 24));
  }

  calcolaRimborso () {
      this.errorMsg = false;
      this.eMsg = "";     
      
      this.rimborso.dataIstanza =  new Date(this.dataIstanza.year, this.dataIstanza.month - 1, this.dataIstanza.day);
      this.rimborso.dataRimborso =  new Date(this.dataRimborso.year, this.dataRimborso.month - 1, this.dataRimborso.day);
      this.rimborso.dataVersamento =  new Date(this.dataVersamento.year, this.dataVersamento.month - 1, this.dataVersamento.day);
 
      
      if(this.rimborso.dataVersamento> new Date()){
          this.errorMsg = true;
          this.eMsg = "La Data Versamento non può contenere una data futura";
      }else if(this.rimborso.dataVersamento> this.rimborso.dataIstanza){
          this.errorMsg = true;
          this.eMsg = "Non è possibile inserire una 'Data Versamento' successiva alla 'Data Istanza Rimborso'";
      } else if(this.diffDate(this.rimborso.dataVersamento, this.rimborso.dataIstanza)<= 731 ){
          
          this.loadRimborso();
          this.rimborso.statoPratica = 'CALCOLATA';
          this.rimborso.importoRimborsato =  this.totaleRimborso;
        
          
          
          console.log(this.diffDate(this.rimborso.dataIstanza, this.rimborso.dataVersamento));
      
          this.subscribers.salvaRimborso = this.anagraficaSoggettiService.salvaRimborso(this.rimborso)
          .subscribe(res => {
            this.disableDetermina = false;  
            this.rimborso.version = res.version;
          }, err => {
            this.logger.error("errore ");
        });
      }else{
          this.errorMsg = true;
          this.eMsg = "Non è possibile richiedere il rimborso qualora la data 'Data Istanza Rimborso' superi di oltre due anni la 'Data Versamento'";
      }


  }

  goBackCalcola() {
    this.calcolaRimborsoView = false;
    this.errorMsg = false;
    this.eMsg = "";
    this.reInit();
   
  }

  reInit() {
    this.loaderDT = true;
    if (this.anagraficaSoggettiService.headerDichiarante != null) {
      this.subscribers.ricercaListaRimborsi = this.anagraficaSoggettiService.ricercaListaRimborsi()    
        .subscribe(res => {
          this.elencoRimborso = res;
          this.elencoRimborso.map(el => { 
            if (el.statoPratica != 'DETERMINA') {
              el.allarme = true;
            } else {
              el.allarme = false;
            }
          })
          this.loaderDT = false;
          setTimeout(() => {
            if(this.dtTrigger!= null){
              this.dtTrigger.next(); 
              this.dtTrigger = null;  
            } 
          });
        }, err => {
          this.logger.error("errore ");
      });
    }
  }

  salvaDetermina() {
    if (this.rimborso.importo > 30) {
      this.rimborso.statoPratica = 'DETERMINA';
      this.loaderDetermina = true;
      this.rimborso.importoRimborsato =  this.totaleRimborso;
      this.subscribers.salvaDetermina = this.anagraficaSoggettiService.salvaDetermina(this.rimborso)
        .subscribe(res => {
            this.loaderDetermina = false;
            this.disableCalcolo = true;
            saveAs(res, 'determina_' + this.rimborso.anagraficaSoggettoVO.codiceAzienda +"_" +  this.rimborso.annualita + '.docx');  

            this.subscribers.salvaRimborso = this.anagraficaSoggettiService.salvaRimborso(this.rimborso)
              .subscribe(res => {
                this.rimborso.version = res.version;
                this.rimborso.allarme = false;
              }, err => {
                this.logger.error("errore ");
            });
        }, err => {
          this.logger.error("errore ");
      });
    } else {
      this.errorMsg = true;
      this.eMsg = "L'inporto è inferiore a 30.00€";
    }
  }

}
