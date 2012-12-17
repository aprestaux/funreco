package com.github.aprestaux.funreco.domain;

import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;

@Entity(value = "actions", noClassnameStored = true)
public class DBAction {
	@Id
    private ObjectId id;

    @Embedded
    private DBProfile profile;

    @Embedded
    private DBObject object;
	
	@Property
	private Date date = new Date();


}
