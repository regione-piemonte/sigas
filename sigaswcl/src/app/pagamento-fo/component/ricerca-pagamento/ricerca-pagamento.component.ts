import { AfterViewInit, Component, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Router } from "@angular/router";
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { Routing } from '../../../commons/routing'
import { PaymentFoService } from '../../service/pagamento-fo.service';
import { LoggerService } from "../../../core/services/logger.service";

import { TipoVersamentiVO } from "../../../commons/vo/tipo-versamenti-vo";
import { UserService } from '../../../core/services/user.service';
import { SubjectVO } from "../../commons/vo/subject-vo";

@Component({
  selector: 'app-ricerca-pagamento',
  templateUrl: './ricerca-pagamento.component.html',
  styleUrls: ['./ricerca-pagamento.component.scss']
})

@DestroySubscribers()
export class RicercaPagamentoComponent implements OnInit, AfterViewInit {
        
  public subscribers: any = {};
  private listSubject: Array<SubjectVO> = [];
  private yearsLoaded: boolean = false;
  private yearsList: Array<string>;  
  private paymentTypes: Array<TipoVersamentiVO>;
  private isBoSeach: boolean = false;
  private noSubjectsFoundforSelectedYear: boolean = false;
  private loadingPageFlag: boolean = false;

  constructor(
    private router: Router,
    private logger: LoggerService,
    private foPayService: PaymentFoService,
    private userService: UserService,
    private renderer: Renderer2)
  { }

  ngAfterViewInit(): void {
    const element = this.renderer.selectRootElement('#idDivToContPrin');
    setTimeout(() => element.focus(), 0);
  }  

  ngOnInit() {
      this.resetForm();

      this.loadingPageFlag = true;
    this.foPayService.retrievePaidYears()
      .subscribe(res => {
        this.yearsList = res;
        this.yearsLoaded = true;
        this.loadingPageFlag = false;
      }, err => { this.logger.error("errore "); });

    this.foPayService.retrievePaymentTypes().subscribe(resp => { 
        this.paymentTypes = resp; 
      },
      err => { this.logger.error(err); });   
    
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
            this.noSubjectsFoundforSelectedYear = !res.length;
            this.subjects = {};
            res.forEach((item, index) => {
              var areas: any = this.subjects[item.denominazione];
                if(!areas) areas = {};

                areas[item.siglaProvincia] = 1;
                this.subjects[item.denominazione] = areas;
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
      
//      this.subscribers.profilo = this.userService.profilatura$.subscribe(data => {
//          this.foPayService.searchReq.operatorFO =  data.codiceFiscaleUtente;
//      }, err => {
//          this.logger.error(err);
//      });
    this.router.navigateByUrl("home/ricerca-soggetto-pagamento")
  }
  
  
  resetForm(){
      this.foPayService.searchReq.year = null;
      this.foPayService.searchReq.dateFrom = null;
      this.foPayService.searchReq.dateTo = null;
      this.foPayService.searchReq.monthFrom = null;
      this.foPayService.searchReq.monthTo = null;
      this.foPayService.searchReq.subjectName = null;
      this.foPayService.cartReq.amount = null;
      this.foPayService.searchReq.area = null;
      this.foPayService.searchReq.operatorFO = null;
      this.foPayService.searchReq.payType = null;
  }

  goBack() {
//      this.router.navigateByUrl(Routing.HOME);
      this.resetForm();
  }
}