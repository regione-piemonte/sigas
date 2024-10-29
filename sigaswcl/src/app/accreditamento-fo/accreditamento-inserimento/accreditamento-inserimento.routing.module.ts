import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InserimentoDichiaranteComponent } from './components/inserimento-dichiarante/inserimento-dichiarante.component';
import { InserimentoLegaleRappresentante } from './components/inserimento-legale-rappresentante/inserimento-legale-rappresentante.component';
import { InserimentoOperatoreServizi } from './components/inserimento-operatore-servizi/inserimento-operatore-servizi.component';
import { AccreditamentoSuccessComponent } from '../accreditamento-shared/components/accreditamento-success/accreditamento-success.component';
import { ElencoPraticheComponent } from './components/elenco-pratiche/elenco-pratiche.component';
import { VisualizzaPraticaAccreditamentoComponent } from './components/visualizza-pratica-accreditamento/visualizza-pratica-accreditamento.component';

 
const routes: Routes = [
    {
        path: 'accreditamento',
        children: [
            {
                path: 'inserisciAccreditamento', children: [
                    { path: '', redirectTo: 'home', pathMatch: 'full' },                    
                    { path: 'inserisciAccreditamentoDichiarante', component: InserimentoDichiaranteComponent, data: { breadcrumb: "Inserisci Accreditamento Dichiarante" } },
                    { path: 'inserisciAccreditamentoLegaleRappresentante', component: InserimentoLegaleRappresentante, data: { breadcrumb: "Inserisci Accreditamento Legale Rappresentante" } },
                    { path: 'inserisciAccreditamentoOperatoreServizi', component: InserimentoOperatoreServizi, data: { breadcrumb: "Inserisci Accreditamento Operatore Servizi" } },
                ], data: { breadcrumb: "Inserisci Accreditamento" }
            },
            { path: 'successInserimento', component: AccreditamentoSuccessComponent, data: { breadcrumb: "Accreditamento Success" } },
            {
                path: 'ricerca', children: [
                    { path: 'elencoPratiche', component: ElencoPraticheComponent, data: { breadcrumb: "Elenco Pratiche" } },
                ], data: { breadcrumb: "Ricerca" }
            },
//            {
//                path: 'visualizzaAccreditamento', children: [
//                    { path: 'visualizzaPraticaAccreditamento', component: VisualizzaPraticaAccreditamentoComponent, data: { breadcrumb: "Visualizza Pratica Accreditamento" } },
//                ], data: { breadcrumb: "Visualizza Accreditamento" }
//            }
            
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
export class AccreditamentoInserimentoRoutingModule { }