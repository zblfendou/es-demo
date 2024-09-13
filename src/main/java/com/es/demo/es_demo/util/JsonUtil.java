package com.es.demo.es_demo.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper MAPPER = JsonMapper.builder()
            .enable(MapperFeature.ALLOW_COERCION_OF_SCALARS)
            .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
            .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
            .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();
    
    static {
        // 进行Date日期格式化
        DateFormat dateFormat = new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        MAPPER.setDateFormat(dateFormat);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // 序列换成json时,将所有的long变成string ，处理Long类型转Json后精度丢失问题
        javaTimeModule.addSerializer(Long.class, ToStringSerializer.instance);
        javaTimeModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

        // 进行LocalDateTime时间格式化
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));

        MAPPER.registerModule(javaTimeModule);
        MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        MAPPER.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
    }

    // 该方法暴露出全局MAPPER，有安全风险，如无必要，不要使用
    public static ObjectMapper getMapper() { return MAPPER; }

    public static <T> String toJson(T t) {
        try {
            return MAPPER.writeValueAsString(t);
        } catch (Exception e) {
            logger.error("JSON解析失败", e);
            return null;
        }
    }

    public static JsonNode asNode(String res) {
        try {
            return MAPPER.readTree(res);
        } catch (JsonProcessingException e) {
            logger.error("JSON解析失败 内容 - {}", res, e);
            return null;
        }
    }

    public static <T> T asObject(String res, Class<T> clazz) {
        if (StrUtil.isBlank(res)) return null;
        try {
            return MAPPER.readValue(res, clazz);
        } catch (Exception e) {
            logger.error("JSON解析失败 内容 - {}", res, e);
            return null;
        }
    }

    public static <T> T asObject(String res, Class<T> clazz, String datePattern) {
        // 此处基于全局Mapper复制一个局部变量，保护全局mapper的安全性
        // 由于使用了bean拷贝，所以性能会有下降，谨慎使用，针对日期格式问题，最好在业务代码上进行全局转换或者局部转换
        ObjectMapper mapper = cloneMapper();
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        mapper.setDateFormat(dateFormat);
        if (StrUtil.isBlank(res)) return null;
        try {
            return mapper.readValue(res, clazz);
        } catch (Exception e) {
            logger.error("JSON解析失败 内容 - {}", res, e);
            return null;
        }
    }

    public static <E> List<E> asList(String res, Class<?> clazz) {
        return (List<E>) asCollection(res, List.class, clazz);
    }

    public static <E> List<E> asList(String res, Class<?> clazz, String datePattern) {
        return (List<E>) asCollection(res, List.class, clazz, datePattern);
    }

    public static <K, V> Map<K, V> asMap(String res) {
        if (StrUtil.isBlank(res)) return Collections.emptyMap();
        try {
            return MAPPER.readValue(res, MAPPER.getTypeFactory().constructType(HashMap.class));
        } catch (Exception e) {
            logger.error("JSON解析失败 内容 - {}", res, e);
            return Collections.emptyMap();
        }
    }

    public static <E> Collection<E> asCollection(String res, Class<?> collectionClazz, Class<?> clazz) {
        if (StrUtil.isBlank(res)) return Collections.emptyList();
        try {
            return MAPPER.readValue(res, MAPPER.getTypeFactory().constructParametricType(collectionClazz, clazz));
        } catch (Exception e) {
            logger.error("JSON解析失败 内容 - {}", res, e);
            return Collections.emptyList();
        }
    }

    public static <E> Collection<E> asCollection(String res, Class<?> collectionClazz, Class<?> clazz, String datePattern) {
        if (StrUtil.isBlank(res)) return Collections.emptyList();
        ObjectMapper mapper = cloneMapper();
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        mapper.setDateFormat(dateFormat);
        try {
            return mapper.readValue(res, mapper.getTypeFactory().constructParametricType(collectionClazz, clazz));
        } catch (Exception e) {
            logger.error("JSON解析失败 内容 - {}", res, e);
            return Collections.emptyList();
        }
    }

    private static ObjectMapper cloneMapper() {
        ObjectMapper mapper = new ObjectMapper();
        BeanUtil.copyProperties(MAPPER, mapper);
        return mapper;
    }
}
