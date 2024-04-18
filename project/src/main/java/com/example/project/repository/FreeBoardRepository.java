package com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project.entity.Freeboard;


public interface FreeBoardRepository extends JpaRepository<Freeboard, Integer> {
      @Query(value = "select * from " + "(select rownum rn, tt.* from "
            + "(select * from freeBoard order by board_seq desc) tt) "
            + "where rn>=:startnum and rn<=:endnum", nativeQuery = true)
      List<Freeboard> findByStartnumandEndnum(@Param("startnum") int startnum, @Param("endnum") int endnum);

      // 검색목록
      @Query(value = "select * from " + "(select rownum rn, tt.* from "
            + "(select * from freeBoard where board_title like :keyword order by board_seq desc) tt) "
            + "where rn>=:startnum and rn<=:endnum", nativeQuery = true)
      List<Freeboard> findByStartnumandEndnumAndKeyword(@Param("startnum") int startnum, @Param("endnum") int endnum,
            @Param("keyword") String keyword);
      
      @Query(value="select count(*) as cnt from (select * from freeBoard where board_title like :keyword order by board_seq desc)", nativeQuery = true)
      int getSearchCount(@Param("keyword") String keyword);
      
   }