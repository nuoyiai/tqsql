package tpsql.json;

import java.util.Map;

public interface IJsonFinder {

    /**
     * 找查json子对像
     * @param selector 选择器
     * @return
     */
    JsonObject findObject(JsonObject jsonObject, String selector);

    /**
     * 找查json子对像
     * @param selector 选择器
     * @return
     */
    JsonObject findObject(JsonArray jsonArray, String selector);

    /**
     * 找查array中符合条件的对像
     * @param selector 选择器
     * @return
     */
    JsonArray findArray(JsonArray jsonArray, String selector);

    /**
     * 找查array中符合条件的对像
     * @param jsonArray
     * @param propMap
     * @return
     */
    JsonArray findArray(JsonArray jsonArray, Map<String, String> propMap);

    /**
     * 找查array中符合条件的对像
     * @param jsonArray
     * @param propMap
     * @return
     */
    JsonArray findArray(JsonArray jsonArray, Map<String, String> propMap, FinderType type);

}
