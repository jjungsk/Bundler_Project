// react
import { useState, useEffect } from "react";

// prop-types is a library for typechecking of props
import PropTypes from "prop-types";

// @mui material components
import { Button, Card } from "@mui/material";

// Material Dashboard 2 React components
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";

// Import custom
import ModalCardList from "pages/home/components/modalCardList";
import LikeButton from "pages/home/buttons/likeButton";
import ScrapButton from "pages/home/buttons/scrapButton";

// Import - api
import { apiGetBundleDetail, apiGetLike } from "apis/api/apiHomePage";

// Card Image
import CardImg from "assets/images/bundler/main1.png";
// import CardImg from "assets/images/bundler/bundlerRabbit.png";

// Import - redux store
import { useSelector } from "react-redux";

function HomeBundle({ bundleInfo }) {
  // 해당 유저 정보
  const { userId } = useSelector((state) => state.authToken);

  // 현재 사용자가 해당 카드를 좋아요 했는지 확인
  const [isLiked, setIsLiked] = useState(false);

  // 번들 모달 on/off
  const [cardListModal, setCardListModal] = useState(false);
  // 번들 모달 off
  const handleCardClose = () => setCardListModal(false);

  // 번들 상세 정보 저장
  const [bundleDetail, setBundleDetail] = useState([]);

  // 번들 상세 조회
  const handleBundleDetail = () => {
    const getInfo = async () => {
      await apiGetBundleDetail(bundleInfo.bundleId)
        .then(({ data }) => {
          setBundleDetail(data);
        })
        .catch((error) => console.log(error));
    };
    getInfo();
  };

  // 번들 상세 조회 모달 클릭
  const handleCardListOpen = () => {
    handleBundleDetail(); // api 통신 - 번들 상세 정보 가져오기
    setCardListModal(true);
  };

  // 처음 조회 시, 해당 번들의 좋아요 확인
  useEffect(() => {
    const getIsLiked = async () => {
      await apiGetLike(bundleInfo.bundleId, userId)
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
      <ModalCardList
        open={cardListModal}
        handleCommetList={handleBundleDetail}
        handleCardClose={handleCardClose}
        bundleId={bundleInfo.bundleId}
        cardList={bundleDetail.cardBundleQueryDtoList}
        commentList={bundleDetail.bundleCommentResponseList}
      />
      <MDBox m={3} sx={{ position: "realative" }}>
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
                [번들]&nbsp;&nbsp;&nbsp;
                {bundleInfo.cardBundleQueryDtoList !== null &&
                  `${bundleInfo.cardBundleQueryDtoList.length}개의 카드 포함`}
              </MDTypography>
              <MDTypography variant="overline" mt={1}>
                출제자 :&nbsp;&nbsp;{bundleInfo.bundleWriterNickname}
              </MDTypography>
            </MDBox>
          </MDBox>
          <MDBox display="flex" m="1" sx={{ alignItems: "center", width: "20%" }}>
            <LikeButton
              isLiked={isLiked}
              likeCnt={bundleInfo.feedLikeCnt}
              feedId={bundleInfo.bundleId}
            />
            <ScrapButton feedType={bundleInfo.feedType} targetId={bundleInfo.bundleId} />
          </MDBox>
        </MDBox>
        <MDBox mt={2} mb={3}>
          <MDTypography display="inline" variant="h6" textTransform="capitalize" fontWeight="bold">
            {bundleInfo.feedTitle}
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
            <MDTypography variant="body2" component="p" color="text">
              {bundleInfo.feedContent}
            </MDTypography>
          </pre>
        </MDBox>
        {bundleInfo.cardBundleQueryDtoList !== null && (
          <Button onClick={handleCardListOpen}>번들 상세보기</Button>
        )}
      </MDBox>
    </Card>
  );
}

// Default Vlaue
HomeBundle.defaultProps = {
  bundleInfo: {
    createdAt: "",
    bundleThumbnail: "",
    bundleThumbnailText: "",
    bundleWriterProfileImage: "",
    bundleCommentResponseList: [],
    cardBundleQueryDtoList: [],
  },
};

// Typechecking props for the SimpleBlogCard
HomeBundle.propTypes = {
  bundleInfo: PropTypes.shape({
    bundleId: PropTypes.number.isRequired,
    feedType: PropTypes.string.isRequired,
    bundleWriterId: PropTypes.number.isRequired,
    bundleWriterNickname: PropTypes.string.isRequired,
    feedTitle: PropTypes.string.isRequired,
    feedContent: PropTypes.string.isRequired,
    feedLikeCnt: PropTypes.number.isRequired,
    feedCommentCnt: PropTypes.number.isRequired,
    bundlePrivate: PropTypes.bool.isRequired,
    bundleDefault: PropTypes.bool.isRequired,
    createdAt: PropTypes.string,
    bundleThumbnail: PropTypes.string,
    bundleThumbnailText: PropTypes.string,
    bundleWriterProfileImage: PropTypes.string,
    bundleCommentResponseList: PropTypes.arrayOf(PropTypes.object),
    cardBundleQueryDtoList: PropTypes.arrayOf(PropTypes.object),
  }),
};

export default HomeBundle;
