function huffman(data) {

	// sort from big to small, then you can directly get the smallest and
	// smaller value.
	data.sort(function(a, b) {
		return b - a;
	});

	// change the structure of elements of data to [value, leftChildIndex,
	// rightChildIndex].
	// note that all original data will become the leaves of the result tree.
	// -1 means no such index.
	for ( var i in data) {
		data[i] = [ data[i], -1, -1 ];
	}

	// the final structure of elements of tree:
	// [leaf || node, parent, sideCode, -1 || leftChildIndex, -1 ||
	// rightChildIndex, [sideCode...], [x, y]]
	//
	// each element in the nodes is the index of each node in the tree.
	// 
	// each element in the leaves is the index of each leaf in the tree.
	//
	// keep them in mind otherwise you may feel difficult to understand
	// like this: tree[tree[nodes[1]][3]] - the left child of the second node.
	var tree = [];
	var nodes = [];
	var leaves = [];

	// each combination will remove two and add one.
	// therefore, the amount of combination is equal to the length of original
	// data.
	// in addition, the data will be change as well as its length, so
	// this can not be count < data.length.
	var amount = data.length - 1;
	for (var count = 0; count < amount; count++) {

		// get the smalllest and smaller data and calculate its parent.
		// the smallest data becomes the left child.
		// the smaller data becomes the right child.
		var smallest = data[data.length - 1];
		var smaller = data[data.length - 2];
		var parent = [ smallest[0] + smaller[0] ];

		var leftChild = [ smallest[0], parent[0], 0 ];
		var rightChild = [ smaller[0], parent[0], 1 ];

		// determine where the the children should be put and
		// calculate their indexs.

		if (smallest[1] == -1) {

			leaves.push(tree.length);
		} else {

			nodes.push(tree.length);
		}

		parent.push(tree.length);
		leftChild.push(smallest[1]);
		leftChild.push(smallest[2]);
		tree.push(leftChild);

		if (smaller[1] == -1) {

			leaves.push(tree.length);
		} else {

			nodes.push(tree.length);
		}

		parent.push(tree.length);
		rightChild.push(smaller[1]);
		rightChild.push(smaller[2]);
		tree.push(rightChild);

		// root node
		if (count == amount - 1) {

			nodes.push(tree.length);
			tree.push([ parent[0], -1, -1, parent[1], parent[2], [] ]);
		}

		// put the parent into data and meanwhile you should keep the order of
		// data.
		// the code below detemine which position the parent should be put in.
		// after this operation, the length of data will reduce one.
		for (var i = data.length - 3; i >= 0; i--) {

			// move the smaller data to right.
			if (data[i][0] < parent[0]) {

				data[i + 1] = data[i];

				// it is possible that all data should be moved.
				if (i == 0) {
					data[0] = parent;
					data.length -= 1;
				}
			} else {

				data[i + 1] = parent;
				data.length -= 1;
				break;
			}
		}
	}

	// calculate the huffman code of nodes.
	for (var i = 0; i < nodes.length - 1; i++) {

		var node = tree[nodes[i]];
		var codes = [];
		node.push(codes);

		// the code of side to its parent.
		codes.push(node[2]);

		// find its all parents.
		var parent = node[1];
		for (var j = i + 1; j < nodes.length - 1; j++) {

			if (parent == tree[nodes[j]][0]) {

				// get the code of side of its parent.
				codes.push(tree[nodes[j]][2]);
				// find next parent.
				parent = tree[nodes[j]][1];
			}
		}
	}

	// calculate the huffman code of leaves.
	// determine the first position of search.
	var start = 0;
	for (var i = 0; i < leaves.length; i++) {

		var leaf = tree[leaves[i]];

		// the code of side to its parent.
		var codes = [];
		leaf.push(codes);
		codes.push(leaf[2]);

		// search its parent start from the "start" position.
		while (start < nodes.length) {

			// find its direct parent which has contained all codes of sides it
			// needs.
			var parent = tree[nodes[start]];
			if (leaf[1] == parent[0]) {

				// retrive all codes from its parent.
				for (var j = 0; j < parent[5].length; j++)
					codes.push(parent[5][j]);

				// find its parent out, no need to continue this while loop,
				// back to the for loop.
				// next search for next leaf will start from current position.
				break;
			}
			start++;
		}
	}

	var huffmanRusultCode = document.createElement("div");
	huffmanRusultCode.id = "huffmanRusultCode";
	huffmanRusultCode.style.padding = "20px";
	huffmanRusultCode.style.width = "200px";
	huffmanRusultCode.style.marginTop = "30px";
	huffmanRusultCode.style.position = "absolute";
	for (var i = leaves.length - 1; i >= 0; i--) {

		var codes = tree[leaves[i]][5];
		var code = tree[leaves[i]][0] + ": ";
		for (var j = codes.length - 1; j >= 0; j--) {
			code += codes[j];
		}
		var div = document.createElement("div");
		div.style.fontSize = "20px";
		div.innerHTML = code;
		huffmanRusultCode.appendChild(div);
	}
	document.getElementById("result").replaceChild(huffmanRusultCode,
			document.getElementById("huffmanRusultCode"));

	/**
	 * The following section is for drawing the result tree.
	 */

	var canvas = document.createElement("canvas");
	canvas.id = "huffmanRusultTree";
	canvas.style.marginLeft = "230px";

	// calculate the width of canvas according to the max number of
	// nodes and leaves each level contains.
	var maxNumber = 0;
	var tempNumber = 0;
	var lastLevel = tree[0][5].length;
	for ( var i in tree) {

		if (lastLevel == tree[i][5].length) {

			tempNumber += 1;
		} else {
			maxNumber = Math.max(maxNumber, tempNumber);
			lastLevel = tree[i][5].length;
			tempNumber = 1;
		}
	}

	var r = 30;
	var offsetWidth = 3 * r;
	var offsetHeight = 4 * r;
	canvas.width = offsetWidth * maxNumber + r;
	canvas.height = offsetHeight * (tree[0][5].length + 1);
	document.getElementById("result").replaceChild(canvas,
			document.getElementById("huffmanRusultTree"));

	var c = canvas.getContext("2d");
	c.textAlign = "center";
	c.textBaseline = "middle";
	c.font = "bold 20px Times Roman";
	c.beginPath();

	var level = tree[0][5].length;
	var offsetLeft = 0;

	// circles
	// this drawing is from lower left to upper right
	for ( var i in tree) {

		if (tree[i][3] == -1)
			c.fillStyle = "blue";
		else
			c.fillStyle = "black";

		if (tree[i][5].length != level) {

			level = tree[i][5].length;
			offsetLeft = 2 * r;

		} else {

			offsetLeft += offsetWidth;
		}

		var y = level * offsetHeight + 2 * r;
		var x = offsetLeft;

		tree[i].push([ x, y ]);

		c.moveTo(x + r, y);
		c.arc(x, y, r, 0, 2 * Math.PI);
		c.stroke();

		c.fillText(tree[i][0], x, y);
	}

	// sides
	c.textBaseline = "bottom";
	for (var i = 0; i < nodes.length; i++) {

		var parent = tree[nodes[i]];
		var parentPoint = parent[6];

		for (var j = 0; j < 2; j++) {

			var childPoint = tree[parent[j + 3]][6];
			var height = childPoint[1] - parentPoint[1];
			var width = childPoint[0] - parentPoint[0];

			var scale = r / Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2));
			c.moveTo(parentPoint[0] + width * scale, parentPoint[1] + height
					* scale);
			c.lineTo(childPoint[0] - width * scale, childPoint[1] - height
					* scale);
			c.stroke();

			c.textAlign = width <= 0 ? "right" : "left";
			c.fillText(j, parentPoint[0] + width / 2, parentPoint[1] + height
					/ 2);
		}
	}
}