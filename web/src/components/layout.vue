<template>
    <div class="layout">
        <!-- 一级导航 -->
        <div class="layout-header">
            <Menu mode="horizontal" theme="dark" active-name="1">
                <div class="layout-logo">
                    <router-link to="/home">
                        <img src="../assets/logo.svg">
                    </router-link>
                </div>
                <!-- <div class="layout-nav">
                    <Menu-item name="1">
                        <router-link to="/">
                            <Icon type="android-home"></Icon>
                            首页
                        </router-link>
                    </Menu-item>
                    <Menu-item name="1">
                        <router-link to="/article/mark">
                            <Icon type="ios-navigate"></Icon>
                            发布笔记
                        </router-link>
                    </Menu-item>
                    <Menu-item name="2">
                        <router-link to="/users/users">
                            <Icon type="ios-keypad"></Icon>
                            用户列表
                        </router-link>
                    </Menu-item>
                    <Menu-item name="4">
                        <a href="javascript:;" @click="logoutHandle">
                            <Icon type="ios-paper"></Icon>
                            退出
                        </a>
                    </Menu-item>
                </div> -->
            </Menu>
        </div>
        
        <!-- 内容 -->
        <div class="layout-content">

            <!-- 侧边栏 -->
            <div class="layout-menu">
                <Navigate ref="navi"></Navigate>
            </div>
            
            <!-- 二级路由 -->
            <div class="layout-body">
                <router-view></router-view>
            </div>

        </div>

        <!-- 回到顶部 -->
        <Back-top :height="100"></Back-top>
    </div>
</template>

<script>
// import Loading from '@/components/loading';
import Navigate from '@/components/navigate';
import { mapState, mapMutations } from 'vuex';
import { DO_LOGOUT } from '@/libs/session';
export default{
    data(){
        return {}
    },
    computed: {
        ...mapState({
            loading: state=> state.layout.loading
        })
    },
    beforeRouteUpdate(to, from, next){
        next();
    },
    mounted(){
    },
    methods:{
        logoutHandle(){

            // 退出操作
            DO_LOGOUT();
            this.$router.go(0);
        }
    },
    components: {
        Navigate
    }
}
</script>

<style lang="less">
@import '../styles/core.less';
@header-height: 60px;
.layout{
    min-width: 1100px;
    background: #f5f7f9;
}

// 导航
.layout-header{
    position: absolute;
    width: 100%;
    min-width: 1100px;
    .layout-logo{
        width: 100px;
        height: 50px;
        position: relative;
        top: 8px;
        left: 50px;
        float: left;
        img{
            display: block;
            width: 41.5px;
            height: 41.5px;
        }
    }
    .layout-nav{
        width: 420px;
        margin: 0 auto;
        a{
            color: rgba(255, 255, 255, 0.7);
        }
    }
}

// 内容
.layout-content{
    .mix-flex-box();
    min-height: 100vh;
    overflow: hidden;
    width: auto;
    box-sizing: border-box;
    padding: 10px;
    padding-top: @header-height+10px;
    .layout-menu{
        min-width: 150px;
        width: 150px;
        background: #fff;
        &>.ivu-menu-light{
            background: none;
        }
        &>.ivu-menu-vertical.ivu-menu-light:after{
            background: none;
        }
        border-radius: 5px 0 0 5px;
    }
    .layout-body{
        .mix-flex();
        position: relative;
        display: block;
        background: #fff;
        border-left: 1px solid #dddee1;
        margin-left: -1px;
        width: 100%;
        border-radius: 0 5px 5px 0;
        box-sizing: border-box;
        padding: 10px;
    }
}
</style>