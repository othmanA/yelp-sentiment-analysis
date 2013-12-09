
import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class sortMapper extends Mapper<LongWritable, Text, FloatWritable, Text> {

	FloatWritable f = new FloatWritable();
	Text text = new Text();
	String[] line;

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		/*
		 * Convert the line, which is received as a Text object, to a String
		 * object.
		 */
		line = value.toString().split("\t");

		/*
		 * Setting the key and value
		 */
		try {
			f.set(Float.parseFloat(line[0]));
			text.set(line[1]);
		} catch (Exception e) {
			context.getCounter("Faild", "faild").increment(1);
		}

		/*
		 * pass it to the reducer
		 */
		context.write(f, text);

	}
}