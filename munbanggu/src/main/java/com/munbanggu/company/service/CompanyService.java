package com.munbanggu.company.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.munbanggu.cart.vo.CartVO;
import com.munbanggu.company.vo.CompanyVO;
import com.munbanggu.goods.vo.GoodsVO;

public interface CompanyService {
	public Map<String ,List> companyList(CompanyVO companyVO) throws Exception;
	public boolean findCompany(CompanyVO companyVO) throws Exception;
	public void addCompany(CompanyVO companyVO) throws Exception;
	public boolean modifyCompany(CompanyVO companyVO) throws Exception;
	public void removeCompany(int company_id) throws Exception;
}
