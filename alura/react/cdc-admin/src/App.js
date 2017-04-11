import React, { Component } from 'react';
import './css/pure-min.css';
import './css/side-menu.css';

import AuthorBox from './components/AuthorBox';

class App extends Component {
  constructor() {
    super();
    console.log('[App] constructor');
  }

  componentWillMount() {
    console.log('[App] Component is about to mount');
  }

  componentDidMount() {
    console.log('[App] Component was mounted');
  }

  render() {
    return (
      <div id="layout">
        <a href="#menu" id="menuLink" className="menu-link"><span></span></a>
        <div id="menu">
          <div className="pure-menu">
            <a className="pure-menu-heading" href="#">React</a>
            <ul className="pure-menu-list">
              <li className="pure-menu-item"><a href="#" className="pure-menu-link">Authors</a></li>
              <li className="pure-menu-item"><a href="#" className="pure-menu-link">Books</a></li>
            </ul>
          </div>
        </div>
        <div id="main">
          <div className="header">
            <h1>First App in React</h1>
            <h2>Authors</h2>
          </div>
          <div className="content" id="content">
            <AuthorBox />
          </div> {/*end .content*/}
        </div>
      </div>
    );
  }
}

export default App;
