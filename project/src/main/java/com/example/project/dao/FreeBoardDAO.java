package com.example.project.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.project.dto.FreeBoardDTO;
import com.example.project.entity.Freeboard;
import com.example.project.repository.FreeBoardRepository;

@Repository
public class FreeBoardDAO {
   @Autowired
   FreeBoardRepository freeBoardRepository;

   // 글저장하기 : insert
   public boolean insert(FreeBoardDTO dto) {
      // 1. dto를 entity로 변경
      Freeboard freeboard = dto.toEntity();
      // 2. db에 저장
      Freeboard freeboard_result = freeBoardRepository.save(freeboard);

      boolean result = false;
      if (freeboard_result != null)
         result = true;
      return result;
   }

   // 자유게시판 목록보기
   public List<Freeboard> freeboardList(int startnum, int endnum) {
      return freeBoardRepository.findByStartnumandEndnum(startnum, endnum);
   }

   // 총데이터 갯수
   public int getCount() {
      return (int) freeBoardRepository.count();
   }

   // 조회수 증가
   public Freeboard UpdateHit(int board_seq) {
      // 1. seq로 현재 데이터 얻어오기
      Freeboard freeboard = freeBoardRepository.findById(board_seq).orElse(null);
      if (freeboard != null) {
         // 2. 얻어온 데이터 수정 : 조회수 증가
         freeboard.setHit(freeboard.getHit() + 1);
         // 3. 수정된 데이터 저장
         freeboard = freeBoardRepository.save(freeboard);
         System.out.println(freeboard);
      } else {
         System.out.println("게시물이 없습니다.");
      }
      return freeboard;
   }

   // 자유게시판 상세 보기
   public Freeboard freeboardView(int board_seq) {
      return freeBoardRepository.findById(board_seq).orElse(null);
   }

   // 자유게시판 삭제
   public boolean freeboardDelete(int board_seq) {
      // 1. seq 조회하여 삭제할 데이터 값 가져오기
      Freeboard freeboard = freeBoardRepository.findById(board_seq).orElse(null);
      // reservation 값이 있을 때
      if (freeboard != null) {
         // 2. 삭제
         freeBoardRepository.delete(freeboard); // 데이터 삭제
         /*
          * if(boardRepository.existsById(seq)) { System.out.println("삭제를 성공했습니다."); }
          * else { System.out.println("삭제를 실패했습니다."); } } else {
          * System.out.println("게시글이 존재하지 않습니다."); }
          */
      }
      // 데이터가 있는지 없는지 확인하는 함수 boolean 반환
      // true : 삭제 실패, false : 삭제 성공
      return freeBoardRepository.existsById(board_seq);
   }

   // 자유게시판 수정
   public boolean modify(FreeBoardDTO dto) {
      boolean result = false;
      // 1. 수정할 데이터 값 가져오기
      Freeboard freeboard = freeBoardRepository.findById(dto.getBoard_seq()).orElse(null);
      // 수정할 대상이 존재하면
      if (freeboard != null) {
         // 2. 대상 수정하기
         freeboard.setBoard_title(dto.getBoard_title());
         freeboard.setBoard_content(dto.getBoard_content());
         // 3. 저장
         Freeboard freeboard_update = freeBoardRepository.save(freeboard);
         if (freeboard_update != null) {
            result = true;
         } else {
            result = false;
         }
      }
      return result;
   }
   
   // 글 제목 검색
   public List<Freeboard> findByStartnumandEndnumAndKeyword(int startnum, int endnum, String keyword) {
      // System.out.println("test= " + startnum + "," + endnum + "," + keyword);
      keyword = "%" + keyword + "%";
      // System.out.println("test= " + startnum + "," + endnum + "," + keyword);
      return freeBoardRepository.findByStartnumandEndnumAndKeyword(startnum, endnum, keyword);
   }
   
   public int getSearchCount(String keyword) {
      keyword = "%" + keyword + "%";   
      return freeBoardRepository.getSearchCount(keyword);
      //System.out.println(num);
      //return num;
   }
  
}
