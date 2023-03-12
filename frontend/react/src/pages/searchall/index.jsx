import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import CardSearch from "./component/CardSearch";

function SearchCard() {
  return (
    <DashboardLayout>
      <DashboardNavbar />
      <CardSearch />
    </DashboardLayout>
  );
}

export default SearchCard;
