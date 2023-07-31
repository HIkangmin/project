package com.munbanggu.company.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.munbanggu.cart.vo.CartVO;
import com.munbanggu.company.vo.CompanyVO;
import com.munbanggu.goods.vo.GoodsVO;

public interface CompanyDAO {
	public List<CompanyVO> selectCompanyList(CompanyVO companyVO) throws DataAccessException;
 
	public boolean selectCountCompany(CompanyVO companyVO) throws DataAccessException;
	public void insertCompany(CompanyVO companyVO) throws DataAccessException;
	public void updateCompany(CompanyVO companyVO) throws DataAccessException;
	public void deleteCompany(int company_id) throws DataAccessException;
	
	

}
