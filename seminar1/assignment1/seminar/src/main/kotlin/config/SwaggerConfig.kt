package config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig: WebMvcConfigurationSupport() {

    @Override
    protected fun addResourceHandler(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/")
        registry.addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/")
    }

    @Bean
    fun swaggerApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(true)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.wafflestudio.seminar"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(this.metaInfo())
    }

    private fun metaInfo(): ApiInfo {
        return ApiInfoBuilder()
            .title("API 문서")
            .description("테스트 API 문서입니다.")
            .version("1.0")
            .termsOfServiceUrl("http://localhost:8080")
            .license("Apache")
            .licenseUrl("http://localhost:8080")
            .contact(
                Contact(
                    "bomoon",
                    "http://localhost:8080",
                    "lotsofchuck@gmail.com"
                )
            )
            .build()
    }
}