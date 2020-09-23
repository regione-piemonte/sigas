import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'sigas-alert',
  templateUrl: './sigas-alert.component.html',
  styleUrls: ['./sigas-alert.component.scss']
})
export class SigasAlertComponent implements OnInit {

  @Input('type') type: string;
  @Input('message') message: string;
  @Input('boldMessage') boldMessage: string;
  class: string;
  classIcon: string;
  esito: string;

  constructor() { }

  ngOnInit() {
    if (this.type) {
      if (this.type.toUpperCase() == "SUCCESS") {
        this.esito = "Success";
        this.class = "alert-success";
        this.classIcon = "fas fa-check-circle alert-icon";
      }
      else if (this.type.toUpperCase() == "DANGER") {
        this.esito = "Errore";
        this.class = "alert-danger";
        this.classIcon = "fas fa-times-circle alert-icon";
      }
      else if (this.type.toUpperCase() == "INFO") {
        this.esito = "Info";
        this.class = "alert-info ";
        this.classIcon = "fas fa-info-circle alert-icon";
      }
      else if (this.type.toUpperCase() == "WARNING") {
        this.esito = "Attenzione";
        this.class = "alert-warning"
        this.classIcon = "fas fa-exclamation-triangle alert-icon";
      }
      else
        throw new Error("type sconosciuto");
    }
    else
      throw new Error("type sconosciuto");
  }

}
