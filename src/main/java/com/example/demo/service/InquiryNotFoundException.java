package com.example.demo.service;

//独自の例外処理の作成

//updateでIDを指定したが見つからなかった場合の例外

//　一つのメソッド　　　　　に対しての例外処理
//＆一つのControllerクラス　に対しての例外処理

public class InquiryNotFoundException extends RuntimeException {  //RuntimeExceptionを継承
	
	private static final long serialVersionUID = 1L;
	
	public InquiryNotFoundException(String message) {
		super(message);
	}
	
	
}
