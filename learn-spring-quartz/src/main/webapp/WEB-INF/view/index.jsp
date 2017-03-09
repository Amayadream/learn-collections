<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
    <title>任务列表</title>
    <style>
        .success {
            color: green;
        }
        .error {
            color: red;
        }
        .table table {
            border: 1px solid;
        }
    </style>
</head>
<body>
    <div class="alert">
        <p class="success">${message}</p>
        <p class="error">${error}</p>
    </div>
    <button type="button" onclick="window.location = '${ctx}/quartz/add'">添加任务</button>
    <div class="table">
        <table>
            <tr>
                <th>任务ID</th>
                <th>任务名称</th>
                <th>任务分组</th>
                <th>任务运行时间</th>
                <th>任务描述</th>
                <th>任务状态</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${jobs}" var="item">
                <tr>
                    <td>${item.jobId}</td>
                    <td>${item.jobName}</td>
                    <td>${item.jobGroup}</td>
                    <td>${item.cronExpression}</td>
                    <td>${item.desc}</td>
                    <td>
                        <c:if test="${item.jobStatus == '1'}">
                            <p class="success">正常</p>
                        </c:if>
                        <c:if test="${item.jobStatus == '0'}">
                            <p class="error">已暂停</p>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${item.jobStatus == '1'}">
                            <button type="button" onclick="window.location = '${ctx}/quartz/pause?jobId=${item.jobId}'">暂停任务</button>
                        </c:if>
                        <c:if test="${item.jobStatus == '0'}">
                            <button type="button" onclick="window.location = '${ctx}/quartz/resume?jobId=${item.jobId}'">启用任务</button>
                        </c:if>
                        <button type="button" onclick="window.location = '${ctx}/quartz/update?jobId=${item.jobId}'">编辑任务</button>
                        <button type="button" onclick="window.location = '${ctx}/quartz/remove?jobId=${item.jobId}'">删除任务</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

</body>
</html>
