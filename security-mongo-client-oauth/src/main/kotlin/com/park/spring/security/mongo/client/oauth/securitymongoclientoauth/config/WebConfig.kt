package com.park.spring.security.mongo.client.oauth.securitymongoclientoauth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.web.context.request.RequestContextListener
import org.springframework.web.servlet.config.annotation.*

@EnableWebMvc
@Configuration
class WebConfig : WebMvcConfigurer {

    @Bean
    fun requestContextListener(): RequestContextListener = requestContextListener()

    @Bean
    fun placeholderConfigurer(): PropertySourcesPlaceholderConfigurer = PropertySourcesPlaceholderConfigurer()

    override fun configureDefaultServletHandling(configurer: DefaultServletHandlerConfigurer?) {
        configurer!!.enable()
    }

    override fun addViewControllers(registry: ViewControllerRegistry?) {
        super.addViewControllers(registry)
        registry!!.addViewController("/")
                .setViewName("forward:/index")
        registry.addViewController("/index")
        registry.addViewController("/secure")
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
        registry!!.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/")
    }
}