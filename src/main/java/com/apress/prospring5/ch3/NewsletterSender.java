package com.apress.prospring5.ch3;

//3-10. Setter-Injection
/*
 * 비지니스 인터페이스에 의존성 주입을 위해 항상 수정자를 정의하지는 않지만,
 * 구성 인자를 정의하는 수정자와 접근자(getter)를 두는 것은 좋은 생각.
 * 구성 인자는 의존성의 특수한 형태로 볼 수 있다.
 * 
 * 방금까지(3-9) 비지니스 인터페이스에 의존성을 정의하지 말아야 한다고 해놓고, 왜 이런 의존성을 정의하는가?
 * 그 이유는 SMTP 서버 주소와 이메일의 발신 주소가 실제로는 의존성이 아니기 때문.
 * 오히려 NewsLetterSender 인터페이스의 모든 구현체가 동작하는데 사용되는 상세 구성 정보를 담고 있기 때문이다.
 * 
 * 구성인자와, 구성인자가 아닌 의존성 사이의 차이점이 무엇인가!
 * 대부분은 해당 의존성이 구성인자인지 바로 알 수 있지만, 확실하지 않다면 구성인자의 세가지 특징을 찾아보면 된다.
 * 1.구성인자는 수동적이다 -> 직접적으로 동작을 수행하는데 사용되지 않는다.
 * 2.구성인자는 일반적으로 다른 어떤 컴포넌트가 아라 정보 자체이다.
 * 3.구성인자는 대개 단순한 값이거나 단순한 값들의 컬렉션이다. -> 정보를 나타내는 용도로 표시.
 * 
 * 비지니스 인터페이스 내에서 구성옵션을 정의할지 말지 고민된다면,
 * 구성인자가 인터페이스 내의 모든 구현체에서 사용될지, 단 하나의 구현체에서만 사용될지 고려하라.
 * 
 */
public interface NewsletterSender {
	void setSmtpServer(String smtpServer);
	String getSmtpServer();
	void setFromAddress(String fromAddress);
	String getFromAddress();
	
	void send();
	
}
