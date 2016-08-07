package com.zxm.es.load.mr;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Random;

/**
 * Created by zxm on 8/4/16.
 */
public class LoadMapper extends Mapper<LongWritable, Text, NullWritable, BytesWritable>{

    private static Log log = LogFactory.getLog(LoadMapper.class);

    @Override
    protected void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {

        byte[] source = value.toString().replaceAll("[\\x00-\\x09\\x11\\x12\\x14-\\x1F\\x7F]", "").getBytes();
        BytesWritable jsonDoc = new BytesWritable(source);
        context.write(NullWritable.get(), jsonDoc);
    }

    private static synchronized long getLongId() {
        StringBuffer sb = new StringBuffer(System.nanoTime()+"");
        Random random = new Random();
        String a = (random.nextInt(9)+1) + sb.toString();
        return Long.parseLong(a);
    }
}
