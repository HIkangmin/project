package com.munbanggu.admin.company.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.munbanggu.company.vo.CompanyVO;

public interface AdminCompanyService {
	public ArrayList<CompanyVO> listCompany(HashMap condMap) throws Exception;
	public CompanyVO companyDetail(String company_id) throws Exception;
	public void  modifyCompanyInfo(HashMap CompanyMap) throws Exception;
	public void  modifyCompany(HashMap CompanyMap) throws Exception;
	public void  addCompanyInfo(HashMap CompanyMap) throws Exception;
	public void  deleteCompanyInfo(HashMap CompanyMap) throws Exception;
	public int   countSelectCompanyList( Map condMap ) throws Exception ;
}
