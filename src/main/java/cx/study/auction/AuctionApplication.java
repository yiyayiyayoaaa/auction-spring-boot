package cx.study.auction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cx.study.auction.utils.NullStringToEmptyAdapterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@SpringBootApplication
public class AuctionApplication extends WebMvcConfigurerAdapter{
    private Logger logger = LoggerFactory.getLogger(AuctionApplication.class);
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);
		GsonHttpMessageConverter messageConverter = new GsonHttpMessageConverter();
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory<>())
                .excludeFieldsWithoutExposeAnnotation()
                .create();
		messageConverter.setGson(gson);
		converters.add(messageConverter);
		logger.info("加载Gson");
	}

	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
	}
}
