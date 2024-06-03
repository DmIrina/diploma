import { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { getListCountermeasures } from "../services/CountermeasureService";
import {
  updateEffectiveCountermeasure,
  createEffectiveCountermeasure,
  getEffectiveCountermeasureById,
} from "../services/EffectiveCountermeasureService";

const useEffectiveCountermeasureComponentHook = () => {
  const [x, setX] = useState("");
  const [y, setY] = useState("");
  const [credibility, setCredibility] = useState("");
  const [title, setTitle] = useState("");
  const [countermeasureId, setCountermeasureId] = useState("");
  const [countermeasures, setCountermeasures] = useState([]);
  const { id } = useParams();
  const navigate = useNavigate();

  const fetchCountermeasure = async () => {
    const response = await getListCountermeasures();
    setCountermeasures(response.data);
  };

  useEffect(() => {
    fetchCountermeasure();
  }, []);

  useEffect(() => {
    if (id) {
      setTitle("Редагування засобу протидії");
      getEffectiveCountermeasureData(id);
    } else {
      setTitle("Новий засіб протидії");
    }
  }, [id]);

  const getEffectiveCountermeasureData = async (effectiveCountermeasureId) => {
    const response = await getEffectiveCountermeasureById(effectiveCountermeasureId);
    const effectiveCountermeasure = response.data;
    setX(effectiveCountermeasure.x);
    setY(effectiveCountermeasure.y);
    setCredibility(effectiveCountermeasure.credibility);
    setCountermeasureId(effectiveCountermeasure.countermeasureId);
  };

  const saveOrUpdateEffectiveCountermeasure = async (e) => {
    e.preventDefault();

    const effectiveCountermeasure = { x, y, credibility, countermeasureId };

    if (x && y && credibility) {
      try {
        if (id) {
          await updateEffectiveCountermeasure(id, effectiveCountermeasure);
          toast.info("Засіб протидії оновлений успішно!");
          navigate("/effectivecountermeasures");
        } else {
          await createEffectiveCountermeasure(effectiveCountermeasure);
          toast.success("Засіб протидії доданий успішно!");
          navigate("/effectivecountermeasures");
        }
      } catch (error) {
        toast.error("An error occurred. Please try again.");
        console.error("Помилка запису/оновлення засобу протидії:", error);
      }
    } else {
      toast.error("Заповніть усі поля!");
    }
  };


  return {
    x,
    setX,
    y,
    setY,
    credibility,
    setCredibility,
    countermeasureId,
    setCountermeasureId,
    countermeasures,
    saveOrUpdateEffectiveCountermeasure,
    title,
  };
};

export default useEffectiveCountermeasureComponentHook;
