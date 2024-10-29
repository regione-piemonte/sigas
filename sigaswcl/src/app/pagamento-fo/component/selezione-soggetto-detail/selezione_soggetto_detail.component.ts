import { AfterViewInit, Component, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { Subject } from "rxjs";
import { DataTableDirective } from 'angular-datatables';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { LoggerService } from "../../../core/services/logger.service";
import { PaymentFoService } from '../../service/pagamento-fo.service';
import { SubjectVO } from "../../commons/vo/subject-vo";

@Component({
  selector: 'app-selezione_soggetto_detail',
  templateUrl: './selezione_soggetto_detail.component.html',
  styleUrls: ['./selezione_soggetto_detail.component.scss']
})

@DestroySubscribers()
export class SelezioneSoggettoDetailComponent implements OnInit, AfterViewInit {

  @ViewChild(DataTableDirective) dtElement: DataTableDirective;

  private loaderDT: boolean;
  private subjectList: Array<SubjectVO>;
  private dtTrigger: Subject<any> = new Subject();

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
    window.scrollTo(0, 0);
    this.loaderDT = true;

    this.foPayService.retrieveSubjectPaymentFo()
      .subscribe(resp => {
        this.subjectList = resp;
        this.loaderDT = false;
        setTimeout(() => { this.dtTrigger.next(); });
      }, err => { this.logger.error(err); });
  }

  checkRow(idanag: string, subjectCode: string) {
    this.foPayService.cartReq.idAnag = idanag;
    this.foPayService.cartReq.subjectCode = subjectCode;
  }

  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
       dtInstance.destroy();
       this.dtTrigger.next();
    });
  }

  onSubmit(){
    this.router.navigateByUrl("home/pagamento-fo-importo")
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
            " provincia " + el.provincie; 
    return frase;
  }
  
}
