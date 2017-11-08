package io.postman.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page<T> implements Serializable {
	private static final long serialVersionUID = 5192357666324600054L;
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	protected int pageNo = 1;
	protected int pageSize = 15;
	protected String orderFields = null;
	protected String order = null;
	protected boolean autoCount = true;
	protected int start = 0;
	/**
	 * 用于返回统计等其他数据
	 */
	protected Object userdata = null;
	protected List<T> result = new ArrayList<T>();
	protected long totalCount = -1L;

	public Page() {
	}

	public Page(int pageSize) {
		this.pageSize = pageSize;
	}

	public Page(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1)
			this.pageNo = 1;
	}
	//查询所有数据（从搜索引擎中查询数据的时候设置pageNo为0 ）
	public void setPageNoall(int pageNo) {
		this.pageNo = pageNo;
	}
	public Page<T> pageNo(int thePageNo) {
		setPageNo(thePageNo);
		return this;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;

		if (pageSize < 1)
			this.pageSize = 1;
	}

	public Page<T> pageSize(int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}

	public int getFirst() {
		if (this.start != 0)
			return this.start + 1;
		return (this.pageNo - 1) * this.pageSize + 1;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public String getOrderFields() {
		return this.orderFields;
	}

	public void setOrderFields(String orderFields) {
		this.orderFields = orderFields;
	}

	public Page<T> orderFields(String theOrderFields) {
		setOrderFields(theOrderFields);
		return this;
	}

	public String getOrder() {
		return this.order;
	}

	public void setOrder(String order) {
		String[] arrayOfString = StringUtil.split(StringUtil.lowerCase(order), ',');

		for (int i = 0; i < arrayOfString.length; i++) {
			String str = arrayOfString[i];
			if ((!StringUtil.equals("desc", str)) && (!StringUtil.equals("asc", str))) {
				throw new IllegalArgumentException("排序方向" + str + "不是合法值");
			}
		}

		this.order = StringUtil.lowerCase(order);
	}

	public Page<T> order(String theOrder) {
		setOrder(theOrder);
		return this;
	}

	public boolean isOrderFieldsSetted() {
		return (StringUtil.isNotBlank(this.orderFields)) && (StringUtil.isNotBlank(this.order));
	}

	public boolean isAutoCount() {
		return this.autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	public Page<T> autoCount(boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}

	public Object getUserdata() {
		return userdata;
	}

	public void setUserdata(Object userdata) {
		this.userdata = userdata;
	}

	public List<T> getResult() {
		return this.result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getTotalPages() {
		if (this.totalCount < 0L) {
			return -1L;
		}

		long l = this.totalCount / this.pageSize;
		if (this.totalCount % this.pageSize > 0L) {
			l += 1L;
		}
		return l;
	}

	public boolean isHasNext() {
		return this.pageNo + 1 <= getTotalPages();
	}

	public int getNextPage() {
		if (isHasNext()) {
			return this.pageNo + 1;
		}
		return this.pageNo;
	}

	public boolean isHasPre() {
		return this.pageNo - 1 >= 1;
	}

	public int getPrePage() {
		if (isHasPre()) {
			return this.pageNo - 1;
		}
		return this.pageNo;
	}

	public int getStart() {
		return this.start;
	}

	public static String getPageCacheKey(String key) {
		String str = "";
		if (key.startsWith("/")) {
			str = key.substring(1, key.length());
		}

		if (str.indexOf("/") != -1) {
			str = str.substring(0, str.indexOf("/"));
		}

		return str;
	}

	/**
	 * 将orderFields驼峰风格转换成数据库列风格(A_B)
	 * @return 数据库列风格的orderFields(A_B)
	 */
	public String getOrderColumn() {
		if (! StringUtil.isEmptyOrNull(this.orderFields)){
			if (this.orderFields.indexOf(" ") <= 0){
				String orderColumn = "";
				for(int i = 0; i < orderFields.length(); i++){					
					if (! Character.isUpperCase(orderFields.charAt(i)))
						orderColumn += orderFields.charAt(i);
					else
						orderColumn += "_"+orderFields.charAt(i);
		        }
				
				return orderColumn.toUpperCase();
			}
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		Page<String> p = new Page<String>();
		p.setOrderFields("aaaBbbCccDdd");
        System.out.println(p.getOrderColumn());
	}
}