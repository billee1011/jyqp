package com.zk.netty.game.shuangjian;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ShuangjianGame {
	
	public static void main(String[] args) {
		long aa = System.currentTimeMillis();
		for(int i=0;i<10;i++){
			shuffleCard();
			dealCard();
	        System.out.println();
		}
	    System.out.println(System.currentTimeMillis()-aa);
	    System.out.println("-------------------------------------------------------------------------");
		for(int i=0;i<10;i++){
			shuffleCardA();
			dealCardA();
	        System.out.println();
		}
	    System.out.println(System.currentTimeMillis()-aa);
	    System.out.println("-------------------------------------------------------------------------");
		for(int i=0;i<10;i++){
			shuffleCardB();
			dealCardB();
	        System.out.println();
		}
	    System.out.println(System.currentTimeMillis()-aa);
		
	}
	
	public final static String[] cards_simple = {"A1", "B1", "C1", "D1","A1", "B1", "C1", "D1", "A2", "B2", "C2", "D2", "A2", "B2", "C2", "D2", "A3", "B3", "C3", "D3","A3", "B3", "C3", "D3", "A4", "B4", "C4", "D4","A4", "B4", "C4", "D4", "A5", "B5", "C5", "D5", "A5", "B5", "C5", "D5","A6", "B6", "C6", "D6","A6", "B6", "C6", "D6",
		"A7", "B7", "C7", "D7","A7", "B7", "C7", "D7", "A8", "B8", "C8", "D8", "A8", "B8", "C8", "D8", "A9", "B9", "C9", "D9","A9", "B9", "C9", "D9", "A10", "B10", "C10", "D10","A10", "B10", "C10", "D10", "A11", "B11", "C11", "D11","A11", "B11", "C11", "D11", "A12", "B12", "C12", "D12", "A12", "B12", "C12", "D12",
		"A13", "B13", "C13", "D13","A13", "B13", "C13", "D13","J1","J1","J2","J2"};
	
	public final static String[] cards_easy = {"A1", "B1", "C1", "D1", "A2", "B2", "C2", "D2", "A3", "B3", "C3", "D3", "A4", "B4", "C4", "D4", "A5", "B5", "C5", "D5", "A6", "B6", "C6", "D6", "A7", "B7", "C7", "D7", "A8", "B8", "C8", "D8",  "A9", "B9", "C9", "D9", "A10", "B10", "C10", "D10", "A11", "B11", "C11", "D11", "A12", "B12", "C12", "D12", "A13", "B13", "C13", "D13",
		"A1", "B1", "C1", "D1", "A2", "B2", "C2", "D2", "A3", "B3", "C3", "D3", "A4", "B4", "C4", "D4", "A5", "B5", "C5", "D5", "A6", "B6", "C6", "D6", "A7", "B7", "C7", "D7", "A8", "B8", "C8", "D8",  "A9", "B9", "C9", "D9", "A10", "B10", "C10", "D10", "A11", "B11", "C11", "D11", "A12", "B12", "C12", "D12", "A13", "B13", "C13", "D13",
		"J1","J1","J2","J2"};
	
	public final static String[] cards = {"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "A11", "A12", "A13", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "B11", "B12", "B13", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "C11", "C12", "C13", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11", "D12", "D13",
			"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "A11", "A12", "A13", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "B11", "B12", "B13", "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "C11", "C12", "C13", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11", "D12", "D13","J1","J2","J1","J2"};
	
	public final static String[] ABC = {"A","B","C","D"};
	
	public static void shuffleCard(){
		ThreadLocalRandom rd = ThreadLocalRandom.current();
		for (int i = 0; i < 108; i++) {
			int j = rd.nextInt(108);// 生成随机数
			String temp = cards[i];// 交换
			cards[i] = cards[j];
			cards[j] = temp;
		}
//		System.out.println("FFF:"+Arrays.toString(cards));
	}
	
	public static void shuffleCardA(){
		Random rd = new Random();
		int c = 6;
		int index = 0;
		System.out.println("当前种子："+c);
		for (int i = 0; i < 108; i++) {
			int j = rd.nextInt(108);// 生成随机数
			if(i%c==0){
				index++;
				String temp = cards_easy[i];// 交换
				cards_easy[i] = cards_easy[j];
				cards_easy[j] = temp;
			}
		}
		System.out.println("B:"+index);
		System.out.println("bbb:"+Arrays.toString(cards_easy));
	}
	
	public static void shuffleCardB(){
		
		Random rd = new Random();
		int c = 6;
		int index = 0;
		System.out.println("当前种子："+c);
		for (int i = 0; i < 108; i++) {
			int j = rd.nextInt(108);// 生成随机数
			if(i%c==0){
				index++;
				
				String temp = cards_simple[i];// 交换
				cards_simple[i] = cards_simple[j];
				cards_simple[j] = temp;
			}
		}
		System.out.println("C:"+index);
		System.out.println("ccc:"+Arrays.toString(cards_simple));
	}
	/**
	 * 一次27张发
	 */
	public static String[] dealCardB(){
//		String[] returnCards = new String[4];
//		String tempStr = "";
//		int j = 0;
//		for (int i = 0; i < 108; i++) {
//			if (i>0&&i % 27 == 0){
//				returnCards[j] = tempStr.substring(0, tempStr.length()-1);
//				tempStr = "";
//				j++;
//			}
//			tempStr = tempStr + cards_simple[i]+",";
//			if(i==107){
//				returnCards[j] = tempStr.substring(0, tempStr.length()-1);
//			}
//		}
//		System.out.println("CCC:"+Arrays.toString(returnCards));
		int j = 0;
		for (int i = 0; i < 27; i++) {
			j = i*4;
			System.out.println("AAA:"+cards[j]+"	BBB:"+cards[j+1]+"	CCC:"+cards[j+2]+"	DDD:"+cards[j+3]);
		}
		return null;
	}
	
	/**
	 * 一次27张发
	 */
	public static String[] dealCardA(){
		String[] returnCards = new String[4];
		String tempStr = "";
		int j = 0;
		for (int i = 0; i < 108; i++) {
			if (i>0&&i % 27 == 0){
				returnCards[j] = tempStr.substring(0, tempStr.length()-1);
				tempStr = "";
				j++;
			}
			tempStr = tempStr + cards_easy[i]+",";
			if(i==107){
				returnCards[j] = tempStr.substring(0, tempStr.length()-1);
			}
		}
		System.out.println("BBB:"+Arrays.toString(returnCards));
		return returnCards;
	}
	
	/**
	 * 一次27张发
	 */
	public static String[] dealCard(){
		String[] returnCards = new String[4];
		String tempStr = "";
		int j = 0;
		for (int i = 0; i < 108; i++) {
			if (i>0&&i % 27 == 0){
				returnCards[j] = tempStr.substring(0, tempStr.length()-1);
				tempStr = "";
				j++;
			}
			tempStr = tempStr + cards[i]+",";
			if(i==107){
				returnCards[j] = tempStr.substring(0, tempStr.length()-1);
			}
		}
		System.out.println("aaa:"+Arrays.toString(returnCards));
		return returnCards;
	}
	
	/**
	 * 轮流发
	 */
	public static void dealCard2(){
		int j = 0;
		
		for (int i = 0; i < 27; i++) {
			j = i*4;
			System.out.println("AAA:"+cards[j]+"	BBB:"+cards[j+1]+"	CCC:"+cards[j+2]+"	DDD:"+cards[j+3]);
		}
	}
	/**
	 * 幸运值发
	 */
	public static void luckyDealCard(int luckyValue){
		int j = 0;
		for (int i = 0; i < 27; i++) {
			j = i*4;
			System.out.println("AAA:"+cards[j]+"	BBB:"+cards[j+1]+"	CCC:"+cards[j+2]+"	DDD:"+cards[j+3]);
		}
	}
	
	public static String[] luckyCard(int luckyValue){
		Random rd = new Random();
		String[] card = null;
		if(luckyValue>59&&luckyValue<71){
			int num = rd.nextInt(14);
			if(num>0){
				card = new String[5];
				int color = rd.nextInt(4);
				for(int i=0;i<4;i++){
					if(i==color){
						card[4] = ABC[i];
					}
					card[i] = ABC[i];
				}
			}
		}else if(luckyValue>70&&luckyValue<81){
			
		}else if(luckyValue>80&&luckyValue<91){
			
		}else if(luckyValue>90&&luckyValue<100){
			
		}
		
		return card;
	}
}
