package com.example.project.dto;

import java.util.Date;

import com.example.project.entity.Freeboard;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class FreeBoardDTO {
	private int board_seq;
	private String board_author;
	private String board_title;
	private String board_content;
	private Date board_date;
	private int hit;

	// Entity 객체 리턴
	public Freeboard toEntity() {
		return new Freeboard(board_seq, board_author, board_title, board_content, board_date, hit);
	}

}
