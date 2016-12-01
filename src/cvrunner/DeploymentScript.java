package cvrunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.aspectj.tools.ajc.Main;

public class DeploymentScript {
	
	private static final String sourceRoot = "src\\us\\mattowens\\concurrencyvisualizer";
	private static final String libLocation = "lib";
	private static final String deploymentPath = "ConcurrencyVisualizer";
	private static final String deploymentLibs = deploymentPath + "\\lib";
	private static final String compileOutput = deploymentLibs + "\\concurrency_visualizer.jar";
	private static final String classpath = compileOutput + ";" + deploymentLibs + "\\aspectj1.8\\lib\\aspectjTools.jar";
	private static final String compileLiveMode = "javac src\\cvrunner\\ConcurrencyVisualizerLiveMode.java -d " + deploymentPath + " -cp " + classpath;
	private static final String compileHistoricalMode = "javac src\\cvrunner\\ConcurrencyVisualizerHistoricalMode.java -d " + deploymentPath + " -cp " + classpath;
	private static final String eventColors = "EventColors.txt";
	private static final String liveRun = "LiveMode.bat";
	private static final String historicalRun = "HistoricalMode.bat";
	private static final String license = "LICENSE";
	
	private static FileSystem fileSystem = FileSystems.getDefault();
	
	public static void main(String[] args) throws IOException {
		
		//Remove old version if it exists
		File deploymentDirectory = new File(deploymentPath);
		
		if(deploymentDirectory.exists()) {
			deleteDirectory(deploymentDirectory);
		}
		
		boolean createdDirectory = deploymentDirectory.mkdir();
		copySingleFile(eventColors);
		copySingleFile(liveRun);
		copySingleFile(historicalRun);
		copySingleFile(license);



		
		deepCopyDirectory(libLocation, deploymentLibs);
		System.out.println("Compiling AspectJ...");
		String[] ajcArgs = {
				"-sourceroots", sourceRoot,
				"-outjar", compileOutput,
				"-1.8", "-Xlint:ignore"
		};
		Main main = new Main();
		main.runMain(ajcArgs, false); //Run without System.exit()
		System.out.println("Done");
		System.out.println("Compiling LiveMode...");
		startProcess(compileLiveMode);
		System.out.println("Done");
		System.out.println("Compiling HistoricalMode...");
		startProcess(compileHistoricalMode);
		System.out.println("Done");
		

		
	}
	
	
	private static void deleteDirectory(File directory) {
		File[] files = directory.listFiles();
		
		if(files != null) {
			for(File file : directory.listFiles()) {
				if(file.isDirectory()) {
					deleteDirectory(file);
				} else {
					file.delete();
				}
			}
		}
		directory.delete();
	}
	
	private static void deepCopyDirectory(String source, String destination) throws IOException {
		Path sourcePath = fileSystem.getPath(source);
		Path destinationPath = fileSystem.getPath(destination);
		
		File sourceFile = new File(source);
		if(sourceFile.isDirectory()) {
			File destinationDirectory = new File(destination+"\\" + sourceFile.getName());
			destinationDirectory.mkdir();
		}
		Files.copy(sourcePath, destinationPath);
		
		File sourceDirectory = sourcePath.toFile();
		
		for(File file : sourceDirectory.listFiles()) {
			if(file.isDirectory()) {
				deepCopyDirectory(file.getPath(), destination+"\\"+file.getName());
			} else {
				Path filePath = fileSystem.getPath(file.getPath());
				Path copiedPath = fileSystem.getPath(destination + "\\" + file.getName());
				Files.copy(filePath, copiedPath);
			}
		}
	}
	
	private static void copySingleFile(String source) throws IOException {
		Path sourcePath = fileSystem.getPath(source);
		Path destinationPath = fileSystem.getPath(deploymentPath+"\\"+source);
		Files.copy(sourcePath, destinationPath);
	}
	
	private static void startProcess(String command) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(command);
		InputStream stdin = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(stdin);
		BufferedReader br = new BufferedReader(isr);

		String line = null;

		while ( (line = br.readLine()) != null)
		     System.out.println(line);

		try {
			int exitVal = process.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
