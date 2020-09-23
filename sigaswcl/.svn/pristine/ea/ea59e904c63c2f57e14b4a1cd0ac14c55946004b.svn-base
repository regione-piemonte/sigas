import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes, ExtraOptions } from '@angular/router';

import { HomeComponent } from './home/component/home/home.component';
import { ErrorComponent } from './core/component/error/error.component';

const appRoutes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, data: { disableBreadcrumbs: "" } },
  { path: 'errorrest', component: ErrorComponent }
];

const configRouter: ExtraOptions = {
  enableTracing: false,
  useHash: true
};

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(
      appRoutes,
      configRouter
    ),
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule { }
