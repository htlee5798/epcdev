<%
String namoFileKind = request.getParameter("namofilekind");

//filelink
String namoFilePhysicalPath = "C:\\dev_web\\images\\namo\\namofile";
String namoFileUPath = "/ce3/namofile";

//movie
String namoFlashPhysicalPath = "C:\\dev_web\\images\\namo\\namomovie";
String namoFlashUPath = "/ce3/namomovie";

//image
String namoImagePhysicalPath = "";
String namoImageUPath 		 = "";		

String imageUType = request.getParameter("imageUPath");

org.springframework.web.context.WebApplicationContext wac = org.springframework.web.context.support.WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
lcn.module.framework.property.ConfigurationService configProperties = (lcn.module.framework.property.ConfigurationService)wac.getBean("configurationService");
if ( "board".equalsIgnoreCase(imageUType) ) {
	namoImagePhysicalPath = configProperties.getString("board.image.mime.save.path");
	namoImageUPath = configProperties.getString("board.image.mime.save.url");
} else if ( "edi".equalsIgnoreCase(imageUType) ) {
	namoImagePhysicalPath = configProperties.getString("edi.namoeditor.file.path");
	namoImageUPath = configProperties.getString("edi.namoeditor.file.url");
} else if ( "productdetail".equalsIgnoreCase(imageUType) ) {
	namoImagePhysicalPath = configProperties.getString("online.product.detail.image.path");
	namoImageUPath = configProperties.getString("online.product.detail.image.url");
} else {
	namoImagePhysicalPath = configProperties.getString("namo.default.image.path");
	namoImageUPath = configProperties.getString("namo.default.image.url");
}

if (namoFileKind != null && "file".equals(namoFileKind)) {
	imagePhysicalPath = namoFilePhysicalPath;
	imageUPath = namoFileUPath;
} else if (namoFileKind != null && "flash".equals(namoFileKind)) {
	imagePhysicalPath = namoFlashPhysicalPath;
	imageUPath = namoFlashUPath;
} else {
	imagePhysicalPath = namoImagePhysicalPath;
	imageUPath = namoImageUPath;
}
%>