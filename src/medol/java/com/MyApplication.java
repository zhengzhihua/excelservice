package com;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;



public class MyApplication extends ResourceConfig {  
    public MyApplication() {  
    	//服务类所在的包路径  
        packages("com");  
        //注册JSON转换器  
        register(JacksonJsonProvider.class);  
       }  
   }  
