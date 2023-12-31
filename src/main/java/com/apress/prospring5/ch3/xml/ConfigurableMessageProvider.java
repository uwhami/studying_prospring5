package com.apress.prospring5.ch3.xml;

import com.apress.prospring5.ch2.decoupled.MessageProvider;


//3-29. 생성자 주입 사용하기.
public class ConfigurableMessageProvider implements MessageProvider{
	
	private String message;
	
	public ConfigurableMessageProvider() {
		System.out.println("==========Call ConfigurableMessageProvider Constructor.");
	}
	
	public ConfigurableMessageProvider(String message) {
		System.out.println("==========Call ConfigurableMessageProvider Constructor with string message.");
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	
	
}
