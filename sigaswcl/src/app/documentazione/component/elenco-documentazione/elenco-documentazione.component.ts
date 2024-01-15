import {Component, OnInit, ViewChild, Inject} from '@angular/core';
import { DOCUMENT } from '@angular/common';
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
import { variable } from '@angular/compiler/src/output/output_ast';

import * as moment from 'moment';
import { AllegatoDocumentazioneVO } from '../../../commons/vo/allegato-documentazione-vo';

declare var jquery: any;
declare var $: any;

interface iElencoDoc {
    idDocumento?: number;
    denominazione?: string;
    dataProtocollazione?: Date;
    annualita?: number;
    tipoDocumento?: string;
    nomeFile?: string;
    note?: string;
    nProtocollo?: string;
    rifArchivio?: string;
    codiceStato?: string;
    descrizioneStato?: string;
    noteBo?: string;
    allegatiPresenti?: boolean;
    letteraRispostaPresente?: boolean;
    codiceTipoDocumento?: string;
    insDate?: Date;
}

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
    tableData: iElencoDoc[] = [];

    elencoDoc: Array<DocumentiVO>;
    public subscribers: any = {};
    anagraficaSoggettoVO: AnagraficaSoggettoVO;
    nprotocollo: string;
    @ViewChild(DataTableDirective) dtElement: DataTableDirective;
    public isRowSelected: boolean;
    public dataProtDal: any;
    public dataProtAl: any;
    public statoDocumentoVO: StatoDocumentoVO;
    public elencoLettereRisposta: Array<DocumentiVO>;
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
    private documentCount: number;
    private btnClickedMap : Map<String, boolean>;    

    constructor(
        private logger: LoggerService,
        private documentazioneService: DocumentazioneService,
        private router: Router,
        private sharedCache: SharedCacheService,
        @Inject(DOCUMENT) private document: Document
    ) {
    }

    calculateArialLabel(nprotocollo:string){        
        if(this.btnClickedMap.get(nprotocollo)){                        
            return "chiusura dettaglio degli allegati";
        }                
        return "apertura dettaglio degli allegati";
    }

    calculateIdForButton(nprotocollo:string){                              
        return "btn-" + nprotocollo;
    }

    calculateClasses(nprotocollo:string){        
        if(this.btnClickedMap.get(nprotocollo)){                        
            return "btn btn-danger btn-sm";
        }                
        return "btn btn-success btn-ok btn-sm";
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
            document.getElementById("btn-" + nprotocollo).innerHTML = "-";            
            this.btnClickedMap.set(nprotocollo,true);            
        }else if(this.btnClickedMap.get(nprotocollo)){
            document.getElementById("btn-" + nprotocollo).innerHTML = "+";            
            this.btnClickedMap.set(nprotocollo,false);            
        }else if (rowChildVisible){
            document.getElementById("btn-" + nprotocollo).innerHTML = "-";            
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
            if(data[13] != null && data[13] != undefined && data[13].length > 0){
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
                        '<td colspan="3">'+ element.nomeFile + '</td>' +
                        '<td colspan="3">'+ element.descrizione + '</td>' +                                                
                      '</tr>'
        return rowHTML;        
    }

    prepareRowHTMLTemplateLettera(data): Array<string>{
        var lettereRispostaHTML:string[];
        if(data[13] != null && data[13] != undefined && data[13].length > 0){
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
                                          codiceStatoDocumento: string}[] = JSON.parse(data[13]);                                                        
            
            lettereRispostaHTML = lettereRipostaJSONFormat.map((data) => {                                                        
                                                            return '<tr class="pointer" style="background-color:#F9F9F4">' +
                                                                    '<span class="sr-only">Lettera di risposta con protocollo ' + data.nprotocollo + '</span>' +
                                                                    '<td class="dt-left rd-lettera">' +                                                                            
                                                                            '<label for="radio-' + data.nprotocollo + '" class="sr-only">seleziona</label>' +
                                                                            '<input name="selection" id="radio-' + data.nprotocollo + '" type="radio" value="' + data.idDocumento + '">' +
                                                                    '</td>'+
                                                                    '<td class="dt-left">' + data.dataProtocollazione + '</td>' +                                                                        
                                                                    '<td class="trunk20 dt-left" title="' + data.nomeFile + '">' + data.nomeFile + '</td>' +                                                                                                                                                 
                                                                    '<td class="dt-left">' + data.nprotocollo + '</td>' +
                                                                    '<td class="trunk20 dt-left" title="' + data.note + '">' + data.note + '</td>' +
                                                                    '<td class="dt-center">' +
                                                                        '<a class="btn btn-success"' +
                                                                            'download="' + data.nomeFile + '"' +
                                                                            'href="'+ data.urlMaster + data.idDocumento + '>Scarica</a>' +
                                                                    '</td>' +
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
                var rowHeaderAllegatiHTML = '<tr style="background-color:#F9F9F4"><td colspan="3" style="font-size: x-small;"><strong>Allegati</strong></td><td colspan="3" style="font-size: x-small;"><strong>descrizione</strong></td></tr>';
                //var firstRowAllegati = '<tr style="background-color:#F9F9F4"><td collspan="6" style="font-size: x-small;"><strong>Documento</strong></td><tr>';
                //--------------------------------
                //rowHTML = rowHTML + rowHeaderAllegatiHTML + firstRowAllegati;
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
                var rowHeaderLetteraHTML = '<tr style="background-color:#F9F9F4"><td colspan="6" style="font-size: x-small;"><strong>Lettera di risposta</strong></td></tr>';
                var firstRowLettera = '<tr style="background-color:#F9F9F4"><td></td><td style="font-size: x-small;"><strong>Protocollata il</strong></td>' +
                                      '<td style="font-size: x-small;"><strong>Documento</strong></td>' + 
                                      '<td style="font-size: x-small;"><strong>Protocollo</strong></td>' + 
                                      '<td style="font-size: x-small;"><strong>Note</strong></td><td></td><tr>';
                //--------------------------------

                rowHTML = rowHTML + rowHeaderLetteraHTML + firstRowLettera; 
                for (var itemL of lettereArrayHTML) {
                    //if(!checkAllegati){
                    //    rowHTML = rowHTML + itemL.replace('background-color:#F9F9F4','');
                    //} else {
                        rowHTML = rowHTML + itemL; 
                    //}                 
                };
            };            
            rowHTML = rowHTML + '</table>'
            row.child(rowHTML).show();
            /*
            $('input:radio').on('click', function(e) {                                                    
                if(!isNaN(e.currentTarget.value)){
                    $('#btnDettaglio').prop('disabled', false);
                    sessionStorage.setItem('letteraSelezionata',e.currentTarget.value);
                }
                                        
            });
            */
            $('#elencoDocumentiTBL').on('click', '.rd-lettera', function(e){
                console.log(e.target.value);                     
                if(!isNaN(e.target.value)){
                    $('#btnDettaglio').prop('disabled', false);                            
                    sessionStorage.setItem('letteraSelezionata',e.target.value);
                }
            });             
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

    determinaLettereRisposta(numProtocollo: String): any{
        if(this.elencoLettereRisposta!=null && this.elencoLettereRisposta!=undefined){            
            var marts = this.elencoLettereRisposta
                                    .filter(doc => doc.rifArchivio === numProtocollo)
                                    .map((data)=>{                                        
                                                    return '{' + 
                                                                '"denominazione":"' + data.anagraficaSoggettoVO.denominazione + '",' +
                                                                '"dataProtocollazione":"' + moment(data.dataProtocollazione).format("DD/MM/YYYY") + '",' +
                                                                '"annualita":"' + data.annualita + '",' +
                                                                '"descrizione":"' + data.tipoDocumentoVO.descrizione + '",' +
                                                                '"nomeFile":"' + data.nomeFile + '",' +
                                                                '"note":"' + ((data.note!=null)?data.note:'') + '",' +
                                                                '"nprotocollo":"' + ((data.nprotocollo!=null)?data.nprotocollo:'') + '",' +
                                                                '"rifArchivio":"' + data.rifArchivio + '",' +
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

    marts(idLetera: number){
        alert("marts")
    }
 
    ngOnInit(): void {
        this.btnClickedMap  = new Map<String, boolean>();    
        sessionStorage.clear();        
        this.dtOptions = {
            deferRender: true,
            destroy: true,
            processing: true,            
            responsive: {
                details: false
            },            
            head: 20,
            pageLength: 10,
            pagingType: 'first_last_numbers',
            language: DataTableIt.language,
            columnDefs: [                
                {targets: 0, orderable: false},
                {targets: 1, orderable: false},
                {targets: 2, orderData: 12},                
                {targets: 4, orderable: false},
                {targets: 5, orderable: false},
                {targets: 6, orderable: false},
                {targets: 7, orderable: false},
                {targets: 8, orderable: false, visible: false},
                {responsivePriority: 1, targets: 9, orderable: false},
                {responsivePriority: 1, targets: 10, orderable: false},
                {responsivePriority: 1, targets: 11, orderable: false},
                {targets: 12, visible: false},
                {targets: 13, visible: false},
                {targets: 14, visible: false}                 
            ],
            bInfo: false,
            "drawCallback": function( settings ) {
                $('input:radio').on('click', function(e) {                                        
                    console.log(e.currentTarget.name); 
                    console.log(e.currentTarget.value);                        
                    if(!isNaN(e.currentTarget.value)){
                        $('#btnDettaglio').prop('disabled', false);                            
                        sessionStorage.setItem('letteraSelezionata',e.currentTarget.value);
                    }                                                
                });
            },                        
            "createdRow": function( row, data, index) {
                $("#spinner").prop('hidden', false);
                var lettereRispostaHTML:string[];
                var allegatiHTML;                
                if(data[13] != null && data[13] != undefined && data[13].length > 0){
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
                                                  codiceStatoDocumento: string}[] = JSON.parse(data[13]);                                                        
                    
                    lettereRispostaHTML = lettereRipostaJSONFormat.map((data) => {                        
                                                            return      '<tr class="pointer" style="background-color:#F9F9F4">' +
                                                                        '<span class="sr-only">Lettera di risposta con protocollo ' + data.nprotocollo + '</span>' +
                                                                        '<td class="dt-left rd-lettera" style="style="padding-left: 1.3rem;">' +
                                                                                '<label for="radio-' + data.nprotocollo + '" class="sr-only">seleziona</label>' +
                                                                                '<input name="selection" id="radio-' + data.nprotocollo + '" type="radio" value="' + data.idDocumento + '">' +
                                                                        '</td>'+
                                                                        '<td class="dt-left">' + data.dataProtocollazione + '</td>' +                                                                        
                                                                        '<td class="trunk20 dt-left" title="' + data.nomeFile + '">' + data.nomeFile + '</td>' +                                                                                                                                                 
                                                                        '<td class="dt-left">' + data.nprotocollo + '</td>' +
                                                                        '<td class="trunk20 dt-left" title="' + data.note +'">' + data.note + '</td>' +
                                                                        '<td class="dt-center">' +
                                                                            '<a class="btn btn-success"' +
                                                                                'download="' + data.nomeFile + '"' +
                                                                                'href="'+ data.urlMaster + data.idDocumento + '>Scarica</a>' +
                                                                        '</td>' +
                                                                        '</tr>' 
                                                                        

                                                });                                       
                };                
                if(lettereRispostaHTML!=null && lettereRispostaHTML!=undefined && lettereRispostaHTML.length>0){                   
                    var rowHTML = '<div class="dataTables_wrapper no-footer"><table style="width:80%;"><tr style="background-color:#F9F9F4"><td colspan="6" style="font-size: x-small;"><strong>Lettera di risposta</strong></td></tr>';
                    rowHTML = rowHTML + '<tr style="background-color:#F9F9F4"><td></td><td style="font-size: x-small;"><strong>Protocollata il</strong></td>' +
                                        '<td style="font-size: x-small;"><strong>Documento</strong></td>' + 
                                        '<td style="font-size: x-small;"><strong>Protocollo</strong></td>' + 
                                        '<td style="font-size: x-small;"><strong>Note</strong></td><td></td><tr>';                    
                    var checkAllegati: boolean = false;
                    if(allegatiHTML!=null && allegatiHTML!=undefined && allegatiHTML.length>0){
                        checkAllegati = true;
                        for (var itemA of allegatiHTML) {
                            rowHTML = rowHTML + itemA
                        };                        
                    }                    
                    for (var itemL of lettereRispostaHTML) {
                        if(!checkAllegati){
                            //rowHTML = rowHTML + itemL.replace('background-color:#F9F9F4','');
                            rowHTML = rowHTML + itemL;
                        } else {
                            rowHTML = rowHTML + itemL; 
                        }                    
                    }                    
                    rowHTML = rowHTML + '</table></div>';                    
                    var row =  $('#elencoDocumentiTBL').DataTable().row(index);
                    row.child(rowHTML).show();                                                            
                    $('#elencoDocumentiTBL').DataTable().row(index).$('.dt-hasChild').removeClass('dt-hasChild');                                                                                                   
                }                
            },
            initComplete: function () {
                /*                
                $('input:radio').on('click', function(e) {                                        
                    console.log(e.currentTarget.name); 
                    console.log(e.currentTarget.value);                        
                    if(!isNaN(e.currentTarget.value)){
                        $('#btnDettaglio').prop('disabled', false);                            
                        sessionStorage.setItem('letteraSelezionata',e.currentTarget.value);
                    }                                                
                });
                */
                $('#elencoDocumentiTBL').on('click', '.rd-lettera', function(e){
                    console.log(e.target.value);                     
                    if(!isNaN(e.target.value)){
                        $('#btnDettaglio').prop('disabled', false);                            
                        sessionStorage.setItem('letteraSelezionata',e.target.value);
                    }
                });                
                setTimeout(() => {
                    $("#spinner").prop('hidden', false);
                },1000)                
                
            }
        };
        this.loaderDT = true;
        this.documentazioneService.ricercaAziendeDocumentiInoltrati()
            .subscribe(aziende => {
                this.listaAziende = aziende;
                this.documentazioneService.listaStatoDocumenti()
                    .subscribe(documenti => {
                        this.listaStatoDocumento = documenti;                        
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
        setTimeout(() => {
            this.loaderDT = false;
        },2000);                
    }   

    associaClick(){
        this.elencoDoc.forEach((value)=>{
            //let btn:HTMLButtonElement=<HTMLButtonElement>document.getElementById(value.nprotocollo);            
            if(document.getElementById(value.nprotocollo)){
                document.getElementById(value.nprotocollo).onclick= function(){
                    console.log('marts ok');    
                };                  
            }           
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
                /*
                this.elencoDoc.forEach(doc => {
                    var tmpDocFlat :iElencoDoc;
                    tmpDocFlat.annualita = doc.annualita;
                    tmpDocFlat.codiceStato = doc.statoDocumentoVO.codiceStato;
                    tmpDocFlat.descrizioneStato = doc.statoDocumentoVO.descrizione;
                    tmpDocFlat.codiceTipoDocumento = doc.tipoDocumentoVO.codiceTipoDocumento;
                    tmpDocFlat.dataProtocollazione = doc.dataProtocollazione;
                    tmpDocFlat.denominazione = doc.anagraficaSoggettoVO.denominazione;
                    tmpDocFlat.idDocumento = doc.idDocumento;
                    tmpDocFlat.insDate = doc.insDate;
                    tmpDocFlat.nProtocollo = doc.nprotocollo;
                    tmpDocFlat.nomeFile = doc.nomeFile;
                    tmpDocFlat.note = doc.note;
                    tmpDocFlat.noteBo = doc.noteBo;
                    tmpDocFlat.rifArchivio = doc.rifArchivio;
                    tmpDocFlat.tipoDocumento = doc.tipoDocumentoVO.descrizione;
                    tmpDocFlat.letteraRispostaPresente = true;

                    this.tableData.push(tmpDocFlat);
                })
                */                
                this.cacheFilterAndData();                                ;
                setTimeout(() => {
                    this.loaderDT = false;
                },2000);               
                this.rerender();                                
            }, err => {
                this.logger.error(err);
            });
        this.isRowSelected = false;
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
        //this.associaClick();
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
        sessionStorage.removeItem('letteraSelezionata');
    }

    goDettaglioDocumentazione() {
        var idLettera = sessionStorage.getItem('letteraSelezionata');
        if(idLettera!=null && idLettera!=undefined){
           
            var letteraSelezionata : Array<DocumentiVO> = this.elencoLettereRisposta.filter(doc => doc.idDocumento === Number.parseInt(idLettera));
            if(letteraSelezionata!=null && letteraSelezionata!= undefined){
                this.isRowSelected = true;
                this.documentazioneService.documentazioneSel = letteraSelezionata[0];
            }
            sessionStorage.removeItem('letteraSelezionata');
        }
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
            
            if (cachedData.filter.anagraficaSoggetto !== null && cachedData.filter.anagraficaSoggetto !== undefined) {
                this.anagraficaSoggettoVO = this.listaAziende.filter(azienda => azienda.idAnag === cachedData.filter.anagraficaSoggetto.idAnag)[0];
            }

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
