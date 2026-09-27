package com.onemillionworlds.tamarin.openxrbindings;

import com.onemillionworlds.tamarin.openxrbindings.structs.StructsAreGeneratedCorrectlyTest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JNI binds a Java native method to its C function by name only, so if their parameter types disagree nothing fails at
 * link time; the C just reads garbage (e.g. a Java long read as a jint is silently truncated). This checks the
 * generated XR10 native methods against the generated C JNI functions.
 */
public class NativeSignaturesMatchTest {

    private static final Pattern JAVA_NATIVE = Pattern.compile("public static native int (n\\w+)\\(([^)]*)\\);");
    private static final Pattern C_FUNCTION = Pattern.compile("JNIEXPORT jint JNICALL Java_com_onemillionworlds_tamarin_openxrbindings_XR10_(n\\w+)\\s*\\(JNIEnv \\*env, jclass cls([^)]*)\\)");
    private static final Pattern C_SIGNATURE_COMMENT = Pattern.compile("\\* Method:\\s+(n\\w+)\\s*\\n\\s*\\* Signature: \\(([A-Z]*)\\)I");

    @Test
    public void javaNativesMatchCFunctions() throws IOException {
        File moduleRoot = StructsAreGeneratedCorrectlyTest.findModuleRoot();
        String java = read(new File(moduleRoot, "src/main/generated/java/com/onemillionworlds/tamarin/openxrbindings/XR10.java"));
        String c = read(new File(moduleRoot, "src/main/generated/native/src/com_onemillionworlds_tamarin_openxrbindings_XR10.c")).replace("\r\n", "\n");

        Map<String, List<String>> javaTypes = new TreeMap<>();
        Matcher javaMatcher = JAVA_NATIVE.matcher(java);
        while(javaMatcher.find()){
            List<String> types = new ArrayList<>();
            for(String parameter : splitParameters(javaMatcher.group(2))){
                types.add(parameter.split("\\s+")[0]);
            }
            javaTypes.put(javaMatcher.group(1), types);
        }

        Map<String, List<String>> cTypes = new TreeMap<>();
        Matcher cMatcher = C_FUNCTION.matcher(c);
        while(cMatcher.find()){
            List<String> types = new ArrayList<>();
            for(String parameter : splitParameters(cMatcher.group(2))){
                // jlong -> long etc
                types.add(parameter.split("\\s+")[0].substring(1));
            }
            cTypes.put(cMatcher.group(1), types);
        }

        Map<String, String> cSignatures = new TreeMap<>();
        Matcher signatureMatcher = C_SIGNATURE_COMMENT.matcher(c);
        while(signatureMatcher.find()){
            cSignatures.put(signatureMatcher.group(1), signatureMatcher.group(2));
        }

        // make sure the parsing actually found the functions
        assertTrue(javaTypes.size() > 400, "Only found " + javaTypes.size() + " Java native methods");

        List<String> problems = new ArrayList<>();

        Set<String> allNames = new TreeSet<>(javaTypes.keySet());
        allNames.addAll(cTypes.keySet());
        for(String name : allNames){
            List<String> javaParameters = javaTypes.get(name);
            List<String> cParameters = cTypes.get(name);
            if(javaParameters == null){
                problems.add(name + " has a C function but no Java native method");
            } else if(cParameters == null){
                problems.add(name + " has a Java native method but no C function");
            } else{
                if(!javaParameters.equals(cParameters)){
                    problems.add(name + " Java " + javaParameters + " but C " + cParameters);
                }
                String expectedSignature = jniSignature(javaParameters);
                if(!expectedSignature.equals(cSignatures.get(name))){
                    problems.add(name + " Java signature (" + expectedSignature + ")I but the C comment says (" + cSignatures.get(name) + ")I");
                }
            }
        }

        assertTrue(problems.isEmpty(), "Java and C natives disagree:\n" + String.join("\n", problems));
    }

    private static String read(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
    }

    private static List<String> splitParameters(String parameters){
        List<String> result = new ArrayList<>();
        for(String parameter : parameters.split(",")){
            if(!parameter.trim().isEmpty()){
                result.add(parameter.trim());
            }
        }
        return result;
    }

    private static String jniSignature(List<String> javaTypes){
        StringBuilder signature = new StringBuilder();
        for(String type : javaTypes){
            switch(type){
                case "int": signature.append("I"); break;
                case "long": signature.append("J"); break;
                case "float": signature.append("F"); break;
                case "double": signature.append("D"); break;
                default: throw new IllegalArgumentException("Unexpected native parameter type " + type);
            }
        }
        return signature.toString();
    }
}
