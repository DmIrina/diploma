import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
  getListCountermeasures,
  deleteCountermeasure,
} from "../services/CountermeasureService";
import { toast } from "react-toastify";

const useListCountermeasureComponentHook = () => {
  const [countermeasures, setCountermeasures] = useState([]);
  const navigate = useNavigate();

  const getCountermeasures = async () => {
    try {
      const response = await getListCountermeasures();
      setCountermeasures(response.data);
    } catch (err) {
      console.log(err);
    }
  };

  const updateCountermeasure = (id) => {
    navigate(`/edit-countermeasure/${id}`);
  };

  const removeCountermeasure = async (id) => {
    await deleteCountermeasure(id);
    toast.error("Countermeasure deleted successfully!");
    getCountermeasures();
  };

  useEffect(() => {
    getCountermeasures();
  }, []);

  return {
    countermeasures,
    getCountermeasures,
    updateCountermeasure,
    removeCountermeasure,
  };
};

export default useListCountermeasureComponentHook;
