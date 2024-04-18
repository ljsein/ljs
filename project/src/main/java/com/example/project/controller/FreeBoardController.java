package com.example.project.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.project.dto.FreeBoardDTO;
import com.example.project.dto.PageDTO;
import com.example.project.entity.Freeboard;
import com.example.project.service.FreeBoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class FreeBoardController {
   @Autowired
   FreeBoardService service;

   @PostMapping("/board/freeBoardWrite")
   public String freeBoardWrite(FreeBoardDTO dto, Model model, HttpServletRequest request) {
      // 1. 데이터 처리
      HttpSession session = request.getSession();
      dto.setBoard_author((String) session.getAttribute("memName"));
      dto.setBoard_date(new Date());
      System.out.println(dto.toString());
      // db
      boolean result = service.insert(dto);

      // 2. 데이터 공유
      model.addAttribute("result", result);

      /*
       *  3. view 처리 파일 선택 return "/board/freeBoardWrite";
       */
      
      if(result) {
          return "redirect:/board/freeBoardList";
      } else {
          
          return "/board/freeBoardWriteForm";
      }
   }

   @GetMapping("/board/freeBoardWriteForm")
   public String freeBoardWriteForm(Model model, HttpServletRequest request) {
	   HttpSession session = request.getSession();
	      model.addAttribute("memId", session.getAttribute("memId"));
	      model.addAttribute("memName", session.getAttribute("memName"));
      return "/board/freeBoardWriteForm";
   }

   @GetMapping("/board/freeBoardForm")
   public String freeBoardForm() {
      return "/board/freeBoardForm";
   }

   // ModifyForm 이동
   // => 수정하기 위해 제목 내용 불러오기
   @GetMapping("/board/freeBoardList")
   public String freeBoardList(Model model, HttpServletRequest request) {
      // 1. 데이터 처리
      String keyword = request.getParameter("keyword");

      int pg = 1;
      if (request.getParameter("pg") != null) {
         pg = Integer.parseInt(request.getParameter("pg"));
      }

      // System.out.println("keyword= " + keyword + ",pg= " + pg);

      // 1-1) 목록보기 : 5개씩
      int endnum = pg * 5;
      int startnum = endnum - 4;

      List<Freeboard> list = null;
      int totalA = 0;
      if (keyword == null) { // 전체
         // http://localhost:8080/board/freeBoardList?pg=1
         list = service.freeboardList(startnum, endnum);
         totalA = service.getCount();
      } else { // 검색된 결과
         // http://localhost:8080/board/freeBoardList?pg=1&search=%EB%82%B4
         list = service.findByStartnumandEndnumAndKeyword(startnum, endnum, keyword);
         totalA = service.getSearchCount(keyword);
         System.out.println("totalA search = " + totalA);
      }

      // 1-2) 페이징 처리
      // int totalA = dao.getCount();
      System.out.println("totalA :  " + totalA);
      int totalP = (totalA + 4) / 5;

      int startPage = (pg - 1) / 3 * 3 + 1;
      int endPage = startPage + 2;
      if (endPage > totalP)
         endPage = totalP;

      // 페이징 변화와 현재 페이지 정보를 리스트에 저장
      List<PageDTO> pageList = new ArrayList<>();
      for (int i = startPage; i <= endPage; i++) {
         PageDTO pageDTO = new PageDTO();
         pageDTO.setPage(i);
         if (pg == i)
            pageDTO.setCurrent(true);

         pageList.add(pageDTO);
      }

      // 2. 데이터 공유
      System.out.println("keyword= " + keyword);
      model.addAttribute("list", list);
      model.addAttribute("keyword", keyword);
      model.addAttribute("pageList", pageList);
      if (startPage > 3)
         model.addAttribute("previousPage", startPage - 1);
      if (endPage < totalP)
         model.addAttribute("nextPage", endPage + 1);
      model.addAttribute("pg", pg);

      HttpSession session = request.getSession();
      boolean is_login = false;
      if (session.getAttribute("memId") != null)
         is_login = true;

      model.addAttribute("is_login", is_login);

      model.addAttribute("memId", session.getAttribute("memId"));
      model.addAttribute("memName", session.getAttribute("memName"));

      // 3. view 처리 파일 선택
      return "/board/freeBoardList";
   }

   @GetMapping("/board/freeBoardView")
   public String freeBoardView(Model model, HttpServletRequest request) {
      // System.out.println("test: " + request.getParameter("board_seq") + ", " +
      // request.getParameter("pg"));
      // 1. 데이터 처리
      int board_seq = Integer.parseInt(request.getParameter("board_seq"));
      int pg = Integer.parseInt(request.getParameter("pg"));
      // db
      service.UpdateHit(board_seq);
      Freeboard freeboard = service.freeboardView(board_seq); // 상세보기 데이터

      // 로그인한 사람과 글쓴사람이 같은지 검사
      HttpSession session = request.getSession();
      boolean isMemId = false;
      if (freeboard.getBoard_author().equals(session.getAttribute("memId"))) {
         isMemId = true;
         
         
         
      }
      // 2. 데이터 공유
      model.addAttribute("pg", pg);
      model.addAttribute("freeboard", freeboard);
      model.addAttribute("board_seq", board_seq);
      model.addAttribute("isMemId", isMemId);
      
      model.addAttribute("memId", session.getAttribute("memId"));
      model.addAttribute("memName", session.getAttribute("memName"));

      // 3. view 처리 파일 선택
      return "/board/freeBoardView";
   }

   @GetMapping("/board/freeBoardDelete")
   public String boardDelete(Model model, HttpServletRequest request) {
      // 1. 데이터 처리
      int board_seq = Integer.parseInt(request.getParameter("board_seq"));
      int pg = Integer.parseInt(request.getParameter("pg"));
      // db
      Freeboard freeboard = service.freeboardView(board_seq);
      HttpSession session = request.getSession();
      
      boolean result;
      if (freeboard.getBoard_seq() == board_seq) {
         service.freeboardDelete(board_seq);
         result = true;
      } else {
         result = false;
      }
      model.addAttribute("pg", pg);
      model.addAttribute("result", result);


      // 3. view 처리 파일 선택
      return "/board/freeBoardDelete";
   }

   @GetMapping("/board/freeBoardModifyForm")
   public String boardModifyForm(Model model, HttpServletRequest request) {
      int pg = Integer.parseInt(request.getParameter("pg"));
      int board_seq = Integer.parseInt(request.getParameter("board_seq"));

      // db
      Freeboard freeboard = service.freeboardView(board_seq);

      model.addAttribute("pg", pg);
      model.addAttribute("freeboard", freeboard);

      return "/board/freeBoardModifyForm";
   }

   // ModifyForm 이동
   // => 수정하기 위해 제목 내용 불러오기
   @PostMapping("/board/freeBoardModify")
   public String boardModify(Model model, HttpServletRequest request, FreeBoardDTO dto) {
      // 1. 데이터 처리
      System.out.println(dto.toString());
      int pg = Integer.parseInt(request.getParameter("pg"));

      // db
      boolean result = service.modify(dto);

      // 2. 데이터 공유
      model.addAttribute("pg", pg);
      model.addAttribute("board_seq", dto.getBoard_seq());
      model.addAttribute("result", result);

      // 3. view 처리 파일 선택
      return "/board/freeBoardModify";
   }

}