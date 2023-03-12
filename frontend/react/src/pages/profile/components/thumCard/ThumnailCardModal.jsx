// Import React
import React from "react";
// import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";

// @mui material components
import { Card, Modal } from "@mui/material";

// Material Dashboard 2 React components
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";

// Import Custom Component
import CardImg from "assets/images/bundler/main4.png";

function ProfileCardModal({ open, handleClose, infoCard }) {
  const handleCloseModal = (e) => {
    e.preventDefault();
    handleClose();
  };

  const style = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: "75%",
    bgcolor: "transparent",
    border: "none",
    boxShadow: 24,
    outline: 0,
    borderRadius: 5,
    mb: 3,
    maxWidth: 800,
  };
  return (
    <Card>
      <Modal open={open} onClose={handleCloseModal}>
        <Card sx={style}>
          <MDBox p={3} bgColor="#152744">
            <MDBox>
              <MDBox
                component="img"
                src={CardImg}
                alt={CardImg}
                borderRadius="lg"
                shadow="md"
                width="8%"
                position="relative"
                display="inline-block"
                zIndex={1}
              />
              <MDBox display="inline-block" mx={2}>
                <MDTypography
                  variant="h4"
                  textTransform="capitalize"
                  fontWeight="bold"
                  sx={{ textAlign: "left" }}
                >
                  [카드]&nbsp;&nbsp;&nbsp;{infoCard.firstCategoryName}
                </MDTypography>
                <MDTypography variant="h6" textTransform="capitalize" sx={{ textAlign: "left" }}>
                  {infoCard.secondCategoryName}
                </MDTypography>
                <MDTypography variant="overline" mt={1}>
                  출제자 :&nbsp;&nbsp;{infoCard.userNickname}
                </MDTypography>
              </MDBox>
            </MDBox>
            <MDBox mt={2} mb={3}>
              <MDTypography
                display="inline"
                variant="h5"
                textTransform="capitalize"
                fontWeight="bold"
              >
                문제&nbsp;&gt;&nbsp;&nbsp;{infoCard.feedTitle}
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
                  지문
                  <br />
                  {infoCard.feedContent}
                </MDTypography>
              </pre>
            </MDBox>
            <MDBox mt={2} mb={3}>
              <MDBox display="flex" alignItems="center" mt={3} lineHeight={1}>
                <MDTypography variant="h6">해설</MDTypography>
              </MDBox>
              <pre
                style={{
                  maxHeight: "100px",
                  overflow: "auto",
                  overflowX: "hidden",
                }}
              >
                <MDTypography variant="body2" component="p" color="text">
                  {infoCard.cardCommentary}
                </MDTypography>
              </pre>
            </MDBox>
          </MDBox>
        </Card>
      </Modal>
    </Card>
  );
}

// Default Vlaue
ProfileCardModal.defaultProps = {
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

// Typechecking props for the SimpleBlogCard
ProfileCardModal.propTypes = {
  open: PropTypes.bool.isRequired,
  handleClose: PropTypes.func.isRequired,
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

export default ProfileCardModal;
