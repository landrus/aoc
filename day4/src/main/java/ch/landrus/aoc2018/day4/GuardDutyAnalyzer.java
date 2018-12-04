package ch.landrus.aoc2018.day4;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GuardDutyAnalyzer {
	
	private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    private List<String> input;
    
    public GuardDutyAnalyzer(Path inputFile) throws IOException {
    	try (Stream<String> stream = Files.lines(inputFile)) {
        	input = stream.collect(Collectors.toList());
        }
    }
    
    private Guard guard;
    private Map<Integer, Guard> guards = new HashMap<>();
    
    public void collectInformation() {
    	List<GuardActivity> activities = input.stream().map(act -> new GuardActivity(act))
    			.sorted((o1, o2) -> o1.time.compareTo(o2.time)).collect(Collectors.toList());
    	
    	activities.stream().forEachOrdered(act -> {
    		switch (act.activity) {
    			case BEGIN:
    				if (guards.containsKey(act.id)) {
    					guard = guards.get(act.id);
    				} else {
    					guard = new Guard(act.id);
    					guards.put(act.id, guard);
    				}
    				
    				guard.addActivity(act.time, act.activity);
    				
    				break;
    			case WAKE:
    				guard.addActivity(act.time, act.activity);
    				break;
    			case SLEEP:
    				guard.addActivity(act.time, act.activity);
    				break;
    		}
    	});
    }
    
    private LocalDateTime sleep;
    
    public int getGuardMostAsleep() {
    	guards.values().stream().forEach(g -> {
    		g.activities.stream().forEachOrdered(a -> {
    			if (a.type == ActivityType.SLEEP) {
    				sleep = a.time;
    			} else if (a.type == ActivityType.WAKE) {
    				long sleepingMinutes = ChronoUnit.MINUTES.between(sleep, a.time);
    				g.addMinutesSleeping((int) sleepingMinutes);
    			}
    		});
    		
    		if (g.minutesSleeping > guard.minutesSleeping) {
				guard = g;
			}
    	});
    	
    	return guard.mostSleepyMinute();
    }
    
    public int getGuardMostFrequentMinute() {
    	Map<Integer, Map<Guard, Integer>> stuff = new HashMap<>();
    	LocalDateTime sleep = null;
    	int guardId = 0;
    	int amount = 0;
    	int minute = 0;

    	for (Guard g : guards.values()) {
    		for (Guard.Activity a : g.activities) {
    			switch (a.type) {
	    			case BEGIN:
	    				break;
	    			case SLEEP:
	    				sleep = a.time;
	    				break;
	    			case WAKE:
	    				for (int i = sleep.getMinute(); i < a.time.getMinute(); i++) {
	    					Map<Guard, Integer> guardMinute;
	    					
	    					if (!stuff.containsKey(i)) {
	    						guardMinute = new HashMap<>();
	    						stuff.put(i, guardMinute);
	    					} else {
	    						guardMinute = stuff.get(i);
	    					}
	    					
	    					if (!guardMinute.containsKey(g)) {
	    						guardMinute.put(g, 1);
	    					} else {
	    						int gAmount = guardMinute.get(g) + 1;
	    						guardMinute.put(g, gAmount);
	    						
	    						if (gAmount > amount) {
	    							amount = gAmount;
	    							guardId = g.id;
	    							minute = i;
	    						}
	    					}
	    				}
	
	    				break;
    			}
    		}
    	}
    	
    	return guardId * minute;
    }
    
    private static enum ActivityType { BEGIN, WAKE, SLEEP }
    
    private static class Guard {
    	
    	private static class Activity {
    		
    		private final LocalDateTime time;
    		private final ActivityType type;
    		
    		private Activity(LocalDateTime time, ActivityType type) {
    			this.time = time;
    			this.type = type;
    		}
        	
        	@Override
        	public String toString() {
        		return "[" + time.format(DT_FORMAT) + ":" + type + "]";
        	}
    		
    	}
    	
    	private final int id;
    	private int minutesSleeping;
    	private final List<Activity> activities = new ArrayList<>();
    	
    	private Guard(int id) {
    		this.id = id;
    	}
    	
    	private void addActivity(LocalDateTime time, ActivityType type) {
    		activities.add(new Activity(time, type));
    	}
    	
    	private void addMinutesSleeping(int minutes) {
    		minutesSleeping += minutes;
    	}

    	
    	private int mostSleepyMinute() {
    		int[] minutes = new int[60];
    		LocalDateTime sleep = null;
    		
    		for (Activity a : activities) {
				switch (a.type) {
					case BEGIN:
						break;
					case SLEEP:
						sleep = a.time;
						break;
					case WAKE:
						for (int i = sleep.getMinute(); i < a.time.getMinute(); i++) {
							minutes[i] = minutes[i] + 1;
						}
						
						break;
				}
			}
    		
    		int minute = 0;
    		int minuteValue = 0;
    		
    		for (int i = 0; i < minutes.length; i++) {
				if (minutes[i] > minuteValue) {
					minute = i;
					minuteValue = minutes[i];
				}
			}
    		
    		return minute * id;
    	}
    	
    	@Override
    	public String toString() {
    		return "[" + id + ":" + activities + "]";
    	}
    	
    }
    
    private static class GuardActivity {
    	
    	public final LocalDateTime time;
    	public final ActivityType activity;
    	public int id;
    	
    	public GuardActivity(String line) {
    		Scanner scanner = new Scanner(line);
    		scanner.findInLine("\\[(.*)\\] (.*)");
    		MatchResult match = scanner.match();
    		time = LocalDateTime.parse(match.group(1), DT_FORMAT);
    		String act = match.group(2);
    		
    		if (act.startsWith("Guard")) {
    			activity = ActivityType.BEGIN;
    			id = Integer.parseInt(act.split(" ")[1].substring(1));
    		} else if (act.startsWith("wake")) {
    			activity = ActivityType.WAKE;
    		} else {
    			activity = ActivityType.SLEEP;
    		}
    		
    		scanner.close();
    	}
    	
    	@Override
    	public String toString() {
    		return "[" + time.format(DT_FORMAT) + ":" + activity + (id != 0 ? ":" + id : "") + "]";
    	}
    	
    }
    
    public static void main(String[] args) throws IOException, URISyntaxException {
    	URL inputUrl = GuardDutyAnalyzer.class.getResource("/day4-input.txt");
    	Path inputPath = Paths.get(inputUrl.toURI());
        GuardDutyAnalyzer analyzer = new GuardDutyAnalyzer(inputPath);
        analyzer.collectInformation();
        System.out.printf("Strategy 1: %s", analyzer.getGuardMostAsleep());
        System.out.printf("\nStrategy 2: %s", analyzer.getGuardMostFrequentMinute());
    }
    
}
