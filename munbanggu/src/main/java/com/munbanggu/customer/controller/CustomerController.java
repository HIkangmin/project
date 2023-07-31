package com.munbanggu.customer.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.munbanggu.customer.vo.BoardVO;


public interface CustomerController {
	//공지사항
	public ModelAndView noticeMain(@RequestParam Map<String, Object> dateMap,
			HttpServletRequest request)  throws Exception;
	public ModelAndView selectNotice(@RequestParam("notice_id") String notice_id, HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView NoticekeywordSearch(@RequestParam("keyword") String keyword) throws Exception;

	
	//FAQ
		public ModelAndView faqMain(@RequestParam Map<String, Object> dateMap,
				HttpServletRequest request)  throws Exception;
		public ModelAndView FaqkeywordSearch(@RequestParam("keyword") String keyword) throws Exception;
		
	//Q&A
	public ModelAndView qnaMain(@RequestParam Map<String, Object> dateMap,
			HttpServletRequest request)  throws Exception;
	public ModelAndView selectQna(@RequestParam("qna_id") String qna_id,HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView qnaWrite(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ResponseEntity addQna(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)  throws Exception;
		
		
}