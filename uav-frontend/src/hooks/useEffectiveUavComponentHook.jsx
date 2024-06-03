import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { getListUavs } from "../services/UavService";
import {
  updateEffectiveUav,
  createEffectiveUav,
  getEffectiveUavById,
} from "../services/EffectiveUavService";

const useEffectiveUavComponentHook = () => {
  const [x, setX] = useState("");
  const [y, setY] = useState("");
  const [status, setStatus] = useState("");
  const [title, setTitle] = useState("");
  const [uavId, setUavId] = useState("");
  const [uavs, setUavs] = useState([]);
  const { id } = useParams();
  const navigate = useNavigate();

  const fetchUav = async () => {
    const response = await getListUavs();
    setUavs(response.data);
  };

  useEffect(() => {
    fetchUav();
  }, []);

  const saveOrUpdateEffectiveUav = async (e) => {
    e.preventDefault();

    const effectiveUav = { x, y, status, uavId };

    if (x && y) {
      try {
        if (id) {
          await updateEffectiveUav(id, effectiveUav);
          toast.info("БПЛА оновлений успішно!");
          navigate("/effectiveuavs");
        } else {
          await createEffectiveUav(effectiveUav);
          toast.success("БПЛА доданий успішно!");
          navigate("/effectiveuavs");
        }
      } catch (error) {
        toast.error("An error occurred. Please try again.");
        console.error("Помилка запису/оновлення БПЛА:", error);
      }
    } else {
      toast.error("Заповніть усі поля!");
    }
  };

  const getEffectiveUavData = async (effectiveUavId) => {
    const response = await getEffectiveUavById(effectiveUavId);
    const effectiveUav = response.data;
    setX(effectiveUav.x);
    setY(effectiveUav.y);
    setStatus(effectiveUav.status);
    setUavId(effectiveUav.uavId);
  };

  useEffect(() => {
    if (id) {
      setTitle("Редагування БПЛА");
      getEffectiveUavData(id);
    } else {
      setTitle("Новий БПЛА");
    }
  }, [id]);

  return {
    x,
    setX,
    y,
    setY,
    status,
    setStatus,
    uavId,
    setUavId,
    uavs,
    saveOrUpdateEffectiveUav,
    title,
  };
};

export default useEffectiveUavComponentHook;
