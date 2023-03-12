// axios 설정
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getCookieToken, removeCookieToken } from "../redux/store/Cookie";
import { requestToken } from "../apis/api/Users";
import { DELETE_TOKEN, SET_TOKEN } from "../redux/store/Auth";

function CheckToken(key) {
  const [isAuth, setIsAuth] = useState("loading");
  const { authenticated, expireTime } = useSelector((state) => state.authToken);
  const refreshToken = getCookieToken();
  const dispatch = useDispatch();

  useEffect(() => {
    const checkAuthToken = async () => {
      if (refreshToken === undefined) {
        dispatch(DELETE_TOKEN());
        // 웹 브라우저의 쿠키 에 토큰이 저장되어 있는지 확인한다. 만일 존재하지 않는다면 Failed 를 반환한다.
        setIsAuth(false);
      } else {
        // 쿠키에 refresh 토큰이 저장되어 있다면 redux 내에도 저장되었는지 확인한다.
        // eslint-disable-next-line
        if (authenticated && new Date().getTime() < expireTime) {
          setIsAuth(true);
        } else {
          const response = await requestToken(refreshToken);
          // 만일 리덕스 내에 저장되어 있지 않거나, 저장되었더라도 토큰이 만료되었다면 새로운 access 토큰을 요청한다.
          if (response.status) {
            dispatch(SET_TOKEN(response.data));
            setIsAuth(true);
          } else {
            // access 토큰에 요청에 대한 응답이 올바르지 않다면 Failed를 반환한다.
            dispatch(DELETE_TOKEN());
            removeCookieToken();
            setIsAuth(false);
          }
        }
      }
    };
    checkAuthToken();
  }, [refreshToken, dispatch, key]);

  return {
    isAuth,
  };
}
export default CheckToken;
