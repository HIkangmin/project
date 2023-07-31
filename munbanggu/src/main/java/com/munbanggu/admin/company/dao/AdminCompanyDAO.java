package com.munbanggu.admin.company.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.munbanggu.company.vo.CompanyVO;

public interface AdminCompanyDAO {
	public ArrayList<CompanyVO> listCompany(HashMap condMap) throws DataAccessException;
	public CompanyVO CompanyDetail(String company_id) throws DataAccessException;
	public void modifyCompanyInfo(HashMap companyMap) throws DataAccessException;

	public void modifyCompany(HashMap companyMap) throws DataAccessException;
	public void addCompanyInfo(HashMap companyMap) throws DataAccessException;
	public void deleteCompanyInfo(HashMap companyMap) throws DataAccessException;
	
	public int countSelectCompanyList( Map condMap ) throws DataAccessException ;
}
