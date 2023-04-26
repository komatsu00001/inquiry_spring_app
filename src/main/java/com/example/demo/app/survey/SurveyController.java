package com.example.demo.app.survey;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Survey;
import com.example.demo.service.SurveyService;

@Controller
@RequestMapping("/survey")
public class SurveyController {
	
	
	//Serviceクラスの呼び出し
	private final SurveyService surveyService;
	@Autowired
	public SurveyController(SurveyService surveyService) {
		this.surveyService = surveyService;
	}
	
	//全件データ表示画面の出力
	@GetMapping
	public String index(Model model) {
		
		List<Survey> list = surveyService.getAll();
		model.addAttribute("surveyList", list);
		model.addAttribute("title", "アンケート一覧");
		return "survey/index";
	}
	
	//アンケート画面の出力
	@GetMapping("/form")
	public String form(SurveyForm surveyForm,
			@ModelAttribute("complete") String complete,
			Model model
			) {
		
		model.addAttribute("title", "アンケートフォーム");
		
		return "survey/form";
		
	}
	
	//戻るボタンでアンケート画面の出力
	@PostMapping("/form")
	public String GoBackForm(SurveyForm surveyForm,
			Model model
			) {
		model.addAttribute("title", "アンケートフォーム");
		
		return "survey/form";
		
	}
	
	//確認画面の出力
	@PostMapping("/confirm")
	public String confirm(@Validated SurveyForm surveyForm,
			BindingResult result,
			Model model
			) {
		
		if (result.hasErrors()) {
			model.addAttribute("title", "アンケートフォーム");
			
			return "survey/form";
		}
		
		model.addAttribute("title", "確認ページ");
		
		return "survey/confirm";
		
	}
	
	//データを送信しアンケート画面に戻る（データ送信なのでリダイレクト）
	@PostMapping("/complete")
	public String complete(@Validated SurveyForm surveyForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes
			) {
		
		if (result.hasErrors()) {
			
			model.addAttribute("title", "アンケートフォーム");
			
			return "survey/form";
		}
		
		//入力データをDBに保存
		Survey survey = new Survey();
		survey.setAge(surveyForm.getAge());
		survey.setSatisfaction(surveyForm.getSatisfaction());
		survey.setComment(surveyForm.getComment());
		survey.setCreated(LocalDateTime.now());
		
		
		surveyService.save(survey);
		
		redirectAttributes.addFlashAttribute("complete", "ご協力ありがとうございました。");
		
		return "redirect:/survey/form";
		
	}
}
