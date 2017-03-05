package com.erdaoya.springcloud.common.actuator.maven.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author erdaoya
 * @since 1.1
 */
@Slf4j
public class MavenEndpoint implements Endpoint{

    @Override
    public String getId() {
        return "maven";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isSensitive() {
        return false;
    }

    @Override
    public Object invoke() {
        Map<String,Object> mavenEndpointObject =   new HashMap<>();
        List<String> dependencies = new ArrayList<>();
        try {
            mavenEndpointObject.put("scope","runtime,compile,test,provided,system" );
            ClassPathResource resource = new ClassPathResource("maven-resolve.properties");
            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 512);
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                String dependency = line.trim();
                if ("".equals(dependency)) continue;
                if(dependency.startsWith("The following files")) continue;
                dependencies.add(dependency);
            }
        } catch (Exception e){
            log.info("not found maven-resolve.properties");
        }
        mavenEndpointObject.put("dependencies", dependencies);
        return mavenEndpointObject;
    }
}
