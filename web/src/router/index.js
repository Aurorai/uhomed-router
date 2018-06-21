import Vue from 'vue';
import Router from 'vue-router';
import Routers from './routers';
import Util from '../libs/util';
import iView from 'iview';
import { IS_LOGIN } from '@/libs/session';

Vue.use(Router)

let RouterConfig = {
    routes: Routers
};

// RouterConfig.mode = 'history';

let router = new Router(RouterConfig);

router.beforeEach((to ,from, next)=>{
    iView.LoadingBar.start();
    Util.title( to.meta.title );

    // 未登录
    if( to.meta.token && !IS_LOGIN() ){
        let callback = to.fullPath;
        Util.login(router, callback);
    }
    next();
});

router.afterEach((to, from, next) => {
    iView.LoadingBar.finish();
    window.scrollTo(0, 0);
});

export default router;
