// Import - react
import { useState } from "react";
import { useDispatch } from "react-redux";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { HiLockClosed } from "react-icons/hi";
import { ErrorMessage } from "@hookform/error-message";

import { loginUser } from "apis/api/Users";
import { setRefreshToken } from "redux/store/Cookie";
import { SET_TOKEN } from "redux/store/Auth";

import { Button } from "@mui/material/";
import MDSnackbar from "components/MDSnackbar";

function SignIn() {
  // useDipatch를 dispatch로 선언한다
  const dispatch = useDispatch();

  // (*) Validation
  const [valid, setValid] = useState({
    isValid: false,
    comment: "",
    state: "",
  });

  // useForm 사용을 위한 선언
  const {
    register,
    setValue,
    formState: { errors },
    handleSubmit,
  } = useForm();
  const navigate = useNavigate();

  // submit 이후 동작할 코드
  const onValid = async ({ email, password }) => {
    // input 태그 값 비워주는 코드
    setValue("password", "");
    // loginUser를 이용하여 "https://i8a810.p.ssafy.io/api/v1/login" 요청
    const response = await loginUser({ email, password });
    if (response.status === 200) {
      // 쿠키에 Refresh Token 저장
      setRefreshToken(response.data.refreshToken);
      // store에 Access Token 저장
      dispatch(SET_TOKEN(response.data));

      navigate("/home");
    } else {
      setValid({
        isValid: true,
        comment: "로그인 정보를 다시 확인해 주세요",
        state: "error",
      });
    }
  };

  return (
    // 제출 시 이메일과 패스워드가 loginUser이 요청될 때 전송된다
    <form onSubmit={handleSubmit(onValid)}>
      <div>
        <div>
          <label htmlFor="email">
            <input
              // 아이디 입력창
              {...register("email", { required: "Please Enter Your ID" })}
              className="inputinfo"
              type="text"
              pattern="[a-zA-Z0-9]+[@][a-zA-Z0-9]+[.]+[a-zA-Z]+[.]*[a-zA-Z]"
              placeholder="이메일을 입력해주세요"
              // eslint-disable-next-line
              onFocus={(event) => (event.target.placeholder = "")}
              // eslint-disable-next-line
              onBlur={(event) => (event.target.placeholder = "이메일을 입력해주세요")}
              required="required"
            />
            <ErrorMessage
              name="email"
              errors={errors}
              render={({ message }) => (
                <p className="text-sm font-medium text-rose-500">{message}</p>
              )}
            />
          </label>
        </div>
        <div className="loginMargin">
          <label htmlFor="password">
            <input
              // 패스워드 입력창
              {...register("password", { required: "Please Enter Your Password" })}
              className="inputinfo"
              type="password"
              pattern="^([A-Za-z0-9])(?=.*[!@#$%^&*()]).{8,20}$"
              placeholder="비밀번호를 입력해주세요"
              // eslint-disable-next-line
              onFocus={(event) => (event.target.placeholder = "")}
              // eslint-disable-next-line
              onBlur={(event) => (event.target.placeholder = "비밀번호를 입력해주세요")}
              required="required"
            />
          </label>
          <ErrorMessage
            name="password"
            errors={errors}
            render={({ message }) => <p className="text-sm font-medium text-rose-500">{message}</p>}
          />
        </div>
        <div>
          <Button
            className="learn-more2"
            id="bundlerBtn"
            sx={{
              marginTop: "4%",
              bgcolor: "#81D8CF",
              color: "#000000",
              fontSize: "large",
              fontWeight: "bold",
            }}
            fullWidth
            type="submit"
          >
            <HiLockClosed
              className="h-5 w-5 text-indigo-500 group-hover:text-indigo-400"
              aria-hidden="true"
            />
            &nbsp;로그인
          </Button>
        </div>
      </div>
      {/* 로그인 입력창 위 캐릭터 */}
      <div className="padlock">
        <div className="padlock__hook">
          <div className="padlock__hook-body" />
          <div className="padlock__hook-body" />
        </div>
        <div className="padlock__body">
          <div className="padlock__face">
            <div className="padlock__eye padlock__eye--left" />
            <div className="padlock__eye padlock__eye--right" />
            <div className="padlock__mouth padlock__mouth--one" />
            <div className="padlock__mouth padlock__mouth--two" />
            <div className="padlock__mouth padlock__mouth--three" />
          </div>
        </div>
      </div>
      <MDSnackbar
        color={valid.state}
        icon="notifications"
        title="알람"
        content={valid.comment}
        // dateTime="11 mins ago"
        open={valid.isValid}
        close={() => setValid({ valid: false, comment: "", state: "error" })}
      />
    </form>
  );
}

export default SignIn;
