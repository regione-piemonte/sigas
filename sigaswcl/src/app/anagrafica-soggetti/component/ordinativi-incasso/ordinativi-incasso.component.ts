import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { Subject } from "rxjs";
import { DataTableDirective } from 'angular-datatables';
import { Routing } from '../../../commons/routing';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { DataTableIt } from "../../../commons/class/commons-data-table";
import { LoggerService } from "../../../core/services/logger.service";
import { OrdinativiService } from '../../service/ordinativi.service';
import { ConsumiVO } from '../../../commons/vo/consumi-vo';
import { OrdinativiIncassoVO } from '../../../commons/vo/ordinativi-incasso';
import { RicercaOrdinativiRequest } from '../../../commons/request/ricerca-ordinativi-request';
import { saveAs } from "file-saver";
import { AllarmiSoggettoVO } from '../../../commons/vo/allarmi-soggetto-vo';
import { SigasDialogComponent } from "../../../shared/component/sigas-dialog/sigas-dialog.component";
declare var jquery: any;
declare var $: any;
import * as moment from 'moment';

import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';

@Component({
  selector: 'app-ordinativi-incasso',
  templateUrl: './ordinativi-incasso.component.html',
  styleUrls: ['./ordinativi-incasso.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})

@DestroySubscribers()
export class OrdinativiIncassoComponent implements OnInit {


  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;

  private dtOptions: any;
  private loaderDT: boolean;
  private loaderYear: boolean;
  private elencoConsumi: Array<ConsumiVO>;
  private reportAnno: Array<string>;
  private validati: Array<any>;
  private dtTrigger: Subject<any> = new Subject();
  private ricercaOrdinativiRequest: RicercaOrdinativiRequest;
  private anno: string;
  private validato: string;
  private loaderExcel: boolean;
  private allarmeSogg: AllarmiSoggettoVO;
  private allarmeIncoerenza: boolean;
  private filtro: string;
  private statoConciliazione: string;

  public subscribers: any = {};
  
  
  private elencoOrdinativiIncasso: Array<OrdinativiIncassoVO>;
  private elencoOrdinativiIncassoSelezionati: Array<OrdinativiIncassoVO>;
  
  private associazioneReq: OrdinativiIncassoVO;
  
  private showMessageError: boolean;
  private showSuccess: boolean;
  private messageSuccess: string;
  private messageError: string;
  
  private dataIncassoDa: any;
  private dataIncassoA: any;
  
  
  private errorMsg: boolean;
  private eMsg: string;
  
  
  @ViewChild(SigasDialogComponent) sigasDialog: SigasDialogComponent;
  buttonConfirmText: string;
  buttonAnnullaText: string;
  messageDialog: string;
  soggetto: string;
  ordinativoDaCancellare: OrdinativiIncassoVO;
 currentDate: Date;
  
  constructor(
    private router: Router,
    private logger: LoggerService,
    private ordinativiService: OrdinativiService
  ) { }

  ngOnInit() {
    // Move on top
    window.scrollTo(0, 0);

    
    this.dtOptions = {
            searching: true,
            pagingType: 'full_numbers',
            info: true,
            language: DataTableIt.language,
            ordering: true,
            columnDefs: [
                           { orderable: false, targets: 0 },
                           { orderable: false, targets: 1 },
                           { orderable: false, targets: 2 }
                         ]
//            columnDefs: [
//              { targets: '_all', className: 'dt-nowrap' },
//              { targets: '_all', className: 'dt-center' }
//            ]
          };

    this.ricercaOrdinativiRequest =  new RicercaOrdinativiRequest(null, null, null, null, null, null, null);
    this.elencoOrdinativiIncassoSelezionati = new Array<OrdinativiIncassoVO>(); 
//    this.loaderYear = true;
//    this.subscribers.ricercaPerAnno = this.ordinativiService.ricercaAnnualitaPagamenti()
//      .subscribe(res => {
//        this.reportAnno = res;
//        this.loaderYear = false;
//
//      }, err => {
//        this.logger.error("errore ");
//        this.loaderYear = false;
//    });
//    
//
//    console.log("Messaggio: "+this.ordinativiService.getMessageSuccess);
//    console.log("Show: "+this.ordinativiService.getShowSuccess);
//    
//    setTimeout(() => {
//        if(this.ordinativiService.getShowSuccess){
//            this.showSuccess = this.ordinativiService.getShowSuccess;
//            this.messageSuccess = this.ordinativiService.getMessageSuccess;
//        }
//        
//        if(this.ordinativiService.getShowMessageError){
//            this.showMessageError = this.ordinativiService.getShowMessageError;
//            this.messageError = this.ordinativiService.getMessageError;
//        }
//      });
//    
//    
//    
    if(this.ordinativiService.ricercaOrdinativiReq!=null && 
            (this.ordinativiService.ricercaOrdinativiReq.dataIncassoDa!=null || 
            this.ordinativiService.ricercaOrdinativiReq.dataIncassoA!=null)){
        
        if(this.ordinativiService.ricercaOrdinativiReq.dataIncassoDa!=null){
            const _dataIncassoDa = moment(this.ordinativiService.ricercaOrdinativiReq.dataIncassoDa);
            this.dataIncassoDa = {
                    year: _dataIncassoDa.year(),
                    month: Number(_dataIncassoDa.format('MM')),
                    day: Number(_dataIncassoDa.format('DD'))
                };
        }
        
        
        if(this.ordinativiService.ricercaOrdinativiReq.dataIncassoA!=null){
            const _dataIncassoA = moment(this.ordinativiService.ricercaOrdinativiReq.dataIncassoA);
            this.dataIncassoA = {
                    year: _dataIncassoA.year(),
                    month: Number(_dataIncassoA.format('MM')),
                    day: Number(_dataIncassoA.format('DD'))
                };
        }
        
        
        console.log('Conciliato'+this.ordinativiService.ricercaOrdinativiReq.conciliato)
        if(this.ordinativiService.ricercaOrdinativiReq.conciliato==null && this.ricercaOrdinativiRequest.conciliatoParziale == null){
            this.statoConciliazione = 'all';            
        }else if(this.ordinativiService.ricercaOrdinativiReq.conciliato && !this.ricercaOrdinativiRequest.conciliatoParziale){
            this.statoConciliazione= 'Conciliato';
        }else if(!this.ordinativiService.ricercaOrdinativiReq.conciliato && !this.ricercaOrdinativiRequest.conciliatoParziale){
            this.statoConciliazione= 'Da_Conciliare';
        }else if(!this.ordinativiService.ricercaOrdinativiReq.conciliato && this.ricercaOrdinativiRequest.conciliatoParziale){
            this.statoConciliazione= 'Conciliato_Parziale';
        }
        
        
    }
//    else{
//        
//
//          var d = new Date();
//          d.setMonth(0);
//          d.setDate(1);
//        
//        const _dataIncassoDa = moment(d);
//        this.dataIncassoDa = {
//                year: _dataIncassoDa.year(),
//                month: Number(_dataIncassoDa.format('MM')),
//                day: Number(_dataIncassoDa.format('DD'))
//            };
//            
//        console.log(this.dataIncassoDa);
//        this.ricercaOrdinativiRequest.dataIncassoDa = this.dataIncassoDa;
//        this.currentDate = new Date();
//        const _dataIncassoA = moment(this.currentDate);
//        this.dataIncassoA = {
//                year: _dataIncassoA.year(),
//                month: Number(_dataIncassoA.format('MM')),
//                day: Number(_dataIncassoA.format('DD'))
//            };
//        console.log(this.dataIncassoA);
//        this.ricercaOrdinativiRequest.dataIncassoA = this.dataIncassoA;
//        this.statoConciliazione= 'Da_Conciliare';
//        
//        this.ordinativiService.ricercaOrdinativiReq =  new RicercaOrdinativiRequest(null, d, this.currentDate, false, null, null);
//
//        
//        
//    }
  
  if(this.ordinativiService.ricercaOrdinativiReq==undefined || this.ordinativiService.ricercaOrdinativiReq==null){
      this.ordinativiService.ricercaOrdinativiReq =  new RicercaOrdinativiRequest(null, null, null, false, null, null, false);
      this.statoConciliazione= 'Da_Conciliare';
  } else{
      if(this.ordinativiService.ricercaOrdinativiReq.conciliato==null && this.ordinativiService.ricercaOrdinativiReq.conciliatoParziale == null){
          this.statoConciliazione = 'all';            
      }else if(this.ordinativiService.ricercaOrdinativiReq.conciliato && !this.ordinativiService.ricercaOrdinativiReq.conciliatoParziale){
          this.statoConciliazione= 'Conciliato';
      }else if(!this.ordinativiService.ricercaOrdinativiReq.conciliato && !this.ordinativiService.ricercaOrdinativiReq.conciliatoParziale){
          this.statoConciliazione= 'Da_Conciliare';
      }else if(!this.ordinativiService.ricercaOrdinativiReq.conciliato && this.ordinativiService.ricercaOrdinativiReq.conciliatoParziale){
          this.statoConciliazione= 'Conciliato_Parziale';
      }
  }
  
    this.ordinativiService.ricercaOrdinativiReq.azienda = this.ordinativiService.soggettoAssociato.denominazione;
    this.ordinativiService.ricercaOrdinativiReq.codiceAzienda = this.ordinativiService.soggettoAssociato.codiceAzienda;
    setTimeout(() => {
        this.loaderDT = true;
        console.log('filtro ordinativi conciliato:' +this.ordinativiService.ricercaOrdinativiReq.conciliato);
        console.log('filtro ordinativi:' +this.ordinativiService.ricercaOrdinativiReq.conciliatoParziale);
        this.subscribers.ricercaCosumi = this.ordinativiService.ricercaOrdinativi()
        .subscribe(resp => {
          this.elencoOrdinativiIncasso = resp;
         
          this.loaderDT = false;
          setTimeout(() => {
            this.dtTrigger.next(); 
          });

        }, err => {
          this.logger.error(err);
          this.loaderDT = false;
          setTimeout(() => {
            this.dtTrigger.next(); 
          });

        });
    }, 500);
    
    
    this.loaderExcel = false;
    this.associazioneReq = new OrdinativiIncassoVO(0,'','','',0,0,false,null,null,null,'','','','','','','','','','',0,0,'',0,0,'','',0,null);
    this.ordinativoDaCancellare = new OrdinativiIncassoVO(0,'','','',0,0,false,null,null,null,'','','','','','','','','','',0,0,'',0,0,'','',0,null);
    this.clearMsg();
    this.ordinativiService.listaOrdinativiIncassoSelezionati = null;
    
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
  }

  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
       dtInstance.destroy();
       this.dtTrigger.next();
   });
  }

  resetDataIncassoDa(){
      console.log('Sono in resetDataIncassoDA');
      this.ordinativiService.ricercaOrdinativiReq.dataIncassoDa = null;
      this.dataIncassoDa = null;
  }
  
  resetDataIncassoA(){
      console.log('Sono in resetDataIncassoA');
      this.ordinativiService.ricercaOrdinativiReq.dataIncassoA = null;
      this.dataIncassoA = null;
  }
  
  changeFilterDataIncassoDA(event) {
      console.log(event);
      this.clearMsg();
      
      this.ricercaOrdinativiRequest.dataIncassoDa = new Date(event.year, event.month - 1, event.day);
      
      if(this.statoConciliazione=='Conciliato'){
          this.ricercaOrdinativiRequest.conciliato = true;
          this.ricercaOrdinativiRequest.conciliatoParziale = false;
      }else if(this.statoConciliazione=='Da_Conciliare'){
          this.ricercaOrdinativiRequest.conciliato = false;
          this.ricercaOrdinativiRequest.conciliatoParziale = false;
      }else if(this.statoConciliazione=='Conciliato_Parziale'){
          this.ricercaOrdinativiRequest.conciliato = false;
          this.ricercaOrdinativiRequest.conciliatoParziale = true;
      }else{
          this.ricercaOrdinativiRequest.conciliato = null;
          this.ricercaOrdinativiRequest.conciliatoParziale = null;
      }
      this.ordinativiService.ricercaOrdinativiReq.conciliato = this.ricercaOrdinativiRequest.conciliato;
      
      this.ordinativiService.ricercaOrdinativiReq.dataIncassoDa = this.ricercaOrdinativiRequest.dataIncassoDa;
      
//      if(this.ricercaOrdinativiRequest.dataIncassoA!=null){
//          if(this.diffDate(this.ricercaOrdinativiRequest.dataIncassoDa, this.ricercaOrdinativiRequest.dataIncassoA)<= 365 ){
//              this.loaderDT = true;
//              this.subscribers.ricercaCosumi = this.ordinativiService.ricercaOrdinativi()
//              .subscribe(resp => {
//                this.elencoOrdinativiIncasso = resp;
//               
//                this.loaderDT = false;
//                this.rerender();
//              }, err => {
//                this.logger.error(err);
//                this.loaderDT = false;
//                this.rerender();
//              });
//          }else{
//              this.errorMsg = true;
//              this.eMsg = "L'intervallo di date selezionato non può essere maggiore di un anno";
//          }
//          
//      }
     
  }
  
  changeFilterDataIncassoA(event) {
      console.log(event);
      this.clearMsg();
      
      this.ricercaOrdinativiRequest.dataIncassoA = new Date(event.year, event.month - 1, event.day);
      
      if(this.statoConciliazione=='Conciliato'){
          this.ricercaOrdinativiRequest.conciliato = true;
          this.ricercaOrdinativiRequest.conciliatoParziale = false;
      }else if(this.statoConciliazione=='Da_Conciliare'){
          this.ricercaOrdinativiRequest.conciliato = false;
          this.ricercaOrdinativiRequest.conciliatoParziale = false;
      }else if(this.statoConciliazione=='Conciliato_Parziale'){
          this.ricercaOrdinativiRequest.conciliato = false;
          this.ricercaOrdinativiRequest.conciliatoParziale = true;
      }else{
          this.ricercaOrdinativiRequest.conciliato = null;
          this.ricercaOrdinativiRequest.conciliatoParziale = null;
      }
      
      this.ordinativiService.ricercaOrdinativiReq.conciliato = this.ricercaOrdinativiRequest.conciliato;
      
      this.ordinativiService.ricercaOrdinativiReq.dataIncassoA = this.ricercaOrdinativiRequest.dataIncassoA;
      
//      if(this.ricercaOrdinativiRequest.dataIncassoDa!=null){
//          if(this.diffDate(this.ricercaOrdinativiRequest.dataIncassoDa, this.ricercaOrdinativiRequest.dataIncassoA)<= 365 ){
//              this.loaderDT = true;
//              this.subscribers.ricercaCosumi = this.ordinativiService.ricercaOrdinativi()
//              .subscribe(resp => {
//                this.elencoOrdinativiIncasso = resp;
//               
//                this.loaderDT = false;
//                this.rerender();
//              }, err => {
//                this.logger.error(err);
//                this.loaderDT = false;
//                this.rerender();
//              });
//          }else{
//              this.errorMsg = true;
//              this.eMsg = "L'intervallo di date selezionato non può essere maggiore di un anno";
//          }
//          
//      }
      
  }
  changeFilter() {
      
      this.clearMsg();
//    if (this.anno != null) {
      
      
      
      
      if(this.statoConciliazione=='Conciliato'){
          this.ricercaOrdinativiRequest.conciliato = true;
          this.ricercaOrdinativiRequest.conciliatoParziale = false;
      }else if(this.statoConciliazione=='Da_Conciliare'){
          this.ricercaOrdinativiRequest.conciliato = false;
          this.ricercaOrdinativiRequest.conciliatoParziale = false;
      }else if(this.statoConciliazione=='Conciliato_Parziale'){
          this.ricercaOrdinativiRequest.conciliatoParziale = true;
          this.ricercaOrdinativiRequest.conciliato = false;
      }else{
          this.ricercaOrdinativiRequest.conciliato = null;
          this.ricercaOrdinativiRequest.conciliatoParziale = null;
      }
      
      this.ordinativiService.ricercaOrdinativiReq.conciliato = this.ricercaOrdinativiRequest.conciliato;
      this.ordinativiService.ricercaOrdinativiReq.conciliatoParziale = this.ricercaOrdinativiRequest.conciliatoParziale;
//      if(this.diffDate(this.ricercaOrdinativiRequest.dataIncassoDa, this.ricercaOrdinativiRequest.dataIncassoA)<= 365 ){
//          this.loaderDT = true;
//      
//          this.subscribers.ricercaCosumi = this.ordinativiService.ricercaOrdinativi()
//              .subscribe(resp => {
//                this.elencoOrdinativiIncasso = resp;
//               
//                this.loaderDT = false;
//                this.rerender();
//              }, err => {
//                this.logger.error(err);
//                this.loaderDT = false;
//                this.rerender();
//              });
//      
//      }else{
//          this.errorMsg = true;
//          this.eMsg = "L'intervallo di date selezionato non può essere maggiore di un anno";
//      }
    
  }
  
  ricercaOrdinativi(){
      this.loaderDT = true;
      
      this.subscribers.ricercaCosumi = this.ordinativiService.ricercaOrdinativi()
          .subscribe(resp => {
            this.elencoOrdinativiIncasso = resp;
           
            this.loaderDT = false;
            this.rerender();
          }, err => {
            this.logger.error(err);
            this.loaderDT = false;
            this.rerender();
          });
  }
  
  diffDate(date1, date2){
      var dt1 = new Date(date1);
      var dt2 = new Date(date2);
      return Math.floor((Date.UTC(dt2.getFullYear(), dt2.getMonth(), dt2.getDate()) - Date.UTC(dt1.getFullYear(), dt1.getMonth(), dt1.getDate()) ) /(1000 * 60 * 60 * 24));
  }
  
//  ordinativoSelect(isChecked: boolean,ordIncSel: OrdinativiIncassoVO){
//      if(isChecked){
//          this.elencoOrdinativiIncassoSelezionati.push(ordIncSel);
//      }else{
//          
//          this.elencoOrdinativiIncassoSelezionati.splice(this.elencoOrdinativiIncassoSelezionati.indexOf(ordIncSel, 0), 1)
//      }
//      
//  }
  
  ordinativoSelect(ordIncSel: OrdinativiIncassoVO){
//      this.elencoOrdinativiIncassoSelezionati = new Array<OrdinativiIncassoVO>(); 
      this.clearMsg();
      this.elencoOrdinativiIncassoSelezionati[0]= ordIncSel;
      this.ordinativiService.listaOrdinativiIncassoSelezionati = this.elencoOrdinativiIncassoSelezionati;
      
  }

  //non più usato da eliminare
  goInserimentoSoggetto(){
      this.router.navigateByUrl(Routing.NUOVO_SOGGETTO);
  }
  
  goAssociazioneSoggetto(){
      
      
//      if(this.elencoOrdinativiIncassoSelezionati[0].sigasPagamentiVersamentis!=null && 
//              this.elencoOrdinativiIncassoSelezionati[0].sigasPagamentiVersamentis.length>0){
//          this.ordinativiService.soggettoAssociato = this.elencoOrdinativiIncassoSelezionati[0].sigasPagamentiVersamentis[0].fkAnag;
//          this.router.navigateByUrl(Routing.ASSOCIA_VERSAMENTO);
//      }else{
//          this.router.navigateByUrl(Routing.ASSOCIA_SOGGETTO);
//      }
      
      this.router.navigateByUrl(Routing.ASSOCIA_VERSAMENTO);
      
  }
  
  //Non piu usato da eliminare
//  dettaglioSoggetto(consumo: ConsumiVO) {
//    this.anagraficaSoggettiService.dettaglioConsumo = consumo;
//    this.anagraficaSoggettiService.valida = consumo.validato === 'VALIDATO'?true:false;
//    if (this.anno != undefined)
//      this.anagraficaSoggettiService.annoDichiarazione = this.anno;
//    this.anagraficaSoggettiService.headerDichiarante = { 
//          idAnag: consumo.idAnag,
//          denominazione: consumo.denominazione
//    }
//    this.anagraficaSoggettiService.setCodiceAzienda = consumo.codiceAzienda;
//
//    this.router.navigateByUrl(Routing.DETTAGLIO_SOGGETTO)
//  }
  
  
  dettaglioOrdinativoIncasso(orinativoIncasso: OrdinativiIncassoVO){
      
  }
  
  clearMsg(){
      this.messageSuccess = "";
      this.showSuccess = false;
      this.showMessageError = false;
      this.messageError = "";
      this.errorMsg = false;
      this.eMsg = "";
  }

//  goExcel() {
//    this.loaderExcel = true;
//    this.anagraficaSoggettiService.annoDichiarazione = this.anno;
//    this.subscribers.scaricaExcelElencoSoggetti = this.anagraficaSoggettiService.scaricaExcelElencoSoggetto(this.elencoConsumi).subscribe(
//        res => {
//          this.loaderExcel = false;
//          saveAs(res, "export_elenco_anagrafica_soggetti_" + this.anno + '.xls');
//          
//        },
//        err => {
//            this.logger.error("errore in download excel");
//        }
//    );
//  }
  
  eliminaConciliazione(orinativoIncasso: OrdinativiIncassoVO) {
    this.ordinativoDaCancellare = orinativoIncasso;
    console.log('Sono in test popup');
      this.buttonConfirmText = "Elimina";
      this.buttonAnnullaText = "Indietro";
      this.messageDialog= "Eliminando la conciliazione sul pagamento selezionato verranno eliminati i seguenti versamenti per il soggetto";
      this.soggetto = orinativoIncasso.sigasPagamentiVersamentis[0].fkAnag.denominazione;
    }
  
  confermaEliminaConciliazione(orinativoIncasso: OrdinativiIncassoVO){
      this.loaderDT = true;
      this.subscribers.eliminaConciliazione = this.ordinativiService.eliminaConciliazione(orinativoIncasso)
      .subscribe(resp => {
          this.messageSuccess = "Conciliazione Eliminata correttamente";
          this.showSuccess = true;
          
          
          this.subscribers.ricercaCosumi = this.ordinativiService.ricercaOrdinativi()
          .subscribe(resp => {
            this.elencoOrdinativiIncasso = resp;
           
            this.loaderDT = false;
            this.rerender();
          }, err => {
            this.logger.error(err);
            this.loaderDT = false;
            this.rerender();
          });
      }, err => {
        this.logger.error(err);
        this.errorMsg = true;
        this.eMsg = "Si è verificato un errore in fase di eliminazione della conciliazione";
      });
      this.chiudiInfoPopUp();
  }
  
  public chiudiInfoPopUp() {
      $('#dialogConferma').modal('hide');
  }
  
  goBack(){
      this.clearMsg();
      this.router.navigateByUrl(Routing.ASSOCIA_SOGGETTO);
  }


}
