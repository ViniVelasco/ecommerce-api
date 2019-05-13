package com.velasco.ecommerceapi.domain.enums;

public enum Profile {
	
	ADMIN(1, "ROLE_ADMIN"),
	CLIENT(2, "ROLE_CLIENT");
	
	private int code;
	private String description;
	
	private Profile(int code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public static Profile toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		
		for(Profile x: Profile.values()) {
			if(cod.equals(x.getCode())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido " + cod);
	}

}
