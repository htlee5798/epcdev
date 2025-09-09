package com.lottemart.common.util;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public abstract class ObjectUtil {
	public abstract boolean isEmpty();

	public static boolean isEmpty(Object bean) {
		if (bean == null)
			return true;
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(bean.getClass());

		for (int i = 0; i < propertyDescriptors.length; i++) {
			if (bean == null)
				return true;

			if ((bean instanceof String) && (((String) bean).trim().length() == 0)) {
				return true;
			}

			if (bean instanceof Map) {
				return ((Map<?, ?>) bean).isEmpty();
			}

			if (bean instanceof List) {
				return ((List<?>) bean).isEmpty();
			}

			if (bean instanceof Object[]) {
				return (((Object[]) bean).length == 0);
			}

			return false;
		}
		return true;
	}
	
	public static boolean isNull(Object object) {
		if (object == null) {
			return true;
		}
		return false;
	}

}