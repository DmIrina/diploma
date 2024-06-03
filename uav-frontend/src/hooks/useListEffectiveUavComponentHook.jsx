import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { listEffectiveUavs, deleteEffectiveUav } from "../services/EffectiveUavService";
import { getListUavs } from "../services/UavService";

const useListEffectiveUavComponentHook = () => {
  const [effectiveUavs, setEffectiveUavs] = useState([]);
  const [uavs, setUavs] = useState([]);
  const navigate = useNavigate();

  const fetchEffectiveUavs = async () => {
    try {
      const response = await listEffectiveUavs();
      setEffectiveUavs(response.data);
    } catch (err) {
      console.log(err);
    }
  };

  const fetchUavs = async () => {
    const response = await getListUavs();
    setUavs(response.data);
  };

  useEffect(() => {
    fetchEffectiveUavs();
    fetchUavs();
  }, []);

  const getUavName = (uavId) => {
    const uav = uavs.find((item) => item.id === uavId);
    return uav ? uav.uavName : "Невідомий БПЛА";
  };

  const updateEffectiveUav = (id) => {
    navigate(`/edit-effective-uav/${id}`);
  };

  const deleteEffectiveUavById = async (id) => {
    await deleteEffectiveUav(id);
    toast.error("БПЛА видалено!");
    fetchEffectiveUavs();
  };

  return {
    effectiveUavs,
    uavs,
    fetchEffectiveUavs,
    fetchUavs,
    getUavName,
    updateEffectiveUav,
    deleteEffectiveUavById,
  };
};

export default useListEffectiveUavComponentHook;
