import axios from "axios";

const REST_API_URL = "http://localhost:8080/api/effectiveUavs";

export const listEffectiveUavs = () => {
  return axios.get(REST_API_URL);
};

export const createEffectiveUav = (effectiveUav) => {
  return axios.post(REST_API_URL, effectiveUav);
};

export const deleteEffectiveUav = (id) => {
  return axios.delete(`${REST_API_URL}/${id}`);
};

export const getEffectiveUavById = (id) => {
  return axios.get(`${REST_API_URL}/${id}`);
};

export const updateEffectiveUav = (id, effectiveUav) => {
  return axios.put(`${REST_API_URL}/${id}`, effectiveUav);
};
