/**
 * A collection of nodes, the return value of gets(str, parent).
 */
function Nodes() {

	this.nodes = [];
	this.length = 0;
	this.push = function(node) {
		this.nodes[this.length++] = node;
	};
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
 * This is equal to document.getElementById(id);
 * 
 * @param {String}
 *            id
 * @returns {Element}
 */
function get(id) {
	return document.getElementById(id);
}

function Point(x, y) {

	this.x = x;
	this.y = y;
}

/**
 * Gets the location relatived to the document.body.
 * 
 * @param e
 * @returns {Point}
 */
function getLocation(e) {

	var left = 0, top = 0;
	while (e != null) {
		left += e.offsetLeft;
		top += e.offsetTop;
		e = e.offsetParent;
	}
	return new Point(left, top);
}

/**
 * Tests whether the point(x,y) is located on the element.
 * 
 * @param {Number}
 *            x
 * @param {Number}
 *            y
 * @param {Element}
 *            e
 * @returns {Boolean}
 */
function isLocationOn(x, y, e) {
	var point = getLocation(e);
	var ex = point.x - window.pageXOffset;
	var ey = point.y - window.pageYOffset;
	if (x < ex || x > (ex + e.offsetWidth) || y < ey
			|| y > (ey + e.offsetHeight))
		return false;
	else
		return true;
}

/**
 * Creates an Array and initializes it with the value.
 * 
 * @param {Number}
 *            length
 * @param {String}
 *            value
 * @returns {Array}
 */
function createArray(length, value) {
	var array = new Array(length);
	for (var i = 0; i < length; i++) {
		array[i] = value || i;
	}
	return array;
}

/**
 * Keeps the precision decimal places.
 */
function round(number, precision) {
	return Math.round(number * Math.pow(10, precision))
			/ Math.pow(10, precision);
}

/**
 * Calculates the sum of these one dimension arrays.
 */
function arraySum(arrays) {

	// Note that this can not be var sumArray = arguments[0], otherwise
	// arguments[0] will be changed.
	var sumArray = new Array();
	for (var i = 0; i < arguments[0].length; i++) {
		sumArray[i] = arguments[0][i];
	}
	for (var i = 1; i < arguments.length; i++) {
		for (var j = 0; j < sumArray.length; j++) {
			sumArray[j] += arguments[i][j];
		}
	}
	return sumArray;
}

/**
 * Calculates the sum of all elements in the array and puts it into the end.
 */
function appendSum(array) {
	var sum = 0;
	for ( var i in array) {
		sum += array[i];
	}
	array[array.length] = sum;
}

/**
 * Tests whether the str is a natural number.
 */
function isNaturalNumber(str) {
	if (str != null) {
		str = new String(str);
		var array = str.match(/\d/g);
		if (array == null || array.length != str.length)
			return false;
		else
			return true;
	} else
		return false;
}

/**
 * A table for enterring data.
 * 
 * @param {Number}
 *            row the number of rows
 * @param {Number}
 *            column the number of columns
 * @returns {Table} the table tag.
 */
function createInputTable(row, column) {

	/**
	 * The tip is a div tag associated with the input tag, click it will cause
	 * the row or column in which the input tag is be removed.
	 */
	function bindDelTip(input, isColumn) {

		var tip = document.createElement("div");
		var width = 60;
		var height = 28;
		input.onmouseover = function() {

			var point = getLocation(input);
			var text = "删除行";
			var left = point.x + width + "px";
			var top = point.y + "px";

			if (isColumn) {
				text = "删除列";
				left = point.x + "px";
				top = point.y - height + "px";
			}

			tip.innerHTML = text;
			tip.style.left = left;
			tip.style.top = top;
			tip.style.position = "absolute";
			tip.style.color = "white";
			tip.style.background = "#888888";
			tip.style.cursor = "pointer";
			tip.style.fontSize = "small";
			tip.style.width = "40px";
			tip.style.height = "14px";
			tip.style.padding = "7px 10px";

			tip.onmouseout = function() {

				document.body.removeChild(this);
			};

			tip.onmouseover = function() {

				this.style.background = "black";
			};

			tip.onclick = function() {
				document.body.removeChild(this);

				// delete column.
				if (isColumn) {
					var table = input.parentNode.parentNode.parentNode;
					var trs = gets("tr", table);
					var tds = gets("td", trs.item(0));
					var index = -1;

					// find the column index.
					for (var j = 0; j < tds.length; j++) {

						if (tds.item(j) == input.parentNode) {
							index = j;
							break;
						}
					}
					// delete all tds with the index.
					for (var i = 0; i < trs.length - 1; i++) {
						var tds = gets("td", trs.item(i));
						tds.item(index).parentNode.removeChild(tds.item(index));
					}
					// delete row.
				} else {
					var tr = input.parentNode.parentNode;
					tr.parentNode.removeChild(tr);
				}
			};
			document.body.appendChild(tip);
		};

		input.onmouseout = function(event) {

			// test whether the mouse is location on tip to decide
			// whether tip should be removed.
			if (!isLocationOn(event.clientX, event.clientY, tip))
				document.body.removeChild(tip);
		};
	}

	/**
	 * create a input tag.
	 */
	function createInput() {

		var input = document.createElement("input");
		input.type = "text";
		input.style.width = "48px";
		input.style.height = "26px";
		input.style.border = "1px solid gray";
		input.style.padding = "0px 5px";
		input.style.outline = "0px";
		return input;
	}

	var table = document.createElement("table");
	table.style.border = "1px black solid";
	for (var i = 0; i < row + 1; i++) {
		var tr = document.createElement("tr");
		for (var j = 0; j < column + 1; j++) {
			var td = document.createElement("td");
			var child;

			// additional row and column for two "add" buttons.
			if (i == row || j == column) {

				child = document.createElement("button");
				child.innerHTML = "+";
				child.style.width = "60px";
				child.style.height = "28px";
				child.style.color = "gray";
				child.style.background = "white";
				child.style.border = "1px gray solid";

				child.onmouseover = function() {
					this.style.color = "black";
					this.style.borderColor = "black";
				};

				child.onmouseout = function() {
					this.style.color = "gray";
					this.style.borderColor = "gray";
				};

				// "add" button for column.
				if (i == 0) {
					child.title = "添加列";

					// add a column.
					child.onclick = function() {
						var trs = table.getElementsByTagName("tr");
						var tds = trs.item(0).getElementsByTagName("td");

						for (var i = 0; i < trs.length - 1; i++) {

							var td = document.createElement("td");
							var input = createInput();
							td.appendChild(input);
							if (i == 0) {
								// insert before the "add" button.
								trs.item(0).insertBefore(td,
										tds.item(tds.length - 1));
								bindDelTip(input, true);
							} else {
								trs.item(i).appendChild(td);
							}
						}
					};
					// "add" button for row.
				} else if (j == 0) {

					child.title = "添加行";

					// add a row.
					child.onclick = function() {
						var trs = table.getElementsByTagName("tr");
						var length = trs.item(0).getElementsByTagName("td").length - 1;
						var tr = document.createElement("tr");
						for (var i = 0; i < length; i++) {
							var td = document.createElement("td");
							var input = createInput();
							if (i == 0) {
								bindDelTip(input);
							}
							td.appendChild(input);
							tr.appendChild(td);
						}
						// insert before the "add" button.
						table.insertBefore(tr, trs.item(trs.length - 1));
					};
					// unnecessary spaces.
				} else {
					continue;
				}

				// upper left td.
			} else if (i == 0 && j == 0) {

				child = document.createElement("span");
				child.innerHTML = "行名\\列名";
				child.style.fontSize = "small";

				// other tds.
			} else {
				child = createInput();
				if (j == 0) {
					bindDelTip(child);
				}
				if (i == 0) {
					bindDelTip(child, true);
				}
			}
			td.appendChild(child);
			tr.appendChild(td);
		}
		table.appendChild(tr);
	}
	return table;
}

function createDisplayTable(table) {

	var tb = document.createElement("table");
	tb.style.border = "1px black solid";
	tb.style.textAlign = "center";

	for (var i = -1; i < table.array.length; i++) {

		var tr = document.createElement("tr");
		for (var j = -1; j < table.array[0].length; j++) {

			var td = document.createElement("td");
			td.style.padding = "3px 5px";

			// for upper left.
			if (i == -1 && j == -1) {

				td.style.borderBottom = "1px black solid";
				td.style.borderRight = "1px black solid";
				td.innerHTML = "\b";

				// for column names.
			} else if (i == -1) {

				td.style.borderBottom = "1px black solid";
				td.style.fontSize = "small";
				td.innerHTML = table.colNames[j];

				// for row names.
			} else if (j == -1) {

				td.style.borderRight = "1px black solid";
				td.style.fontSize = "small";
				td.innerHTML = table.rowNames[i];

				// for data items.
			} else {
				td.innerHTML = table.array[i][j];
			}

			tr.appendChild(td);
		}
		tb.appendChild(tr);
	}
	return tb;
}

/**
 * Note that the number of rows and columns do not include the rowNames and the
 * colNames.
 * 
 * @param {Number}
 *            rows the number of rows
 * @param {Number}
 *            columns the number of columns.
 * @param {Array}
 *            rowNames
 * @param {Array}
 *            colNames
 */
function CommonTable(rows, columns, rowNames, colNames) {

	rows = rows || 0;
	columns = columns || 0;
	this.rowNames = rowNames || createArray(rows);
	this.colNames = colNames || createArray(columns);

	if (this.rowNames.length != rows)
		throw new Error("The length of the rowNames must be equal to the rows");

	if (this.colNames.length != columns)
		throw new Error(
				"The length of the colNames must be equal to the columns");

	this.array = new Array(rows);
	for (var i = 0; i < rows; i++) {
		this.array[i] = createArray(columns, 0);
	}
}
/**
 * 
 * @param {Array}
 *            array
 * @param {String}
 *            name
 */
CommonTable.prototype.addRow = function(array, name) {

	this.array[this.array.length] = array;
	this.rowNames[this.array.length - 1] = name || "";
};

/**
 * @param {Number}
 *            index
 */
CommonTable.prototype.removeRow = function(index) {

	if (index < 0 || index > this.array.length - 1)
		throw new Error("The index is out of range");

	while (index < this.array.length - 1) {
		this.array[index] = this.array[++index];
		this.rowNames[--index] = this.rowNames[++index];
	}
	this.rowNames.length = this.array.length -= 1;
};

/**
 * I am not sure the name of this table, just call it DiversityTable, and this
 * function is used to create it.
 * 
 * @param {Number}
 *            precision the number of decimal the result should keep. Default is
 *            4.
 * @returns {CommonTable} a new CommonTable.
 */
CommonTable.prototype.toDiversityTable = function(precision) {

	if (this.array.length < 2)
		throw new Error(
				"The number of rows of the table can not be less than 2.");

	var length = this.array.length;
	var table = new CommonTable(length, length, this.rowNames, this.rowNames);

	// Keeps the precision or four decimal places.
	precision = precision || 4;

	for (var i = 0; i < length; i++) {

		// Sets the measure of diversity of each object.
		table.array[i][i] = round(getDiversity(this.array[i]), precision);

		// Sets the measure of diversity of the sum of two different object.
		for (var j = i + 1; j < length; j++) {

			table.array[j][i] = round(getDiversity(arraySum(this.array[i],
					this.array[j])), precision);
		}
	}

	// Sets the increment of diversity of each object.
	for (var i = 0; i < length; i++) {
		for (var j = i + 1; j < length; j++) {
			table.array[i][j] = round(table.array[j][i] - table.array[i][i]
					- table.array[j][j], precision);
		}
	}

	return table;
};

/**
 * Only for DiversityTable, return a object contains three properties : min,
 * row, col.
 */
CommonTable.prototype.getMinIncrementInfo = function() {
	var row = 0, col = 1, min = this.array[0][1];
	for (var i = 0; i < this.array.length; i++) {
		for (var j = i + 1; j < this.array.length; j++) {
			if (this.array[i][j] < min) {
				min = this.array[i][j];
				row = i;
				col = j;
			}
		}
	}
	return {
		min : min,
		row : row,
		col : col
	};
};

/**
 * 
 * @returns {Array} the elements in it are the return value of the
 *          createDisplayTable method.
 */
CommonTable.prototype.classify = function(){
	
	var tables = [];

	while (this.array.length > 1) {

		tables.push(createDisplayTable(this));

		// create a diversity table.
		var dTable = this.toDiversityTable();
		tables.push(createDisplayTable(dTable));

		// create a new table by combining two rows of the table.
		var minInfo = dTable.getMinIncrementInfo();
		var newRow = arraySum(this.array[minInfo.row],
				this.array[minInfo.col]);
		this.addRow(newRow, this.rowNames[minInfo.row] + "&"
				+ this.rowNames[minInfo.col]);
		this.removeRow(Math.max(minInfo.row, minInfo.col));
		this.removeRow(Math.min(minInfo.row, minInfo.col));
	}
	return tables;
};

CommonTable.prototype.clone = function(){
	/*
	var rows = this.array.length;
	var columns = this.array[0].length;
	var rowNames = this.rowNames;
	var colNames = this.colNames;
	var table = new CommonTable(rows, columns, rowNames, colNames);*/
};
/**
 * Calculates the measure of diversity.
 */
function getDiversity(array) {
	if (array.length == 0 || array[array.length - 1] == 0)
		return 0;
	var d = array[array.length - 1] * Math.log(array[array.length - 1]);
	for (var i = 0; i < array.length - 1; i++) {
		if (array[i] > 0)
			d -= array[i] * Math.log(array[i]);
	}
	return d;
}