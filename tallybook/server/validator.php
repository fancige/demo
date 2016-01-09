<?php
require_once("config.php");
require_once("filter.php");

const XUSERNAME = 0;
const XPASSWORD = 1;
const XEMAIL = 2;
const XAPPID = 3;
const XBILLMESC = 4;
const XBILLMONEY = 5;
const XBILLTYPE = 6;
const XBILLIDS = 7;
const XUSERID = 8;
function isX($content, $type) {
	switch ($type) {
		case XUSERID:
			return preg_match("/^\d{1,9}$/", $content);
		case XUSERNAME:
			return preg_match("/^[a-z][a-z0-9]{5,19}$/i", $content);
		case XPASSWORD:
			return preg_match("/^[a-z0-9]{6,30}$/i", $content);
		case XEMAIL:
			return preg_match("/^\w+@[a-z0-9-\.]+$/i", $content) && strlen($content) < 50;
		case XAPPID:
			return preg_match("/^[\d|a-f]{1,256}$/i", $content);
		case XBILLMESC:
			return preg_match("/^\d{13}$/", $content);
		case XBILLMONEY:
			return preg_match("/^\d{1,10}\.\d{1,2}$/", $content) || preg_match("/^\d{1,10}$/", $content);
		case XBILLTYPE:
			return preg_match("/^(income|outlay)$/", $content);
		case XBILLIDS:
			return preg_match("/^\[\d{1,19}(,\d{1,19})*\]$/", $content);
		default:
			trigger_error("No this type : $type");
			return false;
	} 
} 

?>