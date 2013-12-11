import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class NucleotideFrequency {

  static class NucleotideFrequencyMapper extends 
    Mapper <LongWritable, Text, Text, IntWritable> {
      private static final String ADENINE  = "A";
      private static final String THYMINE  = "T";
      private static final String CYTOSINE = "C";
      private static final String GUANINE  = "G";

      public void map(LongWritable key, Text value, Context context)
        throws IOException, InterruptedException {
        int adenineCount  = 0;
        int cytosineCount = 0;
        int guanineCount  = 0;
        int thymineCount  = 0;

        String line = value.toString();   
        for (int i = 0; i < line.length(); i++) {
          String currentChar = String.valueOf(line.charAt(i));
          switch(currentChar) {
            case ADENINE:
              adenineCount += 1;
              break;
            case THYMINE:
              thymineCount += 1;
              break;
            case GUANINE:
              guanineCount +=1 ;
              break;
            case CYTOSINE:
              cytosineCount += 1;
              break;
          }
        }

        context.write(new Text(ADENINE), new IntWritable(adenineCount)); 
        context.write(new Text(CYTOSINE), new IntWritable(cytosineCount)); 
        context.write(new Text(THYMINE), new IntWritable(thymineCount)); 
        context.write(new Text(GUANINE), new IntWritable(guanineCount)); 
      }
    }

  static class NucleotideFrequencyReducer extends 
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
      System.err.println("Usage: NucleotideFrequency <input path> <output path>");
      System.exit(-1);
    }

    Job job = new Job();
    job.setJarByClass(NucleotideFrequency.class);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    job.setMapperClass(NucleotideFrequencyMapper.class);
    job.setReducerClass(NucleotideFrequencyReducer.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
