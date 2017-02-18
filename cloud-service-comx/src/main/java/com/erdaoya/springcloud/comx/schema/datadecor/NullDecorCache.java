package com.erdaoya.springcloud.comx.schema.datadecor;

import com.erdaoya.springcloud.comx.context.Context;

/**
 * Created by xue on 12/20/16.
 */
public class NullDecorCache extends DecorCache{
    public NullDecorCache() {
        super("", 0, false);
    }
    public boolean setAfterChildren(Context context, Object data){
        return false;
    }
    public boolean setBeforeChildren(Context context, Object data){
        return false;
    }
    public boolean getWithChildren(Context context, Object data){
        return false;
    }
    public boolean getWithoutChildren(Context context, Object data){
        return false;
    }
}
