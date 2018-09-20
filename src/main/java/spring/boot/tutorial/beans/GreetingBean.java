package spring.boot.tutorial.beans;

public class GreetingBean {

	private final long id;
	private final String content;

	public GreetingBean(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
}
