import { React, useState, useEffect } from "react";
import axios from "axios";
// 유저정보 이미지가 없을 때
import defaultimg from "assets/images/bundler/bundler_rabbit_2.png";
// 유저정보 박스를 재사용하기 위해 불러온 component
import { Userbox } from "./userbox";

// 유저 정보 검색
function IdSearch() {
  // 유저 정보 검색을 하기 위해 입력한 검색어 저장
  const [keyword, setKeyword] = useState("");
  // 유저 정보 검색 후 결과 값을 리스트로 저장
  const [resultId, setResultId] = useState([]);
  // 유저 정보가 없을 때 검색 결과 없음을 저장
  const [alert, setAlert] = useState("");

  // 유저정보 검색을 위한 요청
  const searchId = async () => {
    // get 요청으로 검색어를 쿼리에 담아 요청
    await axios({
      url: `${process.env.REACT_APP_PORT_GLOBAL}/api/v1/users/list`,
      method: "get",
      params: { keyword },
      withCredentials: true,
    })
      // 검색 기록이 있다면 검색 결과를 받아 resultId에 리스트로 저장하고 alert 비움
      .then((result) => {
        setResultId(result.data);
        setAlert("");
      })
      // 검색 기록이 없다면 alert에 검색 결과 없다는 정보를 저장하고 resultId 리스트 비워줌
      .catch((error) => {
        setAlert(error.response.data.message);
        setResultId([]);
      });
  };

  // 검색한 유저의 프로필로 이동하기 위한 요청
  const goProfile = (userId) => {
    window.open(`/profile/${userId}`, "_self");
  };

  // 검색창(input 태그)에 입력값이 들어올 때마다 onchange로 실행되는 함수
  const searchHandler = (e) => {
    e.preventDefault();
    // 입력된 값을 keyword에 저장해줌
    setKeyword(e.target.value);
  };

  // keyword 값이 변경이 생길 때마다 실행되는 함수
  useEffect(() => {
    // searchId를 실행시켜 회원검색 진행
    searchId();
  }, [keyword]);

  // 유저 검색창 랜더링
  return (
    <div>
      {/* 가운데 정렬을 위한 상위 박스 */}
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        {/* 검색바 */}
        <input
          placeholder="닉네임을 입력하세요"
          style={{
            backgroundSize: "cover",
            backgroundColor: "rgba(0, 0, 0, 0.2)",
            padding: "16px",
            paddingLeft: "30px",
            borderRadius: "15px",
            border: "0px",
            display: "block",
            margin: "15px",
            width: "70%",
            color: "white",
            fontSize: "20px",
          }}
          onChange={searchHandler}
        />
      </div>
      {/* 검색결과가 없을 때 나오는 알림창 */}
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          paddingTop: "2%",
        }}
      >
        <h4
          style={{
            color: "#FFFFFF",
          }}
        >
          {alert}
        </h4>
      </div>
      {/* 검색 결과를 받아 리스트에 담긴 유저정보를 반복해서 보여줌 */}
      {resultId.map((item) => (
        // 유저 정보 박스를 가운데 정렬하기 위한 상위 박스
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          {/* 유저정보를 담아 보여주기 위한 component / 클릭시 해당 유저 프로필로 이동 */}
          <Userbox onClick={() => goProfile(item.userId)}>
            {/* 유저 이미지가 있을 때 */}
            {item.userProfileImageUrl ? (
              <img
                src={item.userProfileImageUrl}
                alt=""
                style={{
                  paddingTop: "0px",
                  paddingBottom: "0px",
                  backgroundColor: "white",
                  borderRadius: "50px",
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                  width: "80px",
                  height: "80px",
                }}
              />
            ) : (
              // 유저 이미지가 없을 때
              <div
                style={{
                  paddingTop: "15px",
                  paddingBottom: "15px",
                  width: "5rem",
                  backgroundColor: "white",
                  borderRadius: "100px",
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                }}
              >
                {/* 유저 이미지가 없을 때 기본이미지 */}
                <img
                  src={defaultimg}
                  alt=""
                  style={{
                    width: "80%",
                  }}
                />
              </div>
            )}
            {/* 사진 이외의 추가 정보 */}
            <div
              style={{
                paddingTop: "20px",
                paddingLeft: "20px",
              }}
            >
              {/* 유저 닉네임 */}
              <h3 id="searchfont">{item.userNickname}</h3>
              {/* 유저 소개 */}
              <p>{item.userIntroduction}</p>
            </div>
          </Userbox>
        </div>
      ))}
    </div>
  );
}

export default IdSearch;
