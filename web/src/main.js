import 'es6-promise/auto';

// 基础组件
import Vue from 'vue';
import themes from './iview';
import App from './App';
import store from './store';
import router from './router';
import filter from './filter';

var VueEditable= require('@/libs/vue-editable.js');
Vue.use(VueEditable);

// element-UI
import { Table, TableColumn } from 'element-ui';
Vue.component('elTable', Table);
Vue.component('elTableColumn', TableColumn);

// 全局观察者
let Obs = window.Obs = new Vue();

// 配置
Vue.config.productionTip = false;

Object.keys(filter).forEach(key=>{
    Vue.filter(key, filter[key]);
});

window.vm = new Vue({
    el: '#app',
    router,
    store,
    template: '<App/>',
    components: { App }
});

export default window.vm;
