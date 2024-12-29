package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	DVDTests.class,
	DVDCollectionTests.class,
	DVDUITests.class
})

public class AllTests {
	
}