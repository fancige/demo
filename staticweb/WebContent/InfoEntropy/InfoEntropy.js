window.onload = function() {

	get("sample").onclick = function() {

		displayResult(getSampleTable().classify());
	};

	get("submit1").onclick = function() {
		submit1();
	};
	get("submit2").onclick = function() {
		submit2();
	};

	var panel = get("topPanel");
	for (var i = 1; i < 4; i++) {
		var btn = document.createElement("button");
		btn.innerHTML = "第" + i + "页";
		btn.style.marginLeft = "30px";
		btn.onclick = function() {
			displayPage(this.innerHTML.charAt(1));
		};
		panel.appendChild(btn);
	}
};

function getSampleTable() {
	var rowNames = [ "党参", "桔梗", "沙参", "芥尼", "羊乳", "石沙参" ];
	var colNames = [ "茎缠绕否", "株高", "叶序", "叶缘", "花序", "子房室数", "果列方式", "种具翼否",
			"小计" ];
	var x1 = [ 2, 2, 1, 0, 0, 1, 2, 0 ];
	var x2 = [ 0, 0, 0, 1, 0, 2, 1, 0 ];
	var x3 = [ 0, 0, 2, 1, 2, 0, 0, 0 ];
	var x4 = [ 0, 0, 0, 2, 1, 0, 0, 0 ];
	var x5 = [ 2, 2, 1, 0, 0, 1, 2, 2 ];
	var x6 = [ 0, 0, 0, 1, 2, 0, 0, 0 ];
	var sample = [ x1, x2, x3, x4, x5, x6 ];

	for ( var i in sample) {
		appendSum(sample[i]);
	}

	var sampleTable = new CommonTable();
	for ( var i in sample) {
		sampleTable.addRow(sample[i]);
	}
	sampleTable.rowNames = rowNames;
	sampleTable.colNames = colNames;
	return sampleTable;
}

function displayPage(pageIndex) {

	for (var i = 1; i <= 3; i++) {
		if (i == pageIndex)
			get("page" + i).style.display = "block";
		else
			get("page" + i).style.display = "none";
	}
};

function submit1() {
	var row = get("row").value;
	var col = get("col").value;
	if (!isNaturalNumber(row))
		alert("行数值无效！");
	else if (!isNaturalNumber(col))
		alert("列数值无效！");
	else {
		displayPage(2);
		var page2 = get("page2");
		var table = createInputTable(parseInt(row), parseInt(col));
		table.id = "inputTable";
		page2.replaceChild(table, get("inputTable"));
	}
}

function submit2() {

	// get the input table and verify its data.
	var table = get("inputTable");
	var trs = gets("tr", table);
	var tds = gets("td", trs.item(0));

	if (trs.length < 4 || tds.length < 3) {

		alert("最少要两行，一列数据！");
	} else {

		// create a CommonTable object and initialize it with the data in the
		// input table.
		var tb = new CommonTable();

		// for data items, start from (1,1).
		for (var i = 1; i < trs.length - 1; i++) {
			tds = gets("td", trs.item(i));
			var row = [];
			for (var j = 1; j < tds.length; j++) {

				// get the value of the input tag and remove all whitespaces.
				var value = gets("input", tds.item(j)).item(0).value.replace(
						/\s/g, "");

				// default value is 0.
				if (value == "") {
					row.push(0);

				} else if (isNaturalNumber(value)) {
					row.push(parseInt(value));

				} else {
					alert("数据项第" + i + "行，第" + j + "列只能包含数字。");
					return;
				}
			}
			appendSum(row);
			tb.addRow(row);
		}

		// for colNames, the first column.
		var colNames = [];
		tds = gets("td", trs.item(0));
		for (var i = 1; i < tds.length - 1; i++) {

			var value = gets("input", tds.item(i)).item(0).value;

			// default value is the column index.
			if (value.match(/\S/g) == null)
				colNames.push(i);
			else {
				// remove all whitespace on the two sides.
				value = value.match(/\s*(.*)\s*/)[1];
				colNames.push(value);
			}
		}
		colNames.push("小计");
		tb.colNames = colNames;

		// for rowNames, the first row.
		var rowNames = [];
		for (var i = 1; i < trs.length - 1; i++) {
			var td = gets("td", trs.item(i)).item(0);
			var value = gets("input", td).item(0).value;

			// default value is the row index.
			if (value.match(/\S/g) == null)
				rowNames.push(i);
			else {
				// remove all whitespace on the two sides.
				value = value.match(/\s*(.*)\s*/)[1];
				rowNames.push(value);
			}
		}
		tb.rowNames = rowNames;

		// show a confirm dialog .

		var bg = document.createElement("div");
		bg.className = "background";
		document.body.appendChild(bg);

		var dialog = document.createElement("div");
		dialog.className = "dialog";

		var displayTable = createDisplayTable(tb);
		dialog.appendChild(displayTable);
		displayTable.style.margin = "0px auto";

		var p = document.createElement("p");
		p.innerHTML = "请检查表格是否有误，点击确定开始分类。";
		dialog.appendChild(p);

		var btn1 = document.createElement("button");
		btn1.innerHTML = "确定";
		dialog.appendChild(btn1);

		var btn2 = document.createElement("button");
		btn2.innerHTML = "取消";
		btn2.style.marginLeft = "10px";
		dialog.appendChild(btn2);

		var dialogParent = document.createElement("div");
		dialogParent.className = "dialogParent";
		dialogParent.appendChild(dialog);
		document.body.appendChild(dialogParent);

		// set the width of the dialog.
		if (displayTable.offsetWidth > 350)
			dialog.style.width = displayTable.offsetWidth + "px";
		else
			dialog.style.width = 350 + "px";

		// start the classification and display the result tables.
		btn1.onclick = function() {
			document.body.removeChild(dialogParent);
			document.body.removeChild(bg);
			displayResult(tb.classify());
		};

		// cancel the classification.
		btn2.onclick = function() {
			document.body.removeChild(dialogParent);
			document.body.removeChild(bg);
		};
	}
}

/**
 * specify how the result tables should be displayed.
 * 
 * @param {Array}
 *            tables
 */
function displayResult(tables) {

	displayPage(3);
	var resultPanel = document.createElement("div");
	resultPanel.id = "result";
	get("page3").replaceChild(resultPanel, get("result"));

	var btn = document.createElement("button");
	btn.innerHTML = "展开所有表格";
	btn.style.width = "300px";
	btn.style.marginLeft = "50px";
	resultPanel.appendChild(btn);

	// each pane contains two tables.
	var panes = [];
	// margin of each pane when the panes are folded.
	var foldMargin = [];
	for (var i = 0; i < tables.length; i += 2) {

		var zIndex = i / 2 + 1;
		var pane = document.createElement("div");
		pane.style.padding = "30px";
		pane.style.border = "1px black solid";
		pane.style.position = "absolute";
		pane.style.zIndex = zIndex;
		pane.style.margin = zIndex * 50 + "px";
		foldMargin.push(zIndex * 50 + "px");		
		pane.style.background = "white";
		pane.style.textAlign = "center";
		pane.style.boxShadow = "10px 10px 5px #888888";
		
		var index = document.createElement("span");
		index.style.cssFloat = "right";
		index.style.marginTop = "-25px";
		index.style.marginRight = "-25px";
		index.style.top = "5px";
		index.style.right = "5px";
		index.innerHTML = "表" + zIndex;
		index.style.fontSize = "larger";
		index.style.fontWeight = "bold";
		pane.appendChild(index);

		var title = document.createElement("h3");
		if (i == 0)
			title.innerHTML = "初始数据";
		else
			title.innerHTML = "第" + (zIndex - 1) + "次合并结果。";
		pane.appendChild(title);

		pane.appendChild(tables[i]);
		pane.appendChild(tables[i + 1]);
		
		tables[i + 1].style.marginTop = "30px";		
		resultPanel.appendChild(pane);
		panes.push(pane);
	}
	
	var resultPanelHeight = panes[0].offsetHeight + 100 + "px";
	resultPanel.style.height = resultPanelHeight;

	// the previous zIndex of the pane on which the mouse is location.
	var preIndex = 0;

	for ( var i in panes) {

		panes[i].onclick = function() {
			var index = 1;
			for (var j = 0; j < panes.length; j++) {

				if (panes[j] == this) {
					this.style.zIndex = preIndex = panes.length;
				} else {
					panes[j].style.zIndex = index++;
				}
			}
		};

		panes[i].onmouseover = function() {

			preIndex = this.style.zIndex;
			this.style.zIndex = panes.length + 1;
		};

		panes[i].onmouseout = function() {
			this.style.zIndex = preIndex;
		};
	}

	var fold = true;

	btn.onclick = function() {

		if (fold) {

			btn.innerHTML = "收起所有表格";
			for (var i = 0; i < panes.length; i++) {
				
				panes[i].style.position = "static";
				panes[i].style.margin = "50px";
				var tbs = gets("table", panes[i]);
				panes[i].style.width = Math.max(tbs.item(0).clientWidth, tbs.item(1).clientWidth) + 20 + "px";
				resultPanel.style.height = "auto";
			}
		} else {
			btn.innerHTML = "展开所有表格";
			for (var i = 0; i < panes.length; i++) {
				panes[i].style.margin = foldMargin[i];
				panes[i].style.position = "absolute";
				panes[i].style.width = "auto";
				resultPanel.style.height = resultPanelHeight;
			}
		}
		fold = !fold;
	};
}