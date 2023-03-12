// [Import - Design]
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";
import Grid from "@mui/material/Grid";

// import ChartData from "pages/pr  ofile/components/Statistic/ChartData230206.json";
import MyResponsivePie from "pages/profile/components/Statistic/pieChart";
// import tempfull from "../components/Statistic/tempFullStat.json";

import ActivityStat from "../components/Statistic/ActivityStat";
// import ActivityBadge from "../components/Statistic/ActivityBadge";

import CompareStat from "../components/Statistic/CompareStat";

// BundlelistTabForm Template
// function StatTab(userId) {
function StatTab(statdata) {
  const propstatdata = statdata;
  const fullStatData = propstatdata.data;

  const fullstatCategory = fullStatData.statCategory;
  const dataForPie = [
    {
      id: fullstatCategory[0].categoryName,
      label: fullstatCategory[0].categoryName,
      value: fullstatCategory[0].categoryMakeCount,
      color: "hsl(317, 70%, 50%)",
    },
    {
      id: fullstatCategory[1].categoryName,
      label: fullstatCategory[1].categoryName,
      value: fullstatCategory[1].categoryMakeCount,
      color: "hsl(27, 70%, 50%)",
    },
    {
      id: fullstatCategory[2].categoryName,
      label: fullstatCategory[2].categoryName,
      value: fullstatCategory[2].categoryMakeCount,
      color: "hsl(90, 70%, 50%)",
    },
    {
      id: fullstatCategory[3].categoryName,
      label: fullstatCategory[3].categoryName,
      value: fullstatCategory[3].categoryMakeCount,
      color: "hsl(173, 70%, 50%)",
    },
    {
      id: fullstatCategory[4].categoryName,
      label: fullstatCategory[4].categoryName,
      value: fullstatCategory[4].categoryMakeCount,
      color: "hsl(11, 70%, 50%)",
    },
  ];
  return (
    <MDBox // 전체 통계탭 박스
      sx={{
        width: "100%",
        height: "100%",
        backgroundColor: "#282535",
      }}
    >
      <MDBox // 역량 통계 전체
        sx={{
          width: "100%",
          height: "100%",
          marginTop: "20px",
        }}
      >
        <MDBox // 역량 통계 - 제목 및 설명
          sx={{
            marginLeft: "20px",
            marginTop: "30px",
          }}
        >
          <MDTypography
            sx={{
              fontSize: "35px",
              float: "left",
            }}
          >
            역량 통계
          </MDTypography>
          <MDTypography
            sx={{
              fontSize: "20px",
              marginLeft: "15px",
              marginTop: "15px",
              float: "left",
              color: "gray",
            }}
          >
            내가 작성한 카드 기준
          </MDTypography>
        </MDBox>
        <MDBox // 역량 통계 - 차트 + 통계치 박스
          sx={{
            width: "100%",
          }}
        >
          <Grid container spacing={3}>
            <Grid item xs={12} md={6}>
              <MDBox // 역량 통계 - 차트
              >
                <MDBox style={{ width: "auto", height: "500px", margin: "0 auto" }}>
                  <MyResponsivePie data={dataForPie} />
                </MDBox>
              </MDBox>
            </Grid>
            <Grid item xs={12} md={6}>
              <MDBox // 역량 통계 - 통계치
              >
                <Grid container spacing={1}>
                  {fullstatCategory.map((category) => (
                    <Grid item xs={12} md={6}>
                      <MDBox // 역량 통계 - 통계치 5개 개별
                        sx={{
                          // width: "320px",
                          // height: "200px",
                          backgroundColor: "#1F1D2B",
                          marginTop: "5px",
                          marginLeft: "20px",
                          flexDirection: "column",
                        }}
                        position="relative"
                      >
                        <MDBox // 역량 통계 - 개별 통계치 - 내용 (대분류 + 중분류)
                          sx={{
                            paddingRight: "5px",
                            paddingBottom: "15px",
                          }}
                        >
                          <MDBox // 역량 통계 - 통계치 - 대분류
                            sx={{
                              marginTop: "30px",
                              marginLeft: "30px",
                              marginBottom: "10px",
                              width: "100%",
                              height: "auto",
                              justifyContent: "row",
                            }}
                          >
                            <Grid container>
                              <Grid item xs={12}>
                                <MDBox
                                  sx={{
                                    width: "auto",
                                  }}
                                >
                                  <MDTypography
                                    sx={{
                                      marginTop: "10px",
                                      fontSize: "20px",
                                      float: "left",
                                    }}
                                  >
                                    {category.categoryName}
                                  </MDTypography>
                                  <MDTypography
                                    sx={{
                                      marginLeft: "10px",
                                      marginTop: "15px",
                                      fontSize: "18px",
                                      color: "white",
                                      float: "left",
                                    }}
                                  >
                                    ({category.proportion}%)
                                  </MDTypography>
                                </MDBox>
                              </Grid>
                              <Grid item xs={12}>
                                <MDTypography
                                  sx={{
                                    marginLeft: "2px",
                                    marginTop: "5px",
                                    fontSize: "18px",
                                    color: "gray",
                                    float: "left",
                                  }}
                                >
                                  작성 카드 {category.categoryMakeCount}개
                                </MDTypography>
                              </Grid>
                            </Grid>
                          </MDBox>
                          <MDBox // 역량 통계 - 통계치 - 중분류 하위
                            sx={{
                              flexDirection: "column",
                              marginTop: "5px",
                              marginLeft: "30px",
                              marginBottom: "10px",
                            }}
                          >
                            {category.subCategories.map((subcategory) => (
                              <MDBox>
                                <MDTypography
                                  sx={{
                                    marginTop: "10px",
                                    fontSize: "14px",
                                  }}
                                >
                                  - {subcategory.categoryName} ({subcategory.proportion}%)
                                  <span style={{ color: "gray", marginLeft: "5px" }}>
                                    ({subcategory.categoryMakeCount}개)
                                  </span>
                                </MDTypography>
                              </MDBox>
                            ))}
                          </MDBox>
                        </MDBox>
                      </MDBox>
                    </Grid>
                  ))}
                </Grid>
              </MDBox>
            </Grid>
          </Grid>
        </MDBox>
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
      <MDBox // 활동 통계 전체
      >
        <MDBox // 활동 통계 - 제목 및 설명
          sx={{
            marginLeft: "20px",
            marginTop: "30px",
          }}
        >
          <MDTypography
            sx={{
              fontSize: "35px",
              float: "left",
            }}
          >
            활동 통계
          </MDTypography>
          <MDTypography
            sx={{
              fontSize: "20px",
              marginLeft: "15px",
              marginTop: "15px",
              float: "left",
              color: "gray",
            }}
          >
            번들러에서의 활동
          </MDTypography>
        </MDBox>
        <MDBox // 활동 통계 - 내용 (수치 + 뱃지)
        >
          <Grid container spacing={3}>
            <Grid item xs={12} md={12}>
              <MDBox // 활동 통계 - 수치
              >
                <ActivityStat
                  registerDate={fullStatData.registerDate}
                  mutualFollows={fullStatData.mutualFollows}
                  continuousCardMakeCnt={fullStatData.continuousCardMakeCnt}
                  totalFeedLikeCnt={fullStatData.totalFeedLikeCnt}
                  totalCardScrapCnt={fullStatData.totalCardScrapCnt}
                  mostMakeCategory={fullStatData.mostMakeCategory}
                  mostMakeSubCategory={fullStatData.mostMakeSubCategory}
                />
              </MDBox>
            </Grid>
            {/* <Grid item xs={12} md={6}>
              <MDBox // 활동 통계 - 뱃지
              >
                <ActivityBadge />
              </MDBox>
            </Grid> */}
          </Grid>
        </MDBox>
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
      <MDBox // 비교 통계 전체
      >
        <MDBox // 비교 통계 - 제목 및 설명
          sx={{
            marginLeft: "20px",
            marginTop: "30px",
            height: "50px",
          }}
        >
          <MDTypography
            sx={{
              fontSize: "35px",
              float: "left",
            }}
          >
            비교 통계
          </MDTypography>
          <MDTypography
            sx={{
              fontSize: "20px",
              marginLeft: "15px",
              marginTop: "15px",
              float: "left",
              color: "gray",
            }}
          >
            그룹 내 자신의 위치를 알려줍니다
          </MDTypography>
        </MDBox>
        <MDBox // 비교 통계 - 내용
        >
          <CompareStat
            feedLikeRankingTotal={fullStatData.feedLikeRankingTotal}
            feedLikeRankingFollowing={fullStatData.feedLikeRankingFollowing}
            cardScrapRankingTotal={fullStatData.cardScrapRankingTotal}
            cardScrapRankingFollowing={fullStatData.cardScrapRankingFollowing}
            cardMakeRankingTotal={fullStatData.cardMakeRankingTotal}
          />
        </MDBox>
      </MDBox>
    </MDBox>
  );
}

// StatTab.propTypes = {
//   // userId: PropTypes.number.isRequired,
//   data: PropTypes.object.isRequired,
// };

export default StatTab;

// // Typechecking props for the SelectedCategory
// BundleListTab.propTypes = {
//   selected: PropTypes.bool.isRequired,
//   handleChange: PropTypes.func.isRequired,
// };
