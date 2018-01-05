package com.zk.netty.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;

/**
 * 字符串正则校验
 * @author syf
 */
public final class ValidateUtils {
	
	private ValidateUtils() {
		
	}
	
	public static void assertNotNull(final Logger logger, final Object object, final String message) {
	
		if (object == null) {
			logger.error(message, new IllegalArgumentException());
		}
	}
	
	public static void assertNotEmpty(final Logger logger, final Collection<?> c, final String message) {

		assertNotNull(logger, c, message);
		if (c.isEmpty()) {
			logger.error(message, new IllegalArgumentException());
		}
	}
	
	public static void assertNotEmpty(final Logger logger, final String[] c, final String message) {
		
		assertNotNull(logger, c, message);
		if (c.length == 0) {
			logger.error(message, new IllegalArgumentException());
		}
	}
	
	public static void assertNotEmpty(final Logger logger, final int[] c, final String message) {
		
		if (c.length == 0) {
			logger.error(message, new IllegalArgumentException());
		}
	}
	
	public static void assertNotEmpty(final Logger logger, final String string, final String message) {
	
		if (isEmpty(string)) {
			logger.error(message, new IllegalArgumentException());
		}
	}

	public static void assertNotBlank(final Logger logger, final String string, final String message) {
	
		if (isBlank(string)) {
			logger.error(message, new IllegalArgumentException());
		}
	}
	
	public static void assertNotZero(final Logger logger, final int i, final String message) {
	
		if (i == 0) {
			logger.error(message, new IllegalArgumentException());
		}
	}
	
	public static void assertGreatZero(final Logger logger, final int i, final String message) {
		
		if (i <= 0) {
			logger.error(message, new IllegalArgumentException());
		}
	}
	
	public static boolean isEmpty(Collection<?> c) {
		
		return (c == null || c.size() == 0) ? true : false;
	}

	public static boolean isEmpty(final int[] i) {
		
		return i == null || i.length == 0;
	}
	
	public static boolean isEmpty(final String string) {
	
		return string == null || string.length() == 0;
	}
	
	public static boolean isNotEmpty(final String string) {
	
		return !isEmpty(string);
	}

	public static boolean isBlank(final String string) {
	
		return isEmpty(string) || string.trim().length() == 0;
	}
	
	public static boolean isNotBlank(final String string) {
	
		return !isBlank(string);
	}
	//邮箱
	public static final String EMAIL_REG = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
	public static final String MOBILE_REG = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";//手机号
	public static final String ISNUMBER= "^[0-9]*$";//任意数字
	public static final String PASSWORD="^[0-9a-zA-Z\\S]{6,30}$";//密码 任意数字，字母,任何非空白字符
	//生日
	public static final String BIRTHDAY="([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))";
	public static final String SHENBIANID="^[a-zA-Z0-9]{4,10}$";//长度在4~10之间，只能输入数字跟英文。
	public static final String ACCOUNTID="^[a-zA-Z0-9]{6,32}$";//长度在4~10之间，只能输入数字跟英文。
	//邀请码
	public static final String INVITE_CODE="^[0-9A-Z]{10}$";//只能 数字或大写字母
	//微信号，以字母开头，可以使用下划线，减号
	public static final String WEIXIN="^(?i)([a-zA-Z][A-Za-z0-9_-]{5,19})$";
	//真实姓名 ,只能输入汉字 ，长度2~20
	public static final String REAL_NAME="^([\u4e00-\u9fa5]{2,20})|([a-zA-Z]{6,20})$";
	//18身份证 
	public static final String IDENTITY_CARD="^([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x))|([A-Z]{1}[0-9]{6}\\(([0-9]{1}|A)\\))|([1-9]{1}[0-9]{6}\\([0-9]{1}\\))|(^[A-Z]{1}[0-9]{8,10})$";
	//微信名,任何空白字符：空格，制表符、换页符，任何非空白字符，包含中文，字母，数字，长度1~32位
	public static final String WEIXIN_NAME="";//(\\W\\w{1,50}) 未使用
	//用户名
	public static final String USER_NAME="^(?i)([a-zA-Z]{1}[_0-9a-zA-Z-]{5,19})$";
	//中能输入中文或英文
	public static final String TAOBAO_NAME="([\u4e00-\u9fa5]{2,32})|([a-zA-Z]{2,32})";
//	//匹配 任何字符。不包含中文，长度5,32位
//	public static final String WANGWANG="^[a-zA-Z0-9_\\W][^\u4e00-\u9fa5]{4,24}$";
	//有效的网址链接
	/*
	 *1 该正则表达式匹配的字符串必须以http://、https://、ftp://开头；

	2：该正则表达式能匹配URL或者IP地址；（如：http://www.baidu.com 或者 http://192.168.1.1）

	3：该正则表达式能匹配到URL的末尾，即能匹配到子URL；（如能匹配：http://www.baidu.com/s?wd=a&rsv_spt=1&issp=1&rsv_bp=0&ie=utf-8&tn=baiduhome_pg&inputT=1236）

	4：该正则表达式能够匹配端口号；
	 */
	
	public static final String URL="((http|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?";
	//淘宝店铺链接
	public static final String TAOBAO_URL="^(?i)((http://){0,1}([A-Za-z0-9-_]+).taobao.com)$";
	//公司名
	public static final String COMPANY="([\u4e00-\u9fa5]{2,32})|([a-zA-Z]{2,32})";
	//UUID 一个128位长的数字，一般用16进制表示。
	//算法的核心思想是结合机器的网卡、当地时间、一个随机数来生成GUID。从理论上讲，如果一台机器每秒产生10000000个GUID，则可以保证（概率意义上）3240年不重复。 
	public static final String UUID="^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
	
	//匹配  英文 数字 空格 英文句号
	public static final String ABC_NUM_SPACE="^[a-zA-Z0-9\\s.]*$";
	//匹配中英文数字 空格 
	public static final String CHINESE_ABC_NUM_SPACE="^[\u4e00-\u9fa5a-zA-Z0-9\\s]*$";
	//匹配中英文数字
    public static final String CHINESE_ABC_NUM="^[\u4e00-\u9fa5a-zA-Z0-9]*$";
	//匹配中文和问号 
    public static final String CHINESE_QUESTION="^[\u4e00-\u9fa5\\?]+$";
    //电话
    public static final String TELEPHONE="^[0-9-]{6,18}$";
    //匹配数字和逗号
    public static final String NUM_COMMA="^[0-9,]{1,256}$";
    
    //匹配英文数字
    public static final String ABC_NUM="^[0-9a-zA-Z]*$";
    //匹配英文数字和逗号
    public static final String ABC_NUM_COMMA="^[0-9a-zA-Z,]*$";
    //匹配中文 
    public static final String CHINESE="^[\u4e00-\u9fa5]{1,64}$";
    //匹配中英文 
    public static final String CHINESE_ABC="^[\u4e00-\u9fa5a-zA-Z]{1,64}$";
    //匹配中英文 数字 下划线。
    public static final String CHINESE_ABC_NUM_UNDERLINE="^[\u4e00-\u9fa5a-zA-Z0-9_]{1,64}$";
    //匹配中英文数字，特殊符号，横杠，留言备注 使用。
    public static final String CHINESE_ABC_NUM_SPECIAL_FLAG="^[\u4e00-\u9fa5a-zA-Z0-9\\s_\\-—、.,，。！!()（）【】\\[\\]#@$%*]*$";
    //商品防伪码正则
    public static final String GOODS_SECURITY_CODE="^(388)[0-9]{11,12}|[A-Z0-9a-z]{18}$";
    
    /**
     * 商品防伪码正则
     * @param uuid
     * @return
     */
    public static boolean isGoodsSecurityCode(String value){
        Pattern pattern = Pattern.compile(GOODS_SECURITY_CODE);
        Matcher matcher= pattern.matcher(value);
        return matcher.matches();
    }
    /**
     * 匹配中英文数字，特殊符号，留言备注 使用。
     * @param uuid
     * @return
     */
    public static boolean isChineseAbcNumSpecialFlag(String value){
        Pattern pattern = Pattern.compile(CHINESE_ABC_NUM_SPECIAL_FLAG);
        Matcher matcher= pattern.matcher(value);
        return matcher.matches();
    }
    /**
     * 匹配中英文 数字 下划线。
     * @param uuid
     * @return
     */
    public static boolean isChineseAbcNumUnderline(String value){
        Pattern pattern = Pattern.compile(CHINESE_ABC_NUM_UNDERLINE);
        Matcher matcher= pattern.matcher(value);
        return matcher.matches();
    }
    /**
     * 匹配中文
     * @param uuid
     * @return
     */
    public static boolean isChinese(String value){
        Pattern pattern = Pattern.compile(CHINESE);
        Matcher matcher= pattern.matcher(value);
        return matcher.matches();
    }
    
    /**
     * 匹配中英文
     * @param uuid
     * @return
     */
    public static boolean isChineseAbc(String value){
        Pattern pattern = Pattern.compile(CHINESE_ABC);
        Matcher matcher= pattern.matcher(value);
        return matcher.matches();
    }
    /**
     * 匹配英文数字
     * @param uuid
     * @return
     */
    public static boolean isAbcNum(String value){
        Pattern pattern = Pattern.compile(ABC_NUM);
        Matcher matcher= pattern.matcher(value);
        return matcher.matches();
    }
    /**
     * 匹配英文数字和逗号
     * @param uuid
     * @return
     */
    public static boolean isAbcNumAndComma(String value){
        Pattern pattern = Pattern.compile(ABC_NUM_COMMA);
        Matcher matcher= pattern.matcher(value);
        return matcher.matches();
    }
    /**
     * 匹配数字和逗号
     * @param uuid
     * @return
     */
    public static boolean isNumAndComma(String value){
        Pattern pattern = Pattern.compile(NUM_COMMA);
        Matcher matcher= pattern.matcher(value);
        return matcher.matches();
    }
    /**
     * 电话号码020-12345678
     * @param uuid
     * @return
     */
    public static boolean isTelephone(String telephone){
        Pattern pattern = Pattern.compile(TELEPHONE);
        Matcher matcher= pattern.matcher(telephone);
        return matcher.matches();
    }
    /**
     * 匹配中文和问号 
     * @param uuid
     * @return
     */
    public static boolean isChineseQuestion(String value){
        Pattern pattern = Pattern.compile(CHINESE_QUESTION);
        Matcher matcher= pattern.matcher(value);
        return matcher.matches();
    }
    /**
     * 匹配 英文 数字   空格 英文句号
     * @param uuid
     * @return
     */
    public static boolean isAbcNumSpace(String value){
        Pattern pattern = Pattern.compile(ABC_NUM_SPACE);
        Matcher matcher= pattern.matcher(value);
        return matcher.matches();
    }
    /**
     * 匹配中英文数字   空格
     * @param uuid
     * @return
     */
    public static boolean isChineseAbcNumSpace(String value){
        Pattern pattern = Pattern.compile(CHINESE_ABC_NUM_SPACE);
        Matcher matcher= pattern.matcher(value);
        return matcher.matches();
    }
	/**
     * 匹配中英文数字
     * @param uuid
     * @return
     */
    public static boolean isChineseAbcNum(String value){
        Pattern pattern = Pattern.compile(CHINESE_ABC_NUM);
        Matcher matcher= pattern.matcher(value);
        return matcher.matches();
    }
	/**
	 * UUID
	 * @param uuid
	 * @return
	 */
	public static boolean isUUID(String uuid){
		Pattern pattern = Pattern.compile(UUID);
		Matcher matcher= pattern.matcher(uuid);
		return matcher.matches();
	}
	/**
	 * 公司名
	 * @param company
	 * @return
	 */
	public static boolean isCompany(String company){
		Pattern pattern = Pattern.compile(COMPANY);
		Matcher matcher= pattern.matcher(company);
		return matcher.matches();
	}
	/**
	 * http|https URL 链接
	 * @param url
	 * @return
	 */
	public static boolean isUrl(String url){
		Pattern pattern = Pattern.compile(URL);
		Matcher matcher= pattern.matcher(url);
		return matcher.matches();
	}
	
	/**
	 * 淘宝店铺名
	 * @param taobaoName
	 * @return
	 */
	public static boolean isTaoBaoName(String taobaoName){
		Pattern pattern = Pattern.compile(TAOBAO_NAME);
		Matcher matcher= pattern.matcher(taobaoName);
		return matcher.matches();
	}
	
	/**
	 * 旺旺号
	 * @param wangwang
	 * @return
	 */
	public static boolean isWangwang(String wangwang){
		int len = 0;
		for (int i = 0; i < wangwang.length(); i++) {
	               int length = wangwang.codePointAt(i);
	               if(length>=0&&length<=128){
	                    len += 1;
	                }else{
	                    len += 2;
	                }
                }
		if(len>=5&&len<=25){
			return true;
		}
		return false;
	}
	/**
	 * 用户名
	 * @param userName
	 * @return
	 */
	public static boolean isUserName(String userName){
		Pattern pattern = Pattern.compile(USER_NAME);
		Matcher matcher= pattern.matcher(userName);
		return matcher.matches();
	}
	/**
	 * 微信名
	 * @param weixinName
	 * @return
	 */
	public static boolean isWeixinName(String weixinName){
		Pattern pattern = Pattern.compile(WEIXIN_NAME);
		Matcher matcher= pattern.matcher(weixinName);
		return matcher.matches();
	}
	/**
	 * 18位 身份证
	 * @param identityCard
	 * @return
	 */
	public static boolean isIdentityCard(String identityCard){
		Pattern pattern = Pattern.compile(IDENTITY_CARD);
		Matcher matcher= pattern.matcher(identityCard);
		return matcher.matches();
	}
	/**
	 * 真实姓名
	 * @param realname
	 * @return
	 */
	public static boolean isRealName(String realname){
		Pattern pattern = Pattern.compile(REAL_NAME);
		Matcher matcher= pattern.matcher(realname);
		return matcher.matches();
	}
	/**
	 * 微信号
	 * @param weixin
	 * @return
	 */
	public static boolean isWeixin(String weixin){
		Pattern pattern = Pattern.compile(WEIXIN);
		Matcher matcher= pattern.matcher(weixin);
		return matcher.matches();
	}
	/**
	 * 邀请码
	 * @param inviteCode
	 * @return
	 */
	public static boolean isInviteCode(String inviteCode){
		Pattern pattern = Pattern.compile(INVITE_CODE);
		Matcher matcher= pattern.matcher(inviteCode);
		return matcher.matches();
	}
	/**
	 * 邮箱
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email){
		
		Pattern pattern = Pattern.compile(EMAIL_REG);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	/**
	 * //手机号
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile){
		
		Pattern pattern = Pattern.compile(MOBILE_REG);
		Matcher matcher = pattern.matcher(mobile);
		return matcher.matches();
	}
	/**
	 * 是否是 数字
	 * @param mobile
	 * @return
	 */
	public static boolean isNumber(String number){
		Pattern pattern=Pattern.compile(ISNUMBER);
		Matcher matcher=pattern.matcher(number);
		return matcher.matches();
	}
	/**
	 * 密码
	 * @param passwd
	 * @return
	 */
	public static boolean isPasswd(String passwd){
		Pattern pattern=Pattern.compile(PASSWORD);
		Matcher matcher=pattern.matcher(passwd);
		return matcher.matches();
	}
	
	/**
	 * 生日
	 * @param birthday
	 * @return
	 */
	public static boolean isBirthday(String birthday){
		Pattern pattern=Pattern.compile(BIRTHDAY);
		Matcher matcher=pattern.matcher(birthday);
		return matcher.matches();
	}
	public static boolean  listMapContain(List<HashMap<String, Object>> listMap, String key, String value){
		if(listMap == null || listMap.size() == 0 || key == null ){
			return false;
		}
		
		for(HashMap<String, Object> map : listMap){
			if(map.containsKey(key) && map.get(key).toString().equals(value)){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 判断是否是格式为  yyyy-MM-dd
	 * @return
	 */
	public static boolean isDate(String date) {
		
		String datePattern = "^((\\d{2}(([02468][048])|([13579][26]))[\\-]{1}((((0{1}[13578])|(1[02]))[\\-]{1}((0{1}[1-9])|([1-2][0-9])|(3[01])))|(((0{1}[469])|(11))[\\-]{1}((0{1}[1-9])|([1-2][0-9])|(30)))|(0{1}2[\\-]{1}((0{1}[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]{1}((((0{1}[13578])|(1[02]))[\\-]{1}((0{1}[1-9])|([1-2][0-9])|(3[01])))|(((0{1}[469])|(11))[\\-]{1}((0{1}[1-9])|([1-2][0-9])|(30)))|(0{1}2[\\-]{1}((0{1}[1-9])|(1[0-9])|(2[0-8])))))){1}$";

        if ((date != null)) {
            Pattern pattern = Pattern.compile(datePattern);
            Matcher matcher = pattern.matcher(date);
            return matcher.matches();
        }
    	return false;
    }
	/**
	 * 判断是否是格式为  yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static boolean isDateTime(String datetime){
		String datePattern = "^((\\d{2}(([02468][048])|([13579][26]))[\\-]{1}((((0{1}[13578])|(1[02]))[\\-]{1}((0{1}[1-9])|([1-2][0-9])|(3[01])))|(((0{1}[469])|(11))[\\-]{1}((0{1}[1-9])|([1-2][0-9])|(30)))|(0{1}2[\\-]{1}((0{1}[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-]{1}((((0{1}[13578])|(1[02]))[\\-]{1}((0{1}[1-9])|([1-2][0-9])|(3[01])))|(((0{1}[469])|(11))[\\-]{1}((0{1}[1-9])|([1-2][0-9])|(30)))|(0{1}2[\\-]{1}((0{1}[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0{1}[0-9])|([1-2][0-3]))\\:([0-5]{1}[0-9])((\\s)|(\\:([0-5]{1}[0-9]))))){1}$";
		if ((datetime != null)) {
            Pattern pattern = Pattern.compile(datePattern);
            Matcher matcher = pattern.matcher(datetime);
            return matcher.matches();
        }
    	return false;
	}
	
	/**
	 * 判断身边号的合法性
	 * @param shenbianid
	 * @return
	 */
	public static boolean isRightShenbianid(String shenbianid) {	
		
		Pattern pattern = Pattern.compile(SHENBIANID);
		Matcher matcher = pattern.matcher(shenbianid);		
		return matcher.matches();
	}
	
	/**
	 * 判断账号的合法性 ^[a-zA-Z0-9]{6,32}$
	 * @param account
	 * @return
	 */
	public static boolean isRightAccount(String account) {	
		Pattern pattern = Pattern.compile(ACCOUNTID);
		Matcher matcher = pattern.matcher(account);		
		return matcher.matches();
	}
}
