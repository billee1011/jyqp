package com.zk.netty.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OrderUtil {
	/** 月日 */
	public static final String md = "yyyyMMdd";

	public static String makeOrderCode(){

		Date date = new Date();
		DateFormat df = new SimpleDateFormat(md);
		String part1 = df.format(date);
		if (part1.length() == 4){
			part1 = part1.substring(1);
		}

		String part2 = (System.currentTimeMillis() / 1000 + "").substring(5);

		// 上线的时候，要调试一下，精确到秒的位置的地方（刷新一下肉眼可以识别，来修改下面的7），对应位置向后截取五位即可。
		String nanoTome = System.nanoTime() + "";
		String part3 = nanoTome.substring(7).substring(0, 5);

		return part1 + part2 + part3;

	}

	public static String makeOutCode(){

		Date date = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String part1 = df.format(date);
		if (part1.length() == 4){
			part1 = part1.substring(1);
		}

		String part2 = (System.currentTimeMillis() / 1000 + "").substring(5);
		String part3 = (System.nanoTime() / 10 + "").substring(8);

		return part1 + part2 + part3;
	}

	public static String getTime(){
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(date);
	}
	
	/**
	 * 获取6位随机数
	 * @return
	 */
	public static int getRandomCode(){
		return (int) Math.abs(new Random().nextInt(899999) + 100000);
	}

	public static void main(String[] args){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(int i=0;i<1000;i++){
			Map<String,Object> m = new HashMap<String,Object>();
			m.put(getRandomCode()+"", "1");
			list.add(m);
		}
		List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<1000;i++){
			Map<String,Object> m = new HashMap<String,Object>();
			m.put(getRandomCode()+"", "1");
			mapList.add(m);
		}
		int index = 0;
		for(Map<String, Object> map : list){
			if(mapList.contains(map)){
				index++;
			}
		}
		System.out.println(index);
	}
}
