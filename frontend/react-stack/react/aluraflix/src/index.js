import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Switch, Route } from 'react-router-dom';

import './index.css';

import Home from './pages/Home';
import Pagina404 from './pages/Pagina404';
import CadastroVideo from './pages/CadastroVideo';
import CadastroCategoria from './pages/CadastroCategoria';

ReactDOM.render(
  // <React.StrictMode>
  //   <App />
  // </React.StrictMode>,
  <BrowserRouter>
    <Switch>
      <Route path="/" component={Home} exact />
      <Route path="/nova-categoria" component={CadastroCategoria} exact />
      <Route path="/novo-video" component={CadastroVideo} exact />
      <Route component={Pagina404} />
    </Switch>
  </BrowserRouter>,
  document.getElementById('root')
);

