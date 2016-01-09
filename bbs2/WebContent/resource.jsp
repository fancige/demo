<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<title>资源</title>
<link rel="stylesheet" type="text/css" href="css/global.css">
<link rel="stylesheet" type="text/css" href="css/forumHeader.css">
<link rel="stylesheet" type="text/css" href="css/resource.css">
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/css.js"></script>
<script type="text/javascript" src="js/compat.js"></script>
<script type="text/javascript" src="js/resource.js"></script>
</head>
<body>
  <div id="body">
    <tag:forumHeader />
    <div id="divList"></div>
    <div id="divUpload" style="background-color: white;">
      <form action="upload?type=upload" method="post" enctype="multipart/form-data">
        <button id="btnAdd" type="button">添加文件</button>
        <button id="btnSubmit" type="submit" style="margin-left: 10px;">开始上传</button>
      </form>
    </div>
  </div>
</body>
</html>