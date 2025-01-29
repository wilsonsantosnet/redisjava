package com.example.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.microsoft.applicationinsights.attach.ApplicationInsights;
import com.microsoft.applicationinsights.connectionstring.ConnectionString;


@SpringBootApplication
@EnableAspectJAutoProxy
public class DemoApplication {

	public static void main(String[] args)  throws Exception {


 		ApplicationInsights.attach();
		ConnectionString.configure(System.getenv("CONNECTIONSTRING_APPINS"));
		SpringApplication.run(DemoApplication.class, args);
		
	}

}
