<?php
require_once("config.php");
require_once("filter.php");
require_once("database.php");
require_once("validator.php");

$msg = "激活链接无效";
$user = _get("user");
$hash = _get("hash");
if (preg_match("/^[a-z0-9]{1,256}$/i", $hash) && isX($user, XUSERNAME)) {
	$mysqli = getMysqli();
	$q = "UPDATE nonactivatedUser SET confirm = 'true' WHERE confirm='false' AND username='$user' AND hash='$hash'";
	if ($mysqli -> query($q) && $mysqli -> affected_rows) {
		$msg = "激活成功";
	}
}
header("content-type:text/plain;charset=utf-8");
echo $msg;

?>