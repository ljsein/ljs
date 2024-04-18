package com.example.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.dao.FreeBoardDAO;
import com.example.project.dto.FreeBoardDTO;
import com.example.project.entity.Freeboard;

@Service
public class FreeBoardService {
   @Autowired
   FreeBoardDAO freeBoardDAO;

   // 글저장하기 : insert
   public boolean insert(FreeBoardDTO dto) {      
      return freeBoardDAO.insert(dto);
   }

   // 자유게시판 목록보기
   public List<Freeboard> freeboardList(int startnum, int endnum) {
      return freeBoardDAO.freeboardList(startnum, endnum);
   }

   // 총데이터 갯수
   public int getCount() {
      return (int) freeBoardDAO.getCount();
   }

   // 조회수 증가
   public Freeboard UpdateHit(int board_seq) {
      return freeBoardDAO.UpdateHit(board_seq);
   }

   // 자유게시판 상세 보기
   public Freeboard freeboardView(int board_seq) {
      return freeBoardDAO.freeboardView(board_seq);
   }

   // 자유게시판 삭제
   public boolean freeboardDelete(int board_seq) {      
      return freeBoardDAO.freeboardDelete(board_seq);
   }

   // 자유게시판 수정
   public boolean modify(FreeBoardDTO dto) {      
      return freeBoardDAO.modify(dto);
   }

   // 글 제목 검색
   public List<Freeboard> findByStartnumandEndnumAndKeyword(int startnum, int endnum, String keyword) {
      return freeBoardDAO.findByStartnumandEndnumAndKeyword(startnum, endnum, keyword);
   }

   public int getSearchCount(String keyword) {
      return freeBoardDAO.getSearchCount(keyword);
      // System.out.println(num);
      // return num;
   }
}