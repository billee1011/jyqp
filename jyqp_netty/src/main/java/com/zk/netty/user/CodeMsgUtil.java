package com.zk.netty.user;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.zk.netty.enums.Code;

public class CodeMsgUtil {
	

	public static Map<String,Object> codeMsgReturn(Map<String,Object> map,Code code){
		map.put("code", code.getCode());
		map.put("msg", code.getMsg());
		return map;
	}
	
	public static Map<String,Object> codeMsgReturn(Map<String,Object> map){
		map.put("code", Code.success.getCode());
		map.put("msg", Code.success.getMsg());
		return map;
	}
	
	public static String codeMsgReturnJson(Map<String,Object> map,Code code){
		map.put("code", Code.success.getCode());
		map.put("msg", Code.success.getMsg());
		return JSONObject.toJSONString(map);
	}
}
