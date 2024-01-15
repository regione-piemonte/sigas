import {Component, OnInit, ViewChild} from '@angular/core';
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
export class DocumentazioneComponent implements OnInit {
    @ViewChild(SigasDialogComponent) sharedDialog: SigasDialogComponent;
    public subscribers: any = {};
    allowedFilesType = ['pdf', 'tiff', 'jpg', 'jpeg', 'p7m'];
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

    constructor(
        private logger: LoggerService,
        private documentazioneService: DocumentazioneService,
        private router: Router,
        private utilityService: UtilityService,
        private route: ActivatedRoute
    ) {
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
        this.loaded = true;
    }

    salvaPraticaDocumentazione() {
        this.chiudiInfoPopUp();

        let notaDataPec: string;
        if (this.dataInvioPec != null && this.dataInvioPec != undefined) {
            notaDataPec = 'PEC inviata il ' + this.dataInvioPecString;
        }

        this.documentazioneService.confermaDocumentazione = new ConfermaDocumentazioneRequest(this.documentoToSave);
        this.loaderPage = true;
        this.loaded = false;
        this.documentazioneService.salvaDocumentazioneBO(notaDataPec).subscribe(data => {
            this.loaderPage = false;
            this.loaded = true;
            this.showMessage = true;
            this.levelMessage = 'SUCCESS';
            this.message = 'Operazione avvenuta con successo';
            this.documentoToSave.statoDocumentoVO.codiceStato = data;
        }, err => {
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
        this.router.navigate([RoutingDocumentazioneBO.DOCUMENTI_IN_ARRIVO]);
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
        this.documentazioneService.ricercaLetteraRisposta(this.documentoToSave.nprotocollo).subscribe(data => {
            if (data.length > 0) {
                this.salvaPraticaDocumentazione();
                if (data[0].note != null && data[0].note != undefined) {
                    this.notaSalvata = true;
                }
            } else {
                if (this.documentoToSave.statoDocumentoVO.idStatoDocumento == this.ACCETTATO
                    || this.documentoToSave.statoDocumentoVO.idStatoDocumento == this.RIFIUTATO) {
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
}
