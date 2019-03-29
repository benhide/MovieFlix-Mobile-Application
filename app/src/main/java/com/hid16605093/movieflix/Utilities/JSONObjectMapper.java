package com.hid16605093.movieflix.Utilities;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
CREATED BY BEN HIDE - HID16605093 - MOBILE COMPUTING - SEPTEMBER TO DECEMBER 2018
**DEVELOPED USING THE TUTORIALS AND CODE REFERENCES AS LISTED BELOW**

References:

JACKSON OBJECT MAPPING TO JAVA CLASS
- https://www.baeldung.com/jackson-object-mapper-tutorial -
*/

// JSON object mapper class
public class JSONObjectMapper
{
    // Object mapper - JSON object request
    private static ObjectMapper objectMapper = new ObjectMapper();

    // Get the object mapper
    public static ObjectMapper getObjectMapper() { return objectMapper; }
}