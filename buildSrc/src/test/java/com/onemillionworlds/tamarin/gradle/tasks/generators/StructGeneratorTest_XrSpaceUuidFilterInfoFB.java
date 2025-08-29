package com.onemillionworlds.tamarin.gradle.tasks.generators;

/**
 * This one is interesting because uuids is not marked as const so it *looks* like an output for
 * openXr to read into. But the number is held in uuidCount (implying its an input). I believe
 * this extension is done incorrectly and it *should* have been marked const but wasn't.
 * This test makes sure these oddballs generate correctly
 */
public class StructGeneratorTest_XrSpaceUuidFilterInfoFB {
}
