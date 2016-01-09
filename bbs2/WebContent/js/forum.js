window.onload = function() {

	var inputPage = get("inputPage");
	function toPage() {

		var value = inputPage.value;
		if (value <= maxPage && value >= 1)
			location.href = "forum.jsp?page=" + value;
		else
			alert("无该页");
	}

	addListener(get("btnPage"), "click", toPage);

	function kp(event) {

		event = event || window.event;
		if (event.keyCode == 13)
			toPage();
	}
	addListener(inputPage, "keypress", kp);

	/*
	 * compat
	 */

	if (!supportAttr("input", "placeholder")) {

		placeholder(inputPage, true);
	}

	if (getIEVersion() == 9)
		removeListener(inputPage, "keypress", kp);
};
