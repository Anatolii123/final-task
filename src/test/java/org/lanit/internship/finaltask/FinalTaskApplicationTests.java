package org.lanit.internship.finaltask;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.lanit.internship.finaltask.controller.PersonCarController;
import org.lanit.internship.finaltask.controller.PersonCarController2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class FinalTaskApplicationTests {

	@Autowired
	private PersonCarController personCarController;

	@Autowired
	private PersonCarController2 personCarController2;

	@Test
	public void testPersonCarController() throws Exception {
		assertThat(personCarController).isNotNull();
	}

	@Test
	public void testPersonCarController2() throws Exception {
		assertThat(personCarController2).isNotNull();
	}

}
