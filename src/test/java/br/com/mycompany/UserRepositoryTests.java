package br.com.mycompany;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import br.com.mycompany.user.User;
import br.com.mycompany.user.UserRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	@Autowired private UserRepository repo;
	

	
	@Test
	public void testListAll() {
		Iterable<User> users = repo.findAll();
		Assertions.assertThat(users).hasSizeGreaterThan(0);
		
		for (User user : users) {
			System.out.println(user);
		}
		
	}
	
	@Test
	public void testUpdate() {
		Integer userId = 10;
		java.util.Optional<User> optionalUser =  repo.findById(userId);
		User user = optionalUser.get();
		user.setPassword("hello2000");
		repo.save(user);
		
		User updateUser = repo.findById(userId).get();
		Assertions.assertThat(updateUser.getPassword()).isEqualTo("hello2000");
	}
	
	@Test 
	public void testDelete() {
		Integer userId = 10;
		repo.deleteById(userId);
		java.util.Optional<User> optionalUser =  repo.findById(userId);
		Assertions.assertThat(optionalUser).isNotPresent();
	}
	
	
	
}
