import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { Subject } from "rxjs";
import { DataTableDirective } from 'angular-datatables';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { LoggerService } from "../../../core/services/logger.service";
import { PaymentFoService } from '../../service/pagamento-fo.service';
import { SubjectVO } from "../../commons/vo/subject-vo";
import { Location } from '@angular/common';

@Component({
  selector: 'app-ricerca-selezione_soggetto_detail',
  templateUrl: './ricerca_selezione_soggetto_detail.component.html',
  styleUrls: ['./ricerca_selezione_soggetto_detail.component.scss']
})

@DestroySubscribers()
export class SearchSubjectSelectBOComponent implements OnInit {

  @ViewChild(DataTableDirective) dtElement: DataTableDirective;

  private loaderDT: boolean;
  private subjectList: Array<SubjectVO>;
  private dtTrigger: Subject<any> = new Subject();

  public subscribers: any = {};

  constructor(
    private router: Router,
    private logger: LoggerService,
    private foPayService: PaymentFoService,
    private location: Location
  ) { }

  ngOnInit() {
//if(true){this.foPayService.searchReq.year='2019';}

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
//    window.history.go(-1);
      this.location.back();
  }
  
}
