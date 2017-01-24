package by.htp6.hospital.dao.factory.exception;

public class WrongTechnologyException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public WrongTechnologyException(){
		super();
	}
	
	public WrongTechnologyException(String message){
		super(message);
	}
	
	public WrongTechnologyException(Exception e){
		super(e);
	}
	
	public WrongTechnologyException(String message, Exception e){
		super(message,e);
	}
}
