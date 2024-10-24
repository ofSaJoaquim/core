package br.com.branetlogistica.core.log;


import java.lang.reflect.Field;

import org.springframework.beans.BeanUtils;

public class LogUtil {

	public static String getValueField(final Object object) throws Exception {

		if(object == null)
			return null;
		
		else if (BeanUtils.isSimpleValueType(object.getClass()))
			return object.toString();			
		
		else if(LogUtil.containsId(object, "id"))  			
			return getId(object).toString();			
		
		return null;

	}
	
	public static boolean isEqual(final Object oldObject, final Object newObject) throws Exception {
		if(oldObject==null && newObject==null)
			return true;
		
		if(oldObject==null && newObject!=null)
			return false;
		
		if(oldObject !=null && newObject==null)
			return false;
		
		if(!oldObject.equals(newObject))
			return false;
		
		return true;
		
		
	}
	
	public static String getFieldName(final Object newObject,final Object oldObject , final String name) throws Exception {

		Object test = null;
		
		if(newObject == null && oldObject ==null)
			return null;
		
		if(newObject !=null)
			test = newObject;
		else
			test = oldObject;
		
		 if (BeanUtils.isSimpleValueType(test.getClass()))
			return name;		
		else  		
			return name+".id";
		
		
	}
	
	
	public static boolean containsId(Object object,String field) throws Exception{
		for(Field f : object.getClass().getDeclaredFields()) {
			if(f.getName().equals(field))
				return true;
		}
		return false;
	}
	
	public static Long getId(Object object) throws Exception{
		final Field field = object.getClass().getDeclaredField("id");
		field.setAccessible(true);		 
		Object id = field.get(object);
		if(id.getClass().equals(Long.class))
			return (Long)id;
		return null;
		
	}
	
}