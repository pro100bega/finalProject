package by.htp6.hospital.command;

public class CommandWasNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;

	public CommandWasNotFoundException() {
		super();
	}
	
	public CommandWasNotFoundException(String message){
		super(message);
	}
	
	public CommandWasNotFoundException(Exception e){
		super(e);
	}
	
	public CommandWasNotFoundException(String message, Exception e){
		super(message, e);
	}
}
