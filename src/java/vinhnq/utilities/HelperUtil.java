/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinhnq.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Vinh
 */
public class HelperUtil implements Serializable {

    private static final String MEDIA_FILE = "file/mediaFile.txt";
    private static final String MEDIA_FILE1 = "file/mediaFile.txt";
    private static final String JSP_FILE = "file/jspFile.txt";
    private static final String SERVLET_FILE = "file/servletFile.txt";
    
    public static String encodeSHA256(String origin) throws NoSuchAlgorithmException
    {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodeHash = digest.digest(origin.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder(2*encodeHash.length);
        for(int i = 0  ; i < encodeHash.length; i ++)
        {
            String hex = Integer.toHexString(0xff & encodeHash[i]);
            if(hex.length() == 1)
            {
                hexString.append("0");
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    public static Connection makeConnection() throws NamingException, SQLException
    {
        Connection cn = null;
        Context context = new InitialContext();
        Context tomcatContext = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) tomcatContext.lookup("SE141125");
        cn = ds.getConnection();
        return cn;
    }
    
    public static List<String> listOfMediaFile(String path) throws IOException{
        List<String> list = null;
        File file = new File(path+"/"+MEDIA_FILE);
        FileReader fr = null;
        BufferedReader br = null;
        
        try {

            if (file.exists()) {
                if (file.isFile()) {
                    fr = new FileReader(file);
                    
                    if(fr != null)
                    {
                        br = new BufferedReader(fr);
                        
                        if(br != null)
                        {
                            while(br.ready())
                            {
                                String line = br.readLine();
                                if(list == null)
                                    list = new ArrayList<String>();
                                list.add(line);
                            }
                        }
                    }
                }
            }
            else
                System.out.println("File not Exits");
            
        } finally {
            if (br != null) {
                br.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
        
        return list;
    }
    
    public static Map<String,String> listOfServletFile(String path) throws IOException{
        Map<String,String> map = null;
        File file = new File(path+"/"+SERVLET_FILE);
        FileReader fr = null;
        BufferedReader br = null;
        
        try {

            if (file.exists()) {
                if (file.isFile()) {
                    fr = new FileReader(file);
                    
                    if(fr != null)
                    {
                        br = new BufferedReader(fr);
                        
                        if(br != null)
                        {
                            while(br.ready())
                            {
                                String line = br.readLine();
                                String[] split = line.split("=");
                                if(split.length >= 2)
                                {
                                    if(map == null)
                                        map = new HashMap<>();
                                    map.put(split[0], split[1]);
                                }
                               
                            }
                        }
                    }
                }
            }
            
        } finally {
            if (br != null) {
                br.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
        
        return map;
    }
    
    public static Map<String,String> listOfJSPFile(String path) throws IOException{
        Map<String,String> map = null;
        File file = new File(path+"/"+JSP_FILE);
        FileReader fr = null;
        BufferedReader br = null;
        
        try {

            if (file.exists()) {
                if (file.isFile()) {
                    fr = new FileReader(file);
                    
                    if(fr != null)
                    {
                        br = new BufferedReader(fr);
                        
                        if(br != null)
                        {
                            while(br.ready())
                            {
                                String line = br.readLine();
                                String[] split = line.split("=");
                                if(split.length >= 2)
                                {
                                    if(map == null)
                                        map = new HashMap<>();
                                    map.put(split[0], split[1]);
                                }
                               
                            }
                        }
                    }
                }
            }
            
        } finally {
            if (br != null) {
                br.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
        
        return map;
    }

}
