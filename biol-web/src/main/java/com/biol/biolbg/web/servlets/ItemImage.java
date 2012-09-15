package com.biol.biolbg.web.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;

/**
 * Servlet implementation class ItemImage
 */
public class ItemImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	private String imagesPath;       

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		imagesPath = config.getServletContext().getInitParameter(ItemImagesFilenameMapper.IMAGES_PATH);
		if (imagesPath == null) {
		    throw new ServletException("Context parameter 'imagesPath' is not configured.");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get itemId parameter from request
		String imageName = request.getParameter("name");
		if (imageName == null) {
			return;
		}
		File imageFile = new File(imagesPath, imageName);
		if (imageFile != null) {
			//determine the content type of file - if it's not an image quit
			String contentType = getServletContext().getMimeType(imageFile.getName());
			if (contentType == null || !contentType.startsWith("image")) {
				return;
			}
	
	        //init servlet response
	        response.reset();
	        response.setBufferSize(DEFAULT_BUFFER_SIZE);
	        response.setHeader("Content-Type", contentType);
	        response.setHeader("Content-Length", String.valueOf(imageFile.length()));
	        response.setHeader("Content-Disposition", "inline; filename=\"" + imageFile.getName() + "\"");
	
	        //prepare streams
	        BufferedInputStream input = null;
	        BufferedOutputStream output = null;
	
	        try {
	            //open streams
	            input = new BufferedInputStream(new FileInputStream(imageFile), DEFAULT_BUFFER_SIZE);
	            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);
	            //write file contents to response.
	            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
	            int length;
	            while ((length = input.read(buffer)) > 0) {
	                output.write(buffer, 0, length);
	            }
	            //finalize task
	            output.flush();
	        } finally {
	            //close streams
	            output.close();
	            input.close();
	        }
		}
	}	
}
