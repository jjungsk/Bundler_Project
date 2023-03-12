import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import PropTypes from "prop-types";

function CompareStat({
  feedLikeRankingFollowing,
  cardScrapRankingFollowing,
  cardMakeRankingTotal,
  feedLikeRankingTotal,
  cardScrapRankingTotal,
}) {
  // const tempCompare = {
  //   feedLikeRankingFollowing: 21,
  //   cardScrapRankingFollowing: 11,
  //   feedLikeRankingTotal: 23,
  //   cardScrapRankingTotal: 44,
  //   cardMakeRankingTotal: 123,
  // };

  return (
    <MDBox
      sx={{
        marginTop: "10px",
        marginLeft: "10px",
      }}
    >
      <MDTypography
        sx={{
          fontSize: "27px",
          color: "#00EBA4",
          marginLeft: "10px",
        }}
      >
        수치 비교
      </MDTypography>
      <MDTypography fontSize="20px" marginTop="10px" marginLeft="10px">
        - (팔로잉 내) 피드 좋아요 백분위 : {feedLikeRankingFollowing}
      </MDTypography>
      <MDTypography fontSize="20px" mt="5px" marginLeft="10px">
        - (팔로잉 내) 카드 스크랩 백분위 : {cardScrapRankingFollowing}
      </MDTypography>
      <MDTypography fontSize="20px" mt="5px" marginLeft="10px">
        - (전체 회원 내) 피드 좋아요 백분위 : {feedLikeRankingTotal}
      </MDTypography>
      <MDTypography fontSize="20px" mt="5px" marginLeft="10px">
        - (전체 회원 내) 카드 스크랩 백분위 : {cardScrapRankingTotal}
      </MDTypography>
      <MDTypography fontSize="20px" mt="5px" marginLeft="10px">
        - (전체 회원 내) 카드 생성 수 백분위 : {cardMakeRankingTotal}
      </MDTypography>
    </MDBox>
  );
}
CompareStat.propTypes = {
  feedLikeRankingFollowing: PropTypes.number.isRequired,
  cardScrapRankingFollowing: PropTypes.number.isRequired,
  cardMakeRankingTotal: PropTypes.number.isRequired,
  feedLikeRankingTotal: PropTypes.number.isRequired,
  cardScrapRankingTotal: PropTypes.number.isRequired,
};

export default CompareStat;
