import com.typesafe.scalalogging.Logger
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.platanios.tensorflow.api.learn.layers.Linear
import org.platanios.tensorflow.api._
import org.platanios.tensorflow.api.learn.StopCriteria
import org.platanios.tensorflow.api.learn.estimators.InMemoryEstimator
import org.platanios.tensorflow.api.tf.learn.{Flatten, Input, Mean, Model, ReLU, SparseSoftmaxCrossEntropy}
import org.platanios.tensorflow.data.image.MNISTLoader
import org.slf4j.LoggerFactory

import java.nio.file.Paths

object Network {
	
	private val logger = Logger(LoggerFactory.getLogger("Examples / MNIST"))
	
	def main(args: Array[String]): Unit = {
		mnist_tensorflow()
	}
	
	def sparkMLP(): Unit = {
		val layers = Array(4, 5, 4, 3)
		val trainer = new MultilayerPerceptronClassifier()
			.setLayers(layers)
			.setBlockSize(128)
			.setSeed(0)
			.setMaxIter(100)
	}
	
	def mnist_tensorflow() = {
		val dataset = MNISTLoader.load(Paths.get("/tmp"))
		val trainImages = tf.data.datasetFromTensorSlices(dataset.trainImages).map(_.toFloat)
		val trainLabels = tf.data.datasetFromTensorSlices(dataset.trainLabels).map(_.toLong)
		val testImages = tf.data.datasetFromTensorSlices(dataset.testImages).map(_.toFloat)
		val testLabels = tf.data.datasetFromTensorSlices(dataset.testLabels).map(_.toLong)
		val trainData =
			trainImages.zip(trainLabels)
				.repeat()
				.shuffle(10000)
				.batch(256)
				.prefetch(10)
		
		// Create the MLP model.
		val input = Input(FLOAT32, Shape(-1, 28, 28))
		val trainInput = Input(INT64, Shape(-1))
		val layer = Flatten[Float]("Input/Flatten") >>
			Linear[Float]("Layer_0", 128) >> ReLU[Float]("Layer_0/Activation", 0.1f) >>
			Linear[Float]("Layer_1", 64) >> ReLU[Float]("Layer_1/Activation", 0.1f) >>
			Linear[Float]("Layer_2", 32) >> ReLU[Float]("Layer_2/Activation", 0.1f) >>
			Linear[Float]("OutputLayer", 10)
		val loss = SparseSoftmaxCrossEntropy[Float, Long, Float]("Loss") >> Mean("Loss/Mean")
		val optimizer = tf.train.GradientDescent(1e-6f)
		val model = Model.simpleSupervised(input, trainInput, layer, loss, optimizer)
		
		// Create an estimator and train the model.
		val estimator = InMemoryEstimator(model)
		estimator.train(() => trainData, StopCriteria(maxSteps = Some(1000000)))
		
		def accuracy(images: Tensor[UByte], labels: Tensor[UByte]): Float = {
			val predictions = estimator.infer(() => images.toFloat)
			predictions
				.argmax(1).toUByte
				.equal(labels).toFloat
				.mean().scalar
		}
		
		logger.info(s"Train accuracy = ${accuracy(dataset.trainImages, dataset.trainLabels)}")
		logger.info(s"Test accuracy = ${accuracy(dataset.testImages, dataset.testLabels)}")
	}
}