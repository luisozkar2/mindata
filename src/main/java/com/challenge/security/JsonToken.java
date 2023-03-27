package com.challenge.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JsonToken {
	private String token;
}
