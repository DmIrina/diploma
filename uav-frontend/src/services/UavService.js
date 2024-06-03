import axios from "axios";

const REST_API_URL = "http://localhost:8080/api/uavs";

export const getListUavs = () => {
    return axios.get(REST_API_URL);
};

export const createUav = (uav) => {
    return axios.post(REST_API_URL, uav);
};

export const getUavById = (id) => {
    return axios.get(REST_API_URL + "/" + id);
};

export const updateUav = (id, uav) => {
    return axios.put(REST_API_URL + "/" + id, uav);
};

export const deleteUav = (id) => {
    return axios.delete(REST_API_URL + "/" + id);
};
