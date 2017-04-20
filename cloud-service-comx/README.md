# comx java 版本实现
-   示例
-   代码
-   TODO list


-   示例:
```    json
    {
        "meta": {
        },
        "decors": [
            {
                "type":"Fixed",
                "fixedData":{
                    "test":[
                        {
                            "id":174830091166023736
                    	},
                    	{
                            "id":176
                    	}
                	]
    	        }
            },
            {
                "refJsonPath":"$.test",
                "type":"script",
                "jscript":"test/test"
            },
            {
                "precondition":"return context.getRequest().getUrl().getQuery().get('id').equals('3');",
                "refJsonPath":"$.test",
                "refPrecondition":"return ref.get('id').equals(176);",
                "field": "info",
                "source": {
                    "base": "default",
                    "uri":"http://api.bs.dev.gomeplus.com/v2/social/group?id=174830091166023736&tid={request.url.query.id}"
                }
            }
        ]
    }
```
    meta 信息 如同前一版本
    decors
        fixedData 如上
        Script Decor 使用 groovy-脚本, 脚本目录 comx-conf 下 groovy-scripts
            脚本示例:
                import Context;
                def callback(Object data, Context context, Map ref){
                    println "start to callback!"
                    ref.put("testg", "testgvalue");
                }
        EachDecor 与原相同
        
        refJsonPath/precondition/refPrecondition
            
    
-   代码
    启动在 com.gomeplus.comx.boot.server 当中
    boot 为入口
    context 当前context
    schema 读取 schema 并 datadecor
    source 
    utils 其他


- TODO tests and 变更现在的执行逻辑
