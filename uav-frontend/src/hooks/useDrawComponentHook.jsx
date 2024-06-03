import {useEffect, useState} from "react";
import {listEffectiveCountermeasures} from "../services/EffectiveCountermeasureService";
import {getListCountermeasures} from "../services/CountermeasureService";
import {getSavedRoute} from "../services/SimulationService.js";
import {getRouteParam} from "../services/SimulationService.js";

const useDrawComponentHook = () => {
  const [effectiveCountermeasures, setEffectiveCountermeasures] = useState([]);
  const [countermeasures, setCountermeasures] = useState([]);
  const [x, setX] = useState("");
  const [y, setY] = useState("");
  const [route, setRoute] = useState([]);

  const fetchEffectiveCountermeasures = async () => {
    try {
      const response = await listEffectiveCountermeasures();
      setEffectiveCountermeasures(response.data);
    } catch (err) {
      console.log(err);
    }
  };

  const fetchCountermeasures = async () => {
    const response = await getListCountermeasures();
    setCountermeasures(response.data);
  };

  const fetchRouteParam = async () => {
    const response = await getRouteParam();
    setX(response.data.dimX);
    setY(response.data.dimY);
  };

  const fetchRoute = async () => {
    const response = await getSavedRoute();
    setRoute(response.data);
  };

  useEffect(() => {
    fetchEffectiveCountermeasures();
    fetchCountermeasures();
    fetchRouteParam();
    fetchRoute();
  }, []);

  const getCountermeasureName = (countermeasureId) => {
    const countermeasure = countermeasures.find((item) => item.id === countermeasureId);
    return countermeasure ? countermeasure.countermeasureName : "Невідомий засіб";
  };

  const getCountermeasure = (countermeasureId) => {
    return countermeasures.find((item) => item.id === countermeasureId);
  };

  return {
    effectiveCountermeasures,
    countermeasures,
    x,
    y,
    route,
    fetchEffectiveCountermeasures,
    fetchCountermeasures,
    getCountermeasureName,
    getCountermeasure
  };
};

export default useDrawComponentHook;
