import {useState, useEffect} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {toast} from "react-toastify";
import {
    getRouteParamById,
    createRouteParam,
    updateRouteParam,
} from "../services/RouteParamService";

const useRouteParamComponentHook = () => {
    const [dimX, setDimX] = useState("");
    const [dimY, setDimY] = useState("");
    const [layers, setLayers] = useState("");
    let [sourceX, setSourceX] = useState("");
    let [sourceY, setSourceY] = useState("");
    let [sourceZ, setSourceZ] = useState("");
    let [targetX, setTargetX] = useState("");
    let [targetY, setTargetY] = useState("");
    let [targetZ, setTargetZ] = useState("");   
    const [algorithmType, setAlgorithmType] = useState("");
    const [routeParamName, setRouteParamName] = useState("");
    const [title, setTitle] = useState("");
    const {id} = useParams();
    const navigate = useNavigate();

    const getRouteParam = async (id) => {
        const response = await getRouteParamById(id);
        const routeParam = response.data;
        setDimX(routeParam.dimX);
        setDimY(routeParam.dimY);
        setLayers(routeParam.layers);
        setSourceX(routeParam.sourceX);
        setSourceY(routeParam.sourceY);
        setSourceZ(routeParam.sourceZ);
        setTargetX(routeParam.targetX);
        setTargetY(routeParam.targetY);
        setTargetZ(routeParam.targetZ);    
        setAlgorithmType(routeParam.algorithmType);
        setRouteParamName(routeParam.routeParamName);
    };



    useEffect(() => {
        if (id) {
            setTitle("Редагувати параметри маршруту");
            getRouteParam(id);
        } else {
            setTitle("Додати нові параметри");
        }
    }, [id]);

    const saveOrUpdateRouteParam = async (e) => {
        e.preventDefault();
        const routeParam = {
            dimX,
            dimY,
            layers,
            sourceX,
            sourceY,
            sourceZ,
            targetX,
            targetY,
            targetZ,
            algorithmType,
            routeParamName
        };
        if (sourceX === 0) {
            sourceX = '0'
        }
        if (sourceY === 0) {
            sourceY = '0'
        }
        if (sourceZ === 0) {
            sourceZ = '0'
        }
        if (targetX === 0) {
            targetX = '0'
        }
        if (targetY === 0) {
            targetY = '0'
        }
        if (targetZ === 0) {
            targetZ = '0'
        }

        if (dimX && dimY && layers && sourceX && sourceY && sourceZ &&
            targetX && targetY && targetZ && algorithmType && routeParamName) {
            if (id) {
                await updateRouteParam(id, routeParam);
                toast.info("Параметри успішно оновлені!");
                navigate("/routeparams");
                return;
            }
            await createRouteParam(routeParam);
            toast.success("Параметри успішно додані!");
            navigate("/routeparams");
        } else {
            toast.error("Заповніть усі поля!");
        }
    };

    return {
        dimX,
        setDimX,
        dimY,
        setDimY,
        layers,
        setLayers,
        sourceX,
        setSourceX,
        sourceY,
        setSourceY,
        sourceZ,
        setSourceZ,
        targetX,
        setTargetX,
        targetY,
        setTargetY,
        targetZ,
        setTargetZ,
        algorithmType,
        setAlgorithmType,
        routeParamName,
        setRouteParamName,
        title,
        saveOrUpdateRouteParam,
    };
};

export default useRouteParamComponentHook;
