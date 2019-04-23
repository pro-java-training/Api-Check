package com.sanhao.ApiCheck;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Hashtable;
import java.util.Map;


@SpringBootApplication
@RestController
public class ApiCheckApplication {

    private static String requestUrl = "http://apiv2.l.com/ApiCheck.php";
    private static Gson gson = new Gson();
    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        SpringApplication.run(ApiCheckApplication.class, args);
    }

    @RequestMapping(value = "/index")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("/index");
        modelAndView.addObject("user", "jiangyu");
        return modelAndView;
    }

    @RequestMapping(value = "getChannelList")
    public String getChannelList(@RequestParam String apiUrl) {
        try {
            MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
            requestParams.add("action", "getChannelList");
            requestParams.add("apiUrl", apiUrl);

            return restTemplate.postForObject(requestUrl, requestParams, String.class);
        } catch (Exception e) {
            return buidErrorData(e);
        }
    }

    @RequestMapping(value = "getControllerList")
    public String getMethodList(@RequestParam String apiUrl,
                                @RequestParam String channel) {
        try {
            MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
            requestParams.add("action", "getControllerList");
            requestParams.add("apiUrl", apiUrl);
            requestParams.add("channel", channel);

            return restTemplate.postForObject(requestUrl, requestParams, String.class);
        } catch (Exception e) {
            return buidErrorData(e);
        }
    }

    @RequestMapping(value = "getMethod")
    public String getMethod(@RequestParam String apiUrl,
                            @RequestParam String channel,
                            @RequestParam String controller,
                            @RequestParam String method) {
        try {
            MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
            requestParams.add("action", "getMethod");
            requestParams.add("apiUrl", apiUrl);
            requestParams.add("channel", channel);
            requestParams.add("controller", controller);
            requestParams.add("method", method);

            return restTemplate.postForObject(requestUrl, requestParams, String.class);
        } catch (Exception e) {
            return buidErrorData(e);
        }
    }

    @RequestMapping(value = "invoke")
    public String invoke() {
        return requestUrl;
    }

    private String buidErrorData(Exception e) {
        Map<String, Object> data = new Hashtable<>();
        data.put("code", 999);
        data.put("msg", e.getMessage());
        return gson.toJson(data);
    }
}
