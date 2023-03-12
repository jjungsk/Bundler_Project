// Import - react
import React, { useState, useEffect } from "react";
import InfiniteScroll from "react-infinite-scroll-component";

// Import - design
import { Button, Box, Typography } from "@mui/material";
import RestartAltIcon from "@mui/icons-material/RestartAlt";

// Import - custom
import HomeCard from "pages/home/components/homeCard";
import HomeBundle from "pages/home/components/homeBundle";

// Import - Api
import axios from "axios";
import { apiGetFeeds } from "apis/api/apiHomePage";
import { useSelector } from "react-redux";

function HomeInfiniteScroll() {
  // ============== INIT 선언 ========================
  const { accessToken } = useSelector((state) => state.authToken);
  axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;

  // ================= DATA ===========================
  // component - local 데이터
  const [value, setValue] = useState({
    start: 0,
    range: 20,
  }); // range 범위 지정
  const [viewPosts, setViewPosts] = useState([]); // 리스트 내 POST
  const [morePosts, setMorePosts] = useState(true); // 추가 POST가 있는지 확인 여부

  // ================ FUNCTION ========================
  // Function 1 - 추가 POST 불러오기
  const getMorePosts = async () => {
    // 추가 POST가 true인 경우만 함수 실행
    if (morePosts) {
      await apiGetFeeds()
        .then(({ sorted }) => {
          const nextPosts = sorted.slice(value.start, value.start + value.range);
          if (nextPosts.length < value.range) {
            setMorePosts(false);
          }
          setValue({
            ...value,
            start: value.start + value.range,
          });
          setViewPosts([...viewPosts, ...nextPosts]);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  };

  // Function - Hooks useEffect
  useEffect(() => {
    const initCall = async () => {
      await apiGetFeeds()
        .then(({ sorted }) => {
          const firstPosts = sorted.slice(value.start, value.start + value.range);
          if (firstPosts.length < value.range) {
            setMorePosts(false);
          }
          setValue({
            ...value,
            start: value.start + value.range,
          });
          setViewPosts([...viewPosts, ...firstPosts]);
        })
        .catch((error) => {
          console.log(error);
        });
    };
    initCall();
  }, []);

  // ================= RETURN =========================
  return (
    <div>
      <InfiniteScroll
        dataLength={viewPosts.length} // set the length of the data.This will unlock the subsequent calls to next
        next={getMorePosts} // 페이지 로딩 중 데이터가 더 있을때 끝에서 수행 할 함수
        hasMore={morePosts} // 페이지 로딩 중 데이터가 더 있다면 true / 없다면 false
        loader={<h4>loading..</h4>}
        endMessage={
          <Box sx={{ textAlign: "center" }}>
            <Button type="button" onClick={() => window.location.replace("/home")}>
              <RestartAltIcon fontSize="large" />
              <Typography variant="h6">REFRESH</Typography>
            </Button>
          </Box>
        }
      >
        {viewPosts.map((post) =>
          post.feedType === "CARD" ? (
            <div key={post.cardId}>
              <HomeCard cardInfo={post} />
            </div>
          ) : (
            <div key={post.bundleId}>
              <HomeBundle bundleInfo={post} />
            </div>
          )
        )}
      </InfiniteScroll>
    </div>
    // </div>
  );
}

export default HomeInfiniteScroll;
