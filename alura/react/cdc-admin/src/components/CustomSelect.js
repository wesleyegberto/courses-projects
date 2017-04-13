import React, { Component } from 'react';
import PubSub from 'pubsub-js';

export default class CustomSelect extends Component {
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
        <select id={this.props.id} name={this.props.name} value={this.props.value} onChange={this.props.onChange}>
          <option value="">Select one</option>
          {this.props.options.map(opt => <option key={opt.value} value={opt.value}>{opt.label}</option>)}
        </select>
        <span>{this.state.errorMessage}</span>
      </div>
    );
  }
}