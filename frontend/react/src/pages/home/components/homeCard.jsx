// React
import { useState, useEffect } from "react";

// prop-types is a library for typechecking of props
import PropTypes from "prop-types";

// @mui material components
import { Card, Button } from "@mui/material";

// Material Dashboard 2 React components
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";

// Import Custom
import ModalDetail from "pages/home/components/modalDetail";
import LikeButton from "pages/home/buttons/likeButton";
import ScrapButton from "pages/home/buttons/scrapButton";

// Import - api
import { apiGetCardDetail, apiGetBundle, apiGetLike } from "apis/api/apiHomePage";

// Card Image
import CardImg from "assets/images/bundler/main7.png";
// import CardImg from "assets/images/bundler/bundlerRabbit.png";

// Import - redux store
import { useSelector } from "react-redux";

// const cardInfo
function HomeCard({ cardInfo }) {
  // 해당 유저 정보
  const { userId } = useSelector((state) => state.authToken);

  // 현재 사용자가 해당 카드를 좋아요 했는지 확인
  const [isLiked, setIsLiked] = useState(false);

  // 현재 사용자가 가지고 있는 번들 목록
  const [bundleList, setBundleList] = useState([]);

  // 카드 상세 정보 저장
  const [cardDetailInfo, setCardDetailInfo] = useState({});

  // 카드 모달 on/off
  const [open, setOpen] = useState(false);
  // 카드 모달 닫기
  const handleClose = () => setOpen(false);
  // 카드 모달 열기 - 열때 카드 상세 정보 api 불러오기
  const handleOpen = () => {
    const emp = async () => {
      await apiGetCardDetail(cardInfo.cardId)
        .then(({ data }) => {
          setCardDetailInfo(data);
        })
        .catch((error) => console.log(error));
    };
    emp();

    setOpen(true);
  };

  // 스크랩 클릭 시, 해당 유저가 가지고 있는 번들 리스트 불러오기
  const handleBundleList = () => {
    const initCall = async () => {
      await apiGetBundle(userId, cardInfo.cardId)
        .then(({ data }) => {
          setBundleList(data);
        })
        .catch((error) => {
          console.log(error);
        });
    };

    initCall();
  };

  // 처음 조회 시, 가지고 있는 번들 리스트 불러오기 + 좋아요 확인
  useEffect(() => {
    const getNewComment = async () => {
      await apiGetBundle(userId, cardInfo.cardId)
        .then(({ data }) => {
          setBundleList(data.like);
        })
        .catch((error) => {
          console.log(error);
        });
    };
    getNewComment();

    const getIsLiked = async () => {
      await apiGetLike(cardInfo.cardId, userId)
        .then(({ data }) => {
          setIsLiked(data.like);
        })
        .catch((error) => {
          console.log(error);
        });
    };
    getIsLiked();
  }, []);

  return (
    <Card sx={{ ml: 2, mb: 3, maxWidth: 800, minHeight: 200, maxHeight: 400 }}>
      <ModalDetail
        open={open}
        handleOpen={handleOpen}
        handleClose={handleClose}
        cardInfo={cardDetailInfo}
      />
      <MDBox m={3}>
        <MDBox display="flex" sx={{ flexWrap: "wrap", justifyContent: "space-between" }}>
          <MDBox display="flex" sx={{ alignItems: "center", width: "80%" }}>
            <MDBox
              component="img"
              src={CardImg}
              alt={CardImg}
              borderRadius="lg"
              shadow="md"
              width="70px"
              height="100px"
              zIndex={1}
            />
            <MDBox mx={2} width="70%">
              <MDTypography
                variant="h4"
                textTransform="capitalize"
                fontWeight="bold"
                sx={{ textAlign: "left" }}
              >
                [카드]&nbsp;&nbsp;&nbsp;{cardInfo.firstCategoryName}
              </MDTypography>
              <MDTypography variant="h6" textTransform="capitalize" sx={{ textAlign: "left" }}>
                {cardInfo.secondCategoryName}
              </MDTypography>
              <MDTypography variant="overline" mt={1}>
                출제자 :&nbsp;&nbsp;{cardInfo.userNickname}
              </MDTypography>
            </MDBox>
          </MDBox>
          <MDBox display="flex" m="1" sx={{ alignItems: "center", width: "20%" }}>
            <LikeButton isLiked={isLiked} likeCnt={cardInfo.feedLikeCnt} feedId={cardInfo.cardId} />
            <ScrapButton
              cardScrapCnt={cardInfo.cardScrapCnt}
              feedType={cardInfo.feedType}
              targetId={cardInfo.cardId}
              bundleList={bundleList}
              handleBundleList={handleBundleList}
            />
          </MDBox>
        </MDBox>
        <MDBox mt={2} mb={3}>
          <MDTypography display="inline" variant="h6" textTransform="capitalize" fontWeight="bold">
            {cardInfo.feedTitle}
          </MDTypography>
        </MDBox>
        <MDBox mt={2} mb={3}>
          <pre
            style={{
              maxHeight: "100px",
              overflow: "auto",
              overflowX: "hidden",
            }}
          >
            <MDTypography display="inline" variant="body2" component="p" color="text">
              {cardInfo.feedContent}
            </MDTypography>
          </pre>
        </MDBox>
        <Button onClick={handleOpen}>카드 상세보기</Button>
      </MDBox>
    </Card>
  );
}

// Default Vlaue
HomeCard.defaultProps = {
  cardInfo: {
    secondCategoryId: -1,
    secondCategoryName: "",
    cardDescription: "",
    cardCommentary: "",
    createdAt: "",
    userProfileImage: "",
    commentResponseDtoList: [],
  },
};

// Typechecking props for the SimpleBlogCard
HomeCard.propTypes = {
  cardInfo: PropTypes.shape({
    cardId: PropTypes.number.isRequired,
    feedType: PropTypes.string.isRequired,
    cardType: PropTypes.string.isRequired,
    userId: PropTypes.number.isRequired,
    userNickname: PropTypes.string.isRequired,
    firstCategoryId: PropTypes.number.isRequired,
    firstCategoryName: PropTypes.string.isRequired,
    feedTitle: PropTypes.string.isRequired,
    feedContent: PropTypes.string.isRequired,
    cardScrapCnt: PropTypes.number.isRequired,
    feedLikeCnt: PropTypes.number.isRequired,
    feedCommentCnt: PropTypes.number.isRequired,
    secondCategoryId: PropTypes.number,
    secondCategoryName: PropTypes.string,
    cardDescription: PropTypes.string,
    cardCommentary: PropTypes.string,
    createdAt: PropTypes.string,
    userProfileImage: PropTypes.string,
    commentResponseDtoList: PropTypes.arrayOf(PropTypes.object),
  }),
};

export default HomeCard;
