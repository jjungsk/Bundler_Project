// Import - react
import React from "react";
import PropTypes from "prop-types";

// Import - design @mui/material
import { Box, Button } from "@mui/material";

// Import - redux
import { useSelector } from "react-redux";

function MakeButton({
  handleValuesAdd,
  handleValuesDelete,
  handleValuesEdit,
  handleValuesCreate,
  bundleToggle,
  handleEmptyBundleCreate,
}) {
  // ======================== Data ===========================
  // Data - global
  const { cardList } = useSelector((state) => state.makeReducer); // state 값 가져오기

  // ======================= Return ============================
  return (
    <Box>
      <Button
        type="button"
        variant="contained"
        sx={{ m: 3 }}
        size="large"
        onClick={handleValuesAdd}
      >
        추가
      </Button>
      {cardList.length !== 0 && (
        <>
          <Button
            type="button"
            variant="contained"
            sx={{ m: 3 }}
            size="large"
            onClick={handleValuesEdit}
          >
            수정
          </Button>
          <Button
            type="button"
            variant="contained"
            sx={{ m: 3 }}
            size="large"
            onClick={handleValuesDelete}
          >
            삭제
          </Button>
          <Button
            type="button"
            variant="contained"
            sx={{ m: 3 }}
            size="large"
            onClick={handleValuesCreate}
          >
            생성
          </Button>
        </>
      )}
      {bundleToggle && cardList.length === 0 && (
        <Button
          type="button"
          variant="contained"
          sx={{ m: 3 }}
          size="large"
          onClick={handleEmptyBundleCreate}
        >
          빈 번들 생성
        </Button>
      )}
    </Box>
  );
}

MakeButton.propTypes = {
  handleValuesAdd: PropTypes.func.isRequired,
  handleValuesDelete: PropTypes.func.isRequired,
  handleValuesEdit: PropTypes.func.isRequired,
  handleValuesCreate: PropTypes.func.isRequired,
  bundleToggle: PropTypes.bool.isRequired,
  handleEmptyBundleCreate: PropTypes.func.isRequired,
};

export default MakeButton;
