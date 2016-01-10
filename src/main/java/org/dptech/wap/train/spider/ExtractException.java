package org.dptech.wap.train.spider;

public class ExtractException extends Throwable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7260586775079487954L;

	public ExtractException(){
		super();
	}
	
	public ExtractException(String message){
		super(message);
	}
	
	public ExtractException(String message, Throwable t){
		super(message, t);
	}
	
	
}
