package com.apress.prospring5.ch3.annotated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


//3-40. 필드 주입을 이용한 빈 클래스(field-injection)
//@Service("singer")
//@Component("singer") //3-75에서 추가
//@Scope("prototype") //3-75에서 추가
@Component
public class Singer {

	/*
	 * inspirationbean 필드는 private 이지만 IoC 컨테이너가 의존성을 주입하는데 문제가 없다.
	 * 스프링 컨테이너가 리플렉션을 이용해 필요한 의존성을 주입하기 때문이다.
	 */
//	@Autowired
//	private Inspiration inspirationBean;
	
//	public void sing() {
//		System.out.println("... " + inspirationBean.getLyric());
//	}

	//3-75에서 추가
	private String lyric = "I played a quick game of chess with the salt and pepper shaker";

	public void setLyric(@Value("I'am busted up but I'm loving every minute of it") String lyric) {
		this.lyric = lyric;
	}

	public void sing() {
		System.out.println(lyric);
	}
}
