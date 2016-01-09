function getIEVersion() {

	var ua = navigator.userAgent.match(/MSIE (\d).0/);
	return ua ? ua[1] : 0;
}
/**
 * 
 * @param input
 *            {Element}
 * @param beforeInput
 *            {Boolean}
 * @param text
 *            {String}
 */
function placeholder(input, asBodyChild, text) {
	function change() {

		label.innerHTML = input.value ? "" : text;
	}

	var label = document.createElement("label");
	var style = getStyle(input);
	text = text || input.getAttribute("placeholder");

	input.removeAttribute("placeholder");
	label.innerHTML = text;
	label.style.position = "absolute";
	label.style.color = "gray";
	label.style.cursor = "text";
	label.style.fontSize = style["fontSize"];
	label.style.fontFamily = style["fontFamily"];
	label.style.fontWeight = style["fontWeight"];
	addListener(label, "click", function() {

		input.focus();
		return false;
	});
	change();

	addListener(input, "input", change);

	if (asBodyChild) {

		document.body.appendChild(label);
		function resize() {

			var l = getLocation(input);
			label.style.left = l.x + parseInt(style["borderLeftWidth"])
					+ parseInt(style["paddingLeft"]) + "px";
			label.style.top = l.y + parseInt(style["borderTopWidth"])
					+ parseInt(style["paddingTop"]) + "px";
		}

		resize();

		addListener(window, "resize", function() {
			resize();
		});

	} else {

		input.parentNode.insertBefore(label, input);
		label.style.marginLeft = parseInt(style["borderLeftWidth"])
				+ parseInt(style["paddingLeft"])
				+ (parseInt(style["marginLeft"]) || 0) + "px";
		label.style.marginTop = parseInt(style["borderTopWidth"])
				+ parseInt(style["paddingTop"])
				+ (parseInt(style["marginTop"]) || 0) + "px";
	}
}

function pseudo(e) {
	if ((e.nodeName == "INPUT" & (e.type == "text" || e.type == "password"))
			|| e.nodeName == "TEXTAREA") {

		addListener(e, "focus", function() {
			e.style.borderColor = "green";
		});

		addListener(e, "blur", function() {
			e.style.borderColor = "#DDDDDD";
		});

	} else if (e.nodeName == "BUTTON") {

		addListener(e, "mouseover", function() {
			e.style.filter = "alpha(opacity=80)";
		});
		addListener(e, "mouseout", function() {
			e.style.filter = "alpha(opacity=100)";
		});
	}
}

// ie
if (getIEVersion() && getIEVersion() < 9) {
	addListener(window, "load", function() {

		var nodes = document.getElementsByTagName("*");
		for (var i = 0; i < nodes.length; i++) {
			pseudo(nodes.item(i));
		}
	});
}

function fixed(bg) {

	var timeout = true;
	function resize() {

		bg.style.width = "100%";
		bg.style.height = "100%";
		if (timeout) {

			timeout = false;
			setTimeout(function() {

				bg.style.width = document.documentElement.scrollWidth;
				bg.style.height = document.documentElement.scrollHeight;
				timeout = true;
			}, 10);
		}
	}

	bg.style.position = "absolute";
	resize();
	addListener(window, "resize", function() {
		resize();
	});
}
