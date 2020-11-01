import React from 'react';
import { Link } from 'react-router-dom';

import PageTemplate from '../../components/PageTemplate';

function Pagina404() {
  return (
    <PageTemplate pageTitle={'Página Não Encontrada =('}>
      <Link to="/">
        Ir para Home
      </Link>
    </PageTemplate>
  );
}

export default Pagina404;
