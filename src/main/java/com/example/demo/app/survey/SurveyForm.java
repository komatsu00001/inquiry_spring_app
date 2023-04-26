package com.example.demo.app.survey;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SurveyForm {
	
	@Min(0)
	@Max(150)
	private int    age;
	@Min(1)
	@Max(5)
	private int    satisfaction;
	@NotNull
	@Size(min = 1, max = 200, message = "1文字以上200文字以内で入力してください。")
	private String comment;
	
	
	public SurveyForm() {
	}
	
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getSatisfaction() {
		return satisfaction;
	}
	public void setSatisfaction(int satisfaction) {
		this.satisfaction = satisfaction;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
