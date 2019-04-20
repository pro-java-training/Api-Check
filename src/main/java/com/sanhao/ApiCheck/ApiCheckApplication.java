package com.sanhao.ApiCheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


@SpringBootApplication
@RestController
public class ApiCheckApplication {

    private static String requestUrl = "http://apiv2.l.com/ApiCheck.php";

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
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(requestUrl, String.class);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "getMethodList")
    public String getMethodList() {
        return requestUrl;
    }

    @RequestMapping(value = "getMethod")
    public String getMethod() {
        return requestUrl;
    }

    @RequestMapping(value = "invoke")
    public String invoke() {
        return requestUrl;
    }
}
