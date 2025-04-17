import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from "@angular/forms";
import { SharedModule} from '../shared/shared.module';
import { ImportazioneUTFComponent } from './component/importazione-utf/importazione-utf.component';
import { ImportazioneUtfRoutingModule } from './importazione-utf-routing.module';
import { CompareImportUTFComponent } from '../importazione-utf/component/compare-import-utf/compare-import-utf.component';
import { CompareImportUTFDettaglioSoggettiComponent } from '../importazione-utf/component/compare-import-utf-dettaglio-soggetti/compare-import-utf-dettaglio-soggetti.component';
import { DataTablesModule } from 'angular-datatables';
import { ImportazioneUTFTabContainerComponent } from './component/importazione-utf-tab-container/importazione-utf-tab-container.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    ImportazioneUtfRoutingModule,
    DataTablesModule,
  ],
  exports: [
    ImportazioneUTFComponent, 
    CompareImportUTFComponent, 
    CompareImportUTFDettaglioSoggettiComponent,
    ImportazioneUTFTabContainerComponent
  ],
  declarations: [ImportazioneUTFComponent, 
                 CompareImportUTFComponent, 
                 CompareImportUTFDettaglioSoggettiComponent,
                 ImportazioneUTFTabContainerComponent]
})
export class ImportazioneUTFModule { }
