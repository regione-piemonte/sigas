import { Component, OnInit, ViewChild, ElementRef, AfterViewInit, Renderer2 } from '@angular/core';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { ImportazioneUTFService } from '../../service/importazione-utf.service';
import { ImportazioneUTFRequest } from '../../../commons/request/importazione-utf-request';
import { LoggerService } from '../../../core/services/logger.service';
import { FormGroup } from '@angular/forms';
import { AnnualitaUTFVO } from '../../../commons/vo/annualita-utf-vo';

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
    this.progress = new AnnualitaUTFVO(null, null, 0, null);
    this.subscribers.ricerca = this.importazioneUTFService.ricercaAnnualita().subscribe(
      data => {
        this.listaAnnualita = data;
        this.listaAnnualita.map( annualita => {
          if (annualita.anno === this.importazioneUTFRequest.annualita) {
            this.progress = new AnnualitaUTFVO(annualita.anno, annualita.errore, annualita.esito, annualita.success);
            if (annualita.esito > 0){
              this.notImportable = true;
            } else {
              this.notImportable = false;
            }
          }
        })
        this.loaderAnno = false;
      }, err => {
          this.logger.error(err);
          this.messageError = err.message;
          this.loaderAnno = false;
      }
    );
  }
}
