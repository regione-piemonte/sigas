import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'sigas-spinner',
  templateUrl: './sigas-spinner.component.html',
  styleUrls: ['./sigas-spinner.component.scss']
})
export class SigasSpinnerComponent implements OnInit {

  @Input() diameter: number;
  @Input() position: string
  size: number;
  left: string;
  right: string;

  ie9: Boolean = false;

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

        this.ie9 = this.detectIE9();
  }

  detectIE9() {
    let ua = window.navigator.userAgent;

    let msie = ua.indexOf('MSIE ');
    if (msie > 0) {
        // IE 10 or older => return version number
        let version: number = parseInt(ua.substring(msie + 5, ua.indexOf('.', msie)), 10);
        if (version == 9)
            return true;
        else return false;
    }

    // other browser
    return false;
}

}
