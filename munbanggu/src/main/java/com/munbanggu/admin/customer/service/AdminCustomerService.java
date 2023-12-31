package com.munbanggu.admin.customer.service;

import java.util.List;
import java.util.Map;

import com.munbanggu.customer.vo.BoardVO;


public interface AdminCustomerService {
	//공지사항
		public Map<String ,List> NoticeList(String startRowNum, String endRowNum) throws Exception;
		public List selectNoticeDetail(String notice_id) throws Exception;
		public void modifyNotice(Map noticeMap) throws Exception;
		public void deleteNotice(String notice_id) throws Exception;
		public void addNotice(BoardVO boardVO) throws Exception;
	    public List<String> NoticekeywordSearch(String keyword);
	    
	    //FAQ
	    public Map<String ,List> FaqList(String startRowNum, String endRowNum) throws Exception;
		public List selectFaqDetail(String faq_id) throws Exception;
		public void modifyFaq(Map faqMap) throws Exception;
		public void deleteFaq(String faq_id) throws Exception;
		public void addFaq(BoardVO boardVO) throws Exception;
	    public List<String> FaqkeywordSearch(String keyword);
	    
	    //Q&A
	    public Map<String ,List> QnaList(String startRowNum, String endRowNum) throws Exception;
		public List selectQnaDetail(String qna_id) throws Exception;
		public void modifyQna(Map qnaMap) throws Exception;
		public List<String> QnakeywordSearch(String keyword);

		//Review
	    public Map<String ,List> ReviewList(String startRowNum, String endRowNum) throws Exception;
		public List selectReviewDetail(String review_id) throws Exception;
		public void modifyReview(Map reviewMap) throws Exception;
		public List<String> ReviewkeywordSearch(String keyword);
	}