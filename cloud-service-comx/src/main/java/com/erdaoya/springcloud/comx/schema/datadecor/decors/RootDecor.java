package com.erdaoya.springcloud.comx.schema.datadecor.decors;

import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.utils.config.Config;

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
