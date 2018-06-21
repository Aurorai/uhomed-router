<template>
    <div>
        <Breadcrumb>
            <Breadcrumb-item>首页</Breadcrumb-item>
            <Breadcrumb-item>类别</Breadcrumb-item>
        </Breadcrumb>
        <br/>
        <Form ref="formValidate" :model="formValidate" :rules="ruleValidate" :label-width="100">
            <FormItem label="分组code" prop="code">
                <Input v-model="formValidate.code" placeholder="xkhstar" class="col-2"></Input>
            </FormItem>
            <FormItem label="分组名称" prop="name">
                <Input v-model="formValidate.name" placeholder="校开花" class="col-1"></Input>
            </FormItem>
            <FormItem>
                <Button type="primary" @click="handleSubmit('formValidate')">提交</Button>
            </FormItem>
        </Form>
    </div>
</template>

<script>
    import MixSearch from '@/mixins/mix-search';
    import { CREATE_GROUP } from '@/service/gateway';
    export default {
        data () {
            return {
                formValidate: {
                    name: '',
                    code: ''
                },
                ruleValidate: {
                    code: [
                        { required: true, message: '分组code不能为空！', trigger: 'blur' }
                    ],
                    name: [
                        { required: true, message: '名称不能为空！', trigger: 'blur' }
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
        methods: {
             handleSubmit (name) {
                this.$refs[name].validate(async (valid) => {
                    if (valid) {
                        let params = {
                            code: this.formValidate.code,
                            name: this.formValidate.name
                        };
                        let response = await CREATE_GROUP(params);
                        if( !response ){return false;}

                        if(response.success){
                            this.$Message.success(response.message);
                            this.$router.go(-1);
                        }else {
                            this.$Message.error(response.message);
                        }
                        
                        // this.$router.push({
                        //     path : '/group'
                        // });
                    } else {
                        this.$Message.error('请根据错误提示修改！');
                    }
                })
            },
            // createGroup(code){
            //     this.$Modal.confirm({
            //         title: '确认',
            //         content: '<p>确定删除该方法吗？</p>',
            //         onOk: async () => {
            //             let params = {
            //                 code: code
            //             };
            //             let response = await DELETE_GROUP(params);
            //             if( !response ){return false;}
            //             this.$Modal.remove();
            //             this.$Message.info(response.message);
            //             this.facth();
            //         }
            //     });
            // },
            handleAdd () {
                this.formDynamic.items.push({
                    value: ''
                });
            },
            handleRemove (index) {
                this.formDynamic.items.splice(index, 1);
            }
        }
    }


</script>