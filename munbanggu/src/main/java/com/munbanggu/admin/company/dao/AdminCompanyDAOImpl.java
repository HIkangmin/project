package com.munbanggu.admin.company.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.munbanggu.company.vo.CompanyVO;

@Repository("adminCompanyDao")
public class AdminCompanyDAOImpl  implements AdminCompanyDAO{
	@Autowired
	private SqlSession sqlSession;
	
	
	public ArrayList<CompanyVO> listCompany(HashMap condMap) throws DataAccessException{
		ArrayList<CompanyVO>  CompanyList=(ArrayList)sqlSession.selectList("mapper.admin.company.listCompany",condMap);
		return CompanyList;
	}
	
	public CompanyVO CompanyDetail(String company_id) throws DataAccessException{
		CompanyVO companyBean=(CompanyVO)sqlSession.selectOne("mapper.admin.company.companyDetail",company_id);
		return companyBean;
	}
	
	public void modifyCompanyInfo(HashMap companyMap) throws DataAccessException{
		sqlSession.update("mapper.admin.company.modifyCompanyInfo",companyMap);
	}
	public void modifyCompany(HashMap companyMap) throws DataAccessException{
		sqlSession.update("mapper.admin.company.modifyCompany",companyMap);
	}
		
	public void addCompanyInfo(HashMap companyMap) throws DataAccessException{
		sqlSession.insert("mapper.admin.company.addCompanyInfo",companyMap);
	}	
	public void deleteCompanyInfo(HashMap companyMap) throws DataAccessException{
		sqlSession.update("mapper.admin.company.deleteCompanyInfo",companyMap);
	}
	
    public int countSelectCompanyList( Map condMap ) throws DataAccessException {
        int count = sqlSession.selectOne("mapper.admin.company.countSelectCompanyList" , condMap) ;
        return count;
    }
}
