import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { HttpClientModule } from "@angular/common/http";
import { FormsModule } from '@angular/forms';
import { SharedModule } from "../../shared/shared.module";
import { AccreditamentoSuccessComponent } from "./components/accreditamento-success/accreditamento-success.component";
import { ListaServiziService } from "./service/lista-servizi.service";

 
@NgModule({
    imports: [CommonModule,
        FormsModule,
        SharedModule],
    exports: [AccreditamentoSuccessComponent],
    declarations: [AccreditamentoSuccessComponent],
    providers: [ListaServiziService]
})
export class AccreditamentoSharedModule { }