package com.mygdx.pirategame.tests;

import com.mygdx.pirategame.tests.impl.AssetTests;
import com.mygdx.pirategame.tests.impl.ScoreTest;
import com.mygdx.pirategame.tests.impl.TileTests;
import com.mygdx.pirategame.tests.lib.GdxTestRunner;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.mockito.internal.util.collections.Sets;

import java.util.Set;

@RunWith(GdxTestRunner.class)
public class TestRunner {

	private final static Set<Class<?>> TESTS_TO_RUN = Sets.newSet(
			AssetTests.class,
			TileTests.class,
			ScoreTest.class
	);

	public static void main(String[] args) {
		for (Class<?> clazz : TESTS_TO_RUN) {
			System.out.println("Running " + clazz.getSimpleName());
			Result result = JUnitCore.runClasses(clazz);

			for (Failure failure : result.getFailures()) {
				System.out.println("Failure for " + clazz + " at " + failure.toString());
				System.out.println(failure.getTrace());
			}

			System.out.println("Complete! Ran " + result.getRunCount() + " tests. In total " + (result.getRunCount() - result.getFailureCount()) + " tests passed. Failure for " + result.getFailureCount() + " tests.");
		}

		System.out.println("All tests complete");
	}
}