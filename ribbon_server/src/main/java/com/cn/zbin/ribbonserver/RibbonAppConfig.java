package com.cn.zbin.ribbonserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class RibbonAppConfig extends WebMvcConfigurerAdapter {

	@Value("${qlh.imagesPath}")
	private String mImagesPath;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		if (mImagesPath.equals("") || mImagesPath.equals("${qlh.imagesPath}")) {
			String imagesPath = RibbonAppConfig.class.getClassLoader().getResource("").getPath();
			if (imagesPath.indexOf(".jar") > 0) {
				imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
			} else if (imagesPath.indexOf("classes") > 0) {
				imagesPath = "file:" + imagesPath.substring(0, imagesPath.indexOf("classes"));
			}
			imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/")) + "/images/";
			mImagesPath = imagesPath;
		}
		registry.addResourceHandler("/images/**").addResourceLocations(mImagesPath);
		System.out.println(mImagesPath);
		super.addResourceHandlers(registry);
	}
}
