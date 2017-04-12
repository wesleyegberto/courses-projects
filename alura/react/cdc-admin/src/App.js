import React, { Component } from 'react';
import { BrowserRouter, Route, Link } from 'react-router-dom';
import './css/pure-min.css';
import './css/side-menu.css';

import Home from './components/Home';
import AuthorBox from './components/AuthorBox';

export default class App extends Component {
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
      <BrowserRouter>
        <div id="layout">
          <a href="#menu" id="menuLink" className="menu-link"><span></span></a>
          <div id="menu">
            <div className="pure-menu">
              <Link className="pure-menu-heading" to="/">React</Link>
              <ul className="pure-menu-list">
                <li className="pure-menu-item"><Link to="/authors" className="pure-menu-link">Authors</Link></li>
                <li className="pure-menu-item"><Link to="/books" className="pure-menu-link">Books</Link></li>
              </ul>
            </div>
          </div>
          <div id="main">
            <div className="header">
              <h1>First App in React</h1>
            </div>
            <div className="content" id="content">
                <Route exact path="/" component={Home} />
                <Route path="/authors" component={AuthorBox} />
                <Route path="/books" component={Home} />
            </div> {/*end .content*/}
          </div>
        </div>
      </BrowserRouter>
    );
  }
}
