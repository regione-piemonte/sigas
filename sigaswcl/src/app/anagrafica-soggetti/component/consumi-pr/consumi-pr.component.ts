import { Component, ViewChild, OnInit } from '@angular/core';
import { Subject } from "rxjs";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { DestroySubscribers } from '../../../core/commons/decorator/destroy-unsubscribers';
import { AnagraficaSoggettiService } from '../../service/anagrafica-soggetti.service';
import { ConsumiPrVO } from '../../../commons/vo/consumi-pr-vo';
import { LoggerService } from '../../../core/services/logger.service';
import { DataTableIt } from "../../../commons/class/commons-data-table";
import { Province } from "../../../commons/province";
import { saveAs } from "file-saver";
import { DownloadReport } from "../../../commons/request/download-Report-request";
import { ScartoVO } from '../../../commons/vo/scarto-vo';
import { AliquotaVO } from '../../../commons/vo/aliquota-vo';
import { StoricoConsumiVO } from '../../../commons/vo/storico-consumi-vo';
import { NuovoAllacciamentoVO } from '../../../commons/vo/nuovo-allacciamento-vo';
import { DataTableDirective } from 'angular-datatables';
import { MessaggiVO } from '../../../commons/vo/messaggi-vo';

@Component({
  selector: 'app-consumi-pr',
  templateUrl: './consumi-pr.component.html',
  styleUrls: ['./consumi-pr.component.scss']
})

@DestroySubscribers()
export class ConsumiPrComponent implements OnInit {
    
    @ViewChild(DataTableDirective) dtElement: DataTableDirective;
  private listaAliquote: Array<AliquotaVO>;
  private modificaConsumi: boolean;
  private aliquota: number;
  private sommaScarti: number;
  private listaScarti: Array<ScartoVO>;
    private listaMessaggiCtrlImporti: Array<MessaggiVO>;
  private scarto: ScartoVO;
  private consumi: ConsumiPrVO;
  private loaderPage: boolean;
  private dtTrigger: Subject<any> = new Subject();
  private dtOptions: any;
  private consumiPr: Array<ConsumiPrVO>;
  private id: number;
  private anno: string;
  private loaderPageStorico: boolean;
  private storicoModificheList: Array<StoricoConsumiVO>;
  private isModificato: Array<boolean>;
  private hasScarti: Array<boolean>;
  private loaderExcel: boolean;
  private validazione: boolean;
  private loaderPageCompensazione: boolean;
    public loaderDT: boolean;

  public subscribers: any = {};

  constructor(
    private router: Router,
    private logger: LoggerService,
    private anagraficaSoggettiService: AnagraficaSoggettiService,
  ) { }

  ngOnInit() {
     // Move on top
     window.scrollTo(0, 0);

     this.isModificato = new Array<boolean>();
     this.hasScarti = new Array<boolean>();
     this.sommaScarti = 0;

     this.dtOptions = {
      searching: false,
      paging: false,
      info: false,
      language: DataTableIt.language,
      ordering: false,
      columnDefs: [
        { targets: '_all', className: 'dt-nowrap' },
        { targets: '_all', className: 'dt-center' }
      ]
    };

    if (this.anagraficaSoggettiService.headerDichiarante) {
     this.loaderPage = true;
     this.reInitSoggetto();
    }

    // Ordine da seguire per stampare i valori nell'array: 5-6-1-2-3-4
    // Per fare ciò è necessario riordinare l'array
    this.anagraficaSoggettiService.getAllAliquote()
    .subscribe(resp => {
      this.listaAliquote = resp;
    }, err => {
      this.logger.error(err);
    });
  }

  getProvincia(provincia: string): string {
   
    let prov = Province[provincia];
    
    if (!prov)
      return provincia;
    return prov;
    
  }
  
  reInit(){
//      if(this.consumi !=null && this.consumi!=undefined){
          
          this.loaderPage = true;
          console.log('Eseguo re init');
          
          this.anagraficaSoggettiService.ricercaConsumiForProvince()
          .subscribe(res => {
           if (res.length!=0 && res[0].provincia_erogazione !=null) {
             this.consumiPr = res;
             let idconsumi = 0;
             let totInd1200 = 0;
             let totIndUp = 0;
             let totCiv120 = 0;
             let totCiv480 = 0;
             let totCiv1560 = 0;
             let totCivUp = 0;
             let totNewAll = 0;
             let totInd = 0;
             let totCiv = 0;
             let rett = 0;
             let arr = 0;
             let rateoDich = 0;
             let congDich = 0;
             let congCalc = 0;
             let totCalc = 0;
             let totaleDich = 0;
             let totaleDichOrigine = 0;
             let conguaglioDichPrec = 0;
             let rateoDichPrec = 0;
             res.map((elem, idx) => {
               idconsumi = elem.id_consumi;
               totInd1200 += elem.usi_industriali_1200;
               totIndUp += elem.usi_industriali_up;
               totCiv120 += elem.usi_civili_120;
               totCiv480 += elem.usi_civili_480;
               totCiv1560 += elem.usi_civili_1560;
               totCivUp += elem.usi_civili_up;
               totNewAll += elem.tot_nuovi_allacciamenti;
               totInd += elem.tot_industriali;
               totCiv += elem.tot_civili;
               rett += elem.rettifiche;
               arr += elem.arrotondamenti;
               rateoDich += elem.rateo_dich;
               congDich += elem.conguaglio_dich;
               congCalc += elem.conguaglio_calc;
               totCalc += elem.totaleCalcolato;
               totaleDich += elem.totaleDich;    
               totaleDichOrigine += elem.totaleDichOrigine;    
               this.anagraficaSoggettiService.storicoConsumi( elem.id_consumi)
               .subscribe( ret => {
                 
                 if (ret.length > 0) {
                   this.isModificato[idx] = true;
                 } else {
                   this.isModificato[idx] = false;
                 }

               }, err => {
                 this.logger.error("errore ");
               });

               this.anagraficaSoggettiService.idConsumiScarti =  elem.id_consumi;

               this.anagraficaSoggettiService.ricercaScartiByIdConsumi().subscribe( ret => {
                 this.listaScarti = ret;
                 // elem.concilia = new Array(ret.length);
                 if (this.listaScarti.length > 0) {
                   let contaConciliati = 0;
                   elem.scarti = true;
                   this.sommaScarti = 0;
                   this.listaScarti.forEach((scarto, index) => {
                     // elem.concilia[index] = scarto.conciliato;
                     if (scarto.conciliato) {
                       contaConciliati++;
                       this.sommaScarti += scarto.aliquota * scarto.consumi;
                     }
                   });

                   if (contaConciliati === this.listaScarti.length) {
                     elem.concilia = true;
                   } else {
                     elem.concilia = false;
                   }
                 } else {
                   elem.scarti = false;
                 }
               });

               if (elem.nuoviAllacciamenti != null) {
                 let totNuoviAllacciamenti = 0;
                 elem.nuoviAllacciamenti.map(na => {
                   totNuoviAllacciamenti += na.importo;
                 });

                 let nuovoAllacciamentoVO: NuovoAllacciamentoVO = new NuovoAllacciamentoVO(0,0, totNuoviAllacciamenti,'');
                 elem.nuoviAllacciamenti.push(nuovoAllacciamentoVO);
               }

             });

            this.loaderPage = false;
            // Add total row
            let consumoPr: ConsumiPrVO = {
               id_consumi: idconsumi,
               annualita: 0,
               user_import: '',
               provincia_erogazione: 'Totali',
               data_presentazione: '',
               stato_dich: '',
               usi_industriali_1200: totInd1200,
               usi_industriali_up: totIndUp,
               usi_civili_120: totCiv120,
               usi_civili_480: totCiv480,
               usi_civili_1560: totCiv1560,
               usi_civili_up: totCivUp,
               tot_nuovi_allacciamenti: totNewAll,
               tot_industriali: totInd,
               tot_civili: totCiv,
               rettifiche: rett,
               arrotondamenti: arr,
               rateo_dich: rateoDich,
               conguaglio_dich: congDich, 
               conguaglio_calc: congCalc,
               addizionale_liquidata: 0,
               totaleCalcolato: totCalc,
               totaleDich: totaleDich,
               totaleDichOrigine: totaleDichOrigine,
               nuoviAllacciamenti: null,
               concilia: false,
               scarti: false,
               note: '',
               compensazione: 0,
               conguaglio_dich_prec: conguaglioDichPrec,
               rateo_dich_prec: rateoDichPrec
            };
            this.consumiPr.push(consumoPr);

            // Calling the DT trigger to manually render the table
           for (var index = 0; index < this.consumiPr.length; index++) {
               if(this.consumiPr[index].provincia_erogazione===this.consumi.provincia_erogazione){
                   this.consumi = this.consumiPr[index];
               }
           }
           
        // Calling the DT trigger to manually render the table
           setTimeout(() => {
             this.dtTrigger.next();    
           });
           
           } else {
             this.loaderPage = false;
           }
          }, err => {
            this.logger.error("errore ");
            this.loaderPage = false;
        });
          
          
//      }
  }

  reInitSoggetto() {
    this.sommaScarti = 0;

    this.anagraficaSoggettiService.ricercaConsumiForProvince()
       .subscribe(res => {
        if (res.length!=0 && res[0].provincia_erogazione !=null) {
          this.consumiPr = res;
          let idconsumi = 0;
          let totInd1200 = 0;
          let totIndUp = 0;
          let totCiv120 = 0;
          let totCiv480 = 0;
          let totCiv1560 = 0;
          let totCivUp = 0;
          let totNewAll = 0;
          let totInd = 0;
          let totCiv = 0;
          let rett = 0;
          let arr = 0;
          let rateoDich = 0;
          let congDich = 0;
          let congCalc = 0;
          let totCalc = 0;
          let totaleDich = 0;
          let totaleDichOrigine = 0;
          let conguaglioDichPrec = 0;
          let rateoDichPrec = 0;
          res.map((elem, idx) => {
            idconsumi = elem.id_consumi;
            totInd1200 += elem.usi_industriali_1200;
            totIndUp += elem.usi_industriali_up;
            totCiv120 += elem.usi_civili_120;
            totCiv480 += elem.usi_civili_480;
            totCiv1560 += elem.usi_civili_1560;
            totCivUp += elem.usi_civili_up;
            totNewAll += elem.tot_nuovi_allacciamenti;
            totInd += elem.tot_industriali;
            totCiv += elem.tot_civili;
            rett += elem.rettifiche;
            arr += elem.arrotondamenti;
            rateoDich += elem.rateo_dich;
            congDich += elem.conguaglio_dich;
            congCalc += elem.conguaglio_calc;
            totCalc += elem.totaleCalcolato;
            totaleDich += elem.totaleDich;      
            totaleDichOrigine += elem.totaleDichOrigine;      
            this.anagraficaSoggettiService.storicoConsumi( elem.id_consumi)
            .subscribe( ret => {
              
              if (ret.length > 0) {
                this.isModificato[idx] = true;
              } else {
                this.isModificato[idx] = false;
              }

            }, err => {
              this.logger.error("errore ");
            });

            this.anagraficaSoggettiService.idConsumiScarti =  elem.id_consumi;

            this.anagraficaSoggettiService.ricercaScartiByIdConsumi().subscribe( ret => {
              this.listaScarti = ret;
              // elem.concilia = new Array(ret.length);
              if (this.listaScarti.length > 0) {
                let contaConciliati = 0;
                elem.scarti = true;
                this.sommaScarti = 0;
                this.listaScarti.forEach((scarto, index) => {
                  // elem.concilia[index] = scarto.conciliato;
                  if (scarto.conciliato) {
                    contaConciliati++;
                    this.sommaScarti += scarto.aliquota * scarto.consumi;
                  }
                });

                if (contaConciliati === this.listaScarti.length) {
                  elem.concilia = true;
                } else {
                  elem.concilia = false;
                }
              } else {
                elem.scarti = false;
              }
            });

            if (elem.nuoviAllacciamenti != null) {
              let totNuoviAllacciamenti = 0;
              elem.nuoviAllacciamenti.map(na => {
                totNuoviAllacciamenti += na.importo;
              });

              let nuovoAllacciamentoVO: NuovoAllacciamentoVO = new NuovoAllacciamentoVO(0,0, totNuoviAllacciamenti,'');
              elem.nuoviAllacciamenti.push(nuovoAllacciamentoVO);
            }

          });

         this.loaderPage = false;
         // Add total row
         let consumoPr: ConsumiPrVO = {
            id_consumi: idconsumi,
            annualita: 0,
            user_import: '',
            provincia_erogazione: 'Totali',
            data_presentazione: '',
            stato_dich: '',
            usi_industriali_1200: totInd1200,
            usi_industriali_up: totIndUp,
            usi_civili_120: totCiv120,
            usi_civili_480: totCiv480,
            usi_civili_1560: totCiv1560,
            usi_civili_up: totCivUp,
            tot_nuovi_allacciamenti: totNewAll,
            tot_industriali: totInd,
            tot_civili: totCiv,
            rettifiche: rett,
            arrotondamenti: arr,
            rateo_dich: rateoDich,
            conguaglio_dich: congDich, 
            conguaglio_calc: congCalc,
            addizionale_liquidata: 0,
            totaleCalcolato: totCalc,
            totaleDich: totaleDich,
            totaleDichOrigine: totaleDichOrigine,
            nuoviAllacciamenti: null,
            concilia: false,
            scarti: false,
            note: '',
            compensazione: 0,
            conguaglio_dich_prec: conguaglioDichPrec,
            rateo_dich_prec: rateoDichPrec
         };
         this.consumiPr.push(consumoPr);

         // Calling the DT trigger to manually render the table
        setTimeout(() => {
          this.dtTrigger.next();    
        });
        } else {
          this.loaderPage = false;
        }
       }, err => {
         this.logger.error("errore ");
         this.loaderPage = false;
     });
  }

  goBack() {
    this.router.navigateByUrl(Routing.ELENCO_CONSUMI);
  }

  goBackModifica() {
    this.modificaConsumi = false;
    this.reInitSoggetto();
  }

  goExcel() {
    this.loaderExcel = true;
    this.subscribers.scarica = this.anagraficaSoggettiService.scaricaExcel().subscribe(
        res => {
          this.loaderExcel = false;
          saveAs(res, "export_dichiarazione_consumi_" + this.anagraficaSoggettiService.headerDichiarante.denominazione +
            "_" + this.anagraficaSoggettiService.annoDichiarazione + '.xls');
          
        },
        err => {
            this.logger.error("errore in download excel");
        }
    );
  }

  modificaDichConsumi(el: ConsumiPrVO) {
    this.modificaConsumi = true;
    this.consumi = el;
    console.log(this.consumi);

    this.anagraficaSoggettiService.idConsumiScarti = this.consumi.id_consumi;

    this.anagraficaSoggettiService.ricercaScartiByIdConsumi()
    .subscribe(resp => {
      this.listaScarti = resp;
    }, err => {
      this.logger.error(err);
    });
    
    
//    this.anagraficaSoggettiService.controlloImportiConsumi()
//    .subscribe(resp => {
//      this.listaMessaggiCtrlImporti= resp;
//    }, err => {
//      this.logger.error(err);
//    });
    
    

  }

  getAliquota(id: number) {
    let aliquota: number;
    this.listaAliquote.forEach(el => {
      if (el.tipoAliquote.idTipoAliquota === id) {
        aliquota = el.aliquota;
      }
    });

    return aliquota;
  }

  conciliaScarto(scarto: ScartoVO, idx: number) {
    console.log(idx);
    let importo = scarto.aliquota * scarto.consumi;  
    
    scarto.conciliato = !scarto.conciliato;
    if (scarto.conciliato) {
      this.sommaScarti += importo;
    } else {
      this.sommaScarti -= importo;
    }

}

  salvaDichConsumi() {
    this.loaderPage = true;

    this.consumi.tot_civili = this.consumi.usi_civili_120 * this.getAliquota(1) +
                              this.consumi.usi_civili_480 * this.getAliquota(2) +
                              this.consumi.usi_civili_1560 * this.getAliquota(3) +
                              this.consumi.usi_civili_up * this.getAliquota(4);

    this.consumi.tot_industriali = this.consumi.usi_industriali_1200 * this.getAliquota(5) + 
                                  this.consumi.usi_industriali_up * this.getAliquota(6);
    
    this.consumi.conguaglio_calc = this.consumi.conguaglio_calc + this.consumi.tot_civili + this.consumi.tot_industriali + 
                                    this.consumi.tot_nuovi_allacciamenti + this.consumi.arrotondamenti + 
                                    this.consumi.rettifiche + this.sommaScarti - this.consumi.totaleCalcolato

    this.consumi.totaleCalcolato = this.consumi.tot_civili + this.consumi.tot_industriali + 
                                    this.consumi.tot_nuovi_allacciamenti + this.consumi.arrotondamenti + 
                                    this.consumi.rettifiche + this.sommaScarti;
    
    

    this.anagraficaSoggettiService.confermaModificaConsumi(this.consumi, this.listaScarti).subscribe(
      resp => {
          console.log(resp);
          this.modificaConsumi = false;
          this.goBackModifica();
          this.consumiPr.forEach(consumo => {
            if (consumo.id_consumi != this.consumi.id_consumi) {
              this.anagraficaSoggettiService.updateCompensazioneConsumi(consumo).subscribe(
                resp => {
                  console.log(resp);
                }, err => {
                  this.logger.error("errore salvataggio consumi");
              });
            }
          });
      }, err => {
        this.logger.error("errore salvataggio consumi");
    });

    

  }


  storicoModifiche(idconsumi: number) {
    this.loaderPageStorico = true;
    this.anagraficaSoggettiService.storicoConsumi(idconsumi).subscribe(
      resp => {
          console.log(resp);
          this.storicoModificheList = resp;
          this.loaderPageStorico = false;
      }, err => {
        this.logger.error("errore salvataggio consumi");
    });
  }

  ripristinModifica(idConsumo: number) {
    this.loaderPageStorico = true;
    this.anagraficaSoggettiService.ripristinaModificaConsumi(idConsumo, this.consumi.id_consumi).subscribe(
      resp => {
          console.log(resp);
//          this.consumi = resp;
          this.goBackModifica();
//          this.reInitSoggetto();

      }, err => {
        this.logger.error("errore salvataggio consumi");
    });
  }

  valida() {
    this.anagraficaSoggettiService.validaSoggetto(!this.validazione).subscribe(
      resp => {
          console.log(resp);
      }, err => {
        this.logger.error("errore salvataggio consumi");
    });
  }

}
