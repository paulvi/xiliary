package com.codeaffine.workflow.persistence.internal;

import java.lang.reflect.Type;

import com.codeaffine.workflow.persistence.ClassFinder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ClassTypeAdapter
  implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>>
{

  private final ClassFinder classFinder;

  public ClassTypeAdapter( ClassFinder classFinder ) {
    this.classFinder = classFinder;
  }

  @Override
  public JsonElement serialize( Class<?> src, Type typeOfSrc, JsonSerializationContext context ) {
    JsonObject result = new JsonObject();
    result.add( "type", new JsonPrimitive( src.getName() ) );
    return result;
  }

  @Override
  public Class<?> deserialize( JsonElement json, Type typeOfT, JsonDeserializationContext context )
    throws JsonParseException
  {
    JsonObject attributes = json.getAsJsonObject().getAsJsonObject();
    String type = attributes.get( "type" ).getAsString();
    return classFinder.find( type );
  }
}