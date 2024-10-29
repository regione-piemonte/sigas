import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { TemplateRoutingModule } from "./template.routing.module";
import { TemplateService } from "./services/template.service";
import { SharedModule } from "../shared/shared.module";
import { TemplateLetteraRispostaComponent } from "./components/template-lettera-risposta/template-lettera-risposta.component";
import { TemplateContainerComponent } from "./components/template-container/template-container.component";
import { SharedTemplateModule } from "../shared-template/template.module";

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        SharedModule,
        TemplateRoutingModule,
        SharedTemplateModule
    ],
    exports: [
        TemplateContainerComponent,
        TemplateLetteraRispostaComponent
    ],
    declarations: [
        TemplateContainerComponent,
        TemplateLetteraRispostaComponent
    ],
    providers: [TemplateService]
})
export class TemplateModule { }