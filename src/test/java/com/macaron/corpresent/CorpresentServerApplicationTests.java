package com.macaron.corpresent;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 避免 websocket 的相关报错
class CorpresentServerApplicationTests {

	@Test
	void contextLoads() {
	}

}
