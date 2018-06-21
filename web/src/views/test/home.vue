<template>
    <div>
        <Breadcrumb>
            <Breadcrumb-item>首页</Breadcrumb-item>
            <Breadcrumb-item>类别</Breadcrumb-item>
        </Breadcrumb>
        <br/>
        <Form ref="data" :label-width="100">

            <Row>
                <Col span="11">
                    <Card>
                        <p slot="title">数据</p>
                        <FormItem label="房租（年租）">
                            <Input v-model="data.rent"  class="col-2">
                                <span slot="append">万</span>
                            </Input>
                        </FormItem>
                        <FormItem label="单价" >
                            <Input v-model="data.dayPrice"  class="col-2">
                                <span slot="append">日/元</span>
                            </Input>
                        </FormItem>
                        <FormItem label="平方" >
                            <Input v-model="data.square"  class="col-2">
                                <span slot="append">²</span>
                            </Input>
                        </FormItem>
                        
                    </Card>
                </Col>
                <Col span="11" offset="2">
                    <Card>
                        <p slot="title">分析结果</p>
                        <p>单价 元/平方:      {{result.dayPrice.toFixed(2)}}</p>
                        <p>月租:      {{(result.monthRent / 10000).toFixed(2)}}</p>
                        <p>年租:      {{(result.yearRent / 10000).toFixed(2)}}</p>
                        
                        <p></p>
                    </Card>
                </Col>
            </Row>
        </Form>
    </div>
</template>

<script>
    import MixSearch from '@/mixins/mix-search';
    import { CREATE_GROUP } from '@/service/gateway';
    export default {
        data () {
            return {
                data: {
                    rent: 0,
                    dayPrice: 0,
                    square:0,
                },
                result: {
                    dayPrice:0,
                    yearRent:0,
                    monthRent:0,

                }
                
            }
        },
        watch : {
            data:{
                handler(curr,old){
                    this.data.rent -=0;
                    this.data.dayPrice -=0;
                    this.data.square -=0;

                    let rent = this.data.rent * 10000;
                    
                    if(rent && rent != 0 && this.data.square && this.data.square != 0){
                        this.result.dayPrice = rent / 365 / this.data.square;
                    }

                    if(this.data.dayPrice && this.data.dayPrice != 0 && this.data.square && this.data.square != 0){
                        this.result.yearRent = this.data.dayPrice * 365 * this.data.square;
                        this.result.monthRent = this.result.yearRent / 365 * 30;
                    }
                    
                    
                
                },
                deep: true
            }
        }
    }


</script>