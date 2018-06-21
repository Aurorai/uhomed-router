<template>
    <div>
        <Breadcrumb>
            <Breadcrumb-item>首页</Breadcrumb-item>
            <Breadcrumb-item>
                <router-link to="/group">分组</router-link>
            </Breadcrumb-item>
            <Breadcrumb-item>
                <a @click="goMethodList()">方法列表</a>
            </Breadcrumb-item>
            <Breadcrumb-item>方法详情</Breadcrumb-item>
        </Breadcrumb>
        <br/>

        <Modal v-model="desc" title="参数描述" @on-ok="ok" @on-cancel="cancel">
            <Input v-model="paramDesc" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..."></Input>
        </Modal>

        <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100">
            <FormItem label="方法code" prop="apiMethodCode">
                <Input v-model="formValidate.apiMethodCode" placeholder="xkhstar" class="col-4"></Input>
            </FormItem>
            <FormItem label="方法名" prop="apiMethodName">
                <Input v-model="formValidate.apiMethodName" placeholder="登录接口" class="col-3"></Input>
            </FormItem>
            <FormItem label="版本号" prop="apiMethodVersion">
                <Input v-model="formValidate.apiMethodVersion" placeholder="接口版本号" class="col-2"></Input>
            </FormItem>
            <FormItem label="接口类型" prop="type">
                <Select v-model="formValidate.type" placeholder="请选择" class="col-1">
                    <Option value="DUBBO">DUBBO</Option>
                    <Option value="HTTP">HTTP</Option>
                    <Option value="HTTPS">HTTPS</Option>
                </Select>
            </FormItem>
            <FormItem label="接口状态" prop="status">
                <Select v-model="formValidate.status" placeholder="请选择" class="col-1">
                    <Option value="Y">online</Option>
                    <Option value="N">offline</Option>
                </Select>
            </FormItem>
            <FormItem label="sso校验" prop="verifiSso">
                <Select v-model="formValidate.verifiSso" placeholder="请选择" class="col-1">
                    <Option value="Y">是</Option>
                    <Option value="N">否</Option>
                </Select>
            </FormItem>
            <FormItem label="调用方式" prop="mode">
                <Select v-model="formValidate.mode" placeholder="请选择" class="col-1">
                    <Option value="GET">GET</Option>
                    <Option value="POST">POST</Option>
                </Select>
            </FormItem>
            <FormItem label="方法描述" prop="methodDesc">
                <Input v-model="formValidate.methodDesc" type="textarea" :autosize="{minRows: 2,maxRows: 5}" placeholder="请输入..." class="col-4"></Input>
            </FormItem>
            <div v-if="formValidate.type == 'DUBBO'">
                <FormItem label="classPath" prop="classPath">
                    <Input v-model="formValidate.classPath" placeholder="com.xkhstar.xxx.xxx" class="col-5"></Input>
                </FormItem>
                <FormItem label="methodName" prop="methodName">
                    <Input v-model="formValidate.methodName" placeholder="test" class="col-3"></Input>
                </FormItem>
            </div>
            <div v-else-if="formValidate.type == 'HTTP'">
                http
            </div>
            <div v-else-if="formValidate.type == 'HTTPS'">
                https
            </div>
            <FormItem label="添加参数">
                <template>
                    <Button @click="addParam()">
                        <Icon type="android-add"></Icon>
                    </Button>
                </template>
            </FormItem>
            <el-table :data="datas" border class="space iel-table" style="width: 100%">
                <el-table-column label="code">
                    <template scope="param">
                        <Input v-model="param.row.paramCode" placeholder="请输入..." class="col-1"></Input>
                    </template>
                </el-table-column>
                <el-table-column label="名称">
                    <template scope="param">
                        <Input v-model="param.row.paramName" placeholder="请输入..." class="col-1"></Input>
                    </template>
                </el-table-column>
                <el-table-column label="类型" width="180">
                    <template scope="param">
                        <Select v-model="param.row.paramType" class="col-1" :transfer='true'>
                            <Option value="String">String</Option>
                            <Option value="Int">Int</Option>
                            <Option value="Float">Float</Option>
                            <Option value="Double">Double</Option>
                            <Option value="Long">Long</Option>
                            <Option value="Boolean">Boolean</Option>
                            <Option value="Date">Date</Option>
                            <Option value="List">List</Option>
                            <Option value="Object">Object</Option>
                        </Select>
                    </template>
                </el-table-column>
                <el-table-column label="来源" width="180">
                    <template scope="param">
                        <Select v-model="param.row.resource" class="col-1" :transfer='true'>
                            <Option value="BIZ_PARAMS">biaParams</Option>
                            <Option value="REQUEST_BODY">RequestBody</Option>
                            <Option value="URL">URL</Option>
                            <Option value="HEADERS">header</Option>
                        </Select>
                    </template>
                </el-table-column>
                <el-table-column label="是否必传" width="100">
                    <template scope="param">
                        <Select v-model="param.row.paramRequire" class="col-05" style="width:100%">
                            <Option value="Y">是</Option>
                            <Option value="N">否</Option>
                        </Select>
                    </template>
                </el-table-column>
                <el-table-column label="长度" width="150">
                    <template scope="param">
                        <Input v-model="param.row.minLength" placeholder="请输入..." class="col-05"></Input>
                        <Input v-model="param.row.length" placeholder="请输入..." class="col-05"></Input>
                    </template>
                </el-table-column>
                <el-table-column label="默认值">
                    <template scope="param">
                        <Input v-model="param.row.defaultValue" placeholder="请输入..." class="col-1"></Input>
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="240px">
                    <template scope="param">
                        <ButtonGroup>
                            <Button @click="showDescText(param.$index)">
                                <Icon type="document-text"></Icon>
                            </Button>
                            <Button :disabled="param.$index == 0" type="primary" @click="up(param.$index)">
                                <Icon type="arrow-up-a"></Icon>
                            </Button>
                            <Button :disabled="(param.$index + 1) == datas.length" type="primary" @click="down(param.$index)">
                                <Icon type="arrow-down-a"></Icon>
                            </Button>
                            <Button @click="addParam(param.$index)">
                                <Icon type="android-add"></Icon>
                            </Button>
                            <Button @click="deleteParam(param.$index)">
                                <Icon type="trash-a"></Icon>
                            </Button>
                        </ButtonGroup>
                    </template>
                </el-table-column>

            </el-table>
            <br/>
            <FormItem>
                <Button type="primary" @click="handleSubmit('formValidate')">保存</Button>
            </FormItem>
        </Form>

    </div>
</template>

<script>
import MixSearch from '@/mixins/mix-search';
import { CREATE_METHOD_DUBBO, METHOD_INFO, UPDATE_METHOD_DUBBO } from '@/service/gateway';
export default {
    data() {
        return {
            desc: false,
            paramDesc: '',
            datas: [],
            index: 0,
            count: 0,
            id: null,
            descValue: '',
            formValidate: {
                apiMethodCode: null,
                apiMethodName: null,
                apiMethodVersion: null,
                status: null,
                verifiSso: null,
                mode: null,
                methodDesc: null,
                classPath: null,
                methodName: null,
                type: null,
                groupCode: this.$route.query.groupCode
            },
            ruleValidate: {
                apiMethodCode: [
                    { required: true, message: '方法code不能为空', trigger: 'blur' }
                ],
                apiMethodName: [
                    { required: true, message: '方法名不能为空', trigger: 'blur' }
                ],
                apiMethodVersion: [
                    { required: true, message: '版本号不能为空', trigger: 'blur' }
                ],
                status: [
                    { required: true, message: '接口状态不能为空', trigger: 'blur' }
                ],
                verifiSso: [
                    { required: true, message: 'sso校验不能为空', trigger: 'blur' }
                ],
                mode: [
                    { required: true, message: '请求方式不能为空', trigger: 'blur' }
                ],
                methodDesc: [
                    { required: true, message: '方法描述不能为空', trigger: 'blur' }
                ],
                type: [
                    { required: true, message: '接口类型不能为空', trigger: 'blur' }
                ],
                classPath: [
                    { required: true, message: 'classPath不能为空', trigger: 'blur' }
                ],
                methodName: [
                    { required: true, message: 'methodName不能为空', trigger: 'blur' }
                ]
            },
            formDynamic: {
                items: [
                    {
                        value: ''
                    }
                ]
            }
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
        showDescText(i){
            this.paramDesc = this.datas[i].paramDesc;
            this.desc = true;
            this.index = i;
        },
        ok() {
            this.datas[this.index].paramDesc = this.paramDesc;
        },
        cancel() {
            this.paramDesc = '';
            this.index = 0;
        },
        goMethodList() {
            this.$router.go(-1);
        },
        up(index) {
            let curr = this.datas.splice(index, 1);
            this.datas.splice(index - 1, 0, curr[0]);
        },
        down(index) {
            let curr = this.datas.splice(index, 1);
            this.datas.splice(index + 1, 0, curr[0]);
        },
        addParam(index) {
            if (index != null) {
                this.datas.splice(index + 1, 0, {
                    length: 0,
                    minLength: 0
                });
            } else {
                this.datas.splice(this.datas.length + 1, 0, {
                    length: 0,
                    minLength: 0
                });
            }
        },
        deleteParam(index) {
            this.datas.splice(index, 1);
        },
        handleSubmit(name) {
            this.$refs[name].validate(async (valid) => {
                if (valid) {
                    let params = {
                        apiMethodCode: this.formValidate.apiMethodCode,
                        apiMethodName: this.formValidate.apiMethodName,
                        apiMethodVersion: this.formValidate.apiMethodVersion,
                        status: this.formValidate.status,
                        verifiSso: this.formValidate.verifiSso,
                        mode: this.formValidate.mode,
                        methodDesc: this.formValidate.methodDesc,
                        classPath: this.formValidate.classPath,
                        methodName: this.formValidate.methodName,
                        groupCode: this.formValidate.groupCode,
                        params: JSON.stringify(this.datas)
                    };
                    let response = null;
                    if (this.$route.path == '/group/method/updateMethod') {
                        params.id = this.$route.query.id;
                        response = await UPDATE_METHOD_DUBBO(params);
                    } else {
                        response = await CREATE_METHOD_DUBBO(params);
                    }

                    if (!response) { return false; }

                    if (response.success) {
                        this.$Message.success(response.message);
                        this.$router.go(-1);
                    } else {
                        this.$Message.error(response.message);
                    }

                } else {
                    this.$Message.error('请根据错误提示修改！');
                }
            })
        },

        handleAdd() {
            this.formDynamic.items.push({
                value: ''
            });
        },
        handleRemove(index) {
            this.formDynamic.items.splice(index, 1);
        },
        async fatchData() {
            let response = await METHOD_INFO({
                id: this.id
            });
            if (!response) { return false; };
            let data = response.data;
            Object.assign(this.formValidate, data);

            if (data.type == 'DUBBO') {
                this.formValidate.classPath = response.dubbo.classPath;
                this.formValidate.methodName = response.dubbo.methodName;
                this.datas = response.params;
            }
        }
    }
}


</script>