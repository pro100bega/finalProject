package by.htp6.hospital.dao.pool.exception;

public class ConnectionIsNullException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ConnectionIsNullException() {
		super();
	}
	
	public ConnectionIsNullException(String message){
		super(message);
	}
	
	public ConnectionIsNullException(Exception e){
		super(e);
	}
	
	public ConnectionIsNullException(String message, Exception e){
		super(message, e);
	}
}
