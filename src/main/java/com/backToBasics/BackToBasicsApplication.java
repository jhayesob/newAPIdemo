package com.backToBasics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

// Main Application Class
/** Entry point of the application that bootstraps the framework, scans for
 * beans. Naming convention for sboot is: [ProjectName] + Application
 */

@Slf4j
@SpringBootApplication
public class BackToBasicsApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BackToBasicsApplication.class);
		Environment env = app.run(args).getEnvironment();
		logApplicationStartup(env);

		log.info("Application running.");
	}

	private static void logApplicationStartup(Environment env) {
		String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
		String serverPort = env.getProperty("server.port", "8080");
		String contextPath = Optional.ofNullable(env.getProperty("server.servlet.context-path"))
				.filter(path -> !path.isBlank())
				.orElse("/");
		String hostAddress = "localhost";
		try {
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("The host name could not be determined, using 'localhost' as fallback");
		}

		log.info("""
            
            ----------------------------------------------------------
            \tApplication '{}' is running! Access URLs:
            \tLocal: \t\t{}://localhost:{}{}
            \tExternal: \t{}://{}:{}{}
            \tProfile(s): \t{}
            ----------------------------------------------------------""",
				env.getProperty("spring.application.name"),
				protocol, serverPort, contextPath,
				protocol, hostAddress, serverPort, contextPath,
				env.getActiveProfiles().length == 0 ? "default" : env.getActiveProfiles()
		);
	}
}
