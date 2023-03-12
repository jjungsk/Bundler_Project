import React, { useState, useEffect } from "react";

import MDBox from "components/MDBox";
import MDAvatar from "components/MDAvatar";
import MDTypography from "components/MDTypography";
import Button from "@mui/material/Button";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
// import PropTypes from "prop-types";
import followRabbit from "assets/images/bundler/bundler_rabbit_2.png";

function FollowingModal(props) {
  const [data, setData] = useState([]);
  const [page, setPage] = useState(1);
  const [hasMore, setHasMore] = useState(true);

  // const nickname = nickname;
  // eslint-disable-next-line
  const followprop1 = props;
  const followingdata = followprop1.data;
  // console.log(followingdata);
  const pageUser = followingdata.userId;

  useEffect(() => {
    async function fetchData() {
      // const response = await fetch(`https://api.example.com/data?page=${page}`);
      // const response = await fetch(`http://localhost:8080/api/v1/users/${userId}/followings`);
      const response = await fetch(
        `${process.env.REACT_APP_PORT_GLOBAL}/api/v1/users/${pageUser}/followings`
      );
      const newData = await response.json();

      if (!newData.length) {
        setHasMore(false);
        return;
      }

      setData(data.concat(newData));
      setPage(page + 1);
    }

    fetchData();
  }, [page]);

  const handleScroll = (e) => {
    if (e.target.scrollHeight - e.target.scrollTop === e.target.clientHeight && hasMore) {
      setPage(page + 1);
    }
  };
  // eslint-disable-next-line
  const followingList = followingdata.followingList;
  // console.log(followingList);

  const goProfile = (event, userId) => {
    event.preventDefault();
    window.open(`/profile/${userId}`, "_self");
  };

  // const [page, setPage] = useState(1);

  return (
    <MDBox // 전체 팔로잉 박스
      sx={{
        position: "absolute",
        top: "50%",
        left: "50%",
        transform: "translate(-50%, -50%)",
        height: "800px",
        width: "550px",
        backgroundColor: "#282535",
        border: "1px solid #ffffff",
        borderRadius: "20px",
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
            fontSize: "25px",
            marginLeft: "10%",
            marginTop: "5%",
          }}
        >
          {/* {nickname}님의 팔로잉 */}
          팔로잉 목록
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
      <MDBox
        sx={{
          backgroundColor: "#282535",
          height: "auto",
        }}
      >
        {followingdata.length === 0 ? (
          <MDBox
            sx={{
              display: "flex",
              flexDirection: "row",
              justifyContent: "center",
              height: "150px",
            }}
          >
            <MDBox // 토끼사진과 번들없다는 글자
              sx={{
                display: "flex",
                flexDirection: "column",
                marginTop: "50px",
              }}
            >
              <MDBox // 토끼사진만
                sx={{ display: "flex", justifyContent: "center" }}
              >
                <img
                  src={followRabbit}
                  alt="noFollow"
                  style={{ height: "100px", width: "100px" }}
                />
              </MDBox>
              <MDTypography fontSize="25px" mt={1}>
                팔로잉 목록이 비어있어요!
              </MDTypography>
            </MDBox>
          </MDBox>
        ) : (
          <div onScroll={handleScroll} style={{ height: "80vh", overflow: "scroll" }}>
            {followingList.map((following) => (
              <MDBox
                key={following.id}
                sx={{
                  height: "60px",
                }}
              >
                <MDBox
                  style={{
                    flexDirection: "row",
                    // justifyContent: "space-between"
                  }}
                >
                  <MDAvatar
                    src={following.userProfileImageUrl}
                    sx={{
                      width: "46px",
                      height: "46px",
                      left: "35px",
                    }}
                    style={{
                      float: "left",
                    }}
                  />
                  <MDBox // 팔로우 이름, 자기소개
                    sx={{
                      marginLeft: "20%",
                    }}
                    style={{
                      flexDirection: "colummn",
                      float: "left",
                    }}
                  >
                    <MDTypography
                      sx={{
                        fontSize: "14px",
                      }}
                    >
                      {following.userNickname}
                    </MDTypography>
                    <MDTypography
                      sx={{
                        fontSize: "14px",
                        color: "gray",
                      }}
                    >
                      {following.userIntroduction.slice(0, 16)}
                    </MDTypography>
                  </MDBox>
                  <MDBox // 2단 - 맞팔 여부 체크할 아이콘 박스
                    sx={{
                      marginLeft: "25px",
                      float: "left",
                    }}
                  >
                    {following.followBack === true ? (
                      <MDBox
                        sx={{
                          marginTop: "10px",
                        }}
                      >
                        <CheckCircleIcon
                          sx={{
                            width: "25px",
                            height: "25px",
                            color: "#81D8C3",
                          }}
                        />
                      </MDBox>
                    ) : (
                      <MDBox>
                        <MDTypography sx={{ fontSize: "14px" }}>맞팔 아님</MDTypography>
                      </MDBox>
                    )}
                  </MDBox>
                  <MDBox>
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
                      onClick={(event) => goProfile(event, following.userId)}
                    >
                      이동하기
                    </Button>
                  </MDBox>
                </MDBox>
              </MDBox>
            ))}
            <style>
              {`
          ::-webkit-scrollbar {
            width: 0.2em;
            background-color: #282535;
          }
          
          ::-webkit-scrollbar-thumb {
            background-color: #81D8C3;
          }
          
          ::-webkit-scrollbar-track {
            background-color: #282535;
          }
          ::-webkit-scrollbar-corner {
            background-color: #282535;
          }
        `}
            </style>
            {hasMore && <div>Loading...</div>}
          </div>
        )}
      </MDBox>
    </MDBox>
  );
}
// InfiniteScrollingModal2.propTypes = {
//   // nickname: PropTypes.string.isRequired,
//   // userId: PropTypes.number.isRequired,
//   followingData: PropTypes.arrayOf(PropTypes.object).isRequired,
// };
export default FollowingModal;
