import axios from "axios";

const REST_API_URL = "http://localhost:8080/api/effectiveCountermeasures";

export const listEffectiveCountermeasures = () => {
  return axios.get(REST_API_URL);
};

export const createEffectiveCountermeasure = (effectiveCountermeasure) => {
  return axios.post(REST_API_URL, effectiveCountermeasure);
};

export const deleteEffectiveCountermeasure = (id) => {
  return axios.delete(`${REST_API_URL}/${id}`);
};

export const getEffectiveCountermeasureById = (id) => {
  return axios.get(`${REST_API_URL}/${id}`);
};

export const updateEffectiveCountermeasure = (id, effectiveCountermeasure) => {
  return axios.put(`${REST_API_URL}/${id}`, effectiveCountermeasure);
};
