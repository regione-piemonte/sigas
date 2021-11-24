import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {LoggerService} from '../../../core/services/logger.service';
import {ActivatedRoute, Router} from '@angular/router';
import {DatiTemplateVO} from '../../../commons/vo/template/dati-template-vo';
import {DatiTemplateCompilatiVO} from '../../../commons/vo/template/dati-template-compilati-vo';
import {TemplateService} from '../../../template/services/template.service';
import {DatiTemplateRequest} from '../../../commons/request/template/dati-template-request';
import {Constants} from '../../../commons/class/constants';
import {Location} from '@angular/common';
import {DomSanitizer} from '@angular/platform-browser';
import {saveAs} from 'file-saver';
import {TemplateLetteraRispostaComponent} from '../../../template/components/template-lettera-risposta/template-lettera-risposta.component';
import {DocumentazioneService} from '../../service/documentazione.service';
import {IsCreatedVO} from '../../../commons/vo/isCreated-vo';
import {RoutingDocumentazioneBO} from '../../../commons/routing';
import {SigasDialogComponent} from '../../../shared/component/sigas-dialog/sigas-dialog.component';
import {UtilityService} from '../../../core/services/utility/utility.service';
import {MessageEnum} from '../../../core/services/utility/enum/messageEnum';


@Component({
    selector: 'documentazione-template-lettera',
    templateUrl: './documentazione-template-lettera.component.html'
})
export class DocumentazioneTemplateLetteraGestContAmministrativoComponent implements OnInit, OnDestroy {

    @ViewChild(SigasDialogComponent) sigasDialog: SigasDialogComponent;
    @ViewChild(TemplateLetteraRispostaComponent) template: TemplateLetteraRispostaComponent;
    public isDialogVisible = false;
    public subscribers: any = {};
    public loaded: boolean;
    public isAnteprima: boolean = false;
    public isPrinted: boolean = false;
    public scrollEnable: boolean;
    public idDocumentazione: number;
    public datiTemplateModel: DatiTemplateVO;
    public datiTemplateModelStampa: DatiTemplateCompilatiVO = new DatiTemplateCompilatiVO();
    public datiCompilati: DatiTemplateCompilatiVO;
    public url: string;
    //Messaggio Top
    public showMessageTop: boolean;
    public typeMessageTop: String;
    public messageTop: String;
    visualizzaAnteprimaValidTemplate: boolean = false;
    public letteraRisposta: Blob;
    public isStampa: boolean = false;
    public isStampaProtocollata: boolean = false;
    public loadedScarica: boolean = false;
    private intervalIdS: number = 0;

    constructor(
        private logger: LoggerService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private templateService: TemplateService,
        private location: Location,
        private sanitizer: DomSanitizer,
        private documentazioneService: DocumentazioneService,
        private utilityService: UtilityService
    ) {
    }

    ngOnInit(): void {
        this.logger.info(DocumentazioneTemplateLetteraGestContAmministrativoComponent.name);
        this.subscribers.route = this.activatedRoute.params.subscribe(params => {
            this.idDocumentazione = +params['id'];
            let request: DatiTemplateRequest = new DatiTemplateRequest();
            request.codiceTemplate = Constants.LETTERA_RISPOSTA;
            request.idDocumentazione = this.idDocumentazione;
            this.subscribers.getTemplate = this.templateService.getDatiTemplate(request).subscribe(data => {
                this.datiTemplateModel = data;
                this.loaded = true;
            }, err => {
                this.logger.error('Errore durante il recupero dei dati');
                this.loaded = true;
            });
        });
    }

    indietro() {
        this.utilityService.getMessageByKey(MessageEnum.CONFERMA_INDIETRO_PROT_LETTERA_RISP)
            .subscribe(msg => {
                this.sigasDialog.testo = msg.message;
                this.sigasDialog.open();
                this.sigasDialog.salvaAction.subscribe(value => {
                    this.location.back();
                });
            });
    }

    visualizzaAnteprima() {
        console.log(this.template.getDatiCompilati());
        this.datiTemplateModelStampa = this.template.getDatiCompilati();
        this.isAnteprima = true;
        this.template.setAnteprima(true);
        this.scrollEnable = true;
    }

    proseguiModifica() {
        this.isAnteprima = false;
        this.isStampa = false;
        this.template.setAnteprima(false);
        this.template.setStampa(false);
        this.scrollEnable = true;
    }

    stampaPDF() {
        this.isPrinted = true;
        this.isStampa = true;

        this.template.setStampa(true);
        this.template.setDatiCompilati(this.datiTemplateModelStampa);

        let tmp: DatiTemplateCompilatiVO = this.template.getDatiCompilati();
        this.resetMessageTop();
        if (this.checkDatiTemplate(tmp)) {
            //this.loaded = false; 
            let request: DatiTemplateRequest = new DatiTemplateRequest();
            request.codiceTemplate = Constants.LETTERA_RISPOSTA;
            request.idDocumentazione = this.idDocumentazione;
            request.datiTemplateCompilatiVO = tmp;
            request.nProtocollo = '';
            let isCreatedVO: IsCreatedVO = new IsCreatedVO();
            isCreatedVO.id = this.idDocumentazione;
//            this.subscribers.isLetteraSaved = this.documentazioneService.isLetteraSaved(isCreatedVO).subscribe(data => {
//                if(!data.isCreated){
            this.subscribers.stampa = this.templateService.stampaTemplate(request).subscribe(data => {
                //this.url = URL.createObjectURL(data);
                this.letteraRisposta = data;
                this.scrollEnable = true;
                this.loadedScarica = true;
                this.isStampaProtocollata = true;
                this.manageMessageTop('Lettera risposta creata con successo.', 'SUCCESS');
            }, err => {
                this.logger.error('Errore durante il download del PDF');
                //this.loaded = true;
                this.scrollEnable = true;
                this.isStampa = true;
                this.loadedScarica = false;
                this.isPrinted = false;
            });
//                }
//                else{
//                    this.manageMessageTop("La lettera accompagnatoria dell'ordinanza è già stata creata.",'DANGER');
//                    this.isAnteprima = false;
//                    //this.loaded = true;
//                    this.scrollEnable = true;
//                }
//            })

        } else {
            this.manageMessageTop('Compilare tutti i campi obbligatori', 'WARNING');
            this.scrollEnable = true;
        }
    }

    scarica() {
        this.loaded = false;
        let request: DatiTemplateRequest = new DatiTemplateRequest();
        request.codiceTemplate = Constants.LETTERA_RISPOSTA;
        request.idDocumentazione = this.idDocumentazione;
        let nome: string;
//        this.templateService.nomiTemplate(request).subscribe(data => {
//            nome = data.nome;
//            this.templateService.downloadTemplate(request).subscribe(data => {
        saveAs(this.letteraRisposta, 'Lettera_Risposta_' + this.idDocumentazione);
        this.loaded = true;
//                this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_ORDINANZA_RIEPILOGO + this.idOrdinanza); 
//            }, err => {
//                this.logger.error("Errore durante il download del PDF");
//                this.loaded = true;
//            });
//        });


    }

    protocollaLetteraRisp() {
        this.loaded = false;
        this.documentazioneService.protocollaLetteraRisp(this.blobToFile(this.letteraRisposta, 'Lettera_Risposta_' + this.idDocumentazione + '.pdf'))
            .subscribe(data => {
                let request: DatiTemplateRequest = new DatiTemplateRequest();
                request.codiceTemplate = Constants.LETTERA_RISPOSTA;
                request.idDocumentazione = this.idDocumentazione;
                request.datiTemplateCompilatiVO = this.datiTemplateModelStampa;
                request.nProtocollo = data.nprotocollo;
                this.subscribers.stampa = this.templateService.stampaTemplate(request).subscribe(data => {
                    this.loaded = true;
                    this.letteraRisposta = data;
                    saveAs(this.letteraRisposta, 'Lettera_Risposta_' + this.idDocumentazione);

                }, err => {
                    this.loaded = true;
                    this.logger.error('Errore durante il download del PDF');

                });

                let mesLetRisp: string = 'Protocollo Assegnato: ' + data.nprotocollo;
                console.log(mesLetRisp);
                this.documentazioneService.messaggioProtocollazioneLetteraRisp = mesLetRisp;
                this.router.navigateByUrl(RoutingDocumentazioneBO.DETTAGLIO_DOCUMENTAZIONE + '?pec=true');
            }, err => {
                this.loaded = true;
                let mesErrLetRisp: string = 'Errore durante la protocollazione della lettera di risposta';
                this.documentazioneService.messaggioErrProtocollazioneLetteraRisp = mesErrLetRisp;
                this.router.navigateByUrl(RoutingDocumentazioneBO.DETTAGLIO_DOCUMENTAZIONE);
            });
    }

    public blobToFile = (theBlob: Blob, fileName: string): File => {
        var b: any = theBlob;
        //A Blob() is almost a File() - it's just missing the two properties below which we will add
        b.lastModifiedDate = new Date();
        b.name = fileName;

        //Cast to a File() type
        return <File>theBlob;
    };

    checkDatiTemplate(dati: DatiTemplateCompilatiVO): boolean {
        let flag: boolean = true;
        for (let field in dati) {
            if (!dati[field]) {
                flag = false;
            }
        }
        return flag;
    }

    manageMessageTop(message: string, type: string) {
        this.typeMessageTop = type;
        this.messageTop = message;
        this.timerShowMessageTop();
    }

    timerShowMessageTop() {
        this.showMessageTop = true;
        let seconds: number = 20;//this.configService.getTimeoutMessagge();
        this.intervalIdS = window.setInterval(() => {
            seconds -= 1;
            if (seconds === 0) {
                this.resetMessageTop();
            }
        }, 1000);
    }

    resetMessageTop() {
        this.showMessageTop = false;
        this.typeMessageTop = null;
        this.messageTop = null;
        clearInterval(this.intervalIdS);
    }

    ngAfterViewChecked() {
        let out: HTMLElement = document.getElementById('scrollTop');
        if (this.loaded && this.scrollEnable && out != null) {
            out.scrollIntoView();
            this.scrollEnable = false;
        }
    }

    setFormValidTemplate(event: boolean) {
        this.visualizzaAnteprimaValidTemplate = event;
    }

    ngOnDestroy(): void {
        this.logger.info(DocumentazioneTemplateLetteraGestContAmministrativoComponent.name);
    }
}
