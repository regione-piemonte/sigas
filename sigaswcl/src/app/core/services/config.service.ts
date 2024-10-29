import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  constructor() { }

  /**
   * server del backend nel formato http://server:port
   */
  getBEServer(): string {
    return ENV_PROPERTIES.beServer;
    /*return process.env.ENV_PARAMS.beServer*/;
  }
  /**
   * Url di logout da SSO
   */
//  getSSOLogoutURL(): string {
//      return ENV_PROPERTIES.shibbolethSSOLogoutURL;
//      /*return process.env.ENV_PARAMS.shibbolethSSOLogoutURL;*/
//  }
  
  getSSOLogoutURL(hostname: string): string {
      if (hostname.includes('ruparpiemonte')  ) {
          return ENV_PROPERTIES.shibbolethSSOLogoutURLRupar;
      }
      else{
          return ENV_PROPERTIES.shibbolethSSOLogoutURLSisPiemonte;
      }
      /*return process.env.ENV_PARAMS.shibbolethSSOLogoutURL;*/
  }



  getTimeout(): number {
      return ENV_PROPERTIES.timeout;
  }


  isDebugMode(): boolean {
      return ENV_PROPERTIES.debugMode;
  }
}
