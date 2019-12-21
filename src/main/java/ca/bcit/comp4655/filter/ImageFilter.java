package ca.bcit.comp4655.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.annotation.WebFilter;


@WebFilter ( filterName= "ImageFilter", urlPatterns="/ImageServlet", asyncSupported = true)
@ServletSecurity(@HttpConstraint(transportGuarantee = TransportGuarantee.CONFIDENTIAL))
public class ImageFilter implements Filter
{
	
	private final static String DEFAUL_IMG = "duke.waving.gif";
	@Override
	public void destroy()
	{
		System.out.println( "Good bye from ImageFilter" );	
	}

	@Override
	public void doFilter(final ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
			ServletException
	{
		request.setAttribute( "img", DEFAUL_IMG );
		String userName = request.getParameter( "userName" );
		
		if (  userName!=null )
		{	
			request.setAttribute("userName", userName);
		}
		
		
		filterChain.doFilter(request, response);
		
	}

}
