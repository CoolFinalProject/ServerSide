package ambient_intelligence.SleepSession;

import java.util.List;
import java.util.Map;

public class SleepScoringUtils {
	

	
	
	public static int evaluateSleepSession(Map<String, Object> details) {
			
		int lightScore= SleepScoringUtils.evaluateLight(details);
	    int soundScore = SleepScoringUtils.evaluateSound(details);    	
	        

	       

	        return (int)(lightScore+soundScore)/2;
	    }

	public static int evaluateSound(Map<String,Object> details)
	{
		/* int totalVoice = voiceList.size();
        long spikes = voiceList.stream().filter(db -> db.doubleValue() > 60).count();
        double averageDb = voiceList.stream().mapToDouble(Number::doubleValue).average().orElse(0);
        double deviation = Math.abs(averageDb - 40);

        double voiceScore = 0.0;
	if (averageDb>= 40.0) {
		voiceScore -= deviation;
	}
        voiceScore -= spikes * 0.5;
        voiceScore = Math.max(0, voiceScore);*/
		List<Number> soundList = (List<Number>) details.get("sound");
		
		double soundScore=100.0;
        if (!(soundList == null || soundList.isEmpty()))
        {
        	int totalSound = soundList.size();
        	long over40 = soundList.stream().filter(dec -> dec.doubleValue() >= 40 && dec.doubleValue()<50).count();
        	long over50 = soundList.stream().filter(dec -> dec.doubleValue() >= 50 && dec.doubleValue()<60).count();
        	long over60 = soundList.stream().filter(dec -> dec.doubleValue() >= 60).count();

        	soundScore -= ((over40 * 15) / totalSound);
        	soundScore -= ((over50 * 70) / totalSound);
        	soundScore -= ((over60 * 100) / totalSound);
        	soundScore = Math.max(0, soundScore);
        }
		return (int)soundScore;
	}
	public static int evaluateLight(Map<String,Object> details)
	{
		List<Number> lightList = (List<Number>) details.get("light");
		
		double lightScore=100.0;
        if (!(lightList == null || lightList.isEmpty()))
        {
        	int totalLight = lightList.size();
        	long over10 = lightList.stream().filter(lux -> lux.doubleValue() > 10 && lux.doubleValue()<100).count();
        	long over100 = lightList.stream().filter(lux -> lux.doubleValue() > 100 && lux.doubleValue()<1000).count();
        	long over1000 = lightList.stream().filter(lux -> lux.doubleValue() > 1000).count();

        	lightScore -= ((over10 * 15) / totalLight);
        	lightScore -= ((over100 * 70) / totalLight);
        	lightScore -= ((over1000 * 100) / totalLight);
        	lightScore = Math.max(0, lightScore);
        }
        return (int)lightScore;
	}
}
