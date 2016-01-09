load(function(){
	My.addListener(My.get("imgCode"), "click", function(){
		this.src = "verificode?" + Math.random();
	});
});