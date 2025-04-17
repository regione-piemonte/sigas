import { Component, OnInit, ViewChild, ElementRef, AfterViewInit, Renderer2 } from '@angular/core';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { ImportazioneUTFService } from '../../service/importazione-utf.service';
import { ImportazioneUTFRequest } from '../../../commons/request/importazione-utf-request';
import { LoggerService } from '../../../core/services/logger.service';
import { FormGroup } from '@angular/forms';
import { AnnualitaUTFVO } from '../../../commons/vo/annualita-utf-vo';
import { ImportUTFVO } from '../../../commons/vo/import-utf-vo';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-importazione-utf',
  templateUrl: './importazione-utf.component.html',
  styleUrls: ['./importazione-utf.component.scss']
})

@DestroySubscribers()
export class ImportazioneUTFComponent implements OnInit, AfterViewInit {

  @ViewChild('utfFileRef')
  utfFileRef: ElementRef;  

  private form: FormGroup;
  private importazioneUTFRequest: ImportazioneUTFRequest;
  private listaAnnualita : Array<AnnualitaUTFVO>;
  private progress: AnnualitaUTFVO;
  private loaderAnno: boolean;
  private loaderImportUTF: boolean;
  private showMessageError: boolean;
  private showSuccess: boolean;
  private showInfo: boolean;
  private messageError: string;
  private messageSuccess: string;
  private notImportable: boolean;
  private perc: Array<number> = [0, 15, 40, 60, 100];
  private percNeg: Array<number> = [0, 15, 40, 80];
  
  public subscribers: any = {};

  //idImportAttuale: number = null;
  idImportSelezionato: number;
  idDichirazionePresenteSelezionata: number;
  listaDichiarazioniPresenti: Array<ImportUTFVO>;
  loaderDichiarazioniPresenti: boolean = false;
  esitoImportAttuale: number = null;

  importAttualeSelected: boolean;
  importSelezionatoSelected: boolean;
  dichirazionePresenteSelezionata: ImportUTFVO;

  eventsSubjectIdImportSelezionato: Subject<number> = new Subject<number>();

  constructor(
    private logger: LoggerService,
    private importazioneUTFService: ImportazioneUTFService,
    private renderer: Renderer2
  ) { }

  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);
  }

  ngOnInit() {

    this.importazioneUTFRequest = new ImportazioneUTFRequest(null, "");
    
    this.showMessageError = false;
    this.showSuccess = false;
    this.showInfo = true;
    
    
    /* 
     //let marts = new AnnualitaUTFVO("2018", "Errore", 4, "Success");
     let marts = new AnnualitaUTFVO("2024", "Errore", 4, "Success");
     let martsList = new Array<AnnualitaUTFVO>();
     martsList.push(marts);
     this.listaAnnualita = martsList;
     */
    
     this.getImportState();
     
  }

  handleFileInput(event) {
    this.importazioneUTFService.importazioneUTFReq = this.importazioneUTFRequest;
    if(event.target.files && event.target.files.length > 0) {

      let filename = event.target.files[0].name;
      if("zip" === filename.split('.').pop())
      {
        this.importazioneUTFRequest.utfFile = event.target.files[0];
        this.showSuccess = false;
        this.showMessageError = false;
      } else {
        this.utfFileRef.nativeElement.value = "";
        this.messageError = "Il file '" +  filename +"' non e' un file zip"; 
        this.showSuccess = false;
        this.showMessageError = true;
      }
      
    }

  }

  onSubmit(){
    this.loaderImportUTF = true;
    let subscription = this.importazioneUTFService.importUTF().subscribe();
    setTimeout(() => {
      subscription.unsubscribe();
    },300000);

    // Gestione Asincrona dell'import
    this.messageSuccess = "Le dichiarazioni sono state prese in carico!";
    this.showSuccess = true;
    this.showMessageError = false;
    this.loaderImportUTF = false;
    this.importazioneUTFRequest = new ImportazioneUTFRequest(null, "");
    this.utfFileRef.nativeElement.value = "";
  }

  cancella() {
    this.importazioneUTFRequest = new ImportazioneUTFRequest(null, "");
    this.utfFileRef.nativeElement.value = "";
    this.showMessageError = false;
    this.showSuccess = false;
  }

  getImportState() {
    this.loaderAnno = true;
    this.idDichirazionePresenteSelezionata = null;
    this.importazioneUTFService.emitImportUTFAnnualitaSelezionataChanged(true);
    this.showMessageError = false;
    this.showSuccess = false;    

    this.progress = new AnnualitaUTFVO(null, null, 0, null);
    this.subscribers.ricerca = this.importazioneUTFService.ricercaAnnualita().subscribe(
       data => {
          this.listaAnnualita = data;
          this.listaAnnualita.forEach( annualita => {
            if (annualita.anno === this.importazioneUTFRequest.annualita) {
              this.progress = new AnnualitaUTFVO(annualita.anno, annualita.errore, annualita.esito, annualita.success);
              if (annualita.esito > 0)
              {
                this.importazioneUTFService.emitImportUTFAnnualitaSelezionata(+annualita.anno);
              } 
            }
          })
          this.loaderAnno = false;
       }, err => {
          this.logger.error(err);
          this.showMessageError = true;
          this.messageError = err.message;
          this.loaderAnno = false;
       }
    );
  }

  /*
  _caricaElencoDichirazioniPresentiAnno(anno:number) {
    this.loaderDichiarazioniPresenti = true;
    this.subscribers.elencoDichiarazioni = this.importazioneUTFService.elencoReportUTFByAnno(anno.toString()).subscribe(
      data => {
        console.log("_caricaElencoDichirazioniPresentiAnno", data);
        let tempDichiarazioniPresenti: ImportUTFVO[] = data.filter(importUtf => importUtf.esito == 4 );
        console.log("tempDichiarazioniPresenti", tempDichiarazioniPresenti);
        // data[0].selectedImport = false;
        this.listaDichiarazioniPresenti = [];
        if(tempDichiarazioniPresenti.length > 0){
          this.idImportAttuale = tempDichiarazioniPresenti[0].importId;
          this.esitoImportAttuale = tempDichiarazioniPresenti[0].esito;
          this.importAttualeSelected = tempDichiarazioniPresenti[0].selectedImport;
          console.log("idImportAttuale", this.idImportAttuale);
          console.log("esitoImportAttuale", this.esitoImportAttuale);
        }       

        this.listaDichiarazioniPresenti = tempDichiarazioniPresenti;       
        this.loaderDichiarazioniPresenti = false;
      }, err => {
          this.logger.error(err);
          this.messageError = err.message;
          this.loaderDichiarazioniPresenti = false;
      }
    );
  }
  */

  /*
  confermaImport() {
    this.importazioneUTFService.confermaImportUTF(this.importazioneUTFRequest.annualita, this.idImportAttuale).subscribe(
      result => {

    }, err => {
        this.logger.error(err);
        this.messageError = err.message;
    });
  }
  */

  /*
  selectDichiarazione(event: any) 
  {    
    this.idDichirazionePresenteSelezionata = this.dichirazionePresenteSelezionata.importId;
    this.importSelezionatoSelected = this.dichirazionePresenteSelezionata.selectedImport;    
    this.importazioneUTFService.emitCompareImportUTFVariazioneIdImportSelezionato(true);
    document.getElementById('compareUTFComponent').scrollIntoView();
  }
  */
}
