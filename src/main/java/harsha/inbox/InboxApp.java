package harsha.inbox;

import java.nio.file.Path;

import javax.annotation.PostConstruct;

import harsha.inbox.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class InboxApp {

	@Autowired
	private EmailService emailService;

	public static void main(String[] args) {
		SpringApplication.run(InboxApp.class, args);
	}

	@RequestMapping("/user")
	public String user(@AuthenticationPrincipal OAuth2User principal) {
		System.out.println(principal);
		return principal.getAttribute("name");
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

//	@PostConstruct
//	public void initializeData() {
//
//		for (int i = 0; i < 10; i++) {
//
//			emailService.sendEmail("sri-harsha-joshi", "sri-harsha-joshi", "Test " + i, "Body " + i);
//		}
//
//
//	}

}
