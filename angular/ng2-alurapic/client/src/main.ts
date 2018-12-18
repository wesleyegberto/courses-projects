// importamos o inicializador para browsers
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
// importamos nosso módulo principal
import { AppModule } from './app/app.module';

// obtem uma instância e inicializa o módulo
const platform = platformBrowserDynamic();
platform.bootstrapModule(AppModule);