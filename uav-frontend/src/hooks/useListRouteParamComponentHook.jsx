import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import {
    listRouteParams,
    deleteRouteParam,
    calculateRouteLast
} from "../services/RouteParamService";
import { toast } from "react-toastify";

const useListRouteParamComponentHook = () => {
    const [routeparams, setRouteParams] = useState([]);
    const navigate = useNavigate();

    const getRouteParams = async () => {
        try {
            const response = await listRouteParams();
            setRouteParams(response.data);
        } catch (err) {
            console.log(err);
        }
    };

    const updateRouteParam = (id) => {
        navigate(`/edit-routeparam/${id}`);
    };

    const viewRoute = () => {
        navigate(`/draw`);
    };

    const calculateRoute = async (id) => {
        await calculateRouteLast(id);
        toast.success("Маршрут прокладено"); // Виведення повідомлення про успішність
        getRouteParams();
    };

    const removeRouteParam = async (id) => {
        await deleteRouteParam(id);
        toast.error("Параметри моделі видалено!");
        getRouteParams();
    };

    useEffect(() => {
        getRouteParams();
    }, []);

    return {
        routeparams,
        getRouteParams,
        updateRouteParam,
        removeRouteParam,
        calculateRoute,
        viewRoute,
    };
};

export default useListRouteParamComponentHook;
