package com.codingame.game;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.codingame.gameengine.runner.MultiplayerGameRunner;

public class KeepOffTheGrassMain {
	
    public static void main(String[] args) throws Exception {
    	List<String> agentNames = args.length == 1 ? Arrays.asList(args[0].split(",")) : null;
        MultiplayerGameRunner gameRunner = new MultiplayerGameRunner();
        
        
//        gameRunner.addAgent("python3.8 bots/julien.py", "JulienBot");
//        gameRunner.addAgent("python3.8 bots/kevin.py", "InsectBot");
//
//        gameRunner.start();
        List<GameAgent> agents = loadAgents("agents.yaml");
        for (GameAgent agent : agents) {
        	if (agentNames == null || agentNames.contains(agent.getName())) {
	        	if (agent.getAgentClass() != null) {
					gameRunner.addAgent(Class.forName(agent.getAgentClass()), agent.getName());
	        	} else {
	        		gameRunner.addAgent(agent.getCommandLine(), agent.getName());
	        	}
        	}
        }
        
        gameRunner.start();
    }
    
    private static List<GameAgent> loadAgents(String path) {
    	Constructor constructor = new Constructor(GameAgents.class);
    	Yaml yaml = new Yaml(constructor);
    	InputStream inputStream = KeepOffTheGrassMain.class
    	  .getClassLoader()
    	  .getResourceAsStream(path);
    	GameAgents agents = yaml.load(inputStream);
    	return agents.getAgents();
    }
    
    public static class GameAgents {
    	List<GameAgent> agents;

		public List<GameAgent> getAgents() {
			return agents;
		}

		public void setAgents(List<GameAgent> agents) {
			this.agents = agents;
		}
    }
    
    public static class GameAgent {
    	String agentClass;
    	String commandLine;
    	String name;
    	
		public String getAgentClass() {
			return agentClass;
		}
		public void setAgentClass(String agentClass) {
			this.agentClass = agentClass;
		}
		public String getCommandLine() {
			return commandLine;
		}
		public void setCommandLine(String commandLine) {
			this.commandLine = commandLine;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
    }
}
