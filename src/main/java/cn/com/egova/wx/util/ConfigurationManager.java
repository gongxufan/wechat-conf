/*
 * Copyright (c) BJHIT 2013 All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * <p>读取配置文件内容<p>
 * <p>
 * create  2013-4-19<br>
 *
 * @author li_li <br>
 * @version $Revision$ $Date$
 * @since 1.0
 */
package cn.com.egova.wx.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {
    private static Logger logger = Logger.getLogger(ConfigurationManager.class
            .getName());
    private static ConfigurationManager instance = null;
    private Properties properties;

    /**
     * 初始化ConfigurationManager类
     */
    private ConfigurationManager() {
        this("/sysinfo.properties");
    }

    /**
     * 初始化ConfigurationManager类
     *
     * @param filePath 要读取的配置文件的路径+名称
     * @throws IOException
     */
    private ConfigurationManager(String filePath) {
        try {
            properties = new Properties();
            properties.load(ConfigurationManager.class.getResourceAsStream(filePath));
        } catch (Exception ex) {
            logger.error("系统配置文件初始化异常...");
        }

    }

    /**
     * 根据key得到key所对应的值
     *
     * @param key 取得其值的键
     * @return key的值
     */
    public String getValue(String key) {
        if (properties.containsKey(key)) {
            String value = properties.getProperty(key);// 得到某一属性的值
            return value;
        } else
            return "";
    }

    public static ConfigurationManager getInstance() {
        return instance == null ? instance = new ConfigurationManager() : instance;
    }

}
