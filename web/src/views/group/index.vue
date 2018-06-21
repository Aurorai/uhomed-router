<template>
    <div>
        <Breadcrumb>
            <Breadcrumb-item>首页</Breadcrumb-item>
            <Breadcrumb-item>分组列表</Breadcrumb-item>
        </Breadcrumb>

        <!-- 搜索 -->
        <Form class="space" inline :show-message="false" label-position="top">
            <Form-item label="分组code">
                <Input placeholder="模糊搜索" v-model="params.likeCode"></Input>
            </Form-item>
            <Form-item label="名称">
                <Input placeholder="模糊搜索" v-model="params.linkName"></Input>
            </Form-item>

            <Form-item label="操作">
                <Button type="primary" @click="handleSearch">搜索</Button>
                <!-- <Button type="primary">新增</Button> -->
                <router-link :to="{path:'/group/addGroup'}">
                    <Button type="primary" :to="{path:'/method/addParam'}">新增</Button>
                </router-link>
            </Form-item>
        </Form>

        <!-- 表格 -->
        <el-table :data="datas" border class="space iel-table" style="width: 100%">
            <el-table-column label="node">
                <template scope="scope">
                    <Tooltip>
                        <div slot="content" style="white-space: normal;">
                            {{scope.row.code}}
                        </div>
                        <router-link :to="{path:'/group/method',query:{code:scope.row.code}}">{{scope.row.code}}</router-link>
                    </Tooltip>
                </template>
            </el-table-column>
            <el-table-column prop="name" label="名称">
            </el-table-column>
            <el-table-column prop="methodCount" label="方法数量" width="180">
            </el-table-column>
            <el-table-column label="操作">
                <template scope="scope">
                    <Button type="error" @click="delGroup(scope.row.code)">删除</Button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <Page class="space" @on-change="handlePage" :total="count-0" :current="params.currPage-0" show-elevator show-total></Page>

    </div>
</template>

<script>
import MixSearch from '@/mixins/mix-search';
import { GET_GROUP_LIST, DELETE_GROUP } from '@/service/gateway';
export default {
    data() {
        return {
            datas: [],
            count: 0,
            // 查询条件
            params: {
                pageSize: 10,
                currPage: 1,
                currPage: this.$route.query.currPage-0 || 1
            }
        }
    },
    beforeMount() {
        this.facth();
    },
    methods: {
        async facth() {
            let params = this.params;
            let response = await GET_GROUP_LIST(params);
            if (!response) { return false; }
            this.datas = response.data;
            this.count = response.count;
        },


        delGroup(code) {
            this.$Modal.confirm({
                title: '确认',
                content: '<p>确定删除该方法吗？</p>',
                onOk: async () => {
                    let params = {
                        code: code
                    };
                    let response = await DELETE_GROUP(params);
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
        // $route(curr, old) {
        //     let query = old.query;
        //     if( query.keys.length>0 ){
        //         this.fatch();
        //     }
        // }
        $route(curr, old) {
            this.facth();
        }
    },
    mixins: [MixSearch]
}
</script>