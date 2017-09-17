<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>意见反馈列表</title>

    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">
    <h1>意见反馈列表</h1>
    <hr/>

    <%--<h3>所有博客 <a href="/admin/blogs/add" type="button" class="btn btn-primary btn-sm">添加</a></h3>--%>

    <!-- 如果用户列表为空 -->
    <%--<c:if test="${empty feedbackList}">--%>
        <%--<div class="alert alert-warning" role="alert">--%>
            <%--<span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span>Blog表为空，请<a href="/admin/blogs/add" type="button" class="btn btn-primary btn-sm">添加</a>--%>
        <%--</div>--%>
    <%--</c:if>--%>

    <!-- 如果用户列表非空 -->
    <c:if test="${!empty feedbackList}">
        <table class="table table-bordered table-striped">
            <tr>
                <th>ID</th>
                <th>联系方式</th>
                <th>内容</th>
                <th>图片列表</th>
                <th>用户</th>
                <th>时间</th>
            </tr>

            <c:forEach items="${feedbackList}" var="feedback">
                <tr>
                    <td>${feedback.id}</td>
                    <td>${feedback.contact}</td>
                    <td>${feedback.content}</td>
                    <%--<td>${feedback.userId}</td>--%>
                    <c:if test="${!empty feedback.images}">
                        <c:forEach items="${feedback.images}" var="image">
                            <tr>
                                <td>${image.id} ${image.path}</td>
                            </tr>
                        </c:forEach>
                    </c:if>

                    <td>${feedback.user.nickname}</td>
                    <%--<td>${blog.userByUserId.nickname}, ${blog.userByUserId.firstName} ${blog.userByUserId.lastName}</td>--%>
                    <td><fmt:formatDate value="${feedback.timestamp }" pattern="yyyy-MM-dd"/></td>

                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</body>
</html>