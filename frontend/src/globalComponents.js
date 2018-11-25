import DropDown from "./components/Dropdown.vue";
import VueApexCharts from "vue-apexcharts";


/**
 * You can register global components here and use them as a plugin in your main Vue instance
 */

const GlobalComponents = {
  install(Vue) {
    Vue.component("drop-down", DropDown);
    Vue.component("apexchart", VueApexCharts);
  }
};

export default GlobalComponents;
