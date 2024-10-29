import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss']
})
export class ErrorComponent implements OnInit {

  public subscribers: any = {};
  public message: string;
  public cod: string;

  constructor(
    private route: ActivatedRoute, 
    private router: Router, 
    private location: Location) { }

  ngOnInit() {
    this.subscribers.router = this.route.queryParams.subscribe(queryParams => {
      this.cod = queryParams["err"];
      this.message = queryParams["message"];
      if (!this.cod && !this.message) {
        this.cod = "404";
        this.message = "L'url di richiesta" + this.location.path() + "non è stato trovato sul server."
      }
    });
  }

}
