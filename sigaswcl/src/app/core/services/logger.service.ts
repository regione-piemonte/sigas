import { Injectable } from '@angular/core';
import { ConfigService } from './config.service';

const noop = (): any => undefined;

@Injectable({
  providedIn: 'root'
})
export class LoggerService {

  private isDebugMode: boolean;

  constructor(private configService: ConfigService) {
    this.isDebugMode = this.configService.isDebugMode();
  }

  get info() {
      if (this.isDebugMode) {
          return console.info.bind(console);
      } else {
          return noop;
      }
  }

  get warn() {
      if (this.isDebugMode) {
          return console.warn.bind(console);
      } else {
          return noop;
      }
  }

  get error() {
      if (this.isDebugMode) {
          return console.error.bind(console);
      } else {
          return noop;
      }
  }

  invokeConsoleMethod(type: string, args?: any): void {
      const logFn: Function = (console)[type] || console.log || noop;
      logFn.apply(console, [args]);
  }

}
