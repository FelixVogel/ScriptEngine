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

import javax.script.ScriptException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Represents a evaluation engine, meaning it evaluates the provided script and returns a possible result rather than
 * caching present functions for execution
 *
 * @author Felix Vogel
 */
public class EvalEngine extends Engine {

    private ByteBuffer script;
    private Object result;

    /**
     * Create a new {@link EvalEngine} for the defined script file
     *
     * @param file The script file
     */
    public EvalEngine(final File file) {
        super(file);
    }

    @Override
    public void load() {
        try (final FileInputStream fis = new FileInputStream(file)) {
            script = ByteBuffer.allocate(fis.available());

            int b;
            while ((b = fis.read()) != -1) {
                script.put((byte) b);
            }
        } catch (final IOException e) {
            EngineUtility.debug(e);
        }

        EngineUtility.debug("Loaded Script: " + file.getAbsolutePath());
    }

    /**
     * Evaluate the current script file
     *
     * @return The evaluated result
     */
    public Object eval() {
        reload();

        try {
            result = scriptEngine.eval(new String(script.array()));
        } catch (final ScriptException e) {
            EngineUtility.debug(e);
        }

        return result;
    }

    /**
     * Get the result from the last evaluation
     *
     * @return The last evaluated result
     */
    public Object getResult() {
        return result;
    }

}
