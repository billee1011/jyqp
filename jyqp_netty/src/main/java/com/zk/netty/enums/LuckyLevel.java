package com.zk.netty.enums;

public enum LuckyLevel {
	
	L1(10),
	L2(20),
	L3(30),
	L4(40),
	L5(50),
	L6(60),
	L7(70),
	L8(80),
	L9(90),
	L10(99)
	;
	
	
	private int value;

	public int getValue() {
		return value;
	}
	
	private LuckyLevel(int value){
		this.value = value;
	}
	
	
}
