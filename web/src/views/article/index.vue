<template>
    <div>
        <Breadcrumb>
            <Breadcrumb-item>首页</Breadcrumb-item>
            <Breadcrumb-item>文章列表</Breadcrumb-item>
        </Breadcrumb>

        <!-- 搜索 -->
        <Form class="space" inline :show-message="false"  label-position="top">
            <Form-item label="搜索">
                <Input placeholder="模糊搜索"></Input>
            </Form-item>
            <Form-item label="性别" prop="sex">
                <Select placeholder="性别" style="width:60px;">
                    <Option value="all">所有</Option>
                    <Option value="beijing">男</Option>
                    <Option value="shanghai">女</Option>
                </Select>
            </Form-item>
            <Form-item label="来源" prop="come">
                <Select placeholder="渠道" style="width: 200px;">
                    <Option value="all">所有</Option>
                    <Option value="beijing">山东大学代理渠道</Option>
                    <Option value="zhejiang">浙江大学代理渠道</Option>
                    <Option value="shanghai">上海交大渠道</Option>
                </Select>
            </Form-item>
            <Form-item label="选择日期" style="width:250px;">
                <Row>
                    <Col span="11">
                        <Form-item prop="date">
                            <Date-picker type="date" placeholder="开始日期"></Date-picker>
                        </Form-item>
                    </Col>
                    <Col span="2" style="text-align: center">-</Col>
                    <Col span="11">
                        <Form-item prop="time">
                            <Date-picker type="date" placeholder="结束日期"></Date-picker>
                        </Form-item>
                    </Col>
                </Row>
            </Form-item>

            <Form-item label="操作">
                <Button type="primary">搜索</Button>
                <Button type="primary">导出</Button>
            </Form-item>
        </Form>

        <!-- 表格 -->
        <Table :columns="columns" :data="datas"></Table>

        <!-- 分页 -->
        <Page class="space" 
            @on-change="handlePage" 
            :total="400" 
            show-elevator 
            show-total
        ></Page>

    </div>
</template>

<script>
import MixSearch from '@/mixins/mix-search';
import { GET_ARTICLE_ITEMS } from '@/service/cnode';
export default{
    data(){
        return{
            columns:[
                {
                    type: 'index',
                    width: 50
                },
                {
                    title: '标题',
                    render:(h, params)=>{
                        let title = params.row.title;
                        let Div = {
                            template: `
                                <div>
                                    {{'${title}' | cutstring(30)}}
                                </div>
                            `
                        }
                        return h(Div);
                    }
                },
                {
                    title: '文章类型',
                    width: 100,
                    key: 'tab'
                },
                {
                    title: '置顶',
                    width: 70,
                    key: 'top'
                },
                {
                    title: '回复',
                    width: 110,
                    key: 'reply_count'
                },
                {
                    title: '创建时间',
                    width: 200,
                    key: 'create_at'
                }
            ],
            datas: [],

            // 查询条件
            params:{
                page: 1,
                limit: 10,
                mdrender: false
            }
        }
    },
    beforeMount(){
        this.facth();
    },
    methods:{
        async facth(){
            let params = this.params;
            let response = await GET_ARTICLE_ITEMS(params);
            if( !response ){return false;}
            this.datas = response.data;
        },
        handlePage(number){
            this.query = {page: number-0};
        }
    },
    watch:{
        $route(){
            this.facth();
        }
    },
    mixins:[MixSearch]
}
</script>