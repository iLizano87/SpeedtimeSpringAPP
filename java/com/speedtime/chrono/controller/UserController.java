package com.speedtime.chrono.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.speedtime.chrono.dao.UserDAO;
import com.speedtime.chrono.model.Track;
import com.speedtime.chrono.model.User;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	UserDAO userDAO;

	//Login de user por POST (Seguridad)
	@PostMapping(path = "/loginuser", produces = MediaType.APPLICATION_JSON_VALUE)
	public User loginUser(@RequestBody Map<String, String> request) {
		String email = request.get("email");
		String pass = request.get("pass");

		// Buscar usuario por email en tu base de datos
		User user = userDAO.findByEmail(email);
		if (user == null) {
			// Si no se encuentra el usuario, redirigir a una página de error o mostrar un
			// mensaje de error
			return null;
		}
		// Verificar la contraseña utilizando Jasypt
		PasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		if (passwordEncryptor.checkPassword(pass, user.getPass())) {
			// Contraseña correcta, devolver el usuario como respuesta
			return user;
		} else {
			// Contraseña incorrecta, redirigir a una página de error o mostrar un mensaje
			// de error
			return null;
		}
	}

	//Obtiene user por email
	@GetMapping("/user")
	@ResponseBody
	public boolean getUserByEmail(@RequestParam String email) {
	    User user = userDAO.findByEmail(email);
	    return user != null;
	}

	//Obtiene user por id, produce JSON
	@GetMapping(path = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Optional<User> getUserById(@PathVariable long id, Model model) {
		// Buscar usuario por email y pass en tu base de datos
		Optional<User> user = userDAO.findById(id);
		if (user == null) {
			// Si no se encuentra el usuario, null
			return null;
		}
		// Si se encuentra el usuario, agregarlo al modelo y mostrar la vista
		model.addAttribute("user", user);
		return user;
	}

	//Lista de users en JSON
	@GetMapping(path = "/allusers", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUsers() {
		List<User> userlist = userDAO.findAll();
		return userlist;
	}

	//Modificar user por id, produce JSON
	@PutMapping(path = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public User updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
		try {
			Optional<User> optionalUser = userDAO.findById(userId);
			if (optionalUser.isPresent()) {
				User existingUser = optionalUser.get();

				if (updatedUser.getNick() != null && !updatedUser.getNick().isEmpty()) {
					existingUser.setNick(updatedUser.getNick());
				}
				if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
					existingUser.setEmail(updatedUser.getEmail());
				}
				if (updatedUser.getMode() != null && !updatedUser.getMode().isEmpty()) {
					existingUser.setMode(updatedUser.getMode());
				}
				if (updatedUser.getPhone() != null && !updatedUser.getPhone().isEmpty()) {
					existingUser.setPhone(updatedUser.getPhone());
				}
				if (updatedUser.getBrand() != null && !updatedUser.getBrand().isEmpty()) {
					existingUser.setBrand(updatedUser.getBrand());
				}
				if (updatedUser.getModel() != null && !updatedUser.getModel().isEmpty()) {
					existingUser.setModel(updatedUser.getModel());
				}
				if (updatedUser.getEngine() != null && !updatedUser.getEngine().isEmpty()) {
					existingUser.setEngine(updatedUser.getEngine());
				}

				if (updatedUser.getPass() != null && !updatedUser.getPass().isEmpty()) {
					// Encriptar la nueva contraseña antes de guardarla
					PasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
					String encryptedPassword = passwordEncryptor.encryptPassword(updatedUser.getPass());
					existingUser.setPass(encryptedPassword);
				}

				userDAO.save(existingUser);

				return existingUser;
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	//Registrar user
	@PostMapping("/user")
	public String submitForm(@Valid @RequestBody User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "user-form";
		}
		// Encriptar la contraseña del usuario antes de guardar
		PasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
		String encryptedPassword = passwordEncryptor.encryptPassword(user.getPass());
		user.setPass(encryptedPassword);

		userDAO.save(user);
		return "user-success";
	}

	//Borrar user por id
	@DeleteMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable Long id, Model model) {
		// Buscar usuario por id en base de datos
		User user = userDAO.findUserById(id);
		if (user == null) {
			// Si no se encuentra el usuario, error
			return "error";
		}
		// Si se encuentra el usuario, eliminar
		userDAO.delete(user);
		return "success";
	}

}
