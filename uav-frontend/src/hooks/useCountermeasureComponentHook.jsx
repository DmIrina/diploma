import { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";
import {
  getCountermeasureById,
  createCountermeasure,
  updateCountermeasure,
} from "../services/CountermeasureService";

const useCountermeasureComponentHook = () => {
  const [countermeasureName, setCountermeasureName] = useState("");
  const [cmType, setCmType] = useState("");
  const [hmin, setHmin] = useState("");
  const [hmax, setHmax] = useState("");
  const [distance, setDistance] = useState("");
  const [nchannels, setNchannels] = useState("");
  const [damageProbability, setDamageProbability] = useState("");
  const [title, setTitle] = useState("");
  const { id } = useParams();
  const navigate = useNavigate();

  const getCountermeasure = async (id) => {
    const response = await getCountermeasureById(id);
    const countermeasure = response.data;
    setCountermeasureName(countermeasure.countermeasureName);
    setCmType(countermeasure.cmType);
    setHmin(countermeasure.hmin);
    setHmax(countermeasure.hmax);
    setDistance(countermeasure.distance);
    setNchannels(countermeasure.nchannels);
    setDamageProbability(countermeasure.damageProbability);
  };

  useEffect(() => {
    if (id) {
      setTitle("Редагувати засіб");
      getCountermeasure(id);
    } else {
      setTitle("Новий засіб");
    }
  }, [id]);

  const saveOrUpdateCountermeasure = async (e) => {
    e.preventDefault();
    const countermeasure = { countermeasureName, cmType, hmin, hmax, distance, nchannels, damageProbability};
    if (countermeasureName && cmType && hmin && hmax && distance && nchannels && damageProbability) {
      if (id) {
        await updateCountermeasure(id, countermeasure);
        toast.info("Countermeasure updated successfully!");
        navigate("/countermeasures");
        return;
      }
      await createCountermeasure(countermeasure);
      toast.success("Countermeasure added successfully!");
      navigate("/countermeasures");
    } else {
      toast.error("Заповніть усі поля!");
    }
  };

  return {
    countermeasureName,
    setCountermeasureName,
    cmType,
    setCmType,
    hmin,
    setHmin,
    hmax,
    setHmax,
    distance,
    setDistance,
    nchannels,
    setNchannels,
    damageProbability,
    setDamageProbability,
    title,
    saveOrUpdateCountermeasure,
  };
};

export default useCountermeasureComponentHook;
