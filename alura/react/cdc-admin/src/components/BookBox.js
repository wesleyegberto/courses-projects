import React, { Component } from 'react';
import PubSub from 'pubsub-js';
import $ from 'jquery';

import BookForm from './BookForm';
import BookTable from './BookTable';

export default class BookBox extends Component {

  constructor() {
    super();
    this.state = {
      authors: [],
      books: []
    };
  }

  componentDidMount() {
    $.ajax({
      url: 'http://localhost:8080/api/autores',
      dataType: 'json',
      success: authors => {
        this.setState({ authors: authors });
      }
    });

    $.ajax({
      url: 'http://localhost:8080/api/livros',
      dataType: 'json',
      success: books => {
        this.setState({ books: books });
      }
    });

    PubSub.subscribe('books-list-updated', (topic, updatedList) => {
      this.setState({ books: updatedList });
    });
  }

  render() {
    return (
      <div>
        <div className="pure-form pure-form-aligned">
          <BookForm authors={this.state.authors}/>
        </div>
        <div>
          <BookTable authors={this.state.authors} books={this.state.books} />
        </div>
      </div>
    );
  }
}