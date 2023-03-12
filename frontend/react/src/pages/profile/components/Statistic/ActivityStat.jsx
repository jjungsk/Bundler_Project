import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import PropTypes from "prop-types";

function ActivityStat({
  registerDate,
  mutualFollows,
  continuousCardMakeCnt,
  totalFeedLikeCnt,
  totalCardScrapCnt,
  mostMakeCategory,
  mostMakeSubCategory,
}) {
  // const tempAct = {
  //   registerDate: "2023-01-10",
  //   mutualFollows: 35,
  //   continuousCardMakeCnt: 33,
  //   totalFeedLikeCnt: 301,
  //   totalCardScrapCnt: 12,
  //   mostMakeCategory: "CS",
  //   mostMakeSubCategory: "네트워크",
  // };

  return (
    <MDBox
      sx={{
        // marginTop: "10px",
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
        활동 기록
      </MDTypography>
      <MDTypography fontSize="20px" marginTop="10px" marginLeft="10px">
        - 사용자 가입 일자 : {registerDate}
      </MDTypography>
      <MDTypography fontSize="20px" mt="5px" marginLeft="10px">
        - 최장 연속 카드 작성일수 : {continuousCardMakeCnt}
      </MDTypography>
      <MDTypography fontSize="20px" mt="5px" marginLeft="10px">
        - 총 피드 좋아요 수 : {totalFeedLikeCnt}
      </MDTypography>
      <MDTypography fontSize="20px" mt="5px" marginLeft="10px">
        - 총 카드 스크랩 수 : {totalCardScrapCnt}
      </MDTypography>
      <MDTypography fontSize="20px" mt="5px" marginLeft="10px">
        - 가장 많이 작성한 카테고리 - 대분류 : {mostMakeCategory}
      </MDTypography>
      <MDTypography fontSize="20px" mt="5px" marginLeft="10px">
        - 가장 많이 작성한 카테고리 - 중분류 : {mostMakeSubCategory}
      </MDTypography>
      <MDTypography fontSize="20px" mt="5px" marginLeft="10px">
        - 맞팔로우 수 : {mutualFollows}
      </MDTypography>
    </MDBox>
  );
}

ActivityStat.propTypes = {
  registerDate: PropTypes.string.isRequired,
  mutualFollows: PropTypes.number.isRequired,
  continuousCardMakeCnt: PropTypes.number.isRequired,
  totalFeedLikeCnt: PropTypes.number.isRequired,
  totalCardScrapCnt: PropTypes.number.isRequired,
  mostMakeCategory: PropTypes.string.isRequired,
  mostMakeSubCategory: PropTypes.string.isRequired,
};

export default ActivityStat;
