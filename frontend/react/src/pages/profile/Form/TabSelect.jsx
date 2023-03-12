import PropTypes from "prop-types";

// 각 탭 form들을 import
import BundleListTab from "./BundleListForm";
import CardListTab from "./CardListForm";
import StatTab from "./StatForm";

function SelectedTab({ selected, data1, data2, data3 }) {
  switch (selected) {
    case "bundleTab":
      return <BundleListTab data={data2} />;
    case "statTab":
      return <StatTab data={data3} />;
    default:
      return <CardListTab data={data1} />;
  }
}

SelectedTab.defaultProps = {
  selected: "cardTab",
  data1: {},
  data2: {},
  data3: {},
};

SelectedTab.propTypes = {
  selected: PropTypes.string,
  data1: PropTypes.arrayOf(PropTypes.object),
  data2: PropTypes.arrayOf(PropTypes.object),
  data3: PropTypes.arrayOf(PropTypes.object),
};

export default SelectedTab;
