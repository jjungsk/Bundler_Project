// Material Dashboard 2 React example components
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";

// react-router-dom components
import { Link } from "react-router-dom";
// @mui material components
import Card from "@mui/material/Card";
import { Button } from "@mui/material/";

// Material Dashboard 2 React components
import MDBox from "components/MDBox";
import "./index.css";

// import template
import GithubLoginButton from "pages//login/GithubLoginButton";
import SignIn from "./signIn";

function AuthLogin() {
  return (
    <div className="container">
      <DashboardLayout>
        <DashboardNavbar />
        <Card sx={{ minWidth: 400, maxWidth: 500 }}>
          <div className="container5">
            {/* 로그인 버튼 */}
            <MDBox mt={0} mb={1}>
              <SignIn />
            </MDBox>
          </div>
          <div className="max-w-md w-full space-y-8">
            {/* 회원가입 버튼 */}
            <MDBox mt={1} mb={2} textAlign="center">
              <Link to="/signup">
                <Button
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
                  fullWidth
                >
                  회원가입
                </Button>
              </Link>
            </MDBox>
          </div>
          {/* github 로그인 */}
          <MDBox mt={0} mb={3} textAlign="center">
            <GithubLoginButton />
          </MDBox>
        </Card>
      </DashboardLayout>
    </div>
  );
}

export default AuthLogin;
