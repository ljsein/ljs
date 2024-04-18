package com.example.project.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Freeboard {  // Free_board
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FREEBOARD_SEQUENCE_GENERATOR")
	@SequenceGenerator(name = "FREEBOARD_SEQUENCE_GENERATOR", sequenceName = "board_seq", initialValue = 1, allocationSize = 1)
	@Id
	private int board_seq;
	private String board_author;
	private String board_title;
	private String board_content;
	private Date board_date;
	private int hit;
}
