package net.myprac1.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.myprac1.domain.Question;
import net.myprac1.domain.QuestionRepository;
import net.myprac1.domain.Result;
import net.myprac1.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@GetMapping("/form")
	public String form(HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}
		return "qna/form";
	}
	
	@PostMapping("")
	public String create(String title, String contents, HttpSession session) {
		if(!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/loginForm";
		}		
		User sessionUser = HttpSessionUtils.getUserFromSession(session);	
		Question newQuestion = new Question(sessionUser, title, contents);
		questionRepository.save(newQuestion);
		return "redirect:/";
	}
	
	@GetMapping("/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("question", questionRepository.findById(id).get());
		return "/qna/show";
	}
	
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		Question question = questionRepository.findById(id).get();
		Result result = valid(session, question);
		if(!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "redirect:/users/loginForm";
		}

		model.addAttribute("question", question);
		return "/qna/updateForm";
	

	}
	//글 수정삭제 권한 있는지 알아오는 메소드(별도 클래스 사용시)
	private Result valid(HttpSession session, Question question) {
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if(!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이 필요합니다.");
		}	
		if(!question.isSameWriter(loginUser)) {			
			return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}
		return Result.ok();
	}
	//글 수정삭제 권한 있는지 알아오는 메소드(exception 사용시)
	private boolean hasPermission(HttpSession session, Question question) {
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if(!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException("로그인이 필요합니다");

		}	
		if(!question.isSameWriter(loginUser)) {			
			throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능합니다.");

		}

		return true;
	}
	
	
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
		Question question = questionRepository.findById(id).get();
		Result result = valid(session, question);
		if(!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "redirect:/users/loginForm";
		}
		
		question.update(title, contents);
		questionRepository.save(question);
		return String.format("redirect:/questions/%d", id);

	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, Model model, HttpSession session) {
		Question question = questionRepository.findById(id).get();
		Result result = valid(session, question);
		if(!result.isValid()) {
			model.addAttribute("errorMessage", result.getErrorMessage());
			return "redirect:/users/loginForm";
		}
		questionRepository.deleteById(id);
		return "redirect:/";		

	}
	
}
