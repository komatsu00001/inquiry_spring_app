package com.example.demo.app.inquiry;



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

import com.example.demo.entity.Inquiry;
import com.example.demo.service.InquiryService;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {
	
	//serviceクラスの呼び出し
	private final InquiryService inquiryService;
	@Autowired
	public InquiryController(InquiryService inquiryService) {
		this.inquiryService = inquiryService;
	}
	
	//データ一覧を表示
	@GetMapping
	public String index(Model model) {
		List<Inquiry> list = inquiryService.getAll();
		
		
		//例外処理テスト用
//		Inquiry inquiry = new Inquiry();
//		inquiry.setId(4);
//		inquiry.setName("kakikukeko");
//		inquiry.setEmail("sample4@example.com");
//		inquiry.setContents("not found");
//		
//		inquiryService.update(inquiry);
		
//		//１つのメソッドに対して個別の例外処理
//		try {
//			inquiryService.update(inquiry);
//		} catch (InquiryNotFoundException e) {
//			model.addAttribute("message", e);
//			return "error/CustomPage";
//		}
		
		
		model.addAttribute("inquiryList", list);
		model.addAttribute("title", "お問い合わせ一覧");
		
		return "inquiry/index_boot";
	}
	
	//ログイン画面表示
	@GetMapping("/form")
	public String form(InquiryForm inquiryForm,
			Model model,
			@ModelAttribute("complete") String complete   //フラッシュスコープの値を呼び出し
			) {
		model.addAttribute("title", "お問い合わせフォーム");
		
		return "inquiry/form-boot";
	}
	
	
	//戻りボタンでログインに返る時
	@PostMapping("/form")
	public String formGoBack(InquiryForm inquiryForm ,
			Model model) {
		model.addAttribute("title", "お問い合わせフォーム");
		
		return "inquiry/form-boot";
	}
	
	
	//確認ページの出力
	@PostMapping("/confirm")
	public String confim(@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model) {
		
		if (result.hasErrors()) {
			model.addAttribute("title", "お問い合わせフォーム");
			return "inquiry/form-boot";
		}
		
		model.addAttribute("title", "確認ページ");
		
		return "inquiry/confirm-boot";
	}
	
	
	//完了処理をしログイン画面に表示
	//二重クリックでデータの重複を防ぐためリダイレクトを利用
	@PostMapping("/complete")
	public String complete(
			@Validated InquiryForm inquiryForm,
			BindingResult result,
			Model model,
			RedirectAttributes redirectAttributes
			) {
		if (result.hasErrors()) {
			model.addAttribute("title", "お問い合わせフォーム");
			return "form-boot";
		}
		
		//入力情報をDBに保存する
		//inquiryFormのデータをinquiryに詰め替える
		Inquiry inquiry = new Inquiry();
		inquiry.setName(inquiryForm.getName());
		inquiry.setEmail(inquiryForm.getEmail());
		inquiry.setContents(inquiryForm.getContents());
		inquiry.setCreated(LocalDateTime.now());
		
		inquiryService.save(inquiry);
		
		//Modelはリダイレクトで使えないためフラッシュスコープで送信
		redirectAttributes.addFlashAttribute("complete", "お問い合わせは送信されました。");
		
		return "redirect:/inquiry/form";
		
	}
//	//1つのControllerクラスで共通の例外処理
//	@ExceptionHandler(InquiryNotFoundException.class)//括弧内のクラスthrowされたとき実行
//	public String handleException(InquiryNotFoundException e, Model model) {
//		model.addAttribute("message", e);
//		return "error/CustomPage";
//	}
}
