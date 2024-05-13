import { Component, OnInit, ViewChild, ElementRef, Input, Output, EventEmitter, AfterViewInit, Renderer2 } from '@angular/core';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { DocumentazioneService } from '../../service/documentazione.service';
import { LoggerService } from '../../../core/services/logger.service';
import { DataTableIt } from '../../../commons/class/commons-data-table';
import { DataTableDirective } from 'angular-datatables'; 
import { Subject } from 'rxjs';
import { SigasDialogComponent } from '../../../shared/component/sigas-dialog/sigas-dialog.component';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap'; 
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';
import * as moment from 'moment';
import { Router } from '@angular/router';
import { AnagraficaSoggettoVO } from '../../../commons/vo/soggetti-vo';
import { DocumentiVO } from '../../../commons/vo/documenti-vo'; 
import { TipoDocumentoVO } from '../../../commons/vo/tipo-documento-vo'; 
import { StatoDocumentoVO } from '../../../commons/vo/stato-documento-vo'; 
import { ConfermaDocumentazioneRequest } from '../../../commons/request/conferma-documentazione-request';
import { FileUploader, FileItem } from 'ng2-file-upload';
import { AllegatoMultipleFieldVO } from '../../../commons/vo/allegato-multiple-field-vo';
import { SalvaAllegatoRequest } from "../../../commons/request/salva-allegato-request"; 
import { SalvaAllegatoMultipleRequest } from "../../../commons/request/salva-allegato-multiple-request";
import { AllegatoDocumentazioneVO } from '../../../commons/vo/allegato-documentazione-vo';
import {Constants} from '../../../commons/class/constants';
import {UtilityService} from '../../../core/services/utility/utility.service';
import {MessageEnum} from '../../../core/services/utility/enum/messageEnum';

declare var jquery: any;
declare var $: any;
const URL = 'https://evening-anchorage-3159.herokuapp.com/api/';
@Component({
  selector: 'app-documentazione',
  templateUrl: './documentazione.component.html',
  styleUrls: ['./documentazione.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})

@DestroySubscribers()
export class DocumentazioneComponent implements OnInit, AfterViewInit {
    @ViewChild(SigasDialogComponent) sharedDialog: SigasDialogComponent;

    public riferimentoProtocolloRegex = Constants.REGEX_PROTOCOLLO_ANNO;
    public subscribers: any = {};
    
    private loaderDT: boolean;
    private dtTrigger: Subject<any> = new Subject();
    private dtOptions: any;
    private showMessage: boolean;
    private message: string;
    private levelMessage: string;
    private listaAziende:Array<AnagraficaSoggettoVO>;
    private listaTipoDocumento:Array<TipoDocumentoVO>;
    private listaTipoDocumentoMacro:Array<TipoDocumentoVO>;
    private listaTipoComunicazione:Array<TipoDocumentoVO>;
    private listaTipoRimborso:Array<TipoDocumentoVO>;
    private yearsList: Number[];
    private documentoToSave:  DocumentiVO;
    private fileToUpload: File = null;
    private fileCaricato: boolean;
    private cfPivaEsistente: boolean;
    private loaderPage: boolean;
    
    public eliminaM : boolean = true;
    public confermaM : boolean = true;
    //public maxSize: number = 5242880;
    public maxSize: number = 20971520;
    public maxAllegati: number = 20;
    public sizeAlert: boolean = false;
    public descAllegatoAlert: boolean = false;
    
    public numAllegatiAlert: boolean = false;
    public typeAlert: boolean = false;
    public nuovoAllegato: SalvaAllegatoRequest;
    public nomeAllegatoTmp: string;     
    
    allowedFilesType = ["pdf","tiff","jpg","jpeg","p7m"];
    allowedFilesTypeMessage : string;
    
    @Output()
    onNewFile = new EventEmitter<any>();
    @Input()
    allegatiMultipli: boolean;
    @Output()
    multipli = new EventEmitter<boolean>();
    
    public nuovoAllegatoMultiple: SalvaAllegatoMultipleRequest;
    
    sigasAllegatos: Array<AllegatoDocumentazioneVO>;
    
    public comunicazioniVarie: boolean;
    public rimborso: boolean;
    public accertamento: boolean;
    
    private yearsListAccertamento: Number[];
    
    
  constructor(
    private logger: LoggerService,
    private documentazioneService: DocumentazioneService,
    private utilityService: UtilityService,
    private router: Router,
    private renderer: Renderer2
  ) { }
  
  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);
  }

  @ViewChild(DataTableDirective) dtElement: DataTableDirective;

  ngOnInit(): void {  
      this.documentoToSave = new DocumentiVO(0,null,
              null,null,null,null,'','',null,'',null, new StatoDocumentoVO(null,'',''),null,null,null,'',null,null,null,null,null);
      this.logger.info("init Documentazione Component");
      this.clearMsg();
 
      this.documentazioneService.ricercaAziendeAccreditato().subscribe(data => {
          this.listaAziende = data;
      }, err => {
          this.logger.error(err);        
      });
      
      this.documentazioneService.listaTipoDocumenti().subscribe(data => {
          this.listaTipoDocumento = data;
          
          this.listaTipoDocumentoMacro = this.listaTipoDocumento.filter(tipoDocumento => 
          tipoDocumento.idTipoDocumentoPadre === null
          )
      }, err => {
          this.logger.error(err);        
      });
      
      this.calcolaAnniRecenti();
      
      this.initModel();
      
    }
  
  initModel() {
      this.nuovoAllegato = new SalvaAllegatoRequest();
      this.nuovoAllegatoMultiple = new SalvaAllegatoMultipleRequest();
      this.comunicazioniVarie = false;
      this.rimborso = false;
      this.accertamento = false;
  }
  
  calcolaAnniRecenti() {
      this.yearsList = new Array();
      var current = (new Date()).getFullYear();
      for (var i = 0; i < 5; i++) {
        this.yearsList[i] = current;
        current--;
      } 
      return this.yearsList;
    }

    salvaDocumentazione() {
        this.sigasAllegatos = new Array<AllegatoDocumentazioneVO>();
        this.documentazioneService.confermaDocumentazione = new ConfermaDocumentazioneRequest(this.documentoToSave);
        this.loaderPage = true;
        this.documentazioneService.salvaDocumentazione(this.fileToUpload,this.nuovoAllegatoMultiple.allegati ).subscribe(data => {
            this.loaderPage = false;
            this.showMessage = true;
            this.levelMessage = "SUCCESS";
            this.message = "Documentazione caricata correttamente.";
            this.resetForm();
        }, err => {
            this.utilityService.getMessageByKey(err.errorCode)
            	.subscribe(msg => {
                   this.message = msg.message || '';
                        }, error => {
                            this.logger.error(error);
                        });
            this.logger.error(err); 
            this.loaderPage = false;
            this.showMessage = true;
            this.levelMessage = "DANGER";
            this.message = err.message;
            setTimeout(() => {
                this.clearMsg();
            }, 10000);
        });
    }

    clearMsg(){
        this.message = "";
        this.showMessage = false;
        this.levelMessage = "";
    }
    
    goBack(){
//        this.router.navigate([RoutingAccreditamento.ACCREDITAMENTO_ELENCO]);
    }
 
    
    changeAzienda(event: any) {
        this.cfPivaEsistente = false;
        this.listaAziende.forEach((item, index) => {
            if(this.documentoToSave.anagraficaSoggettoVO.denominazione!=undefined && item.denominazione==this.documentoToSave.anagraficaSoggettoVO.denominazione){
                this.documentoToSave.cfPiva= item.cfPiva;
                if(item.cfPiva!=null){
                    this.cfPivaEsistente = true;
                }
                
            }
        });
    }
    
    handleFileInput(files: FileList) {
        let type : string = "";
        let array :string[] = files.item(0).name.split(".");
        if(array.length > 1){
            type = array[array.length-1];
        }
        if (files.item(0).size > this.maxSize) {
            this.sizeAlert = true;
            this.utilityService.getMessageByKey(MessageEnum.DOCDIMMAX)
                        .subscribe(msg => {
                           this.message = msg.message || '';
                        }, error => {
                            this.logger.error(error);
                        });
            this.logger.info("File size: " + files.item(0).size);
        } else {  
                if(this.allowedFilesType.filter(x => x==type.toLowerCase()).length==0){                
                this.typeAlert = true;
                this.confermaM = true;       
                
                this.message = "I documenti caricati non sono idonei all'archiviazione su Doqui ACTA. Si prega di ricontrollare l'estensione dei file selezionati";
                this.levelMessage = "DANGER";
                this.showMessage = true;
                setTimeout(() => {
                    this.message = "";
                    this.levelMessage = "";
                    this.showMessage = false;
                }, 5000);
            } else{
                this.fileToUpload = files.item(0);
                console.log(this.fileToUpload);
                this.fileCaricato = true;
                //this.documentoToSave.nomeFile = this.fileToUpload.name;
                if(array.length > 1){
                    this.documentoToSave.nomeFile = array[array.length-1]
                    type = array[array.length-1];
                }else{

                } 
            }
        }
        
      }
    
    changeTipoDocumento(event: any){
        if(this.documentoToSave.tipoDocumentoVO.codiceTipoDocumento=='COMV'){
            this.comunicazioniVarie = true;
            this.rimborso = false;
            this.accertamento = false;
            this.listaTipoComunicazione = this.listaTipoDocumento.filter(tipoDocumento => 
            tipoDocumento.idTipoDocumentoPadre === this.documentoToSave.tipoDocumentoVO.idTipoDocumento
            )
        }else if(this.documentoToSave.tipoDocumentoVO.codiceTipoDocumento=='RIMB'){
            this.rimborso = true;
            this.comunicazioniVarie = false;
            this.accertamento = false;
            this.listaTipoRimborso = this.listaTipoDocumento.filter(tipoDocumento => 
            tipoDocumento.idTipoDocumentoPadre === this.documentoToSave.tipoDocumentoVO.idTipoDocumento
            )
        }else if(this.documentoToSave.tipoDocumentoVO.codiceTipoDocumento=='ACCE'){
            this.accertamento = true;
            this.rimborso = false;
            this.comunicazioniVarie = false;
            this.yearsListAccertamento = this.yearsList;
        }else{
            this.comunicazioniVarie = false;
            this.rimborso = false;
            this.accertamento = false;
        }
    }
    
    public uploaderM : FileUploader = new FileUploader({url: URL});
    
    
    
    
    eliminaFile(file : FileItem, files : FileUploader) {
      
           if(file != null)
                this.elimina(file);
            else
                this.eliminaTutti(files);
          

        
    }
    
    aggiungiM(){
        this.confermaM = true;
        this.eliminaM = true;
    }

    uploadMultipli(files : FileItem[]){ 
        console.log("sinze allegati :: " + files.length + " 1 Master");
     
        this.onNewFile.emit(this.nuovoAllegatoMultiple);
        this.multipli.emit(true);
        
    }
    
    elimina (file : FileItem){
        this.sizeAlert = false;
        this.numAllegatiAlert = false;
        this.descAllegatoAlert = false;
        file.remove();
        if(!this.confermaM)
             this.eliminaM = false;
    }

    eliminaTutti (files : FileUploader){
        this.sizeAlert = false;
        this.numAllegatiAlert = false;
        this.descAllegatoAlert = false;
        files.clearQueue();
        if(!this.confermaM)
             this.eliminaM = false;
    }
    
    eliminaFilePrincipale(){
        this.fileToUpload = null;
        this.fileCaricato = false;
    }
    
    confermaAllegato(files : FileItem[]){
        console.log("controllo allegati :: " + files.length);
        this.sizeAlert = false;
        this.numAllegatiAlert = false;
        this.descAllegatoAlert = false;
        this.nuovoAllegatoMultiple.allegati = new Array<AllegatoMultipleFieldVO>();
        if(files.length > this.maxAllegati){
            this.numAllegatiAlert = true;
            this.utilityService.getMessageByKey(MessageEnum.DOCNUMMAXALLEGATI)
                 .subscribe(msg => {
                     this.message = msg.message || '';
                        }, error => {
                            this.logger.error(error);
                        });
        }else{
            for (let i = 0; i < files.length; i++) {
                console.log('entro nel for');
                let key : File = files[i]._file
                let descrizione: string = files[i].headers + '';            
                if (key.size > this.maxSize) {
                    this.sizeAlert = true;
                    this.logger.info("File size: " + key.size);
                    files[i].isError = true;
                    this.uploaderM.queue[i].isError = true;
                    break;
                } else {
                    if(descrizione!=null && descrizione!=undefined && descrizione.trim()!=''){
                        files[i].isError = false;
                        let allegato : AllegatoMultipleFieldVO = new AllegatoMultipleFieldVO();
                        this.sizeAlert = false;
                        allegato.file = key;
                        allegato.descrizione = descrizione;
                        allegato.filename = allegato.file.name;
                        //allegato.idTipoAllegato =  28;
                       
                        //spezza il nome del file da visualizzare se è troppo lungo e senza spazi. Non copre tutti i casi, ma dovrebbe bastare
                        if (allegato.filename.length > 30 && allegato.filename.split(" ").length == 1) {
                            if (allegato.filename.length <= 60) {
                                this.nomeAllegatoTmp = allegato.filename.slice(0, 30) + " " + allegato.filename.slice(30);
                            } else
                                this.nomeAllegatoTmp = allegato.filename.slice(0, 30) + " " + allegato.filename.slice(30, 60) + " " + allegato.filename.slice(60);
                        }
                        let type : string = "";
                        let array :string[] = allegato.filename.split(".");
                        if(array.length > 1){
                            type = array[array.length-1];
                        }
                        
                        if(this.allowedFilesType.filter(x => x==type.toLowerCase()).length==0){                
                            this.typeAlert = true;
                            this.confermaM = true;       
                            
                            this.message = "I documenti caricati non sono idonei all'archiviazione su Doqui ACTA. Si prega di ricontrollare l'estensione dei file selezionati";
                            this.levelMessage = "DANGER";
                            this.showMessage = true;
                            setTimeout(() => {
                                this.message = "";
                                this.levelMessage = "";
                                this.showMessage = false;
                            }, 5000);
                            files[i].isError = true;
                            this.uploaderM.queue[i].isError = true;
                            break;                        
                        } else{
                            this.typeAlert = false;
                            this.confermaM = false;
                            this.eliminaM = true;
                            
                            this.message = "I documenti caricati possono essere archiviati su Doqui ACTA. Per proseguire cliccare sul tasto Conferma";
                            this.levelMessage = "SUCCESS";
                            this.showMessage = true;
                            setTimeout(() => {
                                this.message = "";
                                this.levelMessage = "";
                                this.showMessage = false;
                            }, 5000);
                            this.nuovoAllegatoMultiple.allegati.push(allegato);
                            console.log('Sono qui');
                        }
                        
                    }else{
                        this.descAllegatoAlert = true;
                        this.utilityService.getMessageByKey(MessageEnum.DOCDESCALLEGATI)
                 			.subscribe(msg => {
                     			this.message = msg.message || '';
		                        }, error => {
		                            this.logger.error(error);
		                        });
                        files[i].isError = true;
                    }
                    
                }
            }
            
            if(this.descAllegatoAlert){
                this.confermaM = true;
            }
        }
        
    }
    
//    descrizioneChange(i: any){
//        console.log('posizione elemento'+i);
//        let descrizione = this.uploaderM.queue[i].headers+'' 
//        if(this.uploaderM.queue[i].headers+''.trim()!=''){
//            this.uploaderM.queue[i].isError = false;
//            this.descAllegatoAlert = false;
//            this.confermaAllegato(this.uploaderM.queue);
//        }
//         
//        
//    }
    
    resetForm(){
        this.documentoToSave = new DocumentiVO(0,null,
                null,null,null,null,'','',null,'',null, new StatoDocumentoVO(null,'',''),null,null,null,'',null,null,null,null,null);
        this.initModel();
        setTimeout(() => {
            this.clearMsg();
        }, 10000);
        
        this.eliminaFile(null, this.uploaderM);
        this.eliminaFilePrincipale();
    }
}
