window.onload = function() {

	var imgCode = get("imgCode");
	var form = gets("form").item(0);
	imgCode.onclick = getCode;
	form.onsubmit = validate;

	var userkey = get("userkey");
	var password = get("password");
	var verificode = get("verificode");

	function getCode() {

		imgCode.src = "../verificode?random=" + Math.random();
	}

	function validate() {

		var submit = get("submit");
		var tip = get("tip");
		var autoLogin = get("autoLogin");

		if (userkey.value.length == 0)
			tip.innerHTML = "用户ID不能为空！";
		else if (password.value.length == 0)
			tip.innerHTML = "密码不能为空！";
		else {
			submit.innerHTML = "登录中";
			var data = "userkey=" + userkey.value + "&password="
					+ password.value + "&verificode=" + verificode.value
					+ "&autoLogin=" + autoLogin.checked;
			var callback = function(result) {

				if (result == "success") {

					submit.innerHTML = "请稍候";
					location.href = "user/home.jsp";
				} else {

					if (result == "verificode")
						tip.innerHTML = "验证码错误";
					else if (result == "fail")
						tip.innerHTML = "用户名或密码错误";
					else
						tip.innerHTML = "服务器错误";
					submit.innerHTML = "登录";
					getCode();
				}
			};
			ajax("POST", "login", false, data, callback);
		}
		return false;
	}

	/*
	 * compat
	 */
	if (!supportAttr("input", "placeholder")) {
		placeholder(userkey);
		placeholder(password);
		placeholder(verificode);
	}
};