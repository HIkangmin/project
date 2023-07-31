package com.munbanggu.admin.customer.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.munbanggu.customer.vo.BoardVO;

public interface AdminCustomerDAO {

	//공지사항
		public List<BoardVO> NoticeList(String startRowNum, String endRowNum);
		public List selectNoticeDetail(String notice_id);
		public void modifyNotice(Map noticeMap) throws DataAccessException;
		public void deleteNotice(String notice_id) throws DataAccessException;
		public void insertNotice(BoardVO BoardVO) throws DataAccessException;
		public List<String> NoticeKeywordSearch(String keyword) throws DataAccessException;
		
		//FAQ
		public List<BoardVO> FaqList(String startRowNum, String endRowNum);
		public List selectFaqDetail(String faq_id);
		public void modifyFaq(Map faqMap) throws DataAccessException;
		public void deleteFaq(String faq_id) throws DataAccessException;
		public void insertFaq(BoardVO BoardVO) throws DataAccessException;
		public List<String> FaqKeywordSearch(String keyword) throws DataAccessException;
		
		//Q&A
		public List<BoardVO> QnaList(String startRowNum, String endRowNum);
		public List selectQnaDetail(String qna_id);
		public void modifyQna(Map qnaMap) throws DataAccessException;
		public List<String> QnaKeywordSearch(String keyword) throws DataAccessException;
	
		
		//Review
		public List<BoardVO> ReviewList(String startRowNum, String endRowNum);
		public List selectReviewDetail(String review_id);
		public void modifyReview(Map reviewMap) throws DataAccessException;
		public List<String> ReviewKeywordSearch(String keyword) throws DataAccessException;

}
