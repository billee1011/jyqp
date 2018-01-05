package com.zk.netty.user.service;

import java.util.Map;

public interface UserService {
	/**
	 * 根据username获取用户信息
	 * @param username
	 * @return
	 */
	public Map<String,Object> getUserByUsername(String username);
	/**
	 * 根据mobile获取用户信息
	 * @param mobile
	 * @return
	 */
	public Map<String,Object> getUserByMobile(String mobile);
	/**
	 * 根据wxOpenId获取用户信息
	 * @param wxOpenId
	 * @return
	 */
	public Map<String,Object> getUserByWxOpenId(String wxOpenId);
	/**
	 * 根据qqOpenId获取用户信息
	 * @param qqOpenId
	 * @return
	 */
	public Map<String,Object> getUserByQqOpenId(String qqOpenId);
	/**
	 * 根据userid 获取用户信息
	 * @param userid
	 * @return
	 */
	public Map<String,Object> getUserById(int userid);
	
	/**
	 * 根据userid 获取用户简短信息
	 * @param userid
	 * @return
	 */
	public Map<String,Object> getUserShortInfoById(int userid);
	
	/**
	 * 检查手机号是否存在
	 * @param type
	 * @param account
	 * @return
	 */
	public boolean checkMobile(String mobile);
	/**
	 * 注册账号
	 * @param regType
	 * @param account
	 * @param password
	 * @param nickname
	 * @return
	 */
	public int regAccount(int regType,String account,String password,String nickname);
	/**
	 * 添加验证码
	 * @param type
	 * @param mobile
	 * @param code
	 * @param sendip
	 * @return
	 */
	public int addMobileCode(int type,String mobile,String code,String sendip);
	/**
	 * 获取手机验证码
	 * @param type
	 * @param mobile
	 * @param code
	 * @return
	 */
	public Map<String,Object> getMobileCode(int type,String mobile,String code);
	/**
	 * 更新验证码状态
	 * @param type
	 * @param mobile
	 * @param code
	 * @param status
	 * @return
	 */
	public int updateMobileCode(int type,String mobile,String code,int status);
	/**
	 * 根据手机号修改密码
	 * @param mobile
	 * @param password
	 * @return
	 */
	public int updatePassword(String mobile,String password);
}
