function load(handler) {

	My.addListener(window, "load", handler);
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

var My = function() {
};

My.ajax = function(method, url, async, data, callback) {

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

My.ajaxPost = function(url, data, callback) {
	My.ajax("post", url, true, data, callback);
};

My.ajaxGet = function(url, callback) {
	My.ajax("get", url, true, "", callback);
};

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
My.gets = function(str, parent) {

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
};
/**
 * 
 * @param {String}
 *            id
 * @returns {Element}
 */
My.get = function(id) {
	return document.getElementById(id);
};

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
My.getLocation = function(e) {

	var x = 0, y = 0;
	while (e != null) {
		x += e.offsetLeft;
		y += e.offsetTop;
		e = e.offsetParent;
	}
	return new Point(x, y);
};

/**
 * 
 * @param previous
 *            {Element}
 * @param next
 *            {Element}
 * @returns {Point}
 */
My.getRelativeLocation = function(previous, next) {

	if (previous == null || next == null)
		return new Point(0, 0);
	else {
		var p1 = getLocation(previous);
		var p2 = getLocation(next);
		return new Point(p2.x - p1.x, p2.y - p1.y);
	}
};

/**
 * 
 * @param e
 *            {Element}
 * @returns null or Element
 */
My.getPreElement = function(e) {

	return getNearElement(e, true);
};
/**
 * 
 * @param e
 *            {Element}
 * @returns null or Element
 */
My.getNextElement = function(e) {

	return getNearElement(e, false);
};

/**
 * 
 * @param e
 *            {Element}
 * @param previous
 *            {Boolean}
 * @returns null or Element
 */
My.getNearElement = function(e, previous) {

	var nearNode = previous ? e.previousSibling : e.nextSibling;
	if (nearNode != null)
		if (nearNode.nodeType == 1)
			return nearNode;
		else
			return getNearElement(nearNode, previous);
	else
		return null;
};

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
My.addListener = function(element, eventType, handler) {

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
};

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
My.removeListener = function(element, eventType, handler) {

	element.removeEventListener ? element.removeEventListener(eventType,
			handler, false) : element.detachEvent(eventType, "on" + handler);
};

/**
 * 
 * @param nodeName
 *            {String}
 * @param value
 *            {String}
 * @returns {Boolean}
 */
My.supportAttr = function(nodeName, value) {
	return (value in document.createElement(nodeName));
};

/**
 * Makes the <code>element</code> move from (margin_top = <code>fromTop</code>,
 * margin_left = <code>fromTop</code>) to (margin_top = <code>toTop</code>,
 * margin_left = <code>toLeft</code>) with the <code>time</code>.
 * 
 * @param {Element}
 *            element
 * @param {Number}
 *            time
 * @param {Number}
 *            fromTop
 * @param {Number}
 *            fromLeft
 * @param {Number}
 *            toTop
 * @param {Number}
 *            toLeft
 * 
 * @author fancige
 */

My.changePosition = function(element, time, fromTop, fromLeft, toTop, toLeft) {

	var sTop = toTop - fromTop;
	var sLeft = toLeft - fromLeft;
	var delay = 10;

	var times = time / delay;
	var count = 1;
	var offsetLeft = sLeft / times;
	var offsetTop = sTop / times;

	var marginTop = fromTop;
	var marginLeft = fromLeft;

	var id = "counter";
	id = setInterval(function() {

		element.style.marginTop = marginTop + "px";
		element.style.marginLeft = marginLeft + "px";

		if (count >= times) {

			clearInterval(id);

		} else {

			marginTop += offsetTop;
			marginLeft += offsetLeft;
			count++;
		}

	}, delay);
};
/**
 * <p>
 * Changes the opacity of the <code>element</code> from the value
 * <code>from</code> to the value <code>to</code> with the <code>time</code>
 * given.
 * </p>
 * <p>
 * Note that the value of both <code>from</code> and <code>to</code> must be
 * between 0-1;
 * </p>
 * 
 * @param {Element}element
 * @param {Number}time
 * @param {Number}from
 * @param {Number}to
 * 
 * @author fancige
 */
My.changeOpacity = function(element, time, from, to) {

	var delay = 10;
	var times = time / delay;
	var offset = (to - from) / times;

	var count = 1;
	var current = from;

	var id = "counter";

	id = setInterval(function() {

		element.style.opacity = current;
		element.style.filter = "alpha(opacity = " + current * 100 + ")";
		if (count >= times) {

			clearInterval(id);

		} else {

			count++;
			current += offset;
		}
	}, delay);
};

/**
 * @param {Element}e
 * @returns {CSS2Properties}
 */
My.getCss = function(e) {

	return window.getComputedStyle ? window.getComputedStyle(e, null)
			: e.currentStyle;
};