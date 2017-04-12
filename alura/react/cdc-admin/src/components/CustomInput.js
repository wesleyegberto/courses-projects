import React, { Component } from 'react';
import PubSub from 'pubsub-js';

export default class CustomInput extends Component {
  constructor() {
    super();
    this.state = {
      errorMessage: ''
    };
  }

  componentDidMount() {
    PubSub.subscribe('input-error', (topic, error) => {
      if (error.field === this.props.name) {
        this.setState({ errorMessage: error.defaultMessage });
      }
    });
    PubSub.subscribe('input-error-clear', (topic, message) => {
      this.setState({ errorMessage: '' });
    });
  }

  render() {
    return (
      <div className="pure-control-group">
        <label htmlFor={this.props.id}>{this.props.label}</label> 
        <input type={this.props.type} id={this.props.id} name={this.props.name} value={this.props.value} onChange={this.props.onChange} />
        <span>{this.state.errorMessage}</span>
      </div>
    );
  }
}