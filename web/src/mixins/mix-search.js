import Util from '@/libs/util';
export default{
    computed:{
        query:{
            get(){
                return Object.assign({}, this.$route.query);
            },
            set(params){
                Object.assign(this.params, params);
                params = Util.removeNull(this.params);
                this.$router.replace({query: params});
            }
        }
    },
    beforeMount(){
        let query = this.$route.query || {};
        Object.assign(this.params, query);
        this.$router.replace({query: Util.removeNull(this.params)});
    }
};