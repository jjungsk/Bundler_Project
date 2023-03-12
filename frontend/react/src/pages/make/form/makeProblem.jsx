// Import - react
import React from "react";
import PropTypes from "prop-types";

// Import - design mui
import { Box, TextField, Typography } from "@mui/material";

function MakeProblem({ handleChangeValues }) {
  // ------------ Function ----------------
  // (Data 1 - Func) Catd Input Data Changed
  const handleChange = (event) => {
    event.preventDefault();
    const { name, value } = event.target;
    handleChangeValues(event, name, value);
  };

  // ------------ Return ----------------
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
      <Box sx={{ mt: 2, display: "flex" }}>
        <Typography variant="h6">
          <Box sx={{ textAlign: "center", mt: 3 }}>제목</Box>
        </Typography>
        <TextField
          // {...(valid.isFeedTitle ? { error: true } : {})}
          // helperText="필수 입력란"
          multiline
          rows={1}
          required
          autoFocus
          id="problem-title"
          type="text"
          name="feedTitle"
          label="Required"
          InputLabelProps={{
            shrink: true,
          }}
          onChange={handleChange}
        />
      </Box>
      <Box sx={{ display: "flex" }}>
        <Typography variant="h6">
          <Box sx={{ textAlign: "center", mt: 3 }}>내용</Box>
        </Typography>
        <TextField
          multiline
          rows={5}
          required
          id="problem-content"
          type="text"
          name="feedContent"
          label="Required"
          InputLabelProps={{
            shrink: true,
          }}
          onChange={handleChange}
        />
      </Box>
      <Box sx={{ display: "flex" }}>
        <Typography variant="h6">
          <Box sx={{ textAlign: "center", mt: 3 }}>해답</Box>
        </Typography>
        <TextField
          // {...(valid.isCardCommentary ? { error: true } : {})}
          // helperText="필수 입력란"
          multiline
          rows={4}
          id="problem-cardCommentary"
          type="text"
          name="cardCommentary"
          label="Optional"
          InputLabelProps={{
            shrink: true,
          }}
          onChange={handleChange}
        />
      </Box>
      <Box sx={{ display: "flex" }}>
        <Typography variant="h6">
          <Box sx={{ textAlign: "center", mt: 3 }}>설명</Box>
        </Typography>
        <TextField
          multiline
          rows={2}
          id="problem-description"
          type="text"
          name="cardDescription"
          label="Optional"
          InputLabelProps={{
            shrink: true,
          }}
          onChange={handleChange}
        />
      </Box>
    </Box>
  );
}

MakeProblem.propTypes = {
  handleChangeValues: PropTypes.func.isRequired,
};

export default MakeProblem;
