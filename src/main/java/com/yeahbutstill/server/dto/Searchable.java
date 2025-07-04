package com.yeahbutstill.server.dto;

import com.yeahbutstill.server.enumeration.SearchType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.RECORD_COMPONENT)
@Retention(RetentionPolicy.RUNTIME)
public @interface Searchable {
  SearchType type() default SearchType.LIKE;
}
