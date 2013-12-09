import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class sortReducer extends Reducer<FloatWritable, IntWritable, FloatWritable, IntWritable> {

	@Override
	public void reduce(FloatWritable key, Iterable<IntWritable> values, Context context)
			throws IOException, InterruptedException {

		// We are using the reducer for ordering the results by business id
		int count = 0;
		for (IntWritable value : values){
			count++;
		}
			context.write(key, new IntWritable(count));

	}

}