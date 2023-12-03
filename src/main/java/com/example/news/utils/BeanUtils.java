package com.example.news.utils;

import java.lang.reflect.Field;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BeanUtils {

	@SneakyThrows
	public void copyNonNullProperties(Object source, Object destination) {
		Class<?> clazz = source.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object value = field.get(source);

			if (value != null) {
				field.set(destination, value);
			}
		}
	}
}
