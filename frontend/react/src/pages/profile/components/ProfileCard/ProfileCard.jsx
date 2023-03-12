import PropTypes from "prop-types";
import Modal from "@mui/material/Modal";
import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { useSelector } from "react-redux";

import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import MDAvatar from "components/MDAvatar";
import MDButton from "components/MDButton";

// Icon
import AlternateEmailIcon from "@mui/icons-material/AlternateEmail";
import PersonAddAlt1Icon from "@mui/icons-material/PersonAddAlt1";
import GroupsIcon from "@mui/icons-material/Groups";
import SettingsIcon from "@mui/icons-material/Settings";
import GitHubIcon from "@mui/icons-material/GitHub";
import StarIcon from "@mui/icons-material/Star";

import ProfileSetBox3 from "../SettingModal/ProfileSetting3";
import FollowingModal from "../Follow/FollowingInfinite3";
import FollowerModal from "../Follow/FollowerInfinite";

function ProfileCard({
  userId,
  profileImage,
  nickname,
  email,
  introduction,
  GithubUrl,
  FollowingCount,
  FollowerCount,
  githubCheck,
}) {
  const { user } = useParams();
  const userId2 = useSelector((state) => state.authToken.userId);
  const pageUser = user !== undefined ? user : userId2;

  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const [open2, setOpen2] = useState(false);
  const FollowerOpen = () => setOpen2(true);
  const FollowerClose = () => setOpen2(false);

  const [open3, setOpen3] = useState(false);
  const ProfileSetOpen = () => setOpen3(true);
  const ProfileSetClose = () => setOpen3(false);

  const [isFollowing, setIsFollowing] = useState(false);
  const [IconHovered, setIconHovered] = useState(false);
  // const [StarIconHovered, setStarIconHovered] = useState(false);
  const handleStarClick = () => {
    setIsFollowing(!isFollowing);
  };

  const [FollowingDataGet, setFollowingData] = useState([]);
  const [FollowerDataGet, setFollowerData] = useState([]);

  const accessToken = useSelector((state) => state.authToken.accessToken);
  // 팔로잉 Axios
  useEffect(() => {
    axios
      // .get(`${process.env.REACT_APP_PORT_GLOBAL}/api/v1/users/${user}/followings`, {
      .get(`${process.env.REACT_APP_PORT_GLOBAL}/api/v1/users/${pageUser}/followings`, {
        headers: { Authorization: `Bearer ${accessToken}` },
      })
      .then((res) => {
        setFollowingData(res.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  // 팔로워 Axios
  useEffect(() => {
    axios
      // .get(`${process.env.REACT_APP_PORT_GLOBAL}/api/v1/users/${user}/followers`, {
      .get(`${process.env.REACT_APP_PORT_GLOBAL}/api/v1/users/${pageUser}/followers`, {
        headers: { Authorization: `Bearer ${accessToken}` },
      })
      .then((res) => {
        setFollowerData(res.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  const FollowingData = FollowingDataGet;
  // console.log(FollowingDataGet);
  const FollowerData = FollowerDataGet;
  // console.log(FollowerData);
  // console.log(email);
  // console.log(typeof email);

  return (
    <MDBox
      sx={{
        width: 500,
        height: 279,
        backgroundColor: "#1C1A25",
        marginTop: "30px",
        marginLeft: "30px",
        border: "1px solid #ffffff",
        borderRadius: "21px",
      }}
      style={{
        justifyContent: "flex-start",
      }}
    >
      <Modal open={open} onClose={handleClose}>
        {/* <FollowingBox nickname="임성준" /> */}
        {/* <FollowingBox2 nickname="임성준" /> */}
        <FollowingModal nickname="임성준" data={FollowingData} />
      </Modal>
      <Modal open={open2} onClose={FollowerClose}>
        {/* <FollowerModal nickname="임성준" followerData={FollowerData} pageUser={userId} /> */}
        <FollowerModal nickname="임성준" data={FollowerData} />
      </Modal>
      <Modal open={open3} onClose={ProfileSetClose}>
        <ProfileSetBox3
          SetImage={profileImage}
          id={userId}
          nickname={nickname}
          introduction={introduction}
        />
      </Modal>
      <MDBox // 이미지와 이름, 이메일, 소속 담을 박스
        sx={{
          width: "450px",
          height: "110px",
          marginTop: "15px",
        }}
        style={{
          justifyContent: "flex-start",
        }}
      >
        <MDAvatar // 프로필 이미지
          alt={nickname}
          src={profileImage}
          sx={{
            width: "100px",
            height: "100px",
            left: "20px",
          }}
          style={{
            float: "left",
          }}
        />
        <MDBox // 이름, 이메일, 소속 정보만 담을 박스 (이미지 x)
          flexDirection="row"
          sx={{
            verticalAlign: "top",
            marginLeft: "40px",
          }}
          style={{
            float: "left",
            position: "realtive",
            justifyContent: "flex-start",
          }}
        >
          <MDTypography
            variant="h4"
            fontweight="medium"
            color="white"
            style={{ textAlign: "left", marginLeft: "5px", marginBottom: "2px" }}
          >
            {nickname}
          </MDTypography>
          <MDBox sx={{ marginTop: "2px" }}>
            <AlternateEmailIcon sx={{ color: "gray" }} />
            {githubCheck === "GITHUB" ? (
              <MDTypography
                variant="h7"
                fontWeight="medium"
                color="white"
                sx={{ marginLeft: "10px", fontSize: "17px", marginBottom: "10px" }}
              >
                {nickname}
              </MDTypography>
            ) : (
              <MDTypography
                variant="h7"
                fontWeight="medium"
                color="white"
                sx={{ marginLeft: "10px", fontSize: "17px", marginBottom: "10px" }}
              >
                {email}
              </MDTypography>
            )}
          </MDBox>
          <MDBox sx={{ alignItems: "left" }}>
            <GitHubIcon sx={{ color: "gray" }} />
            <MDTypography
              variant="h7"
              fontWeight="medium"
              color="white"
              sx={{ marginLeft: "10px", fontSize: "17px" }}
            >
              {GithubUrl}
            </MDTypography>
          </MDBox>
        </MDBox>
        <MDBox // 프로필 설정 아이콘 및 팔로우 아이콘 담을 박스
          style={{
            marginLeft: "50px",
            display: "flex",
            flexDirection: "Column",
            float: "right",
          }}
        >
          <SettingsIcon // 프로필 설정 아이콘
            sx={{
              // color: "gray",
              width: "30px",
              height: "30px",
            }}
            onClick={ProfileSetOpen}
            onMouseEnter={() => setIconHovered(true)}
            onMouseLeave={() => setIconHovered(false)}
            color={IconHovered ? "white" : "gray"}
          />
          <StarIcon
            sx={{
              marginTop: "10px",
              width: "30px",
              height: "30px",
            }}
            // onMouseEnter={() => setStarIconHovered(true)}
            // onMouseLeave={() => setStarIconHovered(false)}
            color={isFollowing ? "white" : "gray"}
            onClick={handleStarClick}
          />
        </MDBox>
      </MDBox>
      <MDBox // 프로필 소개 담을 박스
        sx={{
          backgroundColor: "#808191",
          width: "443px",
          height: "63px",
          borderRadius: "15px",
          marginLeft: "15px ",
          marginTop: "5px",
        }}
      >
        <MDTypography
          sx={{
            color: "white",
            marginLeft: "15px",
            marginTop: "5px",
            padding: "3px",
            fontSize: "15px",
            fontWeight: "medium",
          }}
        >
          {introduction}
        </MDTypography>
      </MDBox>
      <MDBox // 하단 환경설정 및 팔로잉 팔로워 버튼 담을 박스
        sx={{
          height: "60px",
          marginTop: "10px",
        }}
      >
        <MDBox // 팔로잉 버튼과 팔로워 버튼을 담을 박스
          style={{
            flexDirection: "row",
            justifyContent: "center",
          }}
          sx={{
            marginLeft: "15%",
          }}
        >
          <MDButton // 팔로잉 버튼
            onClick={handleOpen}
            sx={{
              backgroundColor: "#282535",
              width: "166px",
              height: "51px",
              borderRadius: "15px",
              border: "1px solid #ffffff",
            }}
            style={{
              float: "left",
              justifyContent: "space-between",
            }}
          >
            <PersonAddAlt1Icon
              sx={{
                color: "gray",
                width: "28px",
                height: "26px",
                marginLeft: "20%",
                marginTop: "5%",
              }}
              style={{
                float: "left",
              }}
            />
            <MDBox // 팔로잉 글자와 숫자
              style={{
                flexDirection: "colummn",
                float: "left",
              }}
              sx={{
                marginLeft: "10%",
              }}
            >
              <MDTypography
                sx={{
                  fontSize: "13px",
                  fontweight: "medium",
                }}
              >
                팔로잉
              </MDTypography>
              <MDTypography
                sx={{
                  fontSize: "20px",
                  fontWeight: "medium",
                }}
              >
                {FollowingCount}
              </MDTypography>
            </MDBox>
          </MDButton>
          <MDButton // 팔로워 버튼
            onClick={FollowerOpen}
            sx={{
              backgroundColor: "#282535",
              width: "166px",
              height: "51px",
              borderRadius: "15px",
              marginLeft: "15px",
              border: "1px solid #ffffff",
            }}
            style={{
              float: "left",
              justifyContent: "space-between",
            }}
          >
            <GroupsIcon
              sx={{
                color: "gray",
                width: "28px",
                height: "26px",
                marginLeft: "20%",
                marginTop: "5%",
              }}
              style={{
                float: "left",
              }}
            />
            <MDBox // 팔로워 글자와 숫자
              style={{
                flexDirection: "colummn",
                float: "left",
              }}
              sx={{
                marginLeft: "10%",
              }}
            >
              <MDTypography
                sx={{
                  fontSize: "13px",
                  fontweight: "medium",
                }}
              >
                팔로워
              </MDTypography>
              <MDTypography
                sx={{
                  fontSize: "20px",
                  fontWeight: "medium",
                }}
              >
                {FollowerCount}
              </MDTypography>
            </MDBox>
          </MDButton>
        </MDBox>
      </MDBox>
    </MDBox>
  );
}

// Default Vlaue
ProfileCard.defaultProps = {
  profileImage: "",
  githubCheck: "",
};

// Typechecking props for the SimpleBlogCard
ProfileCard.propTypes = {
  userId: PropTypes.number.isRequired,
  nickname: PropTypes.string.isRequired,
  email: PropTypes.string.isRequired,
  introduction: PropTypes.string.isRequired,
  GithubUrl: PropTypes.string.isRequired,
  FollowingCount: PropTypes.number.isRequired,
  FollowerCount: PropTypes.number.isRequired,
  profileImage: PropTypes.string,
  githubCheck: PropTypes.string,
};

export default ProfileCard;
