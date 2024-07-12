package br.qziul.video_aula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 	API RESTFul desenvolvido durante video-aula.
 * 	Projeto gerado com Spring Initializr.
 * 	Startes e dependências usadas:
 * 	- JPA
 * 	- Validation
 * 	- Web
 * 	- Hateoas
 * 	- PostgreSQL
 * @author QziuL (Github)
 */

@SpringBootApplication
public class VideoAulaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoAulaApplication.class, args);
	}

}
