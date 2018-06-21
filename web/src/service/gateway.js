import '@/mock/gateway';

const PROD = process.env.NODE_ENV === 'production';

/**
 * 网关 API接口
 */
import axios from 'axios';
import qs from 'qs';
import errorHandle from '@/libs/error';
import { DO_LOGIN } from '@/libs/session';

// 登录
export const GET_LOGIN = (param)=>{
    return gateway('get', '/gateway/login', param).then((response)=>{
        if( response.success && response.token ){
            DO_LOGIN(response.token);
            return response;
        }else{
            throw { code: '301', message: '未登录成功，或服务器错误。' }
        }
    });
};


/** 方法操作 */
export const CREATE_METHOD_DUBBO = (param)=>{
    return gateway('post','/admin/method/createDubbo.json',param);
};
export const GET_METHOD_LIST = (param)=>{
    return gateway('get', '/admin/method/list.json', param);
};
export const METHOD_PARAM_LIST = (param)=>{
    return gateway('get','/admin/method/param/list.json',param)
}

// 删除方法
export const DELETE_METHOD = (param)=>{
    return gateway('post','/admin/method/delete.json',param);
};

// 测试方法
export const TEST_METHOD = (param) => {
    return gateway('post','/admin/method/test.json',param);
};
// 更新方法
export const UPDATE_METHOD_DUBBO = (param) => {
    return gateway('post','/admin/method/updateDubbo.json',param);
};
export const METHOD_INFO = (param) => {
    return gateway('get','/admin/method/info.json',param);
};
/** 方法操作 */

/** 分组操作 */
export const GET_GROUP_LIST = (param)=>{
    return gateway('get','/admin/group/list.json',param);
};
export const DELETE_GROUP = (param)=>{
    return gateway('post','/admin/group/delete.json',param);
};
export const CREATE_GROUP = (param)=>{
    return gateway('post','/admin/group/create.json',param);
}
/** 分组操作 */


export const METHOD_GATEWAY_GET = (param)=>{
    return gateway('get','/gateway',param);
}

export const METHOD_GATEWAY_POST = (param)=>{
    return gateway('post','/gateway',param);
}



// 封装 API
function gateway(type, way, data){

    // 环境变量判断是否需要gateway前缀
    // way = way;
    way = PROD ? way : '/pass'+way ;
    let params = {};
    if( Object.prototype.toString.call(data) == '[object Object]' ){
        for( let key in data ){
            if( data[key] !='' && data.hasOwnProperty(key) ){
                params[key] = data[key];
            }
        };
    }else{
        params = data;
    }

    /**
     * 这里可对传参进行处理
     */
    if( type == "get" ){
        return axios[type](way, {params}).then(dataHandle).catch(errorHandle);
    }else{
        return axios[type](way, qs.stringify(params)).then(dataHandle).catch(errorHandle);
    }
};

// 数据处理 每一个service 返回结果可能不同，在这里进行对数据适配或者放行，在页面进行错误处理
function dataHandle(response){

    // 服务器地址不错在
    if( response.status != 200 ){
        throw {code: 400, message: '400: 服务器无返回 OR 接口不存在' };
    };
    response = response.data;

    /**
     * 这里可以对数据进行处理与容错
     */

    return response;
};
