import { AfterViewInit, Component, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { Subject } from "rxjs";
import { DataTableDirective } from 'angular-datatables';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { LoggerService } from "../../../core/services/logger.service";
import { PaymentFoService } from '../../service/pagamento-fo.service';
import { SubjectVO } from "../../commons/vo/subject-vo";
import { DataTableIt } from '../../commons/class/commons-data-table';

@Component({
  selector: 'app-ricerca-selezione_soggetto_detail',
  templateUrl: './ricerca_selezione_soggetto_detail.component.html',
  styleUrls: ['./ricerca_selezione_soggetto_detail.component.scss']
})

@DestroySubscribers()
export class SearchSubjectSelectComponent implements OnInit, AfterViewInit {

  @ViewChild(DataTableDirective) dtElement: DataTableDirective;

  private loaderDT: boolean;
  private subjectList: Array<SubjectVO>;
  private dtTrigger: Subject<any> = new Subject();
  private dtOptions: any;

  public subscribers: any = {};

  constructor(
    private router: Router,
    private logger: LoggerService,
    private foPayService: PaymentFoService,
    private renderer: Renderer2
  ) { }

  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);
  }

  ngOnInit() {

    this.dtOptions = {
      searching: true,
      pagingType: 'full_numbers',
      info: false,
      language: DataTableIt.language,
      ordering: true,
      columnDefs: [
        { className: 'dt-center'},
        { orderable: false, targets: 0 }
        
      ]

    };
    
    if(!this.foPayService.searchReq.year)
      this.router.navigateByUrl("home/ricerca-soggetto-pagamento")

    window.scrollTo(0, 0);
    this.loaderDT = true;
 
    this.foPayService.retrieveFoPaymentSubjectsForSearch()
      .subscribe(resp => {
        this.subjectList = resp;
        this.loaderDT = false;
        setTimeout(() => { this.dtTrigger.next(); });
      }, err => { this.logger.error(err); });
  }

  checkRow(idAnag:string, subject: string, id: string) {
    this.foPayService.searchReq.subjectId = idAnag;
//    this.foPayService.searchReq.subjectName = subject;
    this.foPayService.searchReq.id = id;
  }

  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
       dtInstance.destroy();
       this.dtTrigger.next();
    });
  }

  onSubmit(){
    this.router.navigateByUrl("home/ricerca-dettaglio-pagamento")
  }
  
  dettaglioSocieta(consumo: SubjectVO) {
  }

  goHome() {
    this.router.navigateByUrl("home");
  }

  goBack() {
    window.history.go(-1);
  }

  calculateArialLabel(el: SubjectVO){
    let frase: String;
    frase = "Seleziona pagamento associato al soggetto " + el.nomeAzienda + 
            " avente il codice " + el.codiceAzienda +
            " relativo al mese " + el.mesi; 
    return frase;
  }

   public getStatus(status): String {        
      switch(parseInt(status,10)) {
          case 10: /*APERTO                */
              return "Bozza";
          case 20: /*COMPLETO              */
              return "Completo";
          case 30: /*PAGAMENTO AVVIATO     */
              return "In Pagamento";
          case 40: /*PAGAMENTO NOTIFICATO  */
              return "In Elaborazione";
          case 45: /*CREATO AVVISO PAGAMENTO  */
              return "Generato Avviso Pagamento";
          case 50: /*PAGATO                */
              return "Pagato";
          case 51: /*ERRORE PAGAMENTO      */
              return "Errore Pagamento";
      }
      return status;    
  }
  
}
