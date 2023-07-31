package com.munbanggu.common.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.munbanggu.common.vo.CommonSelectVO;
import com.munbanggu.company.vo.CompanyVO;

public interface CommonDAO {
	
	public ArrayList<Object> commonSearchSelectList(Map condMap) throws DataAccessException;
	
	public int commonCountSelect( Map condMap ) throws DataAccessException ;
	
	

}
