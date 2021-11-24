import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { RouterModule } from "@angular/router";
import { SharedModule } from "../shared/shared.module";
import { SharedTemplateIntestazioneComponent } from "./components/shared-template-intestazione/shared-template-intestazione.component";
import { SharedTemplateSoggettiComponent } from "./components/shared-template-soggetti/shared-template-soggetti.component";
@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        RouterModule,
        SharedModule,
    ],
    exports: [
        SharedTemplateIntestazioneComponent,SharedTemplateSoggettiComponent,
    ],
    declarations: [
        SharedTemplateIntestazioneComponent,SharedTemplateSoggettiComponent,
    ],
    providers: []
})
export class SharedTemplateModule { }