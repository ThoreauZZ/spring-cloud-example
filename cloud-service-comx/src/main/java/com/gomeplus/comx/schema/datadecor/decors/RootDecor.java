package com.gomeplus.comx.schema.datadecor.decors;

import com.alibaba.fastjson.JSON;
import com.gomeplus.comx.context.Context;
import com.gomeplus.comx.utils.config.Config;

/**
 * Created by xue on 12/20/16.
 */
public class RootDecor extends CompositionDecor{
    public RootDecor(Config conf){
        super(conf);
    }
    public void doDecorate(Object data, Context context){}
    public String getType(){return AbstractDecor.TYPE_ROOT;}
}
