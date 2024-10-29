import { AfterViewInit, Component, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { Routing } from '../../../commons/routing'
import { PaymentFoService } from '../../service/pagamento-fo.service';
import { LoggerService } from "../../../core/services/logger.service";

import { TipoVersamentiVO } from "../../../commons/vo/tipo-versamenti-vo";


import {NgbDateParserFormatter} from '@ng-bootstrap/ng-bootstrap';
import {NgbDateCustomParserFormatter} from '../../../commons/class/dateformat';
import { SubjectVO } from "../../commons/vo/subject-vo";

@Component({
  selector: 'app-ricerca-pagamento-bo',
  templateUrl: './ricerca-pagamento-bo.component.html',
  styleUrls: ['./ricerca-pagamento-bo.component.scss'],
  providers: [
              {provide: NgbDateParserFormatter, useClass: NgbDateCustomParserFormatter}
          ]
})

@DestroySubscribers()
export class RicercaPagamentoBOComponent implements OnInit, AfterViewInit {
        
  public subscribers: any = {};
  private yearsLoaded: boolean = false;
  private loadingPageFlag: boolean = false;

  private yearsList: Array<string> = [];
  private operastorList: Array<string> = [];
  private paymentTypes: Array<TipoVersamentiVO> = [];
  private listSubject: Array<SubjectVO> = [];

  private isBoSeach: boolean = false;
  private noSubjectsFoundforSelectedYear: boolean = false;

  constructor(
    private router: Router,
    private logger: LoggerService,
    private foPayService: PaymentFoService,
    private renderer: Renderer2) { }

  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);
  }  

  ngOnInit() {
    this.resetForm();

    this.loadingPageFlag= true;
    this.foPayService.retrievePaidYears()
      .subscribe(res => {
        this.yearsList = res;
        this.yearsLoaded = false;
        this.loadingPageFlag = false;
      }, err => { this.logger.error("errore "); });

    this.foPayService.retrievePaymentTypes().subscribe(resp => { 
        this.paymentTypes = resp; 
      },
      err => { this.logger.error(err); });

  }

  changeSubject(event: any){
      
  }
  private subjects: { [key: string]: 
      { [key: string]: string }
    } = {};

  changeYear(isEvent: any) {
    if(isEvent) {
      this.subjects = {};
      this.foPayService.searchReq.subjectName = null;
      this.foPayService.searchReq.area = null;
    }

    
    
    this.foPayService.retrieveFoSubjectsForSearch(this.foPayService.searchReq.year).subscribe(
        res => {
            this.listSubject = res;
          if(res) {
            this.operastorList = [];
            this.noSubjectsFoundforSelectedYear = !res.length;
            this.subjects = {};
            res.forEach((item, index) => {
              if(!this.operastorList.includes(item.codiceFiscale)) 
                this.operastorList.push(item.codiceFiscale);

              var areas: any = this.subjects[item.denominazione];
                if(!areas) areas = {};

                areas[item.siglaProvincia] = 1;
                this.subjects[item.denominazione] = areas;
                
                console.log(this.subjects[item.denominazione]);
              });
          }
        },
        err => { this.logger.error("errore " + err); });
  }

  onSubmit() {
      this.listSubject.forEach((item, index) => {
          if(this.foPayService.searchReq.subjectName==item.denominazione){
              this.foPayService.searchReq.subjectName=item.nomeAzienda;
          }    
          
      });
    this.router.navigateByUrl("home/ricerca-soggetto-pagamento-bo");
  }
  
  resetForm(){
      this.foPayService.searchReq.year = null;
      this.foPayService.searchReq.dateFrom = null;
      this.foPayService.searchReq.dateTo = null;
      this.foPayService.searchReq.monthFrom = null;
      this.foPayService.searchReq.monthTo = null;
      this.foPayService.searchReq.subjectName = null;
//      this.foPayService.cartReq.amount = null;
      this.foPayService.searchReq.codiceFiscalePIva = null;
      this.foPayService.searchReq.area = null;
      this.foPayService.searchReq.operatorFO = null;
      this.foPayService.searchReq.payType = null;
  }

  goBack() {
      this.resetForm();
//      this.router.navigateByUrl(Routing.HOME);
  }
  
  resetDataIncassoFrom(){
      console.log('Sono in resetDataIncassoFrom');
      this.foPayService.searchReq.dateFrom = null;
  }
  
//  changeFilterDataFrom(event) {
//      console.log(event);
//      
//      this.foPayService.searchReq.dateFrom = new Date(event.year, event.month - 1, event.day);
//  }
  
  
  resetDataIncassoTo(){
      console.log('Sono in resetDataIncassoTo');
      this.foPayService.searchReq.dateTo = null;
  }
  
//  changeFilterDataTo(event) {
//      console.log(event);
//      
//      this.foPayService.searchReq.dateTo = new Date(event.year, event.month - 1, event.day);
//  }
}