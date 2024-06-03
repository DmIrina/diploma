import {useState, useEffect} from "react";
import {useNavigate, useParams} from "react-router-dom";
import {toast} from "react-toastify";
import {
    getUavById,
    createUav,
    updateUav,
} from "../services/UavService";

const useUavComponentHook = () => {
    const [uavName, setUavName] = useState("");
    const [hmin, setHmin] = useState("");
    const [hmax, setHmax] = useState("");
    const [distance, setDistance] = useState("");
    const [speed, setSpeed] = useState("");
    const [resistance, setResistance] = useState("");
    const [noiseimmunity, setNoiseimmunity] = useState("");
    const [stealth, setStealth] = useState("");
    const [eag, setEag] = useState("");
    const [title, setTitle] = useState("");
    const {id} = useParams();
    const navigate = useNavigate();

    const getUav = async (id) => {
        const response = await getUavById(id);
        const uav = response.data;
        setUavName(uav.uavName);
        setHmin(uav.hmin);
        setHmax(uav.hmax);
        setDistance(uav.distance);
        setSpeed(uav.speed);
        setResistance(uav.resistance);
        setNoiseimmunity(uav.noiseimmunity);
        setStealth(uav.stealth);
        setEag(uav.eag);
    };

    useEffect(() => {
        if (id) {
            setTitle("Редагувати БПЛА");
            getUav(id);
        } else {
            setTitle("Новий БПЛА");
        }
    }, [id]);

    const saveOrUpdateUav = async (e) => {
        e.preventDefault();
        const uav = {uavName, hmin, hmax, distance, speed, resistance, noiseimmunity, stealth, eag};
        if (uavName && hmin && hmax && distance && speed && resistance && noiseimmunity && stealth && eag) {
            if (id) {
                await updateUav(id, uav);
                toast.info("Uav updated successfully!");
                navigate("/uavs");
                return;
            }
            await createUav(uav);
            toast.success("Uav added successfully!");
            navigate("/uavs");
        } else {
            toast.error("Заповніть усі поля!");
        }
    };

    return {
        uavName,
        setUavName,
        hmin,
        setHmin,
        hmax,
        setHmax,
        distance,
        setDistance,
        speed,
        setSpeed,
        resistance,
        setResistance,
        noiseimmunity,
        setNoiseimmunity,
        stealth,
        setStealth,
        eag,
        setEag,
        title,
        saveOrUpdateUav,
    };
};

export default useUavComponentHook;
