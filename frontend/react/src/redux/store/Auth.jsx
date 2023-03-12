import { createSlice } from "@reduxjs/toolkit";

// // TOKEN_TIME_OUT에 600,000ms(600초)의 시간을 저장하여 선언
export const TOKEN_TIME_OUT = 600 * 1000;

//  createSlice: action과 reducer를 하나의 파일에서 관리
export const tokenSlice = createSlice({
  // 액션 타입 문자열의 prefix(접두사)로 사용됨
  name: "authToken",
  // 초기 state 값
  initialState: {
    // 로그인 후 유저 pk를 저장하기 위한 선언
    userId: "",
    // 로그인 후 이메일을 저장하기 위한 선언
    userEmail: "",
    // 로그인 후 이름을 저장하기 위한 선언
    nickname: "",
    // 현재 로그인 여부를 간단히 확인하기위한 선언
    authenticated: false,
    // Access Token을 저장하기 위해 선언
    accessToken: null,
    // Access Token의 만료 시간을 저장하기 위해 선언
    expireTime: null,
  },
  // 리듀서 맵. key는 액션 타입 문자열이 되고, 함수는 액션이 dispatch 될 때 실행되는 reducer
  reducers: {
    // Access Token 정보를 저장
    SET_TOKEN: (state, action) => {
      // eslint에 맞게 복사 후 변경
      const newState = { ...state };
      // 로그인에 성공하면 userId 저장
      newState.userId = action.payload.userId;
      // 로그인에 성공하면 email 저장
      newState.userEmail = action.payload.userEmail;
      // 로그인에 성공하면 nickname 저장
      newState.nickname = action.payload.nickname;
      // 로그인에 성공하면 authenticated를 True로 바꿔 로그인 여부를 저장
      newState.authenticated = true;
      // Access Token에 payload로 온 JWT token을 저장
      newState.accessToken = action.payload.accessToken;
      // Access Token의 만료 시간을 현재시간에 TOKEN_TIME_OUT을 더해서 저장
      newState.expireTime = new Date().getTime() + TOKEN_TIME_OUT;

      return newState;
    },
    // 값을 모두 초기화함으로써 Access Token에 대한 정보도 삭제
    DELETE_TOKEN: (state) => {
      // eslint에 맞게 복사 후 변경
      const newState = { ...state };
      // 로그아웃에 성공하면 pk삭제
      newState.userId = "";
      // 로그아웃에 성공하면 email 삭제
      newState.userEmail = "";
      // 로그아웃 성공하면 nickname 삭제
      newState.nickname = "";
      // 로그아웃에 성공하면 authenticated를 False로 바꿔 로그아웃 여부를 저장
      newState.authenticated = false;
      // Access Token을 null로 바꿔 token 삭제
      newState.accessToken = null;
      // Access Token의 만료 시간을 null로 바꿔 token 삭제
      newState.expireTime = null;
      return newState;
    },
  },
});

// // SET_TOKEN, DELETE_TOKEN을 tokenSlice.actions의 변수로 선언하여 export
export const { SET_TOKEN, DELETE_TOKEN } = tokenSlice.actions;
// // tokenSlice의 state 데이터의 수정이 정의된 함수를 export
export default tokenSlice.reducer;
