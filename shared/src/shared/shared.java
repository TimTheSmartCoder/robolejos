package shared;

import shared.Message;
import shared.MessageService.IMessageListener;

public class shared {

	public static void main(String[] args) {
		
		MessageService s1 = new MessageService("localhost", 6806, 6805);
		s1.initialize();
		MessageService s2 = new MessageService("localhost", 6805, 6806);
		s2.initialize();
		
		s1.send(new Message("start"));
		
		s1.addMessageListener(new IMessageListener() {

			@Override
			public void onMessageReceived(Message message) {
				// TODO Auto-generated method stub
				System.out.println(message.command);
				s1.send(new Message("Hello from s1"));
			}
		});
		
		s2.addMessageListener(new IMessageListener() {

			@Override
			public void onMessageReceived(Message message) {
				// TODO Auto-generated method stub
				System.out.println(message.command);
				s2.send(new Message("Hello from s2"));
			}
		});
		
		while (true) {
			s1.run();
			s2.run();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
