import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class Sentiment extends Configured implements Tool {

	public static void main(String[] args) throws Exception {

		int exitCode = ToolRunner.run(new Sentiment(), args);
		System.exit(exitCode);
	}

	@Override
	public int run(String[] args) throws Exception {

		Job job = JobBuilder.parseInputAndOutput(this, getConf(), args);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		job.setMapperClass(SentimentMapper.class);
		job.setReducerClass(SentimentReducer.class);

		job.setNumReduceTasks(1);

		boolean success = job.waitForCompletion(true);
		return success ? 0 : 1;
	}
}
