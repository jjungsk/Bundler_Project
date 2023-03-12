import axios from "axios";

// 로그인 요청
const loginUser = async (info) => {
  const { email, password } = info;
  const response = await axios({
    url: `${process.env.REACT_APP_PORT_GLOBAL}/api/v1/login`,
    method: "POST",
    withCredentials: true,
    data: {
      email,
      password,
    },
  })
    .then((result) => result)
    .catch((err) => err);
  return response;
};

// 로그아웃 요청 (헤더에 accessToken 붙여서 요청)
const logoutUser = async (refreshToken, accessToken) => {
  const response = await axios({
    url: `${process.env.REACT_APP_PORT_GLOBAL}/api/v1/logout`,
    method: "POST",
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
    withCredentials: true,
    data: {
      refreshToken,
    },
  });
  return response;
};

// 토큰 재요청
const requestToken = async (refreshToken) => {
  const response = await axios({
    url: `${process.env.REACT_APP_PORT_GLOBAL}/api/v1/refresh`,
    method: "POST",
    withCredentials: true,
    data: {
      refreshToken,
    },
  });
  return response;
};

export { loginUser, logoutUser, requestToken };
