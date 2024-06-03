import "./App.css";
import HeaderComponent from "./components/HeaderComponent";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ListCountermeasureComponent from "./components/ListCountermeasureComponent";
import CountermeasureComponent from "./components/CountermeasureComponent";
import ListEffectiveCountermeasureComponent from "./components/ListEffectiveCountermeasureComponent";
import EffectiveCountermeasureComponent from "./components/EffectiveCountermeasureComponent";
import DrawComponent from "./components/DrawComponent.jsx";
import ListUavComponent from "./components/ListUavComponent.jsx";
import UavComponent from "./components/UavComponent.jsx";
import EffectiveUavComponent from "./components/EffectiveUavComponent.jsx";
import ListEffectiveUavComponent from "./components/ListEffectiveUavComponent.jsx";
import ModelParamComponent from "./components/ModelParamComponent.jsx";
import ListModelParamComponent from "./components/ListModelParamComponent.jsx";
import ListRouteParamComponent from "./components/ListRouteParamComponent.jsx";
import RouteParamComponent from "./components/RouteParamComponent.jsx";
import DataWindow from "./components/DataWindow.jsx";


function App() {
  return (
    <>
      <BrowserRouter>
        <HeaderComponent />
        <Routes>

          <Route path="/" element={<ListCountermeasureComponent />} /> {/*TODO MAIN WINDOW*/}

          <Route path="/countermeasures" element={<ListCountermeasureComponent />} />
          <Route path="/add-countermeasure" element={<CountermeasureComponent />} />
          <Route path="/edit-countermeasure/:id" element={<CountermeasureComponent />} />

          <Route path="/effectivecountermeasures" element={<ListEffectiveCountermeasureComponent />} />
          <Route path="/add-effective-countermeasure" element={<EffectiveCountermeasureComponent />} />
          <Route path="/edit-effective-countermeasure/:id" element={<EffectiveCountermeasureComponent />} />

          <Route path="/uavs" element={<ListUavComponent />} />
          <Route path="/add-uav" element={<UavComponent />} />
          <Route path="/edit-uav/:id" element={<UavComponent />} />

          <Route path="/effectiveuavs" element={<ListEffectiveUavComponent />} />
          <Route path="/add-effective-uav" element={<EffectiveUavComponent />} />
          <Route path="/edit-effective-uav/:id" element={<EffectiveUavComponent />} />

          <Route path="/routeparams" element={<ListRouteParamComponent />} />
          <Route path="/add-routeparam" element={<RouteParamComponent />} />
          <Route path="/edit-routeparam/:id" element={<RouteParamComponent />} />
          <Route path="/calculate-route/:id" element={<RouteParamComponent />} />

          <Route path="/modelparams" element={<ListModelParamComponent />} />
          <Route path="/add-modelparam" element={<ModelParamComponent />} />
          <Route path="/edit-modelparam/:id" element={<ModelParamComponent />} />

          <Route path="/draw" element={<DrawComponent />} />
          <Route path="/pages/:id" element={<DataWindow />} />
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
