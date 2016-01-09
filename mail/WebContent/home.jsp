<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="locale" uri="/WEB-INF/locale.tld"%>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags"%>
<locale:set page="mail.home" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<link type="text/css" rel="stylesheet" href="css/common.css">
<link type="text/css" rel="stylesheet" href="css/header0.css">
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/header0.js"></script>
<script type="text/javascript" src="js/home.js"></script>
<style type="text/css">
#sidebar {
	position: fixed;
	top: 40px;
	bottom: 0px;
	overflow: auto;
	background: gray;
	color: white;
	width: 150px;
	min-width: 100px;
}

div.menu {
	padding: 5px 0px 5px 25px;
	cursor: pointer;
	font-size: 14px;
	min-width: 100px;
}

div.menu:HOVER {
	background: #222222;
}

div.focus {
	background: #222222;
}

#splitbar {
	top: 40px;
	left: 150px;
	width: 5px;
	height: 100%;
	position: fixed;
	cursor: col-resize;
	z-index: 3;
}

#contentPane {
	position: fixed;
	left: 155px;
	top: 40px;
	right: 0px;
	bottom: 0px;
	min-width: 300px;
	overflow: auto;
}

div.msgItem {
	cursor: pointer;
	border-bottom: 1px dotted black;
	height: 60px;
}

span.msgCnt {
	text-overflow: ellipsis;
	overflow: hidden;
	white-space: nowrap;
	line-height: 30px;
	height: 30px;
	position: absolute;
}

span.from {
	left: 5%;
	top: 30px;
	width: 50%;
}

span.subject {
	width: 90%;
	left: 1%;
}

span.date {
	top: 30px;
	left: 60%;
	width: 20%;
}

#sendPane {
	width: 100%;
	height: 90%;
}

#sendPane .receiver, #sendPane .subject {
	width: 40%;
	border: none;
	border-bottom: solid 1px gray;
	position: absolute;
}

#sendPane .receive {
	top: 30px;
}

#sendPane .subject {
	top: 60px;
}

#sendPane .content {
	top: 100px;
	width: 70%;
	height: 60%;
	overflow: auto;
	border: none;
	border-bottom: solid 1px gray;
	position: absolute;
	overflow: auto;
	z-index: 5;
}

#sendPane .btnPane {
	position: absolute;
	top: 60%;
	padding-top: 130px;
	padding-bottom: 20px;
	z-index: 0;
}

#sendPane .btnPane button {
	margin-right: 10px;
	margin-bottom: 8px;
}
</style>
<title>${lc_1}</title>
</head>
<body>
  <tag:header0 />
  <div id="sidebar">
    <div id="send" class="menu">${lc_2}</div>
    <div id="folder"></div>
  </div>
  <div id="splitbar"></div>
  <div id="contentPane">
    <div id="sendPane">
      <p>
        <input type="text" class="receiver" placeholder="${lc_3 }">
      </p>
      <p>
        <input type="text" class="subject" placeholder="${lc_4 }">
      </p>
      <p>
        <textarea class="content" placeholder="${lc_5 }"></textarea>
      </p>
      <div class="btnPane">
        <button>${lc_6 }</button>
        <button>${lc_7 }</button>
        <button>${lc_8 }</button>
        <button>${lc_9 }</button>
      </div>
    </div>
  </div>
</body>
</html>