package com.munbanggu.company.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.munbanggu.cart.dao.CartDAO;
import com.munbanggu.cart.vo.CartVO;
import com.munbanggu.company.dao.CompanyDAO;
import com.munbanggu.company.vo.CompanyVO;
import com.munbanggu.goods.vo.GoodsVO;

@Service("companyService")
@Transactional(propagation=Propagation.REQUIRED)
public class CompanyServiceImpl  implements CompanyService{
	@Autowired
	private CompanyDAO companyDAO;
	
	public Map<String ,List> companyList(CompanyVO companyVO) throws Exception{

		Map<String,List> companyMap=new HashMap<String,List>();
		List<CompanyVO> companyList=companyDAO.selectCompanyList(companyVO);
 
 
		companyMap.put("companyList",companyList);
		return companyMap;
	}
	
	public boolean findCompany(CompanyVO companyVO) throws Exception{
		 return companyDAO.selectCountCompany(companyVO);
		
	}	
	public void addCompany(CompanyVO companyVO) throws Exception{
		companyDAO.insertCompany(companyVO);
	}
	
	public boolean modifyCompany(CompanyVO companyVO) throws Exception{
		boolean result=true;
		companyDAO.updateCompany(companyVO);
		return result;
	}
	public void removeCompany(int cart_id) throws Exception{
		companyDAO.deleteCompany(cart_id);
	}
	
}
