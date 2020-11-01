// must be import to be in scope
import React from 'react';
import { Link } from 'react-router-dom';

import './menu.css';
import ButtonLink from './components/ButtonLink';

import Logo from '../../assets/img/LogoMain.png';

// Long way: recommended when there is state
// class Menu extends React.Component {
//   render() {
//     return (
//       <div>
//         <nav>
//           AluraFlix!
//         </nav>
//       </div>
//     );
//   }
// }

function Menu() {
  return (
    <nav className="Menu">
      <Link to="/">
        <img className="Logo" src={Logo} alt="AluraFlix" />
      </Link>

      <ButtonLink href="/novo-video" className="ButtonLink">
        Novo Vídeo
      </ButtonLink>
    </nav>
  );
}

export default Menu;
