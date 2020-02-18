package tech.mufeng.admin.boilerplate.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Resource
    private Environment environment;

    @Bean
    public Docket aclDocket() {
        Profiles profiles = Profiles.of("prod");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(!environment.acceptsProfiles(profiles))
                .groupName("登录与权限")
                .select()
                .paths(PathSelectors.ant("/api/common/acl/**"))
                .build();
    }

    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("管理端模板接口文档")
                .description("接口文档")
                .termsOfServiceUrl("")
                .contact(new Contact("developer", "#", "huangtianyu_hty@163.com"))
                .version("1.0")
                .build();
    }
}
