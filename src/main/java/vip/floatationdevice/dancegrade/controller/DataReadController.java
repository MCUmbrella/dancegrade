package vip.floatationdevice.dancegrade.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vip.floatationdevice.dancegrade.DataManager;
import vip.floatationdevice.dancegrade.data.CommonMappedResult;
import vip.floatationdevice.dancegrade.data.DanceData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class DataReadController
{
    // Count data: GET /api/dataCount
    @GetMapping(value = "/api/dataCount", produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult getDataCount()
    {
        return new CommonMappedResult(200, "OK", DataManager.getDataCount());
    }

    // List data: GET /api/data?name={name}&page={page}
    @GetMapping(value = "/api/data", produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult listData(HttpServletRequest req, HttpServletResponse resp, @RequestParam("page") int page, @RequestParam(value = "name", required = false) String name, @RequestParam(value = "studentId", required = false) Integer studentId)
    {
        if(name != null || studentId != null)
        {
            if(studentId == null && StrUtil.isBlank(name))
                return new CommonMappedResult(40003, "Parameter is blank string");
            HashMap<String, Object> data = new HashMap<>();
            int count = DataManager.findCount(name, studentId);
            ArrayList<JSONObject> list = new ArrayList<>();
            for(DanceData d : DataManager.findData(name, studentId, page))
                list.add(d.toJson());
            data.put("count", count);
            data.put("list", list);
            return new CommonMappedResult(200, "OK", data);
        }
        else
        {
            ArrayList<JSONObject> data = new ArrayList<>();
            for(DanceData d : DataManager.getPagedData(page))
                data.add(d.toJson());
            return new CommonMappedResult(200, "OK", data);
        }
    }

    // Get single data: GET /api/data/{id}
    @GetMapping(value = "/api/data/{id}")
    public CommonMappedResult getData(HttpServletRequest req, HttpServletResponse resp, @PathVariable("id") int id)
    {
        if(!DataManager.hasData(id))
            return new CommonMappedResult(200, "OK", DataManager.getData(id).toJson());
        else return new CommonMappedResult(404, "No such data");
    }
}
