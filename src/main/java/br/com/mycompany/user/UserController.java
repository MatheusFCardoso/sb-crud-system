package br.com.mycompany.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
	@Autowired private UserService service;
	
	@GetMapping("/users")
	public String showUserList(Model model) {
		List<User> listUsers = service.listAll();
		model.addAttribute("listUsers", listUsers );
		return "users";
	}
	
	@GetMapping("/users/new")
	public String showNewForm(Model model) {
		model.addAttribute("user", new User() );
		model.addAttribute("pageTitle", "Adicionar Novo Usuário" );
		return "user_form";
	}
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes ra) {
		ra.addFlashAttribute("message", "Usuário salvo com sucesso !");
		service.save(user);
		return "redirect:/users";
	}
	
	@GetMapping("/users/edit/{id}")
	public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			User user = service.get(id);
			model.addAttribute("user", user);
			model.addAttribute("pageTitle", "Editando o Usuário com o ID: " + id );
			return "user_form";
		} catch (UserNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			return "redirect:/users";
			
		}

	}
	
	@GetMapping("/users/delet/{id}")
	public String deletUser(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
		try {
			service.delete(id);
			ra.addFlashAttribute("message", "Usuário com ID: " + id + " deletado com sucesso !");
		} catch (UserNotFoundException e) {
			ra.addFlashAttribute("message", e.getMessage());
			e.printStackTrace();
			
		}
		return "redirect:/users";

	}
	
}
