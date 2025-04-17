import { EventEmitter, Injectable, Output } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ConfigService } from '../../core/services/config.service';
import { RicercaConsumiRequest } from '../../commons/request/ricerca-consumi-request';
import { SearchSubjectPaymentFoRequest } from '../commons/request/search-subject-payment-fo';
import { PaymentStoreCartRequest } from '../commons/request/payment-store-cart-request';
import { LoggerService } from '../../core/services/logger.service';
import { TipoVersamentiVO } from '../../commons/vo/tipo-versamenti-vo';
import { PaymentMethodVO } from "../commons/vo/payment-method-vo";
import { SubjectVO } from "../commons/vo/subject-vo";
import { PaymentRTInfoVO } from "../commons/vo/payment-rt-vo";
import { PaymentRedirectVO } from "../commons/vo/payment-redirect-vo";

import { PaymentCartVO } from "../commons/vo/payment-cart-vo";
import { UtilityCtrlVO } from "../commons/vo/utility-ctrl-vo";
import { ConditionalExpr } from '@angular/compiler';
import { Observable, Subscription } from 'rxjs';

import { saveAs } from "file-saver";
import { GeneraAvvisoPagamentoResponseVO } from '../commons/vo/genera-avviso-pagamento-response-vo';
import { RicevutaPagamentoVO } from '../commons/vo/ricevuta-pagamento-vo';
import { ReportResponseVO } from '../commons/vo/report-response-vo';

@Injectable({
  providedIn: 'root'
})
export class PaymentFoService {

  public navigateTo: string;

  public cartList: { [key: string]: PaymentStoreCartRequest } = {};
  public cartReq: PaymentStoreCartRequest;

  public searchReq: SearchSubjectPaymentFoRequest

  public subjectSelected: PaymentStoreCartRequest;

  @Output() loadCardListEvent = new EventEmitter<boolean>();
  emmettiEventoLoadCardList(loadEseguito: boolean) {    
    this.loadCardListEvent.emit(loadEseguito);
  }

  @Output() selectCartsEvent = new EventEmitter<boolean>();
  emmettiEventoSelectCarts(cartsExist: boolean) {    
    this.selectCartsEvent.emit(cartsExist);
  }

  public meseSelezionatoAggiungiPagamentoCarrello: string;

  constructor(private http: HttpClient,
              private config: ConfigService, 
              private logger: LoggerService) 
  {
    this.cartReq = new PaymentStoreCartRequest();
    this.searchReq = new SearchSubjectPaymentFoRequest();
  }

  public addCurrentCartItemToList() {
    this.cartReq.status = "BOZZA";
    this.cartList[this.cartReq.year+this.cartReq.area+this.cartReq.subjectName+this.cartReq.month+this.cartReq.paymentType /*this.cartReq.getCartKey*/] = this.cartReq;
    this.cartReq = this.cloneCartReq(this.cartReq);
  }

  public cloneCartReq(cart: PaymentStoreCartRequest): PaymentStoreCartRequest{
    let res = new PaymentStoreCartRequest(
      cart.id,
      cart.amount,
      cart.year,
      cart.area,
      cart.subjectName,
      cart.idAnag,
      cart.subjectCode,
      cart.status,
      cart.paymentCode,
      cart.paymentType,
      cart.currentDate,
      cart.payDate,
      cart.email,
      cart.month,
      cart.type,
      cart.cartOption, 
      cart.codiceFiscalePIva,
      cart.iuv);

    return res;
  }

  public resetCartList() {
    this.cartList = {};
  }

  public reloadCart() {
    this.resetCartList();
    this.loadCart();
  }

  public howManyCartItems():number {
    return Object.keys(this.cartList).length;
  }

  public loadCart(loadedCallback: any = null, year: string = null, subjectName: string = null) 
  {
    if(!!this.howManyCartItems()) 
    {
      if(loadedCallback) 
      {
        loadedCallback();
      }      
      return null;
    }

    this.searchCartItems(year, subjectName).subscribe(
          res => {
                    this.loadCartList(res);
                    this.emmettiEventoLoadCardList(true);
                    if(loadedCallback) loadedCallback();
                },
          err => 
                { 
                  this.logger.error("-------errore " + err); 
                }
    );
  }

  public savePaymentCart(paidCallback: any, errorCallback: any) {
    this.storePaymentCart().subscribe(
      res => {
        this.cartReq = this.cloneCartReq(res);
        this.addCurrentCartItemToList();
        paidCallback();
      },
      err => {
        this.logger.error("errore " + JSON.stringify(err)); 
        if(errorCallback) errorCallback(err)
      });
  }

  public insertPaymentCart(paidCallback: any, errorCallback: any) {
    this._insertPaymentCart().subscribe(
      res => {
        this.cartReq = this.cloneCartReq(res);
        //this.cartList = {};
        this.addCurrentCartItemToList();        
        paidCallback();
      },
      err => {
        this.logger.error("errore " + JSON.stringify(err)); 
        if(errorCallback) errorCallback(err)
      });
  }

  public destroyCartItem(id:number, deleteCallback: any) {
    let res = this.deletePaymentCartItem(id);
    res.subscribe(
      res => {
        let key2del:string = null;
        Object.keys(this.cartList).forEach(key => {
          if(!key2del)
            key2del = this.cartList[key].id == id?key:null;
        });

        if(key2del)
          delete this.cartList[key2del];

        deleteCallback();
      },
      err => { this.logger.error("-------errore " + err); });

      return res;
  }

  public destroyAllCart(deleteCallback: any = null, deleteErrCallback: any = null) {
    const res:Observable<any> = this.deletePaymentCartItem(-1 /* all items */);
    res.subscribe(
      res => {
        this.cartList = {};
        this.cartReq = new PaymentStoreCartRequest();
        deleteCallback();
      },
      err => { 
        this.logger.error("-------errore " + err); 
        if(deleteErrCallback)
          deleteErrCallback(err);
      });

      return res;
  }

  public loadCartList(cartArray: Array<PaymentStoreCartRequest>) {
    this.cartList = {};
    if(cartArray.length > 0) {
      console.log("puoi gesstire carrelli");
      this.emmettiEventoSelectCarts(true);
    }
    cartArray.forEach((item, key) => {
      let cartItem:PaymentStoreCartRequest = new PaymentStoreCartRequest(
        item.id,
        item.amount,
        item.year,
        item.area,
        item.subjectName,
        item.idAnag,
        item.subjectCode,
        item.status,
        item.paymentCode,
        item.paymentType,
        item.currentDate,
        item.payDate,
        item.email,
        (item.month!=null)?item.month.toString():null,
        item.type,
        0,
        item.codiceFiscalePIva,
        item.iuv)
        this.cartList[cartItem.cartKey] = cartItem;        
    });
}

public searchYearsForFoUser() {
  return this.http.get<Array<string>>(this.config.getBEServer() + '/rest/fopayment/searchYearsForFoUser');
}

public retriveMessageAndParameters(descParametro: string,descChiaveMessaggio:string) {
    return this.http.get<UtilityCtrlVO>(this.config.getBEServer() + '/rest/utility/retriveMessageAndParameters',
            { params: new HttpParams()
                .append('descParametro', descParametro)
                .append('descChiaveMessaggio', descChiaveMessaggio)});
  }


public retrievePaidYears() {
  return this.http.get<Array<string>>(this.config.getBEServer() + '/rest/fopayment/retrievePaidYears');
}

public getAllPiemonteCounties() {
  return this.http.get<Array<string>>(this.config.getBEServer() + '/rest/fopayment/getAllPiemonteCounties');
}

public retrievePaymentTypes() {
    return this.http.get<Array<TipoVersamentiVO>>(this.config.getBEServer() + '/rest/fopayment/retrievePaymentTypes');
  }

  public getUserAmountsForPrevYear() {
    return this.http.get<Array<string>>(this.config.getBEServer() + '/rest/fopayment/getUserAmountsForPrevYear', 
      { params: new HttpParams()
          .append('idAnag', this.cartReq.idAnag)
          .append('year', this.cartReq.year)});
  }

  public retrievePaymentMethods() {
    return this.http.get<Array<PaymentMethodVO>>(this.config.getBEServer() + '/rest/fopayment/retrievePaymentMethods');
  }

  public retrieveSubjectsForFoUser(year: string=null) {
    return this.http.post<Array<SubjectVO>>(this.config.getBEServer() + '/rest/fopayment/retrieveSubjectsForFoUser', 
          new RicercaConsumiRequest(this.cartReq.year = year||this.cartReq.year, null));
  }

  public retrieveSubjectPaymentFo(year: string=null, name: string=null, area: string=null, idAnag: string = null) {
    return this.http.post<Array<SubjectVO>>(this.config.getBEServer() + '/rest/fopayment/retrieveSubjectPaymentFo', new SearchSubjectPaymentFoRequest(
        this.cartReq.year = year||this.cartReq.year,
        this.cartReq.subjectName = name||this.cartReq.subjectName,
        this.cartReq.area = area||this.cartReq.area,
        null,
        this.cartReq.idAnag = idAnag||this.cartReq.idAnag));
  }

  public retrieveSubjectPaymentFoById(idAnag: string, codiceFiscalePIva: string=null) {
    return this.http.post<Array<SubjectVO>>(this.config.getBEServer() + '/rest/fopayment/retrieveSubjectPaymentFo', new SearchSubjectPaymentFoRequest(
        null, null, null, null, this.cartReq.idAnag = idAnag||this.cartReq.idAnag, this.cartReq.codiceFiscalePIva = codiceFiscalePIva||this.cartReq.codiceFiscalePIva));
  }

  public retrieveFoSubjectsForSearch(year: string=null) {
    return this.http.post<Array<SubjectVO>>(this.config.getBEServer() + '/rest/fopayment/retrieveFoSubjectsForSearch', 
          new RicercaConsumiRequest(this.searchReq.year = year||this.searchReq.year, null));
  }

  public retrieveFoPaymentSubjectsForSearch(year: string=null, 
            name: string=null, 
            area: string=null,
            dateFrom: any=null,
            dateTo: any=null,
            monthFrom: string=null,
            monthTo: string=null,
            operatorFO: string=null,
            vatCode: string=null,
            payType: string=null,
            codiceFiscalePIva: string=null) {
    if(!dateFrom && this.searchReq.dateFrom && this.searchReq.dateFrom.year)
      dateFrom = this.searchReq.dateFrom.year + '-' + this.pad(this.searchReq.dateFrom.month,2) + '-' + this.pad(this.searchReq.dateFrom.day,2);
    if(!dateTo && this.searchReq.dateTo && this.searchReq.dateTo.year)
      dateTo = this.searchReq.dateTo.year + '-' + this.pad(this.searchReq.dateTo.month,2) + '-' + this.pad(this.searchReq.dateTo.day,2);
                
    return this.http.post<Array<SubjectVO>>(this.config.getBEServer() + '/rest/fopayment/retrieveFoPaymentSubjectsForSearch', new SearchSubjectPaymentFoRequest(
        this.cartReq.year = year || this.searchReq.year,
        this.cartReq.subjectName = name || this.searchReq.subjectName,
        this.cartReq.area = area || this.searchReq.area,
        null,null,
        dateFrom || this.searchReq.dateFrom,
        dateTo || this.searchReq.dateTo,
        monthFrom || this.searchReq.monthFrom,
        monthTo || this.searchReq.monthTo,
        operatorFO || this.searchReq.operatorFO,
        vatCode || this.searchReq.vatCode,
        payType || this.searchReq.payType,
        null,null,
        codiceFiscalePIva || this.searchReq.codiceFiscalePIva));
  }
  
  private pad(num, size) {
      let s = num+"";
      while (s.length < size) s = "0" + s;
      return s;
  }

  public storePaymentCart() {
    return this.http.post<PaymentStoreCartRequest>(this.config.getBEServer() + '/rest/fopayment/storePaymentCart', 
                                                   this.cartReq);
  }

  public _insertPaymentCart() {
    return this.http.post<PaymentStoreCartRequest>(this.config.getBEServer() + '/rest/fopayment/insert-payment-cart', 
                                                   this.cartReq);
  }

  public deletePaymentCartItem(id:number = -1) {
    return this.http.post<string>(this.config.getBEServer() + '/rest/fopayment/deletePaymentCartItem', 
                                  new PaymentStoreCartRequest(id, null, null, null, null, null, null, null, this.cartReq.paymentCode));
  }

  public storePaymentInfoToAllCartItems() {
    return this.http.post<string>(this.config.getBEServer() + '/rest/fopayment/storePaymentInfoToAllCartItems', 
              this.cartReq);
  }

  public startCartPayment() {
    var url: string = this.config.getBEServer() + '/rest/fopayment/startCartPayment';
    return this.http.post<string>(url, this.cartReq);
  }

  public searchCartItems(year: string = null, subjectName: string = null, area: string = null) {
    return this.http.post<Array<PaymentStoreCartRequest>>(this.config.getBEServer() + '/rest/fopayment/searchCartItems', new SearchSubjectPaymentFoRequest(
        this.cartReq.year = year||this.cartReq.year,
        this.cartReq.subjectName = subjectName||this.cartReq.subjectName,
        this.cartReq.area = area||this.cartReq.area));
  }

  public searchPaidCartItems(year: string = null, subjectName: string = null, area: string = null, id:string = null) {
    return this.http.post<Array<PaymentStoreCartRequest>>(this.config.getBEServer() + '/rest/fopayment/searchPaidCartItems', new SearchSubjectPaymentFoRequest(
        this.cartReq.year = year||this.cartReq.year,
        this.cartReq.subjectName = subjectName||this.cartReq.subjectName,
        this.cartReq.area = area||this.cartReq.area,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        id));
  }

  public retrieveFoPaymentSubjectDetail(year: string, id: string) {
    return this.http.get<SubjectVO>(this.config.getBEServer() + '/rest/fopayment/retrieveFoPaymentSubjectDetail', 
      { params: new HttpParams()
        .append('idAnag', id)
        .append('year', year)});
  }

  public getPaymentPagoPaRedirectInfo() {
    return this.http.post<PaymentRedirectVO>(this.config.getBEServer() + '/rest/fopayment/getPaymentPagoPaRedirectInfo', 
          this.cartReq);
  }

  public getPaymentCartRT(iuv: string) {
    return this.http.get<PaymentRTInfoVO>(this.config.getBEServer() + '/rest/fopayment/getPaymentCartRT', 
      { params: new HttpParams()
        .append('iuv', iuv)});
  }

  public saveCart() {
      this.saveCartCall()
        .subscribe(res => {
            saveAs(res, 'carrello_' + this.cartReq.paymentCode +"_" +  this.cartReq.year + '.xls');  
        }, err => {
          this.logger.error("errore ");
      });
  }

  private saveCartCall() {
    var url: string = this.config.getBEServer() + '/rest/fopayment/savePaymentCart';
    return this.http.post(url, this.cartReq, { responseType: 'blob' }).map(
      (res) => {
        return new Blob([res], { type: 'application/xls' })
      });
  }

  public getPaymentPagoPaRedirectCheck(url: string) {
    //return this.http.post(url, null);
    return this.http.get(url, {params: new HttpParams().append('Access-Control-Allow-Origin','*')});
  }

  //Notifica pagamento - Bollettino per il pagamento prodotto da PPAY
  public getPaymentNotice(iuv: string) {
    var url: string = this.config.getBEServer() + '/rest/fopayment/avviso-pagamento/' + iuv + '/download';    
    return this.http.post(url, null,  { responseType: 'blob' }).map(
          (res) =>  
          {
            return new Blob([res], { type: 'application/pdf' })
          }
    );    
  }
  
  //Genera avviso pagamento
  public generaPaymentNotice() {
    var url: string = this.config.getBEServer() + '/rest/fopayment/avviso-pagamento';
    return this.http.post(url, this.cartReq,  { responseType: 'blob' }).map(
            (res) =>  
            {
               return new Blob([res], { type: 'application/pdf' })
            }
    );        
  }

  //Ricevuta di pagamento REPORT - template in formato PDF
  public downloadRicevutaPagamento(iuv: string) {
    var url: string = this.config.getBEServer() + '/rest/fopayment/ricevuta-pagamento/' + iuv + '/download';       
    return this.http.post(url, null,  { responseType: 'blob' }).map(
      (res) => {
        return new Blob([res], { type: 'application/pdf' })
      });
  }  

  //Ricevuta pagamento - object
  public getRicevutaPagamento(iuv: string) {
    var url: string = this.config.getBEServer() + '/rest/fopayment/ricevuta-pagamento/' + iuv;    
    return this.http.get<RicevutaPagamentoVO>(url);
  }

  //Redirect URL verso PAGO PA versione V1
  public getPaymentPagoPaURLInfo() {
    var url: string = this.config.getBEServer() + '/rest/fopayment/pago-pa-redirect-info';    
    return this.http.post<PaymentRedirectVO>(url,this.cartReq);
  }

  saveSubjectSelected() {
    this.subjectSelected = new PaymentStoreCartRequest;
    this.subjectSelected.year = this.cartReq.year;
    this.subjectSelected.subjectName = this.cartReq.subjectName;
    this.subjectSelected.codiceFiscalePIva = this.cartReq.codiceFiscalePIva;
  }
}
