// Import - react
import React from "react";
import PropTypes from "prop-types";

// Import - design @mui/material
import { Box, TextField, Typography } from "@mui/material";

// Imort - custom
// import MakeBundleImageUpload from "pages/make/form/makeBundleImageUpload";

function MakeBundle({ selected, handleBundle }) {
  // 부모로 데이터 넘기기
  const handleBundleChange = (event) => {
    event.preventDefault();
    handleBundle(event);
  };

  return (
    <Box
      component="form"
      sx={{
        "& .MuiTextField-root": { ml: 3, mt: 3, width: "70vw" },
        mt: 3,
      }}
      noValidate
      autoComplete="off"
    >
      {selected === true && (
        <>
          <Box sx={{ display: "flex" }}>
            <Typography variant="h6">
              <Box sx={{ textAlign: "center", mt: 3 }}>썸네일</Box>
            </Typography>
            <TextField
              multiline
              rows={1}
              required
              autoFocus
              id="bundle-bundleThumbnail"
              type="text"
              name="bundleThumbnail"
              label="Required"
              InputLabelProps={{
                shrink: true,
              }}
              onChange={handleBundleChange}
            />
          </Box>
          <Box sx={{ display: "flex", mb: 5 }}>
            <Typography variant="h6">
              <Box sx={{ textAlign: "center", mt: 3 }}>
                썸네일
                <br />
                내용
              </Box>
            </Typography>
            <TextField
              multiline
              rows={3}
              required
              id="bundle-bundleThumbnailText"
              type="text"
              name="bundleThumbnailText"
              label="Required"
              InputLabelProps={{
                shrink: true,
              }}
              onChange={handleBundleChange}
            />
          </Box>
          <Box sx={{ display: "flex", mb: 5 }}>
            <Typography variant="h6">
              <Box sx={{ textAlign: "center", mt: 3 }}>
                피드
                <br />
                제목
              </Box>
            </Typography>
            <TextField
              multiline
              rows={3}
              required
              id="bundle-feedTitle"
              type="text"
              name="feedTitle"
              label="Required"
              InputLabelProps={{
                shrink: true,
              }}
              onChange={handleBundleChange}
            />
          </Box>
          <Box sx={{ display: "flex", mb: 5 }}>
            <Typography variant="h6">
              <Box sx={{ textAlign: "center", mt: 3 }}>
                피드
                <br />
                내용
              </Box>
            </Typography>
            <TextField
              multiline
              rows={3}
              required
              id="bundle-feedContent"
              type="text"
              name="feedContent"
              label="Required"
              InputLabelProps={{
                shrink: true,
              }}
              onChange={handleBundleChange}
            />
          </Box>

          {/* <MakeBundleImageUpload /> */}
        </>
      )}
    </Box>
  );
}

// Typechecking props for the SelectedCategory
MakeBundle.propTypes = {
  selected: PropTypes.bool.isRequired,
  handleBundle: PropTypes.func.isRequired,
};

export default MakeBundle;
