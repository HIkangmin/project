package com.munbanggu.common.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;


@Repository("commonDAO")
public class CommonDAOImpl  implements  CommonDAO{
	@Autowired
	private SqlSession sqlSession;

	@Override
	public ArrayList<Object> commonSearchSelectList(Map condMap) throws DataAccessException {
		ArrayList<Object>  selectList=(ArrayList)sqlSession.selectList("mapper.common.commonSearchSelectList",condMap);
		return selectList;
	}

	@Override
	public int commonCountSelect(Map condMap) throws DataAccessException {
		int count = sqlSession.selectOne("mapper.common.commonCountSelect" , condMap) ;
		return count;
	}


	
	



}
