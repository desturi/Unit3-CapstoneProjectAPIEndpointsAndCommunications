import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';
import { provideClientHydration, withEventReplay } from '@angular/platform-browser';

export const appConfig = {
providers: [
provideRouter(routes),
    provideClientHydration(withEventReplay()),
    provideHttpClient()
  ]
};
