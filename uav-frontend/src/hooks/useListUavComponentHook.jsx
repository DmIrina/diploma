import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
  getListUavs,
  deleteUav,
} from "../services/UavService";
import { toast } from "react-toastify";

const useListUavComponentHook = () => {
  const [uavs, setUavs] = useState([]);
  const navigate = useNavigate();

  const getUavs = async () => {
    try {
      const response = await getListUavs();
      setUavs(response.data);
    } catch (err) {
      console.log(err);
    }
  };

  const updateUav = (id) => {
    navigate(`/edit-uav/${id}`);
  };

  const removeUav = async (id) => {
    await deleteUav(id);
    toast.error("БПЛА видалено!");
    getUavs();
  };

  useEffect(() => {
    getUavs();
  }, []);

  return {
    uavs,
    getUavs,
    updateUav,
    removeUav,
  };
};

export default useListUavComponentHook;
