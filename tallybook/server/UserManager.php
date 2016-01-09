<?php
require_once("config.php");
require_once("database.php");
require_once("validator.php");

function loginByAppid($uid, $appid, &$errmsg) {
	if (isX($appid, XAPPID) && isX($uid, XUSERID)) {
		$q = "SELECT userid FROM userlist WHERE userid = $uid AND appid = '$appid'";
		$mysqli = getMysqli();
		if ($rs = $mysqli -> query($q)) {
			if ($rs -> fetch_assoc()) {
				return true;
			} 
		} else {
			$errmsg = "error";
			trigger_error("Failed to $q caused by : \r\n\t" . $mysqli -> error);
		} 
	} 
	return false;
} 

function loginByPassword($key, $password, &$userid, &$appid, &$errmsg) {
	$keyname = "";
	if (isX($key, XUSERNAME)) {
		$keyname = "username";
	} else if (isX($key, XEMAIL)) {
		$keyname = "email";
	} 
	if (isX($password, XPASSWORD) && $keyname) {
		$mysqli = getMysqli();
		$q = "SELECT userid, password, appid FROM userlist WHERE $keyname = '$key'";
		if ($rs = $mysqli -> query($q)) {
			if ($row = $rs -> fetch_assoc()) {
				if (password_verify($password, $row["password"])) {
					$appid = $row["appid"];
					$userid = $row["userid"];
					return true;
				} 
			} else {
				$q = "SELECT password FROM nonactivatedUser WHERE $keyname = '$key'";
				if ($rs = $mysqli -> query($q)) {
					if ($row = $rs -> fetch_assoc()) {
						if (password_verify($password, $row["password"])) {
							$errmsg = "active";
						} 
					} 
				} else {
					trigger_error("Failed to $q caused by : \r\n\t" . $mysqli -> error);
					$errmsg = "error";
				} 
			} 
		} else {
			trigger_error("Failed to $q caused by : \r\n\t" . $mysqli -> error);
			$errmsg = "error";
		} 
	} 
	return false;
} 

function isExist($key, $value, &$errmsg) {
	if ($key == "email" || $key == "username") {
		$q1 = "SELECT $key FROM userlist WHERE $key = '$value'";
		$q2 = "SELECT $key FROM nonactivatedUser WHERE $key = '$value'";
		$q = $q1 . " UNION " . $q2;
		$mysqli = getMysqli();
		if ($rs = $mysqli -> query($q)) {
			if ($rs -> fetch_assoc()) {
				return true;
			} 
		} else {
			$errmsg = "error";
			trigger_error("Failed to $q caused by : \r\n\t" . $mysqli -> error);
		} 
	} 
	return false;
} 

?>