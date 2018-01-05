package com.zk.netty.game.majiang;

import java.util.Random;

public class MajiangGame {
	public static void main(String[] args) {
		for(int i=0;i<5;i++){
			shuffleCard();
			dealCard();
	        System.out.println("-----------------------------------");
		}
//		System.out.println(cards.length);
	}
	
	final static String[] cards = 
		{"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9","B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9",
		"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9","B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9",
		"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9","B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", 
		"A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9","B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", 
		"D1","D1","D1","D1","E1","E1","E1","E1","F1","F1","F1","F1","G1","G1","G1","G1","H1","H1","H1","H1","I1","I1","I1","I1","J1","J1","J1","J1",
		"K1","K1"};

	public static void shuffleCard(){
		Random rd = new Random();
		for (int i = 0; i < 102; i++) {
			int j = rd.nextInt(102);// 生成随机数
			String temp = cards[i];// 交换
			cards[i] = cards[j];
			cards[j] = temp;
		}
//		System.out.println("FFF:"+Arrays.toString(cards));
	}

	public static void dealCard(){
		for (int i = 0; i < 48; i++) {
			if (i % 16 == 0)
				System.out.println();
			System.out.print(cards[i]+",");
		}
	}
}
