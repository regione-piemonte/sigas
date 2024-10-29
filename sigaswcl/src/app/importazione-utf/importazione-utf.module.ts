import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from "@angular/forms";
import { SharedModule} from '../shared/shared.module';
import { ImportazioneUTFComponent } from './component/importazione-utf/importazione-utf.component';
import { ImportazioneUtfRoutingModule } from './importazione-utf-routing.module';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    ImportazioneUtfRoutingModule
  ],
  exports: [
    ImportazioneUTFComponent
  ],
  declarations: [ImportazioneUTFComponent]
})
export class ImportazioneUTFModule { }
