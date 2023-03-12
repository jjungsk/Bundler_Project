import { useState, useEffect } from "react";

// react-router components
import { Routes, Route, useLocation, Navigate } from "react-router-dom";

// @mui material components
import { ThemeProvider } from "@mui/material/styles";
import CssBaseline from "@mui/material/CssBaseline";

// Material Dashboard 2 React example components
import Sidenav from "examples/Sidenav";

// Material Dashboard 2 React themes
import theme from "assets/theme";

// Material Dashboard 2 React Dark Mode themes
import themeDark from "assets/theme-dark";

// Material Dashboard 2 React routes
import { routes1, routes2 } from "routes";

// Material Dashboard 2 React contexts
import { useMaterialUIController, setMiniSidenav } from "context";

// Images NavBar 브랜드 로고
import logoImage from "assets/images/bundler/sideLogo.png";

// route
import Home from "pages/home";
import Search from "pages/searchall";
import SearchId from "pages/searchid";
import Make from "pages/make";
import Profile from "pages/profile";
import Start from "pages/start";
import AuthLogin from "pages/login";
import SignUp from "pages/signup";
import A810 from "pages/A810";
import Notfound from "pages/404Notfound";

// 로그인 여부 확인
import CheckToken from "auth/CheckToken";

// font
import "./App.css";

function App() {
  const [controller, dispatch] = useMaterialUIController();
  const {
    miniSidenav,
    direction,
    layout,
    sidenavColor,
    transparentSidenav,
    whiteSidenav,
    darkMode,
  } = controller;

  const [onMouseEnter, setOnMouseEnter] = useState(false);
  // const [rtlCache, setRtlCache] = useState(null);
  const { pathname } = useLocation();

  // Open sidenav when mouse enter on mini sidenav
  const handleOnMouseEnter = () => {
    if (miniSidenav && !onMouseEnter) {
      setMiniSidenav(dispatch, false);
      setOnMouseEnter(true);
    }
  };

  // Close sidenav when mouse leave mini sidenav
  const handleOnMouseLeave = () => {
    if (onMouseEnter) {
      setMiniSidenav(dispatch, true);
      setOnMouseEnter(false);
    }
  };

  // Setting the dir attribute for the body element
  useEffect(() => {
    document.body.setAttribute("dir", direction);
  }, [direction]);

  // Setting page scroll to 0 when changing the route
  useEffect(() => {
    document.documentElement.scrollTop = 0;
    document.scrollingElement.scrollTop = 0;
  }, [pathname]);

  // ------------로그인 여부 확인-----------------------------
  // 사용자가 현재 머물러있는 페이지에 대한 정보를 알려주는 hooks
  const location = useLocation();
  // 로그인 여부 확인해서 isAuth에 저장
  const { isAuth } = CheckToken(location.key);
  // ------------로그인 여부 확인-----------------------------

  return (
    <ThemeProvider theme={darkMode ? themeDark : theme}>
      <CssBaseline />
      {layout === "dashboard" && (
        <Sidenav
          color={sidenavColor}
          brand={(transparentSidenav && !darkMode) || whiteSidenav ? logoImage : logoImage}
          routes={isAuth ? routes1 : routes2}
          onMouseEnter={handleOnMouseEnter}
          onMouseLeave={handleOnMouseLeave}
        />
      )}
      <Routes>
        {/* isAuth가 true일 때 들어갈 수 있는 component false일 땐 login으로 */}
        <Route path="/home" element={isAuth ? <Home /> : <Navigate to="/" />} />
        <Route path="/search" element={isAuth ? <Search /> : <Navigate to="/login" />} />
        <Route path="/searchid" element={isAuth ? <SearchId /> : <Navigate to="/login" />} />
        <Route path="/make" element={isAuth ? <Make /> : <Navigate to="/login" />} />
        <Route path="/profile" element={isAuth ? <Profile /> : <Navigate to="/login" />} />
        <Route path="/profile/:user" element={isAuth ? <Profile /> : <Navigate to="/login" />} />
        <Route path="/A810" element={<A810 />} />
        <Route path="/*" element={<Notfound />} />
        {/* isAuth가 false일 때 들어갈 수 있는 component true일 땐 home으로 */}
        <Route path="/" element={isAuth === false ? <Start /> : <Navigate to="/home" />} />
        <Route path="/login" element={isAuth === false ? <AuthLogin /> : <Navigate to="/home" />} />
        <Route path="/signup" element={isAuth === false ? <SignUp /> : <Navigate to="/home" />} />
        <Route path="/A810" element={<A810 />} />
        <Route path="/*" element={<Notfound />} />
      </Routes>
    </ThemeProvider>
  );
}

export default App;
