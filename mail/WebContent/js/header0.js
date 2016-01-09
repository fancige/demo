load(function() {
	var open = false;
	My.addListener(My.get("h0_imgSetting"), "click", function() {
		My.get("h0_divSetting").style.display = open ? "none" : "block";
		open = !open;
	});
});