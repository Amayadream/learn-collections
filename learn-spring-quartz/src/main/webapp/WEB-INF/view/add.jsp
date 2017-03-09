<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
    <title>添加任务</title>
</head>
<body>
    <div>
        <form action="${ctx}/quartz/add" method="POST">
            任务名称: <input type="text" name="jobName"><br>
            任务分组: <input type="text" name="jobGroup"><br>
            任务时间: <input type="text" name="cronExpression"><br>
            任务描述: <input type="text" name="desc"><br>
            <button type="submit">提交</button><br>
            <button type="reset">重置</button>
        </form>
    </div>
</body>
</html>
