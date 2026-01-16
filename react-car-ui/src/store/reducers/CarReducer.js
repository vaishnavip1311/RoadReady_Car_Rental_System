const initialState={
    cars:[]
}

const CarReducer=(state=initialState,action)=>{
    if(action.type==="FETCH_ALL_CARS"){
        return{
            ...state,
            cars:action.payload
        }
    }
    return state
}
export default CarReducer