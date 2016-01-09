function get(str){
	
	this.css = function(name, value){
		
		alert(value);
	};
	
	// contains attribute class;
	if(str.indexOf(".") != -1){
		
		var array = new Array();
		var classValue;
		var tags;
		var index = str.indexOf(".");
		// only class.
		if(index == 0){
			 
			classValue = str.substring(1,str.length);
			tags = document.body.childNodes;
		
		// tag + class.
		}else{
			
			var tagName = str.substring(0, index);
			tags = document.getElementsByTagName(tagName);
			classValue = str.substring(index + 1, str.length);
		}
		
		for(var i = 0; i < tags.length; i++){
			
			if(tags.item(i).className == classValue){
				
				array.push(tags.item(i));
			}
		}
		return array;
		
	}else if(str.indexOf("#") != -1){
		
		return document.getElementById(str.substring(str.indexOf("#") + 1, str.length));
		
	}else{
		
		return document.getElementsByTagName(str);
	}
};

function ajax(method, url, async, data, callback){
	
	var xmlhttp;
	if(XMLHttpRequest){
		
		xmlhttp = new XMLHttpRequest();
	}else{
		
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function(){
		
		if(xmlhttp.readyState == 4 && xmlhttp.status == 200){

			callback(xmlhttp.responseText);
		}
	};
	xmlhttp.open(method, url, async);
	if(method == "POST")
	xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	xmlhttp.send(data);
}
function GetCurrentStyle(obj, prop) {
    if (obj.currentStyle) { //IE浏览器
            return obj.currentStyle[prop];
    } else if (window.getComputedStyle) { //W3C标准浏览器
            propprop = prop.replace(/([A-Z])/g, "-$1");
            propprop = prop.toLowerCase();
            return document.defaultView.getComputedStyle(obj, null)[propprop];
    }
    return null;
}