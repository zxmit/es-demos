package com.zxm.es.load.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.elasticsearch.hadoop.mr.EsOutputFormat;

import java.io.IOException;

/**
 * Created by zxm on 8/4/16.
 */
public class LoadMain {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();


        conf.setBoolean("mapred.map.tasks.speculative.execution", false);
        conf.setBoolean("mapred.reduce.tasks.speculative.execution", false);
        conf.set("es.input.json", "yes");
        conf.set("es.nodes", "node1:9200");
        conf.set("es.resource", "xm_email_index_2/resource");
        Job job = Job.getInstance(conf);

        job.setJobName(LoadMain.class.getName());
        job.setJarByClass(LoadMain.class);
        job.setMapperClass(LoadMapper.class);
        job.setOutputFormatClass(EsOutputFormat.class);

//        job.setInputFormatClass(TextInputFormat.class);


        job.setNumReduceTasks(0);

//        job.setOutputKeyClass(NullWritable.class);
//        job.setOutputValueClass(Text.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(BytesWritable.class);



        FileInputFormat.addInputPath(job, new Path(args[0]));
        job.setSpeculativeExecution(false);
        job.waitForCompletion(true);
    }
}
