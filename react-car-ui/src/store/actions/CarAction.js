import axios from "axios";

export const fetchAllCars = (dispatch) => (url = "http://localhost:8080/api/car/all") => {
  axios
    .get(url, {
      headers: {
        Authorization: "Bearer " + localStorage.getItem("token"),
      },
    })
    .then((response) => {
      dispatch({
        type: "FETCH_ALL_CARS",
        payload: response.data,
      });
    })
    .catch((error) => {
      console.error("Error fetching cars:", error);
      // Optionally dispatch an error action here
    });
};
