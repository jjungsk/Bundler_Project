// Import React
import React, { useState } from "react";
import PropTypes from "prop-types";

// Import mui/style
import BookmarkBorderIcon from "@mui/icons-material/BookmarkBorder";
import { Box, Typography } from "@mui/material";
import MDSnackbar from "components/MDSnackbar";

// Import - custom
import ScrapButtonModal from "pages/home/buttons/scrapButtonModal";
import { apiPostBundleScrap } from "apis/api/apiHomePage";
import { useSelector } from "react-redux";

// scrap Button 상태 - 활성화/비활성화
function ScrapButtons({ feedType, targetId, bundleList, handleBundleList }) {
  // Data Global
  const { userId } = useSelector((state) => state.authToken);

  // scrap Button Modal Set
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  // (*) Alarm
  const [valid, setValid] = useState({
    isValid: false,
    comment: "",
    state: "info",
  });

  // CARD Scrap Modal 창
  const handleToggle = (e) => {
    e.preventDefault();
    handleOpen(); // modal은 무조건 열리게
    handleBundleList();
  };

  // BUNDLE Scrap function
  const handleBundleScrap = () => {
    const params = { userId, bundleId: targetId };
    const added = async () => {
      await apiPostBundleScrap(params)
        .then(({ data }) => {
          setValid({
            isValid: true,
            comment: data,
            state: "info",
          });
        })
        .catch(() => {
          setValid({
            isValid: true,
            comment: "번들 스크랩 실패",
            state: "error",
          });
        });
    };
    added();
  };

  return (
    <Box className="icons-list">
      <ScrapButtonModal
        open={open}
        handleClose={handleClose}
        targetId={targetId}
        bundleList={bundleList}
        handleBundleList={handleBundleList}
      />
      {feedType === "CARD" ? (
        <>
          <BookmarkBorderIcon
            sx={{ color: "red", cursor: "pointer", transition: { transform: "300ms ease" } }}
            fontSize="large"
            className="button"
            onClick={handleToggle}
          />
          <Typography variant="h2" align="center">
            &nbsp;
          </Typography>
        </>
      ) : (
        <>
          <BookmarkBorderIcon
            sx={{ cursor: "pointer", "&:hover": { transform: "scale(1.2)" } }}
            fontSize="large"
            className="button"
            onClick={handleBundleScrap}
          />
          <Typography variant="h2" align="center">
            &nbsp;
          </Typography>
        </>
      )}
      <MDSnackbar
        color={valid.state} // info: 파랑, error: 빨강
        icon="notifications"
        title="알람"
        content={valid.comment}
        // dateTime="11 mins ago"
        open={valid.isValid}
        close={() => setValid({ valid: false, comment: "", state: "info" })}
      />
    </Box>
  );
}

ScrapButtons.defaultProps = {
  bundleList: [],
  handleBundleList: function async() {},
};

ScrapButtons.propTypes = {
  feedType: PropTypes.string.isRequired,
  targetId: PropTypes.number.isRequired,
  bundleList: PropTypes.arrayOf(PropTypes.object),
  handleBundleList: PropTypes.func,
};

export default ScrapButtons;
