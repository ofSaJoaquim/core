package br.com.branetlogistica.core.context;

public class Context {
	private static ThreadLocal<ContextData> contextData = new InheritableThreadLocal<>();	
	
	public static ContextData getContextData() {
		return contextData.get();
	}

	public static void setContextData(ContextData contextData) {
		Context.contextData.set(contextData);
	}
	
	public static void clear() {
		contextData.set(null);	
		contextData.remove();
	}
	
	
}
