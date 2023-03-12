import { useDispatch, useSelector } from "react-redux";
import { Button } from "@mui/material";
import { getCookieToken, removeCookieToken } from "redux/store/Cookie";
import { DELETE_TOKEN } from "redux/store/Auth";
import { logoutUser } from "apis/api/Users";

function SignOut() {
  // store에 저장된 Access Token 정보를 받아 온다
  const accessToken = useSelector((state) => state.authToken.accessToken);
  // useDipatch를 dispatch로 선언한다
  const dispatch = useDispatch();

  // Cookie에 저장된 Refresh Token 정보를 받아 온다
  const refreshToken = getCookieToken();
  // eslint-disable-next-line
  const logout = async () => {
    //  logoutUser를 이용하여 "https://i8a810.p.ssafy.io/api/v1/logout" 요청
    const data = await logoutUser(refreshToken, accessToken);
    if (data.status === 200) {
      // store에 저장된 Access Token 정보를 삭제
      dispatch(DELETE_TOKEN());
      // Cookie에 저장된 Refresh Token 정보를 삭제
      removeCookieToken();
      // login 페이지로 이동한다
      window.open("/login", "_self");
    }
  };

  return (
    <div>
      <Button
        onClick={() => logout()}
        id="bundlerBtn"
        className="learn-more7"
        sx={{
          bgcolor: "#81D8CF",
          color: "#000000",
          fontSize: "medium",
          fontWeight: "bold",
          width: "150px",
        }}
        type="button"
      >
        로그아웃
      </Button>
    </div>
  );
}

export default SignOut;
