package com.munbanggu.admin.company.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.munbanggu.admin.company.dao.AdminCompanyDAO;
import com.munbanggu.company.vo.CompanyVO;


@Service("adminCompanyService")
@Transactional(propagation=Propagation.REQUIRED)
public class AdminCompanyServiceImpl implements AdminCompanyService {
	@Autowired
	private AdminCompanyDAO adminCompanyDAO;
	
	public ArrayList<CompanyVO> listCompany(HashMap condMap) throws Exception{
		return adminCompanyDAO.listCompany(condMap);
	}

	public CompanyVO companyDetail(String company_id) throws Exception{
		 return adminCompanyDAO.CompanyDetail(company_id);
	}
	public void  addCompanyInfo(HashMap companyMap) throws Exception{
 
		 adminCompanyDAO.addCompanyInfo(companyMap);	
	}
	public void  modifyCompanyInfo(HashMap companyMap) throws Exception{
		 String company_id=(String)companyMap.get("company_id");
		 adminCompanyDAO.modifyCompanyInfo(companyMap);
	}
	
	public void  modifyCompany(HashMap companyMap) throws Exception{
		 String company_id=(String)companyMap.get("company_id");
		 adminCompanyDAO.modifyCompany(companyMap);
	}
	public void  deleteCompanyInfo(HashMap companyMap) throws Exception{
		 String company_id=(String)companyMap.get("company_id");
		 adminCompanyDAO.deleteCompanyInfo(companyMap);
	}
	
	@Override
	public int countSelectCompanyList( Map condMap ) throws Exception {
		int count = adminCompanyDAO.countSelectCompanyList( condMap );
		return count;
	}
}
