import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { listEffectiveCountermeasures, deleteEffectiveCountermeasure } from "../services/EffectiveCountermeasureService";
import { getListCountermeasures } from "../services/CountermeasureService";

const useListEffectiveCountermeasureComponentHook = () => {
  const [effectiveCountermeasures, setEffectiveCountermeasures] = useState([]);
  const [countermeasures, setCountermeasures] = useState([]);
  const navigate = useNavigate();

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

  useEffect(() => {
    fetchEffectiveCountermeasures();
    fetchCountermeasures();
  }, []);

  const getCountermeasureName = (countermeasureId) => {
    const countermeasure = countermeasures.find((item) => item.id === countermeasureId);
    return countermeasure ? countermeasure.countermeasureName : "Невідомий засіб протидії";
  };

  const updateEffectiveCountermeasure = (id) => {
    navigate(`/edit-effective-countermeasure/${id}`);
  };

  const deleteEffectiveCountermeasureById = async (id) => {
    await deleteEffectiveCountermeasure(id);
    toast.error("Засіб протидії видалено!");
    fetchEffectiveCountermeasures();
  };

  return {
    effectiveCountermeasures,
    countermeasures,
    fetchEffectiveCountermeasures,
    fetchCountermeasures,
    getCountermeasureName,
    updateEffectiveCountermeasure,
    deleteEffectiveCountermeasureById,
  };
};

export default useListEffectiveCountermeasureComponentHook;
