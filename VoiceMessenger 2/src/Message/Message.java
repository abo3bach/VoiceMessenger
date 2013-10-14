package Message;

import java.io.File;

public class Message {
	
	private String sendingUser_;
	private String recievingUser_;
	private File message_;
	
	public Message(String sendingUser, String recievingUser, File message){
		
		this.sendingUser_ = sendingUser;
		this.recievingUser_ = recievingUser;
		this.message_ = message;
	}
	
	public Message(){
		
	}
	
	public String getSender(){
		
		return sendingUser_;
		
	}
	
	public String getReciever(){
		
		return recievingUser_;
		
	}
	
	public File getMessage(){
		
		return message_;
		
	}
	
	
	public void setSender(String sender){
		
		this.sendingUser_ = sender;
		
	}
	
	public void setReciever(String reciever){
		
		this.recievingUser_ = reciever;
		
	}
	
	public void setMessage(File message){
		
		this.message_ = message;
		
	}

	
}
