import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from "@angular/forms";
import { SharedModule } from '../shared/shared.module';
import { DataTablesModule } from "angular-datatables";
import { SoggettiComponent } from './component/soggetti/soggetti.component';
import { AnagraficaSoggettiRoutingModule } from './anagrafica-soggetti-routing.module';
import { AnagraficaSoggettoComponent } from './component/anagrafica-soggetto/anagrafica-soggetto.component';
import { DettaglioSoggettoComponent } from './component/dettaglio-soggetto/dettaglio-soggetto.component';
import { InserimentoSoggettoComponent } from './component/inserimento-soggetto/inserimento-soggetto.component';
import { ConsumiPrComponent } from './component/consumi-pr/consumi-pr.component';
import { VersamentiComponent } from './component/versamenti/versamenti.component';
import { DocumentiComponent } from './component/documenti/documenti.component';
import { RimborsiComponent } from './component/rimborsi/rimborsi.component';
import { AccertamentiComponent } from './component/accertamenti/accertamenti.component';
import { CalcoloAccertamentoComponent } from './component/accertamenti/calcolo-accertamento/calcolo-accertamento.component';
import { NgbDatepickerModule } from '@ng-bootstrap/ng-bootstrap';
import { OrdinativiIncassoComponent } from './component/ordinativi-incasso/ordinativi-incasso.component';
import { AssociaOrdincSoggettoComponent } from './component/associa-ordinc-soggetto/associa-ordinc-soggetto.component';
import { AssociaOrdincVersamentoComponent } from './component/associa-ordinc-versamento/associa-ordinc-versamento.component';
import { RateoComponent } from './component/rateo/rateo.component';
import { ComparazioneSoggettiComponent } from './component/fusione-societa/comparazione-soggetti/comparazione-soggetti.component';
import { StoricoAnagraficaComponent } from './component/storico-anagrafica/storico-anagrafica.component';
import { AnagraficaSoggettoIncorporatoComponent } from './component/anagrafica-soggetto-incorporato/anagrafica-soggetto-incorporato.component';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    DataTablesModule, 
    SharedModule,
    AnagraficaSoggettiRoutingModule,
    NgbDatepickerModule
  ],
  exports: [
    SoggettiComponent
  ],
  declarations: [SoggettiComponent, 
                 InserimentoSoggettoComponent, 
                 DettaglioSoggettoComponent, 
                 AnagraficaSoggettoComponent, 
                 ConsumiPrComponent, 
                 VersamentiComponent, 
                 DocumentiComponent, 
                 RimborsiComponent, 
                 AccertamentiComponent, 
                 CalcoloAccertamentoComponent, 
                 OrdinativiIncassoComponent, 
                 AssociaOrdincSoggettoComponent, 
                 AssociaOrdincVersamentoComponent,
                 RateoComponent,
                 ComparazioneSoggettiComponent,
                 StoricoAnagraficaComponent,
                 AnagraficaSoggettoIncorporatoComponent]
})
export class AnagraficaSoggettiModule { }
