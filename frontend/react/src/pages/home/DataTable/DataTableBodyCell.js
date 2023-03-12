// prop-types is a library for typechecking of props
import PropTypes from "prop-types";

// Material Dashboard 2 React components
import MDBox from "components/MDBox";

import { Button } from "@mui/material";

function DataTableBodyCell({ noBorder, align, children, handleEdit }) {
  const handleEditForm = (e) => {
    e.preventDefault();
    handleEdit(children.props.row.original);
  };

  return (
    <MDBox
      component="td"
      textAlign={align}
      py={1.5}
      px={3}
      sx={({ palette: { light }, typography: { size }, borders: { borderWidth } }) => ({
        fontSize: size.sm,
        borderBottom: noBorder ? "none" : `${borderWidth[1]} solid ${light.main}`,
      })}
    >
      <MDBox
        display="inline-block"
        width="max-content"
        color="text"
        sx={{ verticalAlign: "middle" }}
      >
        <Button sx={{ backgroundColor: "transparent" }} onClick={(e) => handleEditForm(e)}>
          {children}
        </Button>
      </MDBox>
    </MDBox>
  );
}

// Setting default values for the props of DataTableBodyCell
DataTableBodyCell.defaultProps = {
  noBorder: false,
  align: "left",
};

// Typechecking props for the DataTableBodyCell
DataTableBodyCell.propTypes = {
  children: PropTypes.node.isRequired,
  noBorder: PropTypes.bool,
  align: PropTypes.oneOf(["left", "right", "center"]),
  handleEdit: PropTypes.func.isRequired,
};

export default DataTableBodyCell;
