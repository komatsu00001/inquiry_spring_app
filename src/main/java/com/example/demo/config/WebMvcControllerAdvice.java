package com.example.demo.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;

import com.example.demo.service.InquiryNotFoundException;


/**
 * 全てのControllerで共通処理を定義
 * Controllerが実行する前に起動する
 */
@ControllerAdvice
public class WebMvcControllerAdvice {

	/*
	 * This method changes empty character to null
	 * こちらのメソッドを用意しておくと送信された空文字はnullに変換されます
	 */
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
   
    //SELECT時など、データベースのデータ件数が０件の場合の例外処理
    //この例外処理はすべてのControllerで適用される例外処理
    @ExceptionHandler(EmptyResultDataAccessException.class)//括弧内のクラスthrowされたとき実行
    public String handleException(EmptyResultDataAccessException e, Model model) {
    	model.addAttribute("message", e);
    	return "error/CustomPage";
    }
    
	//全てのControllerクラスで共通の例外処理
    //UPDATE文で指定先が見つからなかった場合
	@ExceptionHandler(InquiryNotFoundException.class)//括弧内のクラスthrowされたとき実行
	public String handleException(InquiryNotFoundException e, Model model) {
		model.addAttribute("message", e);
		return "error/CustomPage";
	}

    
}