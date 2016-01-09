<?php
require_once("../config.php");
require_once("../database.php");
require_once("../validator.php");
function add($uid, $data, &$errmsg) {
	if (!($data = json_decode($data)) || !is_array($data) || !isX($uid, XUSERID)) {
		return false;
	} 
	$q = "INSERT INTO tallybook_bill (userid, money, type, date) VALUES ";
	foreach($data as $key => $val) {
		if (!is_object($val)) {
			return false;
		} 
		$id = $val -> id;
		$money = $val -> money;
		$type = $val -> type;
		if (!isX($id, XBILLMESC) || !isX($money, XBILLMONEY) || !isX($type, XBILLTYPE)) {
			return false;
		} 
		$date = date("Y-m-d H:i:s", $id / 1000);
		$q .= "($uid, $money, '$type', '$date'),";
	} 
	$q = substr($q, 0, strlen($q) - 1);
	if ($mysqli = getMysqli()) {
		if ($mysqli -> query($q)) {
			return true;
		} else {
			trigger_error("Failed to $q caused by : \r\n\t" . $mysqli -> error);
			$errmsg = "error";
		} 
	} else {
		trigger_error("Failed to connect to the database");
		$errmsg = "error";
	} 
	return false;
} 

function del($uid, $data, &$errmsg) {
	$data = preg_replace("/\s/", "", $data);
	$data = preg_replace("/\"/", "", $data);
	if (isX($data, XBILLIDS) && isX($uid, XUSERID)) {
		$data = preg_replace("/\[(.*)\]/", '(\1)', $data);
		if ($mysqli = getMysqli()) {
			$q = "DELETE FROM tallybook_bill WHERE userid = $uid AND id IN $data";
			if ($mysqli -> query($q)) {
				return true;
			} else {
				trigger_error("Failed to $q caused by" . $mysqli -> error);
				$errmsg = "error";
			} 
		} else {
			trigger_error("Failed to connect to the database");
			$errmsg = "error";
		} 
	} 
	return false;
} 

function get($uid, &$data, &$errmsg) {
	if (isX($uid, XUSERID)) {
		if ($mysqli = getMysqli()) {
			$q = "SELECT id, money, type, CONVERT(date, DATE) AS date FROM tallybook_bill WHERE userid = $uid";
			if ($rs = $mysqli -> query($q)) {
				$data = json_encode(new RsValue($rs));
				return true;
			} else {
				trigger_error("Failed to $q caused by : \r\n\t" . $mysqli -> error);
				$errmsg = "error";
			} 
		} else {
			trigger_error("Failed to connect to the database");
			$errmsg = "error";
		} 
	} 
	return false;
} 

?>