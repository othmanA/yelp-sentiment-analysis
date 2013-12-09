import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class SentimentReducer extends Reducer<Text, Text, Text, Text> {

	private Text result = new Text();
	private Text reviewId = new Text();

	private int positiveWordsCount;
	private int negativeWordsCount;
	private float score;
	String[] schema;
	private DecimalFormat twoDForm = new DecimalFormat("##.#");
	String scoreString;

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {

		reviewId.set(key);
		positiveWordsCount = 0;
		negativeWordsCount = 0;

		for (Text value : values) {
			schema = value.toString().split(",");
			positiveWordsCount = Integer.parseInt(schema[0]);
			negativeWordsCount = Integer.parseInt(schema[1]);

			// The formula to calculate the score is
			// IF POSITIVE > NEGATIVE THEN SCORE = POSITIVE / NEGATIVE
			// IF NEGATIVE < POSITIVE THEN SCORE = -1 (NEGATIVE / POSITIVE)
			// IF NEGATIVE = POSITIVE THEN SCORE = 0

			// IF SCORE > 20 THEN SCORE = 20 .. IF SCORE < -20 THEN SCORE = -20
			// SO we get the result in a scale of 20
			// We are trying to not ignore any value, thus we will convert
			// any 0 in Denominator to 1
			if (positiveWordsCount > negativeWordsCount) {
				if (negativeWordsCount == 0)
					negativeWordsCount = 1;

				score = (float) positiveWordsCount / negativeWordsCount;
			} else if (negativeWordsCount > positiveWordsCount) {
				if(positiveWordsCount == 0)
					positiveWordsCount = 1;
				score = (float) (-1) * (negativeWordsCount / positiveWordsCount);
			} else
				score = 0;
			
			if(score > 20)
				score = 20;
			else if(score < -20)
				score = -20;
		}
		
		// FORMAT the result
		scoreString = twoDForm.format(score);

		result.set(scoreString);
		context.write(result, key);
	}

}