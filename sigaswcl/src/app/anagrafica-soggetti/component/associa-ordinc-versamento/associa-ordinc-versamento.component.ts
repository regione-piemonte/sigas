import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { Subject } from "rxjs";
import { DataTableDirective } from 'angular-datatables';
import { Routing } from '../../../commons/routing';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { DataTableIt } from "../../../commons/class/commons-data-table";
import { LoggerService } from "../../../core/services/logger.service";
import { AnagraficaSoggettiService } from '../../service/anagrafica-soggetti.service';
import { ConsumiVO } from '../../../commons/vo/consumi-vo';
import { RicercaConsumiRequest } from '../../../commons/request/ricerca-consumi-request';
import { saveAs } from "file-saver";
import { AllarmiSoggettoVO } from '../../../commons/vo/allarmi-soggetto-vo';
import { OrdinativiService } from '../../service/ordinativi.service';
import { VersamentiPrVO } from "../../../commons/vo/versamenti-pr-vo";
import { TipoVersamentiVO } from "../../../commons/vo/tipo-versamenti-vo";
import { OrdinativiIncassoVO } from '../../../commons/vo/ordinativi-incasso';
import { ExceptionVO } from "../../../core/commons/vo/exceptionVO";
import * as moment from 'moment';
import { SigasDialogComponent } from "../../../shared/component/sigas-dialog/sigas-dialog.component";
import { PagamentiVersamentiVO } from '../../../commons/vo/pagamenti-versamenti-vo';


import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';


@Component({
  selector: 'app-associa-ordinc-versamento',
  templateUrl: './associa-ordinc-versamento.component.html',
  styleUrls: ['./associa-ordinc-versamento.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})

@DestroySubscribers()
export class AssociaOrdincVersamentoComponent implements OnInit {


    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;

    private dtOptions: any;
    private loaderDT: boolean;
    private filterDisabled: boolean;
    private loaderYear: boolean;
    private elencoConsumi: Array<ConsumiVO>;
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

    public subscribers: any = {};
    private versamentoToSave: VersamentiPrVO;
    private pagamentoCorrente: OrdinativiIncassoVO;
    private indexPagamento: number;
    private listaAnni:Number[];
    
    private dataVersamento: any;
    private showMessageError: boolean;
    private showSuccess: boolean;
    private messageSuccess: string;
    private messageError: string;
    
    private loaderTipoVersamenti:boolean;
    private tipoVersamentiModel: Array<TipoVersamentiVO>;
    private totaleVersato: number;
    
    private importoConciliato: number;
    private importoResiduo: number;
    private loaderImporti: boolean;
    
    constructor(
      private router: Router,
      private logger: LoggerService,
      private anagraficaSoggettiService: AnagraficaSoggettiService,
      private ordinativiService: OrdinativiService
    ) { }

    ngOnInit() {
        
        
        this.dtOptions = {
                head: 20,
                pagingType: 'full_numbers',
                pageLength: 5,
                processing: true,
                language: DataTableIt.language,
                searching: false,paging: false,
                ordering: false,
                responsive: true,
                columnDefs: [
                  { className: 'dt-center', "targets": [0,1,2,3,4] },
                  { width: '20%', targets: 0 },
                  { width: '20%', targets: 1 },
                  { width: '20%', targets: 2 },
                  { width: '20%', targets: 3 },
                  { width: '20%', targets: 4 }
                ]
              };
        
        
        this.versamentoToSave = new VersamentiPrVO(null, null, null, null,'', '' ,new TipoVersamentiVO(null,'',''),null,null,null,'',null,0);
        this.indexPagamento = 0;
        this.totaleVersato = 0;
        this.importoConciliato = 0;
        this.importoResiduo = 0;
        this.pagamentoCorrente = this.ordinativiService.listaOrdinativiIncassoSelezionati[this.indexPagamento];
        this.calcolaAnniRecenti();
        this.versamentoToSave.idAnag = this.ordinativiService.soggettoAssociato.idAnag;
        this.loadTipoVersamenti();
        
        const _dataValiditaStart = moment(this.pagamentoCorrente.dataEmissione);
        this.dataVersamento = {
                year: _dataValiditaStart.year(),
                month: Number(_dataValiditaStart.format('MM')),
                day: Number(_dataValiditaStart.format('DD'))
            };
        
        
        
        this.loaderImporti = true;
        this.loaderDT = true;
        
        setTimeout(() => {
            for (let pagamentoVers of this.pagamentoCorrente.sigasPagamentiVersamentis) {
                this.importoConciliato = this.importoConciliato + pagamentoVers.fkVersamento.importo;
               
            }
            
            this.importoResiduo = this.pagamentoCorrente.importoAttuale - this.importoConciliato;
            
            this.loaderImporti = false;
//            if(this.pagamentoCorrente.sigasPagamentiVersamentis!=null && 
//                    this.pagamentoCorrente.sigasPagamentiVersamentis.length>0){
                this.loaderDT = false;
                setTimeout(() => {
                    this.dtTrigger.next(); 
                  });
//            }
            this.versamentoToSave.importo = Math.round(this.importoResiduo * 100) / 100;
        });
        
        
        
    }
    
    clearAll() {
        this.versamentoToSave = new VersamentiPrVO(null, null, null, null,'', '' ,new TipoVersamentiVO(null,'',''),null,null,null,'',null,0);   
        //console.log(this.versamentoToSave);
      }


    annullaAssoc(){
        if(this.ordinativiService.listaOrdinativiIncassoSelezionati.length>this.indexPagamento+1){
            this.indexPagamento = this.indexPagamento +1;
            this.pagamentoCorrente = this.ordinativiService.listaOrdinativiIncassoSelezionati[this.indexPagamento];   
        }
    }
    
    goBack(){
//        this.router.navigateByUrl(Routing.ASSOCIA_SOGGETTO);
      this.router.navigateByUrl(Routing.ORDINATIVI_INCASSO);
    }
    
    calcolaAnniRecenti() {
        this.listaAnni = new Array();
        var current = (new Date()).getFullYear();
        for (var i = 0; i < 5; i++) {
          this.listaAnni[i] = current;
          current--;
        }
     }
    
    
    conciliaParziale() {
            this.clearMsg();
            this.versamentoToSave.dataVersamento =  new Date(this.dataVersamento.year, this.dataVersamento.month - 1, this.dataVersamento.day);
            this.loaderImporti = true;
            this.loaderDT = true;
            this.subscribers.confermaInserimentoVersamento = this.ordinativiService.confermaInserimentoVersamentoContabilia(this.versamentoToSave, this.pagamentoCorrente, false, false)
                .subscribe( resp => {
//                    if(this.pagamentoCorrente.sigasPagamentiVersamentis!=null && this.pagamentoCorrente.sigasPagamentiVersamentis.length>0){
//                        this.pagamentoCorrente.sigasPagamentiVersamentis.push(resp);
//                    }else{
//                        this.pagamentoCorrente.sigasPagamentiVersamentis = new Array<PagamentiVersamentiVO>(resp);
//                    }
                    this.pagamentoCorrente.sigasPagamentiVersamentis = resp;
                    
                    
                    this.loaderImporti = false;
                    this.loaderDT = false;
                    this.rerender();
                    setTimeout(() => {
                        this.importoConciliato = 0;
                        for (let pagamentoVers of this.pagamentoCorrente.sigasPagamentiVersamentis) {
                            this.importoConciliato = this.importoConciliato + pagamentoVers.fkVersamento.importo;
                        }

                        
                        if(this.pagamentoCorrente.sigasPagamentiVersamentis!=null && 
                                this.pagamentoCorrente.sigasPagamentiVersamentis.length>0){
                            
                            setTimeout(() => {
                                this.importoResiduo = this.pagamentoCorrente.importoAttuale - this.importoConciliato;
                                this.versamentoToSave.importo =  Math.round(this.importoResiduo * 100) / 100;
//                                this.dtTrigger.next(); 
                                  
                              });
                        }
                        
                        
                        
                    }, 500);
                    
                    this.messageSuccess = "Versamento salvato correttamente";
                    this.showSuccess = true;
                    this.versamentoToSave.importo = Math.round(this.importoResiduo * 100) / 100;
                    this.versamentoToSave.provincia = null;
                }, err => {
                    if (err instanceof ExceptionVO && err.status == '200') {
//                        if(err.errorCode="VGP"){
//                            this.confermaInserimentoParziale();
//                        }else{
                            console.log("Errore gestito");
                            this.showMessageError = true;
                            this.messageError = err.message;
                            this.loaderImporti = false;
                            this.loaderDT = false;
//                        }
                    } 
                    this.logger.error( 'errore salvataggio versamento' );
                } );
            
            
        
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
    
    concilia(){
//        this.ordinativiService.conciliaPagamento(this.pagamentoCorrente);
        this.versamentoToSave.dataVersamento =  new Date(this.dataVersamento.year, this.dataVersamento.month - 1, this.dataVersamento.day);
        
        if(this.ordinativiService.listaOrdinativiIncassoSelezionati.length>this.indexPagamento+1){
//            this.ordinativiService.conciliaPagamento(this.pagamentoCorrente).subscribe(resp => {
                this.ordinativiService.confermaInserimentoVersamentoContabilia(this.versamentoToSave, this.pagamentoCorrente, true, false).subscribe( resp => {
                this.indexPagamento = this.indexPagamento +1;
                this.pagamentoCorrente = this.ordinativiService.listaOrdinativiIncassoSelezionati[this.indexPagamento];   
                this.messageSuccess = "Pagamento Conciliato correttamente";
                this.showSuccess = true;
            }, err => {
                this.logger.error("Errore nel recupero tipo versamento");
                this.loaderTipoVersamenti = false;
            });
            
            
        }else{
            
//            this.ordinativiService.conciliaPagamento(this.pagamentoCorrente).subscribe(resp => {
              this.ordinativiService.confermaInserimentoVersamentoContabilia(this.versamentoToSave, this.pagamentoCorrente, true, false).subscribe( resp => {
                  this.ordinativiService.setMessageSuccess = "Pagamento Conciliato correttamente";
                  this.ordinativiService.setShowSuccess = true;
//                  this.router.navigateByUrl(Routing.ASSOCIA_SOGGETTO);  
                  this.router.navigateByUrl(Routing.ORDINATIVI_INCASSO);
                
                
               
            }, err => {
                if (err instanceof ExceptionVO && err.status == '200') {
//                    if(err.errorCode="VGP"){
//                        this.confermaInserimento();
//                    }else{
                        console.log("Errore gestito");
                        this.showMessageError = true;
                        this.messageError = err.message;
                        this.loaderImporti = false;
                        this.loaderDT = false;
//                    }
                } 
                
                this.logger.error( 'errore salvataggio versamento'+err);
            });
            
        }
            
    }
    @ViewChild(SigasDialogComponent) sigasDialog: SigasDialogComponent;
    buttonConfirmText: string;
    buttonAnnullaText: string;
    messageDialog: string;
    
    confermaInserimento() {
        
        this.buttonConfirmText = "Conferma";
        this.buttonAnnullaText = "Annulla";
        this.messageDialog= "ATTENZIONE! Il versamento è già presente procedendo il versamento sarà sovrascritto dal presente. Continuare?";
        this.sigasDialog.open();
        
        this.subscribers.save = this.sigasDialog.salvaAction.subscribe(data => {
            this.ordinativiService.confermaInserimentoVersamentoContabilia(this.versamentoToSave, this.pagamentoCorrente, true, true).subscribe( resp => {
                this.ordinativiService.setMessageSuccess = "Pagamento Conciliato correttamente";
                this.ordinativiService.setShowSuccess = true;
                this.router.navigateByUrl(Routing.ORDINATIVI_INCASSO);  
             
          }, err => {
              if (err instanceof ExceptionVO && err.status == '200') {
                  console.log("Errore gestito");
                  this.showMessageError = true;
                  this.messageError = err.message;
              } 
              
              this.logger.error( 'errore salvataggio versamento'+err);
          });
                    
      }, err => {
          this.logger.error(err);
      });

        this.annullaPopUp();
      }
    
    
    confermaInserimentoParziale() {
        
        this.buttonConfirmText = "Conferma";
        this.buttonAnnullaText = "Annulla";
        this.messageDialog= "ATTENZIONE! Il versamento è già presente. Procedendo il versamento già presente sarà sovrascritto dal presente. Continuare?";
        this.sigasDialog.open();
        
        this.subscribers.save = this.sigasDialog.salvaAction.subscribe(data => {
            this.ordinativiService.confermaInserimentoVersamentoContabilia(this.versamentoToSave, this.pagamentoCorrente, false, true).subscribe( resp => {
//                if(this.pagamentoCorrente.sigasPagamentiVersamentis!=null && this.pagamentoCorrente.sigasPagamentiVersamentis.length>0){
//                    this.pagamentoCorrente.sigasPagamentiVersamentis.push(resp);
//                }else{
//                    this.pagamentoCorrente.sigasPagamentiVersamentis = new Array<PagamentiVersamentiVO>(resp);
//                }
                
                this.pagamentoCorrente.sigasPagamentiVersamentis = resp;
                
                this.loaderImporti = false;
                this.loaderDT = false;
                this.rerender();
                setTimeout(() => {
                    this.importoConciliato = 0;
                    for (let pagamentoVers of this.pagamentoCorrente.sigasPagamentiVersamentis) {
                        this.importoConciliato = this.importoConciliato + pagamentoVers.fkVersamento.importo;
                    }

                    
                    if(this.pagamentoCorrente.sigasPagamentiVersamentis!=null && 
                            this.pagamentoCorrente.sigasPagamentiVersamentis.length>0){
                        
                        setTimeout(() => {
                            this.importoResiduo = this.pagamentoCorrente.importoAttuale - this.importoConciliato;
                            this.versamentoToSave.importo =  Math.round(this.importoResiduo * 100) / 100;
//                            this.dtTrigger.next(); 
                              
                          });
                    }
                    
                    
                    
                }, 500);
                
                this.messageSuccess = "Versamento salvato correttamente";
                this.showSuccess = true;
             
          }, err => {
              if (err instanceof ExceptionVO && err.status == '200') {
                  console.log("Errore gestito");
                  this.showMessageError = true;
                  this.messageError = err.message;
              } 
              
              this.logger.error( 'errore salvataggio versamento'+err);
          });
                    
      }, err => {
          this.logger.error(err);
      });

        this.annullaPopUpConfermaParziale();
      }

    annullaPopUpConfermaParziale() {
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
        
        this.loaderDT = false;
        this.loaderImporti = false;
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
    
    loadTipoVersamenti() {
        this.logger.info("carico i tipo versamenti")
        this.loaderTipoVersamenti = true;
        this.subscribers.tipoVersamenti = this.anagraficaSoggettiService.ricercaTipoVersamenti().subscribe(data => {
            this.loaderTipoVersamenti = false;
            this.tipoVersamentiModel = data;
        }, err => {
            this.logger.error("Errore nel recupero tipo versamento");
            this.loaderTipoVersamenti = false;
        });
    }

}
