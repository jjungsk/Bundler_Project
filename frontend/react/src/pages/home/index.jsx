/*
 기본 홈 Page 구성  
 */

// Import - layout
// import HomeLayout from "pages/home/layout";
// import HomeNavbar from "pages/home/components/HomeNavbar";
import DashboardLayout from "examples/LayoutContainers/DashboardLayout";
import DashboardNavbar from "examples/Navbars/DashboardNavbar";
import Footer from "examples/Footer";
import HomeInfiniteScroll from "pages/home/components/homeInfiniteScroll";

function Home() {
  return (
    <DashboardLayout>
      <DashboardNavbar />
      <HomeInfiniteScroll />
      <Footer />
    </DashboardLayout>
  );
}

export default Home;
