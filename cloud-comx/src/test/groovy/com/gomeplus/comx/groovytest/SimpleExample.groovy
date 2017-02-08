package com.gomeplus.comx.groovytest

/**
 * Created by xue on 12/8/16.
 */
import groovy.util.GroovyTestSuite
import org.junit.Test

/**
 * Created by xue on 12/8/16.
 */
class SimpleExample extends GroovyTestCase {

    @Test
    public void testLength() {
        def result = [2, 3, 8, 9, 0, 1, 5, 7].size();
        def result2 = new ArrayList<Integer>();
        assert result == 8
    }

}