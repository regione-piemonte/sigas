/*Angular module */
import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './component/home/home.component';




@NgModule({
  imports: [SharedModule, CommonModule],
  exports: [HomeComponent],
  declarations: [HomeComponent],
  providers: []
})
export class HomeModule { }
