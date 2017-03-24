import { RouterModule, Routes } from '@angular/router';

import { CadastroComponent } from './cadastro/cadastro.component';
import { ListagemComponent } from './listagem/listagem.component';

// criamos as rotas
const rotas: Routes = [
  { path: '', component: ListagemComponent },
  { path: 'listagem', component: ListagemComponent },
  { path: 'cadastro', component: CadastroComponent },
  { path: 'edicao/:id', component: CadastroComponent },
  { path: '**', component: ListagemComponent } // qualquer rota inválida
];

// compila as rotas para o Angular
export const ROTAS = RouterModule.forRoot(rotas);
