package vip.floatationdevice.dancegrade.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import vip.floatationdevice.dancegrade.DataManager;
import vip.floatationdevice.dancegrade.data.CommonMappedResult;
import vip.floatationdevice.dancegrade.data.DanceData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

import static cn.hutool.core.util.ObjectUtil.hasNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class DataWriteController
{
    // Create data: POST /api/data
    @PostMapping(value = "/api/data", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult insertData(HttpServletRequest req, HttpServletResponse resp, @RequestBody Map<String, Object> data)
    {
        resp.setStatus(400);
        if(!hasNull(data.get("studentId"), data.get("name"), data.get("scores"), data.get("actions"), data.get("scoreAvg")))
        {
            if(data.get("studentId") instanceof Integer &&
                    data.get("name") instanceof String &&
                    data.get("scores") instanceof ArrayList &&
                    data.get("actions") instanceof ArrayList &&
                    (data.get("scoreAvg") instanceof Double || data.get("scoreAvg") instanceof Integer)
            )
            {
                if(!StrUtil.isBlank((String) data.get("name")))
                {
                    if(DataManager.insertData(DanceData.fromJson(new JSONObject(data))))
                    {
                        resp.setStatus(201);
                        return new CommonMappedResult(201, "Data created");
                    }
                    else
                    {
                        resp.setStatus(500);
                        return new CommonMappedResult(50001, "Unknown database operation error");
                    }
                }
                else return new CommonMappedResult(40003, "Parameter is blank string");
            }
            else return new CommonMappedResult(40002, "Invalid parameter type");
        }
        else return new CommonMappedResult(40001, "Missing parameter");
    }

    // Delete data: DELETE /api/data/{id}
    @DeleteMapping(value = "/api/data/{id}", produces = APPLICATION_JSON_VALUE)
    public CommonMappedResult deleteData(HttpServletRequest req, HttpServletResponse resp, @PathVariable("id") int id)
    {
        if(DataManager.hasData(id))
        {
            if(DataManager.deleteData(id))
            {
                resp.setStatus(200);
                return new CommonMappedResult(200, "Data deleted");
            }
            else
            {
                resp.setStatus(500);
                return new CommonMappedResult(50001, "Unknown database operation error");
            }
        }
        else
        {
            resp.setStatus(404);
            return new CommonMappedResult(404, "No such data");
        }
    }
}
