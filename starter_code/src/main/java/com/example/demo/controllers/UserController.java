package com.example.demo.controllers;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;

//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		return ResponseEntity.of(userRepository.findById(id));
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		return user == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(user);
	}

	@PostMapping("/create")
	public ResponseEntity createUser(@RequestBody CreateUserRequest createUserRequest) {
		log.info("UserController:createUser method execution started.. ");

		String pwd = createUserRequest.getPassword();
		if(pwd.equals(createUserRequest.getConfirmPassword()) &&
				pwd.length()>7) {
			User user = new User();
			user.setUsername(createUserRequest.getUsername());
				log.info("user name set with " + createUserRequest.getUsername());

//			user.setSalt(createSalt());

//			user.setPassword(bCryptPasswordEncoder.encode(pwd));
			user.setPassword(pwd);

			Cart cart = new Cart();
			cartRepository.save(cart);
			user.setCart(cart);

			userRepository.save(user);
			log.info("UserController:createUser method execution ended.. ");

			return ResponseEntity.ok(user);
		} else {
			log.info("password and confirmPassword are not matched. Please try again.");
			return ResponseEntity.badRequest().build();
		}
	}
	
//	@PostMapping("/create")
//	public ResponseEntity createUser0(@RequestBody CreateUserRequest createUserRequest) {
//		log.info("UserController:createUser method execution started.. ");
//
//		String pwd = createUserRequest.getPassword();
//		if(pwd.equals(createUserRequest.getConfirmPassword()) &&
//			pwd.length()>7) {
//			User user = new User();
//			user.setUsername(createUserRequest.getUsername());
//			log.info("user name set with " + createUserRequest.getUsername());
//			user.setSalt(createSalt());
////			user.setPassword(bCryptPasswordEncoder.encode(pwd));
//			user.setPassword(pwd);
//
//			Cart cart = new Cart();
//			cartRepository.save(cart);
//			user.setCart(cart);
//			userRepository.save(user);
//			log.info("UserController:createUser method execution ended.. ");
//			return ResponseEntity.ok(user);
//		} else {
//			log.info("password and confirmPassword are not matched. Please try again.");
//			return ResponseEntity.ok("password and confirmPassword are not matched. Please try again.");
//		}
//	}

	// Method to generate a Salt
//	private static byte[] createSalt() {
//		SecureRandom random = new SecureRandom();
//		byte[] salt = new byte[16];
//		random.nextBytes(salt);
//		System.out.println(Arrays.toString(salt));
//		return salt;
//	}
	
}
