package vip.floatationdevice.dancegrade.data;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

public class DanceData
{
    private Integer id, studentId;
    private String name, scores, actions;
    private Double scoreAvg;

    public Integer getId(){return id;}

    public Integer getStudentId(){return studentId;}

    public String getName(){return name;}

    public String getScores(){return scores;}

    public String getActions(){return actions;}

    public Double getScoreAvg(){return scoreAvg;}

    public void setId(Integer id){this.id = id;}

    public void setStudentId(Integer studentId){this.studentId = studentId;}

    public void setName(String name){this.name = name;}

    public void setScores(String scores){this.scores = scores;}

    public void setActions(String actions){this.actions = actions;}

    public void setScoreAvg(Double scoreAvg){this.scoreAvg = scoreAvg;}

    public static DanceData fromJson(JSONObject j)
    {
        DanceData d = new DanceData();
        d.setId(j.getInt("id"));
        d.setStudentId(j.getInt("studentId"));
        d.setName(j.getStr("name"));
        d.setScores(j.getJSONArray("scores").toString());
        d.setActions(j.getJSONArray("actions").toString());
        d.setScoreAvg(j.getDouble("scoreAvg"));
        return d;
    }

    public JSONObject toJson()
    {
        return new JSONObject()
                .set("id", id)
                .set("studentId", studentId)
                .set("name", name)
                .set("scores", new JSONArray(scores))
                .set("actions", new JSONArray(actions))
                .set("scoreAvg", scoreAvg);
    }
}
