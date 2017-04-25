package shared;

import java.io.Serializable;

public class Message implements Serializable {
	public Message(String command) {
		this.command = command;
	}
	
	public String command;
}