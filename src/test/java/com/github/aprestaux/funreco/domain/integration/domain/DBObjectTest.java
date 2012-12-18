package com.github.aprestaux.funreco.domain.integration.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

import static org.fest.assertions.Assertions.assertThat;

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
		Map<String, Set<String>> properties = new HashMap<String, Set<String>>();
		Set<String> s = new HashSet<String>();
		s.add("video");
		s.add("show");
		properties.put("type", s);

		DBObject object = new DBObject();
		object.setObjectId("testId");
		object.setDate(new Date());
		object.setObjectProperties(properties);

		datastore.save(object);

		assertThat(datastore.find(DBObject.class).countAll()).isEqualTo(1);
		DBObject dbObject = datastore.find(DBObject.class).field("objectId")
				.equal("testId").get();
		assertThat(dbObject.getObjectProperties() == properties);

	}
}
