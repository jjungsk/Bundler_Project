// Improt - React
import React, { useState } from "react";
import ImageUploading from "react-images-uploading";

// Improt - Design
import MDBox from "components/MDBox";
import { Box, Button, Typography } from "@mui/material";

function MakeBundleImageUpload() {
  const [images, setImages] = useState([]);
  const maxNumber = 1;

  const onChange = (imageList, addUpdateIndex) => {
    // data for submit
    const formData = new FormData();
    formData.append("file", imageList);

    console.log(imageList, addUpdateIndex);
    setImages(imageList);
  };

  return (
    <div className="App">
      <ImageUploading
        multiple
        value={images}
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
                <Typography variant="h6">Click or Drag & Drop</Typography>
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
                  <Button type="button" onClick={() => onImageUpdate(index)}>
                    수정
                  </Button>
                  <Button type="button" onClick={() => onImageRemove(index)}>
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

export default MakeBundleImageUpload;
