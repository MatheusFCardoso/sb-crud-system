package br.com.mycompany.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
	@Autowired private UserRepository repo;
	
	public List<User> listAll() {
		return (List<User>) repo.findAll();
	}

	public void save(User user) {
		repo.save(user);	
	}
	
	public User get(Integer id) throws UserNotFoundException {
		java.util.Optional<User> result = repo.findById(id);
		if (result.isPresent()) {
			return result.get();
		}
		throw new UserNotFoundException("Não foi possivel encontrar o usuário com ID " + id);
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long count = repo.countById(id);
		if(count == null || count == 0) {
			throw new UserNotFoundException("Não foi possivel encontrar o usuário com ID " + id);
		}
		repo.deleteById(id);
	}
	
	
}
