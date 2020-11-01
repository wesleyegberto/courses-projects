import React, { useState } from 'react';
import { Link } from 'react-router-dom';

import PageTemplate from '../../components/PageTemplate';

function CadastroCategoria() {
  const valoresIniciais = {
    nome: 'Categoria',
    cor: '#ff0000',
  };

  const [categorias, setCategorias] = useState([]);
  const [novaCategoria, setCategoria] = useState(valoresIniciais);

  const atualizaCampo = (campo, novoValor) => {
    setCategoria({
      ...novaCategoria,
      [campo]: novoValor
    })
  };

  const handleSubmit = evento => {
    evento.preventDefault();
    categorias.push(novaCategoria);
    setCategorias(categorias);
    setCategoria(valoresIniciais);
  };

  return (
    <PageTemplate pageTitle={'Cadastro de Categoria'}>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Nome da categoria:</label>
          <input type="text" name="nome"
            value={novaCategoria.nome}
            onChange={e => {
              atualizaCampo('nome', e.target.value);
            }}
          />
        </div>

        <div>
          <label>Cor:</label>
          <input type="color" name="cor"
            value={novaCategoria.cor}
            onChange={e => novaCategoria.cor = e.target.value}
          />
        </div>

        <button>Cadastrar</button>
      </form>

      <ul>
        {categorias.map((cat, indice) => (
          <li key={indice}>{ cat.nome }</li>
        ))}
      </ul>

      <Link to="/novo-video">Cadastrar Vídeo</Link>
    </PageTemplate>
  );
}

export default CadastroCategoria;
