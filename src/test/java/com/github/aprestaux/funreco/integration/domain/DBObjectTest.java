package com.github.aprestaux.funreco.integration.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.github.aprestaux.funreco.IntegrationSpringConfig;
import com.github.aprestaux.funreco.domain.DBObject;
import com.google.code.morphia.Datastore;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ActiveProfiles(profiles = "integration")
public class DBObjectTest {
	@Configuration
	@Import(IntegrationSpringConfig.class)
	static class ContextConfiguration {

	}

	@Inject
	private Datastore datastore;

	@Before
	public void clean() {
		datastore.delete(datastore.find(DBObject.class));
	}

	@Test
	public void save() {
		Map<String, List<String>> properties = new HashMap<String, List<String>>();
		properties.put("type", Arrays.asList("video", "show"));

		DBObject object = new DBObject();
		object.setObjectId("testId");
		object.setDate(new Date());
		object.setObjectProperties(properties);

		datastore.save(object);

		assertThat(datastore.find(DBObject.class).countAll()).isEqualTo(1);
		DBObject dbObject = datastore.find(DBObject.class).field("objectId").equal("testId").get();
		assertThat(dbObject.getObjectProperties() == properties);

	}
}
