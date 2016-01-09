<?php
require_once("config.php");
require_once("filter.php");
require_once("database.php");
require_once("UserManager.php");

$output = array("result" => false, "msg" => "服务器错误");
$key = _post("key");
$pw = _post("password");
$userid = "";
$appid = "";
$errmsg = "";

if (loginByPassword($key, $pw, $userid, $appid, $errmsg)) {
	$output["result"] = true;
	$output["msg"] = "";
	$output["appid"] = $appid;
	$output["userid"] = $userid;
} else {
	if ($errmsg == "active") {
		$output["msg"] = "该账号未激活，请及时到注册邮箱激活，超时后账号将被收回";
	} else if ($errmsg != "error") {
		$output["msg"] = "用户名或密码错误";
	} 
} 
header("content-type:text/json;charset=utf-8");
echo json_encode($output, JSON_UNESCAPED_UNICODE);
?>