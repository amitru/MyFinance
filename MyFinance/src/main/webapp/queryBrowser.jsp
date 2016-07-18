<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="com.amitru.mystockalert.dao.StockAlertDAO"%>
<%@page import="com.amitru.mystockalert.main.StockAlertContextProvider"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.sql.Connection"%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSetMetaData"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body align="center">

<%

	String queryText = request.getParameter("qryTxt");
	String qryType = request.getParameter("qryType");

	if(queryText==null) queryText="select * from mystockalert_wishlist";
	if(qryType==null) qryType="SELECT";
	

%>

<center>
<h1>Query Browser</h1>
<form name="frm" method="post">

<table>
<tr>
<td>
Type of Query
<select name ="qryType">
		<option>SELECT</option>  
		<option>UPDATE</option>    
		</select>
</td>
</tr>


<tr>
<td>
	<textarea rows="20" cols="50" name="qryTxt" title="aa">
		<%=queryText%>
	</textarea> 
</td>



<td>
<input type="submit" value="Execute Query" name="sub">
</td>
</tr>




	<%
	Connection connection  = null;
	ResultSet rs =null;
	Statement stmt =null;
	int updateCount=-1;
	try
	{
		if(queryText!=null || queryText!="") {
			
			//StockAlertContextProvider appContext = new StockAlertContextProvider();
			//StockAlertDAO stockAlertDAO = (StockAlertDAO)appContext.getApplicationContext().getBean("StockAlertDAO", StockAlertDAO.class);
			
			WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servlet.getServletContext());
			StockAlertDAO stockAlertDAO = ctx.getBean("StockAlertDAO", StockAlertDAO.class);
			
			connection = stockAlertDAO.getDataSource().getConnection();
			
			connection.setAutoCommit(false);
			stmt = connection.createStatement();
			
			if("UPDATE".equalsIgnoreCase(qryType)) {
				updateCount = stmt.executeUpdate(queryText);
				out.print("Rows Updated=>" + updateCount);
				if(updateCount>0)
					connection.commit();
				else
					connection.rollback();
			}
			else {
			rs= stmt.executeQuery(queryText);
			ResultSetMetaData md = rs.getMetaData();
			%>
			<table border="1"><tr>
			<%
			if(md.getColumnCount()>0) {
				//out.println(md.getColumnCount());
				for(int i=0;i<md.getColumnCount();i++) {
					%>
					<td> <%=md.getColumnName(i+1)%> </td>
					<%
				}
			}
			%>	
			</tr><tr>
			<%
				while(rs.next()) {
					if(md.getColumnCount()>0) {
						for(int k=0;k<md.getColumnCount();k++) {
							%>
							<td>
								<%=rs.getString(k+1)%>
							</td>
							<%
						}%> </tr> <%
					}
				}
		} // end else
	  }
	}
	catch(Exception ex) {
		ex.printStackTrace();
		out.println("Unable to retrive data.");
	}
	finally {
		try{if(rs!=null) rs.close();rs=null;} catch(Exception ex1) {}
		try{if(stmt!=null) stmt.close();stmt=null;} catch(Exception ex2) {}
		try{if(connection!=null) connection.close();connection=null;} catch(Exception ex3) {}
	}
	%>	
</table>
</form>

</body>
</html>