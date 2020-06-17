<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>自定义MVC开发框架</title>
</head>
<body>
<h1>部门增加表单</h1>
<form action="dept_add.action" method="post" enctype="multipart/form-data">
    部门编号：<input type="text" name="deptno" value="10">${errors.deptno}<br>
    部门名称：<input type="text" name="dname" value="沐言科技">${errors.dname}<br>
    部门位置：<input type="text" name="loc" value="北京">${errors['loc']}<br>
    部门LOGO：<input type="file" name="photo"><br>
    <button type="submit">提交</button>
    <button type="reset">重置</button>
</form>
</body>
</html>
