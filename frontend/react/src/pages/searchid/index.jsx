// Material Dashboard 2 React example components
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import IdSearch from "./component/IdSearch";

function SearchId() {
  return (
    <DashboardLayout>
      <DashboardNavbar />
      <div>
        <IdSearch />
      </div>
    </DashboardLayout>
  );
}

export default SearchId;
