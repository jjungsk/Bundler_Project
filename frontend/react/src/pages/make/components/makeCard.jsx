/**
=========================================================
* Material Dashboard 2 React - v2.1.0
=========================================================
*/

// react-router components
// import { Link } from "react-router-dom";

// prop-types is a library for typechecking of props
import PropTypes from "prop-types";

// @mui material components
import { Card, Button } from "@mui/material";

// Material Dashboard 2 React components
import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";

function MakeCard({ category, id, title, description }) {
  return (
    <Card sx={{ ml: 1, maxWidth: 300 }}>
      <MDBox p={3} mx={3}>
        <MDBox>
          <MDBox display="inline-block" mx={2}>
            <MDTypography variant="h3" textTransform="capitalize" fontWeight="bold">
              {category}
            </MDTypography>
            <MDTypography variant="overline" mt={1}>
              {id}
            </MDTypography>
          </MDBox>
        </MDBox>
        <MDBox mt={2} mb={3}>
          <MDTypography display="inline" variant="h6" textTransform="capitalize" fontWeight="bold">
            {title}
          </MDTypography>
        </MDBox>
        <MDBox mt={2} mb={3}>
          <MDTypography variant="body2" component="p" color="text">
            {description}
          </MDTypography>
        </MDBox>
        <Button>버어트으은</Button>
      </MDBox>
    </Card>
  );
}

// Default Vlaue
// HomeCard.defaultProps = {
//   commentList: null,
// };

// Typechecking props for the SimpleBlogCard
MakeCard.propTypes = {
  category: PropTypes.string.isRequired,
  id: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
  description: PropTypes.string.isRequired,
};

export default MakeCard;
