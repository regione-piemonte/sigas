import { BrowserModule } from '@angular/platform-browser';
import { NgModule, LOCALE_ID } from '@angular/core';
import { registerLocaleData } from '@angular/common';
import localeIt from '@angular/common/locales/it';
import { AppRoutingModule } from './app-routing.module';
import { CoreModule } from './core/core.module';
import { AppComponent } from './app.component';
import { HomeModule } from './home/home.module';
import { AnagraficaSoggettiModule } from './anagrafica-soggetti/anagrafica-soggetti.module';
import { ImportazioneUTFModule } from './importazione-utf/importazione-utf.module';
import { TassiModule } from './tassi/tassi.module';
import { AliquoteModule } from './aliquote/aliquote.module';

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
    ImportazioneUTFModule,
    AliquoteModule,
    TassiModule,
    CoreModule
  ],
  providers: [{ provide: LOCALE_ID, useValue: 'it' }],
  bootstrap: [AppComponent]
})
export class AppModule { }
