package com.yzj.jep.app.util;

import org.springframework.core.convert.converter.Converter;
/**
 * 自定义去掉前后空格
 * S：页面传递参数的类型
 * T：转换后的类型
 */
public class TrimConverter implements Converter<String, String>{
 
	@Override
	public String convert(String source) {
		// TODO Auto-generated method stub   " abc "
		try {
			if(null != source){
				source = source.trim();
				if(!"".equals(source)){
					return source;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
 
}
