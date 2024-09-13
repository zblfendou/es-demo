//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.es.demo.es_demo.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(
        ignoreUnknown = true
)
public class HttpResult {
    private static final String DEFAULT_FAILURE_CODE = "400";
    private static final String DEFAULT_OK_CODE = "200";
    private boolean status;
    private String code;
    private String msg;
    private Object res;

    public HttpResult() {
    }

    public HttpResult(boolean status, String code, String msg, Object res) {
        this.status = status;
        this.code = code;
        this.msg = msg;
        this.res = res;
    }

    public static HttpResult asFailure(String code, String msg, Object res) {
        return new HttpResult(false, code, msg, res);
    }

    public static HttpResult asFailure(String msg, Object res) {
        return asFailure("400", msg, res);
    }

    public static HttpResult asFailure(String msg) {
        return asFailure(msg, (Object) null);
    }

    public static HttpResult asFailure() {
        return asFailure((String) null);
    }

    public static HttpResult asOk(String code, String msg, Object res) {
        return new HttpResult(true, code, msg, res);
    }

    public static HttpResult asOk(String msg, Object res) {
        return asOk("200", msg, res);
    }

    public static HttpResult asOk(Object res) {
        return asOk((String) null, res);
    }

    public static HttpResult asOk() {
        return asOk((Object) null);
    }

    public boolean getStatus() {
        return this.status;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public Object getRes() {
        return this.res;
    }

    public String asString() {
        return String.valueOf(this.getRes());
    }

    private static String getResString(String json) {
        JsonNode node = JsonUtil.asNode(json);
        if (null == node) {
            return null;
        } else {
            JsonNode res = node.get("res");
            return null == res ? null : res.toString();
        }
    }

    public static <T> T asObject(String json, Class<T> clazz) {
        String res = getResString(json);
        return null == res ? null : JsonUtil.asObject(res, clazz);
    }

    public static <T> T asObject(String json, Class<T> clazz, String datePattern) {
        String res = getResString(json);
        return null == res ? null : JsonUtil.asObject(res, clazz, datePattern);
    }

    public static <E> List<E> asList(String json, Class<?> clazz) {
        String res = getResString(json);
        return null == res ? null : JsonUtil.asList(res, clazz);
    }

    public static <E> List<E> asList(String json, Class<?> clazz, String datePattern) {
        String res = getResString(json);
        return null == res ? null : JsonUtil.asList(res, clazz, datePattern);
    }

    public static <K, V> Map<K, V> asMap(String json) {
        String res = getResString(json);
        return null == res ? null : JsonUtil.asMap(res);
    }

    public static <E> Collection<E> asCollection(String json, Class<?> collectionClazz, Class<?> clazz) {
        String res = getResString(json);
        return null == res ? null : JsonUtil.asCollection(res, collectionClazz, clazz);
    }

    public static <E> Collection<E> asCollection(String json, Class<?> collectionClazz, Class<?> clazz, String datePattern) {
        String res = getResString(json);
        return null == res ? null : JsonUtil.asCollection(res, collectionClazz, clazz, datePattern);
    }

    public static HttpResult fromJson(String json) {
        return (HttpResult) JsonUtil.asObject(json, HttpResult.class);
    }

    public String toString() {
        return "HttpResult{status=" + this.status + ", code='" + this.code + '\'' + ", msg='" + this.msg + '\'' + ", res=" + this.res + '}';
    }
}
