import React from 'react';
import PropTypes from 'prop-types';
import styled from 'styled-components';

import Menu from '../Menu';
import Footer from '../Footer';

const Main = styled.main`
  background-color: var(--black);
  color: var(--white);
  flex: 1;
  padding-top: 2em;
  padding-left: 5%;
  padding-right: 5%;
`;

function PageTemplate({ pageTitle, children }) {
  return (
    <>
      <Menu />
      <Main>
        {(pageTitle && pageTitle.length) && (
          <h1>{ pageTitle }</h1>
        )}
        { children }
      </Main>
      <Footer />
    </>
  );
}

PageTemplate.defaultProps = {
  pageTitle: '/',
};

PageTemplate.propTypes = {
  pageTitle: PropTypes.string,
};

export default PageTemplate;
