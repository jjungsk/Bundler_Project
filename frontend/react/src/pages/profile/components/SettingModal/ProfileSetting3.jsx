import Button from "@mui/material/Button";
import PropTypes from "prop-types";
import { useState } from "react";
import { useSelector } from "react-redux";
import axios from "axios";

import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import MDAvatar from "components/MDAvatar";
import MDInput from "components/MDInput";

import MakeProfileImageUpload from "./makeProfileUploadImage";

// function ProfileSetBox({ userId, profileImage, nickname, email, introduction, group }) {
// function ProfileSetBox3(SetImage, id, nickname, introduction) {
function ProfileSetBox3({ SetImage, id, nickname, introduction }) {
  const profileNowImg = SetImage;
  const [inputValue, setInputValue] = useState(nickname);
  const [inputValue2, setInputValue2] = useState(introduction);
  const [ImageInput, setImageInput] = useState([]);
  // console.log(inputValue);
  // console.log(inputValue2);
  // const [inputValue3, setInputValue3] = useState(bundleTitle);
  // console.log(profileNowImg);
  // ------------- 프로필 이미지 파일 받아오기 ----------
  const parentFunction = (x) => {
    setImageInput(x);
  };

  // -------------- 어세스 토큰 --------------------------------------
  const accessToken = useSelector((state) => state.authToken.accessToken);
  // -------------- 프로필 설정 완료 함수 -----------------------------
  const ProfileFinish = () => {
    axios({
      // url: "http://i8a810.p.ssafy.io:8080/api/v1/bundles/15",
      url: `${process.env.REACT_APP_PORT_GLOBAL}/api/v1/users/${id}`,
      method: "PUT",
      withCredentials: true,
      headers: {
        // "Content-Type": "application/json",
        Authorization: `Bearer ${accessToken}`,
      },
      data: {
        userId: id,
        userNickname: inputValue,
        userIntroduction: inputValue2,
      },
    })
      .then(() => {})
      .catch((error) => {
        console.error(error);
      });
  };
  // ------------------- 프로필 이미지 설정하기 --------------
  const PhotoChange = () => {
    axios({
      // url: "http://i8a810.p.ssafy.io:8080/api/v1/bundles/15",
      url: `${process.env.REACT_APP_PORT_GLOBAL}/api/v1/users/${id}/profilePhoto`,
      method: "POST",
      withCredentials: true,
      headers: {
        // "Content-Type": "application/json",
        Authorization: `Bearer ${accessToken}`,
      },
      data: ImageInput,
    })
      .then(() => {})
      .catch((error) => {
        console.error(error);
      });
  };

  // ------------------- 취소 버튼 ----------------------
  const cancelBtn = (event, userId) => {
    event.preventDefault();
    window.open(`/profile/${userId}`, "_self");
  };
  // --------------------------------------------------------------------------------------------
  return (
    <MDBox // 전체 외부 설정페이지 박스
      sx={{
        height: "650px",
        width: "1050px",
        backgroundColor: "#282535",
        border: "1px solid #ffffff",
        borderRadius: "20px",
        position: "absolute",
        top: "50%",
        left: "50%",
        transform: "translate(-50%, -50%)",
      }}
    >
      <MDBox // 글자 있는 박스
        sx={{
          width: "100%",
        }}
        style={{
          justifyContent: "center",
        }}
      >
        <MDTypography
          sx={{
            fontSize: "30px",
            marginLeft: "5%",
            marginTop: "2%",
          }}
        >
          프로필 설정
        </MDTypography>
      </MDBox>
      <MDBox // 흰 줄
        style={{
          width: "100%",
          textAlign: "center",
          borderBottom: "1px solid #aaa",
          lineHeight: "0.1em",
          margin: "10px 0 20px",
        }}
      />
      <MDBox // 프로필 설정 전체 박스 밑 내용이 담길 박스들
        sx={{
          width: "1000px",
          height: "420px",
        }}
        style={{
          flexDirection: "row",
        }}
      >
        <MDBox // 프로필 사진과 이미지 변경 있는 박스
          sx={{
            marginTop: "20px",
            width: "300px",
            height: "350px",
          }}
          style={{
            flexDirection: "column",
            float: "left",
            justifyContent: "center",
          }}
        >
          <MDAvatar
            src={profileNowImg}
            sx={{
              width: "200px",
              height: "200px",
              left: "60px",
            }}
          />
          <Button
            variant="contained"
            sx={{
              marginLeft: "80px",
              marginRight: "10px",
              marginTop: "25px",
              fontSize: "14px",
              fontWeight: "bolder",
              color: "white",
              backgroundColor: "#FF6F6F",
            }}
            onClick={PhotoChange}
          >
            프로필 이미지 변경
          </Button>
          <MDBox
            sx={{
              marginLeft: "20px",
              marginTop: "20px",
              width: "300px",
              backgroundColor: "#BF9FBA",
              borderRadius: "10px",
            }}
          >
            <MakeProfileImageUpload parentFunction={parentFunction} />
          </MDBox>
        </MDBox>
        <MDBox // 설정 제목 및 인풋 값이 담길 박스
          sx={{
            marginLeft: "80px",
          }}
          style={{
            flexDirection: "column",
            float: "left",
          }}
        >
          <MDBox>
            <MDTypography>닉네임 (영어만 입력 가능)</MDTypography>
            <MDInput
              sx={{
                width: "600px",
              }}
              defaultValue={nickname}
              value={inputValue}
              onChange={(e) => setInputValue(e.target.value)}
              pattern="[a-zA-Z0-9]"
            />
          </MDBox>
          <MDBox
            sx={{
              marginTop: "20px",
            }}
          >
            <MDTypography>자기 소개 (50자 이내)</MDTypography>
            <MDInput
              sx={{
                width: "600px",
              }}
              defaultValue={introduction}
              value={inputValue2}
              onChange={(e) => setInputValue2(e.target.value)}
            />
          </MDBox>
          <MDBox
            sx={{
              marginTop: "20px",
            }}
          >
            <MDTypography>GitHub URL</MDTypography>
            <MDInput
              sx={{
                width: "600px",
              }}
            >
              싸피 8기
            </MDInput>
          </MDBox>
        </MDBox>
      </MDBox>
      <MDBox // 밑에 버튼 두개 담을 박스
        sx={{
          marginRight: "35px",
          marginBottom: "20px",
          marginTop: "20px",
        }}
      >
        <Button // 취소 버튼
          // onClick={ProfileSetClose}
          variant="contained"
          style={{
            float: "right",
          }}
          sx={{
            marginRight: "10px",
            fontSize: "14px",
            fontWeight: "bolder",
            color: "#000000",
            backgroundColor: "#81D8C3",
          }}
          onClick={(event) => cancelBtn(event, id)}
        >
          취소
        </Button>
        <Button
          variant="contained"
          style={{
            float: "right",
          }}
          sx={{
            marginRight: "10px",
            fontSize: "14px",
            fontWeight: "bolder",
            color: "#000000",
            backgroundColor: "#81D8C3",
          }}
          onClick={ProfileFinish}
        >
          설정 완료
        </Button>
      </MDBox>
    </MDBox>
  );
}

ProfileSetBox3.defaultProps = {
  SetImage: "",
};

ProfileSetBox3.propTypes = {
  id: PropTypes.number.isRequired,
  SetImage: PropTypes.string,
  //   // Image: PropTypes.string.isRequired,
  nickname: PropTypes.string.isRequired,
  // email: PropTypes.string.isRequired,
  introduction: PropTypes.string.isRequired,
  //   // group: PropTypes.string.isRequired,
};

export default ProfileSetBox3;
