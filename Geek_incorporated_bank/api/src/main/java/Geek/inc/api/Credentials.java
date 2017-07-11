package Geek.inc.api;



public class Credentials {

	private String client;
	
	private String token;
	
	private int pincode;
	
	private boolean state;
	
	private boolean blocked;

	public boolean isBlocked() {
		return this.blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public Credentials() {
		
	}

	public String getClient() {
		return this.client;
	}

	public boolean isState() {
		return this.state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getPincode() {
		return this.pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
	
}
