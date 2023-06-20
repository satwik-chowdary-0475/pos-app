package com.increff.pos.dto.helper;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.ArrayList;
import java.util.List;

public class HelperDto {
        public static String[] getNullPropertyNames(Object source) {
            final BeanWrapper beanWrapper = new BeanWrapperImpl(source);
            java.beans.PropertyDescriptor[] propertyDescriptors = beanWrapper.getPropertyDescriptors();
            List<String> nullProperties = new ArrayList<>();
            for (java.beans.PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                String propertyName = propertyDescriptor.getName();
                if (beanWrapper.getPropertyValue(propertyName) == null) {
                    nullProperties.add(propertyName);
                }
            }
            return nullProperties.toArray(new String[0]);
        }
}
