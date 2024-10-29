import { Component, OnInit, ViewChild ,TemplateRef, AfterViewInit, Renderer2} from '@angular/core';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { TassiVO } from '../../../commons/vo/tassi-vo';
import { TipoTassiVO } from '../../../commons/vo/tipo-tassi-vo';
import { DataTableIt } from "../../../commons/class/commons-data-table";
import { DataTableDirective } from 'angular-datatables';
import { Subject } from "rxjs";
import { TassiService } from '../../service/tassi.service';
import { LoggerService } from "../../../core/services/logger.service";
import { ConfermaTassoRequest } from "../../../commons/request/conferma-tasso-request";
import { ExceptionVO } from "../../../core/commons/vo/exceptionVO";
import { MessageRestError } from "../../../core/commons/commons-core";
import { SigasDialogComponent } from "../../../shared/component/sigas-dialog/sigas-dialog.component";


import * as moment from 'moment';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';

declare var jquery: any;
declare var $: any;

@Component({
  selector: 'app-tassi',
  templateUrl: './tassi.component.html',
  styleUrls: ['./tassi.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})

@DestroySubscribers()
export class TassiComponent implements OnInit, AfterViewInit {
 
  private dtOptions: any;
  private elencoTassi: Array<TassiVO>;  
  private dtTrigger: Subject<any> = new Subject();
  private loaderDT: boolean;
  private tassiModel: TassiVO;
  private tassoToDel: TassiVO;
  private disableButton: boolean;
  private tipoTassiModel: Array<TipoTassiVO>;
  private loaderTipoTassi: boolean;
  private isNewRecord: boolean;
  private showMessageError: boolean;
  private showSuccess: boolean;
//  private showInfo: boolean;  
  private messageError: string;
  private messageSuccess: string;
  private buttonConfirmText: string ;
  private buttonAnnullaText: string ;
  private messageDialog: string ;

  public subscribers: any = {};
  private dataInizio: any;
  private dataFine: any;

  @ViewChild(DataTableDirective) dtElement: DataTableDirective;
  @ViewChild(SigasDialogComponent) sigasDialog: SigasDialogComponent;

  constructor(
          private logger: LoggerService,
          private tassiService: TassiService,
          private renderer: Renderer2
        ) { }

  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);
  }

  ngOnInit() {
      window.scrollTo(0, 0);
      this.loaderDT = true;
      this.tassiModel = new TassiVO( 0, 0, 0 , new Date(), new Date(),0);
      this.tassoToDel = new TassiVO( 0, 0, 0, new Date(), new Date(),0);
      this.dtOptions = {
        head: 20,
        pagingType: 'full_numbers',
        pageLength: 5,
        processing: true,
        language: DataTableIt.language,
        responsive: true,
        searching: false,paging: false,
        order: [[2, 'asc']],
        columnDefs: [
          { className: 'dt-center', "targets": [0,1,2,3,4] },
          // { "orderable": false,  width: '1%', "targets": 0 },
          { width: '15%', targets: 0 },
          { width: '20%', targets: 1 },
          { width: '20%', targets: 2 },
          { width: '20%', targets: 3 },
          { width: '25%', targets: 4 }
        ]
      };
      this.disableButton = false;
      this.showMessageError = false;
      this.showSuccess = false;

      this.subscribers.getAll = this.tassiService.getAll()
      .subscribe(resp => {
        this.elencoTassi = resp;
        this.loaderDT = false;
        setTimeout(() => {
          this.dtTrigger.next();    
        });
      }, err => {
        this.logger.error(err);
        this.loaderDT = false;
    });
      this.loadTipoTassi();
  }

  
  @ViewChild('readOnlyTemplate') readOnlyTemplate: TemplateRef<any>;
  @ViewChild('editTemplate') editTemplate: TemplateRef<any>;
  
  private loadTipoTassi() {
      this.logger.info("carico i tipo tassi")
      this.loaderTipoTassi = false;
      this.subscribers.tipoTassi = this.tassiService.getAllTipoTassi().subscribe(data => {
          this.tipoTassiModel = data;
          this.loaderTipoTassi = true;
      }, err => {
          this.logger.error("Errore nel recupero dei Tipo Tassi");
          this.loaderTipoTassi = false;
      });
  }
  
  // Load either Read-Only Template or EditTemplate
  loadTemplate(tasso: TassiVO) {
      if (this.tassiModel && this.tassiModel.id == tasso.id) {
          return this.editTemplate;
      } else {
          return this.readOnlyTemplate;
      }
  }
  
  //CRUD
  addTasso(){      
      this.tassiModel = new TassiVO( 0, 1, 0, null, null, 0);
      this.showSuccess = false;
      this.showMessageError = false;
      this.loadTipoTassi(); 
      this.elencoTassi.push(this.tassiModel)
      this.isNewRecord = true;
      this.disableButton = true;
      
  }
  
  modificaTasso(tasso: TassiVO){
      console.log(tasso);
      this.tassiModel = tasso;     
      this.showSuccess = false;
      this.showMessageError = false;
      this.isNewRecord = false;
      this.disableButton = true;
      const _dataInizio = moment(tasso.dataStart);
      this.dataInizio = {
              year: _dataInizio.year(),
              month: Number(_dataInizio.format('MM')),
              day: Number(_dataInizio.format('DD'))
          };
      
      const _dataFine = moment(tasso.dataEnd);
      this.dataFine = {
              year: _dataFine.year(),
              month: Number(_dataFine.format('MM')),
              day: Number(_dataFine.format('DD'))
          };
      this.loadTipoTassi() ;  
  }
  
  saveTasso() {
      this.tassiModel.dataStart =  new Date(this.dataInizio.year, this.dataInizio.month - 1, this.dataInizio.day);
      this.tassiModel.dataEnd =  new Date(this.dataFine.year, this.dataFine.month - 1, this.dataFine.day);
      if (this.isNewRecord) {
          //add a new Tasso
          this.tassiService.addTasso(this.tassiModel).subscribe(resp => {
              this.tassiService.getAll()
              .subscribe(resp => {
                this.elencoTassi = resp;
                this.loaderDT = false;
                this.rerender();
              }, lerr => {
                this.logger.error(lerr);
                this.loaderDT = false;
              });
              this.messageSuccess = 'Tasso inserito con successo.';
              this.showSuccess = true;
              this.showMessageError = false;
              this.tassiModel = null;
              this.isNewRecord = false;
              this.disableButton = false;
          },err =>{
              if (err instanceof ExceptionVO && err.status == '200') {
                  console.log("Errore gestito");
                  this.showMessageError = true;
                  this.messageError = err.message;
              }    
          });
      } else {
          //edit the record
          this.tassiService.updateTasso(this.tassiModel.id, this.tassiModel).subscribe(resp => {
              this.tassiService.getAll()
              .subscribe(resp => {
                this.elencoTassi = resp;
                this.loaderDT = false;
                this.rerender();
              }, lerr => {
                this.logger.error(lerr);
                this.loaderDT = false;
              });
              this.messageSuccess = 'Tasso aggiornato con successo.';
              this.showSuccess = true;
              this.showMessageError = false;
              this.tassiModel = null;
          },err =>{
              if (err instanceof ExceptionVO && err.status == '200') {
                  console.log("Errore gestito");
                  this.showMessageError = true;
                  this.messageError = err.message;
              }    
          });
          this.disableButton = false;
      }
  }
  
  //Cancel edit
  annulla() {
      if (this.isNewRecord) {
          const index = this.elencoTassi.indexOf(this.tassiModel);
          this.elencoTassi.splice(index, 1);
      }
      this.subscribers.getAll = this.tassiService.getAll()
      .subscribe(resp => {
        this.elencoTassi = resp;
        this.loaderDT = false;
        this.rerender();
      }, lerr => {
        this.logger.error(lerr);
        this.loaderDT = false;
      });
      this.tassiModel = null; 
      this.showMessageError = false;
      this.disableButton = false;
  }
  
  eliminaTasso(tasso: TassiVO){
      this.tassoToDel = tasso;
      this.messageDialog = "Stai per cancellare un tasso di interesse";
      this.buttonAnnullaText = "Annulla";
      this.buttonConfirmText = "Conferma";
  }
  
   confermaEliminaTasso(){
      this.subscribers.elimina = this.tassiService.eliminaTasso(this.tassoToDel.id).subscribe(
              data => {
              this.tassiService.getAll()
               .subscribe(resp => {
                 this.elencoTassi = resp;
                 this.loaderDT = false;
                 this.rerender();
                 this.messageSuccess = 'Tasso cancellato con successo.';
                 this.showSuccess = true;
                 this.showMessageError = false;
                 
               }, err => {
                 this.logger.error(err);
                 this.loaderDT = false;
               });
            }, err => {
                this.logger.error(err);
                this.loaderDT = false;
            });
      this.chiudiInfoPopUp();
  }
  
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
