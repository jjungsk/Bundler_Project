// Improt - React
import React, { useState } from "react";
import ImageUploading from "react-images-uploading";

// Improt - Design
import MDBox from "components/MDBox";
import { Box, Button, Typography } from "@mui/material";

// eslint-disable-next-line
function MakeProfileImageUpload({ parentFunction }) {
  const [nowProfileimage, setProfileImages] = useState([]);
  parentFunction(nowProfileimage);
  const maxNumber = 1;

  const onChange = (imageList, addUpdateIndex) => {
    // data for submit
    const formData = new FormData();
    formData.append("file", imageList);

    console.log(imageList, addUpdateIndex);
    setProfileImages(imageList);
  };

  return (
    <div className="App">
      <ImageUploading
        multiple
        value={nowProfileimage}
        onChange={onChange}
        maxNumber={maxNumber}
        dataURLKey="data_url"
      >
        {({
          imageList,
          onImageUpload,
          // onImageRemoveAll,
          onImageUpdate,
          onImageRemove,
          isDragging,
          dragProps,
        }) => (
          // write your building UI
          <Box className="upload-image-wrapper" sx={{ display: "flex", textAlign: "center" }}>
            <Button
              sx={{ display: "flex", pl: 0 }}
              type="button"
              style={isDragging ? { color: "red" } : undefined}
              onClick={onImageUpload}
              {...dragProps}
            >
              <Typography variant="h6">
                <Box sx={{ textAlign: "center", mr: 3 }}>
                  이미지
                  <br />
                  업로드
                </Box>
              </Typography>
              <Box sx={{ textAlign: "center", verticalAlign: "center" }}>
                <Typography variant="h6">클릭하시거나 이미지를 여기로 끌어주세요!</Typography>
              </Box>
            </Button>
            {/* &nbsp;
            <button type="button" onClick={onImageRemoveAll}>
              Remove all images
            </button> */}
            {imageList.map((image, index) => (
              <MDBox key={image.toString()} className="image-item">
                <img src={image.data_url} alt="" width="100" />
                <Box className="image-item-btn-wrapper">
                  <Button type="button" onClick={() => onImageUpdate(index)} color="white">
                    수정
                  </Button>
                  <Button type="button" onClick={() => onImageRemove(index)} color="white">
                    제거
                  </Button>
                </Box>
              </MDBox>
            ))}
          </Box>
        )}
      </ImageUploading>
    </div>
  );
}

export default MakeProfileImageUpload;
