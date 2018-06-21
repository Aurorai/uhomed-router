<template>
    <div>
        <Breadcrumb>
            <Breadcrumb-item>首页</Breadcrumb-item>
            <Breadcrumb-item><router-link to="/group">分组管理</router-link></Breadcrumb-item>
            <Breadcrumb-item><a @click="goMethodList()">方法列表</a></Breadcrumb-item>
            <Breadcrumb-item>调试</Breadcrumb-item>
        </Breadcrumb>
        <br/>
        <template>
            <Form :label-width="100" ref="formValidate" :model="formValidate" :rules="ruleValidate">
                <Card >
                    <p slot="title">{{info.apiMethodName}}</p>
                    <p>{{info.methodDesc}}</p>
                </Card>
                <br/>
                <Row>
                    <Col span="10">
                        <Card>
                            <p slot="title">调用时生成的参数：</p>
                            <FormItem v-if="info.verifiSso" label="sso" prop="sso">
                                <Input v-model="formValidate.sso" required class="col-3"></Input>
                                <Tooltip content="sso" placement="bottom">
                                    <Icon type="help"></Icon>
                                </Tooltip>
                            </FormItem>
                            <FormItem v-if="info.verifiSso" label="直连ip" prop="router">
                                <Input v-model="formValidate.router" required class="col-2"></Input>
                                <Tooltip content="router" placement="bottom">
                                    <Icon type="help"></Icon>
                                </Tooltip>
                            </FormItem>
                            <FormItem v-for="param in datas" :key="param.id" :label="param.paramName" :prop="param.paramCode" >
                                <Input :placeholder="param.paramType" v-model="formValidate[param.paramCode]" class="col-2"></Input>
                                <Tooltip placement="bottom">
                                    <div slot="content" style="white-space: normal;">
                                        {{param.paramName}} {{param.paramType}} {{param.paramDesc}}
                                    </div>
                                    <Icon type="help"></Icon>
                                </Tooltip>
                            </FormItem>
                            <FormItem>
                                <Button type="primary" @click="handleSubmit()">调试</Button>
                            </FormItem>
                            <FormItem label="method" >
                                <Input v-model="info.apiMethodCode" disabled class="col-3" ></Input>
                                <Tooltip content="调用接口名" placement="bottom">
                                    <Icon type="help"></Icon>
                                </Tooltip>
                            </FormItem>
                            <FormItem label="version" >
                                <Input v-model="info.apiMethodVersion" disabled class="col-1" ></Input>
                                <Tooltip content="接口版本号" placement="bottom">
                                    <Icon type="help"></Icon>
                                </Tooltip>
                            </FormItem>

                            <FormItem label="format" >
                                <Input disabled  v-model="formValidate.format" class="col-1" ></Input>
                                <Tooltip content="返回值类型" placement="bottom">
                                    <Icon type="help"></Icon>
                                </Tooltip>
                            </FormItem>

                            <FormItem label="mode" >
                                <Input disabled :value="info.mode" class="col-1" ></Input>
                                <Tooltip content="请求方式" placement="bottom">
                                    <Icon type="help"></Icon>
                                </Tooltip>
                            </FormItem>

                            <FormItem label="timestamp" >
                                <Input disabled :value="timestamp" class="col-2" ></Input>
                                <Tooltip content="时间戳" placement="bottom">
                                    <Icon type="help"></Icon>
                                </Tooltip>
                            </FormItem>

                        </Card>
                    </Col>
                    <Col span="12" offset="2">
                        <Card >
                            <p slot="title">返回结果：</p>
                            <pre ref="code"></pre>
                        </Card>
                    </Col>
                </Row>
            </Form>
        </template>

    </div>
</template>

<script>
import '@/vendors/highlight';
const hljs = window.hljs;
import MixSearch from '@/mixins/mix-search';
import { METHOD_PARAM_LIST,METHOD_GATEWAY_GET,METHOD_GATEWAY_POST } from '@/service/gateway';
export default {
    data() {
        return {
            formValidate:{
                format:'JSON'
            },
            datas:[],
            count:0,
            id: null,
            info : {},
            result : null,
            timestamp: new Date().getTime() * 1000,
            ruleValidate:{}
        }
    },
    mounted() {
        let id = this.$route.query.id;
        if (id) {
            this.id = id;
            this.fatchData();
        }
    },
    methods: {
        goMethodList(){
            this.$router.go(-1);
        },
        async handleSubmit() {
            this.$refs['formValidate'].validate(async (valid) => {
                if(!valid){
                    this.$Message.error('表单验证失败!');
                    return;
                }
                let bizParams = {};
                this.datas.forEach(function(element) {
                    if((this.formValidate[element.paramCode] == '' || this.formValidate[element.paramCode] == null)){
                        return;
                    }
                    bizParams[element.paramCode] = this.formValidate[element.paramCode];
                }, this);

                let params = {
                    method: this.info.apiMethodCode,
                    version: this.info.apiMethodVersion,
                    format: this.formValidate.format,
                    timestamp : this.timestamp,
                    bizParams:JSON.stringify(bizParams),
                    sso : this.formValidate.sso,
                    router:this.formValidate.router
                }

                let response = null;
                if(this.info.mode == 'POST'){
                    response = await METHOD_GATEWAY_POST(params);
                }else {
                    response = await METHOD_GATEWAY_GET(params);
                }

                if (response == null) { return false; }
                this.result = response;
                
                this.$nextTick(()=>{
                    let div = document.createElement('code');
                    div.setAttribute('class', 'json');
                    div.innerHTML =JSON.stringify(this.result, null, 4);
                    hljs.highlightBlock(div);
                    this.$refs.code.innerHTML = div.outerHTML;
                });
            })
        },
        handleReset(name) {
            this.$refs[name].resetFields();
        },
        async fatchData() {
            let response = await METHOD_PARAM_LIST({
                methodId: this.id
            });
            if (!response) { return false; };
            let data = response.data;
            this.datas = data;
            this.info = response.info;

            this.datas.forEach(function(element) {
                if(element.defaultValue){
                    this.formValidate[element.paramCode] = element.defaultValue;
                }else {
                    this.formValidate[element.paramCode] = '';
                }
                if(element.paramRequire =='Y'){
                    this.ruleValidate[element.paramCode] = [{ required: true, message: element.paramName + '不能为空', trigger: 'blur' }];
                }
            }, this);
            if(this.info.verifiSso == 'Y'){
                this.ruleValidate.sso = [{ required: true, message: 'sso不能为空', trigger: 'blur' }]
            }
            this.formValidate['sso'] = 'uhomed-entrance-test';
        }
    }
}


</script>