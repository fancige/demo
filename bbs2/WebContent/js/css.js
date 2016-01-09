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

function animate(element, time, fromTop, fromLeft, toTop, toLeft) {

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
}
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
function opacity(element, time, from, to) {

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
}

/**
 * 
 * @param {Element}element
 * @returns {CSS2Properties}
 */
function getStyle(element) {

	return window.getComputedStyle ? window.getComputedStyle(element, null)
			: element.currentStyle;
}