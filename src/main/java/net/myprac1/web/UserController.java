package net.myprac1.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.myprac1.domain.User;
import net.myprac1.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
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
	public String updateForm(@PathVariable Long id, Model model) {
		User user = userRepository.findById(id).get();  
		model.addAttribute("user", user);
		return "user/updateForm";
	}
	
	@PostMapping("/{id}")
	public String update(@PathVariable Long id, User updateUser) {
		User user = userRepository.findById(id).get();  
		user.update(updateUser);
		userRepository.save(user);
		return "redirect:/users";
	}
	
}
