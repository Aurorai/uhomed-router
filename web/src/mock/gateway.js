/**
 * 此处是网关数据接口模拟
 */
import Mock from 'mockjs';

// 模拟登陆接口
Mock.mock(/gateway\/login/, {
    code: '200',
    message: '登陆成功',
    success: true,
    token: Mock.mock('@guid')
});