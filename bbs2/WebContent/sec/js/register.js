window.onload = function() {

	// tipPanel
	var tipPanel = get("tipPanel");
	var top = -100;
	var left = -get("form").offsetLeft;
	tipPanel.style.marginTop = top + "px";
	tipPanel.style.marginLeft = left + "px";
	tipPanel.style.display = "block";

	animate(tipPanel, 500, top, left, top, 50);
	opacity(tipPanel, 500, 0, 1);

	// password
	var password = get("password");
	password.onfocus = function() {

		tipPanel.style.display = "block";
		tipPanel.style.paddingTop = "32px";
		tipPanel.style.paddingBottom = "18px";
		tipPanel.innerHTML = "密码长度6-20个字符，可用数字和字母（区分大小写），并且必须同时包含数字和字母。";
		tipPanel.style.color = "black";
		tipPanel.style.marginTop = "-60px";
		tipPanel.style.marginLeft = "250px";
	};

	password.onblur = function() {

		validate("password");
	};

	// repassword
	var repassword = get("repassword");
	repassword.onfocus = function() {

		if (!validate("password"))
			return;

		tipPanel.style.display = "block";
		tipPanel.style.color = "black";
		tipPanel.style.marginTop = "0px";
		tipPanel.style.marginLeft = "250px";
		tipPanel.innerHTML = "再输一次密码.";
	};

	repassword.onblur = function() {

		if (!validate("password"))
			return;

		validate("repassword");
	};

	// vertification code
	var verificode = get("verificode");
	var imgcode = get("imgcode");

	function getCode() {

		imgcode.src = "../verificode?random=" + Math.random();
	}

	imgcode.onclick = getCode;

	verificode.onfocus = function() {

		if (!validate("password") || !validate("repassword"))
			return;
		tipPanel.style.marginTop = "35px";
		tipPanel.style.marginLeft = "200px";
		tipPanel.style.paddingTop = "40px";
		tipPanel.style.paddingBottom = "10px";
		tipPanel.style.color = "black";
		tipPanel.innerHTML = "请输入验证码，字母不区分大小写。如果验证码看不清，点击验证码更换。";
		tipPanel.style.display = "block";
	};

	verificode.onblur = function() {

		if (!validate("password") || !validate("repassword"))
			return;
		tipPanel.style.display = "none";
	};

	// submit
	var form = gets("form").item(0);
	var btnSubmit = get("submit");
	form.onsubmit = function() {

		if (validate("password") && validate("repassword")) {

			btnSubmit.innerHTML = "注册中";

			var data = "password=" + password.value + "&verificode="
					+ verificode.value;
			var callback = function(text) {

				if (/^\d+$/.test(text)) {

					get("userid").innerHTML = text;
					get("background").style.display = "block";
					get("dialog").style.display = "block";

				} else {

					tipPanel.style.marginTop = "35px";
					tipPanel.style.marginLeft = "200px";
					tipPanel.style.display = "block";
					tipPanel.style.color = "red";
					tipPanel.innerHTML = text;
				}
				getCode();
				btnSubmit.innerHTML = "注册";
			};

			ajax("POST", "register", false, data, callback);
		}
		return false;
	};

	function validate(id) {

		tipPanel.style.color = "red";
		tipPanel.style.paddingTop = "50px";
		tipPanel.style.paddingBottom = "0px";

		if ("password" == id) {

			var value = password.value;
			if (0 == value.length)
				tipPanel.innerHTML = "密码不能为空！";
			else if (6 > value.length)
				tipPanel.innerHTML = "密码不能少于6个字符！";
			else if (20 < value.length)
				tipPanel.innerHTML = "密码不能多于20个字符！";
			else {
				var result = value.match(/\d/g);
				var number = result == null ? 0 : result.length;
				result = value.match(/[a-z]/gi);
				var word = result == null ? 0 : result.length;
				if ((number + word) < value.length)
					tipPanel.innerHTML = "密码不能包含数字和字母以外的字符！";
				else if (0 == number)
					tipPanel.innerHTML = "密码不能全为字母！";
				else if (0 == word)
					tipPanel.innerHTML = "密码不能全为数字！";
				else {
					tipPanel.style.display = "none";
					return true;
				}
			}
			return false;

		} else if ("repassword" == id) {

			if (password.value == repassword.value) {

				tipPanel.style.display = "none";
				return true;

			} else {

				tipPanel.style.display = "block";
				tipPanel.innerHTML = "两次密码输入不相同";
				return false;
			}
		}
	}

	/*
	 * compat
	 */
	if (getIEVersion() && getIEVersion() < 7)
		fixed(get("background"));
};