// prop-types is a library for typechecking of props
import PropTypes from "prop-types";
import React, { useState } from "react";

// @mui material components
import Card from "@mui/material/Card";
import Grid from "@mui/material/Grid";
// import Modal from "@mui/material/Modal";
// import { useState } from "react";
// import Divider from "@mui/material/Divider";
// import Icon from "@mui/material/Icon";
import FavoriteIcon from "@mui/icons-material/Favorite";
import MobileScreenShareIcon from "@mui/icons-material/MobileScreenShare";

// Material Dashboard 2 React components
import MDBox from "components/MDBox";
import MDButton from "components/MDButton";
import MDTypography from "components/MDTypography";
// import CardImg from "assets/images/bundler/bundlerRabbit.png";
import ProfileCardModal from "./ThumnailCardModal";

// cardType, cardTitle, cardLike, cardScrap, CategoryName
function CardThumbnailCard({ infoCard }) {
  // 카드 상세페이지 모달 open 여부
  const [isModalOpend, setIsModalOpend] = useState(false);

  const handleClose = () => setIsModalOpend(false); // props 모달 닫기 from 자식 component

  return (
    <Card
      sx={{
        marginBottom: "20px",
        height: "auto",
      }}
    >
      <ProfileCardModal open={isModalOpend} handleClose={handleClose} infoCard={infoCard} />
      <MDBox p={3}>
        <MDBox
          sx={{
            width: "100%",
          }}
          display="flex"
          flexDirection="column"
          alignItems="center"
          mb={3}
        >
          <MDTypography display="inline" variant="h3" textTransform="capitalize" fontWeight="bold">
            {infoCard.firstCategoryName}
          </MDTypography>
          <MDTypography
            sx={{
              color: "#00EBA4",
              fontSize: "30px",
            }}
          >
            {infoCard.secondCategoryName}
          </MDTypography>
        </MDBox>
        <MDTypography variant="body2" component="p" color="white" fontSize="25px" mb={3}>
          {infoCard.feedTitle}
        </MDTypography>
        <MDBox
          sx={{
            marginTop: "30px",
          }}
        >
          <Grid container spacing={2}>
            <Grid item xs={6} md={4}>
              <MDBox // 카드 좋아요(아이콘+좋아요 수)가 담길 박스
                sx={{
                  marginLeft: "10px",
                }}
              >
                <FavoriteIcon
                  sx={{
                    width: "25px",
                    height: "25px",
                  }}
                  color="primary"
                  style={{
                    float: "left",
                  }}
                />
                <MDTypography
                  sx={{
                    marginLeft: "8px",
                    fontSize: "15px",
                  }}
                  style={{
                    float: "left",
                  }}
                >
                  {infoCard.feedLikeCnt}
                </MDTypography>
              </MDBox>
            </Grid>
            <Grid item xs={6} md={4}>
              <MDBox // 카드 스크랩(아이콘 + 스크랩 수)가 담길 박스
                sx={{
                  marginLeft: "10px",
                }}
              >
                <MobileScreenShareIcon
                  sx={{
                    width: "25px",
                    height: "25px",
                  }}
                  color="white"
                  style={{
                    float: "left",
                  }}
                />
                <MDTypography
                  sx={{
                    marginLeft: "8px",
                    fontSize: "15px",
                    float: "left",
                  }}
                >
                  {infoCard.cardScrapCnt}
                </MDTypography>
              </MDBox>
            </Grid>
            <Grid item xs={6} md={4}>
              <MDBox // 카드 썸네일 하단 - 상세보기 버튼
              >
                <MDButton
                  types="button"
                  variant="outlined"
                  size="small"
                  onClick={() => setIsModalOpend(!isModalOpend)}
                >
                  상세 보기
                </MDButton>
              </MDBox>
            </Grid>
          </Grid>
        </MDBox>
      </MDBox>
    </Card>
  );
}

CardThumbnailCard.defaultProps = {
  infoCard: {
    cardCommentary: "",
    cardDescription: "",
    cardId: 0,
    cardScrapCnt: 0,
    cardType: "",
    createdAt: "",
    feedCommentCnt: 0,
    feedContent: "",
    feedLikeCnt: 0,
    feedTitle: "",
    feedType: "",
    firstCategoryId: 1,
    firstCategoryName: "",
    secondCategoryId: 6,
    secondCategoryName: "",
    userId: 9,
    userNickname: "",
    userProfileImage: "",
  },
};

// 썸네일 카드의 프롭타입 설정
CardThumbnailCard.propTypes = {
  infoCard: PropTypes.shape({
    cardCommentary: PropTypes.string,
    cardDescription: PropTypes.string,
    cardId: PropTypes.number,
    cardScrapCnt: PropTypes.number,
    cardType: PropTypes.string,
    createdAt: PropTypes.string,
    feedCommentCnt: PropTypes.number,
    feedContent: PropTypes.string,
    feedLikeCnt: PropTypes.number,
    feedTitle: PropTypes.string,
    feedType: PropTypes.string,
    firstCategoryId: PropTypes.number,
    firstCategoryName: PropTypes.string,
    secondCategoryId: PropTypes.number,
    secondCategoryName: PropTypes.string,
    userId: PropTypes.number,
    userNickname: PropTypes.string,
    userProfileImage: PropTypes.string,
  }),
};

export default CardThumbnailCard;
