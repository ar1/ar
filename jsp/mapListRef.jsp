<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.*"%>  
 <%@ page import="com.apt.Property"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table>
<tr>
<td>Property Name</td>
<td>Long</td>
<td>Lat</td>
<td>Ref Number</td>
</tr>

<%
ArrayList propertyResultArray = (ArrayList)request.getAttribute("page_content");
for(int i=0; i<propertyResultArray.size(); i++) {
    Property prop = (Property)propertyResultArray.get(i);
    String propName=prop.getPROP_NAME();
    String propLong=prop.getLONG_CENTROID();
    String propLat=prop.getLAT_CENTROID();
    String propRefNum=prop.getRef_Nbr();
    
%>
<tr>
<td><%=propName%></td>
<td><%=propLong%></td>
<td><%=propLat%></td>
<td><%=propRefNum%></td>
</tr>
<%
}
%>
</table>
</body>
</html>