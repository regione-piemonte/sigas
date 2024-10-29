import {Component, OnInit, ViewChild, ElementRef,Inject, AfterViewInit, Renderer2} from '@angular/core';
import { DOCUMENT } from '@angular/common';
import {Routing} from '../../../commons/routing';
import {Router} from '@angular/router';
import {DatatableComponent} from '@swimlane/ngx-datatable';
import {DataTableDirective} from 'angular-datatables';
import {Subject} from 'rxjs';
import {saveAs} from 'file-saver';
import {DataTableIt} from '../../../commons/class/commons-data-table';
import {LoggerService} from '../../../core/services/logger.service';
import {AnagraficaSoggettiService} from '../../service/anagrafica-soggetti.service';
import {AnaComunicazioniVO} from '../../../commons/vo/ana-comunicazioni-vo';
import {AnagraficaSoggettoVO} from '../../../commons/vo/soggetti-vo';
import {TipoComunicazioniVO} from '../../../commons/vo/tipo-comunicazioni-vo';
import {DestroySubscribers} from '../../../core/commons/decorator/destroy-unsubscribers';
import {ConfermaAnaComunicazioniRequest} from '../../../commons/request/conferma-anaComunicazioni-request';
import {RicercaAnaComunicazioniRequest} from '../../../commons/request/ricerca-anaComunicazioni-request';

import * as moment from 'moment';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';
import {SigasDialogComponent} from '../../../shared/component/sigas-dialog/sigas-dialog.component';
import { AllegatoVO } from 'src/app/commons/vo/allegato-vo';

import {DocumentiVO} from '../../../commons/vo/documenti-vo';

@Component({
    selector: 'app-documenti',
    templateUrl: './documenti.component.html',
    styleUrls: ['./documenti.component.scss'],
    providers: [
        {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
    ]
})
@DestroySubscribers()
export class DocumentiComponent implements OnInit, AfterViewInit {
    @ViewChild(DataTableDirective)
    dtElement: DataTableDirective;
    @ViewChild('table') table: DatatableComponent;
    @ViewChild('utfFileRef') utfFileRef: ElementRef;


    public subscribers: any = {};
    @ViewChild(SigasDialogComponent) sigasDialog: SigasDialogComponent;
    buttonConfirmText: string;
    buttonAnnullaText: string;
    messageDialog: string;
    private dtOptions: any;
    private dtTrigger: Subject<any> = new Subject();
    private afuConfig: any;
    private fileToUpload: File = null;
    private loaderPage: boolean;
    private loaderDT: boolean;
    private fileCaricato: boolean;
    private fileModificato: boolean;
    private erroreCaricamento: boolean;
    private erroreUpload: string;
    private listaTipologie: Array<TipoComunicazioniVO>;
    private listaDocumenti: Array<AnaComunicazioniVO>;
    private listaAnni: Number[];
    private currentDate: Date;
    private documentoToSave: AnaComunicazioniVO;
    private idTipologiaDoc: number;
    private documento: AnaComunicazioniVO;
    private anagraficaSoggetto: AnagraficaSoggettoVO;
    private nuovoDocumento: boolean;
    private updateDocumento: boolean;
    private confermaAnaComunicazioniRequest: ConfermaAnaComunicazioniRequest;
    private ricercaAnaComunicazioniRequest: RicercaAnaComunicazioniRequest;
    private tipologia: string;
    private annoDocumento: string;
    private idAnag: number;
    private loaderExcel: boolean;
    private urlDownloadDoc: String;
    private dataDocumentoPicker: any;
    private message: string;
    private showMsg: boolean;
    private levelMessage: string;
    private documentCount: number;

    private dialogBoxType: string;
    private dialogBoxTitle: string;
    private dialogBoxMessage: string;
    private dialogBoxIdDocToDelete: number;
    
    
    private btnClickedMap : Map<String, boolean>; 

    constructor(private router: Router,
                private logger: LoggerService,
                private anagraficaSoggettiService: AnagraficaSoggettiService,
                @Inject(DOCUMENT) private document: Document,
                private renderer: Renderer2
                ) 
    {
        this.urlDownloadDoc = this.anagraficaSoggettiService.getUrlScaricaDocumento();
    }

    ngAfterViewInit(): void {
        const element = this.renderer.selectRootElement('#idDivToContPrin');
        setTimeout(() => element.focus(), 0);
    }
    
    calculateClasses(nprotocollo:string){        
        if(this.btnClickedMap.get(nprotocollo)){                        
            return "btn btn-danger btn-sm";
        }                
        return "btn btn-success btn-ok btn-sm";
    }

    calculateArialLabel(nprotocollo:string){        
        if(this.btnClickedMap.get(nprotocollo)){                        
            return "chiusura dettaglio degli allegati";
        }                
        return "apertura dettaglio degli allegati";
    }

    calculateDetails(nprotocollo:string, anaComunicazioni: AnaComunicazioniVO, index){ 
        var isBTNGreen: boolean = true;
        if(this.btnClickedMap.get(nprotocollo)){
            isBTNGreen = false;
        }       
        var rowChildVisible = this.viewDetails(anaComunicazioni,index);
        console.log("lunghezza: " + this.btnClickedMap.size)
        this.btnClickedMap.forEach((key,value) => {
            console.log("marts key: " + key + " value:" + value);
        });
        if(this.btnClickedMap.get(nprotocollo)==null||this.btnClickedMap.get(nprotocollo)==undefined){
            document.getElementById(nprotocollo).innerHTML = "-";            
            this.btnClickedMap.set(nprotocollo,true);            
        }else if(this.btnClickedMap.get(nprotocollo)){
            document.getElementById(nprotocollo).innerHTML = "+";            
            this.btnClickedMap.set(nprotocollo,false);            
        }else if (rowChildVisible){
            document.getElementById(nprotocollo).innerHTML = "-";            
            this.btnClickedMap.set(nprotocollo,true);
        }        
    }        
    
    viewDetails(anaComunicazioni: AnaComunicazioniVO, index): boolean{        
        var row =  $('#elencoDocumentiTBL').DataTable().row(index);
        var data = $('#elencoDocumentiTBL').DataTable().row(index).data();         
        if ( row.child.isShown() ) {            
            row.child.hide();
            return false;
        }
        else {            
            this.settingROW(anaComunicazioni, row);
            return true;            
        }
        
    }
    

    prepareRowHTMLTemplateReduce(d, element){
        var rowHTML = '<tr style="background-color:#F9F9F4">' +                        
                            '<td colspan="8">'+ element.fileName + '</td>' +                                                
                      '</tr>';                    
        return rowHTML;        
    }

    settingROW(d, row){
        if(d.allegati!=null && d.allegati != undefined){
            var rowHTML = '<table style="width:80%;">';
            var rowHeaderAllegatiHTML = '<tr style="background-color:#F9F9F4"><td colspan="8" style="font-size: x-small;"><strong>Allegati</strong></td></tr>';
            rowHTML = rowHTML + rowHeaderAllegatiHTML;            
            d.allegati.forEach(element => {
                rowHTML = rowHTML + this.prepareRowHTMLTemplateReduce(d, element);
            });
            rowHTML = rowHTML + '</table>'
            row.child(rowHTML).show();
        }                
    }   
    
    gestioneClasse(listaAllegati: Array<AllegatoVO>): String{
        if(listaAllegati!=null){
            return "dt-control";
        }
        return "short-td";
    }

    ngOnInit() {
        this.btnClickedMap  = new Map<String, boolean>();    
        this.documento = new AnaComunicazioniVO(0, new AnagraficaSoggettoVO(0, 0, '', '', '', '', '', '', 0, '', '', '', 0, false, 0, '', '', 0, 0, null), null,
            new TipoComunicazioniVO(null, '', ''), null, '', '', '', '', '', false, null, null, null);
        this.documentoToSave = Object.assign({}, this.documento);
        this.erroreUpload = 'Esiste già un file con quel nome, uid oppure numero protocollo';
        this.subscribers = {};

        this.calcolaAnniRecenti();
        this.afuConfig = {
            formatsAllowed: '.jpg,.pdf,.doc,.png',

            uploadAPI: {
                url: 'https://example-file-upload-api'
            },
            replaceTexts: {
                selectFileBtn: 'Seleziona il file',
                resetBtn: 'Reset',
                uploadBtn: 'Upload',
                dragNDropBox: 'Drag N Drop',
                attachPinBtn: 'Attach Files...',
                afterUploadMsg_success: 'Successfully Uploaded !',
                afterUploadMsg_error: 'Upload Failed !'
            }
        };
        this.dtOptions = {
            destroy: true,
            head: 20,
            pagingType: 'full_numbers',
            pageLength: 10,
            processing: true,
            searching: false,
            language: DataTableIt.language,
            responsive: {
                details: false
            },                    
            order: [],
            //SIGAS-227
            /* OLD CODE
            columnDefs: [{
                'targets': 'no-sort'
            }]
            */
            columnDefs: [
                {targets: 0, orderData: 9},
                {targets: 3, orderData: 8},
                {targets: 4, orderable: false},
                {targets: 5, orderable: false},
                {targets: 6, orderable: false},
                {targets: 7, orderable: false},
                {targets: 8, visible: false},
                {targets: 9, visible: false}
            ],
            bInfo: false,            
            "rowCallback": function( row, data, index) {
                this.rowMarts = row;
                /*
                if(this.listaDocumenti[index+1].allegati == null || this.listaDocumenti[index+1].allegati == undefined ){
                    $('td:eq(10)', row).removeClass('dt-control');
                }
                */
                /*
                if ( data[4] == "A" ) {
                  $('td:eq(4)', row).html( '<b>A</b>' );
                }
                */
            }
        };        
        this.ricercaAnaComunicazioniRequest = new RicercaAnaComunicazioniRequest(0, 'Tutti', 'Tutte');
        this.tipologia = 'Tutte';
        this.annoDocumento = 'Tutti';
        this.anagraficaSoggettiService.ricercaAnaComunicazioniReq = this.ricercaAnaComunicazioniRequest;
        this.loaderDT = true;        
        this.reinit();        
        this.loaderPage = true;
        this.subscribers.allTipoConsumo = this.anagraficaSoggettiService.getAllTipoComunicazioni()
            .subscribe(res => {
                this.loaderPage = false;
                this.listaTipologie = res;
            }, err => {
                this.logger.error('errore ');
                this.loaderPage = false;
            });
    }

    reinit() {
        if (this.anagraficaSoggettiService.headerDichiarante.idAnag != null) {
            this.subscribers.riceraByAnnoAndTipo = this.anagraficaSoggettiService.ricercaDocumentiByAnnoAndTipologia()
                .subscribe(res => {
                    this.listaDocumenti = res;
                    this.listaDocumenti.map(doc => {
                        this.anagraficaSoggettiService.setIdDoc = doc.idComunicazione;
                        this.anagraficaSoggettiService.ricercaAllarmiByIdDocumentoAndCodiceAzienda()
                            .subscribe(ret => {
                                if (ret.length > 0) {
                                    doc.allarmeOn = true;
                                } else {
                                    doc.allarmeOn = false;
                                }
                            });
                    });
                    this.loaderPage = false;
                    this.loaderDT = false;
                    if(this.listaDocumenti!=null){
                        this.documentCount = this.listaDocumenti.length;                                                                  
                    }else{
                        this.documentCount = 0;
                    }                    
                    this.rerender();                   
                    
                }, err => {
                    this.logger.error('errore ');
                });
        }
    }   

    calcolaAnniRecenti() {
        this.listaAnni = [];
        var current = (new Date()).getFullYear();
        for (var i = 0; i < 5; i++) {
            this.listaAnni[i] = current;
            current--;
        }
        return this.listaAnni;
    }

    onClickNuovo() {
        this.loaderPage = true;
        this.clearAll();
        this.nuovoDocumento = true;
        this.loaderPage = false;
        this.dataDocumentoPicker = null;
        console.log(this.documentoToSave);
    }

    clearAll() {
        this.documentoToSave = Object.assign({}, this.documento);
        this.documentoToSave.tipoComunicazioneVO.idTipoComunicazione = 0;
        this.utfFileRef.nativeElement.value = '';
        this.fileToUpload = null;
        this.fileCaricato = false;
        this.fileModificato = false;
        this.erroreCaricamento = false;
    }

    clearMsg() {
        this.showMsg = false;
        this.message = '';
        this.levelMessage = '';
    }

    onClickSalva() {
        let field: string = null;

        if(this.dataDocumentoPicker==null||this.dataDocumentoPicker==undefined){
            field == null ? field = ' data documento' : field += ', data documento';
        } else {
            this.documentoToSave.dataDocumento = new Date(this.dataDocumentoPicker.year, this.dataDocumentoPicker.month - 1, this.dataDocumentoPicker.day);
        }       

        if (this.documentoToSave.tipoComunicazioneVO.idTipoComunicazione == null || 
            this.documentoToSave.tipoComunicazioneVO.idTipoComunicazione == undefined || 
            this.documentoToSave.tipoComunicazioneVO.idTipoComunicazione == 0) 
        {
            field == null ? field = ' tipo documento' : field += ', tipo documento';
        }

        if (this.documentoToSave.annualita == null) {
            field == null ? field = ' annualita\' di riferimento' : field += ', annualita\' di riferimento';
        }        

        if(this.fileToUpload==null||this.fileToUpload==undefined){
            field == null ? field = ' allegato' : field += ', allegato';
        }

        if (field != null) {
            this.erroreCaricamento = true;
            this.erroreUpload = 'E\' necessario inserire i valori obbligatori:' + field;
        } else {
            this.erroreCaricamento = false;
            this.anagraficaSoggettiService.ricercaSoggettoByID()
                .subscribe(res => {
                    this.documentoToSave.anagraficaSoggettoVO = res;
                    if (null != this.fileToUpload) {
                        this.documentoToSave.descrizione = this.fileToUpload.name;
                    } else {
                        this.documentoToSave.descrizione = null;
                    }
                    this.anagraficaSoggettiService.idTipoComunicazion = this.idTipologiaDoc;
                    this.subscribers.riceraTipoComunicazione = this.anagraficaSoggettiService.ricercaTipoComunicazioniByIdTipoComunicazione()
                        .subscribe(res => {
                            this.documentoToSave.tipoComunicazioneVO = res;
                            this.loaderPage = true;
                            this.nuovoDocumento = false;

                            this.confermaAnaComunicazioniRequest = new ConfermaAnaComunicazioniRequest(this.documentoToSave);
                            this.anagraficaSoggettiService.confermaAnaComunicazioniReq = this.confermaAnaComunicazioniRequest;
                            this.anagraficaSoggettiService.setCodiceAzienda = this.documentoToSave.anagraficaSoggettoVO.codiceAzienda;
                            this.addDocumento();
                            this.loaderPage = false;
                        }, err => {
                            this.logger.error('errore ');
                            this.loaderPage = false;
                        });

                }, err => {
                    this.logger.error('errore ');
                    this.loaderPage = false;
                });
        }

    }

    onClickUpdate() {
        if (null != this.fileToUpload) {
            this.documentoToSave.descrizione = this.fileToUpload.name;
        }
        if (undefined === this.idTipologiaDoc) {
            this.idTipologiaDoc = this.documentoToSave.tipoComunicazioneVO.idTipoComunicazione;
        }
        this.documentoToSave.dataDocumento = new Date(this.dataDocumentoPicker.year, this.dataDocumentoPicker.month - 1, this.dataDocumentoPicker.day);
        this.anagraficaSoggettiService.idTipoComunicazion = this.idTipologiaDoc;
        this.subscribers.riceraByTipoComunicazione = this.anagraficaSoggettiService.ricercaTipoComunicazioniByIdTipoComunicazione()
            .subscribe(res => {
                this.documentoToSave.tipoComunicazioneVO = res;
                this.loaderPage = true;
                this.updateDocumento = false;

                this.confermaAnaComunicazioniRequest = new ConfermaAnaComunicazioniRequest(this.documentoToSave);
                this.anagraficaSoggettiService.confermaAnaComunicazioniReq = this.confermaAnaComunicazioniRequest;
                this.anagraficaSoggettiService.setCodiceAzienda = this.documentoToSave.anagraficaSoggettoVO.codiceAzienda;
                this.updateDocument();
                this.loaderPage = false;
            }, err => {
                this.logger.error('errore ');
                this.loaderPage = false;
            });
    }

    goBack() {
        this.nuovoDocumento = false;
        this.updateDocumento = false;
        this.erroreCaricamento = false;
    }

    goBackMain() {
        this.router.navigateByUrl(Routing.ELENCO_CONSUMI);
    }

    setTipologiaDocumento(id: string) {
        this.idTipologiaDoc = Number.parseInt(id);
        console.log(this.idTipologiaDoc);
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

    changeFilter() {
        if (null != this.annoDocumento && null != this.tipologia) {
            this.loaderDT = true;
            if (this.tipologia === undefined) {
                this.tipologia = 0 + '';
            }
            if (this.annoDocumento === undefined) {
                this.annoDocumento = 0 + '';
            }
            this.ricercaAnaComunicazioniRequest = new RicercaAnaComunicazioniRequest(this.idAnag, this.annoDocumento, this.tipologia);
            this.anagraficaSoggettiService.ricercaAnaComunicazioniReq = this.ricercaAnaComunicazioniRequest;
            console.log(this.ricercaAnaComunicazioniRequest);
            this.subscribers.riceraByAnnoAndTipo = this.anagraficaSoggettiService.ricercaDocumentiByAnnoAndTipologia()
                .subscribe(res => {
                    this.loaderPage = false;
                    this.loaderDT = false;
                    this.listaDocumenti = res;
                    this.listaDocumenti.map(doc => {
                        this.anagraficaSoggettiService.setIdDoc = doc.idComunicazione;
                        this.anagraficaSoggettiService.ricercaAllarmiByIdDocumentoAndCodiceAzienda()
                            .subscribe(ret => {
                                if (ret.length > 0) {
                                    doc.allarmeOn = true;
                                } else {
                                    doc.allarmeOn = false;
                                }
                            });
                    });
                    if(this.listaDocumenti!=null){
                        this.documentCount = this.listaDocumenti.length;
                    }else{
                        this.documentCount = 0;
                    }                    
                    this.rerender();
                    //var table = $('#elenco-documenti').DataTable();                      
                    //table.page.len(25).draw();
                    
                }, err => {
                    this.logger.error('errore ');
                });
                this.btnClickedMap  = new Map<String, boolean>();
        }
    }

    dettaglioDocumento(anaComunicazioni: AnaComunicazioniVO) {
        this.loaderPage = true;
        this.clearAll();
        this.documentoToSave = this.documentoToSave = Object.assign({}, anaComunicazioni);
        const _dataDocumento = moment(anaComunicazioni.dataDocumento);
        this.dataDocumentoPicker = {
            year: _dataDocumento.year(),
            month: Number(_dataDocumento.format('MM')),
            day: Number(_dataDocumento.format('DD'))
        };
        console.log('dettaglio');
        this.fileCaricato = true;
        this.fileModificato = false;
        this.updateDocumento = true;
        this.loaderPage = false;
    }

    handleFileInput(files: FileList) {
        this.fileToUpload = files.item(0);
        console.log(this.fileToUpload);
        this.fileCaricato = true;
        this.fileModificato = true;
        this.erroreCaricamento = false;
    }

    addDocumento() {
        this.loaderDT = true;
        this.anagraficaSoggettiService.fileChange(this.fileToUpload).subscribe(resp => {
            console.log(resp);
            console.log(this.documentoToSave);
            this.documentoToSave.idComunicazione = resp.idComunicazione;
            this.erroreCaricamento = false;

            this.anagraficaSoggettiService.allarmeDocumento(this.documentoToSave.allarmeOn, this.documentoToSave.idComunicazione)
                .subscribe(response => {
                    console.log('OK');
                    if (this.listaDocumenti != null) {
                        this.listaDocumenti.map(doc => {
                            this.anagraficaSoggettiService.setIdDoc = doc.idComunicazione;
                            this.anagraficaSoggettiService.ricercaAllarmiByIdDocumentoAndCodiceAzienda()
                                .subscribe(ret => {
                                    if (ret.length > 0) {
                                        console.log('allarme presente');
                                        doc.allarmeOn = true;
                                    } else {
                                        doc.allarmeOn = false;
                                    }
                                });
                        });
                    }
                    this.loaderDT = false;
                    this.anagraficaSoggettiService.ricercaAnaComunicazioniReq = new RicercaAnaComunicazioniRequest(0, 'Tutti', 'Tutte');
                    this.tipologia = 'Tutte';
                    this.annoDocumento = 'Tutti';
                    this.reinit();
                }, err => {
                    this.logger.error('errore gestione allarme');
                    this.loaderDT = false;
                });

        }, err => {
            this.erroreCaricamento = true;
            this.erroreUpload = err.message;
            this.nuovoDocumento = true;
            this.loaderDT = false;
            this.logger.error('errore ');
        });
    }

    updateDocument() {
        this.loaderDT = true;
        this.erroreCaricamento = false;
        // if (null != this.fileToUpload) {
        this.anagraficaSoggettiService.updateFileChange(this.fileToUpload).subscribe(response => {
            this.erroreCaricamento = false;
            this.subscribers.riceraByAnnoAndTipo = this.anagraficaSoggettiService.ricercaDocumentiByAnnoAndTipologia()
                .subscribe(res => {
                    this.loaderPage = false;
                    this.loaderDT = false;
                    this.listaDocumenti = res;
                    this.listaDocumenti.map(doc => {
                        this.anagraficaSoggettiService.allarmeDocumento(this.documentoToSave.allarmeOn, this.documentoToSave.idComunicazione)
                            .subscribe(resp => {
                                console.log('OK');
                                this.anagraficaSoggettiService.setIdDoc = doc.idComunicazione;
                                this.anagraficaSoggettiService.ricercaAllarmiByIdDocumentoAndCodiceAzienda()
                                    .subscribe(ret => {
                                        if (ret.length > 0) {
                                            console.log('allarme presente');
                                            doc.allarmeOn = true;
                                        } else {
                                            doc.allarmeOn = false;
                                        }
                                    });
                            }, err => {
                                this.loaderDT = false;
                                this.logger.error('errore gestione allarme');
                            });
                    });
                    this.goBack();
                    this.rerender();
                }, err => {
                    this.loaderDT = false;
                    this.logger.error('errore ');
                });
        }, err => {
            this.erroreCaricamento = true;
            this.erroreUpload = err.message;
            this.updateDocumento = true;
            this.loaderDT = false;
            this.logger.error('errore ');
        });
    }

    modButtonAllarme() {
        this.documentoToSave.allarmeOn = !this.documentoToSave.allarmeOn;
        console.log(this.documentoToSave.allarmeOn);
    }

    goExcel() {
        console.log('Scarica Excel');
        this.loaderExcel = true;
        this.anagraficaSoggettiService.annoDichiarazione = this.annoDocumento;
        this.subscribers.scaricaExcelElencoSoggetti = this.anagraficaSoggettiService.scaricaExcelElencoDocumenti(Number(this.tipologia))
            .subscribe(
                res => {
                    this.loaderExcel = false;
                    saveAs(res, 'export_elenco_documenti_' + this.anagraficaSoggettiService.headerDichiarante.denominazione + '.xls');

                },
                err => {
                    this.logger.error('errore in download excel');
                }
            );
    }

    scaricaDocumento(idComunicazione) {
        return this.anagraficaSoggettiService.getUrlScaricaDocumento() + idComunicazione;
    }    

    dialogBoxShowDeleteDocument(idAnaComunicazione: number) { 
        this.dialogBoxIdDocToDelete = idAnaComunicazione;               
        this.dialogBoxTitle = 'Cancella documento';
        this.dialogBoxMessage = 'Sei sicuro di voler eliminare il documento?';
    }

    dialogBoxConfirmButton(){
        this.subscribers.cancellaDocumento = this.anagraficaSoggettiService.eliminaDocumento(this.dialogBoxIdDocToDelete)
                .subscribe((response) => {
                    this.logger.info('Documento cancellato correttamente!');
                    this.message = 'Documento eliminato correttamente';
                    this.showMsg = true;
                    this.levelMessage = 'SUCCESS';
                    this.loaderDT = true;                    
                    setTimeout(() => {
                        this.clearMsg();
                        this.reinit();                        
                    }, 10000);

                }, error => {
                    this.logger.error('ERRORE: ' + error);
                    this.message = 'Si è verificato un errore';
                    this.showMsg = true;
                    this.levelMessage = 'ERROR';
                });
    }

    dialogBoxDismissButton() {
    }

    confermaEliminaDocumento(idAnaComunicazione: number) {

        this.buttonConfirmText = 'Conferma';
        this.buttonAnnullaText = 'Annulla';
        this.messageDialog = 'Sei sicuro di voler eliminare il documento?';                
        this.sigasDialog.open();

        this.subscribers.save = this.sigasDialog.salvaAction.subscribe(data => {
            this.anagraficaSoggettiService.eliminaDocumento(idAnaComunicazione)
                .subscribe((response) => {
                    this.logger.info('Documento cancellato correttamente!');

                    this.message = 'Documento eliminato correttamente';
                    this.showMsg = true;
                    this.levelMessage = 'SUCCESS';
                    this.loaderDT = true;
                    this.reinit();
                    setTimeout(() => {
                        this.clearMsg();
                    }, 10000);
                }, error => {
                    this.logger.error('ERRORE: ' + error);
                    this.message = 'Si è verificato un errore';
                    this.showMsg = true;
                    this.levelMessage = 'ERROR';
                });


        }, err => {
            this.logger.error(err);
        });


        this.annullaPopUp();
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
}
