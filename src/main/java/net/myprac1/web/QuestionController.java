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

		try {
			Question question = questionRepository.findById(id).get();
			hasPermission(session, question);
			model.addAttribute("question", question);
			return "/qna/updateForm";
		} catch (IllegalStateException e) {
			System.out.println("오류메세지 발생");
			model.addAttribute("errorMessage", e); //이게 안담기는 것 같다 더 알아보자
			return "redirect:/users/loginForm";
		}		

	}
	
	
	//글 수정삭제 권한 있는지 알아오는 메소드
	private boolean hasPermission(HttpSession session, Question question) {
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if(!HttpSessionUtils.isLoginUser(session)) {
			throw new IllegalStateException("로그인이 필요합니다");

		}	
		else if(!question.isSameWriter(loginUser)) {			
			throw new IllegalStateException("자신이 쓴 글만 수정, 삭제가 가능합니다.");

		}
		else {
				return true;
		}



		

	}
	
	
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, String title, String contents, Model model, HttpSession session) {
		try {
			Question question = questionRepository.findById(id).get();
			hasPermission(session, question);
			question.update(title, contents);
			questionRepository.save(question);
			return String.format("redirect:/questions/%d", id);
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "redirect:/users/loginForm";
		}	
		


	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id, Model model, HttpSession session) {
		try {
			Question question = questionRepository.findById(id).get();
			hasPermission(session, question);
			questionRepository.deleteById(id);
			return "redirect:/";
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "redirect:/users/loginForm";
		}
		

	}
	
}
