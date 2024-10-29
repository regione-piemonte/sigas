import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import localeIt from '@angular/common/locales/it';
import { AppRoutingModule } from './app-routing.module';
import { CoreModule } from './core/core.module';
import { AppComponent } from './app.component';
import { HomeModule } from './home/home.module';
import { AnagraficaSoggettiModule } from './anagrafica-soggetti/anagrafica-soggetti.module';
import { PagamentoFoModule } from './pagamento-fo/pagamento-fo.module';
import { ImportazioneUTFModule } from './importazione-utf/importazione-utf.module';
import { TassiModule } from './tassi/tassi.module';
import { AliquoteModule } from './aliquote/aliquote.module';
import { AccreditamentoModule } from './accreditamento/accreditamento.module';
import { AccreditamentoInserimentoModule } from './accreditamento-fo/accreditamento-inserimento/accreditamento-inserimento.module';
import { AccreditamentoModificaModule } from './accreditamento-fo/accreditamento-modifica/accreditamento-modifica.module';
import { DocumentazioneFoModule } from './documentazione-fo/documentazione-fo.module';
import { DocumentazioneModule } from './documentazione/documentazione.module';
 
registerLocaleData(localeIt, 'it');

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HomeModule,
    AnagraficaSoggettiModule,
    PagamentoFoModule,
    ImportazioneUTFModule,
    AliquoteModule,
    TassiModule,
    CoreModule,
    AccreditamentoModule,
    AccreditamentoInserimentoModule,
    AccreditamentoModificaModule,
    DocumentazioneModule,
    DocumentazioneFoModule
  ],
  providers: [{ provide: LOCALE_ID, useValue: 'it' }],
  bootstrap: [AppComponent]
})
export class AppModule { }
