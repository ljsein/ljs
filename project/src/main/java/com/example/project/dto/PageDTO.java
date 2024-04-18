package com.example.project.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PageDTO {
	private int page;			// 페이지
	private boolean current;	// 현재 페이지
}
