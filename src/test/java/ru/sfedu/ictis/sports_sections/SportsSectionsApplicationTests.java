package ru.sfedu.ictis.sports_sections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sfedu.ictis.sports_sections.controller.SectionController;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SportsSectionsApplicationTests {

	@Autowired
	private SectionController sectionController;

	@Test
	void testDependencyInjection() {
		assertNotNull(sectionController);
		assertNotNull(sectionController.getSectionService()); // доступ к полю напрямую
	}
}
