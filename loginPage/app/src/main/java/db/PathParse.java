package db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PathParse
{
    Map<String, String> mapPathVals = new HashMap<>();

    public PathParse(String path)
    {
        mapPathVals = parsePath(path);
    }

    private Map<String, String> parsePath(String path)
    {
        ArrayList<String> dataParsed = new ArrayList<>(Arrays.asList(path.split("[/]")));
        Map<String, String> mapParsed = new HashMap<>();

        // making the data parsed even length
        if (dataParsed.size() % 2 == 1)
            dataParsed.add("");

        for (int i = 0; i < dataParsed.size(); i += 2)
        {
            mapParsed.put(dataParsed.get(i), dataParsed.get(i + 1));
        }

        return (mapParsed);
    }
    
    public String getDataFromParsed(String key)
    {
        return this.mapPathVals.get(key);
    }
}
