<?php
require_once("config.php");
require_once("filter.php");
require_once("database.php");
require_once("validator.php");
require_once("UserManager.php");

function __sendmail($user) {
	$fp = fsockopen("fancige.com", 80, $errno, $errstr, 5);
	fwrite($fp, "GET /sendmail.php?user=$user HTTP/1.1\r\nHost: fancige.com\r\n\r\n");
	fclose($fp);
} 

function __log($user) {
	$path = getRoot() . "/myfolder/log/register_" . date("Ymd") . ".log";
	$handle = fopen($path, "a");
	$msg = date("H:i:s") . " " . _server("REMOTE_ADDR") . " $user\r\n";
	fwrite($handle, $msg);
	fclose($handle);
} 

$email = _post("email");
$user = _post("username");
$password = _post("password");
$output = array("msg" => "服务器错误", "result" => false);
if (!isX($user, XUSERNAME)) {
	$output["msg"] = "用户名不合规范";
} else if (!isX($password, XPASSWORD)) {
	$output["msg"] = "密码不合规范";
} else if (!isX($email, XEMAIL)) {
	$output["msg"] = "邮箱地址不合规范";
} else if (!checkdnsrr(preg_replace("/^.+\@([^\@]+)$/", '\1', $email))) {
	$output["msg"] = "邮箱地址无效";
} else {
	$errmsg = "";
	$e0 = isExist("email", $email, $errmsg);
	$e1 = isExist("username", $user, $errmsg);
	if (!$errmsg) {
		if ($e0) {
			$output["msg"] = "邮箱已被使用";
		} else if ($e1) {
			$output["msg"] = "用户名已被注册";
		} else {
			$mysqli = getMysqli();
			$hash = sha1($user . time() . $email . rand(10, 30) + "Ajbo2./OJI");
			$expire = "NOW() + INTERVAL 1 DAY";
			$p = $password;
			$password = password_hash($password, PASSWORD_DEFAULT);
			trigger_error(password_verify($p, $password)?"true":"false");
			$q = "INSERT INTO nonactivatedUser VALUES ('$user', '$email', '$password', '$hash', $expire, 'false')";
			if ($mysqli -> query($q)) {
				__sendmail($user);
				__log($user);
				$output["msg"] = "已提交注册信息！激活邮件将发送到所填邮箱，请24小时内进入邮箱激活，否则注册无效";
				$output["result"] = true;
			} else {
				trigger_error("Failed to $q caused by : \r\n\t" . $mysqli -> error);
			} 
		} 
	} 
} 
header("content-type:text/json;charset=utf-8");
echo json_encode($output, JSON_UNESCAPED_UNICODE);

?>