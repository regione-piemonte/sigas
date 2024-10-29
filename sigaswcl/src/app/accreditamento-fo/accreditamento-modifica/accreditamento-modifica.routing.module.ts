import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ElencoPraticheAccreditamentoComponent } from './components/elenco-pratiche-accreditamento/elenco-pratiche-accreditamento.component';
import { AccreditamentoSuccessComponent } from '../accreditamento-shared/components/accreditamento-success/accreditamento-success.component';
import { ModificaPraticaAccreditamentoComponent } from './components/modifica-pratica-accreditamento/modifica-pratica-accreditamento.component';



const routes: Routes = [
    {
        path: 'accreditamento',
        children: [
            {
                path: 'modificaAccreditamento', children: [
                    { path: '', redirectTo: 'modificaAccreditamentoDichiarante', pathMatch: 'full' },
                    { path: 'elencoPratiche', component: ElencoPraticheAccreditamentoComponent, data: { breadcrumb: "Elenco Pratiche" } },
                    { path: 'modificaPraticaAccreditamento', component: ModificaPraticaAccreditamentoComponent, data: { breadcrumb: "Modifica Pratica Accreditamento" } },
                ], data: { breadcrumb: "Modifica Accreditamento" }
            },
            { path: 'successModifica', component: AccreditamentoSuccessComponent, data: { breadcrumb: "Accreditamento Success" } }
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [
        RouterModule
    ]
})
export class AccreditamentoModificaRoutingModule { }