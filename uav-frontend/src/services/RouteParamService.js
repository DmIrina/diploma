import axios from "axios";

const REST_API_URL = "http://localhost:8080/api/route";

export const listRouteParams = () => {
    return axios.get(REST_API_URL);
};

export const createRouteParam = (routeParam) => {
    return axios.post(REST_API_URL, routeParam);
};

export const getRouteParamById = (id) => {
    return axios.get(REST_API_URL + "/" + id);
};

export const updateRouteParam = (id, routeParam) => {
    return axios.put(REST_API_URL + "/" + id, routeParam);
};

export const calculateRouteLast = (id) => {
    return axios.put(REST_API_URL + "/calculate/" + id);
};

export const deleteRouteParam = (id) => {
    return axios.delete(REST_API_URL + "/" + id);
};
