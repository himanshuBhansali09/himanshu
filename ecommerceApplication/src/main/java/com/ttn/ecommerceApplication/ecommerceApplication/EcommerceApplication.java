package com.ttn.ecommerceApplication.ecommerceApplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttn.ecommerceApplication.ecommerceApplication.configuration.FileStorageProperties;
import com.ttn.ecommerceApplication.ecommerceApplication.daoImpl.UploadDaoImpl;
import com.twilio.Twilio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.CacheControl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@EnableAsync
@EnableScheduling
@RestController
@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
public class EcommerceApplication implements WebMvcConfigurer
{
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public ObjectMapper objectMapper()
	{
		return new ObjectMapper();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins("http://localhost:3000");
			}
		};
	}

	@Bean
	public LocaleResolver 	localeResolver()
	{
		AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();
		acceptHeaderLocaleResolver.setDefaultLocale(Locale.US);
		return acceptHeaderLocaleResolver;
	}


	@Autowired
	UploadDaoImpl uploadDao;

	public static void main(String[] args) {

		SpringApplication.run(EcommerceApplication.class, args);
		System.out.println(System.getProperty("user.dir"));
		String fileBasePath = System.getProperty("user.dir")+"/src/main/resources/productVariation";
		File file = new File(fileBasePath);
		file.mkdir();
		String fileBasePath1 = System.getProperty("user.dir")+"/src/main/resources/users";
		File file1 = new File(fileBasePath1);
		file1.mkdir();
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/users/")
				.setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
	}

		/*Twilio.init("AC6e3f007b213a141efb5bc835721b967a", "78d1d18160a31aef65fe843f10632e1b");

		Message message = Message.creator(new PhoneNumber("+917530902834"),
				new PhoneNumber("+14698442733"),
				"hello coder ").create();

		System.out.println(message.getSid());*/
	}

