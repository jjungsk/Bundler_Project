import { Cookies } from "react-cookie";

// 1. 쿠키 객체를 생성해서 cookies에 저장한다
const cookies = new Cookies();

// 2. Refresh Token을 Cookie에 저장하기 위한 함수
export const setRefreshToken = (refreshToken) => {
  // 오늘 날짜를 today에 저장하고
  const today = new Date();
  // 만료날짜를 오늘날짜에 7일 더해서 저장한다
  const expireDate = today.setDate(today.getDate() + 7);

  // 쿠키 값 설정 set(쿠키 이름, 밸류 : 값을 저장하고 필요한 경우 객체를 문자열화, [옵션])
  return cookies.set("refreshToken", refreshToken, {
    // 엄격하거나 느슨한 적용
    sameSite: "strict",
    // 모든 페이지에서 쿠키에 액세스할 수 있도록 하려면 경로로 사용
    path: "/",
    // 쿠키의 절대 만료 날짜
    expires: new Date(expireDate),
  });
};

// 3. Cookie에 저장된 Refresh Token 값을 갖고 오기 위한 함수
export const getCookieToken = () =>
  // 쿠키 값 가져오기 get(쿠키 이름, [옵션])
  cookies.get("refreshToken");

// 4. Cookie 삭제를 위한 함수. 로그아웃 시 사용
export const removeCookieToken = () =>
  // remove(쿠키 이름, [옵션])
  cookies.remove("refreshToken", {
    // 엄격하거나 느슨한 적용
    sameSite: "strict",
    // 모든 페이지에서 쿠키에 액세스할 수 있도록 하려면 경로로 사용
    path: "/",
  });
