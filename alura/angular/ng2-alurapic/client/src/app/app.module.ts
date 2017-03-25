import { BrowserModule } from '@angular/platform-browser';
import { NgModule }      from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { FotoModule } from './foto/foto.module';
import { PainelModule } from './painel/painel.module';
import { MensagemModule } from "./mensagem/mensagem.module";
import { BotaoModule } from "./botao/botao.module";
import { ModalModule } from "./modal/modal.module";

import { ROTAS } from './app.routes';
import { AppComponent } from './app.component'; // classe AppComponent precisa do exports
import { ListagemComponent } from './listagem/listagem.component';
import { CadastroComponent } from './cadastro/cadastro.component';

@NgModule({
  imports:      [
    BrowserModule, // contém ngIf, ngFor, etc
    HttpModule, // contém os providers para injetar http
    FormsModule, // contém o ngModel, validação padrão de forms
    ReactiveFormsModule, // validação reativa de form
    FotoModule, // todos os componentes desse módulo poderão usar
    PainelModule,
    MensagemModule,
    BotaoModule,
    ModalModule,
    ROTAS // importamos as rotas SPA
  ],
  declarations: [ AppComponent, CadastroComponent, ListagemComponent ],
  bootstrap: [ AppComponent ]
})
export class AppModule {
  
}