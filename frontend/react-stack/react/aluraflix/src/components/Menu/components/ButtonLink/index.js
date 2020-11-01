import React from 'react';
import { Link } from 'react-router-dom';
import PropTypes from 'prop-types';

function ButtonLink(props) {
  return (
    <Link to={props.href} className={props.className}>
      {props.children}
    </Link>
  );
}

ButtonLink.defaultProps = {
  href: '/',
  className: '',
};

ButtonLink.propTypes = {
  className: PropTypes.string,
  href: PropTypes.string,
  children: PropTypes.node.isRequired,
};

export default ButtonLink;
