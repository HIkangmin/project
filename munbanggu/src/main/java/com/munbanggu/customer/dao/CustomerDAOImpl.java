package com.munbanggu.customer.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.munbanggu.customer.vo.BoardVO;
import com.munbanggu.customer.vo.NoticeVO;



@Repository("CustomerDAO")
public class CustomerDAOImpl  implements CustomerDAO{
	@Autowired
	private SqlSession sqlSession;

	//공지사항
	@Override
	public List<BoardVO> NoticeList(String startRowNum, String endRowNum) throws DataAccessException {
		BoardVO boardVO = new BoardVO();
		boardVO.setStartRowNum(startRowNum);
	    boardVO.setEndRowNum(endRowNum);
	    
	    List<BoardVO> noticeList = sqlSession.selectList("mapper.customer.NoticeList", boardVO);
	    return noticeList;
	}
	
	public List selectNoticeDetail(String notice_id) throws DataAccessException{
		List selectNotice=(List)sqlSession.selectList("mapper.customer.selectNoticeDetail",notice_id);
		return selectNotice;
	}
	
	@Override
	public List<String> NoticeKeywordSearch(String keyword) throws DataAccessException {
	   List<String> list=(ArrayList)sqlSession.selectList("mapper.customer.NoticeKeywordSearch",keyword);
	   return list;
	}
	
	//FAQ
		@Override
		public List<BoardVO> FaqList(String startRowNum, String endRowNum) throws DataAccessException {
			BoardVO boardVO = new BoardVO();
			
		     boardVO.setStartRowNum(startRowNum);
		    boardVO.setEndRowNum(endRowNum);
		    
		    List<BoardVO> selectList = sqlSession.selectList("mapper.customer.FaqList", boardVO);
		    return selectList;
		}
		
		public List selectFaqDetail(String faq_id) throws DataAccessException{
			List selectFaq=(List)sqlSession.selectList("mapper.customer.selectFaqDetail",faq_id);
			return selectFaq;
		}
		
		@Override
		public List<String> FaqKeywordSearch(String keyword) throws DataAccessException {
		   List<String> list=(ArrayList)sqlSession.selectList("mapper.customer.FaqKeywordSearch",keyword);
		   return list;
		}
		
		
		
		
	//QNA
	@Override
	public List<BoardVO> QnaList(String startRowNum, String endRowNum) throws DataAccessException {
		BoardVO boardVO = new BoardVO();
		
	     boardVO.setStartRowNum(startRowNum);
	    boardVO.setEndRowNum(endRowNum);
	    
	    List<BoardVO> qnaList = sqlSession.selectList("mapper.customer.QnaList", boardVO);
	    return qnaList;
	}
	
	public List selectQnaDetail(String qna_id) throws DataAccessException{
		List selectQna=(List)sqlSession.selectList("mapper.customer.selectQnaDetail",qna_id);
		return selectQna;
	}
	
	
	@Override
	public int insertQna(Map newQnaMap) throws DataAccessException {
		sqlSession.insert("mapper.customer.insertQna",newQnaMap);
		return (Integer)newQnaMap.get("qna_id");
	}
	
	/*
	 * @Override public List<String> QnaKeywordSearch(String keyword) throws
	 * DataAccessException { List<String>
	 * list=(ArrayList)sqlSession.selectList("mapper.customer.QnaKeywordSearch",
	 * keyword); return list; }
	 */
}
