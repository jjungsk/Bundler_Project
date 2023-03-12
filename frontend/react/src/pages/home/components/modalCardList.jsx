// Import react
import React, { useState } from "react";
import PropTypes from "prop-types";
import { useSelector, useDispatch } from "react-redux";

// @mui material components
import { Card, Modal } from "@mui/material";

// Material Dashboard 2 React components
import MDBox from "components/MDBox";

// Import Custom
import DataTable from "pages/home/DataTable";
import ModalDetail from "pages/home/components/modalDetail";
import HomeInput from "pages/home/components/homeInput";
import HomeCommentList from "pages/home/components/homeCommentList";

// Import - axios
import { apiGetCardDetail, apiPostComment } from "apis/api/apiHomePage";

// Import - redux action
import actBundleCardId from "redux/actions/actionFeed";

function ModalCardList({
  open,
  handleCommetList,
  handleCardClose,
  bundleId,
  cardList,
  commentList,
}) {
  // Data - global
  const dispatch = useDispatch();
  const { bundleCardId } = useSelector((state) => state.homeReducer);
  const { userId } = useSelector((state) => state.authToken);
  // Data - local
  const columnList = [
    { Header: "유형", accessor: "firstCategoryName", width: "20%" },
    { Header: "제목", accessor: "feedTitle", width: "40%" },
    { Header: "내용", accessor: "feedContent" },
  ];

  // useState - card Detail Information
  // const [targetCardId, setTargetCardId] = useState(-1);
  const [cardDetail, setCardDetail] = useState({});
  const [cardOpenModal, setCardOpenModal] = useState(false);
  const handleDetailCardOpen = () => setCardOpenModal(true);
  const handleDetailCardClose = () => setCardOpenModal(false);

  // 댓글 저장
  const [isCommentListOpen, setIsCommentListOpen] = useState(false);

  // 댓글 이모티콘 클릭 시, commentList 설정
  const isCommentOpen = (onoff) => {
    setIsCommentListOpen(onoff);
  };

  // Function
  const handleBundleCloseModal = (e) => {
    e.preventDefault();
    handleCardClose();
  };

  // api로 카드 detail 정보 가져오기
  const handleOpen = async () => {
    await apiGetCardDetail(bundleCardId)
      .then(({ data }) => {
        setCardDetail(data);
      })
      .catch((error) => {
        console.log(error);
      });

    handleDetailCardOpen(); // 카드 모달 상세 페이지 열기
  };

  // List 클릭 시, 해당 카드의 상세정보 가져오고 모달 창으로 띄우기
  const handleSelectedCard = async (item) => {
    dispatch(actBundleCardId(item.cardId));

    await apiGetCardDetail(item.cardId)
      .then(({ data }) => {
        setCardDetail(data);
      })
      .catch((error) => {
        console.log(error);
      });

    handleDetailCardOpen();
  };

  // 번들 댓글 저장 및 댓글 다시 불러오기
  const handleComment = async (comment) => {
    const params = {
      targetFeedId: bundleId,
      content: comment.inputData,
      userId,
    };

    await apiPostComment(params)
      .then(async () => {
        handleCommetList(); // 댓글 목록 다시 불러오기
      })
      .catch((error) => {
        console.log(error);
      });
  };

  // custom style
  const style = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: "65%",
    bgcolor: "transparent",
    border: "none",
    boxShadow: 24,
    outline: 0,
    borderRadius: 5,
  };

  return (
    cardList !== null && (
      <>
        <ModalDetail
          open={cardOpenModal}
          handleOpen={handleOpen}
          handleClose={handleDetailCardClose}
          cardInfo={cardDetail}
        />
        <Modal open={open} onClose={handleBundleCloseModal}>
          <Card sx={style}>
            <MDBox p={3}>
              <DataTable
                table={{
                  columns: columnList,
                  rows: cardList,
                }}
                handleEdit={handleSelectedCard}
                canSearch
              />
            </MDBox>
            <MDBox p={3}>
              <HomeInput
                feedCommentCnt={commentList}
                handleComment={handleComment}
                isCommentOpen={isCommentOpen}
              />
              <HomeCommentList
                isCommentListOpen={isCommentListOpen}
                handleCommetList={handleCommetList}
                commentList={commentList}
              />
            </MDBox>
          </Card>
        </Modal>
      </>
    )
  );
}

// Default Vlaue
ModalCardList.defaultProps = {
  cardList: [],
  commentList: [],
  handleCommetList: () => {},
};

// Typechecking props for the ModalCardList
ModalCardList.propTypes = {
  open: PropTypes.bool.isRequired,
  handleCommetList: PropTypes.func,
  handleCardClose: PropTypes.func.isRequired,
  bundleId: PropTypes.number.isRequired,
  cardList: PropTypes.arrayOf(PropTypes.object),
  commentList: PropTypes.arrayOf(PropTypes.object),
};

export default ModalCardList;
