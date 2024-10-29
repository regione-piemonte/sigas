import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SigasAlertComponent } from './component/sigas-alert/sigas-alert.component';
import { SigasSpinnerComponent } from './component/sigas-spinner/sigas-spinner.component';
import { SigasSpinnerFormComponent } from './component/sigas-spinner-form/sigas-spinner-form.component';
import { SigasSuccessComponent } from './component/sigas-success/sigas-success.component';
import { LuoghiService } from './service/luoghi-service';
import { SigasDialogComponent } from './component/sigas-dialog/sigas-dialog.component';
import { ProvinciaPipe } from '../commons/filtri/provincia.pipe';
import { AnnualitaPipe } from '../commons/filtri/annualita.pipe';
import { UtilsService } from './service/utils-service';

@NgModule({
  imports: [
    CommonModule
  ],
  exports: [
    SigasAlertComponent,SigasSuccessComponent,
    SigasDialogComponent,
    SigasSpinnerComponent,
    SigasSpinnerFormComponent,
    ProvinciaPipe,
    AnnualitaPipe
  ],
  declarations: [
    SigasAlertComponent,SigasSuccessComponent,
    SigasDialogComponent,
    SigasSpinnerComponent,
    SigasSpinnerFormComponent,
    ProvinciaPipe,
    AnnualitaPipe
    
  ],
  providers: [LuoghiService, UtilsService]
})
export class SharedModule { }
