window.onload = function() {

	if (!window.localStorage) {
		alert("该浏览器貌似不支持本地存储，无法保存注册信息，换成其它浏览器试试，比如firefox或者chrome。");
		return;
	}

	var pageID = "welcome";
	switch (location.hash) {
	case "#login":
		pageID = "login";
		break;
	case "#register":
		pageID = "register";
		break;
	}
	var page = document.getElementById(pageID);
	page.style.display = "block";

	if (pageID != "welcome") {
		var inputs = document.querySelectorAll("#" + pageID + " input");
		inputs[inputs.length - 1].onclick = function() {

			var username = inputs[0].value;
			var psw = inputs[1].value;

			if (pageID == "register") {
				var p = /^\w{5,10}$/;
				var repsw = inputs[2].value;

				if (p.test(username) && p.test(psw)) {
					var store = getStore();
					if (!store[username]) {
						if (psw == repsw) {
							saveStore(username, psw);
							alert("注册成功");
						} else {
							alert("两次密码输入不相同");
						}
					} else {
						alert("用户名已存在");
					}
				} else {
					alert("用户名和密码只能包含字母或数字，长度5-10个");
				}
			} else {
				var store = getStore();
				if (store[username] && store[username] == psw) {
					alert("登录成功");
				} else {
					alert("用户名或密码错误");
				}
			}
		};
	}

	var storeName = "regInfo";
	function saveStore(username, psw) {
		var regInfo = window.localStorage[storeName];
		var store;
		if (regInfo) {
			store = JSON.parse(regInfo);
			store[username] = psw;
		} else {
			store = {
				username : psw
			};
		}
		window.localStorage[storeName] = JSON.stringify(store);
	}

	function getStore() {
		var store = window.localStorage[storeName];
		if (store) {
			return JSON.parse(store);
		} else {
			return {};
		}
	}
};