package com.munbanggu.admin.customer.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.munbanggu.customer.vo.BoardVO;


@Repository("AdminCustomerDAO")
public class AdminCustomerDAOImpl implements AdminCustomerDAO {

	@Autowired
	private SqlSession sqlSession;

	//공지사항
	@Override
	public List<BoardVO> NoticeList(String startRowNum, String endRowNum) throws DataAccessException {
		BoardVO boardVO = new BoardVO();
	    boardVO.setStartRowNum(startRowNum);
	    boardVO.setEndRowNum(endRowNum);
	    
	    List<BoardVO> noticeList = sqlSession.selectList("mapper.admin.customer.NoticeList", boardVO);
	    return noticeList;
	}
	
	public List selectNoticeDetail(String notice_id) throws DataAccessException{
		List selectNotice=(List)sqlSession.selectList("mapper.admin.customer.selectNoticeDetail",notice_id);
		return selectNotice;
	}

	@Override
	public void modifyNotice(Map noticeMap) throws DataAccessException{
		sqlSession.update("mapper.admin.customer.modifyNotice",noticeMap);
	}
	
	@Override
	public void deleteNotice(String notice_id) throws DataAccessException{
		sqlSession.delete("mapper.admin.customer.deleteNotice",notice_id);
	}
	
	@Override
	public void insertNotice(BoardVO boardVO) throws DataAccessException{
		sqlSession.insert("mapper.admin.customer.insertNotice",boardVO);
	}
	
	@Override
	public List<String> NoticeKeywordSearch(String keyword) throws DataAccessException {
	   List<String> list=(ArrayList)sqlSession.selectList("mapper.admin.customer.NoticeKeywordSearch",keyword);
	   return list;
	}
	
	
	//FAQ
	@Override
	public List<BoardVO> FaqList(String startRowNum, String endRowNum) throws DataAccessException {
		BoardVO boardVO = new BoardVO();
	    boardVO.setStartRowNum(startRowNum);
	    boardVO.setEndRowNum(endRowNum);
	    
	    List<BoardVO> selectList = sqlSession.selectList("mapper.admin.customer.selectList", boardVO);
	    return selectList;
	}
	
	public List selectFaqDetail(String faq_id) throws DataAccessException{
		List selectFaq=(List)sqlSession.selectList("mapper.admin.customer.selectFaqDetail",faq_id);
		return selectFaq;
	}

	@Override
	public void modifyFaq(Map faqMap) throws DataAccessException{
		sqlSession.update("mapper.admin.customer.modifyFaq",faqMap);
	}
	
	@Override
	public void deleteFaq(String faq_id) throws DataAccessException{
		sqlSession.delete("mapper.admin.customer.deleteFaq", faq_id);
	}
	
	
	
	@Override
	public void insertFaq(BoardVO boardVO) throws DataAccessException{
		sqlSession.insert("mapper.admin.customer.insertFaq",boardVO);
	}
	
	@Override
	public List<String> FaqKeywordSearch(String keyword) throws DataAccessException {
	   List<String> list=(ArrayList)sqlSession.selectList("mapper.admin.customer.FaqKeywordSearch",keyword);
	   return list;
	}
	
	//Q&A
	@Override
	public List<BoardVO> QnaList(String startRowNum, String endRowNum) throws DataAccessException {
		BoardVO boardVO = new BoardVO();
		boardVO.setStartRowNum(startRowNum);
		boardVO.setEndRowNum(endRowNum);
	    
	    List<BoardVO> qnaList = sqlSession.selectList("mapper.admin.customer.QnaList", boardVO);
	    return qnaList;
	}
	
	public List selectQnaDetail(String qna_id) throws DataAccessException{
		List selectQna=(List)sqlSession.selectList("mapper.admin.customer.selectQnaDetail",qna_id);
		return selectQna;
	}

	@Override
	public void modifyQna(Map qnaMap) throws DataAccessException{
		sqlSession.update("mapper.admin.customer.modifyQna",qnaMap);
	}
	
	@Override
	public List<String> QnaKeywordSearch(String keyword) throws DataAccessException {
	   List<String> list=(ArrayList)sqlSession.selectList("mapper.admin.customer.QnaKeywordSearch",keyword);
	   return list;
	}
	
	
	
	
	
	//Review
		@Override
		public List<BoardVO> ReviewList(String startRowNum, String endRowNum) throws DataAccessException {
			BoardVO boardVO = new BoardVO();
			boardVO.setStartRowNum(startRowNum);
			boardVO.setEndRowNum(endRowNum);
		    
		    List<BoardVO> reviewList = sqlSession.selectList("mapper.admin.customer.ReviewList", boardVO);
		    return reviewList;
		}
		
		public List selectReviewDetail(String review_id) throws DataAccessException{
			List selectReview=(List)sqlSession.selectList("mapper.admin.customer.selectReviewDetail",review_id);
			return selectReview;
		}

		@Override
		public void modifyReview(Map reviewMap) throws DataAccessException{
			sqlSession.update("mapper.admin.customer.modifyReview",reviewMap);
		}
		
		@Override
		public List<String> ReviewKeywordSearch(String keyword) throws DataAccessException {
		   List<String> list=(ArrayList)sqlSession.selectList("mapper.admin.customer.ReviewKeywordSearch",keyword);
		   return list;
		}
}
