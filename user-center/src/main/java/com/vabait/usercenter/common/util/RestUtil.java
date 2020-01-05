package com.vabait.usercenter.common.util;

import com.vabait.usercenter.common.protocol.ResponseProto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestUtil {
    public static void postObjectAsync(String url, Object object) {
        System.out.println("restTemplate ");

        AsyncRestTemplate restTemplate = new AsyncRestTemplate();
        HttpEntity<?> httpEntity = new HttpEntity(object);
        ListenableFuture<ResponseEntity<ResponseProto>> future = restTemplate.postForEntity(url, httpEntity, ResponseProto.class);

        future.addCallback(new ListenableFutureCallback<ResponseEntity<ResponseProto>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("future onFailure ");
            }

            @Override
            public void onSuccess(ResponseEntity<ResponseProto> responseProtoResponseEntity) {
                boolean success = responseProtoResponseEntity.getBody().isSuccess();
                System.out.println("future responseProto " + success);
            }
        });
    }

    public static void postFormAsync(String url, Map params) {
        System.out.println("AsyncRestTemplate");

        AsyncRestTemplate restTemplate = new AsyncRestTemplate();
        HttpEntity httpEntity = new HttpEntity(new LinkedMultiValueMap<String, Object>() {{
            setAll(params);
        }}, null);
        ListenableFuture<ResponseEntity<ResponseProto>> future = restTemplate.postForEntity(url, httpEntity, ResponseProto.class);

        future.addCallback(new ListenableFutureCallback<ResponseEntity<ResponseProto>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("future onFailure ");
            }

            @Override
            public void onSuccess(ResponseEntity<ResponseProto> responseProtoResponseEntity) {
                boolean success = responseProtoResponseEntity.getBody().isSuccess();
                String message = responseProtoResponseEntity.getBody().getMessage();
                System.out.println("future responseProto " + success + " message " + message);
            }
        });
    }

    public static ResponseProto postForm(String url, Map params) {
        return postForm(url, params, ResponseProto.class);
    }

    public static ResponseProto postForm(RestTemplate restTemplate, String url, Map params) {
        return postForm(restTemplate, url, params, ResponseProto.class);
    }

    public static <T> T postForm(String url, Map params, Class<T> responseType) {
        //HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity httpEntity = new HttpEntity(new LinkedMultiValueMap<String, Object>() {{
            setAll(params);
        }}, null);

        ResponseEntity<T> response = new RestTemplate().postForEntity(url, httpEntity, responseType);

        return response.getBody();
    }

    public static <T> T postForm(String url, Map params, Map filePathParams, Class<T> responseType) {
        HttpEntity httpEntity = new HttpEntity(new LinkedMultiValueMap<String, Object>() {{
            setAll(params);
            if (filePathParams != null) {
                filePathParams.forEach((k, v) -> {
                    FileSystemResource file = new FileSystemResource((String) v);
                    add((String) k, file);
                });
            }
        }}, null);

        ResponseEntity<T> response = new RestTemplate().postForEntity(url, httpEntity, responseType);

        return response.getBody();
    }

    public static <T> T postForm(RestTemplate restTemplate, String url, Map params, Class<T> responseType) {
        //HttpHeaders headers = new HttpHeaders();
        //headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity httpEntity = new HttpEntity(new LinkedMultiValueMap<String, Object>() {{
            setAll(params);
        }}, null);

        ResponseEntity<T> response = restTemplate.postForEntity(url, httpEntity, responseType);

        return response.getBody();
    }

    public static ResponseProto postObject(String url, Object object) {
        return postObject(url, object, ResponseProto.class);
    }

    public static ResponseProto postObject(RestTemplate restTemplate, String url, Object object) {
        return postObject(restTemplate, url, object, ResponseProto.class);
    }

    public static <T> T postObject(String url, Object object, Class<T> responseType) {
        //或者HttpEntity httpEntity=new HttpEntity<>(object);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<T> response = restTemplate.postForEntity(url, object, responseType);

        return response.getBody();
    }

    public static <T> T postObject(RestTemplate restTemplate, String url, Object object, Class<T> responseType) {
        //或者HttpEntity httpEntity=new HttpEntity<>(object);
        ResponseEntity<T> response = restTemplate.postForEntity(url, object, responseType);

        return response.getBody();
    }

    public static ResponseProto put(String url, Map params) {
        return put(new RestTemplate(), url, params);
    }

    public static ResponseProto put(RestTemplate restTemplate, String url, Map params) {
        if (params != null) {
            url = StringUtil.getQueryParamUrl(url, params);
        }
        //new RestTemplate().put(url,null);
        ResponseEntity<ResponseProto> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, null, ResponseProto.class);
        return responseEntity.getBody();
    }

    public static <T> T put(RestTemplate restTemplate, String url, Map params, Class<T> responseType) {
        if (params != null) {
            url = StringUtil.getQueryParamUrl(url, params);
        }
        //new RestTemplate().put(url,null);
        ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, null, responseType);
        return responseEntity.getBody();
    }

    public static ResponseProto putObject(RestTemplate restTemplate, String url, Object object) {
        HttpEntity httpEntity = new HttpEntity(object, null);

        ResponseEntity<ResponseProto> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, ResponseProto.class);
        return responseEntity.getBody();
    }

    public static void putAsync(String url, Map params) {
        System.out.println("AsyncRestTemplate");

        AsyncRestTemplate restTemplate = new AsyncRestTemplate();
        if (params != null) {
            url = StringUtil.getQueryParamUrl(url, params);
        }
        ListenableFuture<ResponseEntity<ResponseProto>> future = restTemplate.exchange(url, HttpMethod.PUT, null, ResponseProto.class);

        future.addCallback(new ListenableFutureCallback<ResponseEntity<ResponseProto>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("future onFailure ");
            }

            @Override
            public void onSuccess(ResponseEntity<ResponseProto> responseProtoResponseEntity) {
                boolean success = responseProtoResponseEntity.getBody().isSuccess();
                String message = responseProtoResponseEntity.getBody().getMessage();
                System.out.println("future responseProto " + success + " message " + message);
            }
        });
    }

    public static <T> T get(String url, Map params, Class<T> responseType) {
        if (params != null) {
            url = StringUtil.getQueryParamUrl(url, params);
        }
        return get(url, responseType);
    }

    public static <T> T get(RestTemplate restTemplate, String url, Map params, Class<T> responseType) {
        if (params != null) {
            url = StringUtil.getQueryParamUrl(url, params);
        }
        return get(restTemplate, url, responseType);
    }

    public static <T> T get(String url, List params, Class<T> responseType) {
        if (params != null) {
            url = StringUtil.getQueryPathUrl(url, params);
        }
        return get(url, responseType);
    }

    public static <T> T get(String url, Class<T> responseType) {
        T result = null;
        try {
            ResponseEntity<T> responseEntity = new RestTemplate().getForEntity(url, responseType);
            result = responseEntity.getBody();
        } catch (RestClientException e) {
            System.out.println(e);
        }

        return result;
    }

    public static <T> T getUtf8(String url, Class<T> responseType) {
        T result = null;
        try {
            ResponseEntity<T> responseEntity = getRestTemplate("UTF-8").getForEntity(url, responseType);
            result = responseEntity.getBody();
        } catch (RestClientException e) {
            System.out.println(e);
        }

        return result;
    }

    public static <T> T get(RestTemplate restTemplate, String url, Class<T> responseType) {
        T result = null;
        try {
            ResponseEntity<T> responseEntity = restTemplate.getForEntity(url, responseType);
            result = responseEntity.getBody();
        } catch (RestClientException e) {
            System.out.println(e);
        }

        return result;
    }

    //new ParameterizedTypeReference<T>() {}
    public static <T> T get(RestTemplate restTemplate, String url, Map params, ParameterizedTypeReference<T> responseType) {
        if (params != null) {
            url = StringUtil.getQueryParamUrl(url, params);
        }

        T result = null;
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, responseType);

            result = responseEntity.getBody();
        } catch (RestClientException e) {
            System.out.println(e);
        }

        return result;
    }

    public static ResponseProto delete(String url, String id) {
        /*
        * url=StringUtil.getQueryParamUrl(url,new HashMap(){{
            put("id",id);
        }});
        new RestTemplate().delete(url);
        */
        url = StringUtil.getQueryParamUrl(url, new HashMap() {{
            put("id", id);
        }});
        ResponseEntity<ResponseProto> responseEntity = new RestTemplate().exchange(url, HttpMethod.DELETE, null, ResponseProto.class);
        return responseEntity.getBody();
    }

    public static ResponseProto delete(RestTemplate restTemplate, String url, String id) {
        /*
        * url=StringUtil.getQueryParamUrl(url,new HashMap(){{
            put("id",id);
        }});
        new RestTemplate().delete(url);
        */
        url = StringUtil.getQueryParamUrl(url, new HashMap() {{
            put("id", id);
        }});
        ResponseEntity<ResponseProto> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, ResponseProto.class);
        return responseEntity.getBody();
    }

    public static ResponseProto delete(String url, Map params) {
        if (params != null) {
            url = StringUtil.getQueryParamUrl(url, params);
        }
        ResponseEntity<ResponseProto> responseEntity = new RestTemplate().exchange(url, HttpMethod.DELETE, null, ResponseProto.class);
        return responseEntity.getBody();
    }

    public static ResponseProto delete(RestTemplate restTemplate, String url, Map params) {
        if (params != null) {
            url = StringUtil.getQueryParamUrl(url, params);
        }
        ResponseEntity<ResponseProto> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, null, ResponseProto.class);
        return responseEntity.getBody();
    }

    /*
    https://blog.csdn.net/mryang125/article/details/80963280
    UTF-8
     */
    public static RestTemplate getRestTemplate(String charset) {
        RestTemplate restTemplate = new RestTemplate();
        if (charset != null) {
            List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
            if (list != null) {
                for (HttpMessageConverter<?> httpMessageConverter : list) {
                    if (httpMessageConverter instanceof StringHttpMessageConverter) {
                        ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(Charset.forName(charset));
                    }
                }
            }
        }
        return restTemplate;
    }
}
