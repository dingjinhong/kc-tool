package kc.tool.lang.constants;

public enum OSType {
	IOS("ios", "ios"), ANDROID("android", "android"), WEB("web","web");
	private String name;
	private String key;
	private OSType(String key,String name){
		this.key=key;
		this.name=name;
	}
	public String getName() {
		
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
