package com.mygdx.pirategame.tests;

import com.mygdx.pirategame.tests.impl.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.mockito.internal.util.collections.Sets;

import java.util.Set;

public class TestRunner {

	private final static Set<Class<?>> TESTS_TO_RUN = Sets.newSet(
			AssetTests.class,
			ScoreTest.class,
			ShipTest.class,
			CoinTest.class,
			CannonTest.class,
			CollegeWallTest.class,
			GenerationTests.class,
			DamageTest.class,
			ListenerTest.class,
			ScreenTest.class,
			ButtonTests.class
	);

	public void runTests() {
		boolean hasPassed = true;
		int testsRan = 0;

		for (Class<?> clazz : TESTS_TO_RUN) {
			System.out.println("Running " + clazz.getSimpleName());
			Result result = JUnitCore.runClasses(clazz);

			for (Failure failure : result.getFailures()) {
				System.out.println("Failure for " + clazz + " at " + failure.toString());
				System.out.println(failure.getTrace());

				hasPassed = false;
			}

			testsRan += result.getRunCount();
			System.out.println("Complete! Ran " + result.getRunCount() + " tests. In total " + (result.getRunCount() - result.getFailureCount()) + " tests passed. Failure for " + result.getFailureCount() + " tests.");
		}

		System.out.println("All tests complete. In total, we ran " + testsRan + " tests!");
		Assert.assertTrue("Not all tests have passed!", hasPassed);
	}
}