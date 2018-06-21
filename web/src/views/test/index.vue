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
                        <FormItem label="转让费（万）" >
                            <Input v-model="data.deposit"  class="col-2">
                                <span slot="append">万</span>
                            </Input>
                        </FormItem>
                        <FormItem label="客单价">
                            <Input v-model="data.price"  class="col-2">
                                <span slot="append">元</span>
                            </Input>
                        </FormItem>
                        <FormItem label="成本">
                            <Input v-model="data.cost" class="col-1">
                                <span slot="append">%</span>
                            </Input>
                        </FormItem>
                        <FormItem label="水电费">
                            <Input v-model="data.dayWater" class="col-1">
                                <span slot="append">日</span>
                            </Input>
                        </FormItem>
                        <FormItem label="预计销售">
                            <Input v-model="data.daySell" class="col-1">
                                <span slot="append">日</span>
                            </Input>
                        </FormItem>
                        <FormItem label="加盟费(万)">
                            <Input v-model="data.settled"  class="col-1">
                                <span slot="append">万</span>
                            </Input>
                        </FormItem>
                        <FormItem label="占股比">
                            <Input v-model="data.proportion" class="col-1">
                                <span slot="append">%</span>
                            </Input>
                        </FormItem>
                        <FormItem label="员工人数">
                            <Input v-model="data.persionCount" class="col-1"></Input>
                        </FormItem>
                        <FormItem label="员工月薪">
                            <Input v-model="data.persionPrice" class="col-1"></Input>
                        </FormItem>
                    </Card>
                </Col>
                <Col span="11" offset="2">
                    <Card>
                        <p slot="title">分析结果</p>
                        <p>保本销售数量（日）:      {{result.daySell.toFixed(2)}}</p>
                        <p>保本销售数量（月）:      {{result.monthSell.toFixed(2)}}</p>
                        <p>前期投资:      {{(result.investment / 10000).toFixed(2)}} 万</p>
                        <p>个人投资: {{(result.persionPrice / 10000).toFixed(2)}} 万</p>
                        <p>回本天数： {{result.backDay.toFixed(2)}}</p>
                        <p>日流水：{{result.dayFlowingWater}}</p>
                        <p>日利润：{{result.dayProfit.toFixed(2)}} ，占比：{{(result.dayProfit / result.dayFlowingWater * 100).toFixed(2)}} %</p>
                        <p>日开销：{{result.daySpend.toFixed(2)}} ，占比：{{(result.daySpend / result.dayFlowingWater * 100).toFixed(2) }} %</p>
                        <p>日租金：{{result.dayRent.toFixed(2)}} ，占比：{{(result.dayRent / result.dayFlowingWater).toFixed(2) * 100}} %</p>
                        <p>日人工：{{result.dayPersionPrice.toFixed(2)}}，占比：{{(result.dayPersionPrice / result.dayFlowingWater).toFixed(2) * 100}} %</p>
                        <p>月分红：{{result.monthResult.toFixed(2)}}</p>
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
                    deposit: 0,
                    price:13,
                    cost:25,
                    settled:15,
                    proportion:40,
                    persionCount:5,
                    persionPrice:3000,
                    dayWater:100,
                    daySell : null
                },
                result: {
                    daySell:0, 
                    monthSell:0,
                    investment: 0,
                    persionPrice: 0,
                    daySpend : 0 ,
                    backDay: 0,
                    dayFlowingWater:0,
                    dayRent:0,
                    dayPersionPrice:0,
                    dayProfit:0,
                    monthResult : 0
                }
                
            }
        },
        watch : {
            data:{
                handler(curr,old){
                    this.data.rent -=0;
                    this.data.deposit -=0;
                    this.data.price-=0;
                    this.data.cost-=0;
                    this.data.settled-=0;
                    this.proportion-=0;
                    this.data.persionCount-=0;
                    this.data.persionPrice-=0;
                    this.data.dayWater-=0;
                    this.data.daySell-=0;
                    // 房租金额
                    let rent = this.data.rent * 10000;
                    // 每个净利润 【单价 - (单价 * 单个成本)】
                    let profit = this.data.price - (this.data.price * (this.data.cost / 100));
                    // 日房租金额
                    let dayRent = rent / 365;
                    this.result.dayRent = dayRent;
                    // 员工工资日金额 【(人数 * 月薪) / 30】
                    let dayPersionPrice = (this.data.persionCount * this.data.persionPrice) / 30;
                    this.result.dayPersionPrice = dayPersionPrice;
                    // 日开销 【员工工资单日金额 + 日租金额 + 水电费 + (销售数量*成本率)】
                    this.result.daySpend = dayPersionPrice + dayRent + this.data.dayWater;

                    if(this.data.daySell){
                        this.result.daySpend = this.result.daySpend + (this.data.daySell * (this.data.price * (this.data.cost / 100)));
                    }
                    // 保本日销售数量 【日开销 / 单价净利润】
                    this.result.daySell = (this.result.daySpend / profit);
                    // 保本月销数量 【日开销 * 30】
                    this.result.monthSell = (this.result.daySell * 30);
                    // 前期投资金额 【房租+转让费+((员工数量*员工人数) * 2) + 加盟费】
                    this.result.investment = rent + (this.data.deposit * 10000) + ((this.data.persionCount * this.data.persionPrice) * 2) + (this.data.settled * 10000);
                    // 前期个人投资金额【总金额 * 股份占比】
                    this.result.persionPrice = (this.result.investment) * (this.data.proportion / 100);
                    // 日流水
                    this.result.dayFlowingWater = (this.data.daySell * this.data.price);
                    // 回本天数 【前期个人投资金额/ ((约日销量 * 单杯利润) - 日开销)】
                    if(this.data.daySell){
                        this.result.backDay = this.result.persionPrice / ((this.data.daySell * profit) - this.result.daySpend);
                        this.result.dayProfit = this.result.dayFlowingWater - this.result.daySpend;
                        //月分红【日毛利*(股份占比*100)*30天】
                        this.result.monthResult = (this.result.dayProfit * (this.data.proportion / 100)) * 30;
                    }
                    
                    
                
                },
                deep: true
            }
        }
    }


</script>