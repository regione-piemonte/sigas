import {AfterViewInit, Component, OnInit, Renderer2, ViewChild} from '@angular/core';
import {DestroySubscribers} from '../../../core/commons/decorator/destroy-unsubscribers';
import {DocumentazioneService} from '../../service/documentazione.service';
import {LoggerService} from '../../../core/services/logger.service';
import {DataTableDirective} from 'angular-datatables';
import {Subject} from 'rxjs';
import {SigasDialogComponent} from '../../../shared/component/sigas-dialog/sigas-dialog.component';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';
import {ActivatedRoute, Router} from '@angular/router';
import {DocumentiVO} from '../../../commons/vo/documenti-vo';
import {StatoDocumentoVO} from '../../../commons/vo/stato-documento-vo';
import {ConfermaDocumentazioneRequest} from '../../../commons/request/conferma-documentazione-request';
import {RoutingDocumentazioneBO} from '../../../commons/routing';
import {UtilityService} from '../../../core/services/utility/utility.service';
import {MessageEnum} from '../../../core/services/utility/enum/messageEnum';
import { FileItem } from 'ng2-file-upload';
import { ExceptionVO } from "../../../core/commons/vo/exceptionVO";
import { ElencoDocumentazioneComponent } from '../elenco-documentazione/elenco-documentazione.component';

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
    public subscribers: any = {};
    allowedFilesType = ['pdf'];
    allowedFilesTypeMessage: string;
    dialogMsg: string;
    dataInvioPec: Date;
    dataInvioPecString: string;
    @ViewChild(DataTableDirective) dtElement: DataTableDirective;
    private loaderDT: boolean;
    private dtTrigger: Subject<any> = new Subject();
    private dtOptions: any;
    private showMessage: boolean;
    private message: string;
    private levelMessage: string;
    private documentoToSave: DocumentiVO;
    private loaderPage: boolean;
    private listaStatoDocumento: Array<StatoDocumentoVO>;
    private letteraRisposta: boolean;
    private loaded: boolean;
    private notaSalvata: boolean;

    private ACCETTATO = 3;
    private RIFIUTATO = 4;

    private isPecEnabled: boolean;

    private importoDepositoCausionale: number;
    private depositoCausionaleProvincia: String;
    private maxSize: number = 20971520;
    private sizeAlert: boolean = false;
    private fileToUpload: File = null;
    private fileCaricato: boolean = false;
    private depCausionaleNumeroAccertamento: string;
    private depCausionaleAnnoAccertamento: number;
    private depCausionaleNumeroDetermina: string;
    private showMessageSuccess: boolean = false;
    private messageSuccess: string = '';

    constructor(
        private logger: LoggerService,
        private documentazioneService: DocumentazioneService,
        private router: Router,
        private utilityService: UtilityService,
        private route: ActivatedRoute,
        private renderer: Renderer2
    ) {
    }

    ngAfterViewInit(): void {
        const element = this.renderer.selectRootElement('#idDivToContPrin');
        setTimeout(() => element.focus(), 0);
    }

    ngOnInit(): void {
        this.logger.info('init Documentazione Component');
        this.clearMsg();
        this.route.queryParams
            .subscribe(params => {
                    this.isPecEnabled = params.pec;
                }
            );
        this.documentoToSave = this.documentazioneService.documentazioneSel;
        this.documentazioneService.listaStatoDocumenti().subscribe(data => {
            if (this.documentoToSave.statoDocumentoVO.codiceStato == 'LETT_RISP') {
                this.listaStatoDocumento = data.filter(stato => stato.codiceStato == 'LETT_RISP');
            } else {
                this.listaStatoDocumento = data.filter(stato => stato.codiceStato !== 'LETT_RISP');
            }
            if((this.documentoToSave.tipoDocumentoVO.codiceTipoDocumento == 'DEPO' || this.documentoToSave.tipoDocumentoVO.codiceTipoDocumento == 'DEPO_INT' ) && 
               this.documentoToSave.statoDocumentoVO.codiceStato != 'ACC'){
               this.listaStatoDocumento = this.listaStatoDocumento.filter(stato => stato.codiceStato != 'ACC');
            }           
            setTimeout(() => {
                this.loaderDT = false;
            }, 1000);
        }, err => {
            this.logger.error(err);
        });

        if (this.documentazioneService.messaggioProtocollazioneLetteraRisp != null
            && this.documentazioneService.messaggioProtocollazioneLetteraRisp != '') {
            this.showMessage = true;
            this.levelMessage = 'SUCCESS';
            this.message = this.documentazioneService.messaggioProtocollazioneLetteraRisp;
            setTimeout(() => {
                this.documentazioneService.messaggioProtocollazioneLetteraRisp = '';
            }, 1000);
        }

        if (this.documentazioneService.messaggioErrProtocollazioneLetteraRisp != null
            && this.documentazioneService.messaggioErrProtocollazioneLetteraRisp != '') {
            this.showMessage = true;
            this.levelMessage = 'DANGER';
            this.message = this.documentazioneService.messaggioErrProtocollazioneLetteraRisp;
            setTimeout(() => {
                this.documentazioneService.messaggioErrProtocollazioneLetteraRisp = '';
            }, 1000);
        }

        this.letteraRisposta = false;
        this.notaSalvata = false;

        if (this.documentoToSave.statoDocumentoVO.codiceStato !== 'LETT_RISP') {
            this.documentazioneService.ricercaLetteraRisposta(this.documentoToSave.nprotocollo)
                .subscribe(data => {
                    if (data.length > 0) {
                        this.letteraRisposta = true;
                        if (data[0].note != null && data[0].note != undefined) {
                            this.notaSalvata = true;
                        }
                    }
                }, err => {
                    this.logger.error(err);
                    this.loaderPage = false;
                    this.showMessage = true;
                    this.levelMessage = 'DANGER';
                    this.message = 'Si è verificato un errore durante la ricerca della lettera di risposta';
                });
        } else {
            this.letteraRisposta = true;
        }

        if(this.documentoToSave.tipoDocumentoVO.codiceTipoDocumento == 'DEPO' || this.documentoToSave.tipoDocumentoVO.codiceTipoDocumento == 'DEPO_INT') 
        {
            if(this.documentoToSave.depositoCausionaleVOs!=null && this.documentoToSave.depositoCausionaleVOs != undefined)
            {
                if(this.documentoToSave.depositoCausionaleVOs[0].numeroAccertamento!=null && 
                   this.documentoToSave.depositoCausionaleVOs[0].numeroAccertamento != undefined)
                {
                    this.depCausionaleNumeroAccertamento = this.documentoToSave.depositoCausionaleVOs[0].numeroAccertamento.toString();
                }
                
                if(this.documentoToSave.depositoCausionaleVOs[0].annoAcccertamento!=null && 
                   this.documentoToSave.depositoCausionaleVOs[0].annoAcccertamento != undefined)
                {
                    this.depCausionaleAnnoAccertamento = +this.documentoToSave.depositoCausionaleVOs[0].annoAcccertamento;
                }

                if(this.documentoToSave.depositoCausionaleVOs[0].numeroDetermina!=null && 
                   this.documentoToSave.depositoCausionaleVOs[0].numeroDetermina != undefined)
                {
                    this.depCausionaleNumeroDetermina = this.documentoToSave.depositoCausionaleVOs[0].numeroDetermina.toString();
                }      


                this.importoDepositoCausionale = this.documentoToSave.depositoCausionaleVOs.reduce((acc, itemFilter) => { 
                                                                                                            return acc + itemFilter.importo; 
                                                                                                    }, 0);                                                                                                    
                
                this.importoDepositoCausionale = Math.round(this.importoDepositoCausionale * 100) / 100;

                let elencoProvinciaArray = this.documentoToSave.depositoCausionaleVOs.map((value) => value.provinciaVO.sigla);
                
                if(elencoProvinciaArray !=null && elencoProvinciaArray!=undefined)
                {
                    if(elencoProvinciaArray.length > 1)
                    {
                        this.depositoCausionaleProvincia = "Tutte le province";

                    } else {
                        this.depositoCausionaleProvincia = elencoProvinciaArray.filter((value, index, self) => self.indexOf(value) === index)
                                                                               .join(', ');
                    }
                }
                /*
                this.depositoCausionaleProvincia = elencoProvinciaArray.filter((value, index, self) => self.indexOf(value) === index)
                                                                        .join(', ');
                */

            }
        }

        this.loaded = true;
    }

    salvaPraticaDocumentazione() {
        this.chiudiInfoPopUp();

        let notaDataPec: string;
        if (this.dataInvioPec != null && this.dataInvioPec != undefined) {
            notaDataPec = 'PEC inviata il ' + this.dataInvioPecString;
        }

        //Setting a null del valore importo e codiceProvincia utilizzati in fase di richiesta Deposito Causionale da parte
        //dell'utente NON BackOffice
        this.documentazioneService.confermaDocumentazione = new ConfermaDocumentazioneRequest(this.documentoToSave, null, null,null);
        this.loaderPage = true;
        this.loaded = false;
        this.documentazioneService.salvaDocumentazioneBO(notaDataPec, 
                                                         this.depCausionaleAnnoAccertamento,
                                                         this.depCausionaleNumeroAccertamento,
                                                         this.depCausionaleNumeroDetermina,
                                                         this.importoDepositoCausionale).subscribe(data => 
        {
            this.loaderPage = false;
            this.loaded = true;
            
            //this.showMessage = true;
            //this.levelMessage = 'SUCCESS';
            //this.message = 'Operazione avvenuta con successo';

            this.showMessage = false;
            this.showMessageSuccess = true;
            this.messageSuccess = 'Operazione avvenuta con successo';

            this.documentoToSave.statoDocumentoVO.codiceStato = data;
            if((this.documentoToSave.tipoDocumentoVO.codiceTipoDocumento == 'DEPO' || this.documentoToSave.tipoDocumentoVO.codiceTipoDocumento == 'DEPO_INT' )&& 
               this.documentoToSave.statoDocumentoVO.codiceStato == 'LETT_RISP') {            
               this.listaStatoDocumento = this.listaStatoDocumento.filter(stato => stato.codiceStato == 'RIF');
            }
        }, err => {

            this.showMessageSuccess = false;
            this.messageSuccess = '';

            this.logger.error(err);
            this.loaderPage = false;
            this.loaded = true;
            this.showMessage = true;
            this.levelMessage = 'DANGER';
            this.message = 'Si è verificato un errore durante il salvataggio della documentazione';
        });
    }

    clearMsg() {
        this.message = '';
        this.showMessage = false;
        this.levelMessage = '';
    }

    goBack() {
        this.router.navigate([RoutingDocumentazioneBO.DOCUMENTI_IN_ARRIVO], { queryParams: { caller:'arrivo'}});
    }

    completaLetteraOrdinanza() {
        this.salvaPraticaDocumentazione();
        this.router.navigateByUrl(RoutingDocumentazioneBO.GESTIONE_RISPOSTA_TEMPLATE_LETTERA + this.documentoToSave.idDocumento);
    }

    /**
     * PopUp di conferma per l'aggiornamento
     * dello stato del docuemnto.
     */
    public apriPopUpAggiornaStato() {
	    this.utilityService.getMessageByKey((this.dataInvioPec != null && this.dataInvioPec != undefined)?MessageEnum.CONFERMA_AGGIORNA_INVIOPEC_DOC:MessageEnum.CONFERMA_AGGIORNA_STATO_DOC)
            .subscribe(msg => {
                this.sharedDialog.testo = msg.message;
                this.sharedDialog.open();
                this.sharedDialog.salvaAction.subscribe(value => {
                    this.apriInfoPopup();
                });
            });
    }

    /**
     * PopUp di conferma per l'aggiornamento
     * del solo stato del documento, oppure
     * per la creazione della lettera di risposta.
     */
    public apriInfoPopup() {
        this.notaSalvata = false;
        this.loaded = false;
        this.documentazioneService.ricercaLetteraRisposta(this.documentoToSave.nprotocollo).subscribe(data => {
            if (data.length > 0) {
                this.salvaPraticaDocumentazione();
                if (data[0].note != null && data[0].note != undefined) {
                    this.notaSalvata = true;
                }
            } else {
                if ((this.documentoToSave.statoDocumentoVO.idStatoDocumento == this.ACCETTATO || 
                    this.documentoToSave.statoDocumentoVO.idStatoDocumento == this.RIFIUTATO ) &&
                    (this.documentoToSave.tipoDocumentoVO.codiceTipoDocumento != 'DEPO' && 
                     this.documentoToSave.tipoDocumentoVO.codiceTipoDocumento != 'DEPO_INT')) 
                {
                    this.utilityService.getMessageByKey(MessageEnum.CONFERMA_LETTERA)
                        .subscribe(msg => {
                            this.dialogMsg = msg.message || '';
                            $('#dialogBox').modal('show');
                        }, error => {
                            this.logger.error(error);
                        });
                } else {
                    this.salvaPraticaDocumentazione();
                }
            }
        }, err => {
            this.logger.error(err);
            this.loaderPage = false;
            this.showMessage = true;
            this.levelMessage = 'DANGER';
            this.message = 'Si è verificato un errore durante la ricerca della lettera di risposta';
        });
    }

    public chiudiInfoPopUp() {
        $('#dialogBox').modal('hide');
    }

    changeFilterDataInvioPec(event) {
        console.log(event);
//        this.clearMsg();

        this.dataInvioPec = new Date(event.year, event.month - 1, event.day);
        this.dataInvioPecString = event.day + '/' + (event.month) + '/' + event.year;


    }
    
    resetDataInvioPec() {
        this.dataInvioPec = null;
    }

    handleFileInput(files: FileList) {
        //gestione messaggi alert
        this.message = "";
        this.levelMessage = "";
        this.showMessage = false;
        
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
        } else {  
                if(this.allowedFilesType.filter(x => x==type.toLowerCase()).length==0)
                {                    
                    this.message = "I documenti caricati non sono idonei all'archiviazione su Doqui ACTA. Si prega di ricontrollare l'estensione dei file selezionati";
                    this.levelMessage = "DANGER";
                    this.showMessage = true;
                    // setTimeout(() => {
                    //     this.message = "";
                    //     this.levelMessage = "";
                    //     this.showMessage = false;
                    // }, 5000);
                } else {
                    this.fileToUpload = files.item(0);                    
                    this.fileCaricato = true;                                        
                }
        }
        
      }

      eliminaFilePrincipale(){
        this.fileToUpload = null;
        this.fileCaricato = false;
        $("#mainFile").val('');
      }

      confermaAvvisoPagamento(){
        this.chiudiConfermaAvvisoPagamentoModal()
        this.loaded = false;
        this.documentazioneService
            .generaProtocollaBollettinoPagamentoDepCausionale(this.fileToUpload, this.documentoToSave.idDocumento, 
                                                             this.importoDepositoCausionale, this.depCausionaleAnnoAccertamento,
                                                             this.depCausionaleNumeroAccertamento, this.depCausionaleNumeroDetermina,
                                                             this.documentoToSave.tipoDocumentoVO.codiceTipoDocumento)
            .subscribe(data => {               
                this.listaStatoDocumento.push(new StatoDocumentoVO(3,"Accettata","ACC"))                        
                let statoDocAccettato = this.listaStatoDocumento.filter(stato => stato.codiceStato == 'ACC')[0];
                this.documentoToSave.statoDocumentoVO = statoDocAccettato;                           
                this.loaded = true;

                //this.showMessage = true;
                //this.levelMessage = 'SUCCESS';
                //this.message = 'Operazione avvenuta con successo';

                this.showMessage = false;
                this.showMessageSuccess = true;
                this.messageSuccess = 'Operazione avvenuta con successo';
            }, 
            error => {
                this.showMessageSuccess = false;
                this.messageSuccess = '';

                this.message = "E' già stata presentata una richiesta di Deposito Cauzionale per il soggetto selezionato e periodo selezionato";
                this.levelMessage = "DANGER";
                this.showMessage = true;
                this.loaded = true;                
        });
      }

      showDeterminaDepositoCauzionale() {
        return (this.documentoToSave.tipoDocumentoVO.codiceTipoDocumento=='DEPO' || 
                this.documentoToSave.tipoDocumentoVO.codiceTipoDocumento=='DEPO_INT')&& 
               (this.documentoToSave.statoDocumentoVO.codiceStato=='IN_LAV' || this.documentoToSave.statoDocumentoVO.codiceStato=='ACC');
      }

      apriConfermaAvvisoPagamentoModal() {
        $('#confermaAvvisoPagamentoModal').modal('show');
      }

      chiudiConfermaAvvisoPagamentoModal() {
        $('#confermaAvvisoPagamentoModal').modal('hide');
      }
}
