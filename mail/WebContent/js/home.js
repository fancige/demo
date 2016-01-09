load(function() {
	My.ajaxPost("service", "id=finfos", function(resp) {
		var fs = JSON.parse(resp);
		var f = My.get("folder");
		for ( var i in fs) {
			var div = document.createElement("div");
			div.innerHTML = fs[i]["name"];
			div.id = fs[i]["fid"];
			div.className = "menu";
			f.appendChild(div);
		}

		loaded();
	});

	function loaded() {
		
		var nodes = My.gets("div.menu");
		for (var i = 0; i < nodes.length; i++) {
			My.addListener(nodes.item(i), "click", function() {
				for (var j = 0; j < nodes.length; j++) {
					if (this == nodes.item(j)) {
						if (this.className.indexOf("focus") == -1) {
							this.className += " focus";
						}
					} else {
						nodes.item(j).className = nodes.item(j).className
								.replace(/\s?focus/, "");
					}
				}
			});	
			
			if(nodes.item(i).id == "send"){
				My.addListener(nodes.item(i), "click", function(){
				});
			}
		}
	}

	function resize() {
		var min = parseInt(My.getCss(My.get("sidebar"))["minWidth"]) || 0;
		var max = window.innerWidth
				- parseInt(My.getCss(My.get("contentPane"))["minWidth"]) || 0;
		var x = event.clientX;
		if (x > min && x < max) {
			My.get("sidebar").style.width = x + "px";
			My.get("splitbar").style.left = x + "px";
			My.get("contentPane").style.left = x + 5 + "px";
		}
	}

	My.addListener(My.get("splitbar"), "mousedown", function() {
		My.addListener(document, "mousemove", resize);
		My.addListener(document, "mouseup", function() {
			My.removeListener(document, "mousemove", resize);
		});
	});

	
//	var d = document.createElement("div");
//	d.className = "msgItem";
//	var span = document.createElement("span");
//	span.className = "subject msgCnt";
//	span.innerHTML = "主题";
//	My.addListener(span, "click", function() {
//		My.get("contentPane").style.background = "green";
//		location.search = "?sub=123";
//	});
//	d.appendChild(span);
//	span = document.createElement("span");
//	span.innerHTML = "发件人发件人发件人发件人发件人发件人发件人发件人发件";
//	span.className = "from msgCnt";
//	d.appendChild(span);
//	span = document.createElement("span");
//	span.innerHTML = "时间";
//	span.className = "date msgCnt";
//	d.appendChild(span);
//	My.get("contentPane").appendChild(d);
	
});