import { configureStore } from "@reduxjs/toolkit";
import UserReducer from "./reducers/UserReducer";
import CarReducer from "./reducers/CarReducer";

const store=configureStore({
    reducer:{
        user: UserReducer,
        cars: CarReducer
    }
})
export default store;