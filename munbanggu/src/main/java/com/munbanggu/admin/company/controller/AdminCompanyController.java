package com.munbanggu.admin.company.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.munbanggu.member.vo.MemberVO;

public interface AdminCompanyController {
	public ModelAndView adminCompanyMain(@RequestParam Map<String, Object> dateMap,HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView CompanyDetail(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public void modifyCompanyInfo(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView   addCompanyInfo(@RequestParam HashMap receiveMap,HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView   modifyCompany(@RequestParam HashMap receiveMap,HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView deleteCompany(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView deleteCompanyInfo(HttpServletRequest request, HttpServletResponse response)  throws Exception;
}
