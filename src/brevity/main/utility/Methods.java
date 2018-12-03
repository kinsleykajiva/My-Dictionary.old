package brevity.main.utility;

import brevity.main.pojos.WordJsonReader;
import brevity.main.pojos.WordMeanings;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Maps;
import org.eclipse.collections.impl.factory.Sets;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static brevity.main.utility.SqliteDB.createNewTable;
import static brevity.main.utility.Strings.PATH_DEFAULT_DICTIONARY;
import static brevity.main.utility.Strings.PATH_SECONADRY_DICTIONARY;

public final class Methods {
   static String json = "{\"brand\" : \"Toyota\", \"doors\" : \"5\" , \"doors\" : \"5\" , \"xxx\" : \"dterte\"}";
    public static void main (String[] a) {
       // createNewTable();
        //stem.out.print( getDictionaryData( json ) );
      //  SqliteDB db = new SqliteDB();
       // db.selectAll();
       //loadDictionary(PATH_DEFAULT_DICTIONARY);
        loadHugeJsonFile("C:\\Users\\kajiva kinsley\\netbeansProjects\\My-Dictionary\\src\\resources\\raw\\dictionary\\SELECT___FROM__entries_.json");
        //loadDictionary(PATH_SECONADRY_DICTIONARY);
        //loadDictionary(PATH_DEFAULT_DICTIONARY);

    }
    static  ImmutableMap ma1 ,ma2; static int w=0;
    private static void loadHugeJsonFile(String path){
       final java.lang.reflect.Type REVIEW_TYPE = new TypeToken<List< WordJsonReader >>() {
        }.getType();
        Gson gson = new Gson();
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(path));
        } catch ( FileNotFoundException e ) {
            e.printStackTrace();
        }
        List<WordJsonReader> data = gson.fromJson(reader, REVIEW_TYPE); // contains the whole reviews list
        System.out.println( data.get( 20 ).getWord() +"----" + data.size());
        SqliteDB db = new SqliteDB();
        data.stream()
                .peek( ele-> System.out.println("At "+ele.getWord() ))
                .forEach( ele->
            db.insert(ele.getWord(),"Word type: "+ele.getWordtype()+" . " + ele.getDefinition())
         );

    }
    public static void loadDictionary (String dictionaryFile) {
        try ( Reader reader1 = new InputStreamReader( Methods.class.getResourceAsStream( dictionaryFile ) , "UTF-8" ) ) {
            JsonReader jreader = new JsonReader(new InputStreamReader( Methods.class.getResourceAsStream( dictionaryFile ) , "UTF-8" ) );
           // jreader.beginArray();
            jreader.beginObject();
            SqliteDB db = new SqliteDB();
            while (jreader.hasNext()) {
                //System.out.println("--\n");
                db.insert(jreader.nextName(), jreader.nextString());
                System.out.println(jreader.nextName() + " : " + jreader.nextString());
            }
            jreader.endObject();
            //jreader.endArray();
            jreader.close();
           /* BufferedReader reader = new BufferedReader(new InputStreamReader(Methods.class.getResourceAsStream( dictionaryFile ) , "UTF-8" ));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            reader.close();
            char[] arr = new char[8 * 1024];
            StringBuilder buffer = new StringBuilder();
            int numCharsRead;
            while ((numCharsRead = reader1.read(arr, 0, arr.length)) != -1) {
                buffer.append(arr, 0, numCharsRead);
            }
            String targetString = buffer.toString();*/
            //System.out.println( targetString  );
            System.out.println("--------\n\n");
            /*if(w==0) {
                ma1 = getDictionaryData( targetString  );
                for (Object key : ma1.keysView()) {
                    System.out.println( key );
                }
                System.out.println("--------\n\n");

              // ma1.forEach( (v)->System.out.println(v)  );
            }*/
            /*if(w==1) {
                ma2 = getDictionaryData( out.toString() );

            }
            if(w==2) {
                System.out.println( ma1.equals(ma2) );

            }*/
            System.out.println(  );
            System.out.println("\n\n");
w++;
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public static ImmutableMap < String, Object > getDictionaryData (String json) {


        JsonObject object = ( JsonObject ) parser.parse( json );

        MutableSet < MutableMap.Entry < String, JsonElement > > set = object.entrySet().stream().collect( Collectors2.toSet() );
        Iterator < MutableMap.Entry < String, JsonElement > > iterator = set.iterator();
        MutableMap < String, Object > map = Maps.mutable.empty();
        while ( iterator.hasNext() ) {

            MutableMap.Entry < String, JsonElement > entry = iterator.next();
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            if ( null != value ) {
                if ( ! value.isJsonPrimitive() ) {
                    if ( value.isJsonObject() ) {
                        map.put( key , getDictionaryData( value.toString() ) );
                    } else if ( value.isJsonArray() && value.toString().contains( ":" ) ) {

                        MutableList < ImmutableMap < String, Object > > list = Lists.mutable.empty();
                        JsonArray array = value.getAsJsonArray();
                        if ( null != array ) {
                            for ( JsonElement element : array ) {
                                list.add( getDictionaryData( element.toString() ) );
                            }
                            map.put( key , list );
                        }
                    } else if ( value.isJsonArray() && ! value.toString().contains( ":" ) ) {
                        map.put( key , value.getAsJsonArray() );
                    }
                } else {
                    map.put( key , value.getAsString() );
                }
            }
        }

        /**/


        return map.toImmutable();
    }

    static JsonParser parser = new JsonParser();

    public static HashMap < String, Object > createHashMapFromJsonString (String json) {

        JsonObject object = ( JsonObject ) parser.parse( json );
        Set < Map.Entry < String, JsonElement > > set = object.entrySet();
        Iterator < Map.Entry < String, JsonElement > > iterator = set.iterator();
        HashMap < String, Object > map = new HashMap <>();

        while ( iterator.hasNext() ) {

            Map.Entry < String, JsonElement > entry = iterator.next();
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            if ( null != value ) {
                if ( ! value.isJsonPrimitive() ) {
                    if ( value.isJsonObject() ) {
                        map.put( key , createHashMapFromJsonString( value.toString() ) );
                    } else if ( value.isJsonArray() && value.toString().contains( ":" ) ) {

                        List < HashMap < String, Object > > list = new ArrayList <>();
                        JsonArray array = value.getAsJsonArray();
                        if ( null != array ) {
                            for ( JsonElement element : array ) {
                                list.add( createHashMapFromJsonString( element.toString() ) );
                            }
                            map.put( key , list );
                        }
                    } else if ( value.isJsonArray() && ! value.toString().contains( ":" ) ) {
                        map.put( key , value.getAsJsonArray() );
                    }
                } else {
                    map.put( key , value.getAsString() );
                }
            }
        }
        return map;
    }

}
