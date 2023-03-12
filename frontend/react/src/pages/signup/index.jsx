import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import "./index.css";

// import template
import SignUpTemplate from "pages/signup/signupTemplate/signupTemplate";
import MDBox from "components/MDBox";

function SignUp() {
  return (
    <div className="container">
      <DashboardLayout>
        <DashboardNavbar />
        <MDBox>
          <SignUpTemplate />
        </MDBox>
      </DashboardLayout>
    </div>
  );
}

export default SignUp;
