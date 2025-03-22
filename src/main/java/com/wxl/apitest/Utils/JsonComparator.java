package com.wxl.apitest.Utils;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;


public class JsonComparator {
    /**
     * 比较两个 JSON 是否完全一致（严格模式）
     *
     * @param expected 预期 JSON
     * @param actual   实际 JSON
     * @return 差异报告（若一致返回 null）
     */
    public static String compareStrict(String expected, String actual) throws JSONException {
        JSONCompareResult result = JSONCompare.compareJSON(expected, actual, JSONCompareMode.STRICT);
        return result.passed() ? null : result.getMessage();
    }

    /**
     * 比较两个 JSON 的结构和值类型（忽略值内容）
     *
     * @param expected 预期 JSON
     * @param actual   实际 JSON
     * @return 差异报告（若一致返回 null）
     */
    public static String compareStructure(String expected, String actual) throws JSONException {
        JSONCompareResult result = JSONCompare.compareJSON(expected, actual, JSONCompareMode.LENIENT);
        return result.passed() ? null : result.getMessage();
    }
}
