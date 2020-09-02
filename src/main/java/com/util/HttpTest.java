package com.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class HttpTest {
    /*public static String putHttp(){
        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
        httpHeaders.setContentType(mediaType);
        String data = "{\n" +
                "    \"SERVICE_HEADER\":{\n" +
                "        \"ORG_CODE\":\"CORE\",\n" +
                "        \"SERVICE_ID\":\"affirmBind\",\n" +
                "        \"SERVICE_NAME\":\"CMBC_SERVICE\",\n" +
                "        \"SERVICE_SN\":\"ZL20190917200317348959\",\n" +
                "        \"TOKEN\":\"BHAmlYmrwDJLUNPQVp75nk2aHaVf403Ff8gtlBMo0akAi3+QOJ9dFMcAiFQHTBcWOyNsQTI9DaFFybMgJv3+YCepJ9MNRVvkvno4z7m1KCIuoQP/g1s1fQ4qQMHtOWkiWAnJO7xfYbiu2re368QAwb1nTYYUDeVgyFABS7w2OB6nw3GS+u2B3cU6gMzAUhLGMZMv54bo0kaQ/w3cJbVDE4RUmtn2vFE6rDUgJ6ybjnS38Otpr81M4nluIo1hmmmmTl/CvgkuxjR/HigR0jFjVLAj3gw+ZYqv5l/fYPIfOMGW+pYNFLotxFwoj5qFQuu/9zAtBDEM5Gsm5erxDGj8XQ==\"\n" +
                "    },\n" +
                "    \"SERVICE_BODY\":{\n" +
                "        \"ORG_CODE\":\"666666\",\n" +
                "        \"ORG_ACCOUNT\":\"ZLHJ_CORE\",\n" +
                "        \"ORG_PASSWORD\":\"123\"\n" +
                "    }\n" +
                "}";
        HttpEntity<String> httpEntity = new HttpEntity<>(data,httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.postForEntity("http://192.168.2.33:8060/zlhj_interface/service", httpEntity, String.class);
        String str = result.toString();
        System.out.println(str);
        return str;
    }*/
    @PostConstruct
    public void getMain() {
        //String result = putHttp();
        System.out.println("第一次：我是@PostConstruct方法");
    }
}
