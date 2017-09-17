<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Insert title here</title>
</head>
<body>
<%--
<form name="serForm" action="/fileUpload" method="post"  enctype="multipart/form-data">
    <h1>采用流的方式上传文件</h1>
    <input type="file" name="file">
    <input type="submit" value="upload"/>
</form>

<form name="Form2" action="/fileUpload2" method="post"  enctype="multipart/form-data">
    <h1>采用multipart提供的file.transfer方法上传文件</h1>
    <input type="file" name="file">
    <input type="submit" value="upload"/>
</form>
--%>

<form name="Form2" action="/aidongxiang/UploadAudio" method="post" enctype="multipart/form-data" >
    <h1>使用spring mvc提供的类的方法上传文件</h1>
    <input type="file" name="file[]" >

    <input type="submit" value="upload"/>
    <div class="form-group">
        <label for="description">音频描述:</label>
        <input type="text" class="form-control" id="description" name="description" placeholder="请输入音频描述"/>
    </div>
    <div class="form-group">
        <label for="isCharge">是否收费:</label>
        <select class="form-control" id="isCharge" name="isCharge">
            <option value="1">收费</option>
            <option value="2">不收费</option>
        </select>
    </div>
    <div class="form-group">
        <label for="price">价格:</label>
        <input type="text" class="form-control" id="price" name="price" placeholder="请输入音频价格"/>
    </div>
    <%--<input type="text" class="form-control" id="title" name="json" placeholder="json"/>--%>
</form>
</body>
</html>