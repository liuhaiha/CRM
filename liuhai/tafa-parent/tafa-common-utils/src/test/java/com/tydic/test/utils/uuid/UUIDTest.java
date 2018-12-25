package com.tydic.test.utils.uuid;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.UUID;


/**
 * ClassName:UUIDTest
 */
public class UUIDTest {

	@Test
	public void testGetUUID() {
		String uuid = UUID.randomUUID().toString();
		String uuid2 = UUID.fromString("123-123-12-23-73").toString();
		assertNotNull(uuid);
		assertNotSame(uuid, uuid2);
	}

}

