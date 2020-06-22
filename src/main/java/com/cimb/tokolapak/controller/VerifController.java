package com.cimb.tokolapak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.UserRepo;
import com.cimb.tokolapak.entity.User;

@RestController
@RequestMapping("/verif")
public class VerifController {

	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/verifUser")
	public User verifikasiEmailUser(@RequestParam String username) {
		
		User findUser = userRepo.findByUsername(username).get();
		if (findUser!= null) {
			findUser.setVerified(true);
			userRepo.save(findUser);
			return findUser;
		} else {
			return null;
		}
	}
}
