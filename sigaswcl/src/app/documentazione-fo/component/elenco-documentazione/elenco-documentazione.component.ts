import {Component, OnInit, ViewChild, ElementRef, Inject, AfterViewInit} from '@angular/core';
import { DOCUMENT } from '@angular/common';
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


import { AllegatoDocumentazioneVO } from '../../../commons/vo/allegato-documentazione-vo';

import { Renderer2 } from '@angular/core';


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
export class ElencoDocumentazioneComponent implements OnInit, AfterViewInit {

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

    private documentCount: number;
    public elencoLettereRisposta: Array<DocumentiVO>;

    private btnClickedMap : Map<String, boolean>;   

    constructor(
        private logger: LoggerService,
        private documentazioneService: DocumentazioneService,
        private router: Router,
        @Inject(DOCUMENT) private document: Document,
        private renderer: Renderer2
    ) {
    }
    ngAfterViewInit(): void {
        const element = this.renderer.selectRootElement('#idDivToContPrin');
        setTimeout(() => element.focus(), 0);
    }    

    getDataProtocollazioneByNumeroProtocolloRif(protocolloRif: String, dataProtocollazioneDefault: Date): Date {
        if(dataProtocollazioneDefault === null || dataProtocollazioneDefault === undefined){            
            return new Date();            
        }        
        return dataProtocollazioneDefault;               
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

    calculateDetails(nprotocollo:string, documento: DocumentiVO, index){ 
        var isBTNGreen: boolean = true;
        if(this.btnClickedMap.get(nprotocollo)){
            isBTNGreen = false;
        }       
        var rowChildVisible = this.viewDetails(documento,index,isBTNGreen);
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

    viewDetails(documento: DocumentiVO, index, isBTNGreen:boolean): boolean{        
        var row =  $('#elencoDocumentiTBL').DataTable().row(index);
        var data = $('#elencoDocumentiTBL').DataTable().row(index).data();                
        var classTR = $('#elencoDocumentiTBL').DataTable().row(index).$('.dt-hasChild').attr('class');
        var classHasChildPresent : boolean = false;       
        if(classTR!=null && classTR!=undefined ){
            var stringToCheck = new String(classTR);
            if(stringToCheck.indexOf('dt-hasChild')>0){
                classHasChildPresent = true;
            }            
        }
        if ( row.child.isShown() && !classHasChildPresent) {
            this.settingROW(documento, row, data, true);            
        } else if(row.child.isShown() && classHasChildPresent){
            if(data[11] != null && data[11] != undefined && data[11].length > 0){
                if(isBTNGreen){
                    this.settingROW(documento, row, data, true);
                }else{
                    $('#elencoDocumentiTBL').DataTable().row(index).$('.dt-hasChild').removeClass('dt-hasChild');
                    this.settingROW(documento, row, data, false);
                    
                }                
            }else{
                row.child.hide();  
            }

        } else {            
            this.settingROW(documento, row, data, true);
        }
        return row.child.isShown();           
    }    

    prepareRowHTMLTemplateReduce(d, element, idDocumento){        
        var rowHTML = '<tr style="background-color:#F9F9F4">' +                        
                        '<td colspan="2">'+ element.nomeFile + '</td>' +
                        '<td colspan="2">'+ element.descrizione + '</td>' +                                                
                      '</tr>'
        return rowHTML;        
    }

    prepareRowHTMLTemplateLettera(data): Array<string>{
        var lettereRispostaHTML:string[];
        if(data[11] != null && data[11] != undefined && data[11].length > 0){
            var lettereRipostaJSONFormat:{denominazione: string, 
                                          dataProtocollazione: string,
                                          annualita: string,
                                          descrizione: string,
                                          nomeFile: string,
                                          note: string,
                                          nprotocollo: string,
                                          rifArchivio: string,
                                          idDocumento: string,
                                          urlMaster: string,
                                          codiceStatoDocumento: string}[] = JSON.parse(data[11]);                                                        
            
            lettereRispostaHTML = lettereRipostaJSONFormat.map((data) => {                                                    
                                                        return '<tr style="background-color:#F9F9F4">' +
                                                                    '<span class="sr-only">Lettera di risposta con protocollo ' + data.nprotocollo + '</span>' +                                                                    
                                                                    '<td style="dt-center">' + data.dataProtocollazione + '</td>' +                                                                                                                                                                                                                        
                                                                    '<td style="trunk20 dt-left" title="' + data.nomeFile + '">' + data.nomeFile + '</td>' +
                                                                    '<td style="dt-left">' + data.nprotocollo + '</td>' +
                                                                    '<td style="trunk20 dt-center" title="' + data.note + '">' + data.note + '</td>' +                                                                        
                                                                '</tr>'
                                                    });
            
        };
        return lettereRispostaHTML;
    }    

    settingROW(d, row, data, aggiungiAllegato){
        if(d.sigasAllegatos!=null && d.sigasAllegatos != undefined){
            var checkAllegati : boolean = false;
            var rowHTML = '<table style="width:80%;">';
            if(aggiungiAllegato){
                //Gestione Allegati
                //--------------------------------
                var rowHeaderAllegatiHTML = '<tr style="background-color:#F9F9F4"><td colspan="2" style="font-size: x-small;"><strong>Allegati</strong></td><td colspan="2" style="font-size: x-small;"><strong>descrizione</strong></td></tr>';                
                //--------------------------------
                rowHTML = rowHTML + rowHeaderAllegatiHTML;
                for (var itemA of d.sigasAllegatos) {
                    checkAllegati = true;
                    rowHTML = rowHTML + this.prepareRowHTMLTemplateReduce(d, itemA, itemA.idAllegato);
                }
            };            
            var lettereArrayHTML: Array<string> = this.prepareRowHTMLTemplateLettera(data);
            if(lettereArrayHTML!=null && lettereArrayHTML!=undefined && lettereArrayHTML.length > 0){
                //Gestione header Lettera Risposta
                //--------------------------------
                var rowHeaderLetteraHTML = '<tr style="background-color:#F9F9F4"><td colspan="4" style="font-size: x-small;"><strong>Lettera di risposta</strong></td></tr>';
                var firstRowLettera = '<tr style="background-color:#F9F9F4"><td style="font-size: x-small;"><strong>Protocollata il</strong></td>' +
                                      '<td style="font-size: x-small;"><strong>Documento</strong></td>' + 
                                      '<td style="font-size: x-small;"><strong>Protocollo</strong></td>' + 
                                      '<td style="font-size: x-small;"><strong>Note</strong></td><tr>';
                //--------------------------------
                rowHTML = rowHTML + rowHeaderLetteraHTML + firstRowLettera; 
                for (var itemL of lettereArrayHTML) {                    
                        rowHTML = rowHTML + itemL;                                     
                };
            };            
            rowHTML = rowHTML + '</table>'
            row.child(rowHTML).show();            
        }                
    } 
    
    gestioneClasse(listaAllegati: Array<AllegatoDocumentazioneVO>, index): String{        
        if(listaAllegati!=null && listaAllegati!=undefined && listaAllegati.length > 0){
            return "dt-control";
        }
        return "short-td";
    }

    determinaAllegati(listaAllegati: Array<AllegatoDocumentazioneVO>, idDocumento): any{
        if(listaAllegati!=null && listaAllegati!=undefined && listaAllegati.length>0){            
            var allegati = listaAllegati.map((data)=>{return '{' + 
                                                                '"nomeFile":"' + data.nomeFile + '",' +
                                                                '"urlFile":"' + this.documentazioneService.getUrlScaricaDocumentoAllegato(idDocumento, data.nomeFile) + '",' +
                                                                '"idDocumento":' + idDocumento +
                                                             '}'
                                                     });
            if(allegati.length > 0){
                return "[" + allegati + "]";
            } else {
                return null;
            }                         
        } else {
            return null;
        }        
    }

    determinaLettereRisposta(numProtocollo: String): any{                
        if(this.elencoLettereRisposta!=null && this.elencoLettereRisposta!=undefined){            
            var marts = this.elencoLettereRisposta
                                    .filter(doc => doc.rifArchivio === numProtocollo)
                                    .map((data)=>{                                        
                                                    return '{' +
                                                                '"dataProtocollazione":"' + moment(data.dataProtocollazione).format("DD/MM/YYYY") + '",' + 
                                                                '"annualita":"' + ((data.annualita!=null) ? data.annualita : '') + '",' +
                                                                '"descrizione":"' + data.tipoDocumentoVO.descrizione + '",' +
                                                                '"nomeFile":"' + data.nomeFile + '",' +
                                                                '"note":"' + ((data.note!=null)?data.note:'') + '",' +
                                                                '"rifArchivio":"' + data.rifArchivio + '",' +
                                                                '"denominazione":"' + data.anagraficaSoggettoVO.denominazione + '",' +                                                                
                                                                '"nprotocollo":"' + ((data.nprotocollo!=null)?data.nprotocollo:'In Protocollazione') + '",' +                                                                
                                                                '"idDocumento":"' + data.idDocumento + '",' +
                                                                '"urlMaster":"' + this.documentazioneService.getUrlScaricaDocumentoMaster() + '",' +
                                                                '"codiceStatoDocumento":"' + data.statoDocumentoVO.codiceStato + '"' +
                                                            '}'
                                                });
            if(marts.length > 0){
                return "[" + marts + "]";
            } else {
                return null;
            }                         
        } else {
            return null;
        }        
    }

    determinaNomeFileLetteraRisposta(numProtocollo: String): any{
        if(this.elencoLettereRisposta!=null && this.elencoLettereRisposta!=undefined){            
            var marts = this.elencoLettereRisposta
                                    .filter(doc => doc.rifArchivio === numProtocollo)
                                    .map((data)=>{ return data.nomeFile});
            if(marts.length > 0){
                return marts;
            } else {
                return null;
            }                         
        } else {
            return null;
        }        
    }

    determinaIdLetteraRisposta(numProtocollo: String): any{
        if(this.elencoLettereRisposta!=null && this.elencoLettereRisposta!=undefined){            
            var marts = this.elencoLettereRisposta
                                    .filter(doc => doc.rifArchivio === numProtocollo)
                                    .map((data)=>{ return data.idDocumento});
            if(marts.length > 0){
                return marts;
            } else {
                return null;
            }                         
        } else {
            return null;
        }        
    }

    ngOnInit(): void {        
       
        this.btnClickedMap  = new Map<String, boolean>();    
        this.logger.info('init Ricerca Pratiche Component');
        this.dtOptions = {
            destroy: true,
            processing: true,
            responsive: {
                details: false
            },
            head: 20,
            pageLength: 10,
            pagingType: 'full_numbers',
            language: DataTableIt.language,            
            columnDefs: [                 
                { width: '10%', targets: 0, orderData: 10, orderable: false },
                { width: '10%', targets: 1, orderable: false },
                { width: '10%', targets: 2, orderable: false },
                { width: '10%', targets: 3, orderable: false },
                { width: '10%', targets: 4, orderable: false },
                { width: '10%', targets: 5, orderable: false },
                { width: '10%', targets: 6, orderable: false },
                { responsivePriority: 1, width: '10%', targets: 7, orderable: false },
                { responsivePriority: 1, width: '10%', targets: 8, orderable: false },
                { width: '10%', targets: 9, orderable: false },
                { targets: 10, visible: false, orderable: true},
                { targets: 11, visible: false},
                { targets: 12, visible: false}   
            ],            
            "createdRow": function( row, data, index) {
                $("#spinner").prop('hidden', false);
                var lettereRispostaHTML:string[];
                var allegatiHTML;
                if(data[11] != null && data[11] != undefined && data[11].length > 0){                   
                    var lettereRipostaJSONFormat:{dataProtocollazione: string,
                                                  annualita: string,
                                                  descrizione: string,
                                                  nomeFile: string,
                                                  note: string,
                                                  rifArchivio: string,
                                                  denominazione: string,                                                  
                                                  nprotocollo: string,                                                  
                                                  idDocumento: string,
                                                  urlMaster: string,
                                                  codiceStatoDocumento: string}[] = JSON.parse(data[11]);                                                        
                    
                    lettereRispostaHTML = lettereRipostaJSONFormat.map((data) => {
                                                            return '<tr style="background-color:#F9F9F4">' +
                                                                        '<span class="sr-only">Lettera di risposta con protocollo ' + data.nprotocollo + '</span>' +                                                                                
                                                                        '<td style="dt-center">' + data.dataProtocollazione + '</td>' +                                                                                                                                                                                                                        
                                                                        '<td style="trunk20 dt-left" title="' + data.nomeFile + '">' + data.nomeFile + '</td>' +
                                                                        '<td style="dt-left">' + data.nprotocollo + '</td>' +
                                                                        '<td style="trunk20 dt-center" title="' + data.note + '">' + data.note + '</td>' +                                                                        
                                                                    '</tr>'
                                                });                                                                                        
                    
                    
                };                
                if(lettereRispostaHTML!=null && lettereRispostaHTML!=undefined && lettereRispostaHTML.length>0){                    
                    var rowHTML = '<div class="dataTables_wrapper no-footer"><table style="width:80%;"><tr style="background-color:#F9F9F4"><td colspan="4" style="font-size: x-small;"><strong>Lettera di risposta</strong></td></tr>';
                    rowHTML = rowHTML + '<tr style="background-color:#F9F9F4"><td style="font-size: x-small;"><strong>Protocollata il</strong></td>' +
                                        '<td style="font-size: x-small;"><strong>Documento</strong></td>' +                                         
                                        '<td style="font-size: x-small;"><strong>Protocollo</strong></td>'+
                                        '<td style="font-size: x-small;"><strong>Note</strong></td><tr>'; 
                    var checkAllegati: boolean = false;
                    if(allegatiHTML!=null && allegatiHTML!=undefined && allegatiHTML.length>0){
                        checkAllegati = true;
                        for (var itemA of allegatiHTML) {
                            rowHTML = rowHTML + itemA
                        };                        
                    }                    
                    for (var itemL of lettereRispostaHTML) {
                        /*
                        if(!checkAllegati){                            
                            rowHTML = rowHTML + itemL; 
                        } else {
                            rowHTML = rowHTML + itemL; 
                        }
                        */
                        
                        rowHTML = rowHTML + itemL;
                    }                    
                    rowHTML = rowHTML + '</table></div>';                    
                    var row =  $('#elencoDocumentiTBL').DataTable().row(index);
                    row.child(rowHTML,'marts').show();                    
                    $('#elencoDocumentiTBL').DataTable().row(index).$('.dt-hasChild').removeClass('dt-hasChild');
                    
                }                
            },
            initComplete: function () {
                setTimeout(() => {
                    $("#spinner").prop('hidden', false);
                },1000)                
                
            },
            order: [[10, 'desc']]
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
                //this.elencoDoc = data;                
                this.elencoDoc = data.filter(doc => doc.statoDocumentoVO.codiceStato !== 'LETT_RISP');                
                if(this.elencoDoc!=null){
                    this.documentCount = this.elencoDoc.length;                                                                  
                }else{
                    this.documentCount = 0;
                };
                this.elencoLettereRisposta = data.filter(doc => doc.statoDocumentoVO.codiceStato === 'LETT_RISP');
                if ((this.elencoDoc === null || this.elencoDoc.length === 0)
                 && (this.elencoLettereRisposta !== null && this.elencoLettereRisposta.length > 0)) {
                    this.elencoDoc = this.elencoLettereRisposta;                    
                }                
                setTimeout(() => {
                    this.loaderDT = false;
                },1000);                
                this.rerender();
            }, err => {
                this.logger.error(err);
            });
            this.btnClickedMap  = new Map<String, boolean>();    
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
