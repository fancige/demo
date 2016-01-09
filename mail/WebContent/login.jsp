<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="locale" uri="/WEB-INF/locale.tld"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<locale:set page="mail.login" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<link type="text/css" rel="stylesheet" href="css/common.css">
<link type="text/css" rel="stylesheet" href="css/header0.css">
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/header0.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<style type="text/css">
#body {
	margin-top: 70px;
	padding: 0px 40px;
}

#imgCode {
	position: absolute;
	margin-left: 10px;
}
</style>
<title>${lc_1}</title>
</head>
<body>
  <tag:header0 />
  <div id="body">
    <form method="post" action="login">
      <p>
        <input type="text" name="username" placeholder="${lc_2}">@ <select name="host" id="host"><option>outlook.com</option>
          <option>gmail.com</option></select>
      </p>
      <p>
        <input type="password" name="password" placeholder="${lc_3}">
      </p>
      <p>
        <input type="text" name="verificode" id="inputCode" placeholder="${lc_4}"><img src="verificode" id="imgCode">
      </p>
      <p>
        <button type="submit">${lc_5 }</button>
        <b style="margin: 0px 5px; color: red">${requestScope.prompt}</b>
      </p>
    </form>
  </div>
</body>
</html>