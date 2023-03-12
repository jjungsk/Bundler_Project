// [Import - Design]
// import Switch from "@mui/material/Switch";
import MDBox from "components/MDBox";
import Grid from "@mui/material/Grid";
import React from "react";
import PropTypes from "prop-types";

// import bundlerRabbit from "assets/images/bundler/bundler_rabbit_6.png";
import sorryRabbit from "assets/images/bundler/bundler_rabbit_8.png";
import MDTypography from "components/MDTypography";
import ProfileBundle from "../components/thumBundle/BundleCard";

// BundlelistTabForm Template
function BundleListTab({ data }) {
  return (
    <MDBox
      sx={{
        marginLeft: "3%",
        marginTop: "7%",
        marginRight: "3%",
      }}
    >
      {data.length === 0 ? (
        <MDBox
          sx={{
            display: "flex",
            flexDirection: "row",
            justifyContent: "center",
            height: "700px",
          }}
        >
          <MDBox // 토끼사진과 번들없다는 글자
            sx={{
              display: "flex",
              flexDirection: "column",
              marginTop: "50px",
            }}
          >
            <MDBox // 토끼사진만
              sx={{ display: "flex", justifyContent: "center" }}
            >
              <img src={sorryRabbit} alt="noBundle" style={{ height: "200px", width: "200px" }} />
            </MDBox>
            <MDTypography fontSize="50px" mt={1}>
              해당하는 번들이 없어요!
            </MDTypography>
          </MDBox>
        </MDBox>
      ) : (
        <Grid container spacing={3}>
          {/* {tempBundleData.map((oneBundle) => ( */}
          {data.map((oneBundle) => (
            <Grid item xs={12} md={6} lg={3}>
              <ProfileBundle infoBundle={oneBundle} />
            </Grid>
          ))}
        </Grid>
      )}
    </MDBox>
  );
}

BundleListTab.defaultProps = {
  data: {},
};

BundleListTab.propTypes = {
  data: PropTypes.shape(PropTypes.object),
};

export default BundleListTab;
