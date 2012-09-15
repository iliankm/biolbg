package com.biol.biolbg.web.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.biol.biolbg.web.util.FileUtil;
import com.biol.biolbg.web.util.cdi.ItemImagesFilenameMapper;


/**
 * Servlet implementation class UploadImage
 */
public class UploadImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String uploadImagePath;
	private Integer maxImageFileSize;

	public void init(ServletConfig config) throws ServletException {
		uploadImagePath = config.getServletContext().getInitParameter(ItemImagesFilenameMapper.IMAGES_PATH);
        if (uploadImagePath == null) {
            throw new ServletException("Context parameter 'imagesPath' is not configured.");
        }
        String strMaxImageFileSize = config.getServletContext().getInitParameter("maxImageFileSize");
        try {
        	maxImageFileSize = Integer.parseInt(strMaxImageFileSize);
        } catch (Exception e) {
        	throw new ServletException("Context parameter 'maxImageFileSize' is not configured or is not a number.");
        }
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response); 
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter( );

	    out.println("<head>");
	    out.println("<link type=\"text/css\" rel=\"stylesheet\" href=\"resources/css/styles.css\" />");
	    out.println("<script type=\"text/javascript\">");
	    out.println("function init() {parent.document.getElementById('baseForm:refrimg').click();}");
	    out.println("window.onload=init;");
	    out.println("</script>");
	    out.println("</head>");

	    out.println("<body>");
	    FileUpload fu = new FileUpload();
		if (fu.processFileUpload(request, uploadImagePath, maxImageFileSize)) {
			FileUtil.deleteImageFilesForItem(uploadImagePath, fu.itemId, fu.uploadedFileName);
			out.println("<img src=\"img/ok.jpeg\"/>");
		} else {
	    	out.println("<img src=\"img/error.jpg\"/>");
	    	out.println("<span class=\"errorText\">");
	    	out.println(fu.getErrText());
	    	out.println("</span>");
		}
	    out.println("</body>");
	}

//--------------------------------------------------------------------------------------------	
	private class FileUpload {
		private String errText = "";
		private String uploadedFileName = "";
		private String itemId = "";
		
		//@SuppressWarnings("unchecked")
		public Boolean processFileUpload(HttpServletRequest request, String uploadImagePath, Integer maxImageFileSize) {
			errText = "";
	        //check for parameter 'itemId'
	        itemId = request.getParameter("itemId");
	        if (itemId == null) {
	        	errText = "Missing 'itemId' parameter.";
	        } else {
	        	Integer iId = -1;
	        	try {
	        		iId = Integer.parseInt(itemId);
	        	} catch (Exception e) {
	        		iId = -1;
	        	}
	        	if (iId <= 0) {
	        		errText = "Invalid item id value.";
	        	}
	        }
	        if (this.errText != "") {
	        	return false;
	        }
	        // If there are no errors, proceed with writing file.
	    	// Create a factory for disk-based file items
	    	DiskFileItemFactory factory = new DiskFileItemFactory();
	    	factory.setSizeThreshold(150000);

	    	// Create a new file upload handler
	    	ServletFileUpload upload = new ServletFileUpload(factory);
	    	
	    	// Parse the request
	    	List<?> items = null;
	    	try {
	    		items = upload.parseRequest(request);
	    		
	    	} catch (Exception e) {
	    		errText = e.getMessage();
	    	}
	        if (this.errText != "") {
	        	return false;
	        }
			//process the uploaded items
			Iterator<?> iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					if (item.getName().equals("")) {
						item.delete();
						errText = "Select file to upload.";
						return false;
					}
					if (item.getSize() > maxImageFileSize) {
						item.delete();
						errText = "File maximum size is ".concat(maxImageFileSize.toString());
						return false;
					}
					String contentType = item.getContentType();
					if (!contentType.startsWith("image/")) {
						item.delete();
						errText = "Selected file not an image.";
						return false;
					}
					String origFileName = item.getName();
					//trim the file path
					origFileName = origFileName.substring(origFileName.lastIndexOf("/") + 1).substring(origFileName.lastIndexOf("\\") + 1);
					String origFileExt = origFileName.substring(origFileName.lastIndexOf(".") + 1);
					String discrText = "_" + new java.util.Date( ).getTime( ) / 1000;
					String newFileName = itemId.concat(discrText).concat(".").concat(origFileExt);
					File uploadedFile = new File(uploadImagePath,newFileName);
					try {
						item.write(uploadedFile);
						uploadedFileName = newFileName;
					} catch (Exception e) {
						item.delete();
						errText = e.getMessage();
					}
				}
			}
	        if (errText != "") {
	        	return false;
	        }
			return true;
		}
		public String getErrText() {
			return errText;
		}
		//public String getUploadedFileName() {
		//	return uploadedFileName;
		//}
		//public String getItemId() {
		//	return itemId;
		//}
	}
}
