import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Program {

  public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
    System.out.println("math club bb");
    JsonParsing();

  }
  
  public static void JsonParsing() throws FileNotFoundException, IOException, ParseException {
    JSONParser parser = new JSONParser();
    JSONObject contact = (JSONObject) parser.parse(new FileReader("social-network.json"));
    JSONArray name = (JSONArray)contact.get("socialNetwork");
    System.out.println(name);
    
    JSONObject person = new JSONObject();
    
    person.put("name", "Sam Govier");
    person.put("age", 25);
    
    JSONArray numbers = new JSONArray();
    numbers.add("250987");
    numbers.add("2340587");
    
    PrintWriter pw = new PrintWriter("ExamplePesron.json");
    pw.write(person.toJSONString());
    pw.flush();
    pw.close();
  }

}
