package messageservice;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageService {
	
	public static class Message {
		
		public String command;
		public Object value;
		
		public Message(String command, Object value) {
			this.command = command;
			this.value = value;
		}
	}
	
	public class MessageServiceClient {
		
		private final int _port;
		private final String _host;
		
		public MessageServiceClient(String host, int port) {
			this._port = port;
			this._host = host;
		}
		
		public void send(final Message message) {
			// TODO Auto-generated method stub
			try {
				Socket socket = new Socket(_host, _port);
				System.out.println("Sending data (" + message.command + "): " + message.value);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				objectOutputStream.writeObject(message.command);
				objectOutputStream.writeObject(message.value);
				objectOutputStream.flush();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Failed to send data: " + message.command);
			}
		}
		
		public void send(List<Message> messages) {
			try {
				Socket socket = this.createSocket();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
				for(Message message : messages) {
					objectOutputStream.writeObject(message.command);
					objectOutputStream.writeObject(message.value);
				}
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public Socket createSocket() throws UnknownHostException, IOException {
			return new Socket(this._host, this._port);
		}
	}
	
	public class MessageServiceServer {
		
		/**
		 * Port number of which the server will listen for
		 * clients to connect.
		 */
		private final int _port;
		
		/**
		 * Thread which server is running on.
		 */
		private Thread _serverThread;
		
		/**
		 * Queue for containing incoming messages.
		 */
		private final BlockingQueue<Message> _messageQueue;
		
		/**
		 * Constructs an MessageService with the given port.
		 * @param port - Port number to listen for clients.
		 */
		public MessageServiceServer(int port) {
			this._port = port;
			this._messageQueue = new LinkedBlockingQueue<Message>();
		}
		
		public void initialize() {
			
			this._messageQueue.clear();
			
			//Kill the existing server thread, if any.
			if (this._serverThread != null 
					&& !this._serverThread.isInterrupted()) {
				this._serverThread.interrupt();
				this._serverThread = null;
			}
			
			this._serverThread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						ServerSocket serverSocket = null;
						serverSocket = new ServerSocket(_port);
						while(!serverSocket.isClosed()) {
							Socket socket = serverSocket.accept();
							ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
							Message message = new Message("", null);
							try {
								try {
									while(!socket.isClosed() && (message.command = (String)objectInputStream.readObject()) != null) {
										message.value = objectInputStream.readObject();
										System.out.println("Received data (" + message.command + "): " + message.value + " : from " + socket.getRemoteSocketAddress());
										_messageQueue.offer(message);
									}
								} catch (EOFException e) {
									//Ignore EOFException for now.
								}
								
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
									e.printStackTrace();
							}
						}
						serverSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			
			this._serverThread.start();
		}
		
		public Message getMessage() {
			try {
				{
					System.out.println("Message queue size: " + this._messageQueue.size());
					if (this._messageQueue.size() > 2) {
						System.out.println("Shorting message queue!!!! size is: " + this._messageQueue.size() );
						Message message = this._messageQueue.take();
						this._messageQueue.clear();
						this._messageQueue.put(new Message(Commands.STOP, null));
						return message;
					}
				}
				return this._messageQueue.take();
			} catch (InterruptedException e) {
				return  null;
			}
		}
		
		public boolean isMessageAvailable() {
			return !this._messageQueue.isEmpty();
		}
		
		public void close() {
			//Kill the existing server thread, if any.
			if (this._serverThread != null 
					|| !this._serverThread.isInterrupted()) {
				this._serverThread.interrupt();
				this._serverThread = null;
			}
		}
	}
	
	public interface IMessageListener {
		
		/**
		 * Called when a message is received.
		 */
		void onMessageReceived(Message message);
	}
	
	private List<IMessageListener> _messageListeners;
	
	/**
	 * MessageServiceClient to communicate to an server.
	 */
	private MessageServiceClient _messageServiceClient;
	
	/**
	 * MessageServceServer to communicate to an client.
	 */
	private MessageServiceServer _messageServiceServer;
	
	/**
	 * Server host to connect to.
	 */
	private String serverHost;
	
	/**
	 * Port for both listening and connect to server.
	 */
	private int _serverPort;
	
	private int _clientPort;
	
	/**
	 * Constructs an MessageService which use the given server host and port.
	 * @param serverHost - Server host to connect to.
	 * @param port - Port of the both server and client.
	 */
	public MessageService(String serverHost, int serverPort, int clientPort) {
		this.serverHost = serverHost;
		this._serverPort = serverPort;
		this._clientPort = clientPort;
		this._messageListeners = new ArrayList<IMessageListener>();
	}
	
	public void initialize() {
		
		//If any message service server running kill it.
		if (this._messageServiceServer != null)
			this._messageServiceServer.close();
		
		//Instancetiate both client and server.
		this._messageServiceClient = new MessageServiceClient(this.serverHost, this._serverPort);
		this._messageServiceServer = new MessageServiceServer(this._clientPort);
		this._messageServiceServer.initialize();
	}
	
	public void run() {
		
		//Check for any messages recevied.
		while(this._messageServiceServer.isMessageAvailable()) {
			Message message = this._messageServiceServer.getMessage();
			if (message != null) this.invokeMessageListeners(message);
		}
	}
	
	public void send(Message message) {
		this._messageServiceClient.send(message);
	}
	
	public void send(List<Message> messages) {
		this._messageServiceClient.send(messages);
	}
	
	public void addMessageListener(IMessageListener listener) {
		this._messageListeners.add(listener);
	}
	
	public void setClientConnection(String serverHost, int serverPort) {
		
	}
	
	private void invokeMessageListeners(Message message) {
		for(IMessageListener listener : this._messageListeners)
			listener.onMessageReceived(message);
	}
}