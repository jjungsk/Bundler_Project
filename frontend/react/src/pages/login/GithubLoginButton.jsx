// GithubLogin을 Component로 사용하기 위해 import
import React from "react";
import { Button } from "@mui/material/";
// 버튼에 있는 github아이콘 import
import GitHubIcon from "@mui/icons-material/GitHub";

function GithubLoginButton() {
  const GithubLoginButtonClick = async () => {
    window.location.assign(
      `${process.env.REACT_APP_PORT_GLOBAL}/oauth2/authorization/github?redirect_uri=${process.env.REACT_APP_PORT_GLOBAL}`
    );
  };

  return (
    <div>
      <Button
        onClick={GithubLoginButtonClick}
        id="bundlerBtn"
        className="learn-more"
        sx={{
          bgcolor: "#FFFFFF",
          color: "#000000",
          fontSize: "large",
          fontWeight: "bold",
          width: "350px",
        }}
        variant="contained"
      >
        <GitHubIcon />
        &nbsp;GitHub
      </Button>
    </div>
  );
}

export default GithubLoginButton;
