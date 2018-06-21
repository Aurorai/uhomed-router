<template>
    <Menu width="auto" 
        :active-name="activeName"
        :open-names="[openName]"
        @on-select="handleSelect"
        accordion
        ref="menu">
        <template v-for="menu of navigate">
            <Menu-item 
                v-if="menu.type=='menu'" 
                :name="menu.name">
                <Icon :type="menu.icon"></Icon>
                {{menu.text}}
            </Menu-item>
            <Submenu 
                v-if="menu.type=='submenu'" 
                :name="menu.name" >
                <template slot="title">
                    <Icon :type="menu.icon"></Icon>
                    {{menu.text}}
                </template>
                <template v-for="child of menu.childens">
                    <Menu-item :name="child.name">{{child.text}}</Menu-item>
                </template>
            </Submenu>
        </template>
    </Menu>
</template>

<script>
import navigate from '@/config/navigate';
import _ from 'lodash';
export default{
    data(){
        return {
            navigate,
            openName: '',
            activeName: ''
        }
    },
    computed:{
        nmap(){
            // 返回解析后的 navigate map
            let ret = {};
            this.navigate.forEach((v)=>{
                if( v.type == 'menu' ){
                    ret[v.name] = {
                        path: v.path,
                        parent: '',
                        childs: v.ison || null
                    }
                }else if( v.type == 'submenu' ){
                    v.childens.forEach((item)=>{
                        ret[item.name] = {
                            path: item.path,
                            parent: v.name,
                            childs: item.ison || null  
                        }
                    })
                }
            });
            return ret;
        },
        cleanPath(){
            // 返回去掉 params 的路径
            let ret = '';
            let path = this.$route.path;
            let params = this.$route.params;
            let parr = Object.keys(params);

            if( parr.length<=0 ){
                ret = path;
                return ret;
            }

            parr.forEach(key=>{
                let param = params[key];
                if( !param ){
                    return;
                }
                let index = path.indexOf(param);
                path = path.substring(0, index-1);
            });
            ret = path;
            return path;
        }
    },
    mounted(){
        this.handleHistory();
    },
    watch:{
        '$route'(to, from){
            this.handleHistory();
        }
    },
    methods:{
        handleSelect(name){
            this.$router.push( this.nmap[name] );
        },
        handleHistory(){
            let path = this.$route.path;
            let openName = null;
            let activeName = null;
            for( let k in this.nmap ){
                
                let v = this.nmap[k];

                // 当前值为path
                if( v.path == path ){
                    activeName = k;
                    openName = v.parent;
                    break;
                }

                // 当前路由为son
                let cleanPath = this.cleanPath;
                if( v.childs && v.childs.indexOf(cleanPath)>=0 ){
                    activeName = k;
                    openName = v.parent;
                    break;
                }
            }
            
            // 选中状态
            this.activeName = activeName;
            this.openName = openName;

            // 更新打开状态
            this.$nextTick(()=>{
                this.$refs.menu.updateOpened();
            });
        }
    }
}
</script>