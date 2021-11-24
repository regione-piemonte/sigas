import {Component, OnInit, ViewChild} from '@angular/core';
import {DestroySubscribers} from '../../../core/commons/decorator/destroy-unsubscribers';
import {DocumentazioneService} from '../../service/documentazione.service';
import {LoggerService} from '../../../core/services/logger.service';
import {DataTableIt} from '../../../commons/class/commons-data-table';
import {DataTableDirective} from 'angular-datatables';
import {Subject} from 'rxjs';
import {RicercaDocumentazioneRequestBo} from '../../../commons/request/ricerca-documentazione-request-bo';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';
import {Router} from '@angular/router';
import {AnagraficaSoggettoVO} from '../../../commons/vo/soggetti-vo';
import {DocumentiVO} from '../../../commons/vo/documenti-vo';
import {StatoDocumentoVO} from '../../../commons/vo/stato-documento-vo';
import {RoutingDocumentazioneBO} from '../../../commons/routing';
import {SharedCacheService} from '../../../core/services/shared-cache/shared-cache.service';

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
    @ViewChild(DataTableDirective) dtElement: DataTableDirective;
    public isRowSelected: boolean;
    public dataProtDal: any;
    public dataProtAl: any;
    public statoDocumentoVO: StatoDocumentoVO;
    private elencoLettereRisposta: Array<DocumentiVO>;
    private COMPONENT_KEY = 'app-elenco-documentazione';
    private loaderDT: boolean;
    private dtTrigger: Subject<any> = new Subject();
    private dtOptions: any;
    private listaAziende: Array<AnagraficaSoggettoVO>;
    private yearsList: Number[];
    private annualita: Number;
    private listaStatoDocumento: Array<StatoDocumentoVO>;
    private dataProtocollazioneDal: Date;
    private dataProtocollazioneAl: Date;

    constructor(
        private logger: LoggerService,
        private documentazioneService: DocumentazioneService,
        private router: Router,
        private sharedCache: SharedCacheService
    ) {
    }

    ngOnInit(): void {
        this.dtOptions = {
            destroy: true,
            processing: true,
            responsive: false,
            head: 20,
            pageLength: 10,
            pagingType: 'full_numbers',
            language: DataTableIt.language,
            columnDefs: [
                {className: 'dt-center', 'targets': [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]},
                {width: '1%', targets: 0, orderable: false},
                {width: '9%', targets: 1, orderable: false},
                {width: '9%', targets: 2},
                {width: '9%', targets: 3},
                {width: '9%', targets: 4, orderable: false},
                {width: '9%', targets: 5, orderable: false},
                {width: '9%', targets: 6, orderable: false},
                {width: '9%', targets: 7, orderable: false},
                {width: '9%', targets: 8, orderable: false},
                {width: '9%', targets: 9, orderable: false},
                {width: '9%', targets: 10, orderable: false},
                {width: '9%', targets: 11, orderable: false},
            ],
            order: [[2, 'desc']]
        };
        this.documentazioneService.ricercaAziendeDocumentiInoltrati()
            .subscribe(aziende => {
                this.listaAziende = aziende;
                this.documentazioneService.listaStatoDocumenti()
                    .subscribe(documenti => {
                        this.listaStatoDocumento = documenti;
                        setTimeout(() => {
                            this.loaderDT = false;
                        }, 1000);
                        this.restoreFilterAndDataCached();
                    }, err => {
                        this.logger.error(err);
                    });
            }, err => {
                this.logger.error(err);
            });
        setTimeout(() => {
            this.dtTrigger.next();
        });
    }

    ricarcaDocumenti() {
        this.documentazioneService.ricercaDocumentazione = new RicercaDocumentazioneRequestBo(
            this.anagraficaSoggettoVO,
            (this.statoDocumentoVO !== null && this.statoDocumentoVO !== undefined) ? this.statoDocumentoVO.idStatoDocumento : null,
            this.dataProtocollazioneDal,
            this.dataProtocollazioneAl);
        this.loaderDT = true;
        this.subscribers.ricercaDocumenti = this.documentazioneService.ricercaDocumentiBO()
            .subscribe(data => {
                this.elencoDoc = data.filter(doc => doc.statoDocumentoVO.codiceStato !== 'LETT_RISP');
                this.elencoLettereRisposta = data.filter(doc => doc.statoDocumentoVO.codiceStato === 'LETT_RISP');
                if ((this.elencoDoc === null || this.elencoDoc.length === 0)
                 && (this.elencoLettereRisposta !== null && this.elencoLettereRisposta.length > 0)) {
                    this.elencoDoc = this.elencoLettereRisposta;
                }
                this.cacheFilterAndData();
                this.loaderDT = false;
                this.rerender();
            }, err => {
                this.logger.error(err);
            });
        this.isRowSelected = false;
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
        this.statoDocumentoVO = null;
        this.dataProtocollazioneDal = null;
        this.dataProtocollazioneAl = null;
        this.dataProtDal = null;
        this.dataProtAl = null;
    }

    changeFilterDataProtocollazioneDal(event) {
        this.dataProtocollazioneDal = new Date(event.year, event.month - 1, event.day);
    }

    changeFilterDataProtocollazioneAl(event) {
        this.dataProtocollazioneAl = new Date(event.year, event.month - 1, event.day);
    }

    resetDataProtocollazioneDal() {
        this.dataProtocollazioneDal = null;
        this.dataProtDal = null;
    }

    resetDataProtocollazioneAl() {
        this.dataProtocollazioneAl = null;
        this.dataProtAl = null;
    }

    documentazioneSelect(documentazioneSel: DocumentiVO) {
        this.isRowSelected = true;
        this.documentazioneService.documentazioneSel = documentazioneSel;
    }

    goDettaglioDocumentazione() {
        this.router.navigateByUrl(RoutingDocumentazioneBO.DETTAGLIO_DOCUMENTAZIONE);
    }

    private cacheFilterAndData() {
        const _filter = {
            anagraficaSoggetto: this.anagraficaSoggettoVO,
            statoDocumento: this.statoDocumentoVO,
            dataDal: this.dataProtocollazioneDal,
            dataAl: this.dataProtocollazioneAl
        };
        const _dataToCache = {
            filter: _filter,
            data: this.elencoDoc
        };
        this.sharedCache.put(this.COMPONENT_KEY, _dataToCache);
    }

    private restoreFilterAndDataCached() {
        const cachedData = this.sharedCache.get(this.COMPONENT_KEY);
        if (cachedData !== null && cachedData !== undefined) {
            this.statoDocumentoVO = null;
            this.dataProtocollazioneDal = null;
            this.dataProtocollazioneAl = null;
            this.dataProtDal = null;
            this.dataProtAl = null;
            this.anagraficaSoggettoVO = this.listaAziende
                .filter(azienda => azienda.idAnag === cachedData.filter.anagraficaSoggetto.idAnag)[0];
            if (cachedData.filter.statoDocumento !== null && cachedData.filter.statoDocumento !== undefined) {
                this.statoDocumentoVO = this.listaStatoDocumento
                    .filter(stato => stato.idStatoDocumento === cachedData.filter.statoDocumento.idStatoDocumento)[0];
            }
            if (cachedData.filter.dataDal !== null && cachedData.filter.dataDal !== undefined) {
                this.dataProtocollazioneDal = cachedData.filter.dataDal;
                this.dataProtDal = this.getObjDateFromDate(cachedData.filter.dataDal);
            }
            if (cachedData.filter.dataAl !== null && cachedData.filter.dataAl !== undefined) {
                this.dataProtocollazioneAl = cachedData.filter.dataAl;
                this.dataProtAl = this.getObjDateFromDate(cachedData.filter.dataAl);
            }
            this.elencoDoc = cachedData.data;
            this.loaderDT = true;
            this.sharedCache.clean();
            this.logger.info('Clean cache');
            this.ricarcaDocumenti();
            this.rerender();
        }
    }

    private getObjDateFromDate(data: Date) {
        return {
            day: data.getDate(),
            month: data.getMonth() + 1,
            year: data.getFullYear()
        };
    }
}
