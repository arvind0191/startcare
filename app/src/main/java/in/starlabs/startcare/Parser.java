package in.starlabs.startcare;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arvind on 28/03/16.
 */
public class Parser {
    Context context;
    public Parser(Context context){
        this.context = context;
    }
    public void parseCompany(String s) throws Exception{
        Database db = new Database(context);
        JSONArray rootArray = new JSONArray(s);
        for (int i = 0;i<rootArray.length();i++) {
            JSONObject rootObject = rootArray.getJSONObject(i);
            String companyname = rootObject.getString("title");
            JSONArray array = rootObject.getJSONArray("models");
            for (int k = 0 ;k<array.length();k++){
                JSONObject jsonObject = array.getJSONObject(k);
                String model = jsonObject.getString("title");
                db.insertdata(companyname,model);
            }
        }
    }
}
