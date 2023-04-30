package vip.floatationdevice.dancegrade;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vip.floatationdevice.dancegrade.data.DanceData;
import vip.floatationdevice.dancegrade.sqlmapper.DanceDataMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class DataManager
{
    private static final String DB_FILENAME = "dancegrade.db";
    private static final Logger l = LoggerFactory.getLogger(DataManager.class);
    private static final SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Main.class.getResourceAsStream("/mybatis.xml"));
    private static boolean ready = false;
    static SqlSession session = null;

// =============================================================================
//  DataManager functions
// =============================================================================

    public static void initialize()
    {
        l.info("DataManager is initializing");
        if(ready) throw new IllegalStateException("DataManager already initialized");
        try
        {
            l.info("Checking database");
            factory.getConfiguration().addMapper(DanceDataMapper.class);
            if(!new File(DB_FILENAME).exists())
            {
                l.warn("Database not found: dancegrade.db");
                createDatabase();
            }
            session = factory.openSession();
            int count = session.selectOne("vip.floatationdevice.dancegrade.sqlmapper.DanceDataMapper.getDataCount");
            l.info("" + count + " data found");
            l.info("Database OK");
        }
        catch(Exception e)
        {
            l.error("DATABASE CHECK UNSUCCESSFUL: " + e);
            l.error("Reset the database by running the program with '--fixdb' argument");
            e.printStackTrace();
            Main.shutdown(-1);
        }
        l.info("DataManager is ready");
        ready = true;
    }

    public static boolean isReady(){return ready;}

    public static void checkReady(){if(!ready) throw new IllegalStateException("DataManager not initialized");}

    public static void resetDatabase() throws Exception
    {
        l.warn("PERFORMING DATABASE RESET");
        File db = new File(DB_FILENAME);
        if(ready)
        {
            ready = false;
            session.close();
            db.renameTo(new File("dancegrade.db.OLD"));
            l.info("Existing database renamed to 'dancegrade.db.OLD'");
            createDatabase();
            session = factory.openSession();
            ready = true;
        }
        else
        {
            if(db.exists() || !db.isFile())
            {
                db.renameTo(new File("dancegrade.db.OLD"));
                l.info("Existing database renamed to 'dancegrade.db.OLD'");
            }
            createDatabase();
        }
        l.warn("DATABASE RESET SUCCESSFUL");
    }

    private static void createDatabase() throws Exception
    {
        l.info("Creating default database");
        File db = new File(DB_FILENAME);
        InputStream is = Main.class.getResourceAsStream("/dancegrade.db");
        assert is != null;
        FileOutputStream fos = new FileOutputStream(db);
        byte[] buffer = new byte[16384];
        int readCount;
        while((readCount = is.read(buffer)) != -1)
            fos.write(buffer, 0, readCount);
        fos.flush();
        fos.close();
        l.info("Database created");
    }

// =============================================================================
//  Read functions
// =============================================================================

    public static int getDataCount()
    {
        return session.selectOne("vip.floatationdevice.dancegrade.sqlmapper.DanceDataMapper.getDataCount");
    }

    public static List<DanceData> getAllData()
    {
        return session.selectList("vip.floatationdevice.dancegrade.sqlmapper.DanceDataMapper.getAllData");
    }

    public static List<DanceData> getPagedData(int page)
    {
        return session.selectList("vip.floatationdevice.dancegrade.sqlmapper.DanceDataMapper.getPagedData", page * 10);
    }

    public static boolean hasData(int id)
    {
        int i = session.selectOne("vip.floatationdevice.dancegrade.sqlmapper.DanceDataMapper.hasData", id);
        return i == 1;
    }

    public static DanceData getData(int id)
    {
        return session.selectOne("vip.floatationdevice.dancegrade.sqlmapper.DanceDataMapper.getData", id);
    }

    public static List<DanceData> findData(String name, Integer studentId, int page)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("studentId", studentId);
        params.put("offset", page * 10);
        return session.selectList("vip.floatationdevice.dancegrade.sqlmapper.DanceDataMapper.findData", params);
    }

    public static int findCount(String name, Integer studentId)
    {
        HashMap<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("studentId", studentId);
        return session.selectOne("vip.floatationdevice.dancegrade.sqlmapper.DanceDataMapper.findCount", params);
    }

// =============================================================================
//  Write functions
// =============================================================================

    public static boolean insertData(DanceData d)
    {
        l.info("Creating data: " + d.getName());
        int affectedCount = session.insert("vip.floatationdevice.dancegrade.sqlmapper.DanceDataMapper.insertData", d);
        session.commit();
        return affectedCount == 1;
    }

    public static boolean deleteData(int id)
    {
        l.info("Deleting data #" + id);
        int affectedCount = session.delete("vip.floatationdevice.dancegrade.sqlmapper.DanceDataMapper.deleteData", id);
        session.commit();
        return affectedCount == 1;
    }
}
