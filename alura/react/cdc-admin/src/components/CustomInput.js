import React, { Component } from 'react';

export default class CustomInput extends Component {

  render() {
    return (
      <div className="pure-control-group">
        <label htmlFor={this.props.id}>{this.props.label}</label> 
        <input type={this.props.type} id={this.props.id} name={this.props.name} value={this.props.value} onChange={this.props.onChange} />
      </div>
    );
  }
}