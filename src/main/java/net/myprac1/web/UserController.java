package net.myprac1.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.myprac1.domain.User;
import net.myprac1.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/loginForm")
	public String loginForm() {
		return "user/login";
	}

	@PostMapping("/login")
	public String login(String userId, String userPassword, HttpSession session) {
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			System.out.println("login failed!");
			return "redirect:/users/loginForm";
		}
		if (!userPassword.equals(user.getUserPassword())) {
			System.out.println("login failed!");
			return "redirect:/users/loginForm";
		}
		System.out.println("login success!");
		session.setAttribute("sessionedUser", user);
		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionedUser");
		return "redirect:/";
	}

	@GetMapping("/form")
	public String form() {
		return "user/form";
	}

	@PostMapping("")
	public String userCreate(User user) {
		userRepository.save(user);
		return "redirect:/users";
	}

	@GetMapping("")
	public String userList(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "user/list";
	}

	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		Object tempUser = session.getAttribute("sessionedUser");
		if (tempUser == null) {
			return "redirect:/users/loginForm";
		}

		User sessionedUser = (User) tempUser;

		if(!id.equals(sessionedUser.getId())) { 
			throw new IllegalStateException("You can't update the another user"); 
			}
		 // 이  if절을 추가해주던가, 아니면 이 if절을 삭제하고, 바로 아래 줄 User user = userRepository.findById(id).get();
		//이부분 findById의 인자를 sessionedUser.getId() 로 주어도 된다.

		User user = userRepository.findById(id).get();
		model.addAttribute("user", user);
		return "user/updateForm";
	}

	@PostMapping("/{id}")
	public String update(@PathVariable Long id, User updateUser, HttpSession session) {
		Object tempUser = session.getAttribute("sessionedUser");
		if (tempUser == null) {
			return "redirect:/users/loginForm";
		}

		User sessionedUser = (User) tempUser;

		if(!id.equals(sessionedUser.getId())) { 
			throw new IllegalStateException("You can't update the another user"); 
		}
		
		User user = userRepository.findById(id).get();
		user.update(updateUser);
		userRepository.save(user);
		return "redirect:/users";
	}

}
