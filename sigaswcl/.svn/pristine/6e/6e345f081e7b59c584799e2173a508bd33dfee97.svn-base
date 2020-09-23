import {Component, OnInit, ViewChild, ElementRef} from '@angular/core';
import {Routing} from '../../../commons/routing';
import {Router} from '@angular/router';
import {DatatableComponent} from '@swimlane/ngx-datatable';
import {DataTableDirective} from 'angular-datatables';
import {Subject} from 'rxjs';
import { saveAs } from "file-saver";
import {DataTableIt} from '../../../commons/class/commons-data-table';
import {LoggerService} from '../../../core/services/logger.service';
import {AnagraficaSoggettiService} from '../../service/anagrafica-soggetti.service';
import { AnaComunicazioniVO } from '../../../commons/vo/ana-comunicazioni-vo';
import { AnagraficaSoggettoVO } from '../../../commons/vo/soggetti-vo';
import { TipoComunicazioniVO } from '../../../commons/vo/tipo-comunicazioni-vo';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { ConfermaAnaComunicazioniRequest } from '../../../commons/request/conferma-anaComunicazioni-request';
import { RicercaAnaComunicazioniRequest } from '../../../commons/request/ricerca-anaComunicazioni-request';

import * as moment from 'moment';
import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';

@Component({
  selector: 'app-documenti',
  templateUrl: './documenti.component.html',
  styleUrls: ['./documenti.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})
@DestroySubscribers()
export class DocumentiComponent implements OnInit {
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  @ViewChild('table') table: DatatableComponent;
  @ViewChild('utfFileRef') utfFileRef: ElementRef; 
  

  public subscribers: any = {};

  
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
  private loaderExcel:boolean;

  private urlDownloadDoc: String;
  private dataDocumentoPicker:any;

  constructor(private router: Router,
              private logger: LoggerService,
              private anagraficaSoggettiService: AnagraficaSoggettiService) {
      this.urlDownloadDoc = this.anagraficaSoggettiService.getUrlScaricaDocumento();
  }

  ngOnInit() {
    this.documento = new AnaComunicazioniVO(0,new AnagraficaSoggettoVO(0,0,'','','','','','',0,'','','',0,false,0,'','',0,0),null,
                                            new TipoComunicazioniVO(null,'',''),null,'','','','','', false);
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
      responsive: true,
      order: [],
      columnDefs: [ {
        'targets'  : 'no-sort'
      }]
    };

    // this.loaderDT = true;
    this.ricercaAnaComunicazioniRequest = new RicercaAnaComunicazioniRequest(0, "Tutti", "Tutte");
    this.tipologia = 'Tutte';
    this.annoDocumento = 'Tutti';
    this.anagraficaSoggettiService.ricercaAnaComunicazioniReq = this.ricercaAnaComunicazioniRequest;
    // if (this.anagraficaSoggettiService.headerDichiarante != null) {
    // this.subscribers.riceraByAnnoAndTipo = this.anagraficaSoggettiService.ricercaDocumentiByAnnoAndTipologia()
    //   .subscribe(res => {
    //     this.loaderDT = false;
    //     this.listaDocumenti = res;
    //     setTimeout(() => {
    //         this.dtTrigger.next();
    //     });
    //   }, err => {
    //     this.logger.error('errore ');
    //   });
    // }
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
        this.rerender();
      }, err => {
        this.logger.error('errore ');
      });
    }
  }

  calcolaAnniRecenti() {
    this.listaAnni = new Array();
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
    this.utfFileRef.nativeElement.value = "";
    this.fileToUpload = null;
    this.fileCaricato = false;
    this.fileModificato = false;
    this.erroreCaricamento = false;
  }

  onClickSalva() {
    let field: string = null;
    this.documentoToSave.dataDocumento =  new Date(this.dataDocumentoPicker.year, this.dataDocumentoPicker.month - 1, this.dataDocumentoPicker.day);
    
    if (this.documentoToSave.tipoComunicazioneVO.idTipoComunicazione == null )
      field = " tipo documnento";
    if (this.documentoToSave.annualita == null)
      field == null? field = " annualita' di riferimento" : field += ", annualita' di riferimento"
    if (this.documentoToSave.dataDocumento == null)
      field == null? field = " data documento" : field += ", data documento"
 
    if(field != null) {
      this.erroreCaricamento = true;
      this.erroreUpload = "E' necessario inserire i valori obbligatori:" + field;
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
    this.documentoToSave.dataDocumento =  new Date(this.dataDocumentoPicker.year, this.dataDocumentoPicker.month - 1, this.dataDocumentoPicker.day);
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
    if (this.dtElement!=null && this.dtElement.dtInstance != null) {
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
        this.rerender();
      }, err => {
        this.logger.error('errore ');
      });
    }
  }

  dettaglioDocumento(anaComunicazioni: AnaComunicazioniVO) {
    this.loaderPage = true;
    this.clearAll();
    this.documentoToSave =  this.documentoToSave = Object.assign({}, anaComunicazioni);
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
            .subscribe( response => {
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
                  this.anagraficaSoggettiService.ricercaAnaComunicazioniReq = new RicercaAnaComunicazioniRequest(0, "Tutti", "Tutte");
                  this.tipologia = 'Tutte';
                  this.annoDocumento = 'Tutti';
                  this.reinit();
                }, err => {
                  this.logger.error( 'errore gestione allarme' );
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
              .subscribe( resp => {
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
                    this.logger.error( 'errore gestione allarme' );
                });
            });
          this.goBack();
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

  goExcel(){
    console.log('Scarica Excel');
    this.loaderExcel = true;
    this.anagraficaSoggettiService.annoDichiarazione = this.annoDocumento;
    this.subscribers.scaricaExcelElencoSoggetti = this.anagraficaSoggettiService.scaricaExcelElencoDocumenti(Number(this.tipologia))
      .subscribe(
         res => {
           this.loaderExcel = false;
           saveAs(res, "export_elenco_documenti_" + this.anagraficaSoggettiService.headerDichiarante.denominazione +".xls");
           
         },
         err => {
             this.logger.error("errore in download excel");
         }
     );
  }

  scaricaDocumento(idComunicazione) {
    return this.anagraficaSoggettiService.getUrlScaricaDocumento() + idComunicazione;
 }

}
