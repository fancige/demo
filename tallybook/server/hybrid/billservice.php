<?php
require_once("../config.php");
require_once("../filter.php");
require_once("../database.php");
require_once("../UserManager.php");
require_once("BillManager.php");

session_start();
$uid = _post("userid");
$appid = _post("appid");
$errmsg = "";
$output = array("result" => false, "msg" => "服务器错误");
if (loginByAppid($uid, $appid, $errmsg)) {
	$nomsg = "";
	$add = _post("add");
	if ($add) {
		if (!($output["add_result"] = add($uid, $add, $nomsg))) {
			$output["add_msg"] = "服务器添加数据时出错";
		} 
	} 
	$del = _post("delete");
	if ($del) {
		if (!($output["delete_result"] = del($uid, $del, $nomsg))) {
			$output["delete_msg"] = "服务器删除数据时出错";
		} 
	} 
	$data = "[]";
	if ($output["get_result"] = get($uid, $data, $nomsg)) {
		$output["get_data"] = $data;
	} else {
		$output["get_msg"] = "服务器获取数据时出错";
	} 
	$output["result"] = true;
	$output["login_result"] = true;
} else if (!$errmsg) {
	$output["login_result"] = false;
	$output["login_msg"] = "你需要重新登录";
} 
echo json_encode($output, JSON_UNESCAPED_UNICODE);

?>