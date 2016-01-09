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

/**
 * 
 * @param {String}
 *            id
 * @returns {Element}
 */
function crt(tagName) {

	return document.createElement(tagName);
}

function ajax(method, url, async, data, callback) {

	var xhr;
	if (window.XMLHttpRequest) {

		xhr = new XMLHttpRequest();
	} else {

		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xhr.onerror = function() {
		alert("网络连接错误");
	};
	xhr.ontimeout = function() {
		alert("网络连接超时");
	};
	xhr.onreadystatechange = function() {

		if (xhr.readyState == 4) {
			callback(xhr.responseText);
		}
	};
	xhr.open(method, url, async);
	xhr.timeout = 5000;
	if (method.toLowerCase() == "post") {
		xhr.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");
	}
	xhr.send(data);
};

function connect(data, callback) {
	ajax("post", "myservlet", true, data, callback);
}

function lockButton(button) {
	button.disabled = "disabled";
	button.className = button.innerHTML;
	button.innerHTML = "请稍后";
}

function unlockButton(button) {
	button.removeAttribute("disabled");
	button.innerHTML = button.className;
}

function appendRow(table, data) {

	var rows = gets("tr", table);
	var firstColumn = gets("td", rows.item(0));
	var lastRow = rows.item(rows.length - 1);
	var editable = gets("td", lastRow).length == 1;

	var rowIndex = 1;
	if (!editable) {
		rowIndex = rows.length;
	} else if (rows.length > 2) {
		rowIndex = gets("td", rows.item(rows.length - 2)).item(0).id;
		rowIndex = parseInt(rowIndex) + 1;
	}
	var tr = crt("tr");
	var td = crt("td");
	td.innerHTML = rowIndex;
	td.className = rowIndex;
	td.id = rowIndex;
	if (editable) {
		td.title = "单击标记是否删除该条记录，双击移除该记录";
		td.style.cursor = "pointer";
		td.onmouseover = function() {
			this.innerHTML = this.className == "X" ? rowIndex : "X";
			this.style.color = this.innerHTML == "X" ? "red" : "black";
		};
		td.onmouseout = function() {
			this.innerHTML = this.className;
			this.style.color = this.innerHTML == "X" ? "red" : "black";
		};
		td.onclick = function() {
			this.className = this.className == "X" ? rowIndex : "X";
		};
		td.ondblclick = function() {
			table.removeChild(tr);
		};
	}
	tr.appendChild(td);

	var selects = [];
	var selectedIndexs = [];
	for (var i = 0; i < firstColumn.length - 1; i++) {
		td = crt("td");
		if (editable) {
			var limits = firstColumn.item(i + 1).className.split(",");
			if (limits.length > 2) {
				var select = crt("select");
				selects.push(select);
				for (var j = 2; j < limits.length; j++) {
					var option = crt("option");
					option.text = limits[j];
					if (option.text == data[i]) {
						selectedIndexs.push(j - 2);
					}
					select.appendChild(option);
				}
				td.appendChild(select);
			} else {
				var input = crt("input");
				input.type = "text";
				input.maxLength = limits[0];
				input.placeholder = limits[1] == "1" ? "必填" : "";
				input.style.width = "100%";
				input.value = data[i] ? data[i] : "";
				td.appendChild(input);
			}
			tr.appendChild(td);
		} else {
			td.innerHTML = data[i] ? data[i] : "";
			tr.appendChild(td);
		}
	}
	editable ? table.insertBefore(tr, lastRow) : table.appendChild(tr);
	for (var index = 0; index < selects.length; index++) {
		selects[index].selectedIndex = selectedIndexs[index];
	}
}

function createEmptyTable(tableName, editable, callback) {
	var data = "type=tableInfo&tableName=" + tableName;
	connect(data, function(tableInfo) {
		tableInfo = JSON.parse(tableInfo);
		var columnAliases = tableInfo["columnAliases"];
		var columnCharLengths = tableInfo["columnCharLengths"];
		var columnNotNulls = tableInfo["columnNotNulls"];
		var enums = tableInfo["enums"];
		// console.log(enums);
		var table = crt("table");
		var tr = crt("tr");
		var td = crt("td");
		td.innerHTML = "序号";
		tr.appendChild(td);
		for (var i = 0; i < columnCharLengths.length; i++) {
			td = crt("td");
			td.innerHTML = columnAliases[i];
			td.className = columnCharLengths[i] + "," + columnNotNulls[i];
			if (enums[i + 1])
				td.className += "," + enums[i + 1];
			tr.appendChild(td);
		}
		table.appendChild(tr);

		if (editable) {
			tr = crt("tr");
			td = crt("td");
			td.innerHTML = "+";
			td.title = "点击添加�?�?";
			td.className = "btnAdd";
			td.style.cursor = "pointer";
			td.onclick = function() {
				appendRow(table, "");
			};
			tr.appendChild(td);
			table.appendChild(tr);
			table.className = tableName;
		}
		callback(table);
	});
}

function submitTable(table, callback) {

	if(!getCurrentTable() || !gets("input", getCurrentTable())){
		return;
	}
	
	var updateData = "";
	var deleteData = "";
	var rows = gets("tr", table);
	var columnNotNulls = [];
	for (var i = 1; i < gets("td", rows.item(0)).length; i++) {
		columnNotNulls.push(gets("td", rows.item(0)).item(i).className
				.split(",")[1]);
	}
	for (var i = 1; i < rows.length - 1; i++) {
		var columns = gets("td", rows.item(i));

		if (columns.item(0).innerHTML == "X") {

			var child = gets("input", columns.item(1)).item(0);
			if (child) {
				deleteData += child.value + ",";
			} else {
				child = gets("select", columns.item(1)).item(0);
				deleteData += child.options[child.selectedIndex].text + ",";
			}
		} else {
			for (var j = 1; j < columns.length; j++) {
				var child = gets("input", columns.item(j)).item(0);
				if (child) {
					if (child.value || columnNotNulls[j - 1] == "0")
						updateData += child.value + ",";
					else {
						alert(gets("td", rows.item(0)).item(j).innerHTML
								+ "不能为空");
						return;
					}
				} else {
					child = gets("select", columns.item(j)).item(0);
					updateData += child.options[child.selectedIndex].text + ",";
				}
			}
		}
	}
	var data = "type=update&updateData=" + updateData + "&deleteData="
			+ deleteData + "&tableName=" + table.className;
	connect(data, callback);
}

function editTable(tableName, params) {

	removeCurrentTable();
	createEmptyTable(tableName, true, function(table) {
		connect(params, function(text) {
			var array = JSON.parse(text);
			for (var i = 0; i < array.length; i++) {
				appendRow(table, array[i]);
			}
			get("resultPanel").appendChild(table);
		});
	});
}

function searchTable(tableName, params) {

	removeCurrentTable();
	createEmptyTable(tableName, false, function(table) {
		connect(params, function(text) {
			var array = JSON.parse(text);
			if (array.length != 0) {
				for (var i = 0; i < array.length; i++) {
					appendRow(table, array[i]);
				}
				get("resultPanel").appendChild(table);
			} else {
				var span = crt("span");
				span.innerHTML = "无记录";
				get("resultPanel").appendChild(span);
			}
		});
	});
}

function getCurrentTable() {
	return gets("table", get("resultPanel")).item(0);
}

function removeCurrentTable() {
	var div = crt("div");
	div.id = "resultPanel";
	div.style.marginTop = "30px";
	get("body").replaceChild(div, get("resultPanel"));
}

function showPage() {

	removeCurrentTable();
	connect("type=page", function(text) {
		var pages = gets("div.page");
		for (var i = 0; i < pages.length; i++) {
			pages.item(i).style.display = "none";
		}
		if (text == "admin") {
			loadAdminPage();
		} else if (text == "teacher") {
			loadTeacherPage();
		} else if (text == "student") {
			loadStudentPage();
		} else {
			get("loginPage").style.display = "block";
		}
	});
}

function loadAdminPage() {

	var page = get("adminPage");
	page.style.display = "block";

	connect("type=tableNames", function(text) {
		var select = crt("select");
		select.id = "selectTable";
		select.style.height = "28px";
		var names = JSON.parse(text);
		for (var i = 0; i < names.length; i++) {
			var opt = crt("option");
			opt.innerHTML = names[i];
			select.appendChild(opt);
		}
		get("selectTable").parentNode.replaceChild(select, get("selectTable"));
	});

	get("view").onclick = function() {
		var select = get("selectTable");
		var tableName = select.options[select.selectedIndex].text;
		searchTable(tableName, "type=query&tableName=" + tableName);
	};

	get("edit").onclick = function() {
		var select = get("selectTable");
		var tableName = select.options[select.selectedIndex].text;
		editTable(tableName, "type=query&tableName=" + tableName);
	};

	get("submit").onclick = function() {
		submitTable(getCurrentTable(), function(result) {
			alert(result);
		});
	};
}

function loadTeacherPage() {
	get("teacherPage").style.display = "block";
}

function loadStudentPage() {

	get("studentPage").style.display = "block";

	var btns = gets("button", get("stuMenu"));
	var selectPanel = function(index) {

		removeCurrentTable();
		var divs = gets("div", get("stuPanel"));
		for (var i = 0; i < btns.length; i++) {

			btns.item(i).style.background = index == i ? "black" : "";
			btns.item(i).style.color = index == i ? "white" : "";
			divs.item(i).style.display = index == i ? "block" : "none";
		}
	};
	selectPanel(0);

	connect("type=tableInfo&tableName=student", function(info) {

		var select = crt("select");
		select.style.height = "28px";
		info = JSON.parse(info);
		var columnAliases = info["columnAliases"];
		for ( var i in columnAliases) {
			var option = crt("option");
			option.text = columnAliases[i];
			select.appendChild(option);
		}
		var old = gets("select", get("stuPanel")).item(0);
		old.parentNode.replaceChild(select, old);
	});

	var getMyInfo = function() {
		connect("type=tableInfo&tableName=student", function(info) {
			connect("type=myInfo", function(data) {
				info = JSON.parse(info);
				var columnAliases = info["columnAliases"];
				var array = JSON.parse(data);
				var divs = gets("div", get("stuPanel"));
				var div = crt("div");
				for ( var i in columnAliases) {

					var p = crt("p");
					p.style.fontSize = "25px";
					var b = crt("b");
					b.innerHTML = columnAliases[i] + ":&nbsp;&nbsp;";
					var span = crt("span");
					span.innerHTML = array[0][i];
					p.appendChild(b);
					p.appendChild(span);
					div.appendChild(p);
				}
				get("stuPanel").replaceChild(div, divs.item(0));
			});
		});
	};

	getMyInfo();

	btns.item(0).onclick = function() {

		selectPanel(0);
		getMyInfo();
	};

	btns.item(1).onclick = function() {
		selectPanel(1);
	};

	btns.item(2).onclick = function() {
		selectPanel(2);
	};

	var submits = gets("button", get("stuPanel"));
	submits.item(0).onclick = function() {

		var select = gets("select", get("stuPanel")).item(0);
		var alias = select.options[select.selectedIndex].text;
		var value = gets("input", get("stuPanel")).item(0).value;
		searchTable("student", "type=query&tableName=student&columnAlias="
				+ alias + "&columnValue=" + value);
	};
	submits.item(1).onclick = function() {
		searchTable("sc", "type=myGrade&courName="
				+ gets("input", get("stuPanel")).item(1).value);
	};

}

window.onload = function() {
	get("formLogin").onsubmit = function() {

		lockButton(get("btnSubmit"));
		var username = get("username").value;
		var password = get("password").value;
		var data = "type=login&username=" + username + "&password=" + password;
		var callback = function(result) {

			unlockButton(get("btnSubmit"));
			if (result == "登陆成功") {
				showPage();
			} else {
				alert(result);
			}
		};
		connect(data, callback);
		get("password").value = "";
		return false;
	};

	var btn = gets("button.exit");
	for (var i = 0; i < btn.length; i++) {
		btn.item(i).onclick = function() {
			connect("type=logout", function() {
				showPage();
			});
		};
	}
	showPage();
};