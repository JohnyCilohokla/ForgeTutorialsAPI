package com.forgetutorials.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import com.infernosstudio.johny.InfernosTimer;
import com.infernosstudio.johny.JC;
import com.infernosstudio.johny.JCFile;
import com.infernosstudio.json.JSONHelper;

public class Build {

	boolean windows = true;

	public static void main(String[] args) {
		InfernosTimer timer = new InfernosTimer("Init");

		File root = null;
		try {
			root = new File(".").getCanonicalFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		File scrFolder = new File(root, "src");
		File mainFolder = new File(scrFolder, "main");
		File sourcesFolder = new File(mainFolder, "java");
		File resourcesFolder = new File(mainFolder, "resources");
		File gradle = new File(root, "gradlew.bat");
		File gradleDir = new File(root, "gradle");
		File gradleTemplate = new File(root, "template.gradle");
		File gradleScript = new File(root, "build.gradle");

		JCFile.deleteDirContent(mainFolder);
		mainFolder.mkdirs();

		JC.out(timer);
		timer.tick("Loading Config");

		JSONObject rootConfig = new JSONObject(JCFile.fileToRawString(new File(root, "config.json")));

		JSONObject buildConfig = rootConfig.getJSONObject("build");

		BuildFileParser fileParser = new BuildFileParser();

		String name = JSONHelper.getOrSet(buildConfig, "NAME", root.getName());
		String buildOutput = JSONHelper.getOrSet(buildConfig, "BUILD_OUTPUT", root.getName());
		boolean projectNameAsProjectFolder = JSONHelper.getOrSet(buildConfig, "BUILD_PROJECT_NAME_AS_FOLDER_NAME", false);

		JSONObject versionRoot = buildConfig.getJSONObject("VERSION");

		JSONArray sourceFolders = rootConfig.getJSONArray("sources");
		JSONArray resourceFolders = rootConfig.getJSONArray("resources");

		String mcVersion = JSONHelper.getOrSet(versionRoot, "MCVERSION", "");
		fileParser.addPram("MCVERSION", mcVersion);

		String forgeVersion = JSONHelper.getOrSet(versionRoot, "FORGEVERSION", "");
		fileParser.addPram("FORGEVERSION", forgeVersion);

		String forgeGradle = JSONHelper.getOrSet(versionRoot, "FORGEGRADLE", "");
		fileParser.addPram("FORGEGRADLE", forgeGradle);

		String corePlugin = JSONHelper.getOrSet(buildConfig, "COREPLUGIN", "false");
		fileParser.addPram("COREPLUGIN", corePlugin);

		String corePluginClass = JSONHelper.getOrSet(buildConfig, "COREPLUGINCLASS", "");
		fileParser.addPram("COREPLUGINCLASS", corePluginClass);

		String version = JSONHelper.getOrSet(versionRoot, "1-VERSION", 0, new DecimalFormat("0"));
		fileParser.addPram("VERSION", version);
		String release = JSONHelper.getOrSet(versionRoot, "2-RELEASE", 0, new DecimalFormat("00"));
		fileParser.addPram("RELEASE", release);
		String revision = JSONHelper.getOrSet(versionRoot, "3-REVISION", 0, new DecimalFormat("0000"), 1);
		fileParser.addPram("REVISION", revision);

		JC.out(timer);
		timer.tick("Gradle Setup");

		if (!gradle.isFile() && !gradleDir.isDirectory()) {
			JC.out(Build.executeCommand(new String[] { new File(root, "XSetup.bat").getAbsolutePath() }, root));
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			resourcesFolder = new File(mainFolder, "resources");
			if (resourcesFolder.isDirectory()) {
				JC.out("Created Junction/Symbolic link");
			} else {
				JC.out("Error creating Junction/Symbolic link");
				System.exit(-1);
			}
		}

		JC.out(timer);
		timer.tick("Files Copy");

		JC.out(root.getAbsolutePath());

		for (int i = 0; i < sourceFolders.length(); i++) {
			String sourcePath = sourceFolders.getString(i);
			JCFile.copyFileOrDir(new File(root, sourcePath), sourcesFolder, fileParser);
		}

		for (int i = 0; i < resourceFolders.length(); i++) {
			String resourcePath = resourceFolders.getString(i);
			JCFile.copyFileOrDir(new File(root, resourcePath), resourcesFolder, fileParser);
		}

		JCFile.deleteFileOrDir(gradleScript);
		try {
			JCFile.copyFile(gradleTemplate, gradleScript, fileParser);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		JC.out(timer);
		timer.tick("Gradle Clean Build");

		Build.build(root, gradle);

		File out = new File("build" + File.separatorChar + "libs" + File.separatorChar + "" + name + ".jar");

		File outputFolder = new File(buildOutput);
		if (projectNameAsProjectFolder) {
			outputFolder = new File(outputFolder, "" + name + "-" + version + "." + release);
		}
		try {
			JCFile.copyFile(out, new File(outputFolder, "" + name + "-universal-" + mcVersion + "-" + version + "." + release + "." + revision + ".jar"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		JC.out(out.getAbsolutePath() + " " + out.exists());

		JC.out(timer);
		timer.tick("Saving config");

		JCFile.stringToRawFile(new File(root, "config.json"), rootConfig.toString(666));

		JC.out(timer.end());
	}

	public static void build(File root, File gradle) {
		String out = Build.executeCommand(new String[] { gradle.getAbsolutePath(), "clean", "build" }, root);
		JC.outF(out);
		JC.out(out.contains("BUILD SUCCESSFUL") ? "BUILD SUCCESSFUL" : out);

	}

	private static String executeCommand(String[] command, File path) {
		StringBuilder out = new StringBuilder();
		try {
			String s;
			ProcessBuilder pb = new ProcessBuilder(command);
			pb.directory(path);
			Process p = pb.start();
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((s = stdInput.readLine()) != null) {
				out.append(s);
				out.append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toString();
	}
}
