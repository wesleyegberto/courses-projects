import React from 'react';
import { Link } from 'react-router-dom';

import PageTemplate from '../../components/PageTemplate';

function CadastroVideo() {
  return (
    <PageTemplate pageTitle={'Cadastro de Vídeo'}>
      <Link to="/nova-categoria">
        Cadastrar Categoria
      </Link>
    </PageTemplate>
  );
}

export default CadastroVideo;
