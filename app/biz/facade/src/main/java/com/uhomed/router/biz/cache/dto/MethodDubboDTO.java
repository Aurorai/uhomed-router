package com.uhomed.router.biz.cache.dto;

/**
 * The type MethodDTO info dubbo.
 *
 * @author
 * @version $$Id : , v 0.1 Exp $$
 */
public class MethodDubboDTO implements MethodDTO {

	private String	classPath;

	private String	methodName;

	private String	registerType;

	private String	registerAddress;

	/**
	 * Instantiates a new MethodDTO dubbo.
	 *
	 * @param classPath
	 *            the class path
	 * @param methodName
	 *            the method name
	 */
	public MethodDubboDTO(String classPath, String methodName, String registerType, String registerAddress) {
		this.classPath = classPath;
		this.methodName = methodName;
		this.registerType = registerType;
		this.registerAddress = registerAddress;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getRegisterAddress() {
		return registerAddress;
	}

	public void setRegisterAddress(String registerAddress) {
		this.registerAddress = registerAddress;
	}

	/**
	 * Gets class path.
	 *
	 * @return the class path
	 */
	public String getClassPath() {
		return classPath;
	}

	/**
	 * Sets class path.
	 *
	 * @param classPath
	 *            the class path
	 */
	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	/**
	 * Gets method name.
	 *
	 * @return the method name
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Sets method name.
	 *
	 * @param methodName
	 *            the method name
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
}
