import java.io.File;
import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.codehaus.jackson.map.ObjectMapper;

public class SentimentMapper extends Mapper<LongWritable, Text, Text, Text> {

	private Text result = new Text();
	private Text reviewId = new Text();

	private ObjectMapper jsonMapper = new ObjectMapper();

	private DistributedCacheReadOnlyFiles positiveWords;
	private DistributedCacheReadOnlyFiles negativeWords;
	private DistributedCacheReadOnlyFiles stopWords;

	private String reviewWords[];

	private int positiveWordsCount;
	private int negativeWordsCount;

	/**
	 * Setup
	 */
	@Override
	protected void setup(Context context) throws IOException {
		positiveWords = new DistributedCacheReadOnlyFiles(new File(
				"positive.txt"));
		negativeWords = new DistributedCacheReadOnlyFiles(new File(
				"negative.txt"));
		stopWords = new DistributedCacheReadOnlyFiles(new File("stop.txt"));
	}

	/**
	 * Map
	 */
	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		// Add 1 to the reviews counter
		context.getCounter("TotalReviews", "totalReviews").increment(1);

		// convert the Json array to an object of class review.
		Review review = jsonMapper.readValue(value.toString(), Review.class);

		reviewWords = review.getText().split("\\W+");

		// Set the values of the counters to zero
		positiveWordsCount = 0;
		negativeWordsCount = 0;

		// We need to remove any stop word from the context
		// If the word is a stop word we will ignore it
		// There is no need to remove it from the array

		// Also we are checking if the word is not positive or negative
		// Since we don't use the else in our checking we have to declare a
		// boolean variable
		for (String word : reviewWords) {
			context.getCounter("TotalWords", "The total number of words in the input")
			.increment(1);
			
			boolean found = false;
			if (!stopWords.contains(word)) {

				if (positiveWords.contains(word)) { // positive ?
					context.getCounter("PositiveWords",
							"The words that are found in the file [positive.txt]")
							.increment(1);
					positiveWordsCount++;
					found = true;
				}

				// We are not going to use the ELSE because sometimes the word
				// is neutral

				if (negativeWords.contains(word)) { // Negative ?
					context.getCounter("NegativeWords",
							"The words that are found in the file [negative.txt]")
							.increment(1);
					negativeWordsCount++;
					found = true;
				}

				// We are calculating the total words for the counters
				context.getCounter("TotalWordsPSN", "The Total number of words that are positive or negative or neutral")
						.increment(1);

				// if the word is not found then it's neutral
				if (!found)
					context.getCounter("Neutral",
							"Words that are not positive, not negative and not stop words").increment(1);
			}else{
			// Calculate the stop words
			context.getCounter("StopWords",
					"Stop words").increment(1);
			}
		}

		// setting the positive count, negative count
		result.set(positiveWordsCount + "," + negativeWordsCount);
		reviewId.set(review.getReview_id());

		// Pass the review id with the result to the reducer
		context.write(reviewId, result);

	}
}