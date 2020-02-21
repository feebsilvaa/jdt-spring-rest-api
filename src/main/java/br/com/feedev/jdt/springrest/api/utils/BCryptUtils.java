package br.com.feedev.jdt.springrest.api.utils;

public class BCryptUtils {

	public static void main(String[] args) {
		String crypt = criptografar("user1");
		System.out.println(crypt);
	}

	public static String criptografar(String value) {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		return encoder.encode(value);
		return value;
	}
	
}
