function process(obj) {
	var divList = document.createElement("div");
	divList.id = "divList";
	get("body").replaceChild(divList, get("divList"));

	var appendSpan = function(parent, text, style) {

		var span = document.createElement("span");
		span.innerHTML = text;
		if (style)
			for ( var i in style) {
				span.style[i] = style[i];
			}
		parent.appendChild(span);
	};

	for ( var i in obj) {

		var div = document.createElement("div");
		div.style.borderBottomWidth = "1px";
		div.style.borderBottomStyle = "dotted";

		var p = document.createElement("p");
		p.style.fontWeight = "bold";

		var a = document.createElement("a");
		a.href = obj[i].url;
		a.innerHTML = obj[i].name;
		p.appendChild(a);

		a = document.createElement("a");
		a.href = "download?type=download&filename=" + encodeURI(obj[i].name);
		a.innerHTML = "下载";
		a.style.color = "green";
		a.style.marginLeft = "20px";
		p.appendChild(a);

		div.appendChild(p);

		p = document.createElement("p");
		p.style.fontSize = "13px";

		appendSpan(p, "文件大小：", {
			fontWeight : "bold",
			marginLeft : "30px"
		});

		appendSpan(p, suitSizeUnit(obj[i].size));

		appendSpan(p, "更新日期：", {
			fontWeight : "bold",
			marginLeft : "20px"
		});

		appendSpan(p, obj[i].modification);

		div.appendChild(p);

		divList.appendChild(div);
	}
};

function suitSizeUnit(origin) {

	var result;
	if (origin > 1024 * 1024 * 1024)
		result = (origin / 1024 / 1024 / 1024).toFixed(2) + "GB";
	else if (origin > 1024 * 1024)
		result = (origin / 1024 / 1024).toFixed(2) + "MB";
	else if (origin > 1024)
		result = (origin / 1024).toFixed(2) + "KB";
	else
		result = origin.toFixed(2) + "B";
	return result;
};

load(function() {

	var updateList = function() {

		var script = document.createElement("script");
		script.id = "script";
		script.src = "download?type=list&jsonp=process";
		if (get("script"))
			document.body.removeChild(get("script"));
		document.body.appendChild(script);
	};

	updateList();

	var validateFile = function(filename, spanState) {

		My.post("upload", "type=validate&filename=" + filename, function(text) {
			spanState.innerHTML = text;
		});
	};

	var form = gets("form").item(0);
	var fileCount = -1;

	var showFile = function(file) {

		var p = document.createElement("p");
		p.className = "pFile";
		p.style.fontSize = "13px";

		var inputName = document.createElement("input");
		inputName.type = "text";
		inputName.disabled = "disabled";
		inputName.id = "inputName" + ++fileCount;
		inputName.value = file.name;
		inputName.onblur = function() {
			this.disabled = "disabled";
			validateFile(inputName.value, spanState);
		};

		addListener(inputName, "input", function() {
			validateFile(inputName.value, spanState);
			if (window.File)
				file.name = inputName.value;
		});

		var btnRename = document.createElement("button");
		btnRename.innerHTML = "重命名";
		btnRename.id = "btnRename" + fileCount;
		btnRename.onclick = function() {

			inputName.removeAttribute("disabled");
		};

		var btnDel = document.createElement("button");
		btnDel.innerHTML = "移除";
		btnDel.id = "btnDel" + fileCount;
		btnDel.onclick = function() {

			if (window.File) {
				delete files[this.id.substring(6)];
			} else
				form.removeChild(file);
			divUpload.removeChild(p);
		};

		p.appendChild(inputName);
		p.appendChild(btnRename);
		p.appendChild(btnDel);

		if (window.File) {

			var btnCancel = document.createElement("button");
			btnCancel.id = "btnCancel" + fileCount;
			btnCancel.innerHTML = "取消";
			btnCancel.style.display = "none";
			p.appendChild(btnCancel);
		}

		var spanState = document.createElement("span");
		spanState.id = "spanState" + fileCount;
		spanState.style.marginLeft = "20px";
		p.appendChild(spanState);
		divUpload.insertBefore(p, form.nextSibling);

		validateFile(inputName.value, spanState);
	};

	var files = [];

	var addInputFile = function() {

		inputFile = document.createElement("input");
		inputFile.type = "file";

		if (window.File) {

			inputFile.id = "inputFile";
			inputFile.multiple = "multiple";
			inputFile.style.display = "none";
			inputFile.onchange = function() {

				for (var i = 0; i < inputFile.files.length; i++) {
					files.push(inputFile.files[i]);
					showFile(inputFile.files[i]);
				}
				form.removeChild(inputFile);
				addInputFile();
			};

			get("btnAdd").onclick = function() {
				inputFile.click();
			};

			form.appendChild(inputFile);
		} else {

			inputFile.id = "btnAdd";
			inputFile.style.display = "inline";
			inputFile.onchange = function() {

				var index = inputFile.value.lastIndexOf("/");
				if (index == -1)
					index = inputFile.value.lastIndexOf("\\");
				var name = inputFile.value.substring(index + 1);
				inputFile.name = name;
				showFile(inputFile);
				inputFile.removeAttribute("id");
				inputFile.style.display = "none";
				addInputFile();
			};
			form.insertBefore(inputFile, get("btnSubmit"));
		}
	};

	if (!window.File) {
		form.removeChild(get("btnAdd"));
	}
	addInputFile();

	var upload = function(file, index) {

		var inputName = get("inputName" + index);
		var btnRename = get("btnRename" + index);
		var btnDel = get("btnDel" + index);
		var btnCancel = get("btnCancel" + index);
		var spanState = get("spanState" + index);
		var parent = inputName.parentNode;

		btnRename.style.display = "none";
		btnDel.style.display = "none";

		var xhr = new XMLHttpRequest();
		xhr.open("post", "upload?ajax=true&type=upload");

		xhr.onreadystatechange = function() {

			if (xhr.readyState == 3) {

				var li = xhr.responseText.lastIndexOf(":");
				spanState.innerHTML = xhr.responseText.substring(li + 1);
			}

			if (xhr.readyState == 4) {

				if (xhr.responseText.indexOf("success") != -1) {
					parent.removeChild(btnRename);
					parent.removeChild(btnDel);
					parent.removeChild(btnCancel);
					updateList();
				} else {
					btnDel.style.display = "inline";
					btnRename.style.display = "inline";
					btnCancel.style.display = "none";
					files[index] = file;
				}
			}
		};

		var before = new Date();
		var now = new Date();
		var loaded = 0;
		var speed = "";
		xhr.upload.onprogress = function(event) {

			if (event.lengthComputable) {

				now = new Date();
				var nms = now.getSeconds() * 1000 + now.getMilliseconds();
				var bms = before.getSeconds() * 1000 + before.getMilliseconds();
				var ms = nms - bms;
				var tip = "正在上传";
				var margin = ".......";
				var percent = (event.loaded / event.total * 100).toFixed(2)
						+ "%";
				if (ms > 1000) {

					speed = suitSizeUnit((event.loaded - loaded) / ms * 1000)
							+ "/s";
					loaded = event.loaded;
					before = now;
				}
				if (percent == "100.00%")
					spanState.innerHTML = "上传完成，正在保存文件...";
				else
					spanState.innerHTML = tip + margin + percent + margin
							+ speed;
			}
		};

		xhr.upload.onabort = function() {
			spanState.innerHTML = "上传已中断！";
		};

		xhr.upload.onerror = function() {
			spanState.innerHTML = "网络错误！";
		};

		var fm = new FormData();
		fm.append(inputName.value, file);
		xhr.send(fm);

		btnCancel.onclick = function() {
			xhr.abort();
		};

		btnCancel.style.display = "inline";
	};

	form.onsubmit = function() {

		get("btnSubmit").disabled = "disabled";
		get("btnAdd").disabled = "disabled";
		if (!window.File) {

			setTimeout(function() {
				My.loading("正在上传，请稍后...", function() {

					location.reload();
				});
			}, 500);

			return true;
		} else {

			for ( var i in files) {
				upload(files[i], i);
				delete files[i];
			}
			get("btnAdd").removeAttribute("disabled");
			get("btnSubmit").removeAttribute("disabled");
			return false;
		}
	};
});