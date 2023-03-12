// Import React
import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";

// Import - design mui
import { FormControl, Input, Button, Box } from "@mui/material";
import SendIcon from "@mui/icons-material/Send";
import ChatBubbleOutlineIcon from "@mui/icons-material/ChatBubbleOutline";

// Import - design MD
import MDTypography from "components/MDTypography";

// Import - custom

function HomeInput({ feedCommentCnt, handleComment, isCommentOpen }) {
  const [data, setData] = useState({
    inputData: "",
  });

  // local - 댓글 갯수
  const [commentCnt, setCommentCnt] = useState(0);

  // 채팅창 on/off 토글 창
  const [isChatOpen, setIsChatOpen] = useState(false);

  const handleChat = () => {
    setIsChatOpen(!isChatOpen);
    isCommentOpen(!isChatOpen);
  };

  // setData : status "loading", "sent", "failure", "sent"
  const handleSubmit = (event) => {
    event.preventDefault();

    handleComment(data); // 상위 부모에게 inputData값 보내기
    setCommentCnt(commentCnt + 1);
    // init 초기화
    setData({ inputData: "" });
    document.getElementById("input-comment-data").value = "";
  };

  useEffect(() => {
    if (feedCommentCnt !== null) setCommentCnt(feedCommentCnt.length);
    else setCommentCnt(0);
  }, [feedCommentCnt]);

  return (
    <form onSubmit={handleSubmit} id="demo">
      <FormControl sx={{ width: "100%", my: "10px" }}>
        <Box display="flex">
          <MDTypography variant="h6" component="p" color="text" mr={1} sx={{ mr: "2" }}>
            댓글&nbsp;&nbsp;&nbsp;{commentCnt}
          </MDTypography>
          <ChatBubbleOutlineIcon fontSize="large" onClick={handleChat} sx={{ cursor: "pointer" }} />
        </Box>
        {isChatOpen && (
          <Input
            sx={{ "--Input-decorator-childHeight": "45px" }}
            placeholder="댓글 입력란..."
            id="input-comment-data"
            type="text"
            fullWidth
            value={data.inputData}
            onChange={(event) => setData({ inputData: event.target.value })}
            // error={data.status === "failure"}
            endAdornment={
              <Button
                variant="contained"
                endIcon={<SendIcon />}
                onClick={handleSubmit}
                loading={data.status === "loading" ? 1 : 0}
              >
                SEND
              </Button>
            }
          />
        )}

        {/* {data.status === "failure" && (
          <FormHelperText sx={(theme) => ({ color: theme.palette.danger[400] })}>
            Oops! something went wrong, please try again later.
          </FormHelperText>
        )}
        {data.status === "sent" && (
          <FormHelperText sx={() => ({ color: "red" })}>You are all set!</FormHelperText>
        )} */}
      </FormControl>
    </form>
  );
}

HomeInput.defaultProps = {
  feedCommentCnt: [],
};

HomeInput.propTypes = {
  feedCommentCnt: PropTypes.shape(PropTypes.arrayOf),
  handleComment: PropTypes.func.isRequired,
  isCommentOpen: PropTypes.func.isRequired,
};

export default HomeInput;
