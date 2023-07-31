package com.munbanggu.company.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.munbanggu.cart.vo.CartVO;
import com.munbanggu.company.vo.CompanyVO;
import com.munbanggu.goods.vo.GoodsVO;

@Repository("companyDAO")
public class CompanyDAOImpl  implements  CompanyDAO{
	@Autowired
	private SqlSession sqlSession;
	
	public List<CompanyVO> selectCompanyList(CompanyVO companyVO) throws DataAccessException {
		List<CompanyVO> companyList =(List)sqlSession.selectList("mapper.company.selectCompanyList",companyVO);
		return companyList;
	}

 
	public boolean selectCountCompany(CompanyVO companyVO) throws DataAccessException {
		String  result =sqlSession.selectOne("mapper.company.selectCountCompany",companyVO);
		return Boolean.parseBoolean(result);
	}

	public void insertCompany(CompanyVO companyVO) throws DataAccessException{
 
 
		sqlSession.insert("mapper.company.insertCompany",companyVO);
	}
	
	public void updateCompany(CompanyVO companyVO) throws DataAccessException{
		sqlSession.insert("mapper.company.updateCompany",companyVO);
	}
	
	public void deleteCompany(int company_id) throws DataAccessException{
		sqlSession.delete("mapper.company.deleteCompany",company_id);
	}


}
