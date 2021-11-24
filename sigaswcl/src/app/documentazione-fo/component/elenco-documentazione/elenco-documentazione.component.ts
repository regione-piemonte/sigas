import {Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import {DestroySubscribers} from '../../../core/commons/decorator/destroy-unsubscribers';
import {DocumentazioneService} from '../../service/documentazione.service';
import {LoggerService} from '../../../core/services/logger.service';
import {DataTableIt} from '../../../commons/class/commons-data-table';
import {DataTableDirective} from 'angular-datatables';
import {Subject} from 'rxjs';
import {RicercaDocumentazioneRequest} from '../../../commons/request/ricerca-documentazione-request';
import {SigasDialogComponent} from '../../../shared/component/sigas-dialog/sigas-dialog.component';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';
import * as moment from 'moment';
import {Router} from '@angular/router';
import {AnagraficaSoggettoVO} from '../../../commons/vo/soggetti-vo';
import {DocumentiVO} from '../../../commons/vo/documenti-vo';
import {TipoDocumentoVO} from '../../../commons/vo/tipo-documento-vo';

declare var jquery: any;
declare var $: any;

@Component({
    selector: 'app-elenco-documentazione',
    templateUrl: './elenco-documentazione.component.html',
    styleUrls: ['./elenco-documentazione.component.scss'],
    providers: [
        {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
    ]
})

@DestroySubscribers()
export class ElencoDocumentazioneComponent implements OnInit {

    elencoDoc: Array<DocumentiVO>;
    public subscribers: any = {};
    anagraficaSoggettoVO: AnagraficaSoggettoVO;
    nprotocollo: string;
    tipoDocumentoVO: TipoDocumentoVO;
    @ViewChild(DataTableDirective) dtElement: DataTableDirective;
    private loaderDT: boolean;
    private dtTrigger: Subject<any> = new Subject();
    private dtOptions: any;
    private listaAziende: Array<AnagraficaSoggettoVO>;
    private yearsList: Number[];
    private annualita: Number;
    private listaTipoDocumento: Array<TipoDocumentoVO>;
    private listaTipoDocumentoMacro: Array<TipoDocumentoVO>;

    constructor(
        private logger: LoggerService,
        private documentazioneService: DocumentazioneService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
        this.logger.info('init Ricerca Pratiche Component');
        this.dtOptions = {
            destroy: true,
            processing: true,
            responsive: false,
            head: 20,
            pageLength: 10,
            pagingType: 'full_numbers',
            language: DataTableIt.language,
            columnDefs: [ { className: 'dt-center', 'targets': [0, 1, 2, 3, 4, 5, 6, 7, 8, 9] },
                { width: '10%', targets: 0 },
                { width: '10%', targets: 1, orderable: false },
                { width: '10%', targets: 2, orderable: false },
                { width: '10%', targets: 3, orderable: false },
                { width: '10%', targets: 4, orderable: false },
                { width: '10%', targets: 5, orderable: false },
                { width: '10%', targets: 6, orderable: false },
                { width: '10%', targets: 7, orderable: false },
                { width: '10%', targets: 8, orderable: false },
                { width: '10%', targets: 9, orderable: false }
            ],
            order: [[0, 'desc']]
        };
        this.documentazioneService.ricercaAziendeAccreditato()
            .subscribe(data => {
                this.listaAziende = data;
            }, err => {
                this.logger.error(err);
            });
        this.documentazioneService.listaTipoDocumenti()
            .subscribe(data => {
                this.listaTipoDocumento = data;
                this.listaTipoDocumentoMacro = this.listaTipoDocumento
                    .filter(tipoDocumento => tipoDocumento.idTipoDocumentoPadre === null);
                setTimeout(() => {
                    this.loaderDT = false;
                }, 1000);
            }, err => {
                this.logger.error(err);
            });
        this.calcolaAnniRecenti();
        setTimeout(() => {
            this.dtTrigger.next();
        });
    }

    calcolaAnniRecenti() {
        this.yearsList = [];
        var current = (new Date()).getFullYear();
        for (var i = 0; i < 5; i++) {
            this.yearsList[i] = current;
            current--;
        }
        return this.yearsList;
    }

    ricarcaDocumenti() {
        this.loaderDT = true;
        this.documentazioneService.ricercaDocumentazione = new RicercaDocumentazioneRequest(this.anagraficaSoggettoVO, this.nprotocollo, this.tipoDocumentoVO, this.annualita);
        this.subscribers.ricercaDocumenti = this.documentazioneService.ricercaDocumenti()
            .subscribe(data => {
                this.elencoDoc = data;
                this.loaderDT = false;
                this.rerender();
            }, err => {
                this.logger.error(err);
            });
    }

    rerender(): void {
        if (this.dtElement != null && this.dtElement.dtInstance != null) {
            this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
                dtInstance.destroy();
                this.dtTrigger.next();
            });
        } else {
            setTimeout(() => {
                this.dtTrigger.next();
            });
        }
    }

    goBack() {
    }

    checkRow(idUtenteProvv: number) {
    }

    cancellaCampiRicerca() {
        this.anagraficaSoggettoVO = null;
        this.nprotocollo = null;
        this.tipoDocumentoVO = null;
        this.annualita = null;
    }
}
