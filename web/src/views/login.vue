<template>
    <div class="login">
        <!-- 内容 -->
        <div id="content">
            <div class="logo">
                <img src="../assets/logo.svg" alt="">
            </div>
            <div class="form">
                <Form
                    ref="datas" 
                    :model="datas" 
                    :rules="rules"
                >

                    <Form-item prop="account">
                        <Input 
                            v-model="datas.account" 
                            icon="android-person" 
                            placeholder="请输入用户名..." 
                            type="text" 
                            size="large"
                            @on-enter="handleSubmit('datas')"
                        ></Input>
                    </Form-item>

                    <Form-item prop="passwd">
                        <Input 
                            v-model="datas.passwd" 
                            icon="android-lock" 
                            placeholder="请输入密码..." 
                            type="password" 
                            size="large"
                            @on-enter="handleSubmit('datas')"
                        ></Input>
                    </Form-item>

                    <Button 
                        type="primary" 
                        size="large" 
                        long 
                        @click="handleSubmit('datas')"
                    >登 录</Button>

                </Form>
            </div>
        </div>

        <!-- 背景 -->
        <div id="particles-js"></div>
    </div>
</template>

<script>
import ParticleConfig from '@/config/particles';
require('particles.js');

// 当前页面所需请求
import { GET_LOGIN } from '@/service/gateway';

export default{
    data(){
        return {
            datas:{
                account: '',
                passwd: ''
            },
            rules:{
                account: [
                    { required: true, message: '请填写用户名', trigger: 'blur' }
                ],
                passwd: [
                    { required: true, message: '请填写密码', trigger: 'blur' },
                    { type: 'string', min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
                ]
            }
        }
    },
    mounted(){
        this.$nextTick(() => {
            this.ParticleJS();
        });
    },
    methods:{
        ParticleJS() {
            particlesJS('particles-js', ParticleConfig);
        },
        handleSubmit(name) {
            this.$refs[name].validate((valid) => {
                if (valid) {
                    this.$Message.success('验证成功!');
                    this.login();
                } else {
                    this.$Message.error('验证失败!');
                }
            })
        },
        async login(){
            let response = await GET_LOGIN(this.datas);
            console.log(response);
            let callback = this.$route.query.callback || '/';
            if( !response ){ return; }
            if( response.success == true ){
                this.$router.push({path: callback});
            }
        }
    }
}
</script>

<style scoped lang="less">
@import '~styles/core.less';
@background-color: #e9eaec;
.login{
    height: 100vh;
    overflow: hidden;
}
#content{
    position: relative;
    z-index: 99;
    margin: 0 auto;
    margin-top: 100px;
    width: 300px;
    text-align: center;
    .logo{
        height: 83px;
    }
    .logo img{
        display: inline-block;
        width: 83px;
        height: 83px;
    }
    .form{
        margin-top: 50px;
        display: inline-block;
        background: @background-color;
        padding: 20px;
        box-sizing: border-box;
        border-radius: 5px;
        width: 300px;
    }
}
#particles-js{
    box-sizing: border-box;
    overflow: hidden;
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 1;
}
</style>