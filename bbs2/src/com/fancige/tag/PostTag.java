package com.fancige.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.fancige.bean.Post;
import com.fancige.manager.PostManager;
import com.fancige.util.StringHandler;

public class PostTag extends SimpleTagSupport {
	private String type;
	private String scope;
	/**
	 * The name of the variable put into the scope.
	 */
	private String var;
	/**
	 * The number of posts each group at most contains.
	 */
	private int groupSize = 10;
	/**
	 * The index of the group.
	 */
	private int groupIndex = 1;
	/**
	 * The number of the indexs near the current group index.
	 */
	private int nearIndexSize = 0;

	private Map<String, String> filter;

	public void setFilter(String filter) {
		this.filter = StringHandler.toMap(filter);
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public void setGroupSize(String groupSize) {
		this.groupSize = StringHandler.parseInt(groupSize, 10);
		this.groupSize = Math.max(1, this.groupSize);
	}

	public void setGroupIndex(String groupIndex) {
		this.groupSize = StringHandler.parseInt(groupIndex, 1);
		this.groupIndex = Math.max(1, this.groupSize);
	}

	public void setNearIndexSize(String nearIndexSize) {
		this.nearIndexSize = StringHandler.parseInt(nearIndexSize, 0);
	}

	@Override
	public void doTag() throws JspException, IOException {
		// Sets the scope.
		int scope = PageContext.PAGE_SCOPE;
		if ("request".equals(this.scope))
			scope = PageContext.REQUEST_SCOPE;
		if ("session".equals(this.scope))
			scope = PageContext.SESSION_SCOPE;
		if ("application".equals(this.scope))
			scope = PageContext.APPLICATION_SCOPE;

		// Resets the var.
		if (null == var || var.isEmpty())
			var = type;

		JspContext context = getJspContext();

		if ("get".equals(type)) {
			context.setAttribute(var, PostManager.getPost(filter.get("postid")));
		} else if ("gets".equals(type)) {
			int amount = PostManager.countRows(filter, true);
			// maxIndex;
			int maxIndex = amount / groupSize;
			if (0 != amount % groupSize)
				maxIndex += 1;

			// groupIndex
			String page = filter.get("page");
			if (page != null) {
				groupIndex = StringHandler.parseInt(page, 1);
				groupIndex = Math.max(groupIndex, 1);
			}
			groupIndex = Math.min(maxIndex, groupIndex);

			int offset = (groupIndex - 1) * groupSize;
			List<Post> posts = PostManager.getPosts(filter, true, offset, groupSize);

			// nearIndexs
			if (nearIndexSize > 0) {
				List<Integer> indexs = new ArrayList<>();
				int half = nearIndexSize / 2;
				int first = 1;
				int length = nearIndexSize;

				if (maxIndex < nearIndexSize)
					length = maxIndex;
				else if (maxIndex - groupIndex <= half)
					first = maxIndex - nearIndexSize + 1;
				else if (groupIndex > half)
					first = groupIndex - half + 1;

				for (int i = 0; i < length; i++)
					indexs.add(first++);
				context.setAttribute("nearIndexs", indexs, scope);
			}
			context.setAttribute("currentIndex", groupIndex, scope);
			context.setAttribute("maxIndex", maxIndex, scope);
			context.setAttribute(var, posts, scope);
		}
	}
}
