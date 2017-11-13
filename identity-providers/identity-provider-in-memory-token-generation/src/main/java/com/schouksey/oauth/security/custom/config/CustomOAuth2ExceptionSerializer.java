package com.schouksey.oauth.security.custom.config;/*
 * Application    : micro-services
 * Package Name   : com.schouksey.oauth.custom.config
 * Class Name     : CustomOAuth2ExceptionSerializer
 * Author         : SUMIT CHOUKSEY <sumitchouksey2315@gmail.com>
 * Created On     : 11/10/2017
 * Description    : An Implementation of StdSerializer to serialize custom error messages
 */

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomOAuth2ExceptionSerializer extends StdSerializer<CustomOAuth2Exception> {

    /**
     * Initialize CustomOAuth2Exception instance as StdInitializer
     */
    public CustomOAuth2ExceptionSerializer() {
        super(CustomOAuth2Exception.class);
    }

    /**
     * Prepare Custom JSON Exception Object for OAuth2Exception
     * @param e
     *          - CustomOAuth2Exception class or class which has extended OAuth2Exception class
     * @param jsonGenerator
     *          - jsonGenerator to create JSON structure
     * @param serializerProvider
     *          - to serialize the JSON structure
     * @throws IOException
     */
    @Override
    public void serialize(CustomOAuth2Exception e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        Map<String,String> statusDetails = new HashMap<>();
        Map<String,String>  exceptionDetails = new HashMap<>();
        Map<String,String>  productDetails = new HashMap<>();
        productDetails.put("name","Alphabet");
        productDetails.put("version","v1.0");
        if(e.getAdditionalInformation()!=null)
        {
            for (Map.Entry<String, String> entry : e.getAdditionalInformation().entrySet())
            {
                if(entry.getKey().equalsIgnoreCase("code") || entry.getKey().equalsIgnoreCase("value"))
                    statusDetails.put(entry.getKey(),entry.getValue());
            }
        }
        exceptionDetails.put("error",e.getOAuth2ErrorCode());
        exceptionDetails.put("description",e.getMessage());
        jsonGenerator.writeObjectField("productDetails",productDetails);
        jsonGenerator.writeObjectField("statusDetails",statusDetails);
        jsonGenerator.writeObjectField("errorLog",exceptionDetails);
        jsonGenerator.writeBooleanField("isError",true);
        jsonGenerator.writeEndObject();
    }
}
