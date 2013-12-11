import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

public class NucleotideFrequencySecond {

  public static class Map extends MapReduceBase implements 
    Mapper<LongWritable, Text, Text, IntWritable> {
      private static final IntWritable ONE = new IntWritable(1);
      private static final String ADENINE  = "A";
      private static final String THYMINE  = "T";
      private static final String CYTOSINE = "C";
      private static final String GUANINE  = "G";

      public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, 
          Reporter reporter) throws IOException {
        String line = value.toString();
        for (int i = 0; i < line.length(); i++) {
          String currentChar = String.valueOf(line.charAt(i));
          switch(currentChar) {
            case ADENINE:
              output.collect(new Text(ADENINE), ONE);
              break;
            case THYMINE:
              output.collect(new Text(THYMINE), ONE);
              break;
            case GUANINE:
              output.collect(new Text(GUANINE), ONE);
              break;
            case CYTOSINE:
              output.collect(new Text(CYTOSINE), ONE);
              break;
          }
        }
      }
    }

  public static class Reduce extends MapReduceBase implements 
    Reducer<Text, IntWritable, Text, IntWritable> {
      public void reduce(Text key, Iterator<IntWritable> values, 
          OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
        int sum = 0;
        while (values.hasNext())
          sum += values.next().get();
        output.collect(key, new IntWritable(sum));
      }
    }

  public static void main(String[] args) throws Exception {
    JobConf conf = new JobConf(NucleotideFrequencySecond.class);
    conf.setJobName("nucleotide_frequency");

    conf.setOutputKeyClass(Text.class);
    conf.setOutputValueClass(IntWritable.class);

    conf.setMapperClass(Map.class);
    conf.setCombinerClass(Reduce.class);
    conf.setReducerClass(Reduce.class);

    conf.setInputFormat(TextInputFormat.class);
    conf.setOutputFormat(TextOutputFormat.class);

    FileInputFormat.setInputPaths(conf, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf, new Path(args[1]));

    JobClient.runJob(conf);
  }
}
