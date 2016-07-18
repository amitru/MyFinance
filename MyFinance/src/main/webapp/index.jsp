<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<html>
<body>
<h2>Hello World!</h2>

<%

ApplicationContext myAppContext = new ClassPathXmlApplicationContext("Spring-Module.xml");

out.println("Loaded..");
out.println(myAppContext);

%>

</body>
</html>
