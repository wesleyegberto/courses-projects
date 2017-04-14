import React, { Component } from 'react';

export default class BookTable extends Component {
  render() {
    return (
      <table className="pure-table">
        <thead>
          <tr>
            <th>Author</th>
            <th>Title</th>
            <th>Price</th>
          </tr>
        </thead>
        <tbody>
          {this.props.books.map(book => 
            <tr key={book.id}>
              <td>{book.autor.nome}</td>
              <td>{book.titulo}</td>
              <td>{book.preco}</td>
            </tr>
          )}
        </tbody>
      </table>
    );
  }
}