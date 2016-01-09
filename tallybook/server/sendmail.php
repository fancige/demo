<?php
require_once("config.php");
require_once("filter.php");
require_once("database.php");
require_once("smtp.php");
require_once("validator.php");
//return;
$user = _get("user");
if (isX($user, XUSERNAME)) {
	$mysqli = getMysqli();
	$q = "SELECT * FROM nonactivatedUser WHERE username = '$user'";
	if ($rs = $mysqli -> query($q)) {
		if ($row = $rs -> fetch_assoc()) {
			$to = $row["email"];
			$from = "service@fancige.com";
			$hash = $row["hash"];
			$subject = "请激活你的账号";
			$content = "
				尊敬的 $user:
				请点击下面链接激活你的账号，或者复制到浏览器打开(注意，链接24小时内有效，超时后注册的用户名将被收回，请及时激活):

				http://fancige.com/accountActivation.php?user=$user&hash=$hash

				如果你不知道为什么会收到这封邮件，请直接忽略或删除";
			$smtp = new smtp("smtp.ym.163.com", 25, true, "service@fancige.com", "");
			if (!$smtp -> sendmail($to, $from, $subject, $content, "繁辞阁")) {
				trigger_error("Failed to send email to $user");
			} 
		} else {
			trigger_error("Failed to send email, nu such user: $user");
		} 
	} else {
		trigger_error("Failed to $q caused by : \r\n\t" . $mysqli -> error);
	} 
} 

?>