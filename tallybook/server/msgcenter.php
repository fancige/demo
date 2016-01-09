<?php
require_once("config.php");
require_once("filter.php");
$type = _post("type");
if($type == "tallybookUpdate"){
	header("content-type:text/json;charset=utf-8");
	echo '{"version":1.2,"content":"服务器地址发生变更，更新后才能使用同步功能"}';
}
?>