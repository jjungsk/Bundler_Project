import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import "./index.css";
import { useNavigate } from "react-router-dom";
import { Button } from "@mui/material";

function Notfound() {
  const navigate = useNavigate();
  const navigateToHome = () => {
    navigate("/home");
  };
  const navigateToA810 = () => {
    navigate("/A810");
  };

  return (
    <div className="container">
      <DashboardLayout>
        <div id="background" />
        <div className="top">
          <h1>404</h1>
          <h3>page not found</h3>
        </div>
        <br />

        <div className="containerghost">
          <div className="ghost-copy">
            <div className="one" />
            <div className="two" />
            <div className="three" />
            <div className="four" />
          </div>
          <div className="ghost">
            <div className="face">
              <div className="eye" />
              <div className="eye-right" />
              <div className="mouth" />
            </div>
          </div>
          <div className="shadow" />
        </div>
        <div className="bottom">
          <br />
          <h4>잘못된 경로에요! 알맞는 경로로 돌아가주세요</h4>
          <div className="buttons">
            <Button
              onClick={navigateToHome}
              id="bundlerBtn"
              className="learn-more2"
              sx={{
                marginTop: "4%",
                bgcolor: "#81D8CF",
                color: "#000000",
                fontSize: "large",
                fontWeight: "bold",
                width: "250px",
              }}
              type="submit"
            >
              홈으로 돌아가기
            </Button>
            &nbsp; &nbsp;
            <Button
              onClick={navigateToA810}
              id="bundlerBtn"
              className="learn-more2"
              sx={{
                marginTop: "4%",
                bgcolor: "#81D8CF",
                color: "#000000",
                fontSize: "large",
                fontWeight: "bold",
                width: "250px",
              }}
              type="submit"
            >
              A810
            </Button>
          </div>
        </div>
      </DashboardLayout>
    </div>
  );
}

export default Notfound;
