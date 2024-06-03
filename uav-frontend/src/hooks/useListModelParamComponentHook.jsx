import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
    listModelParams,
    deleteModelParam,
} from "../services/ModelParamService";
import { toast } from "react-toastify";
import {getListUavs} from "../services/UavService.js";

const useListModelParamComponentHook = () => {
    const [modelparams, setModelParams] = useState([]);
    const [uavs, setUavs] = useState([]);
    const navigate = useNavigate();

    const getModelParams = async () => {
        try {
            const response = await listModelParams();
            setModelParams(response.data);
        } catch (err) {
            console.log(err);
        }
    };

    const fetchUavs = async () => {
        const response = await getListUavs();
        setUavs(response.data);
    };

    useEffect(() => {
        getModelParams();
        fetchUavs();
    }, []);

    const getUavName = (uavId) => {
        const uav = uavs.find((item) => item.id === uavId);
        return uav ? uav.uavName : "Невідомий БПЛА";
    };


    const updateModelParam = (id) => {
        navigate(`/edit-modelparam/${id}`);
    };

    const removeModelParam = async (id) => {
        await deleteModelParam(id);
        toast.error("Параметри моделі видалено!");
        getModelParams();
    };

    useEffect(() => {
        getModelParams();
    }, []);

    return {
        modelparams,
        getModelParams,
        updateModelParam,
        removeModelParam,
        fetchUavs,
        getUavName,
    };
};

export default useListModelParamComponentHook;
