package com.onemillionworlds.tamarin.gradle;

import com.onemillionworlds.tamarin.gradle.tasks.CreateStructs;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Plugin that registers the CreateStructs task.
 */
public class CreateStructsPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        // Register the CreateStructs task
        project.getTasks().register("createStructs", CreateStructs.class, task -> {
            task.setGroup("OpenXR");
            task.setDescription("Parses OpenXR header files and generates Java classes for constants and structs");
        });
    }
}