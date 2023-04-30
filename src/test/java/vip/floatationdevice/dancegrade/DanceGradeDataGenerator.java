package vip.floatationdevice.dancegrade;

import cn.hutool.core.util.RandomUtil;
import vip.floatationdevice.dancegrade.data.DanceData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DanceGradeDataGenerator
{
    private static final Random r = new Random();
    private static final ArrayList<Student> students = new ArrayList<>(1024);
    private static final char[] nameChars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static int dataCount, studentCount;

    public static class Student
    {
        public int studentId;
        public String name;
    }

    public static void initialize()
    {
        studentCount = (dataCount = 10000 + r.nextInt(90001)) / 15;
        for(int i = 0; i != studentCount; i++)
        {
            Student student = new Student();
            StringBuilder name = new StringBuilder(10);
            for(int j = 0; j != 10; j++)
                name.append(nameChars[r.nextInt(nameChars.length)]);
            student.name = name.toString();
            student.studentId = 1000000000 + r.nextInt(1147483648);
            students.add(student);
            System.out.println("Added student " + student.name + ", ID=" + student.studentId);
        }
    }

    public static DanceData generateDanceData()
    {
        DanceData data = new DanceData();
        Student student = students.get(r.nextInt(students.size()));
        double[] scores = new double[5];
        double scoreAvg;
        int[] actions = new int[8 + r.nextInt(12)];

        for(int i = 0; i != actions.length; i++)
            actions[i] = r.nextInt(5);
        a:
        for(int i = 0; i != 5; i++)
        {
            for(int a : actions)
            {
                if(a == i)
                {
                    scores[i] = RandomUtil.randomDouble(40, 100);
                    continue a;
                }
                scores[i] = 0;
            }
        }
        float scoreSum = 0;
        for(int i = 0; i != 5; i++)
            scoreSum += scores[i];
        scoreAvg = scoreSum / 5;

        data.setStudentId(student.studentId);
        data.setName(student.name);
        data.setScores(Arrays.toString(scores));
        data.setScoreAvg(scoreAvg);
        data.setActions(Arrays.toString(actions));
        return data;
    }

    public static void main(String[] args) throws Exception
    {
        DataManager.initialize();
        initialize();
        for(int i = 0; i != dataCount; i++)
        {
            DanceData d = generateDanceData();
            DataManager.session.insert("vip.floatationdevice.dancegrade.sqlmapper.DanceDataMapper.insertData", d);
            //System.out.println(generateDanceData().toJson());
        }
        DataManager.session.commit();
        System.out.println("OK: generated " + studentCount + " students, " + dataCount + " data");
    }
}
