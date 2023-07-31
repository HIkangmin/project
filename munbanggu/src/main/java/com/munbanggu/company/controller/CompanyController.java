package com.munbanggu.company.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

public interface CompanyController {
	public ModelAndView companyMain(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public @ResponseBody String addCompany(@RequestParam("company_id") int company_id,HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public  @ResponseBody String modifyCompany(@RequestParam("company_id") int company_id, 
			                  HttpServletRequest request, HttpServletResponse response)  throws Exception;
	public ModelAndView removeCompany(@RequestParam("company_id") int company_id,HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
	

}
