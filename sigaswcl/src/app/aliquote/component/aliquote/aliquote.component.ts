import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { AliquoteService } from '../../service/aliquote.service';
import { LoggerService } from '../../../core/services/logger.service';
import { DataTableIt } from '../../../commons/class/commons-data-table';
import { DataTableDirective } from 'angular-datatables';
import { AliquotaVO } from '../../../commons/vo/aliquota-vo';
import { TipoAliquotaVO } from '../../../commons/vo/tipo-aliquota-vo';
import { Subject } from 'rxjs';
import { ConfermaAliquotaRequest } from '../../../commons/request/conferma-aliquota-request';
import { SigasDialogComponent } from '../../../shared/component/sigas-dialog/sigas-dialog.component';
import { TipoConsumoVO } from '../../../commons/vo/tipo-consumo-vo';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';
import * as moment from 'moment';


declare var jquery: any;
declare var $: any;

@Component({
  selector: 'app-aliquote',
  templateUrl: './aliquote.component.html',
  styleUrls: ['./aliquote.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})

@DestroySubscribers()
export class AliquoteComponent implements OnInit {

  private loaderModPage: boolean;
  private loaderPage: boolean;
  private nuovaAliquota: boolean;
  private updateAliquota: boolean;
  private dtTrigger: Subject<any> = new Subject();
  private dtOptions: any;
  private elencoAliquote: Array<AliquotaVO>;
  private elencoAnnualita: Array<String>;
  private aliquotaToSave: AliquotaVO;
  private aliquota: AliquotaVO;
  private tipiAliquotaModel: Array<TipoAliquotaVO>;
  private idTipoAliquota: number;
  private confermaAliquotaRequest: ConfermaAliquotaRequest;
  private aliquotaToDel: AliquotaVO;
  private buttonConfirmText: string;
  private buttonAnnullaText: string;
  private messageDialog: string;
  private messageError: string;
  private messageSuccess: string;
  private showMessageError: boolean;
  private showSuccess: boolean;
  private errorShow:boolean;
  private errorMsg: string;
  private dataValiditaDal: any;
  private dataValiditaAl: any;

  public subscribers: any = {};

    public listAliquote: Array<string> = ['civile','industriale','nuovi allacciamenti'];
  constructor(
    private logger: LoggerService,
    private aliquoteService: AliquoteService
  ) { }

  @ViewChild(SigasDialogComponent) sigasDialog: SigasDialogComponent;
  @ViewChild(DataTableDirective) dtElement: DataTableDirective;

  ngOnInit() {
    this.dtOptions = {
      head: 20,
      pagingType: 'full_numbers',
      pageLength: 10,
      processing: true,
      language: DataTableIt.language,
      responsive: true,
      order: [],
      columnDefs: [
        { className: 'dt-center', 'targets': [0, 1, 2, 3, 4, 5, 6] },
        // { "orderable": false,  width: '1%', "targets": 0 },
        { width: '10%', targets: 0 },
        { width: '15%', targets: 1 },
        { width: '20%', targets: 2 },
        { width: '15%', targets: 3 },
        { width: '15%', targets: 4 },
        { width: '5%', targets: 5 },
        { width: '20%', targets: 6 }
      ]
    };

    // this.subscribers.ricercaAliquota = this.aliquoteService.ricercaAliquote()
    //   .subscribe(res => {
    //     this.elencoAliquote = res;
    //     this.loaderPage = false;
    //   }, err => {
    //     this.logger.error('errore ');
    //     this.loaderPage = false;
    //   });

    this.reInitAliquota();

  }

  goBack() {
    this.loaderModPage = true;
    this.nuovaAliquota = false;
    this.updateAliquota = false;
    this.loaderModPage = false;
    this.showSuccess = false;
    this.showMessageError = false;
    this.errorShow = false;
    this.reInitAliquota();
  }

  addRow() {
    this.clearAll();
    this.loaderModPage = true;
    this.nuovaAliquota = true;
    this.loaderModPage = false;
    this.errorShow = false;
    this.showSuccess = false;
    this.showMessageError = false;
                 
  }

  clearAll() {
    this.aliquotaToSave = new AliquotaVO(null, 0, '', new TipoAliquotaVO(0, '', '', false, '', new TipoConsumoVO(0,'','')), null, null, 0, true);
    this.dataValiditaDal = null;
    this.dataValiditaAl = null;
    this.tipiAliquotaModel = null;
  }

  editRow(al: AliquotaVO) {
    this.loaderModPage = true;
    this.updateAliquota = true;
    this.loaderModPage = false;
    this.aliquotaToSave = al;
    const _dataValiditaStart = moment(this.aliquotaToSave.validitaStart);
    this.dataValiditaDal = {
            year: _dataValiditaStart.year(),
            month: Number(_dataValiditaStart.format('MM')),
            day: Number(_dataValiditaStart.format('DD'))
        };
    
    const _dataValiditaEnd = moment(this.aliquotaToSave.validitaEnd);
    this.dataValiditaAl = {
            year: _dataValiditaEnd.year(),
            month: Number(_dataValiditaEnd.format('MM')),
            day: Number(_dataValiditaEnd.format('DD'))
        };
    
    this.ricercaDescrizioni(this.aliquotaToSave.tipoAliquote.tipo);
  }

  ricercaDescrizioni(tipo: string) {
    this.aliquoteService.tipoAliquota = tipo;
    this.subscribers.ricercaTipiAliquoteByTipo = this.aliquoteService.ricercaTipiAliquoteByTipo()
      .subscribe(res => {
        this.tipiAliquotaModel = res;
        console.log(this.aliquotaToSave);
        this.loaderPage = false;
      }, err => {
        this.logger.error('errore ');
        this.loaderPage = false;
      });
  }

  setTipoAliquota(id: string) {
    this.idTipoAliquota = Number.parseInt(id);
  }

  onSubmitSalva() {
//    this.buttonConfirmText = 'Conferma';
//    this.buttonAnnullaText = 'Annulla';
//    this.messageDialog = 'Stai per salvare una nuova aliquota!';
//    this.sigasDialog.open();

//    this.subscribers.save = this.sigasDialog.salvaAction.subscribe(data => {
      console.log('INIZIO');
      console.log(this.aliquotaToSave);
      console.log(this.aliquotaToSave.tipoAliquote);

      this.loaderModPage = true;
      this.aliquoteService.idAliquota = this.idTipoAliquota;
      this.subscribers.ricercaTipiAliquoteByDescrizione = this.aliquoteService.ricercaTipiAliquoteByDescrizione()
      .subscribe(res => {
        this.aliquotaToSave.tipoAliquote = res;
        console.log(this.aliquotaToSave.tipoAliquote);
        console.log(this.aliquotaToSave);
        this.aliquotaToSave.validitaStart = new Date(this.dataValiditaDal.year, this.dataValiditaDal.month - 1, this.dataValiditaDal.day);
        this.aliquotaToSave.validitaEnd = new Date(this.dataValiditaAl.year, this.dataValiditaAl.month - 1, this.dataValiditaAl.day);
        this.aliquoteService.confermaInserimentoAliquota(this.aliquotaToSave)
        .subscribe( resp => {
              console.log(resp);
              this.confermaAliquotaRequest = new ConfermaAliquotaRequest(this.aliquotaToSave);
              console.log(this.confermaAliquotaRequest);
              this.nuovaAliquota = false;
              this.updateAliquota = false;
              this.loaderModPage = false;
              this.errorShow = false;
              this.showSuccess = false;
              this.reInitAliquota();
          }, err => {
            this.logger.error('errore salvataggio aliquota');
            this.loaderModPage = false;

            this.errorShow = true;
            this.errorMsg = err.message;

        });
      }, err => {
        this.logger.error('errore ');
        this.loaderPage = false;
      });
//    }, err => {
//      this.logger.error(err);
//    });

//    this.annullaPopUp();
  }
  
  limiteNumber(value: number){
      if(value > 9999){
          var daconvertire = new String(value);
          if (daconvertire.length > 4) {
              this.aliquotaToSave.aliquota = Number(daconvertire.slice(0, 4));
          }
      }
      
      
  }

  onClickModifica() {
//    this.buttonConfirmText = 'Conferma';
//    this.buttonAnnullaText = 'Annulla';
//    this.messageDialog = 'Stai per modificare l aliquota!';
//    this.sigasDialog.open();

//     this.subscribers.save = this.sigasDialog.salvaAction.subscribe(data => {

      console.log(this.aliquotaToSave);
      

      this.loaderModPage = true;
      this.aliquoteService.idAliquota = this.aliquotaToSave.tipoAliquote.idTipoAliquota;
      this.aliquoteService.descrizioneAliquota = this.aliquotaToSave.tipoAliquote.descrizione;
      this.subscribers.ricercaTipiAliquoteByDescrizione = this.aliquoteService.ricercaTipiAliquoteByDescrizione()
      .subscribe(res => {
        this.aliquotaToSave.tipoAliquote = res;
        this.aliquotaToSave.validitaStart = new Date(this.dataValiditaDal.year, this.dataValiditaDal.month - 1, this.dataValiditaDal.day);
        this.aliquotaToSave.validitaEnd = new Date(this.dataValiditaAl.year, this.dataValiditaAl.month - 1, this.dataValiditaAl.day);
        console.log(this.aliquotaToSave);
        this.aliquoteService.confermaModificaAliquota(this.aliquotaToSave)
        .subscribe( resp => {
              console.log(resp);
              this.confermaAliquotaRequest = new ConfermaAliquotaRequest(this.aliquotaToSave);
              console.log(this.confermaAliquotaRequest);
              this.nuovaAliquota = false;
              this.updateAliquota = false;
              this.loaderModPage = false;
              this.errorShow = false;
              this.showSuccess = false;
              this.reInitAliquota();              
          }, err => {
            this.logger.error('errore salvataggio aliquota');
            this.loaderModPage = false;

            this.errorShow = true;
            this.errorMsg = err.message;
        });
      }, err => {
        this.logger.error('errore ');
        this.loaderPage = false;
      });

//    }, err => {
//      this.logger.error(err);
//      this.loaderPage = false;
//    });

//    this.annullaPopUp();
  }

  reInitAliquota() {
    this.subscribers.ricercaAliquota = this.aliquoteService.ricercaAliquote()
      .subscribe(res => {
          this.elencoAliquote = res;
          this.loaderPage = false;
          this.aliquoteService.ricercaPerAnno().subscribe(resp=>{
              this.elencoAnnualita = resp;
              for (var _i = 0; _i < this.elencoAliquote.length; _i++) {
                  this.elencoAliquote[_i].modificabile = true;
              }
              for (let anno of this.elencoAnnualita) {
                      for (var _i = 0; _i < this.elencoAliquote.length; _i++) {                          
                          if(new Date(this.elencoAliquote[_i].validitaStart).getFullYear().toString()<=anno && 
                                  new Date(this.elencoAliquote[_i].validitaEnd).getFullYear().toString()>=anno){
                              this.elencoAliquote[_i].modificabile = false;
                          }
                  }
                  
              }
              
              
          }, err => {
              this.logger.error('errore ');
              this.loaderPage = false;
            });
        
      }, err => {
        this.logger.error('errore ');
        this.loaderPage = false;
      });

    this.aliquotaToSave =  new AliquotaVO(null, 0, '', new TipoAliquotaVO(0, '', '', false, '', new TipoConsumoVO(0 , '','')), null, null, 0, true);
    this.errorShow = false;
    this.showSuccess = false;
  }
  
//deleteRow(aliquota: AliquotaVO) {
//console.log(aliquota.id);
//this.aliquotaToDel = aliquota;
//this.buttonConfirmText = 'Conferma';
//this.buttonAnnullaText = 'Annulla';
//this.messageDialog = 'Stai per cancellare un aliquota!';
//this.sigasDialog.open();
//
//this.subscribers.save = this.sigasDialog.salvaAction.subscribe(data => {
//  this.subscribers.elimina = this.aliquoteService.eliminaAliquota(this.aliquotaToDel.id).subscribe(
//    () => {
//      const index = this.elencoAliquote.indexOf(this.aliquotaToDel);
//      this.elencoAliquote.splice(index, 1);
//    }
//  );
//}, err => {
//  this.logger.error(err);
//});
//
//this.annullaPopUp();
//}
  
  deleteRow(aliquota: AliquotaVO){
      console.log(aliquota.id);
      this.aliquotaToDel = aliquota;
      this.messageDialog = "Stai per cancellare un aliquota!";
      this.buttonAnnullaText = "Annulla";
      this.buttonConfirmText = "Conferma";
  }
    
  confermaEliminaAliquota(){
      this.loaderModPage = true;
      this.subscribers.elimina = this.aliquoteService.eliminaAliquota(this.aliquotaToDel.id).subscribe(
              data => {
              this.aliquoteService.ricercaAliquote()
               .subscribe(resp => {
                 this.elencoAliquote = resp;
                 this.aliquoteService.ricercaPerAnno().subscribe(resp=>{
                     this.elencoAnnualita = resp;
                     for (var _i = 0; _i < this.elencoAliquote.length; _i++) {
                         this.elencoAliquote[_i].modificabile = true;
                     }
                     for (let anno of this.elencoAnnualita) {
                             for (var _i = 0; _i < this.elencoAliquote.length; _i++) {                          
                                 if(new Date(this.elencoAliquote[_i].validitaStart).getFullYear().toString()<=anno && 
                                         new Date(this.elencoAliquote[_i].validitaEnd).getFullYear().toString()>=anno){
                                     this.elencoAliquote[_i].modificabile = false;
                                 }
                         }
                         
                     }
                     this.loaderModPage = false;
                     this.rerender();
                 }, err => {
                     this.logger.error('errore ');
                     this.loaderPage = false;
                     this.loaderModPage = false;
                   });
                 
                 this.messageSuccess = 'Aliquota cancellata con successo.';
                 this.showSuccess = true;
                 this.showMessageError = false;
                 this.errorShow = false;
               }, err => {
                 this.logger.error(err);
               });
            }, err => {
                this.logger.error(err);
                this.errorShow = true;
                this.errorMsg = err.message;
            });
      this.chiudiInfoPopUp();
  }
  
//  annullaPopUp() {
//    this.subscribers.close = this.sigasDialog.closeAction.subscribe(data => {
//      this.subscribers.close.unsubscribe();
//      this.subscribers.save.unsubscribe();
//      this.subscribers.XAction.unsubscribe();
//    }, err => {
//      this.logger.error(err);
//    });
//    
//    this.subscribers.XAction = this.sigasDialog.XAction.subscribe(data => {
//      this.subscribers.close.unsubscribe();
//      this.subscribers.save.unsubscribe();
//      this.subscribers.XAction.unsubscribe();
//    }, err => {
//      this.logger.error(err);
//    });
//  }
  
  rerender(): void {
      this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
         dtInstance.destroy();
         this.dtTrigger.next();     
     });
    }
  
  
  public chiudiInfoPopUp() {
      $('#dialogConferma').modal('hide');
  }
  
}
