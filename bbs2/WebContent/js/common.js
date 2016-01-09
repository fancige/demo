function ajax(method, url, async, data, callback) {

	var xhr;
	if (window.XMLHttpRequest) {

		xhr = new XMLHttpRequest();
	} else {

		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xhr.onreadystatechange = function() {

		if (xhr.readyState == 4) {

			callback(xhr.responseText);
		}
	};
	xhr.open(method, url, async);
	if (method.toLowerCase() == "post") {
		xhr.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
	}

	xhr.send(data);
};

function ajaxGet(url, callback, async) {

	ajax("get", url, async, "", callback);
};

function load(handler) {

	addListener(window, "load", handler);
}

/**
 * A collection of nodes, the return value of gets(str, parent).
 */
function Nodes() {

	this.nodes = [];
	this.length = 0;
	this.push = function(node) {
		this.nodes[this.length++] = node;
	};
	/**
	 * @returns {Node}
	 */
	this.item = function(index) {
		return this.nodes[index];
	};
}

/**
 * 
 * Get Elements by str.
 * 
 * @param {String}
 *            str tagName || tagName.className || .className
 * @param {Element}
 *            parent the default is document.
 * @returns {Nodes}
 */
function gets(str, parent) {

	var nodes = new Nodes();
	parent = parent || document;

	if (str.indexOf(".") != -1) {

		var className;
		var tagName;
		var index = str.indexOf(".");
		// only class.
		if (index == 0) {

			className = str.substring(1, str.length);
			tagName = "*";

		} else { // tagName + className

			tagName = str.substring(0, index);
			className = str.substring(index + 1, str.length);
		}

		var tags = parent.getElementsByTagName(tagName);

		for (var i = 0; i < tags.length; i++) {

			if (tags.item(i).className == className) {

				nodes.push(tags.item(i));
			}
		}

	} else { // only tagName
		var tags = parent.getElementsByTagName(str);
		for (var i = 0; i < tags.length; i++) {
			nodes.push(tags.item(i));
		}
	}
	return nodes;
}
/**
 * 
 * @param {String}
 *            id
 * @returns {Element}
 */
function get(id) {
	return document.getElementById(id);
}

function Point(x, y) {
	/**
	 * @returns {Number}
	 */
	this.x = x;
	/**
	 * @returns {Number}
	 */
	this.y = y;
};

/**
 * 
 * @param {Element}e
 * @returns {Point}
 */
function getLocation(e) {

	var x = 0, y = 0;
	while (e != null) {
		x += e.offsetLeft;
		y += e.offsetTop;
		e = e.offsetParent;
	}
	return new Point(x, y);
}

/**
 * 
 * @param previous
 *            {Element}
 * @param next
 *            {Element}
 * @returns {Point}
 */
function getRelativeLocation(previous, next) {

	if (previous == null || next == null)
		return new Point(0, 0);
	else {
		var p1 = getLocation(previous);
		var p2 = getLocation(next);
		return new Point(p2.x - p1.x, p2.y - p1.y);
	}
}

/**
 * 
 * @param e
 *            {Element}
 * @returns null or Element
 */
function getPreElement(e) {

	return getNearElement(e, true);
}
/**
 * 
 * @param e
 *            {Element}
 * @returns null or Element
 */
function getNextElement(e) {

	return getNearElement(e, false);
}

/**
 * 
 * @param e
 *            {Element}
 * @param previous
 *            {Boolean}
 * @returns null or Element
 */
function getNearElement(e, previous) {

	var nearNode = previous ? e.previousSibling : e.nextSibling;
	if (nearNode != null)
		if (nearNode.nodeType == 1)
			return nearNode;
		else
			return getNearElement(nearNode, previous);
	else
		return null;
}

/**
 * 
 * @param element
 *            {Element}
 * 
 * @param eventType
 *            {String}
 * @param handler
 *            {Function}
 */
function addListener(element, eventType, handler) {

	if (eventType == "input") {

		if (getIEVersion() == 9)
			addListener(element, "keyup", handler);

		"onpropertychange" in element ? element.onpropertychange = function(
				event) {

			event = event || window.event;
			if (event.propertyName == "value") {
				handler();
			}
		} : element.addEventListener("input", handler, false);
	} else {

		element.addEventListener ? element.addEventListener(eventType, handler,
				false) : element.attachEvent("on" + eventType, handler);
	}
}

/**
 * 
 * @param element
 *            {Element}
 * 
 * @param eventType
 *            {String}
 * @param handler
 *            {Function}
 */
function removeListener(element, eventType, handler) {

	element.removeEventListener ? element.removeEventListener(eventType,
			handler, false) : element.detachEvent(eventType, "on" + handler);
}

/**
 * 
 * @param nodeName
 *            {String}
 * @param value
 *            {String}
 * @returns {Boolean}
 */
function supportAttr(nodeName, value) {
	return (value in document.createElement(nodeName));
}

var My = function() {
};

My.background = function() {

	var bg = document.createElement("div");

	with (bg.style) {
		width = "100%";
		height = "100%";
		position = "fixed";
		left = 0;
		top = 0;
		zIndex = 100;
		backgroundColor = "black";
		filter = "alpha(opacity:50)";
		opacity = 0.5;
	}
	return bg;
};

My.loading = function(text, callback) {

	var bg = this.background();
	var dg = document.createElement("div");
	with (dg.style) {

		textAlign = "center";
		width = "50%";
		top = "25%";
		left = "25%";
		position = "fixed";
		zIndex = bg.style.zIndex + 1;
		color = "white";
		padding = "10px";
	}

	var img = document.createElement("img");
	img.src = "images/loading.gif";
	img.style.zIndex = bg.style.zIndex = 1;
	dg.appendChild(img);

	var p = document.createElement("p");
	p.innerHTML = text || "";
	dg.appendChild(p);

	var btn = document.createElement("span");
	btn.innerHTML = "取消";
	with (btn.style) {
		padding = "3px 10px";
		border = "1px white solid";
		cursor = "pointer";
	}

	btn.onclick = function() {
		document.body.removeChild(bg);
		document.body.removeChild(dg);
		if (callback)
			callback();
	};

	p = document.createElement("p");
	p.appendChild(btn);
	dg.appendChild(p);

	document.body.appendChild(dg);
	document.body.appendChild(bg);
};

My.alert = function(text, callback) {

	var bg = this.background();

	var dg = document.createElement("div");
	with (dg.style) {
		top = "25%";
		left = "25%";
		zIndex = "101";
		padding = "10px";
		backgroundColor = "white";
		position = "fixed";
		fontSize = "15px";
		lineHeight = "25px";
		width = "50%";
		maxHeight = "50%";
		overflowY = "scroll";
	}

	var btn = document.createElement("span");
	btn.innerHTML = "确定";
	with (btn.style) {
		padding = "3px 10px";
		border = "1px black solid";
		cursor = "pointer";
	}

	btn.onclick = function() {
		document.body.removeChild(bg);
		document.body.removeChild(dg);
		if (callback)
			callback();
	};
	var p = document.createElement("p");
	p.innerHTML = text || "";
	dg.appendChild(p);

	var p = document.createElement("p");
	p.style.textAlign = "center";
	p.appendChild(btn);
	dg.appendChild(p);

	document.body.appendChild(bg);
	document.body.appendChild(dg);
	bg.focus();
};

My.post = function(url, data, callback) {
	ajax("post", url, true, data, callback);
};