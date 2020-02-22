/**
 * MIT License
 *
 * Copyright (c) 2020 Felix Vogel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.felix.script;

import javax.script.Invocable;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a function engine, meaning it evaluates a script and caches it so functions in the script can be called
 *
 * @author Felix Vogel
 */
public class FunctionEngine extends Engine {

    private final Map<String, Object> resultCache = new HashMap<>();

    /**
     * Create a new {@link FunctionEngine} for the defined script file
     *
     * @param file The script file
     */
    public FunctionEngine(final File file) {
        super(file);
    }

    @Override
    public void load() {
        try {
            scriptEngine.eval(new FileReader(file));
        } catch (final ScriptException | FileNotFoundException e) {
            EngineUtility.debug(e);
        }

        EngineUtility.debug("Loaded Script: " + file.getAbsolutePath());
    }

    /**
     * Call a root level function present in the current script
     *
     * @param name The name of the function to call
     * @param params The parameters for the function
     * @return The function result
     */
    public Object callFunction(final String name, final Object... params) {
        Object result = null;

        try {
            result = ((Invocable) scriptEngine).invokeFunction(name, params);
        } catch (final ScriptException | NoSuchMethodException e) {
            EngineUtility.debug(e);
        }

        resultCache.put(name, result);

        return result;
    }

    /**
     * Get a result of a previously executed function
     *
     * @param name The name of the function
     * @return The function result
     */
    public Object getResult(final String name) {
        return resultCache.get(name);
    }

}
