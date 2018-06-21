<template>
    <div>
        <Breadcrumb>
            <Breadcrumb-item>首页</Breadcrumb-item>
            <Breadcrumb-item><router-link to="/group">分组列表</router-link></Breadcrumb-item>
            <Breadcrumb-item>方法列表</Breadcrumb-item>
        </Breadcrumb>

        <!-- 搜索 -->
        <Form class="space" inline :show-message="false" label-position="top">
            <Form-item label="搜索">
                <Input placeholder="模糊搜索" v-model="params.likeApiMethodCode"></Input>
            </Form-item>
            <Form-item label="名称">
                <Input placeholder="模糊搜索" v-model="params.likeApiMethodName"></Input>
            </Form-item>
            <FormItem label="状态">
                <Select v-model="params.status" placeholder="请选择">
                    <Option value="">请选择</Option>
                    <Option value="Y">online</Option>
                    <Option value="N">offline</Option>
                </Select>
            </FormItem>

            <Form-item label="操作">
                <Button type="primary" @click="handleSearch">搜索</Button>
                <!-- <Button type="primary">新增</Button> -->
                <router-link :to="nextPath">
                    <Button type="primary">新增</Button>
                </router-link>
            </Form-item>
        </Form>

        <!-- 表格 -->
        <Table border :columns="columns" :data="datas"></Table>
        <!-- 分页 -->
        <Page class="space" @on-change="handlePage" :total="count-0" :current="params.currPage-0" show-elevator show-total></Page>

    </div>
</template>

<script>
import MixSearch from '@/mixins/mix-search';
import { GET_METHOD_LIST, DELETE_METHOD, UPDATE_METHOD_DUBBO, TEST_METHOD } from '@/service/gateway';
export default {
    data() {
        return {
            columns: [
                {
                    type: 'index',
                    width: 60
                },
                {
                    title: '方法名',
                    render(h, params) {
                        let Div = {
                            template: `
                                <Tooltip>
                                    <div slot="content" style="white-space: normal;">
                                        ${params.row.apiMethodCode}
                                    </div>
                                    <router-link :to="{path:'/group/method/updateMethod',query:{id:'${params.row.id}'}}">${params.row.apiMethodCode}</router-link>
                                </Tooltip>
                            `
                        }
                        return h(Div);
                    }
                },
                {
                    title: '版本号',
                    key: 'apiMethodVersion'
                },
                {
                    title: '名称',
                    key: 'apiMethodName'
                },
                {
                    title: 'sso验证',
                    width: 90,
                    key: 'verifiSsoCH'
                },

                {
                    title: '调用类型',
                    width: 90,
                    key: 'mode'
                },
                {
                    title: '参数',
                    width: 90,
                    render(h, params) {
                        let Div = {
                            template: `<router-link :to="{path:'/group/method/updateMethod',query:{id:'${params.row.id}'}}">${params.row.paramCount}</router-link>`
                        }
                        return h(Div);
                    }
                },
                {
                    title: '状态',
                    width: 120,
                    render(h, params) {
                        let html = null;
                        const row = params.row;
                        const color = params.row.status === 'Y' ? 'green' : 'red';
                        const text = params.row.status === 'Y' ? 'online' : 'offline';

                        return h('Tag', {
                            props: {
                                type: 'dot',
                                color: color
                            }
                        }, text);
                        return h(html);
                    }
                },
                {
                    title: '操作',
                    width: 280,
                    render: (h, params) => {
                        let that = this;
                        let html = `<div>`;
                        if (params.row.status == 'Y') {
                            html += `<Button type="primary" @click="testMethod('${params.row.id}')">测试</Button> <router-link :to="{path:'/group/method/develop',query:{id:'${params.row.id}'}}"><Button type="warning">调试</Button></router-link> <Button type="error" @click="offlineMethod('${params.row.id}')">下线</Button> <router-link :to="{path:'/group/method/copyMethod',query:{id:'${params.row.id}'}}"><Button>copy</Button></router-link>`;
                        } else {
                            html += `<Button type="primary" @click="onlineMethod('${params.row.id}')">上线</Button> <Button type="error" @click="delMethod('${params.row.id}')">删除</Button> <router-link :to="{path:'/group/method/copyMethod',query:{id:'${params.row.id}'}}"><Button>copy</Button></router-link>`;
                        }
                        html += `</div>`;

                        let Div = {
                            template: html,
                            methods: {
                                delMethod(id) {
                                    that.delMethod(id);
                                },
                                onlineMethod(id) {
                                    that.updateMethodStatus(id, 'Y');
                                },
                                offlineMethod(id) {
                                    that.updateMethodStatus(id, 'N');
                                },
                                testMethod(id) {
                                    that.testMethod(id);
                                }
                            }
                        }

                        return h(Div);
                    }
                }
            ],
            datas: [],
            count: 0,

            // 查询条件
            params: {
                code: null,
                pageSize: 10,
                currPage: this.$route.query.currPage || 1
            },

            nextPath: {
                path: '/group/method/addMethod',
                query: {
                    groupCode: this.$route.query.code
                }
            }
        }
    },
    beforeMount() {
        if( this.$route.query.currPage ){
            this.facth();
        }
    },
    methods: {
        async facth() {
            this.params.code = this.$route.query.code;
            let params = this.params;
            let response = await GET_METHOD_LIST(params);
            if (!response) { return false; }
            this.datas = response.data;
            this.count = response.count;
        },

        updateMethodStatus(id, status) {
            let ch = status == 'Y' ? '上线' : '下线';
            this.$Modal.confirm({
                title: '确认',
                content: '<p>确定' + ch + '该方法吗？</p>',
                onOk: async () => {
                    let params = {
                        id: id,
                        status: status
                    };
                    let response = await UPDATE_METHOD_DUBBO(params);
                    console.log(response);
                    if (!response) { return false; }
                    if (response.success) {
                        this.$Message.success(response.message);
                    } else {
                        this.$Message.error({
                            content: response.message,
                            duration: 5
                        });
                    }
                    this.facth();
                }
            });
        },
        async testMethod(id) {

            let params = {
                id: id
            };
            let response = await TEST_METHOD(params);
            if (response.success) {
                this.$Message.success({
                    content: response.message,
                    duration: 5
                });
            } else {
                this.$Message.error({
                    content: response.message,
                    duration: 5
                });
            }
        },

        delMethod(id) {
            this.$Modal.confirm({
                title: '确认',
                content: '<p>确定删除该方法吗？</p>',
                onOk: async () => {
                    let params = {
                        id: id
                    };
                    let response = await DELETE_METHOD(params);
                    if (!response) { return false; }
                    if (response.success) {
                        this.$Message.success(response.message);
                    } else {
                        this.$Message.error(response.message);
                    }
                    this.facth();
                }
            });
        },
        handleSearch() {
            this.query = Object.assign({}, this.params, { currPage: 1 });
        },
        handlePage(number) {
            this.query = { currPage: number - 0 };
        }
    },
    watch: {
        $route(curr, old) {
            this.facth();
        }
    },
    mixins: [MixSearch]
}
</script>