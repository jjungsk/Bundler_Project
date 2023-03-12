// Import React
import React, { useState } from "react";
import { useSelector } from "react-redux";
import PropTypes from "prop-types";

// @mui material components
import {
  Card,
  Modal,
  Box,
  Radio,
  RadioGroup,
  FormControlLabel,
  FormControl,
  FormLabel,
  Button,
  Input,
} from "@mui/material";

// Material Dashboard 2 React components
import MDBox from "components/MDBox";
import MDSnackbar from "components/MDSnackbar";

// Import Custom Component
import { apiPutCardScrap, apiPostCardScrap, apiDeleteCardScrap } from "apis/api/apiHomePage";

function ScrapButtonModal({
  open,
  handleClose,
  targetId,
  bundleList,
  handleBundleList,
  isBundleAdded,
}) {
  // 해당 유저 정보
  const { userId } = useSelector((state) => state.authToken);

  // 어떤 번들을 선택할지 또는 생성할지 선택
  const [selectedBundle, setSelectedBundle] = useState(0);

  // 선택한 카드가 이미 선택된 번들에 추가 되어 있는지 안되어 있는지 확인
  const [isExist, setIsExist] = useState(false);

  // 빈 번들 생성 시 제목과 내용
  const [createNewBundle, setCreateNewBundle] = useState({
    bundleTitle: "",
    bundleContent: "",
  });

  // (*) Alarm
  const [valid, setValid] = useState({
    isValid: false,
    comment: "",
    state: "info",
  });

  // 번들 선택
  const handleRadioChange = (event) => {
    setSelectedBundle(event.target.value);
    setIsExist(false);

    bundleList.forEach((bundle) => {
      if (bundle.bundleId === Number.parseInt(event.target.value, 10)) {
        if (bundle.ableToInsertCardInBundle === false) {
          setIsExist(true);
        }
      }
    });
  };

  // 번들 제목
  const handleBundleTitle = (e) => {
    e.preventDefault();
    setCreateNewBundle({ ...createNewBundle, bundleTitle: e.target.value });
  };
  // 번들 내용
  const handleBundleContent = (e) => {
    e.preventDefault();
    setCreateNewBundle({ ...createNewBundle, bundleContent: e.target.value });
  };

  // 모달 닫기
  const handleCloseModal = (e) => {
    e.preventDefault();
    setSelectedBundle(0);
    handleClose();
  };

  // 이미 추가된 카드 이므로 번들에서 삭제
  const handleDelete = (event) => {
    event.preventDefault();

    const del = async () => {
      await apiDeleteCardScrap(selectedBundle, targetId)
        .then((success) => {
          handleBundleList();
          isBundleAdded(false);
          setValid({
            isValid: true,
            comment: success.data,
            state: "info",
          });
        })
        .catch((error) => {
          console.log(error);
          setValid({
            isValid: true,
            comment: "카드 삭제 실패",
            state: "error",
          });
        });
    };
    del();

    setSelectedBundle(0);
    handleClose();
  };

  // 번들 스크랩 및 생성
  const handleSubmit = (event) => {
    event.preventDefault();

    if (selectedBundle === "-1") {
      // 새로운 번들 생성 시
      const added = async () => {
        const params = {
          userId,
          feedTitle: createNewBundle.bundleTitle,
          feedContent: createNewBundle.bundleContent,
        };
        await apiPostCardScrap(targetId, params)
          .then((success) => {
            handleBundleList();
            isBundleAdded(true);
            setValid({
              isValid: true,
              comment: success.data,
              state: "info",
            });
          })
          .catch((error) => {
            console.log(error);
            setValid({
              isValid: true,
              comment: error,
              state: "error",
            });
          });
      };
      added();
    } else {
      // 기존 번들에 추가
      const added = async () => {
        await apiPutCardScrap({
          cardId: targetId,
          bundleId: selectedBundle,
        })
          .then((success) => {
            handleBundleList();
            isBundleAdded(true);
            setValid({
              isValid: true,
              comment: success.data,
              state: "info",
            });
          })
          .catch((error) => {
            setValid({
              isValid: true,
              comment: error,
              state: "error",
            });
          });
      };
      added();
    }

    setSelectedBundle(0);
    handleClose();
  };

  const style = {
    width: "70%",
    bgcolor: "transparent",
    // borderRadius: 5,
    borderRadius: "none",
  };
  return (
    <Card sx={style}>
      <Modal open={open} onClose={handleCloseModal}>
        <Card
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            width: "50%",
          }}
        >
          <MDBox
            p={3}
            bgColor="#152744"
            sx={{ maxHeight: "600px", overflowY: "scroll", overflowX: "hidden" }}
          >
            <FormControl sx={{ m: 3 }} variant="standard">
              <FormLabel id="scrap-card-bundle">스크랩 추가 하기</FormLabel>
              <RadioGroup
                aria-labelledby="scrap-card-bundle"
                name="select-bundle"
                value={selectedBundle}
                onChange={handleRadioChange}
              >
                {bundleList.map((bundle) => (
                  <FormControlLabel
                    key={bundle.bundleId}
                    value={bundle.bundleId}
                    control={<Radio />}
                    label={bundle.feedTitle}
                  />
                ))}
                <FormControlLabel value="-1" control={<Radio />} label="번들 생성" />
              </RadioGroup>
              {/* <FormHelperText>{helperText}</FormHelperText> */}
            </FormControl>
            {selectedBundle === "-1" && (
              <Box display="grid" width="70%">
                <Input placeholder="번들 제목" onChange={handleBundleTitle} />
                <Input placeholder="번들 내용" onChange={handleBundleContent} />
              </Box>
            )}
            <Box sx={{ my: 1 }}>
              <Button
                sx={{ mt: 1, mr: 1 }}
                type="button"
                variant="outlined"
                onClick={(e) => handleCloseModal(e)}
              >
                취소
              </Button>
              {isExist === true ? (
                <Button
                  sx={{ mt: 1, mr: 1 }}
                  type="button"
                  variant="outlined"
                  onClick={(e) => handleDelete(e)}
                >
                  해당 번들에서 삭제
                </Button>
              ) : (
                <Button
                  sx={{ mt: 1, mr: 1 }}
                  type="button"
                  variant="outlined"
                  onClick={(e) => handleSubmit(e)}
                >
                  생성
                </Button>
              )}
            </Box>
          </MDBox>
        </Card>
      </Modal>
      <MDSnackbar
        color={valid.state} // info: 파랑, error: 빨강
        icon="notifications"
        title="알람"
        content={valid.comment}
        // dateTime="11 mins ago"
        open={valid.isValid}
        close={() => setValid({ valid: false, comment: "", state: "info" })}
      />
    </Card>
  );
}

ScrapButtonModal.defaultProps = {
  bundleList: [],
};

// Typechecking props for the SimpleBlogCard
ScrapButtonModal.propTypes = {
  open: PropTypes.bool.isRequired,
  handleClose: PropTypes.func.isRequired,
  targetId: PropTypes.number.isRequired,
  bundleList: PropTypes.arrayOf(PropTypes.object),
  handleBundleList: PropTypes.func.isRequired,
  isBundleAdded: PropTypes.func.isRequired,
};

export default ScrapButtonModal;
