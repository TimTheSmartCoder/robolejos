package server;

import shared.MessageService;
import shared.MessageService.Message;

public class server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MessageService messageService = new MessageService("192.168.1.102", 6800, 6801);
		messageService.initialize();
		messageService.send(new Message("forward", ""));
		while(true) {
			messageService.run();
		}
	}

}
