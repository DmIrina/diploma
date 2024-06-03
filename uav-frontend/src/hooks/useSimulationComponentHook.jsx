import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import {
  getRouteParam,
  getSavedRoute,
} from "../services/SimulationService";

// Схоже його можна видалити

const useSimulationComponentHook = () => {
  const [dimx, setDimx] = useState("");
  const [dimy, setDimy] = useState("");
  const [route, setRoute] = useState("");
  const [title, setTitle] = useState("");
  const { id } = useParams();

  useEffect(() => {
    setTitle("Параметри");
    getSimulation();
  }, []);

  const getSimulation = async () => {
    try {
    const response = await getSavedRoute();
    const routeData = response.data;
    const responseParams = await getRouteParam();

    setDimx(responseParams.dimX);
    setDimy(responseParams.dimY);
    setRoute(routeData);
    } catch (err) {
      console.log(err);
    }
  };

  return {
    dimx,
    setDimx,
    dimy,
    setDimy,
    route,
    setRoute,
    title,
   // saveOrUpdateSimulation,
  };
};

export default useSimulationComponentHook;
