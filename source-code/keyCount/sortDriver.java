
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/*
 * This driver class is called using the ToolRunner.run method
 * call in the main method (below). Extending the Configured class 
 * enables the driver class to access Hadoop configuration options.
 */
public class sortDriver extends Configured implements Tool {

  @Override
  public int run(String[] args) throws Exception {

    if (args.length != 2) {
      System.out.printf("Usage: WordCountDriver <input dir> <output dir>\n");
      return -1;
    }

    Job job = new Job(getConf());
    job.setJarByClass(sortDriver.class);
    job.setJobName("value sort");

    
    FileInputFormat.setInputPaths(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    
    job.setMapperClass(sortMapper.class);
    job.setReducerClass(sortReducer.class);
    
    job.setOutputKeyClass(FloatWritable.class);
    job.setOutputValueClass(IntWritable.class);
    
    job.setNumReduceTasks(1);
    
    

    boolean success = job.waitForCompletion(true);
    return success ? 0 : 1;
  }


  
  
  
  
  public static void main(String[] args) throws Exception {
    int exitCode = ToolRunner.run(new Configuration(), new sortDriver(), args);
    System.exit(exitCode);
  }
}
