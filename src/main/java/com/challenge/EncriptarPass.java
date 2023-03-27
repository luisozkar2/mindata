package com.challenge;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncriptarPass {

	public static void main(String[] args) {
		System.out.println("pass:" + new BCryptPasswordEncoder().encode("medina"));
	}
}