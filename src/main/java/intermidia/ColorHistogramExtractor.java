package intermidia;

import java.io.FileWriter;

import org.openimaj.feature.DoubleFV;
import org.openimaj.image.MBFImage;
import org.openimaj.image.pixel.statistics.HistogramModel;
import org.openimaj.math.statistics.distribution.MultidimensionalHistogram;
import org.openimaj.video.processing.shotdetector.VideoKeyframe;

import TVSSUnits.Shot;
import TVSSUnits.ShotList;
import TVSSUtils.KeyframeReader;

public class ColorHistogramExtractor 
{
	/*Usage: <in: keyframe folder> <out: histogram values>*/
    public static void main( String[] args ) throws Exception
    { 	
    	ShotList shotList = KeyframeReader.readFromFolder(args[0]);
    	FileWriter output = new FileWriter(args[1]);
    	
    	int shotNum = 0;
    	HistogramModel histogramModel = new HistogramModel(4,4,4);		
    	for(Shot shot: shotList.getList())
    	{ 	
    		for(VideoKeyframe<MBFImage> keyframe: shot.getKeyFrameList())
    		{    			
    			histogramModel.estimateModel(keyframe.imageAtBoundary);
    			MultidimensionalHistogram histogram = histogramModel.histogram.clone();
    			    				
    			DoubleFV histFV = histogram.asDoubleFV();
    			output.write(Integer.toString(shotNum));
    			for(double binVal : histFV.values)
    			{
    				output.write(" " + binVal);
    			}
    			output.write("\n");
    		}
    		shotNum++;
    	}
    	output.close();
    }
}
