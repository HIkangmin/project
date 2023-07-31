package com.munbanggu.member.dao;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.munbanggu.member.vo.MemberVO;

public interface MemberDAO {
	public MemberVO login(Map loginMap) throws DataAccessException;
	public void insertNewMember(MemberVO memberVO) throws DataAccessException;
	public String selectOverlappedID(String id) throws DataAccessException;
}
