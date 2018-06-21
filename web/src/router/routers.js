const routers = [
    {
        path: '/login',
        meta: {
            title: '用户登录'
        },
        component: (resolve) => require(['@/views/login.vue'], resolve)
    },
    {
        path: '/error',
        meta: {
            title: 'ERR',
        },
        component: (resolve) => require(['@/views/error.vue'], resolve)
    },
    {
        path: '/',
        meta: {
            title: '首页',
            token: true
        },
        redirect: '/home'
    },
    {
        path: '/layout',
        alias: '/',
        component: (resolve) => require(['@/components/layout.vue'], resolve),
        children: [
            {
                path: '/home',
                meta: {
                    title: '控制面板',
                    token: true
                },
                component: (resolve) => require(['@/views/home'], resolve)
            },
            {
                path: '/article',
                meta: {
                    title: '文章列表',
                    token: true
                },
                component: (resolve) => require(['@/views/article'], resolve)
            },
            {
                path: '/group',
                meta:{
                    title: '方法列表',
                    token: true
                },
                component: (resolve) => require(['@/views/group'], resolve)
            },
            {
                path: '/group/addGroup',
                meta:{
                    title: '新增分组',
                    token: true
                },
                component: (resolve) => require(['@/views/group/addGroup'], resolve)
            },
            {
                path: '/group/method',
                meta:{
                    title: '方法列表',
                    token: true
                },
                component: (resolve) => require(['@/views/group/method'], resolve)
            },
            {
                path: '/group/method/addMethod',
                meta:{
                    title: '添加方法',
                    token: true
                },
                component: (resolve) => require(['@/views/group/method/methodInfo'], resolve)
            },
            {
                path: '/group/method/updateMethod',
                meta:{
                    title: '添加方法',
                    token: true
                },
                component: (resolve) => require(['@/views/group/method/methodInfo'], resolve)
            },
            {
                path: '/group/method/copyMethod',
                meta:{
                    title: 'copy方法',
                    token: true
                },
                component: (resolve) => require(['@/views/group/method/methodInfo'], resolve)
            },
            {
                path: '/group/method/develop',
                meta:{
                    title: '添加方法',
                    token: true
                },
                component: (resolve) => require(['@/views/group/method/develop'], resolve)
            },
            {
                path: '/test',
                meta:{
                    title: '计算成本',
                    token: true
                },
                component: (resolve) => require(['@/views/test'], resolve)
            },
            {
                path: '/test/home',
                meta:{
                    title: '房屋计算',
                    token: true
                },
                component: (resolve) => require(['@/views/test/home'], resolve)
            },
            {
                path: 'error',
                meta: {
                    title: 'ERROR',
                    token: true
                },
                component: (resolve) => require(['@/views/error.vue'], resolve)
            },
            {
                path: '*',
                redirect: 'layout/error',
            }
        ]
    },
    {
        path: '*',
        redirect: '/error'
    }
]
export default routers;