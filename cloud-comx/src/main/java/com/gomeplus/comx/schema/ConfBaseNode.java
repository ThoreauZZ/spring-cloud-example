package com.gomeplus.comx.schema;

import com.gomeplus.comx.utils.config.Config;

/**
 * Created by xue on 12/19/16.
 */
public class ConfBaseNode {
    protected Config conf;
    protected Config parentNode;

    public ConfBaseNode(Config conf) {
        this.conf = conf;
    }
    public Config getConf() {
        return conf;
    }

}
