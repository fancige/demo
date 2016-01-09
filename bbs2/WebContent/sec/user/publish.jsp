<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<script type="text/javascript" src="../../js/common.js"></script>
<script type="text/javascript" src="../../js/css.js"></script>
<script type="text/javascript" src="../../js/compat.js"></script>
<link rel="stylesheet" type="text/css" href="../../css/global.css">
<link rel="stylesheet" type="text/css" href="css/header.css">
<title>发表帖子</title>
<style type="text/css">
#forumCatalog #homepage {
	color: white;
	background: green;
}

#panel {
	margin-top: 50px;
	margin-bottom: 50px;
	padding: 10px 30px;
	background-color: white;
	border: green 3px solid;
	margin-bottom: 50px;
}

#title, #content {
	width: 100%;
}

#content {
	height: 300px;
}
</style>

<script type="text/javascript">
	function check() {

		var tip = document.getElementById("tip");
		var title = document.getElementById("title").value;
		var content = document.getElementById("content").value;
		if (title.length == 0)
			tip.innerHTML = "标题不能为空";
		else if (title.length > 50)
			tip.innerHTML = "标题最多50个字符";
		else if (content.length == 0)
			tip.innerHTML = "内容不能为空";
		else if (content.length > 10000)
			tip.innerHTML = "内容最多10000个字符";
		else {
			return true;
		}
		return false;
	};
</script>
</head>
<body>
  <tag:homeHeader />
  <div id="body">
    <div id="panel">
      <p style="color: green; font-size: 25px; font-weight: bold;">发表帖子</p>
      <form action="postService?type=publish" method="POST" onsubmit="return check();">
        <p>标题：(最多50字)</p>
        <p>
          <input type="text" maxlength="50" name="title" id="title">
        </p>
        <p>内容：（最多10000字）</p>
        <p>
          <textarea name="content" id="content"></textarea>
        </p>
        <p>
          <button type="submit" style="width: 100px;">发表</button>
          <b style="color: red;"></b>
        </p>
      </form>
    </div>
  </div>
</body>
</html>