import { NavLink } from "react-router-dom";

const HeaderComponent = () => {
  return (
    <div>
      <header>
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
          <a className="navbar-brand" href="#">
            Drone Management System
          </a>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item">
                <NavLink className="nav-link" to="/countermeasures">
                  Каталог засобів протидії
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink className="nav-link" to="/effectivecountermeasures">
                  Активні засоби протидії
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink className="nav-link" to="/uavs">
                  Каталог БПЛА
                </NavLink>
              </li>
              {/*<li className="nav-item">*/}
              {/*  <NavLink className="nav-link" to="/effectiveuavs">*/}
              {/*    Активні БПЛА*/}
              {/*  </NavLink>*/}
              {/*</li>*/}
              <li className="nav-item">
                <NavLink className="nav-link" to="/routeparams">
                  Параметри маршруту
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink className="nav-link" to="/modelparams">
                  Параметри моделі
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink className="nav-link" to="/draw">
                  Візуалізація
                </NavLink>
              </li>
            </ul>
          </div>
        </nav>
      </header>
    </div>
  );
};

export default HeaderComponent;
