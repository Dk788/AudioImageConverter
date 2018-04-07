package application;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Converter {
	
	public File audioToImage(File audio) {
    	File newImage = audio;	// TODO: convert audioFile to image
		return newImage;
	}
    
	public File imageToAudio(File image) {
		File newAudio = new File(System.getProperty("java.io.tmpdir") + File.separator + "newAudio");
		int maxFreq = 0;
		int channels = 1;
		int sampleRate = 44100;
		float durationSeconds = (float) 3; // 输出长度
		ArrayList<Short> data = new ArrayList<Short>();
		ArrayList<Integer> tmpData = new ArrayList<Integer>();
		
		try {
			BufferedImage bufferedImage = ImageIO.read(image);
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			int C = 20000 / height;
			int numSamples = (int)(sampleRate * durationSeconds);
			double samplesPerPixel = Math.floor(numSamples / width);

			for (int x = 0; x < numSamples; x++) {
				int rez = 0;
				int pixel_x = (int) (x / samplesPerPixel);
				if (pixel_x >= width)
		            pixel_x = width - 1;
				
				for (int y = 0; y < height; y++) {
					Color color = new Color(bufferedImage.getRGB(pixel_x, y));
					int s = color.getRed() + color.getGreen() + color.getBlue();
				    int volume = s * 100 / 765;
				    if (volume == 0)
		                continue;
				    int freq = C * (height - y + 1);
				    rez += getData(volume, freq, sampleRate, x);  
				}
				tmpData.add(rez);
		        if (Math.abs(rez) > maxFreq)
		            maxFreq = Math.abs(rez);
			}
			
			for (int i = 0; i < tmpData.size(); i++) {
				data.add((short)(32767 * tmpData.get(i) / maxFreq));
			}
			
			WavFile wavFile = WavFile.newWavFile(newAudio, channels, numSamples, 16, sampleRate);
			int[] dataArray = new int[data.size()];
			
			for (int i = 0; i < dataArray.length; i++) {
				dataArray[i] = data.get(i);
			}
			wavFile.writeFrames(dataArray, channels * numSamples);
			wavFile.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return newAudio;
	}
    
	private int getData(int volume, int freq, int sampleRate, int index) {
		return (int) (volume * Math.sin(freq * Math.PI * 2 * index / sampleRate));
	}
}
