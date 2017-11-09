/**    
 * @Title: BusinessException.java
 * @Package com.cms.model.util
 * @Description: TODO
 * @author 母德亮
 * @date 2016年8月12日 上午10:28:24
 * @version V1.0
 */
package io.postman.common.exception;

/**
 * @ClassName: EventComponentException
 * @Description: 事件组件异常类
 * @author caojun
 * @date 2016年8月12日 上午10:28:24
 */
public class RepositoryException extends RuntimeException {

	/** @Field serialVersionUID  */
	private static final long serialVersionUID = 1L;

	/** @Field 错误代码  */
	private String code;

	public RepositoryException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public RepositoryException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public RepositoryException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public RepositoryException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public RepositoryException(String code, String message) {
		this(message);
		this.code = code;
	}

	@Override
	public String toString() {
		return code + ":" + getLocalizedMessage();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
//	public static void main(String[] args) {
//		System.out.println(new BusinessException("sfe", "msg"));
//	}
}
