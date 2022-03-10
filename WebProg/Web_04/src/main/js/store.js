import { createStore } from 'redux';

const initialState = {
    token: "",
    x: [0],
    y: "0",
    r: [1],
    imgR: 1,
    entries: []
};

function reducer(state, action) {
    switch (action.type) {
        case "SET_TOKEN":
            return {...state, token: action.payload};
        case "ADD_X":
            if (state.x.indexOf(action.payload) === -1) {
                let newX = state.x.concat([action.payload]);
                newX.sort();
                return {...state, x: newX};
            }
            return state;
        case "REMOVE_X":
            return {...state, x: state.x.filter((val) => {
                return val !== action.payload;
            })};
        case "SWITCH_X":
            if (state.x.indexOf(action.payload) === -1) {
                return {...state, x: state.x.filter((val) => {
                    return val !== action.payload;
                })};
            }
            else {
                let newX = state.x.concat([action.payload]);
                newX.sort();
                return {...state, x: newX};
            }
        case "SET_Y":
            return {...state, y: action.payload};
        case "ADD_R":
            if (state.r.indexOf(action.payload) === -1) {
                let newR = state.r.concat([action.payload]);
                newR.sort();
                return {...state, r: newR};
            }
            return state;
        case "REMOVE_R":
            return {...state, r: state.r.filter((val) => {
                return val !== action.payload;
            })};
        case "SWITCH_R":
            if (state.r.indexOf(action.payload) === -1) {
                return {...state, r: state.r.filter((val) => {
                    return val !== action.payload;
                })};
            }
            else {
                let newR = state.r.concat([action.payload]);
                newR.sort();
                return {...state, x: newR};
            }
        case "SET_IMG_R":
            return {...state, imgR: parseInt(action.payload)};
        case "SET_ENTRIES":
            return {...state, entries: action.payload};
        case "PUSH_ENTRY":
            return {...state, entries: state.entries.concat([action.payload])};
        case "PUSH_ENTRIES":
            return {...state, entries: state.entries.concat(action.payload)};
        default:
            return state;
    }
}

export let store = createStore(reducer, initialState);
