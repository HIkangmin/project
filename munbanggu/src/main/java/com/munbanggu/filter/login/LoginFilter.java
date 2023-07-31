package com.munbanggu.filter.login ;

import java.io.IOException ;
import javax.servlet.Filter ;
import javax.servlet.FilterChain ;
import javax.servlet.FilterConfig ;
import javax.servlet.ServletException ;
import javax.servlet.ServletRequest ;
import javax.servlet.ServletResponse ;
import javax.servlet.http.HttpServletRequest ;
import javax.servlet.http.HttpServletResponse ;
import javax.servlet.http.HttpSession ;

// 20230622

public class LoginFilter implements Filter
{
	@Override
	public void init( FilterConfig filterConfig ) throws ServletException
	{

	}

	@Override
	public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain )
			throws IOException, ServletException
	{
		HttpServletRequest httpRequest = ( HttpServletRequest ) req ;
		String requestURI = httpRequest.getRequestURI( ) ;
		HttpServletResponse httpResponse = ( HttpServletResponse ) res ;

		HttpSession session = httpRequest.getSession( false ) ;

		System.out.println( "체크 : 주소 = " + requestURI ) ;

		if ( session == null || session.getAttribute( "memberInfo" ) == null )
		{
			httpResponse.sendRedirect( "/main/main.do" ) ;
		} else
		{
			chain.doFilter( req, res ) ;
		}
	}

	@Override
	public void destroy( )
	{

	}
}