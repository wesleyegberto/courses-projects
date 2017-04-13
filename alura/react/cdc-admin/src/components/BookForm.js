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
    this.state = {
      authorId: '',
      title: '',
      price: '',
    };
    // bind context
    this.save = this.save.bind(this);
    this.setAuthorId = this.setAuthorId.bind(this);
    this.setTitle = this.setTitle.bind(this);
    this.setPrice = this.setPrice.bind(this);
  }

  save(event) {
    event.preventDefault();
    const book = {
      autorId: this.state.authorId,
      titulo: this.state.title,
      preco: this.state.price
    };
    console.log(book);
  }

  setAuthorId(event) {
    event.preventDefault();
    this.setState({ authorId: event.target.value });
  }

  setTitle(event) {
    event.preventDefault();
    this.setState({ title: event.target.value });
  }

  setPrice(event) {
    event.preventDefault();
    this.setState({ price: event.target.value });
  }

  render() {
    return (
      <form className="pure-form pure-form-aligned" onSubmit={this.save}>
        <CustomSelect id="autor" name="autor" label="Author" value={this.state.authorId} onChange={this.setAuthorId}
          options={this.props.authors.map(author => ({ value: author.id, label: author.nome }))} />
        <CustomInput type="text" id="titulo" name="titulo" label="Title" value={this.state.title} onChange={this.setTitle} />
        <CustomInput type="text" id="preco" name="preco" label="Price" value={this.state.price} onChange={this.setPrice} />
        <Button value="Save" />
      </form>
    );
  }
}