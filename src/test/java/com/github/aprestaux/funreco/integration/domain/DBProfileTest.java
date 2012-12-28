package com.github.aprestaux.funreco.integration.domain;

import java.util.ArrayList;
import java.util.List;

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
import com.github.aprestaux.funreco.domain.DBProfile;
import com.google.code.morphia.Datastore;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@ActiveProfiles(profiles = "integration")
public class DBProfileTest {
	@Configuration
	@Import(IntegrationSpringConfig.class)
	static class ContextConfiguration {

	}

	@Inject
	private Datastore datastore;

	@Before
	public void clean() {
		datastore.delete(datastore.find(DBProfile.class));
	}

	@Test
	public void save() {

		List<String> friendsIdList = new ArrayList<String>();

		friendsIdList.add("friendId1");
		friendsIdList.add("friendId2");

		DBProfile profile = new DBProfile("testFBId");

		profile.putAttributes("mail", "test@gmail.com");
		profile.putAttributes("name", "nameProfile");
		profile.setFriendsIds(friendsIdList);

		datastore.save(profile);

		assertThat(datastore.find(DBProfile.class).countAll()).isEqualTo(1);
		DBProfile dbProfile = datastore.find(DBProfile.class).get();
		assertThat(dbProfile.getExternalId().equals("testFBId"));

	}
}
