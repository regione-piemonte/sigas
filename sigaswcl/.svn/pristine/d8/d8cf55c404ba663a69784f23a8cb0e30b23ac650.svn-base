import { ProvinceVO } from './../../../commons/vo/luoghi-vo/province-vo';
import { AnagraficaSoggettiService } from './../../service/anagrafica-soggetti.service';
import { Component, ViewChild, OnInit, OnDestroy, OnChanges, Output, EventEmitter, Input } from '@angular/core';
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { DataTableIt } from '../../../commons/class/commons-data-table';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import { RicercaVersamentiRequest } from '../../../commons/request/ricerca-versamenti-request';
import { VersamentiPrVO } from '../../../commons/vo/versamenti-pr-vo';
import { ISubscription } from 'rxjs/Subscription';


@Component({
  selector: 'app-accertamenti',
  templateUrl: './accertamenti.component.html',
  styleUrls: ['./accertamenti.component.scss'],
})

@DestroySubscribers()
export class AccertamentiComponent implements OnInit , OnDestroy {

  @ViewChild(DataTableDirective) dtElement: DataTableDirective;
  @Output() listaDaCalcolare = new EventEmitter<Array<VersamentiPrVO>>();
  public dtTrigger: Subject<any> = new Subject();
  public dtOptions: any;
  public loaderYear: boolean;
  private loaderProvince: boolean;
  private subscribers: any ={}
  anniAccertamenti: Array<string>;
   province: Array<ProvinceVO>;
  private ricercaVersamentiRequest: RicercaVersamentiRequest;
  elencoAccertamenti: Array<VersamentiPrVO>;
  public loaderDT: boolean;
  accertamentiDaCalcolare:  Array<VersamentiPrVO>;
  showAccertamentiDaCalcolare: boolean;
  sommaDaAccertare: number;
  allarmeVersamento:false;
  nonValidi:number;
  provinciaSelezionata: string;
  annoSelezionato:string;
  accertamentoSelezionato: VersamentiPrVO;
  checkCalcolo: boolean;
  ReadOnlyStyleAllarm: boolean;


  constructor(private anagraficaService: AnagraficaSoggettiService) { }

 ngOnInit() {
     
  this.checkCalcolo = true;
  this.anniAccertamenti = [];
  this.province = [];
  this.elencoAccertamenti = [];
  this.accertamentiDaCalcolare = [];
  this.sommaDaAccertare = 0;
  this.nonValidi = 0;
  this.ReadOnlyStyleAllarm = true;
    this.showAccertamentiDaCalcolare = false;
    this.dtOptions = {
      searching: false,
      paging: false,
      info: false,
      language: DataTableIt.language,
      ordering: false,
      columnDefs: [
                   { className: 'dt-center', "targets": [1,2,3,4,5,6,7] },
                   { width: '15%', targets: 2 },
                   { width: '15%', targets: 3 }, 
                   { width: '10%', targets: 4 },
                   { width: '20%', targets: 5 }
                 ]
    };
    
    this.loaderYear = true;
    if (this.anagraficaService.headerDichiarante != null && this.anagraficaService.headerDichiarante.idAnag != null) {
      const subs = this.anagraficaService.ricercaAnniVersamenti()
      .subscribe(res => {
          this.anniAccertamenti = res;
          this.loaderYear = false;
      });
      this.loaderProvince = true;
      const ricercaPerProvince = this.anagraficaService.ricercaProvinceVersamenti().
      subscribe(res => {
        this.province = res;
        this.loaderProvince = false;
      });
      if (this.anagraficaService.headerDichiarante != null) {
        console.log('header', this.anagraficaService.headerDichiarante )
          this.subscribers.Accertamenti = this.anagraficaService.ricercaAccertamenti(this.annoSelezionato, this.provinciaSelezionata)
          .subscribe(res => {
            this.elencoAccertamenti = res;
            this.loaderDT = false;
            setTimeout(() => {
                this.dtTrigger.next(); 
            });
          });
      }
      console.log('elenco', this.elencoAccertamenti);
    }

  }

  reInit() {
    if (this.anagraficaService.headerDichiarante) {
      console.log('header', this.anagraficaService.headerDichiarante )
      this.loaderDT = true;
        this.subscribers.Accertamenti = this.anagraficaService.ricercaAccertamenti(this.annoSelezionato, this.provinciaSelezionata)
        .subscribe(res => {
          this.elencoAccertamenti = res;
          this.loaderDT = false;
          this.rerender();
        });
    }
    console.log('elenco', this.elencoAccertamenti);
  }

  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
       dtInstance.destroy();
       this.dtTrigger.next();
   });
  }

  onSelectAccertamento(event) {
      let i = <number>event.target.value;
      let elemento: VersamentiPrVO =  this.elencoAccertamenti[i];
      if (event.target.checked === true ) {
          if (elemento.importoComplessivo === 0 ) {
            this.accertamentiDaCalcolare.push(elemento);
            this.sommaDaAccertare += this.elencoAccertamenti[i].importo;
          }
          if (elemento.importoComplessivo > 0) {
            
            this.nonValidi++;
            console.log('non validi checkede ', this.nonValidi);
          }
      }
      if (event.target.checked === false) {
        let x = this.accertamentiDaCalcolare.indexOf(this.elencoAccertamenti[i]);
        console.log('unchecked X',x);
        if (x > -1) {
          this.sommaDaAccertare -= this.elencoAccertamenti[x].importo;
          this.accertamentiDaCalcolare = this.accertamentiDaCalcolare
        .filter(item => item !== this.accertamentiDaCalcolare[x]);
        console.log('Accertamenti da calcolare dopo', this.accertamentiDaCalcolare);
        }
        if(x === -1) {
          this.nonValidi--;
          console.log('non validi unchecked ',this.nonValidi);
        }
      }
 }


 getAnniAccertamento() {
   return this.anniAccertamenti;
 }

 getProvinceAccertamento() {
  return this.province;
}

 getElencoAccertamenti() {
  return this.elencoAccertamenti;
 }

 showCalcolaAccertamenti() {
  if (this.accertamentiDaCalcolare.length > 0 && this.nonValidi === 0) {
      this.showAccertamentiDaCalcolare = true;
      this.listaDaCalcolare.emit(this.accertamentiDaCalcolare);
  } else {
      this.checkCalcolo = false;
    //alert('Selezionare almeno un accertamento prima di proseguire');
  }
 }

 getAccertamentiDaCalcolare() {
  return this.accertamentiDaCalcolare;
 }

 backToChoiche(event) {
  /*console.log(event);
  this.accertamentiDaCalcolare = [];
  this.showAccertamentiDaCalcolare = event;*/
     this.loaderDT = true;
  this.ngOnInit();
 }

 svuotaElencoDaAccertare(event) {
  this.accertamentiDaCalcolare = event;
  console.log('accertamenti da calcolare svuotato', this.accertamentiDaCalcolare);
 }


 ngOnDestroy() {
 //this.subscribers.unsubscribe();
 }

}
