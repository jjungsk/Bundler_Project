import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";
// import PropTypes from "prop-types";

// import React from "react";
import axios from "axios";
import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";

import MDBox from "components/MDBox";
// import MDAvatar from "components/MDAvatar";
import MDTypography from "components/MDTypography";

// Material Dashboard 2 React example components
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";

import { ResponsiveCalendar } from "@nivo/calendar";
// import { color } from "@mui/system";
import ProfileCard from "./components/ProfileCard/ProfileCard";
import MySkill from "./components/Skill/SkillBox";

import SelectedTab from "./Form/TabSelect";

// ---------------------------------------------------------------------------------------------------------------------
function Profile() {
  const { user } = useParams();
  const userId2 = useSelector((state) => state.authToken.userId);

  const pageUser = user !== undefined ? user : userId2;
  const [tabvalue, setTabValue] = useState("cardTab");

  const handleChangeTab = (tabevent) => {
    setTabValue(tabevent.target.value);
  };

  const [profileDataGet, setProfileData] = useState([]);
  const [CalendarData, setCalendar] = useState([]);
  const [CalendarDate, setDate] = useState([]);
  // 어세스토큰 5분마다 리셋
  const accessToken = useSelector((state) => state.authToken.accessToken);
  // 프로필 axios를 통해 먼저 렌더링
  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_PORT_GLOBAL}/api/v1/users/${pageUser}/mypage`, {
        headers: { Authorization: `Bearer ${accessToken}` },
      })
      .then((res) => {
        setProfileData(res.data);
        setCalendar(res.data.userCalendar);
        setDate(res.data.userCalendar.dates);
      })
      .catch((error) => {
        console.error(error);
      });
  }, [accessToken]);

  // ---------------- 번들탭 Axios ------------------------------
  const [BundleData, setBundleData] = useState([]);

  // -----------------------------------------------------------------
  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_PORT_GLOBAL}/api/v5/users/${pageUser}/feeds/bundles`, {
        headers: { Authorization: `Bearer ${accessToken}` },
      })
      .then((res3) => {
        setBundleData(res3.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, [accessToken]);

  // -------------------- 카드탭 Axios ----------------------------
  const [CardData, setCardData] = useState([]);
  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_PORT_GLOBAL}/api/v5/users/${pageUser}/feeds/cards`, {
        headers: { Authorization: `Bearer ${accessToken}` },
      })
      .then((res4) => {
        setCardData(res4.data);
        // setStatError("");
      })
      .catch((error) => {
        console.error(error);
        // setStatError("error_stat");
      });
  }, [accessToken]);

  // ------------------- 통계탭 Axios -------------------------------
  // -----stat만 axios 하나로 받고, 프로필이 json 파일 일때의 axios (23.02.09)-----
  const [StatData, setStatisticData] = useState([]);
  // const [StatError, setStatError] = useState();
  useEffect(() => {
    axios
      .get(`${process.env.REACT_APP_PORT_GLOBAL}/api/v1/users/${pageUser}/stats`, {
        headers: { Authorization: `Bearer ${accessToken}` },
      })
      .then((res2) => {
        setStatisticData(res2.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, [accessToken]);

  const profiledata = profileDataGet;

  const thisyear = CalendarData.year;
  const startDate = `${thisyear}-01-01`;
  const endDate = `${thisyear}-12-31`;

  // const cal2 = CalendarData.dates;
  const calendarData = CalendarDate;

  return (
    <DashboardLayout>
      <DashboardNavbar />
      <MDBox mb={2}>
        <MDTypography variant="h2" color="white">
          프로필
        </MDTypography>
      </MDBox>
      <MDBox position="relative" mb={5} sx={{ width: "auto" }}>
        <MDBox // 프로필 카드 + 스트릭 (3개 버튼 상단)
          sx={{ width: "auto" }}
        >
          <Grid container spacing={1}>
            <Grid item xs={12} xl={6}>
              <ProfileCard
                // User={pageUser}
                userId={profiledata.userId}
                profileImage={profiledata.userProfileImage}
                nickname={profiledata.userNickName}
                email={profiledata.userEmail}
                introduction={profiledata.userIntroduction}
                group={profiledata.userGithubUrl}
                FollowingCount={profiledata.userFollowingCount}
                FollowerCount={profiledata.userFollowerCount}
                githubCheck={profiledata.providerType}
                sx={{
                  float: "left",
                }}
              />
            </Grid>
            <Grid item xs={12} xl={6}>
              <MySkill pageUser={pageUser} />
            </Grid>
            <Grid item xs={12} xl={12}>
              <MDBox
                style={{
                  width: "95%",
                  height: "auto",
                  backgroundColor: "#1C1A25",
                  marginTop: "30px",
                  // marginLeft: "30px",
                  marginRight: "30px",
                  border: "1px solid #ffffff",
                  borderRadius: "21px",
                  float: "left",
                  position: "relative",
                }}
              >
                <MDBox style={{ width: "auto", height: "300px", margin: "0 auto" }}>
                  <MDTypography
                    sx={{
                      fontSize: "20px",
                      marginTop: "2%",
                      marginLeft: "2%",
                      display: "flex",
                      justifyContent: "center",
                    }}
                  >
                    {thisyear}년도 카드 작성일
                  </MDTypography>
                  <ResponsiveCalendar
                    data={calendarData}
                    from={startDate}
                    to={endDate}
                    emptyColor="#eeeeee"
                    colors={["#61cdbb", "#97e3d5", "#e8c1a0", "#f47560"]}
                    margin={{ top: 40, right: 40, bottom: 40, left: 40 }}
                    yearSpacing={40}
                    monthBorderColor="#f7c0c0"
                    dayBorderWidth={2}
                    dayBorderColor="#ffffff"
                    theme={{
                      textColor: "#ffffff",
                      // fontSize: "20px",
                      fontSize: "3vh",
                    }}
                    legends={[
                      {
                        anchor: "bottom-right",
                        direction: "row",
                        translateY: 36,
                        itemCount: 4,
                        itemWidth: 42,
                        itemHeight: 36,
                        itemsSpacing: 14,
                        itemDirection: "right-to-left",
                      },
                    ]}
                  />
                </MDBox>
              </MDBox>
            </Grid>
          </Grid>
        </MDBox>
      </MDBox>
      <MDBox py={3} sx={{ marginTop: "30px" }}>
        <Grid container spacing={1}>
          <Grid item xs={12} md={4}>
            <MDBox
              sx={{
                display: "flex",
                justifyContent: "center",
              }}
            >
              <Button
                value="cardTab"
                variant="outlined"
                onClick={handleChangeTab}
                sx={{
                  fontSize: "20px",
                  width: "50%",
                  backgroundColor: "#81D8C3",
                  color: "#000000",
                }}
              >
                내가 작성한 카드
              </Button>
            </MDBox>
          </Grid>
          <Grid item xs={12} md={4}>
            <MDBox
              sx={{
                display: "flex",
                justifyContent: "center",
              }}
            >
              <Button
                value="bundleTab"
                variant="outlined"
                onClick={handleChangeTab}
                sx={{
                  marginLeft: "30px",
                  fontSize: "20px",
                  width: "50%",
                  backgroundColor: "#81D8C3",
                  color: "#000000",
                }}
              >
                번들 리스트
              </Button>
            </MDBox>
          </Grid>
          <Grid item xs={12} md={4}>
            <MDBox
              sx={{
                display: "flex",
                justifyContent: "center",
              }}
            >
              <Button
                value="statTab"
                variant="outlined"
                onClick={handleChangeTab}
                sx={{
                  marginLeft: "30px",
                  fontSize: "20px",
                  width: "50%",
                  backgroundColor: "#81D8C3",
                  color: "#000000",
                }}
              >
                통계
              </Button>
            </MDBox>
          </Grid>
        </Grid>
        <MDBox // 카드 및 번들을 렌더링
          sx={{
            backgroundColor: "#282535",
            marginTop: "5%",
            borderRadius: "40px",
          }}
        >
          <SelectedTab selected={tabvalue} data1={CardData} data2={BundleData} data3={StatData} />
        </MDBox>
      </MDBox>
    </DashboardLayout>
  );
}

export default Profile;
