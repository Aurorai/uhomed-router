import axios from 'axios';
import errorHandle from '@/libs/error';

// 获取文章列表
export const GET_ARTICLE_ITEMS = (param)=>{
    return gateway('get', '/api/v1/topics', param);
};

// 封装 API
function gateway(type, way, params){

    //当前服务前缀
    way ='/cnode'+way;

    /**
     * 这里可对传参进行处理
     */
    return axios[type](way, { params }).then(dataHandle).catch(errorHandle)
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