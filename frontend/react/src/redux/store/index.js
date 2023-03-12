// reduxjs/toolkit
import { configureStore } from "@reduxjs/toolkit";

// Import - combineReducers
import { combineReducers } from "redux";

// Import - 관리할 slice를 tokenReducer로
import tokenReducer from "redux/store/Auth";
import makeCardReducer from "redux/reducers/each/makeCardReducer";
import mainHomeReducer from "redux/reducers/each/mainHomeReducer";

const rootReducer = combineReducers({
  // State - makeCardReducer
  makeReducer: makeCardReducer,
  homeReducer: mainHomeReducer,
  authToken: tokenReducer,
});

// Auth.jsx에 선언된 reducer를 사용하기 위해 configureStore 선언
const store = configureStore({
  // 리듀서 맵. key는 액션 타입 문자열이 되고, 함수는 액션이 dispatch 될 때 실행되는 reducer
  reducer: rootReducer,
});

export default store;
