import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'sigas-spinner-form',
  templateUrl: './sigas-spinner-form.component.html',
  styleUrls: ['./sigas-spinner-form.component.scss']
})
export class SigasSpinnerFormComponent implements OnInit {

  @Input() diameter: number;
  @Input() position: string
  size: number;
  left: string;
  right: string;

  constructor() {
    this.size = 150;
    this.left = "auto";
    this.right = "auto";
   }

  ngOnInit() {
    if (this.diameter)
        this.size = this.diameter;

    if (this.position === "right") {
        this.right = "0px";
        this.left = "auto";
    }
    if (this.position === "left") {
        this.right = "auto";
        this.left = "0px";
    }
  }

}
