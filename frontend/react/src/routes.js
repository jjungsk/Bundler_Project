// Bundler Project Pages import
import Home from "pages/home";
import Search from "pages/searchall";
import SearchId from "pages/searchid";
import Make from "pages/make";
import Profile from "pages/profile";
import AuthLogin from "pages/login";
import SignUp from "pages/signup";

// import store from "redux/store";
// const state = store.getState();
// const userId = state.authToken.userId;
// console.log(userId);

// @mui icons
import Icon from "@mui/material/Icon";

const routes1 = [
  {
    type: "collapse",
    name: "홈",
    key: "home",
    icon: <Icon fontSize="small">home</Icon>,
    route: "/home",
    component: <Home />,
  },
  {
    type: "collapse",
    name: "검색",
    key: "search",
    icon: <Icon fontSize="small">travel_explore_icon</Icon>,
    route: "/search",
    component: <Search />,
  },
  {
    type: "collapse",
    name: "아이디 검색",
    key: "searchid",
    icon: <Icon fontSize="small">person_search</Icon>,
    route: "/searchid",
    component: <SearchId />,
  },
  {
    type: "collapse",
    name: "만들기",
    key: "make",
    icon: <Icon fontSize="small">create</Icon>,
    route: "/make",
    component: <Make />,
  },
  {
    type: "collapse",
    name: "프로필",
    key: "profile",
    icon: <Icon fontSize="small">person</Icon>,
    route: "/profile",
    component: <Profile />,
  },
];
const routes2 = [
  {
    type: "collapse",
    name: "로그인",
    key: "login",
    icon: <Icon fontSize="small">login</Icon>,
    route: "/login",
    component: <AuthLogin />,
  },
  {
    type: "collapse",
    name: "회원가입",
    key: "signup",
    icon: <Icon fontSize="small">assignment</Icon>,
    route: "/signup/",
    component: <SignUp />,
  },
];

export { routes1, routes2 };
