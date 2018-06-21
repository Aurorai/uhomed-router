// 定义左侧导航
let menus =[
    {
        type: 'menu',
        name: 'home',
        icon: 'android-home',
        text: '首页',
        path: '/home'
    },
    {
        type: 'menu',
        name: 'article',
        icon: 'person-stalker',
        text: '分组管理',
        path: '/group',
        ison: ['/group/addGroup','/group/method','/group/method/updateMethod','/group/method/addMethod','/group/method/develop','/group/method/copyMethod']
    },
    {
        type: 'submenu',
        name: 'community',
        icon: 'chatboxes',
        text: '社区',
        childens:[
            {
                name: 'test',
                text: '计算',
                path: '/test',
                ison: ['/test']
            },
            {
                name: 'homeTest',
                text: '房租计算',
                path: '/test/home',
                ison: ['/test/home']
            }
        ]
    },
    {
        type: 'submenu',
        name: 'digital',
        icon: 'pie-graph',
        text: '数据',
        childens: [
            {
                name: 'work',
                text: '业务数据',
                path: '/users/table'
            },{
                name: 'flow',
                text: '流量数据',
                path: '/users/table'
            }
        ]
    },
    {
        type: 'submenu',
        name: 'deploy',
        icon: 'gear-b',
        text: 'APP配置',
        childens: [
            {
                name: 'homeconf',
                text: '首页配置',
                path: '/system/home'
            },
            {
                name: 'recoconf',
                text: '推荐配置',
                path: '/system/recom',
                ison: ['/system/recmodify']
            },
            {
                name: 'startconf',
                text: '启动页管理',
                path: '/system/startup'
            }
        ]
    },
    {
        type: 'submenu',
        name: 'resource',
        icon: 'images',
        text: '资源管理',
        childens: [
            {
                name: 'imageup',
                text: '上传图片',
                path: '/resource/upload'
            },
            {
                name: 'imageres',
                text: '图片资源',
                path: '/resource/imglist'
            }
        ]
    }
];

export default menus;