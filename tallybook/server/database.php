<?php
require_once("config.php");
class RsValue implements JsonSerializable {
	public $a = array();
	public function __construct(mysqli_result $rs) {
		while ($row = $rs -> fetch_assoc()) {
			$this -> a[] = $row;
		} 
	} 

	public function jsonSerialize() {
		return $this -> a;
	} 
} 

function getMysqli(){
	$host = "127.0.0.1";
	$user = "root";
	$password = "root";
	$dbname = "tallybook";
	$mysqli = new mysqli($host, $user, $password, $dbname);
	if ($mysqli -> connect_errno) {
		trigger_error("Failed to connect to MySQL: " . $mysqli->connect_error);
		header("content-type:text/json;charset=utf-8");
		echo '{"result":false,"msg":"服务器错误"}';
		exit();
	}else{
		return $mysqli;
	}
}
?>