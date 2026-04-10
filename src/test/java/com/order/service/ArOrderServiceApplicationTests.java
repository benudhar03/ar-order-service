package com.order.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"spring.data.mongodb.auto-index-creation=false",
		"spring.mongodb.embedded.version=5.0.0"
})
class ArOrderServiceApplicationTests { }
