import React, { Component } from 'react';
import PubSub from 'pubsub-js';
import $ from 'jquery';

import AuthorForm from './AuthorForm';
import AuthorTable from './AuthorTable';

export default class AuthorBox extends Component {

  constructor() {
    super();
    // attribute created by super()
    this.state = { authors: [] };
  }

  componentWillMount() {
    console.log('[AuthorBox] Component is about to mount');
  }

  componentDidMount() {
    console.log('[AuthorBox] Component was mounted');
    $.ajax({
      url: 'http://localhost:8080/api/autores',
      dataType: 'json',
      success: authors => {
        console.log('[AuthorBox] Ajax success');
        // if we weren't using arrow function we'd need to do a bind
        this.setState({ authors: authors });
      }
    });

    PubSub.subscribe('authors-list-updated', (topic, updatedList) => {
      this.setState({ authors: updatedList });
    });
  }

  render() {
    return (
      <div>
        <div className="pure-form pure-form-aligned">
          <AuthorForm/>
        </div>
        <div>
          <AuthorTable authors={this.state.authors} />
        </div>
      </div>
    );
  }
}