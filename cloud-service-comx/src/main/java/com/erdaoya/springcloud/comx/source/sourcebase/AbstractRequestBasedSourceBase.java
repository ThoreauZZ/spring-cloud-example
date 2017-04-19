package com.erdaoya.springcloud.comx.source.sourcebase;

import com.erdaoya.springcloud.comx.context.Context;
import com.erdaoya.springcloud.comx.source.Source;
import com.erdaoya.springcloud.comx.source.SourceException;
import com.erdaoya.springcloud.comx.utils.config.ConfigException;
import com.erdaoya.springcloud.comx.utils.rest.ReservedParameterManager;
import com.erdaoya.springcloud.comx.schema.TinyTemplate;
import com.erdaoya.springcloud.comx.utils.config.Config;
import com.erdaoya.springcloud.comx.utils.rest.RequestMessage;
import com.erdaoya.springcloud.comx.utils.rest.Url;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xue on 12/23/16.
 */
abstract public class AbstractRequestBasedSourceBase extends AbstractSourceBase{
    public AbstractRequestBasedSourceBase(Config conf) {super(conf);}

    /**
     *  针对 requestbase 处理 url 时 将url query 部分 encode
     *  这个涉及正则，需要再次验证
     * @param context
     * @param sourceOptions
     * @param reservedVariables
     * @return Object data
     * @throws ConfigException
     * @throws IOException
     * @throws SourceException
     */
    public Object executeLoading(Context context, Config sourceOptions, HashMap reservedVariables) throws ConfigException, IOException, SourceException{
        String method                   = sourceOptions.str(Source.FIELD_METHOD, RequestMessage.METHOD_GET);
        Integer timeout                 = sourceOptions.intvalue(Source.FIELD_TIMEOUT, RequestMessage.DEFAULT_TIMEOUT);
        RequestMessage currentRequest   = context.getRequest();
        ReservedParameterManager reservedParameterManager = ReservedParameterManager.fromRequest(currentRequest);

        //TODO 验证这一步骤是不是已经被render
        String targetUrl = this.getResourceUrl(context, sourceOptions.rstr(Source.FIELD_URI), reservedVariables, reservedParameterManager.getReservedQueryParams());
        context.getLogger().debug("source load remote data, method:"+ method + " targetUrl:" + targetUrl);
        Map requestData = null;
        if (method.equals(RequestMessage.METHOD_POST) || method.equals(RequestMessage.METHOD_PUT)) {
            if (!method.equals(currentRequest.getMethod())) {
                throw new UnmatchedRequestMethodException("Source loading, method unmatched: Current method is:"+ currentRequest.getMethod());
            }
            requestData = currentRequest.getData();
        }

        try {
            // Url 生成错误 处理
            Url url = new Url(targetUrl);
            RequestMessage request = new RequestMessage(url, method, requestData, reservedParameterManager.getFilteredReservedHeaders(context), timeout);
            return this.doRequest(request, context);
        } catch (SourceException ex) {
            throw ex;
        } catch (Exception ex) {
            // TODO handle exceptions
            // should not be Exception
            ex.printStackTrace();
            throw new SourceException(ex);
        }
    }



    private String getResourceUrl(Context context, String uri, HashMap reservedVariables, HashMap reservedQueryParams) throws ConfigException{
        TinyTemplate tpl = new TinyTemplate(uri);
        String renderedUri = tpl.render(reservedVariables, context, true);

        if (isFullUri(renderedUri)) {
            return renderedUri;
        }

        // TODO url form url;
        renderedUri = this.getUrlPrefix(context) + renderedUri;
        return renderedUri;
    }

    private boolean isFullUri(String renderedUri) {
        return renderedUri.startsWith("http://") || renderedUri.startsWith("https://");
    }
    abstract Object doRequest(RequestMessage request,Context context) throws SourceException;
    abstract String getUrlPrefix(Context context) throws ConfigException;




}
