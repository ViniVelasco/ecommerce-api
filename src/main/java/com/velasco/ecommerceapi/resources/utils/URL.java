package com.velasco.ecommerceapi.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

	/**
	 * 
	 * String[] urlSplit = s.split(","); List<Integer> list = new ArrayList<>();
	 * for(int i=0; i<urlSplit.length;i++) {
	 * list.add(Integer.parseInt(urlSplit[i])); }
	 * 
	 * @param s
	 * @return
	 */
	public static List<Integer> decodeIntList(String s) {
		return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

}
