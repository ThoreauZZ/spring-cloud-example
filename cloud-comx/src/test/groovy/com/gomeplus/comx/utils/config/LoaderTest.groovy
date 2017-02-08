package com.gomeplus.comx.utils.config

import org.junit.Test

/**
 * Created by xue on 12/8/16.
 */
class LoaderTest extends GroovyTestCase {

    @Test
    public void testLoaderFileException() {
        def msg = shouldFail(ConfigException, {
            String fileName = "D://not_exist_file.json";
            Loader.fromJsonFile(fileName);
        })
        assert "failed to read json file:D://not_exist_file.json" == msg;
    }
}
