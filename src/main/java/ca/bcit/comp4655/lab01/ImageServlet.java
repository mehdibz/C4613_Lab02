package ca.bcit.comp4655.lab01;

import java.io.File;
import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = "/ImageServlet", asyncSupported = true)
@ServletSecurity(@HttpConstraint(transportGuarantee = TransportGuarantee.CONFIDENTIAL))
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = -4513882485288772128L;
	private static Logger logger = LogManager.getLogger( ImageServlet.class );

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

		final String userName = request.getParameter("userName");

		String path = request.getServletContext().getRealPath("/" + userName + ".gif");
		if (path != null && new File(path).exists()) {
			request.setAttribute("img", userName + ".gif");
			request.getSession().setAttribute("path", path);
		}
		
		final PushBuilder pushBuilder = ((HttpServletRequest) request).newPushBuilder();
		pushBuilder.path(path).push();

		doLogging( request );
		request.getRequestDispatcher("index.jsp").forward(request, resp);
	}
	
	private void doLogging(HttpServletRequest request) {
		final AsyncContext asyncContext = request.startAsync();
		asyncContext.addListener(new AsyncListener() {
			
			@Override
			public void onTimeout(AsyncEvent arg0) throws IOException {
				System.out.println("Took too long to long. " + this.getClass().getName() );
			}
			
			@Override
			public void onStartAsync(AsyncEvent event) throws IOException {
				System.out.println("Finding image process started!" + this.getClass().getName() );
			}
			
			@Override
			public void onError(AsyncEvent event) throws IOException {
				System.out.println("Error: " + event.getThrowable().getMessage() );
			}
			
			@Override
			public void onComplete(AsyncEvent arg0) throws IOException {
				System.out.println("Process of finding image is completed" + this.getClass().getName());
			}
		} );
		
	
		asyncContext.start( new Runnable() {
			
			@Override
			public void run() {
					logger.info("User " + request.getParameter( "userName" ) + " used image: " + request.getAttribute("img") );
				}
			});
		asyncContext.complete();
	}

}
