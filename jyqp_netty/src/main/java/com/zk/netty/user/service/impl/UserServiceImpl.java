package com.zk.netty.user.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.zk.netty.user.service.UserService;
import com.zk.netty.util.OrderUtil;
import com.zk.netty.util.db.ConnectionManager;
import com.zk.netty.util.db.DbTools;


@Component("userService")
public class UserServiceImpl implements UserService {
	/**
	 * 根据username获取用户信息
	 * @param username
	 * @return
	 */
	@Override
	public Map<String,Object> getUserByUsername(String username){
		String sql = "select fu.id,fu.gameid,fu.username,fu.password,fu.mobile,fu.wx_open_id,fu.qq_open_id,fu.nickname,fu.zodiac_sign,fu.birthday,fu.constellation,fu.head,fu.sex,fu.address,fu.personage_title,"
				+ "fu.vip_exp,fu.vip_level,uw.money,uw.coffers,uw.has_coffers,uw.purple_money,uw.honor_level,uw.lucky_value,uw.honor_value "
				+ "from fc_user fu left join user_wallet uw on fu.id=uw.userid where fu.username=?";
		try {
			return DbTools.getHashValue(sql,username);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	/**
	 * 根据mobile获取用户信息
	 * @param mobile
	 * @return
	 */
	@Override
	public Map<String, Object> getUserByMobile(String mobile) {
		String sql = "select fu.id,fu.gameid,fu.username,fu.password,fu.mobile,fu.wx_open_id,fu.qq_open_id,fu.nickname,fu.zodiac_sign,fu.birthday,fu.constellation,fu.head,fu.sex,fu.address,fu.personage_title,"
				+ "fu.vip_exp,fu.vip_level,uw.money,uw.coffers,uw.has_coffers,uw.purple_money,uw.honor_level,uw.lucky_value,uw.honor_value "
				+ "from fc_user fu left join user_wallet uw on fu.id=uw.userid where fu.mobile=?";
		try {
			return DbTools.getHashValue(sql,mobile);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	/**
	 * 根据wxOpenId获取用户信息
	 * @param wxOpenId
	 * @return
	 */
	@Override
	public Map<String, Object> getUserByWxOpenId(String wxOpenId) {
		String sql = "select fu.id,fu.gameid,fu.username,fu.password,fu.mobile,fu.wx_open_id,fu.qq_open_id,fu.nickname,fu.zodiac_sign,fu.birthday,fu.constellation,fu.head,fu.sex,fu.address,fu.personage_title,"
				+ "fu.vip_exp,fu.vip_level,uw.money,uw.coffers,uw.has_coffers,uw.purple_money,uw.honor_level,uw.lucky_value,uw.honor_value "
				+ "from fc_user fu left join user_wallet uw on fu.id=uw.userid where fu.wx_open_id=?";
		try {
			return DbTools.getHashValue(sql,wxOpenId);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	/**
	 * 根据qqOpenId获取用户信息
	 * @param qqOpenId
	 * @return
	 */
	@Override
	public Map<String, Object> getUserByQqOpenId(String qqOpenId) {
		String sql = "select fu.id,fu.gameid,fu.username,fu.password,fu.mobile,fu.wx_open_id,fu.qq_open_id,fu.nickname,fu.zodiac_sign,fu.birthday,fu.constellation,fu.head,fu.sex,fu.address,fu.personage_title,"
				+ "fu.vip_exp,fu.vip_level,uw.money,uw.coffers,uw.has_coffers,uw.purple_money,uw.honor_level,uw.lucky_value,uw.honor_value "
				+ "from fc_user fu left join user_wallet uw on fu.id=uw.userid where fu.qq_open_id=?";
		try {
			return DbTools.getHashValue(sql,qqOpenId);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	/**
	 * 根据userid 获取用户信息
	 * @param userid
	 * @return
	 */
	@Override
	public Map<String,Object> getUserById(int userid){
		
		String sql = "select fu.id,fu.gameid,fu.username,fu.password,fu.mobile,fu.wx_open_id,fu.qq_open_id,fu.nickname,fu.zodiac_sign,fu.birthday,fu.constellation,fu.head,fu.sex,fu.address,fu.personage_title,"
				+ "fu.vip_exp,fu.vip_level,uw.money,uw.coffers,uw.has_coffers,uw.purple_money,uw.honor_level,uw.lucky_value,uw.honor_value "
				+ "from fc_user fu left join user_wallet uw on fu.id=uw.userid where fu.id=?";
		try {
			return DbTools.getHashValue(sql,userid);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 根据userid 获取用户简短信息
	 * @param userid
	 * @return
	 */
	public Map<String,Object> getUserShortInfoById(int userid){
		
		String sql = "select fu.id uid,fu.nickname,fu.head,uw.money,uw.purple_money,uw.honor_level from fc_user fu left join user_wallet uw on fu.id=uw.userid where fu.id=?";
		try {
			return DbTools.getHashValue(sql,userid);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 检查手机号是否存在
	 * @param type
	 * @param account
	 * @return
	 */
	public boolean checkMobile(String mobile){
		String sql = "select id from fc_user where mobile=?";
		try {
			return DbTools.checkExsit(sql, mobile);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * 注册账号
	 * @param regType
	 * @param account
	 * @param password
	 * @param nickname
	 * @return
	 */
	public int regAccount(int regType,String account,String password,String nickname){
		String sql = "";
		switch (regType) {
		case 0:
			sql = "insert into fc_user(gameid,wx_open_id,password,nickname,createtime,reg_type)values(?,?,?,?,now(),?)";
			break;
		case 1:
			sql = "insert into fc_user(gameid,qq_open_id,password,nickname,createtime,reg_type)values(?,?,?,?,now(),?)";
			break;
		case 2:
			sql = "insert into fc_user(gameid,mobile,password,nickname,createtime,reg_type)values(?,?,?,?,now(),?)";
			break;
		case 3:
			sql = "insert into fc_user(gameid,username,password,nickname,createtime,reg_type)values(?,?,?,?,now(),?)";
			break;
		default:
			break;
		}
		String sqlWallert = "insert into user_wallet(userid,createtime)values(?,now())";
		String sqlGameId = "select id from fc_user where gameid=?";
		Connection conn = ConnectionManager.getConnection(ConnectionManager.WRITE_DB);
		try {
			conn.setAutoCommit(false);
			int result = 0;
			int gameId = OrderUtil.getRandomCode();
			Map<String,Object> mapGameId = DbTools.getHashValue(sqlGameId,conn, gameId);
			boolean bool = true;
			while(bool){
				if(MapUtils.isEmpty(mapGameId)){
					try {
						result = DbTools.executeInsert(sql,conn, gameId,account,password,nickname,regType);
					} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
						result = DbTools.executeInsert(sql,conn, OrderUtil.getRandomCode(),account,password,nickname,regType);
					}
					break;
				}else{
					gameId = OrderUtil.getRandomCode();
					mapGameId = DbTools.getHashValue(sqlGameId,conn, gameId);
				}
			}
			if(result>0){
				DbTools.executeUpdate(sqlWallert,conn, result);
			}
			conn.commit();
			return result;
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConnectionManager.returnConnection(conn);
		}
		return 0;
	}
	/**
	 * 添加验证码
	 * @param type
	 * @param mobile
	 * @param code
	 * @param sendip
	 * @return
	 */
	public int addMobileCode(int type,String mobile,String code,String sendip){
		String sql = "insert into mobile_code(mobile,code,sendip,status,code_type,createtime)values(?,?,?,0,?,now())";
		try {
			return DbTools.executeUpdate(sql, mobile,code,sendip,type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 获取手机验证码
	 * @param type
	 * @param mobile
	 * @param code
	 * @return
	 */
	public Map<String,Object> getMobileCode(int type,String mobile,String code){
		String sql = "select * from mobile_code where mobile=? and code=? and status=0 and code_type=? order by id desc limit 1";
		try {
			return DbTools.getHashValue(sql, mobile,code,type);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 更新验证码状态
	 * @param type
	 * @param mobile
	 * @param code
	 * @param status
	 * @return
	 */
	public int updateMobileCode(int type,String mobile,String code,int status){
		String sql = "update mobile_code set status=? where mobile=? and code=? and code_type=?";
		try {
			return DbTools.executeUpdate(sql, status,mobile,code,type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 根据手机号修改密码
	 * @param mobile
	 * @param password
	 * @return
	 */
	public int updatePassword(String mobile,String password){
		String sql = "update fc_user set password=? where mobile=?";
		try {
			return DbTools.executeUpdate(sql, password,mobile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
