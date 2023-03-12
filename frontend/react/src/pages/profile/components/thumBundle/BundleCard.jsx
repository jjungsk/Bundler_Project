import PropTypes from "prop-types";
import { apiGetBundleDetail } from "apis/api/apiHomePage";
import Modal from "@mui/material/Modal";
import bundlerRabbit from "assets/images/bundler/bundler_rabbit_6.png";
import SettingsIcon from "@mui/icons-material/Settings";

// @mui material components
import Card from "@mui/material/Card";
import { useState } from "react";
import FavoriteIcon from "@mui/icons-material/Favorite";
import LockIcon from "@mui/icons-material/Lock";

import CardMedia from "@mui/material/CardMedia";

import ModalCardList from "pages/home/components/modalCardList";

// Material Dashboard 2 React components
import MDBox from "components/MDBox";
import MDButton from "components/MDButton";
import MDTypography from "components/MDTypography";
import Grid from "@mui/material/Grid";
import BundleSetBox from "../SettingModal/BundleSetting";

function ProfileBundle({ infoBundle }) {
  // const [open, setOpen] = useState(false);
  // const handleOpen = () => setOpen(true);
  const [open4, setOpen4] = useState(false);
  const BundleSetOpen = () => setOpen4(true);
  const BundleSetClose = () => setOpen4(false);
  const [IconHovered2, setIconHovered2] = useState(false);
  const [CardImgHover, setCardImgHover] = useState(false);

  // const [bundleCoverImg, setBundleCoverImg] = useState(CardImg);

  // 번들 상세 정보 저장
  const [bundleDetail, setBundleDetail] = useState([]);

  // 후하...
  // 번들 상세 조회
  const [isModalDetailOpend, setIsModalDetailOpend] = useState(false);
  const modalEetailOpen = () => setIsModalDetailOpend(true);
  const modalDetailClose = () => setIsModalDetailOpend(false);

  const handleBundleDetail = () => {
    const getInfo = async () => {
      await apiGetBundleDetail(infoBundle.bundleId)
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
    modalEetailOpen(); // modal detail 열기
  };

  const CardStyles = {
    // width: "400px",
    // height: "220px",
    transform: "scale(1)",
    transition: ".3s ease-in-out",
    // overflow: "hidden",
  };

  const CardHoverStyles = {
    // width: "400px",
    // height: "220px",
    transform: "scale(1.3)",
    // overflow: "hidden",
  };

  return (
    <Card
      sx={{
        display: "flex",
        flexDirection: "column",
        backgroundColor: "transparent",
        boxShadow: "none",
        overflow: "visible",
        marginBottom: "20px",
      }}
    >
      <ModalCardList
        open={isModalDetailOpend}
        handleCommetList={handleBundleDetail}
        handleCardClose={modalDetailClose}
        bundleId={infoBundle.bundleId}
        cardList={infoBundle.cardBundleQueryDtoList}
        commentList={bundleDetail.bundleCommentResponseList}
      />
      <Modal open={open4} onClose={BundleSetClose}>
        <BundleSetBox
          SelectBundleId={infoBundle.bundleId}
          bundleImage={infoBundle.bundleThumbnail}
          bundleThumtext={infoBundle.bundleThumbnailText}
          bundleTitle={infoBundle.feedTitle}
          bundleThumbnail={infoBundle.bundleThumbnail}
          bundlePrivate={infoBundle.bundlePrivate}
        />
      </Modal>
      <MDBox
        position="relative"
        width="100.25%"
        shadow="xl"
        borderRadius="xl"
        style={{ overflow: "hidden" }}
      >
        {infoBundle.bundleThumbnail === "" ? (
          <CardMedia
            src={bundlerRabbit}
            component="img"
            title={infoBundle.feedTitle}
            sx={{
              maxWidth: "100%",
              margin: 0,
              boxShadow: ({ boxShadows: { md } }) => md,
              objectFit: "cover",
              objectPosition: "center",
              opacity: "50%",
              width: "400px",
              height: "220px",
              overflow: "hidden",
              // width: "400px",
              // height: "220px",
            }}
            onMouseEnter={() => setCardImgHover(true)}
            onMouseLeave={() => setCardImgHover(false)}
            style={CardImgHover ? CardHoverStyles : CardStyles}
          />
        ) : (
          <CardMedia
            src={infoBundle.bundleThumbnail}
            component="img"
            title={infoBundle.feedTitle}
            sx={{
              maxWidth: "100%",
              margin: 0,
              boxShadow: ({ boxShadows: { md } }) => md,
              objectFit: "cover",
              objectPosition: "center",
              opacity: "50%",
              width: "400px",
              height: "220px",
              overflow: "hidden",
              // width: "400px",
              // height: "220px",
            }}
            onMouseEnter={() => setCardImgHover(true)}
            onMouseLeave={() => setCardImgHover(false)}
            style={CardImgHover ? CardHoverStyles : CardStyles}
          />
        )}
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
          {infoBundle.bundleThumbnailText}
        </MDTypography>
      </MDBox>
      <MDBox mt={1} mx={0.5} mb={1.5}>
        <MDBox mb={1} />
        <MDBox mb={3} lineHeight={0}>
          <MDTypography
            variant="button"
            fontWeight="medium"
            color="white"
            fontSize="20px"
            sx={{ marginLeft: "10px" }}
          >
            {infoBundle.feedTitle}
          </MDTypography>
        </MDBox>
        <MDBox // 번들 썸네일 하단 4개 컴퍼넌트
        >
          <Grid container spacing={0.5}>
            <Grid item xs={12} md={6}>
              <MDBox // 썸네일 번들 하단 묶음 - 좋아요(default) + 잠금
                sx={{
                  display: "flex",
                  justifyContent: "spaceBetween",
                }}
              >
                {/* {isBundleDefault === "false" ? ( */}
                {infoBundle.bundleDefault === false ? (
                  <MDBox // 썸네일 번들 하단 - 1. 좋아요와 좋아요 숫자
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
                      {infoBundle.feedLikeCnt}
                    </MDTypography>
                  </MDBox>
                ) : (
                  <MDBox
                    sx={{
                      marginLeft: "10px",
                    }}
                  >
                    <MDTypography>Default</MDTypography>
                  </MDBox>
                )}
                <MDBox // 썸네일 번들 하단 - 2. 잠금 표시
                  sx={{
                    float: "left",
                    marginLeft: "10px",
                  }}
                >
                  {/* {isBundlePrivate === "true" ? ( */}
                  {infoBundle.bundlePrivate === true ? (
                    <MDBox>
                      <LockIcon
                        sx={{
                          width: "25px",
                          height: "25px",
                        }}
                        color="secondary"
                      />
                    </MDBox>
                  ) : (
                    <MDBox />
                  )}
                </MDBox>
              </MDBox>
            </Grid>
            <Grid item xs={12} md={6}>
              <MDBox // 썸네일 번들 하단 묶음 - 상세보기 + 설정
                sx={{
                  display: "flex",
                  justifyContent: "spaceBetween",
                }}
              >
                <MDBox // 썸네일 번들 하단  - 3. 상세보기 버튼
                >
                  {infoBundle.cardBundleQueryDtoList !== null && (
                    <MDButton
                      variant="outlined"
                      size="small"
                      type="button"
                      onClick={handleCardListOpen}
                    >
                      상세 보기
                    </MDButton>
                  )}
                </MDBox>
                <MDBox // 썸네일 번들 하단 - 4. 썸네일 설정 버튼
                  sx={{
                    marginLeft: "20px",
                  }}
                >
                  <SettingsIcon
                    sx={{
                      width: "25px",
                      height: "25px",
                    }}
                    onClick={BundleSetOpen}
                    onMouseEnter={() => setIconHovered2(true)}
                    onMouseLeave={() => setIconHovered2(false)}
                    color={IconHovered2 ? "white" : "gray"}
                  />
                </MDBox>
              </MDBox>
            </Grid>
          </Grid>
        </MDBox>
      </MDBox>
    </Card>
  );
}

ProfileBundle.defaultProps = {
  infoBundle: {
    bundleId: 0,
    feedTitle: "",
    bundleLike: 0,
    bundlePrivate: false,
    bundleDefault: false,
    bundleThumbnail: "",
    bundleThumbnailText: "",
    cardBundleQueryDtoList: [],
    feedLikeCnt: 0,
  },
};

// 썸네일 카드의 프롭타입 설정
ProfileBundle.propTypes = {
  infoBundle: PropTypes.shape({
    feedLikeCnt: PropTypes.number,
    bundleId: PropTypes.number.isRequired,
    feedTitle: PropTypes.string.isRequired,
    bundleLike: PropTypes.number.isRequired,
    bundlePrivate: PropTypes.bool.isRequired,
    bundleDefault: PropTypes.bool.isRequired,
    bundleThumbnail: PropTypes.string.isRequired,
    bundleThumbnailText: PropTypes.string.isRequired,
    cardBundleQueryDtoList: PropTypes.shape(PropTypes.array),
  }),
};

export default ProfileBundle;
