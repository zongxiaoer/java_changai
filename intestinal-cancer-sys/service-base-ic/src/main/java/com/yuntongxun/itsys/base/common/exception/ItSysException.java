package com.yuntongxun.itsys.base.common.exception;

public class ItSysException extends RuntimeException {

	private static final long serialVersionUID = -4137938275882097489L;
	//
	// public ItSysException(String message){
	// super(message);
	// }

	private String code;

	public ItSysException() {
		super();
	}

	public ItSysException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
