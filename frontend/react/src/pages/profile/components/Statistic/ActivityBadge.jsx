import MDBox from "components/MDBox";
import MDTypography from "components/MDTypography";

function ActivityBadge() {
  return (
    <MDBox
      sx={{
        marginLeft: "10px",
        marginBottom: "15px",
        flexDirection: "column",
      }}
    >
      <MDTypography
        sx={{
          fontSize: "27px",
          color: "#00EBA4",
          marginLeft: "10px",
        }}
      >
        받은 뱃지 기록
      </MDTypography>
      <MDBox // 받은 뱃지 이미지를 담을 박스
        sx={{
          marginTop: "10px",
          backgroundColor: "#808191",
          borderRadius: "10px",
          width: "80%",
          height: "350px",
        }}
      />
    </MDBox>
  );
}

export default ActivityBadge;
