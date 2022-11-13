package com.spring.jpa.h2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.spring.jpa.h2.model.Tutorial;
import com.spring.jpa.h2.repository.TutorialRepository;

@DataJpaTest  //this annotation must be applied for repository layer testing
// this annotation simple disables all the pre configurations in application.properties and always use in memory h2 database for testing
@AutoConfigureTestDatabase(replace = Replace.NONE) //if want to use H2 custom database name written in application.properties
public class TutorialRepositoryTests {

	@Autowired
	private TutorialRepository tutorialRepository;

	@Autowired
	private TestEntityManager entityManager; //spring testing manager -> making their own repo layer

	@Test
	@Rollback(false) //will only work with TestEntityManager
	public void testCreateTutorial() {
//		Tutorial savedTutotial = tutorialRepository
//				.save(Tutorial.builder().id(99).title("java").description("corejava").published(true).build());
//		System.out.println("*******" + savedTutotial.toString());
//		assertThat(savedTutotial.getId()).isGreaterThan(0);

		Tutorial t = Tutorial.builder().id(22).title("abc").description("cde").published(true).build();
		entityManager.persist(t); //now we can cancel the rollback
		assertThat(t.getId()).isGreaterThan(0);

	}

	@Test
	@Rollback(false) // will work only for TestEntityManager only
	public void testTutorialValue() {
		Tutorial tut1 = new Tutorial(123, "java", "Desc", true);
		entityManager.persist(tut1);

		Tutorial foundTutorial = tutorialRepository.findById(tut1.getId()).get();

		assertThat(foundTutorial).isEqualTo(tut1);
	}

	@Test
	@Rollback(false)
	public void demo() {

		Tutorial foundTutorial = tutorialRepository.findById(123L).get();

		System.out.println("************" + foundTutorial.toString());

		assertThat(foundTutorial.getTitle()).isEqualTo("java");
	}

}
