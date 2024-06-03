import {useState, useEffect} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {toast} from "react-toastify";
import {
    getModelParamById,
    createModelParam,
    updateModelParam,
} from "../services/ModelParamService";
import {getListUavs} from "../services/UavService.js";

const useModelParamComponentHook = () => {
    const [modelParamName, setModelParamName] = useState("");
    const [title, setTitle] = useState("");
    const {id} = useParams();
    const navigate = useNavigate();

    let [nuav, setNuav] = useState("");
    let [nuavingroups, setNuavingroups] = useState("");
    let [timeBetweenGroups, setTimeBetweenGroups] = useState("");
    let [timeRecharge, setTimeRecharge] = useState("");
    const [uavId, setUavId] = useState("");
    const [uavs, setUavs] = useState([]);

    const fetchUavs = async () => {
        const response = await getListUavs();
        setUavs(response.data);
    };

    useEffect(() => {
        fetchUavs();
    }, []);

    useEffect(() => {
        if (id) {
            setTitle("Редагувати параметри моделі");
            getModelParam(id);
        } else {
            setTitle("Додати нові параметри");
        }
    }, [id]);


    const getModelParam = async (id) => {
        const response = await getModelParamById(id);
        const modelParam = response.data;
        setModelParamName(modelParam.modelParamName);
        setNuav(modelParam.nuav);
        setNuavingroups(modelParam.nuavingroups);
        setTimeBetweenGroups(modelParam.timeBetweenGroups);
        setTimeRecharge(modelParam.timeRecharge);
        setUavId(modelParam.uavId);
    };

    const saveOrUpdateModelParam = async (e) => {
        e.preventDefault();
        const modelParam = {
            nuav,
            nuavingroups,
            timeBetweenGroups,
            timeRecharge,
            uavId,
            modelParamName
        };

        if (nuav === 0) {
            nuav = '0'
        }
        if (nuavingroups === 0) {
            nuavingroups = '0'
        }
        if (timeBetweenGroups === 0) {
            timeBetweenGroups = '0'
        }
        if (timeRecharge === 0) {
            timeRecharge = '0'
        }

        if (nuav && nuavingroups && timeBetweenGroups && timeRecharge && modelParamName) {
            if (id) {
                await updateModelParam(id, modelParam);
                toast.info("Параметри моделі успішно оновлені!");
                navigate("/modelparams");
                return;
            }
            await createModelParam(modelParam);
            toast.success("Параметри моделі успішно додані!");
            navigate("/modelparams");
        } else {
            toast.error("Заповніть усі поля!");
        }
    };

    return {
        nuav,
        setNuav,
        nuavingroups,
        setNuavingroups,
        timeBetweenGroups,
        setTimeBetweenGroups,
        timeRecharge,
        setTimeRecharge,
        uavId,
        setUavId,
        uavs,
        modelParamName,
        setModelParamName,
        title,
        saveOrUpdateModelParam,
    };
};

export default useModelParamComponentHook;
