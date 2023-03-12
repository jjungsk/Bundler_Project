import store from "redux/store";

function setInterceptors(instance) {
  // Add a request interceptor
  instance.interceptors.request.use(
    (config) => {
      // instance 전역 설정
      const state = store.getState();
      const authTokenAccessToken = state.authToken.accessToken;
      const newConfig = config;
      newConfig.headers.Authorization = `Bearer ${authTokenAccessToken}`;
      return newConfig;
    },
    (error) => Promise.reject(error)
  );

  // Add a response interceptor
  // instance.interceptors.response.use(
  //   function (response) {
  //     return response;
  //   },
  //   function (error) {
  //     return Promise.reject(error);
  //   }
  // );

  return instance;
}

export default setInterceptors;
