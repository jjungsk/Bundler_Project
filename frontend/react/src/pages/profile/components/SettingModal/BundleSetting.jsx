import PropTypes from "prop-types";
import { useState } from "react";
import axios from "axios";
import { useSelector } from "react-redux";
import Button from "@mui/material/Button";
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import MDInput from "components/MDInput";
import { FormControl, Select, InputLabel, MenuItem } from "@mui/material";
import Grid from "@mui/material/Grid";
import CardMedia from "@mui/material/CardMedia";

function BundleSetBox({
  SelectBundleId,
  bundleTitle,
  bundleImage,
  bundleThumtext,
  bundleThumbnail,
  bundlePrivate,
}) {
  // console.log(SelectBundleId);
  const [inputValue, setInputValue] = useState(bundleTitle);
  const [inputValue2, setInputValue2] = useState(bundleThumtext);
  const [inputValue3, setInputValue3] = useState(bundleThumbnail);
  const [inputValue4, setInputValue4] = useState(bundlePrivate);
  const handleChange = (event) => {
    setInputValue4(event.target.value);
  };
  // console.log(bundlePrivate);
  // console.log(inputValue4);
  const accessToken = useSelector((state) => state.authToken.accessToken);
  const BundleFinish = () => {
    axios({
      // url: "http://i8a810.p.ssafy.io:8080/api/v1/bundles/15",
      url: `${process.env.REACT_APP_PORT_GLOBAL}/api/v1/bundles/${SelectBundleId}`,
      method: "PUT",
      withCredentials: true,
      headers: {
        // "Content-Type": "application/json",
        Authorization: `Bearer ${accessToken}`,
      },
      data: {
        bundleThumbnail: inputValue3,
        bundleThumbnailText: inputValue2,
        feedTitle: inputValue,
        feedContent: "찐정보만수정",
        bundlePrivate: inputValue4,
      },
    })
      .then(() => {})
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <MDBox // 전체 외부 설정페이지 박스
      sx={{
        height: "auto",
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
          번들 상세 설정
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
      <MDBox // 썸네일 번들 설정 전체 박스 밑 내용이 담길 박스들
        sx={{
          width: "1000px",
          height: "auto",
        }}
        style={{
          flexDirection: "row",
        }}
      >
        <Grid container spacing={2}>
          <Grid item xs={12} md={4}>
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
              {/* <MDAvatar 
                src = {imgdnk}
                sx = {{
                  width : "200px",
                  height : "200px",
                  left : "60px",
                }}
                /> */}
              <MDBox
                position="relative"
                width="100.25%"
                shadow="xl"
                borderRadius="xl"
                sx={{ marginLeft: "20px" }}
              >
                <CardMedia
                  src={bundleImage}
                  component="img"
                  title={bundleTitle}
                  sx={{
                    maxWidth: "100%",
                    margin: 0,
                    boxShadow: ({ boxShadows: { md } }) => md,
                    objectFit: "cover",
                    objectPosition: "center",
                    opacity: "50%",
                    width: "400px",
                    height: "220px",
                  }}
                />
                <MDTypography // 번들 썸네일 텍스트
                  position="absolute"
                  sx={{
                    top: "40%",
                    left: "50%",
                    width: "100%",
                    transform: "translate(-50%, -50%)",
                    fontSize: "40px",
                    textAlign: "center",
                  }}
                >
                  {bundleThumtext}
                </MDTypography>
              </MDBox>
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
              >
                번들 이미지 변경
              </Button>
            </MDBox>
          </Grid>
          <Grid item xs={12} md={8}>
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
                <MDTypography>번들 제목</MDTypography>
                <MDInput
                  sx={{
                    width: "600px",
                    marginTop: "10px",
                  }}
                  defaultValue={bundleTitle}
                  value={inputValue}
                  onChange={(e) => setInputValue(e.target.value)}
                >
                  dellojoon
                </MDInput>
              </MDBox>
              <MDBox
                sx={{
                  marginTop: "20px",
                }}
              >
                <MDTypography>번들 썸네일 텍스트 (최대 15자 이내)</MDTypography>
                <MDInput
                  sx={{
                    width: "600px",
                    marginTop: "10px",
                  }}
                  defaultValue={bundleThumtext}
                  value={inputValue2}
                  onChange={(e) => setInputValue2(e.target.value)}
                />
              </MDBox>
              <MDBox
                sx={{
                  marginTop: "20px",
                }}
              >
                <MDTypography>번들 썸네일 URL</MDTypography>
                <MDInput
                  sx={{
                    width: "600px",
                    marginTop: "10px",
                  }}
                  defaultValue={bundleThumbnail}
                  value={inputValue3}
                  onChange={(e) => setInputValue3(e.target.value)}
                />
              </MDBox>
              <MDBox
                sx={{
                  marginTop: "20px",
                }}
              >
                <MDTypography>번들 공개 설정</MDTypography>
                <FormControl fullWidth sx={{ marginTop: "10px" }}>
                  <InputLabel id="demo-simple-select-label">공개 / 비공개</InputLabel>
                  <Select
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    // value={bundlePrivate}
                    label={null}
                    // onChange={BundleChange}
                    style={{
                      width: "200px",
                      height: "50px",
                    }}
                    onChange={handleChange}
                    value={inputValue4}
                  >
                    <MenuItem value={false}>공개</MenuItem>
                    {/* <MenuItem value={true}>비공개</MenuItem> */}
                    <MenuItem>비공개</MenuItem>
                  </Select>
                </FormControl>
              </MDBox>
            </MDBox>
          </Grid>
        </Grid>
      </MDBox>
      <MDBox // 밑에 버튼 두개 담을 박스
        sx={{
          marginRight: "35px",
          marginBottom: "100px",
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
          onClick={BundleFinish}
        >
          설정 완료
        </Button>
      </MDBox>
    </MDBox>
  );
}

BundleSetBox.propTypes = {
  SelectBundleId: PropTypes.number.isRequired,
  bundleTitle: PropTypes.string.isRequired,
  // bundlePrivate: PropTypes.oneOf(["public", "private"]),
  bundlePrivate: PropTypes.bool.isRequired,
  // bundleDefault : PropTypes.oneOf(["non", "default"]),
  bundleImage: PropTypes.string.isRequired,
  bundleThumtext: PropTypes.string.isRequired,
  bundleThumbnail: PropTypes.string.isRequired,
};

export default BundleSetBox;
