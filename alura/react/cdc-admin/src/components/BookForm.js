import React, { Component } from 'react';
import PubSub from 'pubsub-js';
import $ from 'jquery';

import ErrorHandler from '../ErrorHandler';
import CustomInput from './CustomInput';
import CustomSelect from './CustomSelect';
import Button from './Button';

export default class BookForm extends Component {

  constructor() {
    super();
    this.cleanState();
    // bind context
    this.save = this.save.bind(this);
    this.cleanState = this.cleanState.bind(this);
  }

  cleanState() {
    this.state = {
      authorId: '',
      title: '',
      price: '',
    };
  }

  save(event) {
    event.preventDefault();
    const book = {
      autorId: this.state.authorId,
      titulo: this.state.title,
      preco: this.state.price
    };
    console.log(book);

    $.ajax({
      url:'http://localhost:8080/api/livros',
      contentType:'application/json',
      dataType:'json',
      type:'post',
      data: JSON.stringify(book),
      beforeSend: function() {
        PubSub.publish('input-error-clear', '');
      },
      success: function(updatedAuthors) {
        console.log("Enviado com sucesso");
        this.cleanState();
        PubSub.publish('books-list-updated', updatedAuthors);
      }.bind(this),
      error: function(response) {
        new ErrorHandler().handle(response.responseJSON);
      }  
    });
  }

  // the first arg will come from bind in the field
  updateField(inputName, event) {
    // resolve the expression in [] and use its result as a field
    this.setState({ [inputName]: event.target.value });
  }

  render() {
    return (
      <form className="pure-form pure-form-aligned" onSubmit={this.save}>
        <CustomSelect id="autor" name="autor" label="Author" value={this.state.authorId} onChange={this.updateField.bind(this, 'authorId')}
          options={this.props.authors.map(author => ({ value: author.id, label: author.nome }))} />
        <CustomInput type="text" id="titulo" name="titulo" label="Title" value={this.state.title} onChange={this.updateField.bind(this, 'title')} />
        <CustomInput type="text" id="preco" name="preco" label="Price" value={this.state.price} onChange={this.updateField.bind(this, 'price')} />
        <Button value="Save" />
      </form>
    );
  }
}