<?php
require_once("config.php");
require_once("database.php");

doFilter();
function doFilter() {
	$file = _server("SCRIPT_NAME");
	$uri = _server("REQUEST_URI");
	$ip = _server("REMOTE_ADDR");
	$method = _server("REQUEST_METHOD");
	$agent = _server("HTTP_USER_AGENT");
	$post = json_encode($_POST);
	$dt = date("H:i:s");
	$msg = "$dt $method $uri $ip";
	if (strlen($post) > 2) {
		$msg .= " $post";
	} 
	$msg .= "\r\n";
	$path = getRoot() . "/myfolder/log/filter_" . date("Ymd") . ".log";
	$handle = fopen($path, "a");
	fwrite($handle, $msg);
	fclose($handle);

	$path = getRoot() . "/myfolder/log/register_" . date("Ymd") . ".log";
	if ($file == "/registerAuth.php" && file_exists($path)) {
		$handle = fopen($path, "r");
		$count = 0;
		while ($line = fgets($handle)) {
			if (strlen($line) > 2)
				$count++;
		} 
		fclose($handle);
		if ($count > 10) {
			header("content-type:text/json;charset=utf-8");
			echo '{"result":false,"msg":"用户注册量异常，注册功能暂不可用"}';
			exit();
		} 
	}
} 

function _get($str) {
	return empty($_GET[$str]) ? null : $_GET[$str];
} 

function _post($str) {
	return empty($_POST[$str]) ? null : $_POST[$str];
} 

function _server($str) {
	return empty($_SERVER[$str]) ? null : $_SERVER[$str];
} 

?>