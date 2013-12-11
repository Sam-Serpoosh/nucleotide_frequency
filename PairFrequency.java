import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class PairFrequency {

  static class PairFrequencyMapper extends 
    Mapper <LongWritable, Text, Text, IntWritable> {
      private static final String AT_PAIR = "AT";
      private static final String CG_PAIR = "CG";

      public void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
        int atPairCount = 0;
        int cgPairCount = 0;

        String line = value.toString();   
        atPairCount = numberOfOccurances(line, AT_PAIR);
        cgPairCount = numberOfOccurances(line, CG_PAIR);

        context.write(new Text(AT_PAIR), new IntWritable(atPairCount)); 
        context.write(new Text(CG_PAIR), new IntWritable(cgPairCount)); 
      }

      private int numberOfOccurances(String str, String subString) {
        int lastIndex = 0;
        int count = 0;

        while(lastIndex != -1){
          lastIndex = str.indexOf(subString,lastIndex);
          if( lastIndex != -1){
            count ++;
            lastIndex += subString.length();
          }
        }	
        return count;
      }
  }

  static class PairFrequencyReducer extends 
    Reducer <Text, IntWritable, Text, IntWritable> {

      public void reduce(Text key, Iterable <IntWritable> values, Context context)
        throws IOException, InterruptedException {
        int totalNumber = 0;

        for (IntWritable value : values)
          totalNumber += value.get();

        context.write(key, new IntWritable(totalNumber));
      }
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: PairFrequency <input path> <output path>");
      System.exit(-1);
    }

    Job job = new Job();
    job.setJarByClass(PairFrequency.class);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    job.setMapperClass(PairFrequencyMapper.class);
    job.setReducerClass(PairFrequencyReducer.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
