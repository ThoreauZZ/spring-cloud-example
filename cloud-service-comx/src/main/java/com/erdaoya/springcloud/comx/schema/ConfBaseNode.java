package com.erdaoya.springcloud.comx.schema;

import com.erdaoya.springcloud.comx.utils.config.Config;

/**
 * Created by xue on 12/19/16.
 */
public class ConfBaseNode {
    protected Config conf;

    public ConfBaseNode(Config conf) {
        this.conf = conf;
    }
    public String getComxId() {
        return conf.str("_comxid", "");
    }
    public Config getConf() {
        return conf;
    }

}
