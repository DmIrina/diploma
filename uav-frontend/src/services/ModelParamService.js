import axios from "axios";

const REST_API_URL = "http://localhost:8080/api/modelparams";

export const listModelParams = () => {
    return axios.get(REST_API_URL);
};

export const createModelParam = (modelParam) => {
    return axios.post(REST_API_URL, modelParam);
};

export const getModelParamById = (id) => {
    return axios.get(REST_API_URL + "/" + id);
};

export const updateModelParam = (id, modelParam) => {
    return axios.put(REST_API_URL + "/" + id, modelParam);
};

export const deleteModelParam = (id) => {
    return axios.delete(REST_API_URL + "/" + id);
};
