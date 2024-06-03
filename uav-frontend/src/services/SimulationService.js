import axios from "axios";

// const REST_API_URL = "http://localhost:8080/api/simulation";
//

const REST_API_URL = "http://localhost:8080/api";

export const getSavedRoute = () => {
  return axios.get(REST_API_URL + "/route/saved");
};

export const getRouteParam = () => {
  return axios.get(REST_API_URL + "/route/lastparam");
};

// export const getRoute = () => {
//
//   return axios.get(REST_API_URL + "/route/astar");
// };
//
//
// export const getRoute = async () => {
//   try {
//     const savedRouteResponse = await getSavedRoute();
//     const { algorithmType } = savedRouteResponse.data;
//
//     let routeUrl = "/simulation/route/";
//     if (algorithmType === "dijkstra") {
//       routeUrl += "dijkstra";
//     } else if (algorithmType === "astar") {
//       routeUrl += "astar";
//     } else {
//       // Handle other algorithm types if necessary
//       throw new Error("Unsupported algorithm type");
//     }
//
//     return axios.get(REST_API_URL + routeUrl);
//   } catch (error) {
//     // Handle errors here
//     console.error("Error fetching route:", error);
//     throw error;
//   }
// };



// export const createSimulation = (countermeasure) => {
//   return axios.post(REST_API_URL, countermeasure);
// };

// export const getSimulationById = (id) => {
//   return axios.get(REST_API_URL + "/" + id);
// };

// export const updateSimulation = (id, countermeasure) => {
//   return axios.put(REST_API_URL + "/" + id, countermeasure);
// };

// export const deleteSimulation = (id) => {
//   return axios.delete(REST_API_URL + "/" + id);
// };
