import React, { Component } from 'react';
import PubSub from 'pubsub-js';
import $ from 'jquery';

import ErrorHandler from '../ErrorHandler';
import CustomInput from './CustomInput';
import Button from './Button';

export default class AuthorForm extends Component {

  constructor() {
    super();
    // attribute created by super()
    this.cleanState();
    // bind context
    this.save = this.save.bind(this);
    this.setName = this.setName.bind(this);
    this.setEmail = this.setEmail.bind(this);
    this.setPassword = this.setPassword.bind(this);
  }

  cleanState() {
    this.state = {
      name: '',
      email: '',
      password: ''
    };
  }

  componentWillMount() {
    console.log('[AuthorForm] Component is about to mount');
  }

  componentDidMount() {
    console.log('[AuthorForm] Component was mounted');
  }

  save(event) {
    event.preventDefault();
    console.log('Saving');
    const author = {
      nome: this.state.name,
      email: this.state.email,
      senha: this.state.password
    };

    $.ajax({
      url:'http://localhost:8080/api/autores',
      contentType:'application/json',
      dataType:'json',
      type:'post',
      data: JSON.stringify(author),
      beforeSend: function() {
        PubSub.publish('input-error-clear', '');
      },
      success: function(updatedAuthors) {
        console.log("Enviado com sucesso");
        this.cleanState();
        PubSub.publish('authors-list-updated', updatedAuthors);
      }.bind(this),
      error: function(response) {
        new ErrorHandler().handle(response.responseJSON);
      }      
    });
  }

  setName(event) {
    event.preventDefault();
    this.setState({ name: event.target.value });
  }

  setEmail(event) {
    event.preventDefault();
    this.setState({ email: event.target.value });
  }

  setPassword(event) {
    event.preventDefault();
    this.setState({ password: event.target.value });
  }

  render() {
    return (
      <form className="pure-form pure-form-aligned" onSubmit={this.save}>
        <CustomInput type="text" id="nome" name="nome" label="Name" value={this.state.name} onChange={this.setName} />
        <CustomInput type="email" id="email" name="email" label="E-mail" value={this.state.email} onChange={this.setEmail} />
        <CustomInput type="password" id="password" name="senha" label="Password" value={this.state.password} onChange={this.setPassword} />
        <Button value="Save" />
      </form>
    );
  }
}