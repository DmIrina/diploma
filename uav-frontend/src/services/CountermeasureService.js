import axios from "axios";

const REST_API_URL = "http://localhost:8080/api/countermeasures";

export const getListCountermeasures = () => {
  return axios.get(REST_API_URL);
};

export const createCountermeasure = (countermeasure) => {
  return axios.post(REST_API_URL, countermeasure);
};

export const getCountermeasureById = (id) => {
  return axios.get(REST_API_URL + "/" + id);
};

export const updateCountermeasure = (id, countermeasure) => {
  return axios.put(REST_API_URL + "/" + id, countermeasure);
};

export const deleteCountermeasure = (id) => {
  return axios.delete(REST_API_URL + "/" + id);
};
