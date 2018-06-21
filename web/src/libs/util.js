/**
 * Created by aresn on 16/7/18.
 */
let util = {
};

util.alert = function(content) {
    window.alert(content);
};

util.title = function (title) {
    title = title ? title + ' - iView' : 'iView管理中心';
    window.document.title = title;
};

const SPECIAL_CHARS_REGEXP = /([\:\-\_]+(.))/g;
const MOZ_HACK_REGEXP = /^moz([A-Z])/;

function camelCase(name) {
    return name.replace(SPECIAL_CHARS_REGEXP, function(_, separator, letter, offset) {
        return offset ? letter.toUpperCase() : letter;
    }).replace(MOZ_HACK_REGEXP, 'Moz$1');
}

// getStyle
util.getStyle = function (element, styleName) {
    if (!element || !styleName) return null;
    styleName = camelCase(styleName);
    if (styleName === 'float') {
        styleName = 'cssFloat';
    }
    try {
        const computed = document.defaultView.getComputedStyle(element, '');
        return element.style[styleName] || computed ? computed[styleName] : null;
    } catch(e) {
        return element.style[styleName];
    }
};

// 删除对象空值
util.removeNull = (o)=>{
    let ret = {};
    Object.keys(o).forEach(k=>{
        if( o[k] !='' || o[k] === undefined ){
            ret[k] = o[k];
        }
    });
    return ret;
}

// UUID生成
util.uuid = function(){
    var s = [];
    var hexDigits = "0123456789abcdef";
    for (var i = 0; i < 36; i++) {
        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
    }
    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
    // s[8] = s[13] = s[18] = s[23] = "-";
    var uuid = s.join("");
    return uuid;
}

// 登录
util.login = function(router, callback){
    let params = callback ? {callback: callback} : null ;
    router.push({path: '/login', query: params} );
};

export default util;